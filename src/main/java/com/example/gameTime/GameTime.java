package com.example.gameTime;

import com.example.diamondcircle.DiamondCircleController;
import com.example.game.Game;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.io.Serializable;

public class GameTime extends Thread implements Serializable {
    private Label label;

    private static final String GAME_TIME_MESSAGE = "Vrjeme trajanja igre: ";

    public static final String GAME_TIME = "Ukupno vrijeme trajanja igre: ";

    public static int i = 0;

    public GameTime(Label label) {
        this.label = label;
    }

    @Override
    public void run() {
        while (!Game.gameIsFinished) {
            synchronized (DiamondCircleController.matrixOfImageViews) {
                try {
                    if (!DiamondCircleController.pauseStart) {
                        DiamondCircleController.matrixOfImageViews.wait();
                    }
                    Platform.runLater(() -> {
                        label.setText(GAME_TIME_MESSAGE + i++ + "s");
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String toString() {
        return GAME_TIME + i;
    }
}

