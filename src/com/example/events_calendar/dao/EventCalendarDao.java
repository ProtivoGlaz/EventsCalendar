package com.example.events_calendar.dao;

import com.example.events_calendar.model.Event;

import java.util.List;

/**
 * Defines CRUD operations for storing and retrieving {@link Event} instances
 * from the underlying data store.
 */
public interface EventCalendarDao {

    /**
     * Persists a new event in the data store.
     *
     * @param event the {@link Event} to create; must not be null
     * @throws DaoException if an error occurs during creation
     */
    void create(Event event) throws DaoException;

    /**
     * Retrieves an event by its title.
     *
     * @param title the title of the event to read; must not be null or blank
     * @return the {@link Event} with the specified title
     * @throws DaoException if no event is found or a data-access error occurs
     */
    Event read(String title) throws DaoException;

    /**
     * Updates an existing event in the data store.
     *
     * @param event the {@link Event} containing updated data; must not be null
     * @throws DaoException if the event does not exist or a data-access error occurs
     */
    void update(Event event) throws DaoException;

    /**
     * Removes an event from the data store by its title.
     *
     * @param title the title of the event to delete; must not be null or blank
     * @throws DaoException if no event is found with the given title or a data-access error occurs
     */
    void delete(String title) throws DaoException;

    /**
     * Returns all events currently stored in the data store.
     *
     * @return a {@link List} of all {@link Event} instances
     * @throws DaoException if a data-access error occurs
     */
    List<Event> findAll() throws DaoException;
}
