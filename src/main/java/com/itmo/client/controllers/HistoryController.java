package com.itmo.client.controllers;

import com.itmo.client.UIMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    @FXML
    private Button okButton;

    @FXML
    private ListView<String> commandList;

    @FXML
    private void click(){
        UIMain.mainController.getHistoryStage().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commandList.setItems(UIMain.mainController.getCommandsForHistory());
    }
}
