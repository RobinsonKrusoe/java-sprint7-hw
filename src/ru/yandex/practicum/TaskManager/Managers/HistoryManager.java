package ru.yandex.practicum.TaskManager.Managers;

import ru.yandex.practicum.TaskManager.Tasks.BaseTask.Task;

import java.util.List;

//Интерфейс для управления историей обращений к задачам
public interface HistoryManager {
    //Добавление нового просмотра задачи
    void add(Task task);

    //Удаление просмотра из истории
    void remove(int id);

    //Получение истории последних просмотров
    List<Task> getHistory();
}
