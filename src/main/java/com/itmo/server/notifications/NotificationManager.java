package com.itmo.server.notifications;

import com.itmo.client.StudyGroupForUITable;
import com.itmo.client.User;
import com.itmo.server.Session;
import lombok.AllArgsConstructor;
import com.itmo.utils.SerializationManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
public class NotificationManager {
    private ConcurrentHashMap<User, Session> observers;
    private DatagramChannel datagramChannel;
    private static final SerializationManager<ServerNotification> serializationManager = new SerializationManager<>();
    private static byte[] data;

    public void addElementNotification(StudyGroupForUITable studyGroupForUITable) throws IOException {
        data = serializationManager.writeObject(new AddServerNotification(studyGroupForUITable));
        sendNotification();
    }

    public void removeElementNotification(Long id) throws IOException {
        data = serializationManager.writeObject(new RemoveServerNotification(id));
        sendNotification();
    }

    public void updateElementNotification(StudyGroupForUITable studyGroupForUITable) throws IOException {
        data = serializationManager.writeObject(new RemoveServerNotification(studyGroupForUITable.getId()));
        sendNotification();
        data = serializationManager.writeObject(new AddServerNotification(studyGroupForUITable));
        sendNotification();
    }

    private void sendNotification(){
        observers.forEach((user, session) -> {
            if (session.getSocketAddress() != null) {
                try {
                    ByteBuffer byteBuffer = ByteBuffer.wrap(data);
                    System.out.println("Send notification, address: "+session.getSocketAddress());
                    datagramChannel.send(byteBuffer, session.getSocketAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

