package com.example.ghostFigure;

import com.example.diamondcircle.DiamondCircleController;
import com.example.game.Field;
import com.example.game.Game;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static com.example.diamondcircle.DiamondCircleController.matrixOfImageViews;

public class GhostFigure extends Thread {

    private int counter = 0;

    private static final String diamondGhostPathName = "src/main/resources/diamondGhost.png";

    @Override
    public void run() {

        while (!Game.gameIsFinished) {
            synchronized (matrixOfImageViews) {

                try {
                    if (!DiamondCircleController.pauseStart) {
                        matrixOfImageViews.wait();
                    }

                    if(counter > 0) {
                        for (int p = 0; p < Game.getPathFields().size(); p++) {
                            for (int i = 0; i < Game.numberOfColumns; i++) {
                                for (int j = 0; j < Game.numberOfColumns; j++) {
                                    if (Game.getPathFields().get(p).getIdOfField() >= 0 && Game.getPathFields().get(p).getxFieldCoordinate() == i && Game.getPathFields().get(p).getyFieldCoordinate() == j && matrixOfImageViews[i][j].isHasDiamond()) {
                                        final int firstCord = i;
                                        final int secCoordinate = j;
                                        Platform.runLater(() -> {
                                            matrixOfImageViews[firstCord][secCoordinate].getChildren().clear();
                                            matrixOfImageViews[firstCord][secCoordinate].setHasDiamond(false);
                                        });
                                    }
                                }
                            }
                        }
                    }


                    Random random = new Random();
                    int numberOfDiamonds = random.nextInt(Game.numberOfColumns - 1) + 2;
                    List<Integer> fieldsForDiamonds = new ArrayList<>();
                    for (int i = 0; i < numberOfDiamonds; i++) {
                        int id = random.nextInt(Game.getPathFields().size());
                        if (!fieldsForDiamonds.contains(id)) {
                            fieldsForDiamonds.add(id);
                        } else {
                            i--;
                        }
                    }

                    for (int p = 0; p < Game.getPathFields().size(); p++) {

                        if (fieldsForDiamonds.contains(p)) {
                            for (int i = 0; i < Game.numberOfColumns; i++) {
                                for (int j = 0; j < Game.numberOfColumns; j++) {
                                    if (Game.getPathFields().get(p).getIdOfField() >= 0 && Game.getPathFields().get(p).getxFieldCoordinate() == i && Game.getPathFields().get(p).getyFieldCoordinate() == j) {
                                        final int firstCoordinate = i;
                                        final int secondCoordinate = j;
                                        Platform.runLater(() -> {
                                            File file = new File(diamondGhostPathName);
                                            Image image = new Image(file.toURI().toString());
                                            ImageView iv = new ImageView(image);
                                            Field field = matrixOfImageViews[firstCoordinate][secondCoordinate];
                                            if(!field.isHasFigure()) {
                                                field.getChildren().add(iv);
                                                field.setHasDiamond(true);
                                                AnchorPane.setTopAnchor(iv, 1.0);
                                                AnchorPane.setRightAnchor(iv, 1.0);
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                    counter++;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
