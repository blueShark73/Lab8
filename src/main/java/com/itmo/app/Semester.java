package com.itmo.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * текущий семестр для учебной группы
 */
@Getter
@AllArgsConstructor
public enum Semester implements Serializable {
    THIRD("THIRD", 3),
    FOURTH("FOURTH", 4),
    FIFTH("FIFTH", 5),
    SIXTH("SIXTH", 6),
    EIGHTH("EIGHTH", 8);

    private String english;
    private int number;

    public static Semester getValueByNumber(String number, String messageIfNull) {
        try {
            int numb = Integer.parseInt(number);
            return Arrays.stream(Semester.values()).filter(f -> f.number == numb).findAny().orElse(null);
        } catch (NumberFormatException e) {
            System.out.println(messageIfNull);
            return null;
        }
    }

    public static Semester getValueByEnglish(String value){
        return Arrays.stream(values()).filter(semester -> semester.english.equals(value)).findAny().orElse(null);
    }

    public static ObservableList<String> getItems(){
        return FXCollections.observableArrayList(Semester.THIRD.getEnglish(), Semester.FOURTH.getEnglish(), Semester.FIFTH.getEnglish(), Semester.SIXTH.getEnglish(), Semester.EIGHTH.getEnglish());
    }

    public static int getNumberByEnglish(String english){
        return Objects.requireNonNull(Arrays.stream(values()).filter(semester -> semester.english.equals(english)).findAny().orElse(null)).number;
    }
}
