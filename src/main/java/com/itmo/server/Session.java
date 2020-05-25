package com.itmo.server;

import com.itmo.app.CommandHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * класс сессии
 */
@AllArgsConstructor
@Setter
@Getter
public class Session implements Serializable {
    private String user;
    private String pass;
    private CommandHistory history;
}
