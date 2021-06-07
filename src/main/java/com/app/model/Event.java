package com.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {
    private final String info;
    private final String appName;
    private final String action;

    @JsonCreator
    public Event(@JsonProperty("info") String info,
                 @JsonProperty("appName") String appName,
                 @JsonProperty("action") String action) {
        this.info = info;
        this.appName = appName;
        this.action = action;
    }

    public String getInfo() {
        return info;
    }

    public String getAppName() {
        return appName;
    }

    public String getAction() {
        return action;
    }
}
