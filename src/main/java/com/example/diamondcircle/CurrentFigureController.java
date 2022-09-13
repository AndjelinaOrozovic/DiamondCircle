package com.example.diamondcircle;

import com.example.game.Field;
import com.example.game.Game;
import com.example.figures.Figure;
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

import static com.example.util.UtilHelper.logExceptions;


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

    private static final int R_CONSTANT_SUBTRACT = 2;

    private static final int G_CONSTANT_SUBTRACT = 5;

    private static final int B_CONSTANT_SUBTRACT = 3;

    public static Field[][] currentPathMatrix = new Field[][]{};

    private static final String FIGURE_IN_GAME = "Figurica je provela u igri: ";

    private static final String ID = ", id: ";

    private static final String SECONDS = "s";

    private static final String FIGURE_NOT_IN_GAME = "Figurica jos nije zapocela igru!";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setImage(selectedFigure.getImageSource(), figureImage);
            figureName.setText(selectedFigure.getClass().getSimpleName() + ID + selectedFigure.idFigure);
            colorCurrentPath();
            if(selectedFigure.calculateTime() != -1) {
                figureTime.setText(FIGURE_IN_GAME + selectedFigure.calculateTime() + SECONDS);
            } else {
                figureTime.setText(FIGURE_NOT_IN_GAME);
            }
            startColoring();
        } catch (Exception e) {
            logExceptions(CurrentFigureController.class, e);
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
            r -= R_CONSTANT_SUBTRACT;
            g -= G_CONSTANT_SUBTRACT;
            b -= B_CONSTANT_SUBTRACT;
        }

        for(int i = 0; i < numberOfColumns; i++) {
            for(int j = 0; j < numberOfColumns; j++) {
                Label labelFieldNumber = new Label();
                labelFieldNumber.setText(String.valueOf(i * numberOfColumns + j + 1));
                figurePathGridPane.add(labelFieldNumber, j, i);
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
        String backgroundColor = "-fx-background-color: blue;"  + "-fx-border-color: black;";
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
                            Label labelFieldNumber = new Label();
                            labelFieldNumber.setText(String.valueOf(iFinal * Game.numberOfColumns + jFinal + 1));
                            figurePathGridPane.add(labelFieldNumber, jFinal, iFinal);
                        });
                    }

                }
            }
        }
    }

}
