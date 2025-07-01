package com.example.events_calendar.main;

import com.example.events_calendar.dao.DaoException;
import com.example.events_calendar.dao.EventCalendarDao;
import com.example.events_calendar.dao.impl.JsonEventDaoImpl;
import com.example.events_calendar.model.Event;
import com.example.events_calendar.model.Organizer;
import com.example.events_calendar.model.Schedule;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class DaoTests {
    private static final String TEST_FILE = "test_events.json";

    public static void main(String[] args) {
        try {
            // Удаляем тестовый файл перед запуском
            new File(TEST_FILE).delete();

            EventCalendarDao dao = new JsonEventDaoImpl(TEST_FILE);

            // Тест: создание события
            Event event = new Event(
                    "Test Event",
                    LocalDate.of(2024, 6, 1),
                    new Organizer("Tester"),
                    new Schedule()
            );
            dao.create(event);
            System.out.println("Create: OK");

            // Тест: чтение события
            Event loaded = dao.read("Test Event");
            assert loaded != null && loaded.getTitle().equals("Test Event");
            System.out.println("Read: OK");

            // Тест: обновление события
            Event updated = new Event(
                    "Test Event",
                    LocalDate.of(2024, 6, 2),
                    new Organizer("Tester2"),
                    new Schedule()
            );
            dao.update(updated);
            Event loaded2 = dao.read("Test Event");
            assert loaded2.getDate().equals(LocalDate.of(2024, 6, 2));
            System.out.println("Update: OK");

            // Тест: список событий
            List<Event> all = dao.findAll();
            assert all.size() == 1;
            System.out.println("FindAll: OK");

            // Тест: удаление события
            dao.delete("Test Event");
            try {
                dao.read("Test Event");
                System.out.println("Delete: FAIL");
            } catch (DaoException e) {
                System.out.println("Delete: OK");
            }

            // Очистка
            new File(TEST_FILE).delete();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test failed: " + e.getMessage());
        }
    }
}