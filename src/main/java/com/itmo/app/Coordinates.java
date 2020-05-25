package com.itmo.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * класс координат учебной группы
 */
@AllArgsConstructor
@Getter
@Setter
public class Coordinates implements Serializable {
    private Long x; //Поле не может быть null
    private long y;

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

