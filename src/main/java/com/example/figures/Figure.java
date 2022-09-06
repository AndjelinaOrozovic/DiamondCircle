package com.example.figures;

import com.example.gameTime.GameTime;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Figure implements Serializable {

    private Color color;

    private String colorReadable;

    public int idFigure = 0;

    private static int staticId = 0;

    private String ownerName;

    protected int numberOfFieldsForMoving; //broj polja koje prelazi

    private String imageSource;

    private ImageView imageView;

    protected boolean canFallIntoHolle; // da li moze upasti u rupu

    protected boolean fellIntoHolle; // da li je upala u rupu

    protected int numberOfDiamonds = 0; // broj dijamanata koje je pokupila

    private List<Integer> currentPath = new ArrayList<>(); // trenutna putanja

    private int currentFieldId;

    private int oldFiledId = -1;

    private boolean isFinished = false;

    private String isMakeItToTheEnd = "ne";

    private int startTime = -1;

    private int endTime = -1;

    public Figure(Color color, String imageSource) {
        this.color = color;
        setColorReadable(color);
        this.imageSource = imageSource;
        File file = new File(imageSource);
        Image image = new Image(file.toURI().toString());
        this.imageView = new ImageView(image);
        this.currentFieldId = 0;
        idFigure = staticId++;
    }

    private void setColorReadable(Color color) {
        switch (color.toString()) {
            case "0xffa500ff":
                colorReadable = "Crvena";
                break;
            case "0xff0000ff":
                colorReadable = "Narandzasta";
                break;
            case "0x008000ff":
                colorReadable = "Zelena";
                break;
            case "0x0000ffff":
                colorReadable = "Plava";
                break;
        }
    }

    private String getPathLikeString() {
        String path = "";
        if(currentPath.size() > 0) {
        for(int i = 0; i < currentPath.size() - 1; i++) {
            path += currentPath.get(i) + "-";
        }
            path += currentPath.get(currentPath.size() - 1);
        } else path = "nema putanje";
        return "(" + path + ")";
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

    public List<Integer> getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(List<Integer> currentPath) {
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
        if(isFinished) {
            isMakeItToTheEnd = "da";
        }
    }

    public abstract int sumOfFieldsForMoving(int numberOfFields);

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void addToCurrentPath(Integer filedNumber) {
        currentPath.add(filedNumber);
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public int calculateTime() {
        if (endTime == -1 && startTime != -1) {
            return GameTime.i - startTime;
        } else if (endTime != -1 && startTime != -1) {
            return endTime - startTime;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "\tFigura " + idFigure + " (" + this.getClass().getSimpleName() + ", "
                + colorReadable + ") - predjeni put " + getPathLikeString() + " - stigla do cilja (" +
                isMakeItToTheEnd + ")";

//        return "\nFigure{" +
//                "color='" + color + '\'' +
//                ",idFigure=" + idFigure +
//                ", numberOfFieldsForMoving=" + numberOfFieldsForMoving +
//                ", currentFieldId=" + currentFieldId +
////                ", canFallIntoHolle=" + canFallIntoHolle +
////                ", fellIntoHolle=" + fellIntoHolle +
//                ", numberOfDiamonds=" + numberOfDiamonds +
//                ", ownerName=" + ownerName +
////                ", currentPath=" + currentPath +
//                ", imageSource=" + imageSource +
//                '}' + '\n';
    }

}
