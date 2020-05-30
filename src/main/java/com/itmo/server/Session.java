package com.itmo.server;

import com.itmo.app.CommandHistory;
import com.itmo.client.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.net.SocketAddress;

/**
 * класс сессии
 */
@Setter
@Getter
public class Session implements Serializable {
    private User user;
    private CommandHistory history;
    private SocketAddress socketAddress;

    public Session(User user, CommandHistory history){
        this.user = user;
        this.history = history;
    }
}
