package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.server.Session;
import com.itmo.utils.FieldsValidator;
import com.itmo.exceptions.InputFormatException;

import java.util.Scanner;

/**
 * команда для вывода тех учебных групп, значение поля studentsCount которых больше заданного
 */
public class FilterGreaterThanStudentsCountCommand extends Command implements CommandWithInit {
    private Long studentsCount;

    public void init(String argument, Scanner scanner) {
        if (!FieldsValidator.checkStringParseToLong(argument, "Кол-во студентов - это целое число!!!"))
            throw new InputFormatException();
        studentsCount = Long.parseLong(argument);
    }

    /**
     * сравнение и вывод нужных групп
     */
    @Override
    public String execute(Application application, Session session) {
        StringBuilder result = new StringBuilder();
        application.getSortedCollection().stream().filter(studyGroup -> studyGroup.getStudentsCount() > studentsCount)
                .forEach(studyGroup -> result.append(studyGroup.toString()).append("\n"));
        if (application.getCollection().isEmpty()) return "Коллекция пуста...";
        successfullyExecute = true;
        return result.length() > 0 ? result.deleteCharAt(result.length() - 1).toString() : "Таких элементов в коллекции нет";
    }

    @Override
    String getCommandInfo() {
        return "filter_greater_than_students_count studentsCount : выведет элементы, значение поля studentsCount которых больше заданного";
    }

    @Override
    public String toString() {
        return "filter_greater_than_students_count";
    }

    @Override
    public boolean withArgument() {
        return true;
    }
}
