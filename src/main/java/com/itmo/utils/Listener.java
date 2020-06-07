package com.itmo.utils;

import com.itmo.client.UIMain;
import com.itmo.client.controllers.MainController;
import com.itmo.commands.AddListenerCommand;
import com.itmo.commands.Command;
import com.itmo.server.notifications.ServerNotification;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

@AllArgsConstructor
public class Listener extends Thread {
    private int port;
    private String host;
    private MainController mainController;
    private static final int SIZE = 65536;

    @Override
    public void run() {
        try {
            SocketAddress socketAddress = new InetSocketAddress(host, port);
            DatagramSocket datagramSocket = new DatagramSocket();
            SerializationManager<ServerNotification> serializationManager = new SerializationManager<>();
            datagramSocket.connect(socketAddress);
            Command command = new AddListenerCommand();
            command.setUser(UIMain.client.getUser());
            byte[] data = new SerializationManager<Command>().writeObject(command);
            DatagramPacket packet = new DatagramPacket(data, data.length, socketAddress);
            datagramSocket.send(packet);
            mainController.getPainter().run(mainController);
            while (true) {
                data = new byte[SIZE];
                packet = new DatagramPacket(data, data.length);
                datagramSocket.receive(packet);
                ServerNotification notification = serializationManager.readObject(packet.getData());
                mainController.getPainter().addNotification(notification);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
