package com.example.gameTime;

import com.example.diamondcircle.DiamondCircleController;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class GameTime extends Thread {
    Label label;
    String gameTimeMessage = "Vrjeme trajanja igre: ";
    static int i = 0;

    public GameTime(Label label) {
        this.label = label;
    }

    @Override
    public void run() {
        while (isAlive()) {
            synchronized (DiamondCircleController.matrixOfImageViews) {
                try {
                    if (!DiamondCircleController.pauseStart) {
                        DiamondCircleController.matrixOfImageViews.wait();
                    }
                    Platform.runLater(() -> {
                        label.setText(gameTimeMessage + i++ + "s");
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

    }

