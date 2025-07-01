package com.example.events_calendar.main;

import com.example.events_calendar.dao.EventCalendarDao;
import com.example.events_calendar.dao.impl.JsonEventDaoImpl;
import com.example.events_calendar.model.Event;
import com.example.events_calendar.model.Organizer;
import com.example.events_calendar.model.Schedule;
import com.example.events_calendar.service.EventCalendarService;
import com.example.events_calendar.service.ServiceException;
import com.example.events_calendar.service.impl.EventCalendarServiceImpl;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class ServiceTests {
    private static final String TEST_FILE = "test_events_service.json";

    public static void main(String[] args) {
        try {
            // Удаляем тестовый файл перед запуском
            new File(TEST_FILE).delete();

            // Создаем DAO и сервис с тестовым файлом
            EventCalendarDao dao = new JsonEventDaoImpl(TEST_FILE);
            EventCalendarService service = new EventCalendarServiceImpl() {
                // Переопределяем DAO для теста
                {
                    java.lang.reflect.Field f = EventCalendarServiceImpl.class.getDeclaredField("eventDao");
                    f.setAccessible(true);
                    f.set(this, dao);
                }
            };

            // Тест: добавление события
            Event event = new Event(
                    "Service Event",
                    LocalDate.of(2024, 6, 10),
                    new Organizer("ServiceTester"),
                    new Schedule()
            );
            service.addEvent(event);
            System.out.println("AddEvent: OK");

            // Тест: получение события
            Event loaded = service.getEvent("Service Event");
            assert loaded != null && loaded.getTitle().equals("Service Event");
            System.out.println("GetEvent: OK");

            // Тест: обновление события
            Event updated = new Event(
                    "Service Event",
                    LocalDate.of(2024, 6, 11),
                    new Organizer("ServiceTester2"),
                    new Schedule()
            );
            service.updateEvent(updated);
            Event loaded2 = service.getEvent("Service Event");
            assert loaded2.getDate().equals(LocalDate.of(2024, 6, 11));
            System.out.println("UpdateEvent: OK");

            // Тест: список событий
            List<Event> all = service.getAllEvents();
            assert all.size() == 1;
            System.out.println("GetAllEvents: OK");

            // Тест: удаление события
            service.deleteEvent("Service Event");
            try {
                service.getEvent("Service Event");
                System.out.println("DeleteEvent: FAIL");
            } catch (ServiceException e) {
                System.out.println("DeleteEvent: OK");
            }

            // Очистка
            new File(TEST_FILE).delete();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test failed: " + e.getMessage());
        }
    }
}