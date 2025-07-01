package com.example.events_calendar.main;

import com.example.events_calendar.controller.EventCalendarController;
import com.example.events_calendar.model.Event;
import com.example.events_calendar.model.Organizer;
import com.example.events_calendar.model.Schedule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class Main {
    private static final String TEST_FILE = "events.json"; // основной файл, который использует DaoProvider

    public static void main(String[] args) throws Exception {
        // Для чистоты эксперимента можно удалить файл перед запуском
        // deleteFile();

        EventCalendarController controller = new EventCalendarController();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // 1. Добавление события
        addEvent(controller, mapper);

        // 2. Получение события
        getEvent(controller);

        // 3. Обновление события
        updateEvent(controller, mapper);

        // 4. Получение списка событий
        listEvents(controller);

        // 5. Удаление события
        // deleteEvent(controller);

        // 6. Попытка получить несуществующее событие
        // getEvent(controller);

        // 7. Просмотр содержимого файла
        printFileContent();

        // 8. Очистка (удаление файла)
        // deleteFile();
    }

    private static void addEvent(EventCalendarController controller, ObjectMapper mapper) throws Exception {
        Schedule schedule = new Schedule();
        schedule.addActivity("09:00 Registration");
        schedule.addActivity("10:00 Opening");
        Event event = new Event("DemoEvent", LocalDate.of(2024, 7, 10), new Organizer("Alice"), schedule);
        String eventJson = mapper.writeValueAsString(event);

        System.out.println("=== Добавление события ===");
        System.out.println(controller.doAction("ADD_EVENT " + eventJson));
    }

    private static void getEvent(EventCalendarController controller) {
        System.out.println("\n=== Получение события ===");
        System.out.println(controller.doAction("GET_EVENT DemoEvent"));
    }

    private static void updateEvent(EventCalendarController controller, ObjectMapper mapper) throws Exception {
        Schedule schedule = new Schedule();
        schedule.addActivity("09:00 Registration");
        schedule.addActivity("10:00 Keynote");
        Event updated = new Event("DemoEvent", LocalDate.of(2024, 7, 11), new Organizer("Bob"), schedule);
        String updatedJson = mapper.writeValueAsString(updated);

        System.out.println("\n=== Обновление события ===");
        System.out.println(controller.doAction("UPDATE_EVENT " + updatedJson));
    }

    private static void listEvents(EventCalendarController controller) {
        System.out.println("\n=== Список событий ===");
        System.out.println(controller.doAction("LIST_EVENT"));
    }

    private static void deleteEvent(EventCalendarController controller) {
        System.out.println("\n=== Удаление события ===");
        System.out.println(controller.doAction("DELETE_EVENT DemoEvent"));
    }

    private static void printFileContent() throws Exception {
        System.out.println("\n=== Содержимое файла " + TEST_FILE + " ===");
        if (Files.exists(Paths.get(TEST_FILE))) {
            String content = Files.readString(Paths.get(TEST_FILE));
            System.out.println(content);
        } else {
            System.out.println("Файл не найден.");
        }
    }

    private static void deleteFile() {
        File file = new File(TEST_FILE);
        if (file.exists() && file.delete()) {
            System.out.println("Файл " + TEST_FILE + " удалён.");
        }
    }
}