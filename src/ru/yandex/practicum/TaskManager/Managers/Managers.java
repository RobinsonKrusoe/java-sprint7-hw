package ru.yandex.practicum.TaskManager.Managers;

//Утилитарный  класс для работы с менеджерами задач
 public class Managers {
    private static InMemoryTasksManager inMemoryManager;
    private static FileBackedTasksManager fileBackedManager;
    private static HTTPTasksManager httpManager;

    //Получение Менеджера задач по умолчанию
    public static TaskManager getDefault(int servType){
        switch(servType){
            case 1:
                return inMemoryManager == null ? new InMemoryTasksManager() : inMemoryManager;
            case 2:
                return fileBackedManager == null ? new FileBackedTasksManager("save_tasks.txt") : fileBackedManager;
            case 3:
                return httpManager == null ? new HTTPTasksManager("save_tasks.txt") : httpManager;
        }

        return null;
    }
}
