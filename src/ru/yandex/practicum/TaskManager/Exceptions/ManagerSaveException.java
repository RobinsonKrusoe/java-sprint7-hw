package ru.yandex.practicum.TaskManager.Exceptions;

//непроверяемое исключение
public class ManagerSaveException extends RuntimeException{
    public ManagerSaveException (){

    }

    public ManagerSaveException(final String message) {
        super(message);

    }
}
