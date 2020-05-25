package com.itmo.utils;

import com.itmo.app.Application;
import com.itmo.commands.Command;
import com.itmo.commands.ExitCommand;
import com.itmo.server.Response;
import com.itmo.server.Session;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

/**
 * поток для обработки запроса от клиента
 */
@AllArgsConstructor
public class HandlerThread extends Thread {
    private Application application;
    private byte[] data;
    private SocketAddress socketAddress;
    private DatagramChannel datagramChannel;
    private static final Logger log = LogManager.getLogger();

    @Override
    public void run() {
        try {
            Command command = new SerializationManager<Command>().readObject(data);
            log.info("Server receive command " + command.toString());
            Session session = application.getSession(command.getUser());
            String result;
            try {
                result = command.execute(application, session);
                if (session != null) session.getHistory().add(command);
            } catch (NullPointerException e) {
                e.printStackTrace();
                result = "Ошибка на сервере. Команда не выполнена" +
                        "\nАктивная сессия не найлена, видимо сервер отключился на некоторое время, перезапустите клиента";
            }
            Response response = new Response(result, command.getUser());
            log.info("Command " + command.toString() + " is completed, send an answer to the client");
            new SenderThread(datagramChannel, socketAddress, response).start();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
