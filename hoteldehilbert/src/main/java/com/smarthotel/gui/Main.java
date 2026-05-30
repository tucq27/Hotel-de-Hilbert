package com.smarthotel.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(
                FXMLLoader.load(
                        getClass().getResource("/GUI/TelaAdmin.fxml")
                )
        );

        stage.setScene(scene);
        stage.setTitle("SmartHotel");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}