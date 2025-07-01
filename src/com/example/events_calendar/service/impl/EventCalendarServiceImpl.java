package com.example.events_calendar.service.impl;

import com.example.events_calendar.dao.DaoException;
import com.example.events_calendar.dao.DaoProvider;
import com.example.events_calendar.dao.EventCalendarDao;
import com.example.events_calendar.model.Event;
import com.example.events_calendar.service.EventCalendarService;
import com.example.events_calendar.service.ServiceException;

import java.util.List;

/**
 * Default implementation of {@link EventCalendarService} that performs
 * validation on input data and delegates persistence operations to
 * an {@link EventCalendarDao}.
 */
public class EventCalendarServiceImpl implements EventCalendarService {

    /** Data access object used for CRUD operations on events. */
    private final EventCalendarDao eventDao;

    /**
     * Constructs a new EventCalendarServiceImpl and retrieves
     * the {@link EventCalendarDao} instance from {@link DaoProvider}.
     */
    public EventCalendarServiceImpl() {
        this.eventDao = DaoProvider.getInstance().getEventDao();
    }

    /**
     * Validates the provided {@link Event} and adds it to the calendar.
     *
     * @param event the Event to add; must not be null and must have non-blank
     *              title, non-null date, and a non-blank organizer name
     * @throws ServiceException if validation fails or a persistence error occurs
     */
    @Override
    public void addEvent(Event event) throws ServiceException {
        try {
            if (event == null) {
                throw new ServiceException("Event cannot be null");
            }
            if (event.getTitle() == null || event.getTitle().isBlank()) {
                throw new ServiceException("The name of the event is required");
            }
            if (event.getDate() == null) {
                throw new ServiceException("Date of the event is required");
            }
            if (event.getOrganizer() == null || event.getOrganizer().getName() == null || event.getOrganizer().getName().isBlank()) {
                throw new ServiceException("An organiser is a must");
            }

            eventDao.create(event);
        } catch (DaoException e) {
            throw new ServiceException("Error adding event", e);
        }
    }

    /**
     * Retrieves an {@link Event} by its title.
     *
     * @param title the title of the event to retrieve; must not be null or blank
     * @return the Event matching the given title
     * @throws ServiceException if validation fails, the event does not exist,
     *                          or a persistence error occurs
     */
    @Override
    public Event getEvent(String title) throws ServiceException {
        try {
            if (title == null || title.isBlank()) {
                throw new ServiceException("The name of the event is required");
            }
            return eventDao.read(title);
        } catch (DaoException e) {
            throw new ServiceException("Error receiving event", e);
        }
    }

    /**
     * Validates and updates an existing {@link Event} in the calendar.
     *
     * @param event the Event containing updated data; must not be null and
     *              must have non-blank title, non-null date, and a non-blank organizer name
     * @throws ServiceException if validation fails or a persistence error occurs
     */
    @Override
    public void updateEvent(Event event) throws ServiceException {
        try {
            if (event == null) {
                throw new ServiceException("Event cannot be null");
            }
            if (event.getTitle() == null || event.getTitle().isBlank()) {
                throw new ServiceException("The name of the event is required");
            }
            if (event.getDate() == null) {
                throw new ServiceException("Date of the event is required");
            }
            if (event.getOrganizer() == null || event.getOrganizer().getName() == null || event.getOrganizer().getName().isBlank()) {
                throw new ServiceException("An organiser is a must");
            }

            eventDao.update(event);
        } catch (DaoException e) {
            throw new ServiceException("Event update error", e);
        }
    }

    /**
     * Deletes the event with the specified title from the calendar.
     *
     * @param title the title of the event to delete; must not be null or blank
     * @throws ServiceException if validation fails or a persistence error occurs
     */
    @Override
    public void deleteEvent(String title) throws ServiceException {
        try {
            if (title == null || title.isBlank()) {
                throw new ServiceException("The name of the event is required");
            }
            eventDao.delete(title);
        } catch (DaoException e) {
            throw new ServiceException("Error deleting an event", e);
        }
    }

    /**
     * Fetches all events currently stored in the calendar.
     *
     * @return a list of all {@link Event} objects
     * @throws ServiceException if a persistence error occurs during retrieval
     */
    @Override
    public List<Event> getAllEvents() throws ServiceException {
        try {
            return eventDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Error receiving event list", e);
        }
    }
}
