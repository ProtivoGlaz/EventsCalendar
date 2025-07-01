package com.example.events_calendar.controller.impl;

import com.example.events_calendar.controller.Command;
import com.example.events_calendar.controller.EventCalendarException;
import com.example.events_calendar.model.Event;
import com.example.events_calendar.service.EventCalendarService;
import com.example.events_calendar.service.ServiceProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;

/**
 * Command implementation for retrieving the full list of events.
 * <p>
 * It delegates to the {@link EventCalendarService} to fetch all events
 * and returns their JSON representation.
 */
public class ListEventCommand implements Command {
    /**
     * Jackson mapper configured to handle Java 8 date/time types.
     */
    private final ObjectMapper mapper = new ObjectMapper();
    /**
     * Service responsible for managing calendar events.
     */
    private final EventCalendarService service = ServiceProvider.getInstance().getEventService();

    /**
     * Constructs a new ListEventCommand and registers the
     * {@link JavaTimeModule} on the JSON mapper to support
     * serialization of Java 8 date/time objects.
     */
    public ListEventCommand() {
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Executes the command by fetching all events from the service
     * and serializing them into a JSON array.
     *
     * @param request the raw command string (ignored by this implementation)
     * @return a JSON array string representing all events
     * @throws EventCalendarException if an error occurs during retrieval or serialization
     */
    @Override
    public String execute(String request) throws EventCalendarException {
        try {
            List<Event> events = service.getAllEvents();
            return mapper.writeValueAsString(events);
        } catch (Exception e) {
            throw new EventCalendarException("Error when retrieving the list of events: ", e);
        }
    }
}
