package com.itmo.server.notifications;

import com.itmo.client.StudyGroupForUITable;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class RemoveServerNotification implements ServerNotification, Serializable {
    private Long id;

    @Override
    public void updateData(ObservableList<StudyGroupForUITable> list) {
        list.removeIf(e -> e.getId().equals(id));
    }
}
