package com.example.figures;

import javafx.scene.paint.Color;

public class PlainFigure extends Figure {

    public PlainFigure(Color color, String imageSource) {
        super(color, imageSource);
        numberOfFieldsForMoving = 1;
    }

    @Override
    public int sumOfFieldsForMoving(int numberOfFieldsForMoving) {
        return numberOfFieldsForMoving + numberOfDiamonds;
    }
}
