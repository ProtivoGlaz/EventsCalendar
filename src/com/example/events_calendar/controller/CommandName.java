package com.example.events_calendar.controller;

/**
 * Enumeration of supported command identifiers for the Event Calendar application.
 * <p>
 * Each constant corresponds to a specific user command that can be executed
 * by the {@link com.example.events_calendar.controller.impl.CommandProvider}.
 */
public enum CommandName {
    /**
     * Command to add a new event to the calendar.
     */
    ADD_EVENT,
    /**
     * Command to update an existing event in the calendar.
     */
    UPDATE_EVENT,
    /**
     * Command to retrieve a specific event by its title.
     */
    GET_EVENT,
    /**
     * Command to delete an event from the calendar by its title.
     */
    DELETE_EVENT,
    /**
     * Command to list all events currently in the calendar.
     */
    LIST_EVENT,
    /**
     * Fallback command for unknown or unsupported request names.
     */
    WRONG_REQUEST
}
