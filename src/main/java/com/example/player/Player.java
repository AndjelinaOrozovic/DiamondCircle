package com.example.player;

import com.example.figures.Figure;

import java.io.Serializable;
import java.util.List;

public class Player implements Serializable {

    private final String name;

    private int currentPlayerId = 1;

    private static int playerIdStatic = 1;

    private boolean isPlaying = true;

    private final List<Figure> figures;

    private int currentFigureID;

    private Figure currentFigure;

    public Player(String name, List<Figure> figures) {
        this.name = name;
        this.figures = figures;
        for (Figure figure : figures) {
            figure.setOwnerName(name);
        }
        currentFigure = figures.get(0);
        currentFigureID = 0;
        currentPlayerId = playerIdStatic++;
    }

    public String getName() {
        return name;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public void getNextFigure() {
        currentFigureID++;
        if(currentFigureID > 3) {
            isPlaying = false;
            currentFigure = null;
            currentFigureID = -1;
            return;
        }
        currentFigure = figures.get(currentFigureID);
        currentFigure.setCurrentField(0);
        currentFigure.setOldFiledId(-1);
    }

    private String figuresListToString() {
        StringBuilder figuresList = new StringBuilder();
        for (Figure figure : figures) {
            figuresList.append(figure.toString()).append("\n");
        }
        return figuresList.toString();
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public Figure getCurrentFigure() {
        return currentFigure;
    }


    @Override
    public String toString() {
        return "Igrac " + currentPlayerId + " - " + name + '\n' + figuresListToString() + '\n';
    }
}
