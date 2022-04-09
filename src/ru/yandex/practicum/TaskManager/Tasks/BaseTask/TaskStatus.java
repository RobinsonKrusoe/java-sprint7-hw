package ru.yandex.practicum.TaskManager.Tasks.BaseTask;

//Перечисление для статусов задач
public enum TaskStatus {
    NEW,            //задача только создана, но к её выполнению ещё не приступили.
    IN_PROGRESS,    //над задачей ведётся работа.
    DONE            //задача выполнена.
}
