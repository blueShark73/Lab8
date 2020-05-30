package com.itmo.server.notifications;

import com.itmo.client.StudyGroupForUITable;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class AddServerNotification implements ServerNotification, Serializable {
    private StudyGroupForUITable studyGroupForUITable;

    @Override
    public void updateData(ObservableList<StudyGroupForUITable> list) {
        list.add(studyGroupForUITable);
    }
}
