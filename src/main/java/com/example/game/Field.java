package com.example.game;

import com.example.figures.Figure;
import javafx.scene.layout.AnchorPane;

public class Field extends AnchorPane {

    private int xFieldCoordinate;

    private int yFieldCoordinate;

    private int idOfField = 0;

    private int fieldNumber;

    private boolean hasDiamond = false;

    private boolean hasFigure = false;

    private boolean hasHole = false;

    private Figure figureOnField = null;

    private boolean isEnd = false;

    public Field() {
    }

    public Field(int x, int y, int id) {
        xFieldCoordinate = x;
        yFieldCoordinate = y;
        idOfField = id;
        fieldNumber = x * Game.numberOfColumns + y + 1;
    }


    public boolean isHasDiamond() {
        return hasDiamond;
    }

    public void setHasDiamond(boolean hasDiamond) {
        this.hasDiamond = hasDiamond;
    }

    public int getxFieldCoordinate() {
        return xFieldCoordinate;
    }

    public void setxFieldCoordinate(int xFieldCoordinate) {
        this.xFieldCoordinate = xFieldCoordinate;
    }

    public int getyFieldCoordinate() {
        return yFieldCoordinate;
    }

    public void setyFieldCoordinate(int yFieldCoordinate) {
        this.yFieldCoordinate = yFieldCoordinate;
    }

    public int getIdOfField() {
        return idOfField;
    }

    public void setIdOfField(int idOfField) {
        this.idOfField = idOfField;
    }

    public boolean isHasFigure() {
        return hasFigure;
    }

    public void setHasFigure(boolean hasFigure) {
        this.hasFigure = hasFigure;
    }

    public Figure getFigureOnField() {
        return figureOnField;
    }

    public void setFigureOnField(Figure figureOnField) {
        this.figureOnField = figureOnField;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isHasHole() {
        return hasHole;
    }

    public void setHasHole(boolean hasHole) {
        this.hasHole = hasHole;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    @Override
    public String toString() {
        return "Field{" +
                ", idOfField=" + idOfField +
                ", hasFigure=" + hasFigure +
                ", hasDiamond=" + hasDiamond +
                ", figure=" + figureOnField +
                '}';
    }

}
