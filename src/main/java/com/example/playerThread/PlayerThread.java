package com.example.playerThread;

import com.example.card.Card;
import com.example.diamondcircle.DiamondCircleController;
import com.example.figures.Figure;
import com.example.figures.FlyingFigure;
import com.example.game.Field;
import com.example.game.Game;
import com.example.player.Player;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.diamondcircle.StartWindowController.diamondCircleController;

public class PlayerThread extends Thread {

    public static String cardSource = "";
    List<Player> listOfPlayers = DiamondCircleController.listOfPlayers;

    private Player currentPlayer;

    @Override
    public void run() {

        while (isAlive()) { //zamjeniti uslov - dok igra nije zavrsena

            while (diamondCircleController.hasPlayersForPlaying()) {

                currentPlayer = getNextPlayer(); //uzmi sljedeceg igraca

                diamondCircleController.setActivePlayer(currentPlayer);

                Card currentCard = diamondCircleController.getNextCard(cardSource);  //izvuci kartu i postavi imageView

                Figure currentFigure = currentPlayer.getCurrentFigure(); // uzmi sljedecu figuricu

                if (!currentPlayer.isPlaying()) {
                    continue; //preskace onog koji je zavrsio
                }

                if (currentCard.getNumberOfFields() != 0 && currentFigure != null) { //ako nije specijalna karta i ima jos figurica

                    int numberForMoving = currentFigure.sumOfFieldsForMoving(currentCard.getNumberOfFields()); //izracunaj broj polja za prelazak

                    int numberOfDiamonds = 0; //inicijalizuj broj dijamanata svaki put kad se krene kretati na 0

                    for (int p = 0; p < numberForMoving; p++) { //prelazimo broj polja za prelazak
                        int currentFieldId = currentFigure.getCurrentFieldId(); //uzmi trenutni id polja
                        synchronized (DiamondCircleController.matrixOfImageViews) {
                            try {
                                if (!DiamondCircleController.pauseStart) {
                                    DiamondCircleController.matrixOfImageViews.wait(); //kod za pauzu
                                }

                                if (p == 0) { //samo kada figura zapocne kretanje azuriraj opis karte
                                    diamondCircleController.setCardDescriptionLabel(currentPlayer, numberForMoving, currentFieldId);
                                }

                                if (currentFigure.getOldFiledId() >= 0 && p == 0) { //staro polje na kom je stajala figurica
                                    Field oldField = diamondCircleController.getFieldByFieldId(currentFigure.getOldFiledId());
                                    oldField.setHasFigure(false);
                                    oldField.setFigureOnField(null);
                                }

                                if (currentFieldId >= Game.getPathFields().size()) { //ako je doslo do zadnjeg polja
                                    currentPlayer.getNextFigure();                  //uzmi sljedecu figuricu
                                    if (currentPlayer.getCurrentFigure() == null) { //ako je figurica null to znaci da ih nema vise
                                        currentPlayer.setPlaying(false);           // zavrsi igru za tog igraca
                                    }
                                    diamondCircleController.clearFieldFromFigure(Game.getPathFields().size() - 1); //ocisti zadnje polje od figurice i nastavi dalje
                                    break;
                                } else {

                                    Field field = diamondCircleController.getFieldByFieldId(currentFieldId); //ako nije zadnje polje dohvati ga

                                    diamondCircleController.setFieldView(field, currentFigure); //postavi figuricu na njega
                                    if (field.isHasDiamond()) { //ako ima dijamant pokupi ga
                                        numberOfDiamonds++;
                                    }
                                    int nextFieldId = currentFieldId + 1; //izracunaj sljedeci id fielda
                                    currentFigure.setCurrentField(nextFieldId); //postavi sljedeci id u figuricu da zna na koje sljedece polje da ode

                                    if (p == numberForMoving - 1) { //ako je kraj poteza
                                        if (field.isHasFigure()) { //i figuricu treba da stane na polje na kom vec postoji figurica, uradi jos jedan potez
                                            p--;
                                        } else { //ako je polje slobodno
                                            currentFigure.setOldFiledId(currentFieldId);
                                            currentFigure.setNumberOfDiamonds(numberOfDiamonds);  //postavi broj dijamanata za sljedeci potez
                                            field.setHasFigure(true); //postavi to polje da ima figuricu
                                            field.setFigureOnField(currentFigure); //postavi i figuricu - kasnije zbog brisanja sa rupama
                                        }
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (currentFieldId == Game.getPathFields().size() - 1) {
                            currentPlayer.getNextFigure();                  //uzmi sljedecu figuricu
                            if (currentPlayer.getCurrentFigure() == null) { //ako je figurica null to znaci da ih nema vise
                                currentPlayer.setPlaying(false);           // zavrsi igru za tog igraca
                            }
                            diamondCircleController.clearFieldFromFigure(Game.getPathFields().size() - 1); //ocisti zadnje polje od figurice i nastavi dalje
                            break;
                        }
                    }
                } else {
                    System.out.println("Izvucena specijalna karta!");
                    List<String> oldStyles = new ArrayList<>();
                    synchronized (DiamondCircleController.matrixOfImageViews) {
                        oldStyles = setHolesOnMatrix();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (DiamondCircleController.matrixOfImageViews) {
                        unsetHoles(oldStyles);
                    }
                }

            }

        }
    }

    private List<String> styleFieldsForHoles(int numberOfHoles) {
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
                final int fieldId = p;
                Field field = diamondCircleController.getFieldByFieldId(fieldId);
                String backgroundColor = "-fx-background-color: black;";
                String oldStyle = field.getStyle();
                oldStyles.add(oldStyle);
                Platform.runLater(() -> {
                    field.setStyle(backgroundColor);
                });
                if (field.isHasFigure()) {
                    Figure figure = field.getFigureOnField();
                    if (!(figure instanceof FlyingFigure)) {
                        Platform.runLater(() ->
                                field.getChildren().clear());
                        // if (!(figure instanceof FlyingFigure)) {
                        Player player = null;
                        String playerName = figure.getOwnerName();
                        for (int s = 0; s < listOfPlayers.size(); s++) {
                            if (playerName == listOfPlayers.get(s).getName()) {
                                player = listOfPlayers.get(s);
                            }
                        }
                        player.getNextFigure();
                        //   }
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
        System.out.println("Broj rupa:" + numberOfHoles);
        return styleFieldsForHoles(numberOfHoles);
    }

    private void unsetHoles(List<String> oldStyles) {
        int i = 0;
        for (int p = 0; p < Game.getPathFields().size(); p++) {
            final int fieldId = p;
            final List<String> oldStylesFinal = oldStyles;
            final int j = i;
            Field field = diamondCircleController.getFieldByFieldId(fieldId);
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
