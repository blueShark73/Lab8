package com.itmo.client.controllers;

import com.itmo.client.UIMain;
import com.itmo.commands.HelpCommand;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {
    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            UIMain.client.sendCommandAndReceiveAnswer(new HelpCommand());
            textArea.setText(resourceBundle.getString("helpText"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
