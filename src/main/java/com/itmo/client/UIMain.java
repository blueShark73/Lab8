package com.itmo.client;

import com.itmo.app.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.ZonedDateTime;
import java.util.Scanner;

public class UIMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        StudyGroup studyGroup1 = new StudyGroup(1, "p3111", new Coordinates(1L,2), ZonedDateTime.now(), 32L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Danila", 178L, 76, "12345432", new Location(1,1L,null)), "user", new Scanner(System.in));
        StudyGroup studyGroup2 = new StudyGroup(2, "p31345", new Coordinates(1L,2), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1,1L,"place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup3 = new StudyGroup(3, "p31345", new Coordinates(1L,2), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1,1L,"place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup4 = new StudyGroup(4, "p31345", new Coordinates(1L,2), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1,1L,"place")), "user123", new Scanner(System.in));
        StudyGroup studyGroup5 = new StudyGroup(5, "p31345", new Coordinates(1L,2), ZonedDateTime.now(), 3L, FormOfEducation.DISTANCE_EDUCATION, Semester.EIGHTH, new Person("Sasha", 178L, 76, "1234ert2", new Location(1.1,1L,"place")), "user123", new Scanner(System.in));
        ObservableList<StudyGroupForUITable> studyGroups = FXCollections.observableArrayList(
                new StudyGroupForUITable(studyGroup1), new StudyGroupForUITable(studyGroup2), new StudyGroupForUITable(studyGroup3), new StudyGroupForUITable(studyGroup4), new StudyGroupForUITable(studyGroup5)
        );

        TableView<StudyGroupForUITable> tableView = new TableView<>(studyGroups);
        tableView.setPrefHeight(600);
        tableView.setPrefWidth(600);

        TableColumn<StudyGroupForUITable, Long> idColumn = new TableColumn<>("Id");
        TableColumn<StudyGroupForUITable, String> nameColumn = new TableColumn<>("Name");
        TableColumn<StudyGroupForUITable, Long> coordinatesColumn = new TableColumn<>("Coordinates");
        TableColumn<StudyGroupForUITable, Long> xColumn = new TableColumn<>("X");
        TableColumn<StudyGroupForUITable, Long> yColumn = new TableColumn<>("Y");
        TableColumn<StudyGroupForUITable, String> creationDateColumn = new TableColumn<>("CreationDate");
        TableColumn<StudyGroupForUITable, Long> studentsCountColumn = new TableColumn<>("StudentsCount");
        TableColumn<StudyGroupForUITable, String> formOfEducationColumn = new TableColumn<>("FormOfEducation");
        TableColumn<StudyGroupForUITable, String> semesterColumn = new TableColumn<>("Semester");
        TableColumn<StudyGroupForUITable, String> groupAdminColumn = new TableColumn<>("GroupAdmin");
        TableColumn<StudyGroupForUITable, String> adminNameColumn = new TableColumn<>("AdminName");
        TableColumn<StudyGroupForUITable, Long> heightColumn = new TableColumn<>("Height");
        TableColumn<StudyGroupForUITable, Long> weightColumn = new TableColumn<>("Weight");
        TableColumn<StudyGroupForUITable, String> passportIdColumn = new TableColumn<>("PassportID");
        TableColumn<StudyGroupForUITable, String> locationColumn = new TableColumn<>("Location");
        TableColumn<StudyGroupForUITable, Double> xAdminColumn = new TableColumn<>("XAdmin");
        TableColumn<StudyGroupForUITable, Long> yAdminColumn = new TableColumn<>("YAdmin");
        TableColumn<StudyGroupForUITable, String> locationNameColumn = new TableColumn<>("LocationName");
        TableColumn<StudyGroupForUITable, String> ownerColumn = new TableColumn<>("Owner");

        locationColumn.getColumns().addAll(xAdminColumn, yAdminColumn, locationNameColumn);
        coordinatesColumn.getColumns().addAll(xColumn, yColumn);
        groupAdminColumn.getColumns().addAll(adminNameColumn, heightColumn, weightColumn, passportIdColumn, locationColumn);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        studentsCountColumn.setCellValueFactory(new PropertyValueFactory<>("studentsCount"));
        formOfEducationColumn.setCellValueFactory(new PropertyValueFactory<>("formOfEducation"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        adminNameColumn.setCellValueFactory(new PropertyValueFactory<>("adminName"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        passportIdColumn.setCellValueFactory(new PropertyValueFactory<>("passportID"));
        xAdminColumn.setCellValueFactory(new PropertyValueFactory<>("xAdmin"));
        yAdminColumn.setCellValueFactory(new PropertyValueFactory<>("yAdmin"));
        locationNameColumn.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));

        tableView.getColumns().addAll(idColumn, nameColumn, coordinatesColumn, creationDateColumn, studentsCountColumn, formOfEducationColumn, semesterColumn,
                groupAdminColumn, ownerColumn);

        FlowPane flowPane = new FlowPane(tableView);
        Scene scene = new Scene(flowPane, 1200, 600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
