package com.itmo.client.controllers;

import com.itmo.client.StudyGroupForUITable;
import com.itmo.client.UIMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.itmo.app.Semester;
import com.itmo.app.FormOfEducation;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import com.itmo.utils.FieldsValidator;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {
    @FXML
    private Button updateButton;

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
    private TextField nameField;

    @FXML
    private Label idLabel;

    @FXML
    private Text stateText;

    private StudyGroupForUITable group;

    @FXML
    private void click(ActionEvent event){
        try {
            group.setName(nameField.getText());
            group.setX(Long.parseLong(xField.getText()));
            group.setY(Long.parseLong(yField.getText()));
            group.setStudentsCount(Long.parseLong(studentsCountField.getText()));
            group.setFormOfEducation(formChoiceBox.getValue());
            group.setSemester(semesterChoiceBox.getValue());
            group.setAdminName(group.getAdminName());
            group.setHeight(Long.parseLong(heightField.getText()));
            group.setWeight(Long.parseLong(weightField.getText()));
            group.setPassportID(passportIdField.getText());
            group.setLocationX(Double.parseDouble(locationXField.getText()));
            group.setLocationY(Long.parseLong(locationYField.getText()));
            group.setLocationName(locationNameField.getText());
        } catch (NumberFormatException e){
            stateText.setText("Parsing error, check values");
            stateText.setFill(Color.RED);
            return;
        }
        if (FieldsValidator.complexCheckFields(group)) {
            UIMain.mainController.getUpdateStage().close();
            UIMain.mainController.getStudyGroups().add(group);
        }
        else {
            stateText.setText("Out of range");
            stateText.setFill(Color.RED);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        formChoiceBox.setItems(FormOfEducation.getItems());

        semesterChoiceBox.setItems(Semester.getItems());

        group = UIMain.mainController.getSelectedStudyGroupForUITable();
        idLabel.setText(group.getId().toString());
        nameField.setText(group.getName());
        xField.setText(group.getX().toString());
        yField.setText(group.getY().toString());
        studentsCountField.setText(group.getStudentsCount().toString());
        formChoiceBox.setValue(group.getFormOfEducation());
        semesterChoiceBox.setValue(group.getSemester());
        adminNameField.setText(group.getAdminName());
        heightField.setText(group.getHeight().toString());
        weightField.setText(group.getWeight().toString());
        passportIdField.setText(group.getPassportID());
        locationNameField.setText(group.getLocationName());
        locationXField.setText(group.getLocationX().toString());
        locationYField.setText(group.getLocationY().toString());
    }
}
