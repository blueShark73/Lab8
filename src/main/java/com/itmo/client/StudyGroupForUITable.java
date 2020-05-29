package com.itmo.client;

import com.itmo.app.Location;
import com.itmo.app.Person;
import com.itmo.app.StudyGroup;
import com.itmo.utils.DateTimeAdapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudyGroupForUITable {
    private Long id;
    private String name;
    private String creationDate;
    private Long x;
    private Long y;
    private Long studentsCount;
    private String formOfEducation;
    private String semester;
    private String adminName;
    private Long height;
    private Long weight;
    private String passportID;
    private Double locationX;
    private Long locationY;
    private String locationName;
    private String owner;

    public StudyGroupForUITable(StudyGroup studyGroup){
        Person person = studyGroup.getGroupAdmin();
        Location location = person.getLocation();
        id = studyGroup.getId();
        name = studyGroup.getName();
        x = studyGroup.getCoordinates().getX();
        y = studyGroup.getCoordinates().getY();
        creationDate = DateTimeAdapter.parseToString(studyGroup.getCreationDate());
        studentsCount = studyGroup.getStudentsCount();
        formOfEducation = studyGroup.getFormOfEducation().getEnglish();
        semester = studyGroup.getSemesterEnum().getEnglish();
        adminName = person.getName();
        height = person.getHeight();
        weight = person.getWeight();
        passportID = person.getPassportID();
        locationX = location.getX();
        locationY = location.getY();
        locationName = location.getName();
        owner = studyGroup.getOwner();
    }
}
