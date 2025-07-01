package com.example.events_calendar.dao;

import com.example.events_calendar.dao.impl.JsonEventDaoImpl;

/**
 * Singleton provider for Data Access Object (DAO) instances.
 *
 * <p>Initializes and holds a single {@link EventCalendarDao} implementation
 * backed by a JSON file. Consumers call {@link #getEventDao()} to retrieve
 * the DAO for performing CRUD operations on events.</p>
 */
public final class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();

    private final EventCalendarDao eventCalendarDao;

    private DaoProvider() {
        try {
            this.eventCalendarDao = new JsonEventDaoImpl("events.json");
        } catch (DaoException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static DaoProvider getInstance() {
        return instance;
    }

    public EventCalendarDao getEventDao() {
        return eventCalendarDao;
    }
}