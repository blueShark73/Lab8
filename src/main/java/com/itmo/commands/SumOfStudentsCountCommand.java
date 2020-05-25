package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.server.Session;

/**
 * команда выводит на консоль сумму значений поля studentsCount для всех элементов коллекции
 */
public class SumOfStudentsCountCommand extends Command {
    @Override
    public String execute(Application application, Session session) {
        return "Всего студентов во всех грппах в коллекции: " + application.getSumOfStudentsCount();
    }

    @Override
    String getCommandInfo() {
        return "sum_of_students_count : выведет сумму значений поля studentsCount для всех элементов коллекции";
    }

    @Override
    public String toString() {
        return "sum_of_students_count";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
