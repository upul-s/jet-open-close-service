package com.jet.restaurants.service.openclose;

import com.jet.restaurants.service.openclose.domain.Status;
import com.jet.restaurants.service.openclose.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JetRestaurantOpenCloseService implements CommandLineRunner {

	@Autowired
	private RestaurantService restaurantService;

	public static void main(String[] args) {
		SpringApplication.run(JetRestaurantOpenCloseService.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		restaurantService.createRestaurant("McDonalds", Status.OPEN);
		restaurantService.createRestaurant("Burger King", Status.OPEN);
		restaurantService.createRestaurant("Pizza Hut", Status.OPEN);
		restaurantService.createRestaurant("KFC", Status.OPEN);
		restaurantService.createRestaurant("BurgerMe", Status.OPEN);
 	}


}
