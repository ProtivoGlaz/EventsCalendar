package com.example.events_calendar.controller.impl;

import com.example.events_calendar.controller.Command;
import com.example.events_calendar.controller.EventCalendarException;
import com.example.events_calendar.model.Event;
import com.example.events_calendar.service.EventCalendarService;
import com.example.events_calendar.service.ServiceProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Command implementation that handles adding a new {@link Event} to the calendar.
 * <p>
 * It extracts the JSON payload from the request string, deserializes it into an Event,
 * and delegates the addition to the {@link EventCalendarService}.
 */
public class AddEventCommand implements Command {
    /**
     * Jackson object mapper configured to support Java 8 date/time types.
     */
    private final ObjectMapper mapper;
    /**
     * Service responsible for managing calendar events.
     */
    private final EventCalendarService service;

    /**
     * Constructs an AddEventCommand.
     * <p>
     * Initializes the JSON mapper with {@link JavaTimeModule} to handle
     * {@link java.time.LocalDate} and other Java 8 date/time types.
     * Obtains the {@link EventCalendarService} from the {@link ServiceProvider}.
     */
    public AddEventCommand() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.service = ServiceProvider.getInstance().getEventService();
    }

    /**
     * Executes the command by parsing the request, deserializing the event JSON,
     * and adding the event to the calendar.
     *
     * @param request the raw command string, expected to contain a space
     *                followed by the JSON representation of an Event
     * @return a success message including the title of the added event
     * @throws EventCalendarException if the request is malformed,
     *                                the JSON is empty or invalid,
     *                                or an internal error occurs during addition
     */
    @Override
    public String execute(String request) throws EventCalendarException {
        try {
            int idx = request.indexOf(' ');
            if (idx == -1) {
                throw new EventCalendarException("Event data not transferred");
            }
            String eventJson = request.substring(idx + 1).trim();
            if (eventJson.isEmpty()) {
                throw new EventCalendarException("Empty event data");
            }
            Event event = mapper.readValue(eventJson, Event.class);
            service.addEvent(event);
            return "The event has been successfully added: " + event.getTitle();
        } catch (EventCalendarException e) {
            throw e;
        } catch (Exception e) {
            throw new EventCalendarException("Error when adding an event: ", e);
        }
    }
}
