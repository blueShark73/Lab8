package com.itmo.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * форма обучения группы
 */
@AllArgsConstructor
@Getter
public enum FormOfEducation implements Serializable {
    FULL_TIME_EDUCATION("FULL_TIME_EDUCATION"),
    DISTANCE_EDUCATION("DISTANCE_EDUCATION"),
    EVENING_CLASSES("EVENING_CLASSES");

    private String english;

    public static FormOfEducation getValueByNumber(String number, String messageIfNull) {
        try {
            int numb = Integer.parseInt(number);
            return Arrays.stream(FormOfEducation.values()).filter(f -> f.ordinal() + 1 == numb).findAny().orElse(null);
        } catch (NumberFormatException e) {
            System.out.println(messageIfNull);
            return null;
        }
    }

    public static FormOfEducation getValueByEnglish(String value){
        return Arrays.stream(values()).filter(formOfEducation -> formOfEducation.getEnglish().equals(value)).findAny().orElse(null);
    }

    public static ObservableList<String> getItems(){
        return FXCollections.observableArrayList(FormOfEducation.FULL_TIME_EDUCATION.getEnglish(), FormOfEducation.DISTANCE_EDUCATION.getEnglish(), FormOfEducation.EVENING_CLASSES.getEnglish());
    }
}
