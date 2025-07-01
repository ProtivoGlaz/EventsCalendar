package com.example.events_calendar.service;

import com.example.events_calendar.model.Event;

import java.util.List;

/**
 * Defines operations for managing calendar events at the business-logic layer.
 * <p>
 * Implementations of this interface handle validation, persistence interactions,
 * and any additional processing required when creating, retrieving, updating,
 * or deleting events.</p>
 */
public interface EventCalendarService {

    /**
     * Adds a new event to the calendar.
     *
     * @param event the {@link Event} to add
     * @throws ServiceException if validation fails or a persistence error occurs
     */
    void addEvent(Event event) throws ServiceException;

    /**
     * Retrieves an event by its title.
     *
     * @param title the title of the event to retrieve
     * @return the {@link Event} with the specified title
     * @throws ServiceException if no event with the given title exists or a retrieval error occurs
     */
    Event getEvent(String title) throws ServiceException;

    /**
     * Updates an existing event in the calendar.
     *
     * @param event the {@link Event} containing updated data
     * @throws ServiceException if the event does not exist, validation fails, or an update error occurs
     */
    void updateEvent(Event event) throws ServiceException;

    /**
     * Deletes an event from the calendar by its title.
     *
     * @param title the title of the event to delete
     * @throws ServiceException if no event with the given title exists or a deletion error occurs
     */
    void deleteEvent(String title) throws ServiceException;

    /**
     * Returns a list of all events currently in the calendar.
     *
     * @return a {@link List} of all {@link Event} instances
     * @throws ServiceException if a retrieval error occurs
     */
    List<Event> getAllEvents() throws ServiceException;
}
