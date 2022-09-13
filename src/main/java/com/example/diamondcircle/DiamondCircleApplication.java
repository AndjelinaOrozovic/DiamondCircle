package com.example.diamondcircle;

import com.example.util.FileWatcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class DiamondCircleApplication extends Application {

    private static final String DIAMOND_IMAGE = "/diamondImage.png";

    private static final String START_FXML = "start-window.fxml";

    private static final String START_TITLE = "Diamond Circle Game";

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(START_FXML));

        Scene scene = new Scene(root);
        stage.setTitle(START_TITLE);
        Image image = new Image(DIAMOND_IMAGE);
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
        new FileWatcher().start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}