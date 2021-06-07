package com.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventBody {
    private final String type;
    private final String source;
    private final String customerId;
    private final String screenName;
    private final Event event;

    @JsonCreator
    public EventBody(@JsonProperty("type") String type,
                     @JsonProperty("source") String source,
                     @JsonProperty("customerId") String customerId,
                     @JsonProperty("screenName") String screenName,
                     @JsonProperty("event") Event event) {
        this.type = type;
        this.source = source;
        this.customerId = customerId;
        this.screenName = screenName;
        this.event = event;
    }

    public String getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getScreenName() {
        return screenName;
    }

    public Event getEvents() {
        return event;
    }
}
