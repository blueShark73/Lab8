package com.itmo.app.builder;

import com.itmo.app.*;
import com.itmo.client.UIMain;
import com.itmo.utils.FieldsValidator;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Scanner;

@NoArgsConstructor
public class StudyGroupCheckBuilder implements Builder {
    private StudyGroup studyGroup = new StudyGroup();

    @Override
    public void setId(long id) {
        studyGroup.setId(id);
    }

    public StudyGroupCheckBuilder(String name){
        studyGroup.setName(name);
    }

    public StudyGroup getResult(){
        return studyGroup;
    }

    @Override
    public void setCoordinates() {
        String nextLine;
        do {
            System.out.println("Введите координату Х учебной группы: ");
            nextLine = studyGroup.getScanner().nextLine();
        } while (!FieldsValidator.checkStringParseToLong(nextLine, "Ошибка ввода, координата - это целое число!!! Попробуйте снова."));
        Long x = Long.parseLong(nextLine);
        do {
            System.out.println("Введите координату Y учебной группы: ");
            nextLine = studyGroup.getScanner().nextLine();
        } while (!FieldsValidator.checkStringParseToLong(nextLine, "Ошибка ввода, координата - это целое число!!! Попробуйте снова.") || !FieldsValidator.checkUniquenessCoordinate(x, Long.parseLong(nextLine), UIMain.mainController.getStudyGroups()));
        long y = Long.parseLong(nextLine);
        studyGroup.setCoordinates(new Coordinates(x, y));
    }

    @Override
    public void setName() {
        String name;
        do {
            System.out.println("Введите название учебной группы(оно должно состоять из 2-19 знаков):");
            name = studyGroup.getScanner().nextLine();
        } while (!FieldsValidator.checkNumber((long) name.length(), 2, 19, "Неккоректное имя элемента!!! Попробуйте снова.", false));
        studyGroup.setName(name);
    }

    @Override
    public void setCreationDate() {
        studyGroup.setCreationDate(ZonedDateTime.now());
    }

    @Override
    public void setStudentsCount() {
        String nextLine;
        do {
            System.out.println("Введите кол-во студентов в группе: ");
            nextLine = studyGroup.getScanner().nextLine();
        } while ((!FieldsValidator.checkStringParseToLong(nextLine, "Ошибка ввода, кол-во студентов - это целое число!!!")
                || !FieldsValidator.checkNumber(Long.parseLong(nextLine), 0, 50, "Некорректное кол-во студентов, должно быть от 0 до 50!!! Попробуйте снова.", true))
                && !nextLine.equals(""));
        if (!nextLine.equals("")) studyGroup.setStudentsCount(Long.parseLong(nextLine));
        else {
            System.out.println("Значение поля воспринято как null");
            studyGroup.setStudentsCount(null);
        }
    }

    @Override
    public void setFormOfEducation() {
        String nextLine;
        FormOfEducation formOfEducation;
        do {
            System.out.println("Введите форму обучения(1 - Очная, 2 - Заочная, 3 - Вечерняя школа): ");
            nextLine = studyGroup.getScanner().nextLine();
            formOfEducation = FormOfEducation.getValueByNumber(nextLine, "Вводите корректный номер из предложенных вариантов!!!");
        } while (formOfEducation == null);
        studyGroup.setFormOfEducation(formOfEducation);
    }

    @Override
    public void setSemesterEnum() {
        String nextLine;
        Semester semesterEnum;
        do {
            System.out.println("Введите номер семестра(3/4/5/6/8): ");
            nextLine = studyGroup.getScanner().nextLine();
        } while (Semester.getValueByNumber(nextLine, "Вводите корректный номер семестра из предложенных вариантов!!!") == null && !nextLine.equals(""));
        if (nextLine.equals("")) {
            semesterEnum = null;
            System.out.println("Значение поля воспринято как null");
        } else semesterEnum = Semester.getValueByNumber(nextLine, "");
        studyGroup.setSemesterEnum(semesterEnum);
    }

    @Override
    public void setGroupAdmin() {
        Person groupAdmin = new Person();
        System.out.println("Пришло время выбрать админа!");
        String nextLine;
        do {
            System.out.println("Введите имя админа(оно должно состоять из 2-19 знаков): ");
            nextLine = studyGroup.getScanner().nextLine();
        } while (!FieldsValidator.checkNumber((long) nextLine.length(), 2, 19, "Неккоректное имя админа!!! Попробуйте снова.", false));
        groupAdmin.setName(nextLine);
        do {
            System.out.println("Введите рост(>0 обязательно): ");
            nextLine = studyGroup.getScanner().nextLine();
        } while ((!FieldsValidator.checkStringParseToLong(nextLine, "Ошибка ввода, рост - это число!!! Попробуйте снова.")
                || !FieldsValidator.checkNumber(Long.parseLong(nextLine), 0, 300, "Некорректный рост, студенты у нас обычно от 0 до 300 см!!! Попробуйте снова.", true))
                && !nextLine.equals(""));
        if (!nextLine.equals("")) groupAdmin.setHeight(Long.parseLong(nextLine));
        else System.out.println("Значение поля воспринято как null");
        do {
            System.out.println("Введите вес(>0): ");
            nextLine = studyGroup.getScanner().nextLine();
        } while (!FieldsValidator.checkStringParseToLong(nextLine, "Ошибка ввода, вес - это число!!! Попробуйте снова.")
                || !FieldsValidator.checkNumber(Long.parseLong(nextLine), 0, 300, "Некорректный вес, студенты у нас обычно от 0 до 300 кг!!! Попробуйте снова.", false));
        groupAdmin.setWeight(Long.parseLong(nextLine));
        do {
            System.out.println("Введите номер и серию паспорта(7-24 знака): ");
            nextLine = studyGroup.getScanner().nextLine();
        } while (!FieldsValidator.checkNumber((long) nextLine.length(), 7, 24, "Ошибка ввода!!! Попробуйте снова.", false));
        groupAdmin.setPassportID(nextLine);
        do {
            System.out.println("Введите местоположение админа. Сначала координату Х: ");
            nextLine = studyGroup.getScanner().nextLine();
        } while (!FieldsValidator.checkStringParseToDouble(nextLine, "Ошибка ввода, координата - это число!!!"));
        double xForLocation = Double.parseDouble(nextLine);
        do {
            System.out.println("Теперь координату Y: ");
            nextLine = studyGroup.getScanner().nextLine();
        }
        while (!FieldsValidator.checkStringParseToLong(nextLine, "Ошибка ввода, координата - это целое число!!!"));
        Long yForLocation = Long.parseLong(nextLine);
        String nameForLocation;
        do{
            System.out.println("Введите название этого места(пустая строка, если такого не имеется, от 0 до 19 символов): ");
            nameForLocation = studyGroup.getScanner().nextLine();
        } while (!FieldsValidator.checkNumber((long) nextLine.length(), 0, 19, "Неккоректное название места!!! Попробуйте снова.", false));
        Location location = new Location(xForLocation, yForLocation, nameForLocation);
        groupAdmin.setLocation(location);
        studyGroup.setGroupAdmin(groupAdmin);
    }

    @Override
    public void setScanner(Scanner scanner) {
        studyGroup.setScanner(scanner);
    }
}
