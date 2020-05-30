package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.server.Session;

/**
 * команда выводит все элементы коллекции на консоль
 */
public class ShowCommand extends Command {
    @Override
    public String execute(Application application, Session session) {
        StringBuilder result = new StringBuilder();
        if (!application.getCollection().isEmpty()) {
            application.getSortedCollection().forEach(studyGroup -> result.append(studyGroup.toString()).append("\n"));
            successfullyExecute = true;
            return result.deleteCharAt(result.length()-1).toString();
        } return "Коллекция пуста!!!";
    }

    @Override
    String getCommandInfo() {
        return "show : выводит все элементы коллекции в строковом представлении";
    }

    @Override
    public String toString() {
        return "show";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
