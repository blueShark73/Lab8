package com.itmo.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * класс человека, используется для описания админа группы
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long height; //Поле может быть null, Значение поля должно быть больше 0
    private long weight; //Значение поля должно быть больше 0
    private String passportID; //Длина строки не должна быть больше 24, Строка не может быть пустой, Длина строки должна быть не меньше 7, Поле не может быть null
    private Location location; //Поле не может быть null

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", passportID='" + passportID + '\'' +
                ", location=" + location.toString() +
                '}';
    }
}
