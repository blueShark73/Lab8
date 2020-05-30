package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.server.Session;

/**
 * команда выводит доступные команды
 */
public class HelpCommand extends Command {
    @Override
    public String execute(Application application, Session session) {
        successfullyExecute = true;
        return "Доступные команды: " + "\n" +
                new HelpCommand().getCommandInfo() + "\n" +
                new InfoCommand().getCommandInfo() + "\n" +
                new ShowCommand().getCommandInfo() + "\n" +
                new AddCommand().getCommandInfo() + "\n" +
                new UpdateCommand().getCommandInfo() + "\n" +
                new RemoveCommand().getCommandInfo() + "\n" +
                new ClearCommand().getCommandInfo() + "\n" +
                new ExecuteScriptCommand().getCommandInfo() + "\n" +
                new ExitCommand().getCommandInfo() + "\n" +
                new AddIfMaxCommand().getCommandInfo() + "\n" +
                new AddIfMinCommand().getCommandInfo() + "\n" +
                new HistoryCommand().getCommandInfo() + "\n" +
                new SumOfStudentsCountCommand().getCommandInfo() + "\n" +
                new FilterStartsWithNameCommand().getCommandInfo() + "\n" +
                new FilterGreaterThanStudentsCountCommand().getCommandInfo();
    }

    @Override
    String getCommandInfo() {
        return "help : выводит справку по доступным командам";
    }

    @Override
    public String toString() {
        return "help";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
