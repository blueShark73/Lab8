package com.itmo.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * класс локации, указывает, гле находится админ
 */
@AllArgsConstructor
@Setter
@Getter
public class Location implements Serializable {
    private double x;
    private Long y; //Поле не может быть null
    private String name; //Строка не может быть пустой, Поле может быть null

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}
