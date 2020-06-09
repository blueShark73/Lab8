package com.itmo.client;

import com.itmo.app.Handler;
import com.itmo.client.controllers.AuthController;
import com.itmo.client.controllers.MainController;
import com.itmo.client.controllers.State;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

public class UIMain extends Application {
    public static String USERNAME;
    public static MainController mainController;
    public static AuthController authController;
    public static Client client;
    public static ResourceBundle resourceBundle;
    public static Stage mainStage;
    public static State state;
    public static ClassLoader loader;

    public static final String HOST = "localhost";
    public static final int PORT = 3876;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        client = new Client();
        client.connect(HOST, PORT);
        Handler handler = new Handler();
        handler.setClient(client);
        handler.setDefaultPack();
        client.setHandler(handler);

        String path = "C:\\Users\\79516\\Lab8\\src\\main\\resources";
        File file = new File(path);
        URL[] urls = {file.toURI().toURL()};
        loader = new URLClassLoader(urls);

        resourceBundle = ResourceBundle.getBundle("locals", Locale.forLanguageTag("RU"), loader);
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"), resourceBundle);
        stage.setScene(new Scene(root));
        stage.setTitle("JavaFX application by Dyakonov Michail");

        Parent auth = FXMLLoader.load(getClass().getResource("/views/auth.fxml"));
        Stage authStage = new Stage();
        authStage.setScene(new Scene(auth));
        authStage.setTitle("Hi");
        authStage.show();

        Thread.setDefaultUncaughtExceptionHandler(UIMain::exceptionHandler);
        authController.setHandlers(authStage, mainController);
    }

    private static void exceptionHandler(Thread thread, Throwable throwable) {
        if (throwable instanceof NumberFormatException) {
            mainController.getStateText().setFill(Color.RED);
            mainController.getStateText().setText("Parse error");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
