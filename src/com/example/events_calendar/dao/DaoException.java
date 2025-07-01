package com.example.events_calendar.dao;

import java.io.Serial;

/**
 * Represents an exception that occurs within the data access layer
 * when performing CRUD operations on {@link com.example.events_calendar.model.Event} instances.
 *
 * <p>This exception can carry a custom message, wrap an underlying cause,
 * or both, to indicate failures during persistence operations.</p>
 */
public final class DaoException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Exception e) {
        super(e);
    }

    public DaoException(String message, Exception e) {
        super(message, e);
    }
}