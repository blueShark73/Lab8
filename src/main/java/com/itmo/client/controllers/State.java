package com.itmo.client.controllers;

import com.itmo.client.StudyGroupForUITable;
import com.itmo.utils.Listener;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class State {
    private ObservableList<StudyGroupForUITable> studyGroupForUITables;
    private String currentLang;
    private Listener listener;
}
