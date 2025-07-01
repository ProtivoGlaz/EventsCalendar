package com.example.events_calendar.controller.impl;

import com.example.events_calendar.controller.Command;

/**
 * Command implementation that handles unknown or unsupported command requests.
 * <p>
 * When executed, it returns a message indicating the requested command is not recognized.
 */
public class NoSuchCommand implements Command {

    /**
     * Executes the unknown-command handler.
     *
     * @param request the raw command input; the first line is used to identify the attempted command
     * @return a message stating that the command is unknown
     */
    @Override
    public String execute(String request) {
        String cmd = request.split("\n")[0];
        return "Unknown command: " + cmd;
    }
}
