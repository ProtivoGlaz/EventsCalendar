package com.example.events_calendar.service;

import java.io.Serial;

/**
 * Represents an exception thrown by the service layer of the Event Calendar application.
 *
 * <p>This exception can carry a custom message, wrap an underlying cause,
 * or both, to indicate failures during business logic execution.</p>
 */
public class ServiceException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}
