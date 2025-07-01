package com.example.events_calendar.controller.impl;

import com.example.events_calendar.controller.Command;
import com.example.events_calendar.controller.EventCalendarException;
import com.example.events_calendar.service.EventCalendarService;
import com.example.events_calendar.service.ServiceProvider;

/**
 * Command implementation for deleting an existing {@link com.example.events_calendar.model.Event}
 * from the calendar by its title.
 */
public class DeleteEventCommand implements Command {
    /**
     * Service responsible for managing calendar events.
     */
    private final EventCalendarService service = ServiceProvider.getInstance().getEventService();

    /**
     * Executes the delete command by extracting the event title from the request string
     * and delegating the deletion to the {@link EventCalendarService}.
     *
     * @param request the raw command string, expected to contain a space followed by the event title
     * @return a confirmation message upon successful deletion
     * @throws EventCalendarException if the title is missing, empty, or if an error occurs during deletion
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
            service.deleteEvent(title);
            return "Event successfully deleted";
        } catch (EventCalendarException e) {
            throw e;
        } catch (Exception e) {
            throw new EventCalendarException("Error when deleting an event: ", e);
        }
    }
}
