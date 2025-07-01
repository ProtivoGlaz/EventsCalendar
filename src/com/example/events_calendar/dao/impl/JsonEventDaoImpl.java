package com.example.events_calendar.dao.impl;

import com.example.events_calendar.dao.DaoException;
import com.example.events_calendar.dao.EventCalendarDao;
import com.example.events_calendar.model.Event;
import java.io.IOException;
import java.util.*;

/**
 * JSON-based implementation of {@link EventCalendarDao}.
 * <p>
 * Internally delegates CRUD operations to an {@link EventStorageService},
 * persisting events in a JSON file. All methods synchronize on an internal lock
 * to ensure thread safety when accessing the underlying storage.</p>
 */
public class JsonEventDaoImpl implements EventCalendarDao {

    /** Service handling low-level JSON file read/write and in-memory map operations. */
    private final EventStorageService storageService;

    /** Lock object used to synchronize access to the storageService. */
    private final Object lock = new Object();

    /**
     * Constructs a new JsonEventDaoImpl that stores events in the given JSON file.
     *
     * @param filePath the path to the JSON file used for event persistence
     * @throws DaoException if the storage handler cannot be initialized
     */
    public JsonEventDaoImpl(String filePath) throws DaoException {
        try {
            JsonStorageHandler handler = new JsonStorageHandler(filePath);
            this.storageService = new EventStorageService(handler);
        } catch (IOException e) {
            throw new DaoException("Initialization failed", e);
        }
    }

    /**
     * Persists a new {@link Event} in the JSON store.
     *
     * @param event the event to create; must not be null
     * @throws DaoException if the event is null, already exists, or an I/O error occurs
     */
    @Override
    public void create(Event event) throws DaoException {
        if (event == null) throw new DaoException("Event cannot be null");
        synchronized (lock) {
            try {
                storageService.addEvent(event);
            } catch (IOException | IllegalStateException e) {
                throw new DaoException("Failed to create event", e);
            }
        }
    }

    /**
     * Reads an {@link Event} by its title from the JSON store.
     *
     * @param title the title of the event to read; must not be null
     * @return the event with the specified title
     * @throws DaoException if the title is null, no such event exists, or an I/O error occurs
     */
    @Override
    public Event read(String title) throws DaoException {
        if (title == null) throw new DaoException("Title cannot be null");
        synchronized (lock) {
            try {
                return storageService.getEvent(title);
            } catch (IOException | NoSuchElementException e) {
                throw new DaoException("Failed to read event", e);
            }
        }
    }

    /**
     * Updates an existing {@link Event} in the JSON store.
     *
     * @param event the event containing updated data; must not be null
     * @throws DaoException if the event does not exist or an I/O error occurs
     */
    @Override
    public void update(Event event) throws DaoException {
        synchronized (lock) {
            try {
                storageService.updateEvent(event);
            } catch (IOException | NoSuchElementException e) {
                throw new DaoException("Failed to update event", e);
            }
        }
    }

    /**
     * Deletes the {@link Event} with the given title from the JSON store.
     *
     * @param title the title of the event to delete; must not be null
     * @throws DaoException if the event does not exist or an I/O error occurs
     */
    @Override
    public void delete(String title) throws DaoException {
        synchronized (lock) {
            try {
                storageService.deleteEvent(title);
            } catch (IOException | NoSuchElementException e) {
                throw new DaoException("Failed to delete event", e);
            }
        }
    }

    /**
     * Returns a list of all events currently stored in the JSON file.
     *
     * @return a {@link List} of all {@link Event} instances
     * @throws DaoException if an I/O error occurs during retrieval
     */
    @Override
    public List<Event> findAll() throws DaoException {
        synchronized (lock) {
            try {
                return storageService.findAll();
            } catch (IOException e) {
                throw new DaoException("Failed to list events", e);
            }
        }
    }

}