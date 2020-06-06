package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.app.CommandHistory;
import com.itmo.client.User;
import com.itmo.server.Session;
import com.itmo.utils.FieldsValidator;
import javafx.scene.paint.Color;
import lombok.Setter;

import java.io.Console;
import java.util.Date;
import java.util.Scanner;

/**
 * команда для авторизации пользователя
 */
@Setter
public class LoginCommand extends Command implements CommandWithInit {
    private User userForLogin;

    //авторизация, если такой пользователь зарегистрирован
    @Override
    public String execute(Application application, Session session) {
        if (application.getDataBaseManager().containsUser(userForLogin)) {
            Session sessionActual = new Session(userForLogin, new CommandHistory(CommandHistory.DEFAULT_HISTORY_SIZE));
            sessionActual.setLastActivityDate(new Date());
            Color userColor = application.getDataBaseManager().getUserColor(userForLogin);
            userForLogin.setColor(userColor.getRed(), userColor.getGreen(), userColor.getBlue());
            if(!application.addSession(userForLogin, sessionActual)) return "Пользователь с ником " + userForLogin.getName() + " уже авторизован.\nАвторизация с двух устройств запрещена!";
            user = userForLogin;
            successfullyExecute = true;
            return "Пользователь с ником " + userForLogin.getName() + " успешно авторизован\n" +
                    "Теперь вам доступны все команды, для их просмотра введите help";
        }
        user = null;
        return "Пароль или логин некорректен, авторизация отменена";
    }

    @Override
    public String getCommandInfo() {
        return "login name : авторизоваться по имени name";
    }

    @Override
    public String toString() {
        return "login";
    }

    @Override
    public boolean withArgument() {
        return true;
    }

    //просим пароль
    @Override
    public void init(String argument, Scanner scanner) {
        String pass;
        Console console = System.console();
        do {
            System.out.println("Введите пароль для авторизации(от 6 до 20 символов):");
            pass = new String(console.readPassword());
        } while (!FieldsValidator.checkNumber((long) pass.length(), 6, 19, "Пароль не удовлетворяет условиям. Попробуйте снова", false));
        userForLogin = new User(argument, pass);
    }
}
