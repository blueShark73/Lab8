package com.itmo.server.notifications;

import com.itmo.client.StudyGroupForUITable;
import com.itmo.client.controllers.MainController;
import javafx.collections.ObservableList;

public interface ServerNotification {
    void updateData(MainController mainController);
}
