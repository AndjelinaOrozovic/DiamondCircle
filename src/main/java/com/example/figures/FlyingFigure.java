package com.example.figures;

import javafx.scene.paint.Color;

public class FlyingFigure extends Figure {

    public FlyingFigure(Color color, String imageSource) {
        super(color, imageSource);
        numberOfFieldsForMoving = 1;
    }

    @Override
    public int sumOfFieldsForMoving(int numberOfFieldsForMoving) {
        return numberOfFieldsForMoving + numberOfDiamonds;
    }

}
