package com.jet.restaurants.service.openclose.web;

import com.jet.restaurants.service.openclose.domain.Restaurant;
import com.jet.restaurants.service.openclose.repo.RestaurantRepository;
import com.jet.restaurants.service.openclose.web.requests.UpdateRestaurantStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/status")
public class StatusController {
    private RestaurantRepository restaurantRepository;

    @Autowired
    public StatusController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @KafkaHandler
    public Restaurant updateRestaurantStatus(@PathVariable(value = "restaurantId") Integer restaurantId,
                                       @RequestBody UpdateRestaurantStatusRequest request) {
        Restaurant restaurant = verifyRestaurant(restaurantId);
        restaurant.setStatus(request.getStatus());
        restaurantRepository.save(restaurant);

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
