package com.itmo.client;

import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * класс пользователя
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    private String name;
    private String pass;
    private Color color;

    public User(String name, String pass){
        this.name = name;
        this.pass = pass;
    }
}
