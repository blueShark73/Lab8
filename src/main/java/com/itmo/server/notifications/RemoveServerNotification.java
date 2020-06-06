package com.itmo.server.notifications;

import com.itmo.client.StudyGroupForUITable;
import com.itmo.client.controllers.MainController;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class RemoveServerNotification implements ServerNotification, Serializable {
    private Long id;

    @Override
    public void updateData(MainController mainController) {
        StudyGroupForUITable studyGroupForUITable = mainController.getStudyGroups().filtered(e -> e.getId().equals(id)).get(0);
        mainController.getStudyGroups().remove(studyGroupForUITable);
        mainController.getPainter().drawWithRemoving(studyGroupForUITable, mainController.getStudyGroups());
    }
}
