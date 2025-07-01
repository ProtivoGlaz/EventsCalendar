package com.example.events_calendar.controller;

import com.example.events_calendar.controller.impl.CommandProvider;

/**
 * Central controller for the Event Calendar application.
 * <p>
 * It parses an incoming request string to determine which command to execute,
 * delegates to the {@link CommandProvider} to fetch the appropriate {@link Command},
 * executes the command, and returns the resulting response or an error message.
 */
public class EventCalendarController {

    /**
     * Character that delimits the command name from its arguments in the request.
     */
    private static final char DELIM = ' ';

    /**
     * Provider that maps command names to their {@link com.example.events_calendar.controller.Command} implementations.
     */
    private final CommandProvider provider = new CommandProvider();

    /**
     * Processes the given request by extracting the command name, executing the corresponding
     * command, and returning its output. If any exception occurs during parsing or execution,
     * an error message is returned.
     *
     * @param request the raw input string, expected in the form "COMMAND_NAME [arguments]"
     * @return the result of the command execution, or "ERROR" if execution fails
     */
    public String doAction(String request) {
        String response;
        try {
            String commandName;
            Command executionCommand;

            int delimIndex = request.indexOf(DELIM);
            if (delimIndex == -1) {
                commandName = request;
            } else {
                commandName = request.substring(0, delimIndex);
            }

            executionCommand = provider.getCommand(commandName);
            response = executionCommand.execute(request);
        } catch (Exception e) {
            response = "ERROR";
        }
        return response;
    }
}
