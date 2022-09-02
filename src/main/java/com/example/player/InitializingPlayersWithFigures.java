package com.example.player;

import com.example.figures.*;
import javafx.scene.paint.Color;

import java.util.*;

public abstract class InitializingPlayersWithFigures {

    public static List<Player> listOfPlayers = new ArrayList<>();
    public static List<Player> initializePlayerWithFigures(int numOfPlayers) throws Exception {

        List<Color> colors = new ArrayList<>();
        List<String> listOfNames = new ArrayList<>();
        Random rand = new Random();

        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.ORANGE);
        Collections.shuffle(colors);

        for(int i = 0; i < numOfPlayers; i++) {
            List<Figure> figures = new ArrayList<>();
            if(colors.get(i) == Color.RED) {
                PlainFigure pf = new PlainFigure(colors.get(i), "src/main/resources/walkingFigureRed.png");
                figures.add(pf);
                SuperFastFigure sff = new SuperFastFigure(colors.get(i), "src/main/resources/runningFigureRed.png");
                figures.add(sff);
                FlyingFigure ff = new FlyingFigure(colors.get(i), "src/main/resources/flyingFigureRed.png");
                figures.add(ff);
            } else if(colors.get(i) == Color.ORANGE) {
                PlainFigure pf = new PlainFigure(colors.get(i), "src/main/resources/walkingFigureOrange.png");
                figures.add(pf);
                SuperFastFigure sff = new SuperFastFigure(colors.get(i), "src/main/resources/runningFigureOrange.png");
                figures.add(sff);
                FlyingFigure ff = new FlyingFigure(colors.get(i), "src/main/resources/flyingFigureOrange.png");
                figures.add(ff);
            }
            else if(colors.get(i) == Color.GREEN) {
                PlainFigure pf = new PlainFigure(colors.get(i), "src/main/resources/walkingFigureGreen.png");
                figures.add(pf);
                SuperFastFigure sff = new SuperFastFigure(colors.get(i), "src/main/resources/runningFigureGreen.png");
                figures.add(sff);
                FlyingFigure ff = new FlyingFigure(colors.get(i), "src/main/resources/flyingFigureGreen.png");
                figures.add(ff);
            } else if(colors.get(i) == Color.BLUE) {
                PlainFigure pf = new PlainFigure(colors.get(i), "src/main/resources/walkingFigureBlue.png");
                figures.add(pf);
                SuperFastFigure sff = new SuperFastFigure(colors.get(i), "src/main/resources/runningFigureBlue.png");
                figures.add(sff);
                FlyingFigure ff = new FlyingFigure(colors.get(i), "src/main/resources/flyingFigureBlue.png");
                figures.add(ff);
            }

            List<Figure> playerFigures = new ArrayList<>();
            List<Color> figureColorsOfOnePlayer = new ArrayList<>();

            for(int j = 0; j < 4; j++) {
                int id = rand.nextInt(3);
                playerFigures.add(figures.get(id));
                if(!figureColorsOfOnePlayer.contains(playerFigures.get(j).getColor())) {
                    figureColorsOfOnePlayer.add(playerFigures.get(j).getColor());
                }
            }

            Player player = new Player("Igrac" + i, playerFigures );
            listOfPlayers.add(player);


            if(listOfNames.contains(player.getName()) || figureColorsOfOnePlayer.size() > 1) {
                throw new Exception();
            } else {
                listOfNames.add(player.getName());
            }
        }

        Collections.shuffle(listOfPlayers);
        return listOfPlayers;

    }

}
