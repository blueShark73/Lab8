package com.itmo.utils;

import com.itmo.client.controllers.MainController;
import com.itmo.commands.AddListenerCommand;
import com.itmo.commands.Command;
import com.itmo.server.Response;
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
    public void run(){
        try {
            SocketAddress socketAddress = new InetSocketAddress(host, port);
            DatagramSocket datagramSocket = new DatagramSocket();
            SerializationManager<ServerNotification> serializationManager = new SerializationManager<>();
            datagramSocket.connect(socketAddress);
            Command command = new AddListenerCommand();
            byte[] data = new SerializationManager<Command>().writeObject(command);
            DatagramPacket packet = new DatagramPacket(data, data.length, socketAddress);
            datagramSocket.send(packet);
            data = new byte[SIZE];
            packet = new DatagramPacket(data, data.length);
            datagramSocket.receive(packet);
            Response response = new SerializationManager<Response>().readObject(packet.getData());
            if(response.isSuccessfullyExecute()) {
                while (true) {
                    data = new byte[SIZE];
                    packet = new DatagramPacket(data, data.length);
                    datagramSocket.receive(packet);
                    ServerNotification notification = serializationManager.readObject(packet.getData());
                    notification.updateData(mainController.getStudyGroups());
                    //какой-то метод для перерисовки
                }
            }
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
