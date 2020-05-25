package com.itmo.utils;

import com.itmo.server.Response;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * поток для отправки клиенту ответа
 */
@AllArgsConstructor
public class SenderThread extends Thread {
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private Response response;

    @SneakyThrows
    @Override
    public void run() {
        try {
            byte[] data = new SerializationManager<Response>().writeObject(response);
            ByteBuffer byteBuffer = ByteBuffer.wrap(data);
            datagramChannel.send(byteBuffer, socketAddress);
        } catch (IOException e){
            e.printStackTrace();
            Response response = new Response("Коллеция какая-то большая почистите её, а потом уже просите");
            response.setUser(this.response.getUser());
            datagramChannel.send(ByteBuffer.wrap(new SerializationManager<Response>().writeObject(response)), socketAddress);
        }
    }
}
