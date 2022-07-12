package com.jet.restaurants.service.openclose.service;

import com.jet.restaurants.service.openclose.domain.Restaurant;
import com.jet.restaurants.service.openclose.domain.Status;
import com.jet.restaurants.service.openclose.repo.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
@Slf4j
@Service
public class RestaurantService {

    @Value(value="${jet.restaurant.producer.kafka.topic}")
    private String kafkaTopic;

    private final RestaurantRepository restaurantRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository,
                             KafkaTemplate<String, String> kafkaTemplate) {
        this.restaurantRepository = restaurantRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void createRestaurant(String name, Status status) {
         restaurantRepository.save(Restaurant.create(name, status));
    }


    public void sendMessage(String message) {

        //send message to the kafka topic
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(kafkaTopic, message);

        //adding callback methods for success and failure
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info(String.format("Sent restaurant data = %s  with offset= %s ",
                        message, result.getRecordMetadata().offset()));

            }

            @Override
            public void onFailure( Throwable ex) {
                log.info(String.format("Unable to send restaurant data = %s  due to : %s",
                        message, ex.getMessage()));

            }

        });
    }

}
