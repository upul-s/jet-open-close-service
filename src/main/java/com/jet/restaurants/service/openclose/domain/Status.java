package com.jet.restaurants.service.openclose.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum Status {
    OPEN("open"), CLOSED("closed");

    private String status;

    private Status(String status) {
        this.status = status;
    }

    @JsonCreator
    public static Status decode(final String status) {
        return Stream.of(Status.values()).filter(targetEnum -> targetEnum.status.equals(status)).findFirst().orElse(null);
    }

    @JsonValue
    public String getStatus() {
        return status;
    }


}
