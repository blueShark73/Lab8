package com.itmo.server.notifications;

import com.itmo.client.StudyGroupForUITable;
import com.itmo.client.controllers.MainController;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class AddServerNotification implements ServerNotification, Serializable {
    private StudyGroupForUITable studyGroupForUITable;

    @Override
    public void updateData(MainController mainController) {
        mainController.getPainter().drawWithAdding(studyGroupForUITable, mainController.getStudyGroups());
        mainController.addWithCheckingFormat(studyGroupForUITable);
    }
}
