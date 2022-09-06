package com.example.player;

import com.example.figures.*;
import javafx.scene.paint.Color;

import java.util.*;

public abstract class InitializingPlayersWithFigures {

    private static List<Player> listOfPlayers = new ArrayList<>();

    private static final String walkingPath = "src/main/resources/walkingFigure";
    private static final String runningPath = "src/main/resources/runningFigure";
    private static final String flyingPath = "src/main/resources/flyingFigure";
    private static final String redFigure = "Red.png";
    private static final String orangeFigure = "Orange.png";
    private static final String greenFigure = "Green.png";
    private static final String blueFigure = "Blue.png";

    private static String[] playerNames = {"Marko", "Janko", "Maja", "Ana"};

    public static List<Player> initializePlayerWithFigures(int numOfPlayers) throws Exception {

        List<Color> colors = new ArrayList<>();
        List<String> listOfNames = new ArrayList<>();
        Random rand = new Random();

        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.ORANGE);
        Collections.shuffle(colors);

        for (int i = 0; i < numOfPlayers; i++) {
            List<Color> figureColorsOfOnePlayer = new ArrayList<>();
            List<Figure> figures = new ArrayList<>();
            if (colors.get(i) == Color.RED) {
                for (int j = 0; j < 4; j++) {
                    int figureType = rand.nextInt(3);
                    if (figureType == 0) {
                        PlainFigure pf = new PlainFigure(colors.get(i), walkingPath + redFigure);
                        figures.add(pf);
                    } else if (figureType == 1) {
                        SuperFastFigure sff = new SuperFastFigure(colors.get(i), runningPath + redFigure);
                        figures.add(sff);
                    } else if (figureType == 2) {
                        FlyingFigure ff = new FlyingFigure(colors.get(i), flyingPath + redFigure);
                        figures.add(ff);
                    }
                    if (!figureColorsOfOnePlayer.contains(figures.get(j).getColor())) {
                        figureColorsOfOnePlayer.add(figures.get(j).getColor());
                    }
                }

            } else if (colors.get(i) == Color.ORANGE) {
                for (int j = 0; j < 4; j++) {
                    int figureType = rand.nextInt(3);
                    if (figureType == 0) {
                        PlainFigure pf = new PlainFigure(colors.get(i), walkingPath + orangeFigure);
                        figures.add(pf);
                    } else if (figureType == 1) {
                        SuperFastFigure sff = new SuperFastFigure(colors.get(i), runningPath + orangeFigure);
                        figures.add(sff);
                    } else if (figureType == 2) {
                        FlyingFigure ff = new FlyingFigure(colors.get(i), flyingPath + orangeFigure);
                        figures.add(ff);
                    }
                    if (!figureColorsOfOnePlayer.contains(figures.get(j).getColor())) {
                        figureColorsOfOnePlayer.add(figures.get(j).getColor());
                    }
                }
            } else if (colors.get(i) == Color.GREEN) {
                for (int j = 0; j < 4; j++) {
                    int figureType = rand.nextInt(3);
                    if (figureType == 0) {
                        PlainFigure pf = new PlainFigure(colors.get(i), walkingPath + greenFigure);
                        figures.add(pf);
                    } else if (figureType == 1) {
                        SuperFastFigure sff = new SuperFastFigure(colors.get(i), runningPath + greenFigure);
                        figures.add(sff);
                    } else if (figureType == 2) {
                        FlyingFigure ff = new FlyingFigure(colors.get(i), flyingPath + greenFigure);
                        figures.add(ff);
                    }
                    if (!figureColorsOfOnePlayer.contains(figures.get(j).getColor())) {
                        figureColorsOfOnePlayer.add(figures.get(j).getColor());
                    }
                }
            } else if (colors.get(i) == Color.BLUE) {
                for (int j = 0; j < 4; j++) {
                    int figureType = rand.nextInt(3);
                    if (figureType == 0) {
                        PlainFigure pf = new PlainFigure(colors.get(i), walkingPath + blueFigure);
                        figures.add(pf);
                    } else if (figureType == 1) {
                        SuperFastFigure sff = new SuperFastFigure(colors.get(i), runningPath + blueFigure);
                        figures.add(sff);
                    } else if (figureType == 2) {
                        FlyingFigure ff = new FlyingFigure(colors.get(i), flyingPath + blueFigure);
                        figures.add(ff);
                    }
                    if (!figureColorsOfOnePlayer.contains(figures.get(j).getColor())) {
                        figureColorsOfOnePlayer.add(figures.get(j).getColor());
                    }
                }
            }
            Player player = new Player(playerNames[i], figures);
            listOfPlayers.add(player);

            if (listOfNames.contains(player.getName()) || figureColorsOfOnePlayer.size() > 1) {
                throw new Exception();
            } else {
                listOfNames.add(player.getName());
            }
        }
        Collections.shuffle(listOfPlayers);
        return listOfPlayers;
    }

}
