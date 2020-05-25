package com.itmo.server;

import com.itmo.app.Application;
import com.itmo.exceptions.WithoutArgumentException;

import java.io.IOException;

import org.apache.logging.log4j.*;

/**
 * запуск сервера
 */
public class Main {
    public static final Logger log = LogManager.getLogger();

    public static void main(String[] args) throws Exception{
        try {
            if (args.length == 0) throw new WithoutArgumentException();
            int port = Integer.parseInt(args[0]);
            Application application = new Application();
            System.out.println("Серверное приложение запущено...");
            Server server = new Server();
            server.connect(port);
            log.info("Connection is established, listen port: " + port);
            server.run(application);
        } catch (WithoutArgumentException e) {
            System.out.println("Для запуска введите порт в виде аргумента командной строки!!!");
        } catch (NumberFormatException e) {
            System.out.println("Порт - это целое число!!!");
        } catch (IOException e) {
            System.out.println("Проблемы с подключением...");
        }
    }
}
