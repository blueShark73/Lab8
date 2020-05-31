package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.server.Session;
import lombok.NonNull;

import java.io.IOException;

/**
 * команда очищает коллекцию от элементов, принадлежащих текущему пользователю
 */
public class ClearCommand extends Command {
    @Override
    public String execute(Application application, @NonNull Session session) {
        application.getDataBaseManager().removeAll(session.getUser().getName());
        application.getCollection().forEach(studyGroup -> {
            if (studyGroup.getOwner().equals(session.getUser())) {
                application.getIdList().remove(studyGroup.getId());
                application.getCollection().remove(studyGroup);
                try {
                    application.getNotificationManager().removeElementNotification(studyGroup.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        successfullyExecute = true;
        return "Все элементы принадлежашие пользователю " + session.getUser() + " удалены.";
    }

    @Override
    String getCommandInfo() {
        return "clear : удалит те элементы коллекции, которые принадлежат вам";
    }

    @Override
    public String toString() {
        return "clear";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
