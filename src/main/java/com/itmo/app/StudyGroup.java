package com.itmo.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Scanner;

/**
 * класс учебной группы, содержит в себе проверки пользовательского ввода и описание группы
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long studentsCount; //Значение поля должно быть больше 0, Поле может быть null
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Semester semesterEnum; //Поле может быть null
    private Person groupAdmin; //Поле не может быть null
    private String owner;
    private Scanner scanner;

    /**
     * для команды show
     */
    @Override
    public String toString() {
        return "StudyGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + creationDate +
                ", studentsCount=" + studentsCount +
                ", formOfEducation=" + formOfEducation +
                ", semesterEnum=" + semesterEnum +
                ", groupAdmin=" + groupAdmin.toString() +
                ", owner='" + owner +
                "'}";
    }

    /**
     * сравнение по имени группы
     *
     * @param studyGroup - элемент с которым идет сравнение
     */
    @Override
    public int compareTo(StudyGroup studyGroup) {
        if (studyGroup == null) return 1;
        if (studyGroup.getName() == null) return 1;
        if (getName() == null) return -1;
        return getName().compareTo(studyGroup.getName());
    }
}
