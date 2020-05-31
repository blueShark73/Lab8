package com.itmo.app;


import com.itmo.client.Client;
import com.itmo.commands.*;
import com.itmo.exceptions.InputFormatException;
import com.itmo.exceptions.StackIsLimitedException;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * обработчик команд, ищет совпадение среди данных ему команд в HashMap
 */
public class Handler {
    private HashMap<String, Command> commands;
    private boolean exitCommand = false;
    private Client client;

    /**
     * связывает обработчик и приложение, которое будет исполнять обработанные команды
     */
    public Handler() {
        commands = new HashMap<>();
    }

    /**
     * добавляет новую команду в обработчик
     *
     * @param string  - ключ
     * @param command - значение
     */
    public void addCommand(String string, Command command) {
        commands.put(string, command);
    }

    /**
     * флаг выхода из консольного приложения
     */
    public void setExitCommand() {
        exitCommand = true;
    }

    public boolean removeCommand(String string, Command command){
        return commands.remove(string, command);
    }

    public void removeAllCommands(){
        commands = new HashMap<>();
    }

    /**
     * список команд по умолчанию для авторизованного пользователя
     */
    public void setDefaultPack(){
        removeAllCommands();
        addCommand(new AddCommand().toString(), new AddCommand());
        addCommand(new AddIfMaxCommand().toString(), new AddIfMaxCommand());
        addCommand(new AddIfMinCommand().toString(), new AddIfMinCommand());
        addCommand(new ClearCommand().toString(), new ClearCommand());
        addCommand(new ExecuteScriptCommand().toString(), new ExecuteScriptCommand());
        addCommand(new ExitCommand().toString(), new ExitCommand());
        addCommand(new FilterGreaterThanStudentsCountCommand().toString(), new FilterGreaterThanStudentsCountCommand());
        addCommand(new FilterStartsWithNameCommand().toString(), new FilterStartsWithNameCommand());
        addCommand(new HelpCommand().toString(), new HelpCommand());
        addCommand(new HistoryCommand().toString(), new HistoryCommand());
        addCommand(new InfoCommand().toString(), new InfoCommand());
        addCommand(new RemoveCommand().toString(), new RemoveCommand());
        addCommand(new ShowCommand().toString(), new ShowCommand());
        addCommand(new SumOfStudentsCountCommand().toString(), new SumOfStudentsCountCommand());
        addCommand(new UpdateCommand().toString(), new UpdateCommand());
    }

    /**
     * запускает обработчик
     *
     * @param scanner - сканер, с которого считываем команды
     */
    public void run(Scanner scanner) {
        try {
            //Runtime.getRuntime().addShutdownHook(new Thread(() -> client.sendCommandAndReceiveAnswer(new ExitCommand())));
            while (!exitCommand && scanner.hasNext()) {
                try {
                    //ищем в следующей строке команду, вдруг она не в начале строки
                    String[] nextLine = scanner.nextLine().split(" ");
                    int positionOfCommand = 0;
                    while (nextLine[positionOfCommand].equals("") || nextLine[positionOfCommand].equals("\n"))
                        positionOfCommand++;
                    String nextCommand = nextLine[positionOfCommand];
                    if(nextCommand.equals("\u0004")) throw new NoSuchElementException();
                    String argument = null;
                    if (nextLine.length > positionOfCommand + 1) argument = nextLine[positionOfCommand + 1];
                    Command command = commands.get(nextCommand);
                    if (command == null) throw new InputFormatException();
                    if ((!command.withArgument() && argument != null) || nextLine.length > positionOfCommand + 2)
                        throw new IndexOutOfBoundsException("Слишком много аргументов у команды " + command.toString() + " !!!");
                    if (command.withArgument() && argument == null)
                        throw new IndexOutOfBoundsException("Команды с аргументами должны вводиться с аргументами!!!");
                    if (command.withArgument()) {
                        CommandWithInit commandWithInit = (CommandWithInit) command;
                        commandWithInit.init(argument, scanner);
                        command = (Command) commandWithInit;
                    }
                    if(exitCommand) return;
                    if (command instanceof ExitCommand) setExitCommand();
                    //client.sendCommandAndReceiveAnswer(command);
                } catch (InputFormatException | StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Ошибка ввода!!! Такой команды нет.");
                    scanner.reset();//иначе при вводе большого кол-ва пустых строк будет выведено много предупреждений
                } catch (IndexOutOfBoundsException | StackIsLimitedException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (NoSuchElementException e){
            //client.sendCommandAndReceiveAnswer(new ExitCommand());
        }
    }

    /**
     * обязательный метод для привязки приложения к обрабочику
     *
     */
    public void setClient(Client client) {
        this.client = client;
    }
}
