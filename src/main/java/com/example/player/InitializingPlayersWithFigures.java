package com.example.player;

import com.example.figures.Figure;
import com.example.figures.FlyingFigure;
import com.example.figures.PlainFigure;
import com.example.figures.SuperFastFigure;
import javafx.scene.paint.Color;
import java.util.*;

public abstract class InitializingPlayersWithFigures {

    private static final List<Player> LIST_OF_PLAYERS = new ArrayList<>();

    private static final String WALKING_PATH = "src/main/resources/walkingFigure";
    private static final String RUNNING_PATH = "src/main/resources/runningFigure";
    private static final String FLYING_PATH = "src/main/resources/flyingFigure";
    private static final String RED_FIGURE = "Red.png";
    private static final String ORANGE_FIGURE = "Orange.png";
    private static final String GREEN_FIGURE = "Green.png";
    private static final String BLUE_FIGURE = "Blue.png";

    private static final String[] PLAYER_NAMES = {"Marko", "Janko", "Maja", "Ana"};

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
            if ((Color.RED).equals(colors.get(i))) {
                for (int j = 0; j < 4; j++) {
                    int figureType = rand.nextInt(3);
                    if (figureType == 0) {
                        PlainFigure pf = new PlainFigure(colors.get(i), WALKING_PATH + RED_FIGURE);
                        figures.add(pf);
                    } else if (figureType == 1) {
                        SuperFastFigure sff = new SuperFastFigure(colors.get(i), RUNNING_PATH + RED_FIGURE);
                        figures.add(sff);
                    } else if (figureType == 2) {
                        FlyingFigure ff = new FlyingFigure(colors.get(i), FLYING_PATH + RED_FIGURE);
                        figures.add(ff);
                    }
                    if (!figureColorsOfOnePlayer.contains(figures.get(j).getColor())) {
                        figureColorsOfOnePlayer.add(figures.get(j).getColor());
                    }
                }

            } else if ((Color.ORANGE).equals(colors.get(i))) {
                for (int j = 0; j < 4; j++) {
                    int figureType = rand.nextInt(3);
                    if (figureType == 0) {
                        PlainFigure pf = new PlainFigure(colors.get(i), WALKING_PATH + ORANGE_FIGURE);
                        figures.add(pf);
                    } else if (figureType == 1) {
                        SuperFastFigure sff = new SuperFastFigure(colors.get(i), RUNNING_PATH + ORANGE_FIGURE);
                        figures.add(sff);
                    } else if (figureType == 2) {
                        FlyingFigure ff = new FlyingFigure(colors.get(i), FLYING_PATH + ORANGE_FIGURE);
                        figures.add(ff);
                    }
                    if (!figureColorsOfOnePlayer.contains(figures.get(j).getColor())) {
                        figureColorsOfOnePlayer.add(figures.get(j).getColor());
                    }
                }
            } else if ((Color.GREEN).equals(colors.get(i))) {
                for (int j = 0; j < 4; j++) {
                    int figureType = rand.nextInt(3);
                    if (figureType == 0) {
                        PlainFigure pf = new PlainFigure(colors.get(i), WALKING_PATH + GREEN_FIGURE);
                        figures.add(pf);
                    } else if (figureType == 1) {
                        SuperFastFigure sff = new SuperFastFigure(colors.get(i), RUNNING_PATH + GREEN_FIGURE);
                        figures.add(sff);
                    } else if (figureType == 2) {
                        FlyingFigure ff = new FlyingFigure(colors.get(i), FLYING_PATH + GREEN_FIGURE);
                        figures.add(ff);
                    }
                    if (!figureColorsOfOnePlayer.contains(figures.get(j).getColor())) {
                        figureColorsOfOnePlayer.add(figures.get(j).getColor());
                    }
                }
            } else if ((Color.BLUE).equals(colors.get(i))) {
                for (int j = 0; j < 4; j++) {
                    int figureType = rand.nextInt(3);
                    if (figureType == 0) {
                        PlainFigure pf = new PlainFigure(colors.get(i), WALKING_PATH + BLUE_FIGURE);
                        figures.add(pf);
                    } else if (figureType == 1) {
                        SuperFastFigure sff = new SuperFastFigure(colors.get(i), RUNNING_PATH + BLUE_FIGURE);
                        figures.add(sff);
                    } else if (figureType == 2) {
                        FlyingFigure ff = new FlyingFigure(colors.get(i), FLYING_PATH + BLUE_FIGURE);
                        figures.add(ff);
                    }
                    if (!figureColorsOfOnePlayer.contains(figures.get(j).getColor())) {
                        figureColorsOfOnePlayer.add(figures.get(j).getColor());
                    }
                }
            }
            Player player = new Player(PLAYER_NAMES[i], figures);
            LIST_OF_PLAYERS.add(player);

            if (listOfNames.contains(player.getName()) || figureColorsOfOnePlayer.size() > 1) {
                throw new Exception();
            } else {
                listOfNames.add(player.getName());
            }
        }
        Collections.shuffle(LIST_OF_PLAYERS);
        return LIST_OF_PLAYERS;
    }

}
