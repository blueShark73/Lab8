package com.itmo.client;

import com.itmo.app.Handler;
import com.itmo.client.controllers.AuthController;
import com.itmo.client.controllers.MainController;
import com.itmo.server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class UIMain extends Application {
    public static String USERNAME;
    public static String PASSWORD;
    public static boolean GENERATE_PASS_FOR_USER;
    public static final int MIN_DISTANCE = 10;
    public static MainController mainController;
    public static AuthController authController;
    public static Client client;

    public static final String HOST = "localhost";
    public static final int PORT = 3876;

    @Override
    public void start(Stage stage) throws Exception {
        /*
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

        //FilteredList<StudyGroupForUITable> filteredList = new FilteredList<>(studyGroups);
        //filteredList.setPredicate(studyGroupForUITable -> studyGroupForUITable.getName().equals("p3111"));

        TableView<StudyGroupForUITable> tableView = new TableView<>(studyGroups);
        tableView.setPrefHeight(300);
        tableView.setPrefWidth(1100);

        TableColumn<StudyGroupForUITable, Long> idColumn = new TableColumn<>("Id");
        TableColumn<StudyGroupForUITable, String> nameColumn = new TableColumn<>("Name");
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
        groupAdminColumn.getColumns().addAll(adminNameColumn, heightColumn, weightColumn, passportIdColumn, locationColumn);

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
        xAdminColumn.setCellValueFactory(new PropertyValueFactory<>("xAdmin"));
        yAdminColumn.setCellValueFactory(new PropertyValueFactory<>("yAdmin"));
        locationNameColumn.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));





        tableView.getColumns().addAll(idColumn, nameColumn, creationDateColumn, studentsCountColumn, formOfEducationColumn, semesterColumn,
                groupAdminColumn, ownerColumn);

        Button addButton = new Button("Add element");
        Button addIfMinButton = new Button("Add element if minimal");
        Button addIfMaxButton = new Button("Add element if maximal");
        Button clearButton = new Button("Delete all your elements");
        Button exitButton = new Button("Exit");
        Button helpButton = new Button("What is it? Help me!");
        Button historyButton = new Button("History");
        Button infoButton = new Button("Information about collection");
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Log up");
        Button removeButton = new Button("Remove element");
        Button updateButton = new Button("Update fields");
        Label userLabel = new Label("Current user: " + USERNAME);
        exitButton.setMinWidth(100);
        Text textState = new Text("Normal state");
        textState.setFill(Color.GREEN);
        textState.setFont(new Font(14));

        removeButton.setOnAction(actionEvent -> {
            int index = tableView.getSelectionModel().getFocusedIndex();
            studyGroups.remove(index);
            textState.setText("Element was removed");
            textState.setFill(Color.RED);
        });

        Canvas canvas = new Canvas();
        canvas.setWidth(1100);
        canvas.setHeight(380);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        drawAxis(canvas);

        HashSet<StudyGroup> collection = new HashSet<>();
        Collections.addAll(collection, studyGroup1, studyGroup2, studyGroup3, studyGroup4, studyGroup5, studyGroup6,
                studyGroup7, studyGroup8, studyGroup9, studyGroup10, studyGroup11, studyGroup12);

        collection.forEach(studyGroup -> drawElement(studyGroup.getCoordinates().getX().intValue(), (int) studyGroup.getCoordinates().getY(),
                Color.color(Math.random(), Math.random(), Math.random()), canvas));

        canvas.setOnMouseClicked(event -> {
            int eventX = (int) event.getX();
            int eventY = (int) event.getY();
            int minDistance = MIN_DISTANCE;
            StudyGroup nearerStudyGroup = null;
            for (StudyGroup studyGroup : collection) {
                int distance = calculateDistance(fromNormalXToCanvasX(studyGroup.getCoordinates().getX().intValue(), canvas), eventX,
                        fromNormalYToCanvasY((int) studyGroup.getCoordinates().getY(), canvas), eventY);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearerStudyGroup = studyGroup;
                }
            }
            if (nearerStudyGroup != null && collection.remove(nearerStudyGroup)) {
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getWidth());
                drawAxis(canvas);
                collection.forEach(studyGroup -> drawElement(studyGroup.getCoordinates().getX().intValue(), (int) studyGroup.getCoordinates().getY(),
                        Color.color(Math.random(), Math.random(), Math.random()), canvas));
            }
        });

        Label labelValue = new Label("Value:");
        Label labelSelect = new Label("Select column:");
        Button buttonFind = new Button("Find");
        TextField nameFilter = new TextField();
        nameFilter.setPrefColumnCount(8);
        ObservableList<String> fields = FXCollections.observableArrayList("Id", "Name", "CreationDate", "StudentsCount",
                "FormOfEducation", "Semester", "AdminName", "Height", "Weight", "passportID", "LocationName");
        ChoiceBox<String> filteredField = new ChoiceBox<>(fields);
        filteredField.setValue("Name");

        HBox state = new HBox(textState);
        state.setAlignment(Pos.TOP_CENTER);
        HBox filters = new HBox(10, labelValue, nameFilter, labelSelect, filteredField, buttonFind);
        filters.setAlignment(Pos.TOP_LEFT);
        FlowPane tableFlowPane = new FlowPane(tableView);
        HBox buttons = new HBox(7, addButton, addIfMinButton, addIfMaxButton, clearButton, helpButton,
                historyButton, infoButton, removeButton, updateButton);
        HBox flowPaneHigh = new HBox(10, userLabel, exitButton);
        flowPaneHigh.setAlignment(Pos.TOP_RIGHT);
        buttons.setAlignment(Pos.TOP_CENTER);
        FlowPane flowPane3 = new FlowPane(Orientation.VERTICAL, flowPaneHigh, buttons, filters, tableFlowPane, state, canvas);
        Scene scene = new Scene(flowPane3, 1100, 780);

        Stage authorizationWindow = new Stage();
        authorizationWindow.setTitle("Welcome!!!");
        EventHandler<ActionEvent> handler = actionEvent -> {
            authorizationWindow.close();
            stage.setScene(scene);
            stage.show();
        };
        loginButton.setOnAction(handler);
        registerButton.setOnAction(handler);

        Label userNameLabel = new Label("Your username:");
        Label passLabel = new Label("Your password:");
        TextField userNameField = new TextField();
        PasswordField passField = new PasswordField();
        CheckBox generatePassCheckBox = new CheckBox("Generate password automatically");
        HBox buttonsForIn = new HBox(10, loginButton, registerButton);
        buttonsForIn.setAlignment(Pos.TOP_CENTER);
        VBox elements = new VBox(5, userNameLabel, userNameField, passLabel, passField, generatePassCheckBox, buttonsForIn);
        elements.setAlignment(Pos.TOP_CENTER);
        Scene authScene = new Scene(elements, 200, 180);
        authorizationWindow.setScene(authScene);
        authorizationWindow.show();
         */

        client = new Client();
        client.connect(HOST, PORT);
        Handler handler = new Handler();
        handler.setClient(client);
        handler.setDefaultPack();
        client.setHandler(handler);

        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

        Parent auth = FXMLLoader.load(getClass().getResource("/views/auth.fxml"));
        Stage authStage = new Stage();
        authStage.setScene(new Scene(auth));
        authStage.show();

        Thread.setDefaultUncaughtExceptionHandler(UIMain::exceptionHandler);
        authController.setHandlers(authStage, mainController);
    }

    private static void exceptionHandler(Thread thread, Throwable throwable){
        if(throwable instanceof NumberFormatException) {
            mainController.getStateText().setFill(Color.RED);
            mainController.getStateText().setText("Parse error");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
