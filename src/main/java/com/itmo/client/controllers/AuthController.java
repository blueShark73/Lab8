package com.itmo.client.controllers;

import com.itmo.client.UIMain;
import com.itmo.client.User;
import com.itmo.commands.Command;
import com.itmo.commands.LoginCommand;
import com.itmo.commands.RegisterCommand;
import com.itmo.server.Response;
import com.itmo.utils.FieldsValidator;
import com.itmo.utils.SimplePasswordGenerator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
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

    private void sendAndHandler(Stage currentStage, MainController mainController, Command command) throws IOException, ClassNotFoundException{
        Response response = UIMain.client.sendCommandAndReceiveAnswer(command);
        if (response.isSuccessfullyExecute()) {
            currentStage.close();
            mainController.setValues();
            UIMain.USERNAME = userNameTextField.getText();
        }
    }

    public void setHandlers(Stage currentStage, MainController mainController) {
        EventHandler<ActionEvent> loginHandler = actionEvent -> {
            String userName = userNameTextField.getText();
            String pass = passwordField.getText();
            if (FieldsValidator.checkChars(userName, true, true) &&
                    FieldsValidator.checkNumber((long) pass.length(), 6, 19, "", false)) {
                LoginCommand command = new LoginCommand();
                command.setUserForLogin(new User(userName, pass));
                try {
                    sendAndHandler(currentStage, mainController, command);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    authState.setFill(Color.FUCHSIA);
                    authState.setText("Problems on server");
                    return;
                }
            }
            authState.setText("Username or password is not correct");
            authState.setFill(Color.RED);
        };

        EventHandler<ActionEvent> registerHandler = actionEvent -> {
            String userName = userNameTextField.getText();
            String pass = passwordField.getText();
            boolean generate = authCheckBox.isSelected();
            if (FieldsValidator.checkChars(userName, true, true) &&
                    (FieldsValidator.checkNumber((long) pass.length(), 6, 19, "", false) || generate)) {
                if(generate) {
                    SimplePasswordGenerator generator = new SimplePasswordGenerator(true, true, true, true);
                    pass = generator.generate(6, 19);
                }
                RegisterCommand command = new RegisterCommand();
                Color userColor = Color.color(Math.random(), Math.random(), Math.random());
                command.setUserForRegistration(new User(userName, pass, userColor));
                try {
                    sendAndHandler(currentStage, mainController, command);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    authState.setFill(Color.FUCHSIA);
                    authState.setText("Problems on server");
                    return;
                }
            }
            authState.setText("Username or password is not correct");
            authState.setFill(Color.RED);
        };
        loginButton.setOnAction(loginHandler);
        registerButton.setOnAction(registerHandler);
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIMain.authController = this;
    }
}
