package com.itmo.client.controllers;

import com.itmo.app.*;
import com.itmo.client.StudyGroupForUITable;
import com.itmo.client.UIMain;
import com.itmo.utils.FieldsValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainController implements Initializable {
    @FXML
    private TableView<StudyGroupForUITable> tableView;

    @FXML
    private TableColumn<StudyGroupForUITable, Long> idColumn;

    @FXML
    private TableColumn<StudyGroupForUITable, String> nameColumn;

    @FXML
    private TableColumn<StudyGroupForUITable, String> creationDateColumn;

    @FXML
    private TableColumn<StudyGroupForUITable, Long> studentsCountColumn;

    @FXML
    private TableColumn<StudyGroupForUITable, String> formOfEducationColumn;

    @FXML
    private TableColumn<StudyGroupForUITable, String> semesterColumn;

    @FXML
    private TableColumn<StudyGroupForUITable, String> adminNameColumn;

    @FXML
    private TableColumn<StudyGroupForUITable, Long> heightColumn;

    @FXML
    private TableColumn<StudyGroupForUITable, Long> weightColumn;

    @FXML
    private TableColumn<StudyGroupForUITable, String> passportIdColumn;

    @FXML
    private TableColumn<StudyGroupForUITable, String> locationNameColumn;

    @FXML
    private TableColumn<StudyGroupForUITable, String> ownerColumn;

    @FXML
    private Rectangle userColorRectangle;

    @FXML
    private ChoiceBox<String> fieldChoiceBox;

    @FXML
    private Label currentUserLabel;

    @FXML
    private Button addButton;

    @FXML
    private Button addIfMinButton;

    @FXML
    private Button addIfMaxButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button findButton;

    @FXML
    private Button executeButton;

    @FXML
    private Button sumButton;

    @FXML
    private TextField filteredValue;

    @FXML
    private Text stateText;

    @FXML
    private Canvas canvas;

    @Getter
    @FXML
    private TextField nameTextField;

    @Getter
    private ObservableList<StudyGroupForUITable> studyGroups;

    @Getter
    private StudyGroupForUITable selectedStudyGroupForUITable;

    @Getter
    @Setter
    private boolean minButton;

    @Setter
    @Getter
    private boolean maxButton;

    @Getter
    private Stage addStage;

    @Getter
    private Stage updateStage;

    private Color getRandomColor() {
        return Color.color(Math.random(), Math.random(), Math.random());
    }

    public void setValues() {
        Color userColor = Color.color(Math.random(), Math.random(), Math.random());
        userColorRectangle.setFill(userColor);

        currentUserLabel.setText(currentUserLabel.getText() + UIMain.USERNAME);

        UIMain.drawAxis(canvas);
        for (StudyGroupForUITable studyGroupForUITable : studyGroups) {
            UIMain.drawElement(studyGroupForUITable.getX().intValue(), studyGroupForUITable.getY().intValue(), studyGroupForUITable.getStudentsCount().intValue(), getRandomColor(), canvas);
        }
    }

    private void clickAddButtons() {
        if(!FieldsValidator.checkNumber((long) nameTextField.getText().length(), 2, 19, "", false)){
            stateText.setFill(Color.RED);
            stateText.setText("Name for element must be from 2 to 19 chars");
            return;
        }
        try {
            Parent addWindow = FXMLLoader.load(getClass().getResource("/views/add.fxml"));
            addStage = new Stage();
            addStage.setScene(new Scene(addWindow));
            addStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickUpdateButton(ActionEvent event){
        int index = tableView.getSelectionModel().getFocusedIndex();
        selectedStudyGroupForUITable = studyGroups.get(index);
        studyGroups.remove(index);
        try {
            Parent updateWindow = FXMLLoader.load(getClass().getResource("/views/update.fxml"));
            updateStage = new Stage();
            updateStage.setScene(new Scene(updateWindow));
            updateStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void clickAddButton(ActionEvent event) {
        clickAddButtons();
    }

    @FXML
    private void clickAddIfMinButton(ActionEvent event) {
        setMinButton(true);
        clickAddButtons();
    }

    @FXML
    private void clickAddIfMaxButton(ActionEvent event) {
        setMaxButton(true);
        clickAddButtons();
    }

    @FXML
    private void clickRemove(ActionEvent event) {
        int index = tableView.getSelectionModel().getFocusedIndex();
        studyGroups.remove(index);
        stateText.setText("Element was successfully removed");
        stateText.setFill(Color.GREEN);
    }

    @FXML
    private void clickCanvas(MouseEvent event) {
        int eventX = (int) event.getX();
        int eventY = (int) event.getY();
        int minDistance = UIMain.MIN_DISTANCE;
        StudyGroupForUITable nearerStudyGroup = null;
        for (StudyGroupForUITable studyGroup : studyGroups) {
            int distance = UIMain.calculateDistance(UIMain.fromNormalXToCanvasX(studyGroup.getX().intValue(), canvas), eventX,
                    UIMain.fromNormalYToCanvasY(studyGroup.getY().intValue(), canvas), eventY);
            if (distance < minDistance) {
                minDistance = distance;
                nearerStudyGroup = studyGroup;
            }
        }
        if (nearerStudyGroup != null) {
            int row = tableView.getItems().indexOf(nearerStudyGroup);
            tableView.getSelectionModel().select(row);
            if (row != -1) {
                stateText.setText("Element was successfully found in table");
                stateText.setFill(Color.GREEN);
                return;
            }
            stateText.setText("Element not found in table");
            stateText.setFill(Color.RED);
        }
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIMain.mainController = this;
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        studentsCountColumn.setCellValueFactory(new PropertyValueFactory<>("studentsCount"));
        formOfEducationColumn.setCellValueFactory(new PropertyValueFactory<>("formOfEducation"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        adminNameColumn.setCellValueFactory(new PropertyValueFactory<>("adminName"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        passportIdColumn.setCellValueFactory(new PropertyValueFactory<>("passportID"));
        locationNameColumn.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));

        StudyGroup studyGroup1 = new StudyGroup(1, "p3111", new Coordinates(1L, 2), ZonedDateTime.now(), 32L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Danila", 178L, 76, "12345432", new Location(1, 1L, null)), "user", new Scanner(System.in));
        StudyGroup studyGroup2 = new StudyGroup(2, "p31345", new Coordinates(100L, 2), ZonedDateTime.now(), 35L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup3 = new StudyGroup(3, "p31345", new Coordinates(-1L, 27), ZonedDateTime.now(), 34L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup4 = new StudyGroup(4, "p31345", new Coordinates(189L, 143), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup5 = new StudyGroup(5, "p31345", new Coordinates(-300L, -28), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup6 = new StudyGroup(6, "p31345", new Coordinates(187L, -40), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup7 = new StudyGroup(7, "p31345", new Coordinates(400L, -100), ZonedDateTime.now(), 1L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup8 = new StudyGroup(8, "p31345", new Coordinates(40L, 40), ZonedDateTime.now(), 1L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup9 = new StudyGroup(9, "p31345", new Coordinates(13L, -150), ZonedDateTime.now(), 1L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup10 = new StudyGroup(10, "p31345", new Coordinates(-400L, -5), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup11 = new StudyGroup(11, "p31345", new Coordinates(50L, 100), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup12 = new StudyGroup(12, "p31345", new Coordinates(-90L, 0), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        studyGroups = FXCollections.observableArrayList(
                new StudyGroupForUITable(studyGroup1), new StudyGroupForUITable(studyGroup2), new StudyGroupForUITable(studyGroup3), new StudyGroupForUITable(studyGroup4), new StudyGroupForUITable(studyGroup5), new StudyGroupForUITable(studyGroup6), new StudyGroupForUITable(studyGroup7),
                new StudyGroupForUITable(studyGroup8), new StudyGroupForUITable(studyGroup9), new StudyGroupForUITable(studyGroup10), new StudyGroupForUITable(studyGroup11), new StudyGroupForUITable(studyGroup12)
        );

        tableView.setItems(studyGroups);

        ObservableList<String> fields = FXCollections.observableArrayList("Id", "Name", "CreationDate",
                "StudentsCount", "FormOfEducation", "Semester", "AdminName", "Height", "Weight", "PassportID", "LocationName", "Owner");
        fieldChoiceBox.setItems(fields);
        fieldChoiceBox.setValue("Name");
    }
}
