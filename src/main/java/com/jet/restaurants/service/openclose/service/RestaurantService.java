package com.jet.restaurants.service.openclose.service;

import com.jet.restaurants.service.openclose.domain.Restaurant;
import com.jet.restaurants.service.openclose.domain.Status;
import com.jet.restaurants.service.openclose.repo.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
@Slf4j
@Service
public class RestaurantService {

    private static final String TOPIC_RES_STATUS = "restaurants-status";

    private final RestaurantRepository restaurantRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void createRestaurant(String name, Status status) {
         restaurantRepository.save(Restaurant.create(name, status));
    }



    public void sendMessage(String message) {

        //send message to the kafka topic
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(TOPIC_RES_STATUS, message);

        //adding callback methods for succcess and failure
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sending restaurant data to topic - Success");
                log.info("Sent restaurant data =[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure( Throwable ex) {
                log.info("Sending restaurant data to topic - Failed");
                log.error("Unable to send restaurant data =["
                        + message + "] due to : " + ex.getMessage());

            }
        });
    }

}
