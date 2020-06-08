package com.itmo.client.controllers;

import com.itmo.app.*;
import com.itmo.commands.UpdateCommand;
import com.itmo.client.StudyGroupForUITable;
import com.itmo.client.UIMain;
import com.itmo.client.User;
import com.itmo.utils.DateTimeAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import com.itmo.utils.FieldsValidator;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.ZonedDateTime;
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

    private StudyGroup group = new StudyGroup();

    private StudyGroupForUITable selectedStudyGroup;

    @FXML
    private void click(ActionEvent event) {
        try {
            group.setName(nameField.getText());
            group.setCoordinates(new Coordinates(Long.parseLong(xField.getText()), Long.parseLong(yField.getText())));
            group.setStudentsCount(Long.parseLong(studentsCountField.getText()));
            group.setFormOfEducation(FormOfEducation.getValueByEnglish(formChoiceBox.getValue()));
            group.setSemesterEnum(Semester.getValueByEnglish(semesterChoiceBox.getValue()));
            Person person = new Person(adminNameField.getText(), Long.parseLong(heightField.getText()), Long.parseLong(weightField.getText()),
                    passportIdField.getText(), new Location(Double.parseDouble(locationXField.getText()), Long.parseLong(locationYField.getText()), locationNameField.getText()));
            group.setGroupAdmin(person);
            group.setCreationDate(DateTimeAdapter.parseToZonedDateTime(selectedStudyGroup.getCreationDate()));
            group.setId(Long.parseLong(idLabel.getText()));
            group.setOwner(new User(selectedStudyGroup.getOwner()));
        } catch (NumberFormatException | ParseException e) {
            stateText.setText("Parsing error, check values");
            stateText.setFill(Color.RED);
            return;
        }
        StudyGroupForUITable studyGroupForUITable = new StudyGroupForUITable(group, DateTimeAdapter.defaultDateFormat);
        if (FieldsValidator.complexCheckFields(studyGroupForUITable) && FieldsValidator.checkUniquenessCoordinate(studyGroupForUITable.getX(), studyGroupForUITable.getY(), UIMain.mainController.getStudyGroups())) {
            UpdateCommand command = new UpdateCommand();
            command.setStudyGroup(group);
            try {
                if (UIMain.client.sendCommandAndReceiveAnswer(command).isSuccessfullyExecute()) {
                    UIMain.mainController.getUpdateStage().close();
                    UIMain.mainController.getStateText().setFill(Color.GREEN);
                    UIMain.mainController.getStateText().setText("Element updated");
                    return;
                }
                stateText.setText("Element is not updated");
                stateText.setFill(Color.YELLOW);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            stateText.setText("Out of range");
            stateText.setFill(Color.RED);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        formChoiceBox.setItems(FormOfEducation.getItems());

        semesterChoiceBox.setItems(Semester.getItems());

        selectedStudyGroup = UIMain.mainController.getSelectedStudyGroupForUITable();
        idLabel.setText(selectedStudyGroup.getId().toString());
        nameField.setText(selectedStudyGroup.getName());
        xField.setText(selectedStudyGroup.getX().toString());
        yField.setText(selectedStudyGroup.getY().toString());
        studentsCountField.setText(selectedStudyGroup.getStudentsCount().toString());
        formChoiceBox.setValue(selectedStudyGroup.getFormOfEducation());
        semesterChoiceBox.setValue(selectedStudyGroup.getSemester());
        adminNameField.setText(selectedStudyGroup.getAdminName());
        heightField.setText(selectedStudyGroup.getHeight().toString());
        weightField.setText(selectedStudyGroup.getWeight().toString());
        passportIdField.setText(selectedStudyGroup.getPassportID());
        locationNameField.setText(selectedStudyGroup.getLocationName());
        locationXField.setText(selectedStudyGroup.getLocationX().toString());
        locationYField.setText(selectedStudyGroup.getLocationY().toString());
    }
}
