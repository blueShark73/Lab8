package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.server.Session;

import java.util.Scanner;

/**
 * команда выведет элементы, значение поля name которых начинается с заданной подстроки
 */
public class FilterStartsWithNameCommand extends Command implements CommandWithInit {
    private String argument;

    public void init(String argument, Scanner scanner) {
        this.argument = argument;
    }

    /**
     * поиск и вывод на консоль
     */
    @Override
    public String execute(Application application, Session session) {
        StringBuilder result = new StringBuilder();
        if (application.getCollection().isEmpty()) return "Коллекция пуста...";
        application.getSortedCollection().stream().filter(studyGroup -> new StringBuffer(studyGroup.getName()).indexOf(argument) == 0)
                .forEach(studyGroup -> result.append(studyGroup.toString()).append("\n"));
        return result.length() > 0 ? result.deleteCharAt(result.length() - 1).toString() : "Таких элементов в коллекции нет";
    }

    @Override
    String getCommandInfo() {
        return "filter_starts_with_name name : выведет элементы, значение поля name которых начинается с заданной подстроки";
    }

    @Override
    public String toString() {
        return "filter_starts_with_name";
    }

    @Override
    public boolean withArgument() {
        return true;
    }
}
