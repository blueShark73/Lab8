package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.server.Session;
import lombok.Setter;

import java.net.SocketAddress;

@Setter
public class AddListenerCommand extends Command {
    private SocketAddress socketAddress;

    @Override
    public String execute(Application application, Session session) {
        if(session!=null) {
            session.setSocketAddress(socketAddress);
            successfullyExecute = true;
        }
        return "";
    }

    @Override
    String getCommandInfo() {
        return null;
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
