package com.example.diamondcircle;

import com.example.figures.Figure;
import com.example.game.Field;
import com.example.game.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class CurrentFigureController implements Initializable {

    @FXML
    private GridPane figurePathGridPane;

    @FXML
    private ImageView figureImage;

    @FXML
    private Label figureName;

    @FXML
    private Label figureTime;

    public static Figure selectedFigure;

    private static final int R_CONSTANT = 135;

    private static final int G_CONSTANT = 206;

    private static final int B_CONSTANT = 235;

    public static Field[][] currentPathMatrix = new Field[][]{};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setImage(selectedFigure.getImageSource(), figureImage);
            figureName.setText(selectedFigure.getClass().getSimpleName() + ", id: " + selectedFigure.idFigure);
            colorCurrentPath();
            if(selectedFigure.calculateTime() != -1) {
                figureTime.setText("Figurica je u igri: " + selectedFigure.calculateTime() + "s");
            } else {
                figureTime.setText("Figurica jos nije zapocela igru!");
            }
            startColoring();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void startColoring() {
        int r = R_CONSTANT;
        int g = G_CONSTANT;
        int b = B_CONSTANT;
        int numberOfColumns = Game.numberOfColumns;

        currentPathMatrix = new Field[numberOfColumns][numberOfColumns];

        for (int p = 0; p < Game.getPathFields().size(); p++) {
            for (int i = 0; i < numberOfColumns; i++) {
                for (int j = 0; j < numberOfColumns; j++) {

                    String blueStyle = "-fx-background-color: rgb( " + r + "," + g + "," + b + ");" + "-fx-border-color: black;";

                    Field pane = new Field();

                    pane.setPrefWidth(1000.0 / numberOfColumns);
                    pane.setPrefHeight(1000.0 / numberOfColumns);

                    if (Game.getPathFields().get(p).getIdOfField() >= 0) {
                        if (Game.getPathFields().get(p).getxFieldCoordinate() == i && Game.getPathFields().get(p).getyFieldCoordinate() == j) {
                            pane.setStyle(blueStyle);
                            pane.setxFieldCoordinate(j);
                            pane.setyFieldCoordinate(i);
                            pane.setIdOfField(p);
                            pane.setFieldNumber(i * numberOfColumns + j + 1);
                            currentPathMatrix[i][j] = pane;
                            figurePathGridPane.add(pane, j, i);
                        }

                    }
                }
            }
            r -= 2;
            g -= 5;
            b -= 3;
        }

        for(int i = 0; i < numberOfColumns; i++) {
            for(int j = 0; j < numberOfColumns; j++) {
                Label labelfieldNumber = new Label();
                labelfieldNumber.setText(String.valueOf(i * numberOfColumns + j + 1));
                figurePathGridPane.add(labelfieldNumber, j, i);
            }
        }
    }

    private static void setImage(String imageSource, ImageView imageView) {
        File file = new File(imageSource);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    private void colorCurrentPath() {
        int lastReachedId = selectedFigure.getCurrentFieldId();
        System.out.println(lastReachedId);
        String backgroundColor = "-fx-background-color: blue;";
        for(int p = 0; p < lastReachedId; p++) {
            for (int i = 0; i < Game.getPathFields().size(); i++) {
                for (int j = 0; j < Game.getPathFields().size(); j++) {
                    Field field = new Field();
                    if (Game.getPathFields().get(p).getIdOfField() >= 0 && Game.getPathFields().get(p).getxFieldCoordinate() == i && Game.getPathFields().get(p).getyFieldCoordinate() == j) {
                        field.setStyle(backgroundColor);
                        final int iFinal = i;
                        final int jFinal = j;
                        Platform.runLater(() -> {
                            figurePathGridPane.add(field, jFinal, iFinal);
                            Label labelfieldNumber = new Label();
                            labelfieldNumber.setText(String.valueOf(iFinal * Game.numberOfColumns + jFinal + 1));
                            figurePathGridPane.add(labelfieldNumber, jFinal, iFinal);
                        });
                    }

                }
            }
        }
    }


}
