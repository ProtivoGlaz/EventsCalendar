package com.example.events_calendar.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents an immutable calendar event with a title, date, organizer, and schedule.
 */
public final class Event implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * Title or name of the event.
     */
    private final String title;

    /**
     * Date on which the event takes place.
     */
    private final LocalDate date;

    /**
     * Person or entity responsible for organizing the event.
     */
    private final Organizer organizer;

    /**
     * Schedule details associated with this event (e.g., start/end times).
     */
    private final Schedule schedule;

    /**
     * Constructs a new Event.
     *
     * @param title     the title or name of the event
     * @param date      the date on which the event takes place
     * @param organizer the organizer responsible for this event
     * @param schedule  the schedule details for the event
     */
    @JsonCreator
    public Event(
            @JsonProperty("title") String title,
            @JsonProperty("date") LocalDate date,
            @JsonProperty("organizer") Organizer organizer,
            @JsonProperty("schedule") Schedule schedule) {
        this.title = title;
        this.date = date;
        this.organizer = organizer;
        this.schedule = schedule;
    }

    /**
     * Returns the title of this event.
     *
     * @return the event title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the date of this event.
     *
     * @return the event date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the organizer of this event.
     *
     * @return the event organizer
     */
    public Organizer getOrganizer() {
        return organizer;
    }

    /**
     * Returns the schedule details of this event.
     *
     * @return the event schedule
     */
    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(title, event.title) && Objects.equals(date, event.date) && Objects.equals(organizer, event.organizer) && Objects.equals(schedule, event.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, organizer, schedule);
    }

    @Override
    public String toString() {
        return String.format("Event[title='%s', date=%s, organizer=%s, schedule=%s]",
                title, date, organizer, schedule);
    }
}