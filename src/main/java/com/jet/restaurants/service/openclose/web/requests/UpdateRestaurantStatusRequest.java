package com.jet.restaurants.service.openclose.web.requests;

import com.jet.restaurants.service.openclose.domain.Status;
import lombok.Data;

@Data
public class UpdateRestaurantStatusRequest {

    public Status status;
}
