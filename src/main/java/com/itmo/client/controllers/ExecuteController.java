package com.itmo.client.controllers;

import com.itmo.client.UIMain;
import com.itmo.commands.ExecuteScriptCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Scanner;

public class ExecuteController implements Initializable {
    @FXML
    private ListView<String> selectedFiles;

    @FXML
    private Button oneFileButton;

    @FXML
    private Button multiFileButton;

    @FXML
    private Button executeButton;

    private ObservableList<String> filesPath = FXCollections.observableArrayList();

    private ArrayList<File> files = new ArrayList<>();

    private FileChooser fileChooser = new FileChooser();

    @FXML
    private void clickOneFileButton() {
        File file = fileChooser.showOpenDialog(UIMain.mainController.getExecuteStage());
        if (file != null) {
            files.clear();
            files.add(file);
            showPath();
        }
    }

    @FXML
    private void clickMultiFileButton(){
        List<File> list = fileChooser.showOpenMultipleDialog(UIMain.mainController.getExecuteStage());
        files.clear();
        files.addAll(list);
        showPath();
    }

    @FXML
    private void clickExecuteButton(){
        UIMain.mainController.getExecuteStage().close();
        files.forEach(f -> {
            ExecuteScriptCommand command = new ExecuteScriptCommand();
            command.init(f.getPath(), new Scanner(System.in));
            try {
                UIMain.client.sendCommandAndReceiveAnswer(command);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedFiles.setItems(filesPath);
    }

    private void showPath(){
        filesPath.clear();
        files.forEach(f -> filesPath.add(f.getPath()));
    }
}
