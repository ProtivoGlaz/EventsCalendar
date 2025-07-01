package com.example.events_calendar.controller;

import java.io.Serial;

/**
 * Represents an error that occurs during processing of Event Calendar operations.
 *
 * <p>This exception can carry a custom message, wrap an underlying cause,
 * or both, to indicate failures in command execution or service interactions.</p>
 */
public class EventCalendarException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public EventCalendarException() {
        super();
    }

    public EventCalendarException(String message) {
        super(message);
    }

    public EventCalendarException(Exception e) {
        super(e);
    }

    public EventCalendarException(String message, Exception e) {
        super(message, e);
    }
}
