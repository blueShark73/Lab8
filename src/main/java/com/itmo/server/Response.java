package com.itmo.server;

import com.itmo.client.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * класс того, что приходит клиенту в качестве ответа
 */
@AllArgsConstructor
@Setter
@Getter
public class Response implements Serializable {
    private String answer;
    private User user;
    private boolean successfullyExecute;

    public Response(String answer) {
        this.answer = answer;
    }
}
