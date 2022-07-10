package com.jet.restaurants.service.openclose.exception;

public class RestaurantManagementException extends ConnectException {

	public RestaurantManagementException(Exception e, String key, String message, Object... msgVars) {
		super(e, key, message, msgVars);
	}

	public RestaurantManagementException(String key, String message, Object... msgVars) {
		super(key, message, msgVars);
	}
}

