package com.jet.restaurants.service.openclose.service;

import com.jet.restaurants.service.openclose.domain.Restaurant;
import com.jet.restaurants.service.openclose.domain.Status;
import com.jet.restaurants.service.openclose.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {
    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant createRestaurant(String name, Status status) {
        Restaurant restaurant = restaurantRepository.save(Restaurant.create(name, status));

        return restaurant;
    }

}
