package com.jet.restaurants.service.openclose.repo;

import com.jet.restaurants.service.openclose.domain.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RestaurantRepository
        extends CrudRepository<Restaurant, Integer> {

    Optional<Restaurant> findByName(String name);

}
