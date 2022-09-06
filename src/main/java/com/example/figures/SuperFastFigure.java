package com.example.figures;

import javafx.scene.paint.Color;

public class SuperFastFigure extends Figure {

    public SuperFastFigure(Color color, String imageSource) {
        super(color, imageSource);
        numberOfFieldsForMoving = 2;
        canFallIntoHolle = true;
    }

    @Override
    public int sumOfFieldsForMoving(int numberOfFieldsForMoving) {
        return numberOfFieldsForMoving * 2 + numberOfDiamonds;
    }
}
