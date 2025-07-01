package com.example.events_calendar.controller;

/**
 * Defines a contract for processing a textual request and returning
 * a textual response, potentially throwing an EventCalendarException.
 */
public interface Command {
    /**
     * Executes this command using the provided request string.
     *
     * @param request the raw command input
     * @return the textual result of command execution
     * @throws EventCalendarException if the request is invalid or processing fails
     */
    String execute(String request) throws EventCalendarException;
}
