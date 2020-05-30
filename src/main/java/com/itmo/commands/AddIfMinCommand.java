package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.server.Session;
import lombok.NonNull;

/**
 * команда добавляет элемент, если он меньше минимального элемента коллекции
 */
public class AddIfMinCommand extends AddCommand {
    @Override
    public String execute(Application application, @NonNull Session session) {
        studyGroup.setOwner(session.getUser());
        if ((application.getMinStudyGroup()==null || studyGroup.compareTo(application.getMinStudyGroup()) < 0) && application.getDataBaseManager().addGroup(studyGroup)) {
            application.getCollection().add(studyGroup);
            application.getIdList().add(studyGroup.getId());
            successfullyExecute = true;
            return "Элемент с именем "+studyGroup.getName()+" добавлен";
        }
        return "Элемент не добавлен";
    }

    @Override
    String getCommandInfo() {
        return "add_if_min element : добавит новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    @Override
    public String toString() {
        return "add_if_min";
    }
}

