package com.itmo.utils;

import com.itmo.app.*;
import com.itmo.client.StudyGroupForUITable;
import com.itmo.client.User;

import java.text.ParseException;
import java.time.ZonedDateTime;

public class StudyGroupAdapter {
    public static StudyGroup convert(StudyGroupForUITable studyGroupForUITable){
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName(studyGroupForUITable.getName());
        studyGroup.setCoordinates(new Coordinates(studyGroupForUITable.getX(), studyGroupForUITable.getY()));
        studyGroup.setStudentsCount(studyGroupForUITable.getStudentsCount());
        studyGroup.setFormOfEducation(FormOfEducation.getValueByEnglish(studyGroupForUITable.getFormOfEducation()));
        studyGroup.setSemesterEnum(Semester.getValueByEnglish(studyGroupForUITable.getSemester()));
        Person person = new Person(studyGroupForUITable.getAdminName(), studyGroupForUITable.getHeight(), studyGroupForUITable.getWeight(),
                studyGroupForUITable.getPassportID(), new Location(studyGroupForUITable.getLocationX(), studyGroupForUITable.getLocationY(),
                studyGroupForUITable.getLocationName()));
        studyGroup.setGroupAdmin(person);
        try {
            studyGroup.setCreationDate(DateTimeAdapter.parseToZonedDateTime(studyGroupForUITable.getCreationDate(), studyGroupForUITable.getFormat()));
        } catch (ParseException e){
            e.printStackTrace();
            studyGroup.setCreationDate(ZonedDateTime.now());
        }
        studyGroup.setId(studyGroupForUITable.getId());
        studyGroup.setOwner(new User(studyGroupForUITable.getOwner()));
        return studyGroup;
    }
}
