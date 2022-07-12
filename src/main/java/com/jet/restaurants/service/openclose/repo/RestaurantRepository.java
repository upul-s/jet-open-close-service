package com.jet.restaurants.service.openclose.repo;

import com.jet.restaurants.service.openclose.domain.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RestaurantRepository
        extends CrudRepository<Restaurant, Integer> {

    Optional<Restaurant> findByName(String name);

}
