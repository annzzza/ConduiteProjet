package com.example.conduiteprojet.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BrowserLoader extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("browser-view.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Browse through Assistances");
        stage.setScene(scene);
        stage.show();
    }
}
