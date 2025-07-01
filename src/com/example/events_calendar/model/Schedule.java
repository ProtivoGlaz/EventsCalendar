package com.example.events_calendar.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents an event schedule that maps specific times to activity descriptions.
 */
public class Schedule implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * Maps a time to its corresponding activity description.
     */
    private final Map<LocalTime, String> activitiesByTime;

    /**
     * Initializes the activities map.
     */ {
        activitiesByTime = new HashMap<>();
    }

    /**
     * Constructs an empty Schedule.
     */
    public Schedule() {
    }

    /**
     * Returns the schedule of activities.
     *
     * @return a map where each key is the time of an activity, and the value is its description
     */
    public Map<LocalTime, String> getActivitiesByTime() {
        return activitiesByTime;
    }

    /**
     * Adds an activity entry to the schedule.
     * <p>
     * The input string must follow the format "HH:mm description", where the first token
     * is the time in 24-hour format and the second token is the activity description.
     *
     * @param activity a string containing a time and an activity description
     * @throws IllegalArgumentException if the input does not contain both time and description
     * @throws NumberFormatException    if the hour or minute cannot be parsed as integers
     */
    public void addActivity(String activity) {
        String[] params = activity.split(" ");

        LocalTime time = parseTime(params[0]);
        String activities = params[1];

        activitiesByTime.put(time, activities);
    }

    /**
     * Parses a time string in "HH:mm" format into a {@link LocalTime} instance.
     *
     * @param request the time string to parse
     * @return a LocalTime representing the parsed hour and minute
     * @throws ArrayIndexOutOfBoundsException if the string does not contain hour and minute parts
     * @throws NumberFormatException          if the hour or minute parts are not valid integers
     */
    private LocalTime parseTime(String request) {
        String[] timeParams = request.split(":");
        int hour = Integer.parseInt(timeParams[0]);
        int min = Integer.parseInt(timeParams[1]);

        LocalTime response = LocalTime.of(hour, min);
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(activitiesByTime, schedule.activitiesByTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(activitiesByTime);
    }

    @Override
    public String toString() {
        return String.format("Schedule[activitiesByTime=%s]", activitiesByTime);
    }
}