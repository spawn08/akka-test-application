package com.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

    final String status;

    @JsonCreator
    public Response(@JsonProperty("name") String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
