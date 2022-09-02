package com.example.player;

import com.example.figures.Figure;

import java.util.List;

public class Player {

    private String name;

    private static int currentPlayerId = 0;

    private boolean isPlaying = true;

    List<Figure> figures;

    private int currentFigureID;

    private Figure currentFigure;

    public Player(String name, List<Figure> figures) {
        this.name = name;
        this.figures = figures;
        for(int i = 0; i < figures.size(); i++) {
            figures.get(i).setOwnerName(name);
        }
        currentFigure = figures.get(0);
        currentFigureID = 0;
        currentPlayerId++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
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

    public int getCurrentFigureID() {
        return currentFigureID;
    }

    public void setCurrentFigureID(int currentFigureID) {
        this.currentFigureID = currentFigureID;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public Figure getCurrentFigure() {
        return currentFigure;
    }

    public void setCurrentFigure(Figure currentFigure) {
        this.currentFigure = currentFigure;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' + ", currentFigureId: " + currentFigureID +
                ", figures=" + figures +
                '}' + '\n';
    }
}