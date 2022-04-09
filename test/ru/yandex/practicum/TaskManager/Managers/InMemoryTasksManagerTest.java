package ru.yandex.practicum.TaskManager.Managers;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTasksManagerTest extends TaskManagerTest{

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTasksManager();    //Получение менеджера задач
    }
}