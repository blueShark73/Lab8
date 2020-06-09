package com.itmo.client.controllers;

import com.itmo.app.Semester;
import com.itmo.app.StudyGroup;
import com.itmo.client.StudyGroupForUITable;
import com.itmo.client.UIMain;
import com.itmo.commands.*;
import com.itmo.server.Response;
import com.itmo.utils.Painter;
import com.itmo.utils.StudyGroupAdapter;
import javafx.scene.control.cell.TextFieldTableCell;
import com.itmo.app.FormOfEducation;
import com.itmo.utils.FieldsValidator;
import com.itmo.utils.Listener;
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
import javafx.util.converter.LongStringConverter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

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
    private Button findGreaterButton;

    @FXML
    private Button findLessButton;

    @FXML
    private Button executeButton;

    @FXML
    private Button sumButton;

    @FXML
    private Button translateButton;

    @FXML
    private ChoiceBox<String> langChoiceBox;

    @FXML
    private TextField filteredValue;

    @FXML
    private Label userNameLabel;

    @FXML
    @Getter
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
    private Stage historyStage;

    @Getter
    private ObservableList<String> commandsForHistory;

    @Getter
    private Stage updateStage;

    @Getter
    private String info;

    @Getter
    private Stage infoStage;

    @Getter
    private Stage executeStage;

    @Getter
    private Painter painter;

    private Listener listener;

    public void callExitFromScript() {
        System.exit(0);
    }

    public void setValues() {
        Color userColor = UIMain.client.getUser().getColor();
        userColorRectangle.setFill(userColor);

        userNameLabel.setText(UIMain.client.getUser().getName());

        painter.drawAxis();
        for (StudyGroupForUITable studyGroupForUITable : studyGroups) {
            Color color = Color.color(studyGroupForUITable.getRed(), studyGroupForUITable.getGreen(), studyGroupForUITable.getBlue());
            painter.drawElement(studyGroupForUITable.getX().intValue(), studyGroupForUITable.getY().intValue(), studyGroupForUITable.getStudentsCount().intValue(), color);
        }

        if (UIMain.state != null) {
            listener = UIMain.state.getListener();
            return;
        }
        listener = new Listener(UIMain.PORT, UIMain.HOST);
        listener.start();
    }

    public void addWithCheckingFormat(StudyGroupForUITable studyGroupForUITable){
        studyGroupForUITable.changeDateFormat(new SimpleDateFormat(UIMain.resourceBundle.getString("dateFormat"), UIMain.resourceBundle.getLocale()));
        studyGroups.add(studyGroupForUITable);
    }

    private void clickAddButtons() {
        if (!FieldsValidator.checkNumber((long) nameTextField.getText().length(), 2, 19, "", false)) {
            stateText.setFill(Color.RED);
            stateText.setText("Name for element must be from 2 to 19 chars");
            return;
        }
        try {
            Parent addWindow = FXMLLoader.load(getClass().getResource("/views/add.fxml"), UIMain.resourceBundle);
            addStage = new Stage();
            addStage.setScene(new Scene(addWindow));
            addStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickExecuteButton() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/views/execute.fxml"), UIMain.resourceBundle);
            executeStage = new Stage();
            executeStage.setScene(new Scene(parent));
            executeStage.setTitle("Executing scripts");
            executeStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickHelpButton(){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("/views/help.fxml"), UIMain.resourceBundle);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Good luck");
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void clickSumButton() {
        try {
            Response response = UIMain.client.sendCommandAndReceiveAnswer(new SumOfStudentsCountCommand());
            stateText.setText(response.getAnswer());
            stateText.setFill(Color.GREEN);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickInfoButton() {
        try {
            info = UIMain.client.sendCommandAndReceiveAnswer(new InfoCommand()).getAnswer();

            Parent parent = FXMLLoader.load(getClass().getResource("/views/info.fxml"), UIMain.resourceBundle);
            infoStage = new Stage();
            infoStage.setScene(new Scene(parent));
            infoStage.setTitle("Information about collection");
            infoStage.show();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickHistoryButton() {
        try {
            Response response = UIMain.client.sendCommandAndReceiveAnswer(new HistoryCommand());
            String[] commands = response.getAnswer().split("\n");
            commandsForHistory = FXCollections.observableArrayList(commands);

            Parent parent = FXMLLoader.load(getClass().getResource("/views/history.fxml"), UIMain.resourceBundle);
            historyStage = new Stage();
            historyStage.setScene(new Scene(parent));
            historyStage.setTitle("Command history");
            historyStage.show();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickClearButton() {
        try {
            UIMain.client.sendCommandAndReceiveAnswer(new ClearCommand());
            stateText.setFill(Color.GREEN);
            stateText.setText("All your elements removed");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            stateText.setFill(Color.YELLOW);
            stateText.setText("Problems on server");
        }

    }

    @FXML
    private void clickRemoveButton() {
        int index = tableView.getSelectionModel().getFocusedIndex();
        StudyGroupForUITable studyGroupForUITable = studyGroups.get(index);
        Long id = studyGroupForUITable.getId();
        if (!checkPermission(studyGroupForUITable)) return;
        RemoveCommand command = new RemoveCommand();
        command.setId(id);
        try {
            if (UIMain.client.sendCommandAndReceiveAnswer(command).isSuccessfullyExecute()) {
                stateText.setFill(Color.GREEN);
                stateText.setText("Element successfully removed");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            stateText.setFill(Color.YELLOW);
            stateText.setText("Problems on server");
        }
    }

    @FXML
    private void clickExitButton() {
        try {
            UIMain.client.sendCommandAndReceiveAnswer(new ExitCommand());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    @FXML
    private void clickFindButton() {
        String field = fieldChoiceBox.getValue();
        String value = filteredValue.getText();
        ObservableList<StudyGroupForUITable> filteredList = FXCollections.observableArrayList();
        if (value.isEmpty()) {
            tableView.setItems(studyGroups);
            return;
        }
        try {
            if (field.equals(idColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getId().equals(Long.parseLong(value)));
            else if (field.equals(nameColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getName().contains(value));
            else if (field.equals(creationDateColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getCreationDate().equals(value));
            else if (field.equals(studentsCountColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getStudentsCount().equals(Long.parseLong(value)));
            else if (field.equals(formOfEducationColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getFormOfEducation().contains(value));
            else if (field.equals(semesterColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getSemester().contains(value));
            else if (field.equals(adminNameColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getAdminName().contains(value));
            else if (field.equals(heightColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getHeight().equals(Long.parseLong(value)));
            else if (field.equals(weightColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getWeight().equals(Long.parseLong(value)));
            else if (field.equals(passportIdColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getPassportID().contains(value));
            else if (field.equals(locationNameColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getLocationName().contains(value));
            else if (field.equals(ownerColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getOwner().contains(value));
            tableView.setItems(filteredList);
        } catch (NumberFormatException e) {
            stateText.setFill(Color.RED);
            stateText.setText("Parse error");
        }
    }

    @FXML
    private void clickFindGreaterButton() {
        String field = fieldChoiceBox.getValue();
        String value = filteredValue.getText();
        ObservableList<StudyGroupForUITable> filteredList = FXCollections.observableArrayList();
        if (value.isEmpty()) {
            tableView.setItems(studyGroups);
            return;
        }
        try {
            if (field.equals(idColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getId() > Long.parseLong(value));
            else if (field.equals(nameColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getName().compareTo(value) > 0);
            else if (field.equals(creationDateColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getCreationDate().compareTo(value) > 0);
            else if (field.equals(studentsCountColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getStudentsCount() > Long.parseLong(value));
            else if (field.equals(formOfEducationColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getFormOfEducation().compareTo(value) > 0);
            else if (field.equals(semesterColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> Semester.getNumberByEnglish(studyGroupForUITable.getSemester()) > Semester.getNumberByEnglish(value));
            else if (field.equals(adminNameColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getAdminName().compareTo(value) > 0);
            else if (field.equals(heightColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getHeight() > Long.parseLong(value));
            else if (field.equals(weightColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getWeight() > Long.parseLong(value));
            else if (field.equals(passportIdColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getPassportID().compareTo(value) > 0);
            else if (field.equals(locationNameColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getLocationName().compareTo(value) > 0);
            else if (field.equals(ownerColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getOwner().compareTo(value) > 0);
            tableView.setItems(filteredList);
        } catch (NumberFormatException | NullPointerException e) {
            stateText.setFill(Color.RED);
            stateText.setText("Parse error");
        }
    }

    @FXML
    private void clickFindLessButton() {
        String field = fieldChoiceBox.getValue();
        String value = filteredValue.getText();
        ObservableList<StudyGroupForUITable> filteredList = FXCollections.observableArrayList();
        if (value.isEmpty()) {
            tableView.setItems(studyGroups);
            return;
        }
        try {
            if (field.equals(idColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getId() < Long.parseLong(value));
            else if (field.equals(nameColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getName().compareTo(value) < 0);
            else if (field.equals(creationDateColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getCreationDate().compareTo(value) < 0);
            else if (field.equals(studentsCountColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getStudentsCount() < Long.parseLong(value));
            else if (field.equals(formOfEducationColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getFormOfEducation().compareTo(value) < 0);
            else if (field.equals(semesterColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> Semester.getNumberByEnglish(studyGroupForUITable.getSemester()) < Semester.getNumberByEnglish(value));
            else if (field.equals(adminNameColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getAdminName().compareTo(value) < 0);
            else if (field.equals(heightColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getHeight() < Long.parseLong(value));
            else if (field.equals(weightColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getWeight() < Long.parseLong(value));
            else if (field.equals(passportIdColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getPassportID().compareTo(value) < 0);
            else if (field.equals(locationNameColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getLocationName().compareTo(value) < 0);
            else if (field.equals(ownerColumn.getText()))
                filteredList = studyGroups.filtered(studyGroupForUITable -> studyGroupForUITable.getOwner().compareTo(value) < 0);
            tableView.setItems(filteredList);
        } catch (NumberFormatException | NullPointerException e) {
            stateText.setFill(Color.RED);
            stateText.setText("Parse error");
        }
    }

    @FXML
    private void clickUpdateButton(ActionEvent event) {
        int index = tableView.getSelectionModel().getFocusedIndex();
        selectedStudyGroupForUITable = studyGroups.get(index);
        if (!checkPermission(selectedStudyGroupForUITable)) return;
        try {
            Parent updateWindow = FXMLLoader.load(getClass().getResource("/views/update.fxml"), UIMain.resourceBundle);
            updateStage = new Stage();
            updateStage.setScene(new Scene(updateWindow));
            updateStage.show();
        } catch (IOException e) {
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

    public void changeDateFormat(){
        studyGroups.forEach(s -> s.changeDateFormat(new SimpleDateFormat(UIMain.resourceBundle.getString("dateFormat"), UIMain.resourceBundle.getLocale())));
    }

    @FXML
    private void clickTranslate() {
        switch (langChoiceBox.getValue()) {
            case "Русский":
                UIMain.resourceBundle = ResourceBundle.getBundle("locals", Locale.forLanguageTag("RU"), UIMain.loader);
                break;
            case "Polskie":
                UIMain.resourceBundle = ResourceBundle.getBundle("locals", Locale.forLanguageTag("POL"), UIMain.loader);
                break;
            case "Íslensku":
                UIMain.resourceBundle = ResourceBundle.getBundle("locals", Locale.forLanguageTag("ICE"), UIMain.loader);
                break;
            case "Español":
                UIMain.resourceBundle = ResourceBundle.getBundle("locals", Locale.forLanguageTag("SPA"), UIMain.loader);
                break;
        }
        UIMain.state = new State(studyGroups, langChoiceBox.getValue(), listener, painter);
        Scene scene = UIMain.mainStage.getScene();
        changeDateFormat();
        try {
            scene.setRoot(FXMLLoader.load(getClass().getResource("/views/main.fxml"), UIMain.resourceBundle));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickCanvas(MouseEvent event) {
        int eventX = (int) event.getX();
        int eventY = (int) event.getY();
        double minDistance = Painter.MIN_DISTANCE;
        StudyGroupForUITable nearerStudyGroup = null;
        for (StudyGroupForUITable studyGroup : studyGroups) {
            int distance = painter.calculateDistance(painter.fromNormalXToCanvasX(studyGroup.getX().intValue()), eventX,
                    painter.fromNormalYToCanvasY(studyGroup.getY().intValue()), eventY);
            if (distance < minDistance) {
                minDistance = distance;
                nearerStudyGroup = studyGroup;
            }
        }
        if (nearerStudyGroup != null) {
            int row = tableView.getItems().indexOf(nearerStudyGroup);
            tableView.getSelectionModel().select(row);
            if (row != -1) {
                stateText.setText("Element successfully found in table");
                stateText.setFill(Color.GREEN);
                return;
            }
            stateText.setText("Element not found in table");
            stateText.setFill(Color.RED);
        }
    }

    private void callUpdate(StudyGroup group) {
        UpdateCommand command = new UpdateCommand();
        command.setStudyGroup(group);
        try {
            if (UIMain.client.sendCommandAndReceiveAnswer(command).isSuccessfullyExecute()) {
                stateText.setFill(Color.GREEN);
                stateText.setText("Element updated");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission(StudyGroupForUITable studyGroupForUITable) {
        if (studyGroupForUITable.getOwner().equals(UIMain.USERNAME)) return true;
        stateText.setFill(Color.RED);
        stateText.setText("Permission denied");
        return false;
    }

    private void setOutOfRange() {
        stateText.setFill(Color.RED);
        stateText.setText("Out of range");
    }

    private void setParseError() {
        stateText.setFill(Color.RED);
        stateText.setText("Parse error");
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIMain.mainController = this;
        if(UIMain.state==null) painter = new Painter(canvas);
        else {
            painter = UIMain.state.getPainter();
            painter.setCanvas(canvas);
        }

        tableView.setEditable(true);

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            StudyGroupForUITable group = studyGroups.get(event.getTablePosition().getRow());
            if (!checkPermission(group)) {
                tableView.refresh();
                return;
            }
            if (!FieldsValidator.checkNumber((long) event.getNewValue().length(), 2, 19, "", false)) {
                setOutOfRange();
                tableView.refresh();
                return;
            }
            group.setName(event.getNewValue());
            callUpdate(StudyGroupAdapter.convert(group));
        });

        studentsCountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        studentsCountColumn.setOnEditCommit(event -> {
            StudyGroupForUITable group = event.getTableView().getItems().get(event.getTablePosition().getRow());
            if (!checkPermission(group)) {
                tableView.refresh();
                return;
            }
            if (!FieldsValidator.checkNumber(event.getNewValue(), 1, 50, "", false)) {
                setOutOfRange();
                tableView.refresh();
                return;
            }
            group.setStudentsCount(event.getNewValue());
            callUpdate(StudyGroupAdapter.convert(group));
        });

        semesterColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        semesterColumn.setOnEditCommit(event -> {
            StudyGroupForUITable group = studyGroups.get(event.getTablePosition().getRow());
            if (!checkPermission(group)) {
                tableView.refresh();
                return;
            }
            if (Semester.getValueByEnglish(event.getNewValue()) == null) {
                setParseError();
                tableView.refresh();
                return;
            }
            group.setSemester(event.getNewValue());
            callUpdate(StudyGroupAdapter.convert(group));
        });

        formOfEducationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        formOfEducationColumn.setOnEditCommit(event -> {
            StudyGroupForUITable group = studyGroups.get(event.getTablePosition().getRow());
            if (!checkPermission(group)) {
                tableView.refresh();
                return;
            }
            if (FormOfEducation.getValueByEnglish(event.getNewValue()) == null) {
                setParseError();
                tableView.refresh();
                return;
            }
            group.setFormOfEducation(event.getNewValue());
            callUpdate(StudyGroupAdapter.convert(group));
        });

        adminNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        adminNameColumn.setOnEditCommit(event -> {
            StudyGroupForUITable group = studyGroups.get(event.getTablePosition().getRow());
            if (!checkPermission(group)) {
                tableView.refresh();
                return;
            }
            if (!FieldsValidator.checkNumber((long) event.getNewValue().length(), 2, 19, "", false)) {
                setOutOfRange();
                tableView.refresh();
                return;
            }
            group.setAdminName(event.getNewValue());
            callUpdate(StudyGroupAdapter.convert(group));
        });

        heightColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        heightColumn.setOnEditCommit(event -> {
            StudyGroupForUITable group = event.getTableView().getItems().get(event.getTablePosition().getRow());
            if (!checkPermission(group)) {
                tableView.refresh();
                return;
            }
            if (!FieldsValidator.checkNumber(event.getNewValue(), 1, 300, "", false)) {
                setOutOfRange();
                tableView.refresh();
                return;
            }
            group.setHeight(event.getNewValue());
            callUpdate(StudyGroupAdapter.convert(group));
        });

        weightColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        weightColumn.setOnEditCommit(event -> {
            StudyGroupForUITable group = event.getTableView().getItems().get(event.getTablePosition().getRow());
            if (!checkPermission(group)) {
                tableView.refresh();
                return;
            }
            if (!FieldsValidator.checkNumber(event.getNewValue(), 1, 300, "", false)) {
                setOutOfRange();
                tableView.refresh();
                return;
            }
            group.setWeight(event.getNewValue());
            callUpdate(StudyGroupAdapter.convert(group));
        });

        passportIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passportIdColumn.setOnEditCommit(event -> {
            StudyGroupForUITable group = studyGroups.get(event.getTablePosition().getRow());
            if (!checkPermission(group)) {
                tableView.refresh();
                return;
            }
            if (!FieldsValidator.checkNumber((long) event.getNewValue().length(), 7, 24, "", false)) {
                setOutOfRange();
                tableView.refresh();
                return;
            }
            group.setPassportID(event.getNewValue());
            callUpdate(StudyGroupAdapter.convert(group));
        });

        locationNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        locationNameColumn.setOnEditCommit(event -> {
            StudyGroupForUITable group = studyGroups.get(event.getTablePosition().getRow());
            if (!checkPermission(group)) {
                tableView.refresh();
                return;
            }
            if (!FieldsValidator.checkNumber((long) event.getNewValue().length(), 0, 20, "", false)) {
                setOutOfRange();
                tableView.refresh();
                return;
            }
            group.setLocationName(event.getNewValue());
            callUpdate(StudyGroupAdapter.convert(group));
        });

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

        studyGroups = UIMain.state == null ? FXCollections.observableArrayList() : UIMain.state.getStudyGroupForUITables();

        tableView.setItems(studyGroups);

        ObservableList<String> fields = FXCollections.observableArrayList(idColumn.getText(), nameColumn.getText(),
                creationDateColumn.getText(), studentsCountColumn.getText(), formOfEducationColumn.getText(), semesterColumn.getText(),
                adminNameColumn.getText(), heightColumn.getText(), weightColumn.getText(), passportIdColumn.getText(),
                locationNameColumn.getText(), ownerColumn.getText());
        fieldChoiceBox.setItems(fields);
        fieldChoiceBox.setValue(nameColumn.getText());

        ObservableList<String> languages = FXCollections.observableArrayList("Русский", "Español", "Íslensku", "Polskie");
        langChoiceBox.setItems(languages);
        langChoiceBox.setValue(UIMain.state == null ? "Русский" : UIMain.state.getCurrentLang());
        if (UIMain.state != null) setValues();
    }
}
