package com.example.diamondcircle;

import com.example.game.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import java.io.IOException;

public class StartWindowController {

    @FXML
    private ChoiceBox numberOfPlayersChoiceBox;

    @FXML
    private ChoiceBox numberOfColumnsChoiceBox;

    @FXML
    private Button startAppButton;

    private Stage stage;

    private Scene scene;

    private Parent root;

    public static DiamondCircleController diamondCircleController;

    private static final String DIAMOND_FXML = "diamond-circle-view.fxml";

    public void startMainWindow(ActionEvent event) throws IOException {

        Game.numberOfColumns = Integer.parseInt(numberOfColumnsChoiceBox.getValue().toString());
        Game.numberOfPlayers = Integer.parseInt(numberOfPlayersChoiceBox.getValue().toString());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DIAMOND_FXML));

        root = fxmlLoader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnCloseRequest(x -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
        diamondCircleController = fxmlLoader.getController();
    }
}
