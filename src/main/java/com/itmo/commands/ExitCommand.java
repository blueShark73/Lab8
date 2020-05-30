package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.server.Session;
import lombok.NonNull;

/**
 * команда закрытия приложения
 */
public class ExitCommand extends Command {
    /**
     * удаляем сессию пользователя
     */
    @Override
    public String execute(Application application, @NonNull Session session) {
        application.removeSession(user, session);
        successfullyExecute = true;
        return "Выход из приложения...";
    }

    @Override
    String getCommandInfo() {
        return "exit : завершит программу";
    }

    @Override
    public String toString() {
        return "exit";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
