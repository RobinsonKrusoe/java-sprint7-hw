package ru.yandex.practicum.TaskManager.Managers;

import org.junit.jupiter.api.*;
import ru.yandex.practicum.TaskManager.Servers.KVServer;
import ru.yandex.practicum.TaskManager.Tasks.BaseTask.Task;
import ru.yandex.practicum.TaskManager.Tasks.Epic;
import ru.yandex.practicum.TaskManager.Tasks.SubTask;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HTTPTasksManagerTest{
    static KVServer server;
    HTTPTasksManager taskManager;
    Task task;
    Epic epic;
    SubTask subTask1;
    SubTask subTask2;
    @BeforeAll
    public static void beforeAll() throws IOException {
        server = new KVServer();
        server.start();
    }

    @AfterAll
    public static void afterAll() throws IOException {
        server.stop();
    }

    //Тестирование метода сохранения задач в базу
    @Test
    void save() {
        taskManager =  new HTTPTasksManager();
        assertNotNull(taskManager.getPrioritizedTasks(), "Список задач не пустой!");

        epic = new Epic(200,"Эпик 1", "Пустой эпик");
        taskManager.addTask(epic);

        epic = new Epic(300,"Эпик 2", "Эпик с подзадачами");
        taskManager.addTask(epic);

        subTask1 = new SubTask("Собрать коробки",
                "Коробки на чердаке",
                epic,
                LocalDateTime.now().plusMinutes(100),
                Duration.ofMinutes(30));
        taskManager.addTask(subTask1);

        subTask2 = new SubTask("Упаковать кошку",
                "Переноска за дверью",
                epic,
                LocalDateTime.now().plusMinutes(300),
                Duration.ofHours(1).plusMinutes(30));
        taskManager.addTask(subTask2);

        task = new Task(100,
                "Задача 1",
                "Задача для наполнения менеджера",
                LocalDateTime.now().plusMinutes(200),
                Duration.ofHours(1).plusMinutes(15));
        taskManager.addTask(task);

        assertEquals(3, taskManager.getPrioritizedTasks().size(),
                "Количество задач в отсортированном списке не верно!");

        //Обращения к задачам для формирования истории обращений
        taskManager.getTask(1);
        taskManager.getTask(100);
        taskManager.getTask(200);
        taskManager.getTask(300);

        assertEquals(4, taskManager.history().size(),
                "Количество задач в истории не верно!");

    }

    //Тестирование метода восстановления задач с сервера
    @Test
    void loadFromJson() {
        HTTPTasksManager taskManager =  new HTTPTasksManager("PrimaryManager");
        assertNotNull(taskManager.getPrioritizedTasks(), "Список задач не пустой!");

        //Формирование первичного менеджера задач
        epic = new Epic(200,"Эпик 1", "Пустой эпик");
        taskManager.addTask(epic);

        epic = new Epic(300,"Эпик 2", "Эпик с подзадачами");
        taskManager.addTask(epic);

        subTask1 = new SubTask("Собрать коробки",
                "Коробки на чердаке",
                epic,
                LocalDateTime.now().plusMinutes(100),
                Duration.ofMinutes(30));
        taskManager.addTask(subTask1);

        subTask2 = new SubTask("Упаковать кошку",
                "Переноска за дверью",
                epic,
                LocalDateTime.now().plusMinutes(300),
                Duration.ofHours(1).plusMinutes(30));
        taskManager.addTask(subTask2);

        task = new Task(100,
                "Задача 1",
                "Задача для наполнения менеджера",
                LocalDateTime.now().plusMinutes(200),
                Duration.ofHours(1).plusMinutes(15));
        taskManager.addTask(task);

        assertEquals(3, taskManager.getPrioritizedTasks().size(),
                "Количество задач в отсортированном списке не верно!");

        //Обращения к задачам для формирования истории обращений
        taskManager.getTask(1);
        taskManager.getTask(100);
        taskManager.getTask(200);
        taskManager.getTask(300);

        //Создание нового менеджера задач на основе образа с сервера
        HTTPTasksManager taskManager1 = HTTPTasksManager.loadFromJson("PrimaryManager", "CopyOfPrimaryManager");

        assertEquals(3, taskManager1.getPrioritizedTasks().size(),
                "Количество задач в списке приоритетов после восстаносления с сервера не верно!");

        assertEquals(4, taskManager1.history().size(),
                "Количество задач в истории обращений после восстаносления с сервера не верно!");

        assertEquals(4, taskManager1.history().size(),
                "Количество задач в истории обращений после восстаносления с сервера не верно!");
    }
}