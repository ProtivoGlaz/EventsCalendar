package com.example.events_calendar.service;

import com.example.events_calendar.service.impl.EventCalendarServiceImpl;

/**
 * Singleton provider for application service instances.
 *
 * <p>This class ensures a single {@link EventCalendarService} is created
 * and shared throughout the application.</p>
 */
public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private final EventCalendarService eventCalendarService = new EventCalendarServiceImpl();

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        return instance;
    }


    public EventCalendarService getEventService() {
        if (eventCalendarService == null) {
            throw new IllegalStateException("EventCalendarService is not initialized");
        }
        return eventCalendarService;
    }
}
