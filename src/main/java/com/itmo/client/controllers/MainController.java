package com.itmo.client.controllers;

import com.itmo.app.*;
import com.itmo.client.StudyGroupForUITable;
import com.itmo.client.UIMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

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
    private TextField filteredValue;

    @FXML
    private Text stateText;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Color userColor = Color.color(Math.random(), Math.random(), Math.random());
        userColorRectangle.setFill(userColor);

        currentUserLabel.setText(currentUserLabel.getText() + UIMain.USERNAME);


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
        StudyGroup studyGroup2 = new StudyGroup(2, "p31345", new Coordinates(10L, 2), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup3 = new StudyGroup(3, "p31345", new Coordinates(-1L, 27), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup4 = new StudyGroup(4, "p31345", new Coordinates(189L, 143), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup5 = new StudyGroup(5, "p31345", new Coordinates(-300L, -28), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup6 = new StudyGroup(6, "p31345", new Coordinates(187L, -40), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup7 = new StudyGroup(7, "p31345", new Coordinates(100L, 100), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup8 = new StudyGroup(8, "p31345", new Coordinates(40L, 40), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup9 = new StudyGroup(9, "p31345", new Coordinates(13L, -150), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup10 = new StudyGroup(10, "p31345", new Coordinates(-400L, -5), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup11 = new StudyGroup(11, "p31345", new Coordinates(50L, 100), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup12 = new StudyGroup(12, "p31345", new Coordinates(-90L, 0), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1, 1L, "place")), "user123", new Scanner(System.in));
        ObservableList<StudyGroupForUITable> studyGroups = FXCollections.observableArrayList(
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
