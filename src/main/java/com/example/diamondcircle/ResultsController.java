package com.example.diamondcircle;

import com.example.util.ResultsUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.SelectionMode;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {

    private static final String ERROR_MESSAGE = "Pogresan format fajla.";

    @FXML
    private ListView<File> resultsList;

    @FXML
    private TextArea showResult;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File[] data = ResultsUtil.getResultsFiles();

        ObservableList<File> files = FXCollections.observableArrayList();
        files.addAll(data);

        resultsList.setItems(files);

        resultsList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);

                if(empty || item == null) {
                    setText(null);
                }
                else {
                    setText(item.getName());
                    setOnMousePressed(e -> showSelectedFile());
                }
            }
        });
        resultsList.setEditable(false);
        resultsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        showResult.setEditable(false);
        showResult.setWrapText(true);
    }

    private void showSelectedFile() {
        File selectedFile = resultsList.getSelectionModel().getSelectedItems().get(0);

        String gameResults = ResultsUtil.deserializeResults(selectedFile.getName());

        if(gameResults == null) {
            Platform.runLater(() -> showResult.setText(ERROR_MESSAGE));
        }
        else {
            Platform.runLater(() -> showResult.setText(gameResults));
        }
    }

}
