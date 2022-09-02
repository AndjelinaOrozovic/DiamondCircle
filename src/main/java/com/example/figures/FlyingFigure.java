package com.example.figures;

import com.example.diamondcircle.DiamondCircleController;
import javafx.scene.paint.Color;

public class FlyingFigure extends Figure {

    public FlyingFigure(Color color, String imageSource) {
        super(color, imageSource);
        numberOfFieldsForMoving = 1;
        canFallIntoHolle = false;
    }

    @Override
    public int sumOfFieldsForMoving(int numberOfFieldsForMoving) {
        return numberOfFieldsForMoving + numberOfDiamonds;
    }

}
