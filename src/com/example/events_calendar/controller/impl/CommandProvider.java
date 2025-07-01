package com.example.events_calendar.controller.impl;

import com.example.events_calendar.controller.Command;
import com.example.events_calendar.controller.CommandName;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides the mapping between command names and their corresponding {@link Command} implementations.
 * <p>
 * When given a string key, it returns the appropriate {@link Command}, defaulting to the
 * {@link CommandName#WRONG_REQUEST} handler if the key is invalid or missing.
 */
public class CommandProvider {
    /**
     * Repository of available commands indexed by their {@link CommandName}.
     */
    private final Map<CommandName, Command> repo = new HashMap<>();

    /**
     * Initializes the command repository with all supported commands.
     * <p>
     * Adds handlers for:
     * <ul>
     *     <li>{@link CommandName#ADD_EVENT}</li>
     *     <li>{@link CommandName#GET_EVENT}</li>
     *     <li>{@link CommandName#UPDATE_EVENT}</li>
     *     <li>{@link CommandName#DELETE_EVENT}</li>
     *     <li>{@link CommandName#LIST_EVENT}</li>
     *     <li>{@link CommandName#WRONG_REQUEST}</li>
     * </ul>
     */
    public CommandProvider() {
        repo.put(CommandName.ADD_EVENT, new AddEventCommand());
        repo.put(CommandName.GET_EVENT, new GetEventCommand());
        repo.put(CommandName.UPDATE_EVENT, new UpdateEventCommand());
        repo.put(CommandName.DELETE_EVENT, new DeleteEventCommand());
        repo.put(CommandName.LIST_EVENT, new ListEventCommand());
        repo.put(CommandName.WRONG_REQUEST, new NoSuchCommand());
    }

    /**
     * Returns the {@link Command} associated with the given name.
     * <p>
     * This lookup is case-insensitive. If the provided name is null, empty,
     * or does not correspond to a known {@link CommandName}, the method
     * returns the handler for {@link CommandName#WRONG_REQUEST}.
     *
     * @param name the command identifier string
     * @return the matching {@link Command}, or the "wrong request" handler if not found
     */
    public Command getCommand(String name) {
        CommandName commandName;
        Command command;

        try {
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repo.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            command = repo.get(CommandName.WRONG_REQUEST);
        }

        return command;
    }
}
