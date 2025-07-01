package com.example.events_calendar.controller.impl;

import com.example.events_calendar.controller.Command;
import com.example.events_calendar.controller.EventCalendarException;
import com.example.events_calendar.model.Event;
import com.example.events_calendar.service.EventCalendarService;
import com.example.events_calendar.service.ServiceProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Command implementation for fetching an {@link Event} by its title.
 * <p>
 * It parses the event title from the input request string, invokes the service layer
 * to retrieve the event, and returns the event serialized as JSON.
 */
public class GetEventCommand implements Command {
    /**
     * Jackson object mapper configured to support Java 8 date/time types.
     */
    private final ObjectMapper mapper;
    /**
     * Service responsible for managing calendar events.
     */
    private final EventCalendarService service;

    /**
     * Constructs a GetEventCommand.
     * <p>
     * Initializes the JSON mapper with {@link JavaTimeModule} to handle
     * {@link java.time.LocalDate} and other Java 8 date/time types.
     * Obtains the {@link EventCalendarService} from the {@link ServiceProvider}.
     */
    public GetEventCommand() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.service = ServiceProvider.getInstance().getEventService();
    }

    /**
     * Executes the command by extracting the event title from the request string,
     * retrieving the corresponding {@link Event}, and serializing it to JSON.
     *
     * @param request the raw command string, expected to contain a space
     *                followed by the title of the event to fetch
     * @return the JSON representation of the requested event
     * @throws EventCalendarException if the title is missing or empty,
     *                                or if an error occurs during retrieval or serialization
     */
    @Override
    public String execute(String request) throws EventCalendarException {
        try {
            int idx = request.indexOf(' ');
            if (idx == -1) {
                throw new EventCalendarException("Event name not passed");
            }
            String title = request.substring(idx + 1).trim();
            if (title.isEmpty()) {
                throw new EventCalendarException("Empty event name");
            }
            Event event = service.getEvent(title);
            return mapper.writeValueAsString(event);
        } catch (EventCalendarException e) {
            throw e;
        } catch (Exception e) {
            throw new EventCalendarException("Error when receiving an event: ", e);
        }
    }
}