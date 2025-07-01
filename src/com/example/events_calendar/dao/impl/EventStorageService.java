package com.example.events_calendar.dao.impl;

import com.example.events_calendar.model.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Provides CRUD operations for persisting {@link Event} instances using a JSON-based storage handler.
 *
 * <p>This service wraps a {@link JsonStorageHandler} to read the entire storage map,
 * apply modifications (create, read, update, delete), and write the updated map back
 * to the underlying JSON file. It ensures that events are uniquely identified by their titles
 * and throws appropriate exceptions when operations cannot be completed.</p>
 */
public class EventStorageService {

    /** Handler responsible for low-level JSON file read/write operations. */
    private final JsonStorageHandler storageHandler;

    /**
     * Constructs a new EventStorageService with the specified JSON storage handler.
     *
     * @param storageHandler the handler used to read from and write to the JSON storage file
     */
    public EventStorageService(JsonStorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    /**
     * Adds a new event to the storage.
     *
     * @param event the {@link Event} to add; must not already exist in storage
     * @throws IOException           if an I/O error occurs while reading or writing the file
     * @throws IllegalStateException if an event with the same title already exists
     */
    public void addEvent(Event event) throws IOException, IllegalStateException {
        Map<String, Event> storage = storageHandler.read();
        if (storage.containsKey(event.getTitle())) {
            throw new IllegalStateException("Event already exists: " + event.getTitle());
        }
        storage.put(event.getTitle(), event);
        storageHandler.write(storage);
    }

    /**
     * Retrieves an event by its title.
     *
     * @param title the title of the event to retrieve; must not be null or blank
     * @return the {@link Event} matching the given title
     * @throws IOException            if an I/O error occurs while reading the file
     * @throws NoSuchElementException if no event with the given title is found
     */
    public Event getEvent(String title) throws IOException, NoSuchElementException {
        Map<String, Event> storage = storageHandler.read();
        Event event = storage.get(title);
        if (event == null) {
            throw new NoSuchElementException("Event not found: " + title);
        }
        return event;
    }

    /**
     * Updates an existing event in storage.
     *
     * @param event the {@link Event} containing updated data; must already exist
     * @throws IOException            if an I/O error occurs while reading or writing the file
     * @throws NoSuchElementException if no event with the given title is found
     */
    public void updateEvent(Event event) throws IOException, NoSuchElementException {
        Map<String, Event> storage = storageHandler.read();
        if (!storage.containsKey(event.getTitle())) {
            throw new NoSuchElementException("Event not found: " + event.getTitle());
        }
        storage.put(event.getTitle(), event);
        storageHandler.write(storage);
    }

    /**
     * Deletes an event from storage by its title.
     *
     * @param title the title of the event to delete; must already exist
     * @throws IOException            if an I/O error occurs while reading or writing the file
     * @throws NoSuchElementException if no event with the given title is found
     */
    public void deleteEvent(String title) throws IOException, NoSuchElementException {
        Map<String, Event> storage = storageHandler.read();
        if (!storage.containsKey(title)) {
            throw new NoSuchElementException("Event not found: " + title);
        }
        storage.remove(title);
        storageHandler.write(storage);
    }

    /**
     * Returns a list of all events currently stored.
     *
     * @return a {@link List} of all {@link Event} instances in storage
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<Event> findAll() throws IOException {
        return new ArrayList<>(storageHandler.read().values());
    }
}

