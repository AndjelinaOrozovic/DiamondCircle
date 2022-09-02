package com.example.diamondcircle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class DiamondCircleApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("start-window.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Start Window!");
        Image image = new Image("/diamondImage.png");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}