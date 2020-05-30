package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.server.Session;

/**
 * команда выводит информацию о коллекции
 */
public class InfoCommand extends Command {
    @Override
    public String execute(Application application, Session session) {
        StringBuilder result = new StringBuilder();
        result.append("Информация о коллекции:").append("\n");
        result.append("Дата загрузки коллекции в память: ").append(application.getInitializationDate()).append("\n");
        result.append("Кол-во элементов: ").append(application.getCollection().size()).append("\n");
        if (!application.getCollection().isEmpty()) {
            result.append("Тип данных, хранимых в коллекции: ").append(application.getCollection().iterator().next().getClass()).append("\n");
        }
        successfullyExecute = true;
        return result.deleteCharAt(result.length() - 1).toString();
    }

    @Override
    String getCommandInfo() {
        return "info : выводит информацию о коллекции";
    }

    @Override
    public String toString() {
        return "info";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
