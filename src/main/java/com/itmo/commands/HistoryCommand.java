package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.server.Session;
import lombok.NonNull;


/**
 * выводит последние 9 команд текущего пользователя
 */
public class HistoryCommand extends Command {
    @Override
    public String execute(Application application, @NonNull Session session) {
        successfullyExecute = true;
        return session.getHistory().getHistory();
    }

    @Override
    String getCommandInfo() {
        return "history : выведет последние 9 команд (без их аргументов)";
    }

    @Override
    public String toString() {
        return "history";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
