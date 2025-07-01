package com.example.events_calendar.main;

import com.example.events_calendar.controller.EventCalendarController;
import com.example.events_calendar.model.Event;
import com.example.events_calendar.model.Organizer;
import com.example.events_calendar.model.Schedule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.time.LocalDate;

public class ControllerTests {
    private static final String TEST_FILE = "test_events_controller.json";

    public static void main(String[] args) {
        try {
            // Удаляем тестовый файл перед запуском
            new File(TEST_FILE).delete();

            // Подготовка данных
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            Schedule schedule = new Schedule();
            schedule.addActivity("10:00 Start");
            Event event = new Event("CtrlTest", LocalDate.of(2024, 7, 1), new Organizer("CtrlUser"), schedule);
            String eventJson = mapper.writeValueAsString(event);

            // Используем контроллер (он по умолчанию работает с events.json, но для теста можно временно заменить DaoProvider, если нужно)
            EventCalendarController controller = new EventCalendarController();

            // Тест: добавление события
            String addResp = controller.doAction("ADD_EVENT " + eventJson);
            System.out.println("AddEvent: " + (addResp.contains("успешно") ? "OK" : "FAIL"));

            // Тест: получение события
            String getResp = controller.doAction("GET_EVENT CtrlTest");
            System.out.println("GetEvent: " + (getResp.contains("CtrlTest") ? "OK" : "FAIL"));

            // Тест: обновление события
            Event updated = new Event("CtrlTest", LocalDate.of(2024, 7, 2), new Organizer("CtrlUser2"), schedule);
            String updatedJson = mapper.writeValueAsString(updated);
            String updResp = controller.doAction("UPDATE_EVENT " + updatedJson);
            System.out.println("UpdateEvent: " + (updResp.contains("успешно") ? "OK" : "FAIL"));

            // Тест: список событий
            String listResp = controller.doAction("LIST_EVENT");
            System.out.println("ListEvent: " + (listResp.contains("CtrlTest") ? "OK" : "FAIL"));

            // Тест: удаление события
            String delResp = controller.doAction("DELETE_EVENT CtrlTest");
            System.out.println("DeleteEvent: " + (delResp.contains("успешно") ? "OK" : "FAIL"));

            // Тест: получение несуществующего события
            String getFail = controller.doAction("GET_EVENT CtrlTest");
            System.out.println("GetEvent (not found): " + (getFail.startsWith("ERROR") ? "OK" : "FAIL"));

            // Тест: неизвестная команда
            String wrongCmd = controller.doAction("UNKNOWN_CMD");
            System.out.println("WrongCommand: " + (wrongCmd.contains("Unknown command") ? "OK" : "FAIL"));

            // Очистка
            new File(TEST_FILE).delete();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test failed: " + e.getMessage());
        }
    }
}