package com.example.figures;

import com.example.game.Field;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Figure {

    protected Color color;  //boja figure

    private String ownerName;

    protected int numberOfFieldsForMoving; //broj polja koje prelazi

    protected String imageSource;

    protected ImageView imageView;

    protected boolean canFallIntoHolle; // da li moze upasti u rupu

    protected boolean fellIntoHolle; // da li je upala u rupu

    protected int numberOfDiamonds = 0; // broj dijamanata koje je pokupila

    protected List<Field> currentPath = new ArrayList<>(); // trenutna putanja

    protected int currentFieldId;

    private int oldFiledId = -1;

    protected boolean isFinished = false;

    public Figure(Color color, String imageSource) {
        this.color = color;
        this.imageSource = imageSource;
        File file = new File(imageSource);
        Image image = new Image(file.toURI().toString());
        this.imageView = new ImageView(image);
        this.currentFieldId = 0;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isFellIntoHolle() {
        return fellIntoHolle;
    }

    public void setFellIntoHolle(boolean fellIntoHolle) {
        this.fellIntoHolle = fellIntoHolle;
    }

    public int getNumberOfDiamonds() {
        return numberOfDiamonds;
    }

    public void setNumberOfDiamonds(int numberOfDiamonds) {
        this.numberOfDiamonds = numberOfDiamonds;
    }

    public List<Field> getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(List<Field> currentPath) {
        this.currentPath = currentPath;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getCurrentFieldId() {
        return currentFieldId;
    }

    public void setCurrentField(int currentField) {
        this.currentFieldId = currentField;
    }

    public int getOldFiledId() {
        return oldFiledId;
    }

    public void setOldFiledId(int oldFiledId) {
        this.oldFiledId = oldFiledId;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public abstract int sumOfFieldsForMoving(int numberOfFields);

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public String toString() {
        return "\nFigure{" +
                "color='" + color + '\'' +
                ", numberOfFieldsForMoving=" + numberOfFieldsForMoving +
                ", currentFieldId=" + currentFieldId +
//                ", canFallIntoHolle=" + canFallIntoHolle +
//                ", fellIntoHolle=" + fellIntoHolle +
                ", numberOfDiamonds=" + numberOfDiamonds +
                ", ownerName=" + ownerName +
//                ", currentPath=" + currentPath +
                ", imageSource=" + imageSource +
                '}' + '\n';
    }

}
