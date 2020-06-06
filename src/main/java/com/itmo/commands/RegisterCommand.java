package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.app.CommandHistory;
import com.itmo.client.User;
import com.itmo.server.Session;
import com.itmo.utils.FieldsValidator;
import com.itmo.utils.SimplePasswordGenerator;
import lombok.Getter;
import lombok.Setter;

import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

/**
 * команда для регистрации пользователей
 */
@Getter
@Setter
public class RegisterCommand extends Command implements CommandWithInit {
    private User userForRegistration;

    //регистрируем, если ник уникален
    @Override
    public String execute(Application application, Session session) {
        if (!application.getDataBaseManager().containsUserName(userForRegistration.getName())) {
            application.getDataBaseManager().addUser(userForRegistration);
            Session sessionActual = new Session(userForRegistration, new CommandHistory(CommandHistory.DEFAULT_HISTORY_SIZE));
            sessionActual.setLastActivityDate(new Date());
            application.addSession(userForRegistration, sessionActual);
            user = userForRegistration;
            successfullyExecute = true;
            return "Пользователь с ником " + userForRegistration.getName() + " успешно зарегистрирован\n" +
                    "Теперь вам доступны все команды, для их просмотра введите help";
        }
        user = null;
        return "Регистрация отменена, пользователь с ником " + userForRegistration.getName() + " уже зарегистрирован";
    }

    @Override
    public String getCommandInfo() {
        return "register name : зарегистрирует пользоваля на сервере под ником name";
    }

    @Override
    public String toString() {
        return "register";
    }

    @Override
    public boolean withArgument() {
        return true;
    }

    //просим пароль или генерируем его в файл самостоятельно
    @Override
    public void init(String argument, Scanner scanner) throws IOException {
        if (!FieldsValidator.checkChars(argument, true, true))
            throw new IndexOutOfBoundsException("Имя пользователя может состоять только из букв русского и английского алфавита!!!");
        String pass;
        Console console = System.console();
        do {
            System.out.println("Хотите, чтобы пароль сгенерировался автоматически?(y/n)");
            String answer = console.readLine().toLowerCase();
            if (answer.equals("y") || answer.equals("yes") || answer.equals("д")) {
                SimplePasswordGenerator generator = new SimplePasswordGenerator(true, true, true, true);
                pass = generator.generate(6, 19);
                File file = new File(argument);
                if (file.createNewFile()) {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(pass + '\n');
                    fileWriter.flush();
                    System.out.println("Ваш сгенерированный пароль находится в файле " + argument);
                } else System.out.println("Ваш сгенерированный пароль: " + pass);
                break;
            }
            System.out.println("Введите пароль для регистрации(от 6 до 20 символов):");
            pass = new String(console.readPassword());
        } while (!FieldsValidator.checkNumber((long) pass.length(), 6, 19, "Пароль не удовлетворяет условиям. Попробуйте снова", false));
        userForRegistration = new User(argument, pass);
    }
}
