package com.example.playerThread;

import com.example.diamondcircle.DiamondCircleController;
import com.example.gameTime.GameTime;
import com.example.card.Card;
import com.example.figures.Figure;
import com.example.figures.FlyingFigure;
import com.example.game.Field;
import com.example.game.Game;
import com.example.player.Player;
import com.example.util.ResultsUtil;
import javafx.application.Platform;

import java.util.*;

import static com.example.diamondcircle.StartWindowController.diamondCircleController;
import static com.example.util.UtilHelper.logExceptions;

public class PlayerThread extends Thread {

    private List<Player> listOfPlayers = DiamondCircleController.listOfPlayers;

    private Player currentPlayer;

    private int numOfHoles = 0;

    @Override
    public void run() {

        while (!Game.gameIsFinished) {

            while (diamondCircleController.hasPlayersForPlaying()) {

                currentPlayer = getNextPlayer();

                diamondCircleController.setActivePlayer(currentPlayer);

                Card currentCard = diamondCircleController.getNextCard();

                Figure currentFigure = currentPlayer.getCurrentFigure();

                if(currentFigure != null && currentFigure.getStartTime() == -1) {
                    currentFigure.setStartTime(GameTime.i);
                }

                if (!currentPlayer.isPlaying()) {
                    continue;
                }

                if (currentCard.getNumberOfFields() != 0 && currentFigure != null) {

                    int numberForMoving = currentFigure.sumOfFieldsForMoving(currentCard.getNumberOfFields());

                    int numberOfDiamonds = 0;

                    boolean jump = false;

                    for (int p = 0; p < numberForMoving; p++) {
                        int currentFieldId = currentFigure.getCurrentFieldId();
                        synchronized (DiamondCircleController.matrixOfImageViews) {
                            try {
                                if (!DiamondCircleController.pauseStart) {
                                    DiamondCircleController.matrixOfImageViews.wait();
                                }

                                if (p == 0 && currentFigure != null) {
                                    diamondCircleController.setCardDescriptionLabel(currentPlayer, currentFigure, numberForMoving, currentFieldId, jump, numOfHoles);
                                }

                                if (currentFigure.getOldFiledId() >= 0 && p == 0) {
                                    Field oldField = diamondCircleController.getFieldByFieldId(currentFigure.getOldFiledId());
                                    oldField.setHasFigure(false);
                                    oldField.setFigureOnField(null);
                                }

                                    Field field = diamondCircleController.getFieldByFieldId(currentFieldId);
                                    currentFigure.addToCurrentPath(field.getFieldNumber());

                                    diamondCircleController.setFieldView(field, currentFigure);
                                    if (field.isHasDiamond()) {
                                        numberOfDiamonds++;
                                    }
                                    int nextFieldId = currentFieldId + 1;
                                    currentFigure.setCurrentField(nextFieldId);

                                    if (p == numberForMoving - 1) {
                                        if (field.isHasFigure()) {
                                            p--;
                                                jump = true;
                                            diamondCircleController.setCardDescriptionLabel(currentPlayer, currentFigure, numberForMoving, currentFieldId, jump, numOfHoles);
                                        } else {
                                            currentFigure.setOldFiledId(currentFieldId);
                                            currentFigure.setNumberOfDiamonds(numberOfDiamonds);
                                            field.setHasFigure(true);
                                            field.setFigureOnField(currentFigure);
                                        }
                                    }

                            } catch (Exception e) {
                                logExceptions(PlayerThread.class, e);
                            }
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            logExceptions(PlayerThread.class, e);
                        }
                        if (currentFieldId == Game.getPathFields().size() - 1) {
                            currentFigure.setEndTime(GameTime.i);
                            currentFigure.setFinished(true);
                            diamondCircleController.getFieldByFieldId(currentFieldId).setHasFigure(false);
                            currentPlayer.getNextFigure();
                            if (currentPlayer.getCurrentFigure() == null) {
                                currentPlayer.setPlaying(false);
                            }
                            diamondCircleController.clearFieldFromFigure(Game.getPathFields().size() - 1);
                            break;
                        }
                    }
                } else {
                    List<String> oldStyles = new ArrayList<>();
                    synchronized (DiamondCircleController.matrixOfImageViews) {
                        oldStyles = setHolesOnMatrix();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        logExceptions(PlayerThread.class, e);
                    }
                    synchronized (DiamondCircleController.matrixOfImageViews) {
                        numOfHoles = 0;
                        unsetHoles(oldStyles);
                    }
                }

            }

            if(!diamondCircleController.hasPlayersForPlaying()) {
                Game.gameIsFinished = true;
                Game g = new Game();
                ResultsUtil.serializeResults(String.valueOf(g));
                diamondCircleController.makeLabelsGray();

            }

        }
    }

    private List<String> styleFieldsForHoles(int numberOfHoles) {
        numOfHoles = numberOfHoles;
        List<Integer> fieldsForHoles = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numberOfHoles; i++) {
            int id = random.nextInt(Game.getPathFields().size());
            if (!fieldsForHoles.contains(id)) {
                fieldsForHoles.add(id);
            } else {
                i--;
            }
        }

        List<String> oldStyles = new ArrayList<>();
        for (int p = 0; p < Game.getPathFields().size(); p++) {
            if (fieldsForHoles.contains(p)) {
                Field field = diamondCircleController.getFieldByFieldId(p);
                String backgroundColor = "-fx-background-color: black;";
                String oldStyle = field.getStyle();
                oldStyles.add(oldStyle);
                Platform.runLater(() -> {
                    field.setStyle(backgroundColor);
                    diamondCircleController.setCardDescriptionLabel(currentPlayer, currentPlayer.getCurrentFigure(), 0, 0, false, numberOfHoles);
                });
                if (field.isHasFigure()) {
                    Figure figure = field.getFigureOnField();
                    if (!(figure instanceof FlyingFigure)) {
                        Platform.runLater(() ->
                                field.getChildren().clear());
                        Player player = null;
                        String playerName = figure.getOwnerName();
                        for (Player listOfPlayer : listOfPlayers) {
                            if (playerName.equals(listOfPlayer.getName())) {
                                player = listOfPlayer;
                            }
                        }
                        figure.setEndTime(GameTime.i);
                        player.getNextFigure();
                        field.setHasFigure(false);
                        field.setFigureOnField(null);
                    }

                }
                field.setHasHole(true);
            }
        }
        return oldStyles;
    }

    private List<String> setHolesOnMatrix() {
        final int min = 1;
        final int max = Game.getPathFields().size() / 2;
        int numberOfHoles = (new Random()).nextInt(max - min + 1) + min;
        return styleFieldsForHoles(numberOfHoles);
    }

    private void unsetHoles(List<String> oldStyles) {
        int i = 0;
        for (int p = 0; p < Game.getPathFields().size(); p++) {
            final List<String> oldStylesFinal = oldStyles;
            final int j = i;
            Field field = diamondCircleController.getFieldByFieldId(p);
            if (field.isHasHole() && j < oldStylesFinal.size()) {
                Platform.runLater(() -> {
                    field.setStyle(oldStylesFinal.get(j));
                });
                field.setHasHole(false);
                i++;
            }
        }
    }

    public Player getNextPlayer() {
        Player player = listOfPlayers.get(0);
        listOfPlayers.remove(0);
        listOfPlayers.add(player);
        return player;
    }

}
