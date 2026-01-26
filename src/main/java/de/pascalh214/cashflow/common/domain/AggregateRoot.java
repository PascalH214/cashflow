package de.pascalh214.cashflow.common.domain;

import java.util.ArrayList;
import java.util.List;

public class AggregateRoot {

    private final List<Object> events = new ArrayList<>();

    public void pushEvent(Object event) {
        events.add(event);
    }

    public List<Object> getEvents() {
        List<Object> copy = List.copyOf(events);
        this.events.clear();
        return copy;
    }

}
