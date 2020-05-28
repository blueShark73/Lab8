package com.itmo.client.controllers;

import com.itmo.client.UIMain;
import com.itmo.utils.FieldsValidator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {
    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private CheckBox authCheckBox;

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text authState;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventHandler<ActionEvent> handler = actionEvent -> {
            String userName = userNameTextField.getText();
            String pass = passwordField.getText();
            boolean generate = authCheckBox.isSelected();
            if(FieldsValidator.checkChars(userName, true, true) && ((pass.length()>5 && pass.length()<20)) || generate) {
                UIMain.USERNAME = userNameTextField.getText();
                UIMain.PASSWORD = passwordField.getText();
                UIMain.GENERATE_PASS_FOR_USER = authCheckBox.isSelected();
            }
            else {
                authState.setText("Username or password is not correct");
                authState.setFill(Color.RED);
            }
        };
        loginButton.setOnAction(handler);
        registerButton.setOnAction(handler);
    }
}
