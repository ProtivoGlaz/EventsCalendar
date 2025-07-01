package com.example.events_calendar.dao.impl;

import com.example.events_calendar.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides JSON-based persistence for Event objects, storing them in a file
 * as a map from event titles to Event instances.
 *
 * <p>Uses Jackson's ObjectMapper configured with JavaTimeModule for
 * proper serialization of Java 8 date/time types and indented output.</p>
 */
public class JsonStorageHandler {

    /** Jackson mapper configured for Java 8 date/time and pretty printing. */
    private final ObjectMapper mapper;

    /** File used to persist the JSON data. */
    private final File file;

    /**
     * Constructs a handler that reads from and writes to the specified file path.
     * <p>
     * If the file does not exist, it is created and initialized with an empty map.
     *
     * @param filePath the file path where event data will be stored
     * @throws IOException if the file cannot be created or initialized
     */
    public JsonStorageHandler(String filePath) throws IOException {
        this.file = new File(filePath);
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        initializeFile();
    }

    /**
     * Ensures the storage file exists. If it does not, writes an empty map to it.
     *
     * @throws IOException if an error occurs during file creation or writing
     */
    private void initializeFile() throws IOException {
        if (!file.exists()) {
            write(new HashMap<>());
        }
    }

    /**
     * Reads and deserializes the contents of the JSON file into a map of events.
     * <p>
     * If the file does not exist at call time, returns an empty map.
     *
     * @return a map where keys are event titles and values are Event objects
     * @throws IOException if an error occurs during file reading or JSON parsing
     */
    public Map<String, Event> read() throws IOException {
        if (!file.exists()) return new HashMap<>();
        return mapper.readValue(file,
                mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Event.class));
    }

    /**
     * Serializes the given map of events to the storage file in JSON format.
     *
     * @param data the map from event titles to Event objects to write
     * @throws IOException if an error occurs during file writing or JSON serialization
     */
    public void write(Map<String, Event> data) throws IOException {
        mapper.writeValue(file, data);
    }
}
