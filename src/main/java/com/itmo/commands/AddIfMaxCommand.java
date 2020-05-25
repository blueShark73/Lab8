package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.server.Session;
import lombok.NonNull;

/**
 * команда добавляет элемент, если он больше максимального элемента коллекции
 */
public class AddIfMaxCommand extends AddCommand {
    @Override
    public String execute(Application application, @NonNull Session session) {
        studyGroup.setOwner(session.getUser());
        if (studyGroup.compareTo(application.getMaxStudyGroup()) > 0 && application.getDataBaseManager().addGroup(studyGroup)) {
            application.getCollection().add(studyGroup);
            application.getIdList().add(studyGroup.getId());
            return "Элемент с именем "+studyGroup.getName()+" добавлен";
        }
        return "Элемент не добавлен";
    }

    @Override
    String getCommandInfo() {
        return "add_if_max element : добавит новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public String toString() {
        return "add_if_max";
    }
}
