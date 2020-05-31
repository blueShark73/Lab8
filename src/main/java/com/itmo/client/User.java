package com.itmo.client;

import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private double red;
    private double green;
    private double blue;

    public User(String name, String pass){
        this.name = name;
        this.pass = pass;
    }

    public User(String name){
        this.name = name;
    }

    public void setColor(double red, double green, double blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color getColor(){
        return Color.color(red, green, blue);
    }
}
