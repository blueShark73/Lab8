package com.itmo.client.controllers;

import com.itmo.app.*;
import com.itmo.client.StudyGroupForUITable;
import com.itmo.client.UIMain;
import com.itmo.client.User;
import com.itmo.utils.FieldsValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.Getter;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    @FXML
    private Button addElementButton;

    @FXML
    private ChoiceBox<String> formChoiceBox;

    @FXML
    private ChoiceBox<String> semesterChoiceBox;

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;

    @FXML
    private TextField studentsCountField;

    @FXML
    private TextField adminNameField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField passportIdField;

    @FXML
    private TextField locationXField;

    @FXML
    private TextField locationYField;

    @FXML
    private TextField locationNameField;

    @FXML
    private Label nameLabel;

    @FXML
    private Text stateText;

    @FXML
    @Getter
    private StudyGroup group = new StudyGroup();

    @FXML
    private void click(ActionEvent event){
        try{
            group.setName(nameLabel.getText());
            group.setCoordinates(new Coordinates(Long.parseLong(xField.getText()), Long.parseLong(yField.getText())));
            group.setStudentsCount(Long.parseLong(studentsCountField.getText()));
            group.setFormOfEducation(FormOfEducation.getValueByEnglish(formChoiceBox.getValue()));
            group.setSemesterEnum(Semester.getValueByEnglish(semesterChoiceBox.getValue()));
            Person person = new Person(adminNameField.getText(), Long.parseLong(heightField.getText()), Long.parseLong(weightField.getText()),
                    passportIdField.getText(), new Location(Double.parseDouble(locationXField.getText()), Long.parseLong(locationYField.getText()), locationNameField.getText()));
            group.setGroupAdmin(person);
            group.setCreationDate(ZonedDateTime.now());
            group.setId(-1);
            group.setOwner(new User());
        } catch (NumberFormatException e){
            stateText.setText("Parsing error, check values");
            stateText.setFill(Color.RED);
            return;
        }
        if(FieldsValidator.complexCheckFields(new StudyGroupForUITable(group))) {
            UIMain.mainController.getAddStage().close();
            UIMain.mainController.getNameTextField().setText("");
        }
        else {
            stateText.setText("Out of range");
            stateText.setFill(Color.RED);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        formChoiceBox.setItems(FormOfEducation.getItems());
        formChoiceBox.setValue(FormOfEducation.FULL_TIME_EDUCATION.getEnglish());

        semesterChoiceBox.setItems(Semester.getItems());
        semesterChoiceBox.setValue(Semester.THIRD.getEnglish());

        nameLabel.setText(UIMain.mainController.getNameTextField().getText());
    }
}
