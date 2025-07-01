package com.example.events_calendar.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the organizer of an event.
 */
public class Organizer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * Name of the organizer.
     */
    private final String name;

    /**
     * Constructs a new Organizer.
     *
     * @param name the name of the event organizer
     */
    @JsonCreator
    public Organizer(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Returns the name of this organizer.
     *
     * @return the organizer's name
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Organizer organizer = (Organizer) o;
        return Objects.equals(name, organizer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return String.format("Organizer[name='%s']", name);
    }
}