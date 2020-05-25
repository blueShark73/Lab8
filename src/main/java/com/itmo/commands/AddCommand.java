package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.app.builder.StudyGroupCheckBuilder;
import com.itmo.server.Session;
import com.itmo.utils.FieldsValidator;
import com.itmo.app.StudyGroup;
import com.itmo.exceptions.InputFormatException;
import lombok.NonNull;

import java.util.Scanner;

/**
 * команда для добавления элемента в коллекцию по имени
 */
public class AddCommand extends Command implements CommandWithInit{
    protected StudyGroup studyGroup;

    public void init(String argument, Scanner scanner){
        studyGroup = executeInitialization(argument, scanner);
    }

    /**
     * исполнение
     *
     * @param application - текущее приложение
     */
    @Override
    public String execute(Application application, @NonNull Session session) {
        studyGroup.setOwner(session.getUser());
        if(!application.getDataBaseManager().addGroup(studyGroup)) return "Элемент не добвлен. Проверьте корректность данных";
        application.getIdList().add(studyGroup.getId());
        application.getCollection().add(studyGroup);
        return "Элемент с именем "+studyGroup.getName()+" добавлен в коллекцию";
    }

    /**
     * инициализация, которая одинакова для трех команд
     */
    public StudyGroup executeInitialization(String argument, Scanner scanner) {
        if (!FieldsValidator.checkNumber((long) argument.length(), 2, 19, "Некорректное имя элемента, оно должно быть из 2-19 знаков!!!", false))
            throw new InputFormatException();
        StudyGroupCheckBuilder builder = new StudyGroupCheckBuilder(argument);
        builder.setScanner(scanner);
        builder.setCoordinates();
        builder.setCreationDate();
        builder.setStudentsCount();
        builder.setFormOfEducation();
        builder.setSemesterEnum();
        builder.setGroupAdmin();
        builder.setScanner(null);
        return builder.getResult();
    }

    @Override
    String getCommandInfo() {
        return "add element : добавит новый элемент в коллекцию";
    }

    @Override
    public String toString() {
        return "add";
    }

    @Override
    public boolean withArgument() {
        return true;
    }
}
