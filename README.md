# EventsCalendar

**EventsCalendar** — это консольное Java-приложение, предназначенное для управления событиями: создание, редактирование, удаление и просмотр. Проект следует многослойной архитектуре с чётким разделением на модель, DAO, сервис, контроллер и командную логику.

---

## 📁 Структура проекта
src/
  - com/example/events_calendar/
  - model/ # Сущности: Event, Organizer, Schedule
  - dao/ # Интерфейсы и реализация доступа к данным
  - service/ # Бизнес-логика
  - controller/ # Контроллер и шаблон Command
  - main/ # Главный класс и тесты

---
## 🧩 Основные компоненты

### Model
- `Event` — основная сущность события
- `Organizer` — организатор события
- `Schedule` — дата и время события

### DAO (Data Access Object)
- `EventCalendarDao` — интерфейс DAO
- `JsonEventDaoImpl` — реализация, сохраняющая события в файл `events.json`
- `JsonStorageHandler`, `EventStorageService` — вспомогательные классы

### Service
- `EventCalendarService` — бизнес-логика
- `EventCalendarServiceImpl` — реализация сервисного слоя

### Controller
- `EventCalendarController` — управляет взаимодействием пользователя с сервисом
- `Command`, `CommandName`, `CommandProvider` — шаблон Command
- Команды: `AddEventCommand`, `DeleteEventCommand`, `ListEventCommand` и др.

### Main
- `Main.java` — точка входа в приложение
- `ControllerTests`, `DaoTests`, `ServiceTests` — базовые юнит-тесты

## 🛠️ Используемые технологии

- Java Core (без фреймворков)
- Jackson (для сериализации JSON)
