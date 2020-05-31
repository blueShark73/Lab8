package com.itmo.server;

import com.itmo.app.Application;
import com.itmo.server.notifications.NotificationManager;
import com.itmo.utils.HandlerThread;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.*;

/**
 * класс сервера
 */
public class Server {
    private DatagramChannel channel;
    private Application application;
    private byte[] buffer;
    private static final int DEFAULT_BUFFER_SIZE = 65536;
    private static final int READ_POOL_SIZE = 2;

    public Server(Application application) {
        this.application = application;
        buffer = new byte[DEFAULT_BUFFER_SIZE];
    }

    //модуль приёма соединений
    public void connect(int port) throws IOException {
        SocketAddress address = new InetSocketAddress(port);
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(address);
        application.setNotificationManager(new NotificationManager(application.getActiveSessions(), channel));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Сервер закончил работу...")));
    }

    //создание нового потока для обработки запроса при каждом новом запросе
    public void run(Application application) {
        try {
            Callable<SocketAddress> task = getTask();
            ExecutorService service = Executors.newFixedThreadPool(READ_POOL_SIZE);
            while (true) {
                Future<SocketAddress> result = service.submit(task);
                SocketAddress socketAddress = result.get();
                byte[] copyData = new byte[buffer.length];
                System.arraycopy(buffer, 0, copyData, 0, buffer.length);
                new HandlerThread(application, copyData, socketAddress, channel).start();
            }
        } catch (ClassCastException e) {
            System.out.println("Сервер ожидал команду, а получил что-то не то...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //получение запроса
    private Callable<SocketAddress> getTask() {
        return () -> {
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
            SocketAddress socketAddress;
            do {
                socketAddress = channel.receive(byteBuffer);
            } while (socketAddress == null);
            return socketAddress;
        };
    }
}