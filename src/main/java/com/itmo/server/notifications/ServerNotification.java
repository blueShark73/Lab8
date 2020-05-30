package com.itmo.server.notifications;

import com.itmo.client.StudyGroupForUITable;
import javafx.collections.ObservableList;

public interface ServerNotification {
    void updateData(ObservableList<StudyGroupForUITable> list);
}
