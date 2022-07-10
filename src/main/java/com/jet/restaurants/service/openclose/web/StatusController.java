package com.jet.restaurants.service.openclose.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jet.restaurants.service.openclose.domain.Restaurant;
import com.jet.restaurants.service.openclose.repo.RestaurantRepository;
import com.jet.restaurants.service.openclose.service.RestaurantService;
import com.jet.restaurants.service.openclose.web.requests.UpdateRestaurantStatusRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;
@Slf4j
@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/status")
public class StatusController {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;


    private final ObjectMapper objectMapper;
    @Autowired
    public StatusController(RestaurantRepository restaurantRepository, ObjectMapper objectMapper, RestaurantService restaurantService) {
        this.restaurantRepository = restaurantRepository;
        this.objectMapper = objectMapper;
        this.restaurantService = restaurantService;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @KafkaHandler
    public Restaurant updateRestaurantStatus(@PathVariable(value = "restaurantId") Integer restaurantId,
                                       @RequestBody UpdateRestaurantStatusRequest request) throws JsonProcessingException {
        Restaurant restaurant = verifyRestaurant(restaurantId);
        restaurant.setStatus(request.getStatus());
        restaurantRepository.save(restaurant);
        log.info("Saving restaurant data - Success");
        String orderAsMessage = objectMapper.writeValueAsString(restaurant);

        restaurantService.sendMessage(orderAsMessage);

        return restaurant;
    }

    private Restaurant verifyRestaurant(Integer restaurantId) throws NoSuchElementException {
        return restaurantRepository.findById(restaurantId).orElseThrow(() ->
                new NoSuchElementException("Restaurant not found with id:" + restaurantId));
    }

    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex exception
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }

}
