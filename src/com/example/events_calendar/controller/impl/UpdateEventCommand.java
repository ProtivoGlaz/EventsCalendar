package com.example.events_calendar.controller.impl;

import com.example.events_calendar.controller.Command;
import com.example.events_calendar.controller.EventCalendarException;
import com.example.events_calendar.model.Event;
import com.example.events_calendar.service.EventCalendarService;
import com.example.events_calendar.service.ServiceProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Command implementation for updating an existing {@link Event} in the calendar.
 * <p>
 * It extracts the JSON payload from the request string, deserializes it into an Event,
 * and delegates the update operation to the {@link EventCalendarService}.
 */
public class UpdateEventCommand implements Command {
    /**
     * Jackson object mapper configured to support Java 8 date/time types.
     */
    private final ObjectMapper mapper = new ObjectMapper();
    /**
     * Service responsible for managing calendar events.
     */
    private final EventCalendarService service = ServiceProvider.getInstance().getEventService();

    /**
     * Constructs an UpdateEventCommand.
     * <p>
     * Registers the {@link JavaTimeModule} on the JSON mapper to handle
     * {@link java.time.LocalDate} and other Java 8 date/time types during serialization/deserialization.
     */
    public UpdateEventCommand() {
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Executes the command by parsing the request, deserializing the event JSON,
     * and updating the event in the calendar.
     *
     * @param request the raw command string, expected to contain a space
     *                followed by the JSON representation of the updated Event
     * @return a confirmation message upon successful update
     * @throws EventCalendarException if the request is malformed,
     *                                the JSON is empty or invalid,
     *                                or an internal error occurs during update
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
            service.updateEvent(event);
            return "Event successfully updated";
        } catch (EventCalendarException e) {
            throw e;
        } catch (Exception e) {
            throw new EventCalendarException("Error when updating an event: ", e);
        }
    }
}
