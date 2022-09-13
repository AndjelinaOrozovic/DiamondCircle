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

    private final Color color;

    private String colorReadable;

    public int idFigure = 0;

    private static int staticId = 0;

    private String ownerName;

    protected int numberOfFieldsForMoving;

    private final String imageSource;

    private ImageView imageView;

    protected int numberOfDiamonds = 0;

    private List<Integer> currentPath = new ArrayList<>();

    private int currentFieldId;

    private int oldFiledId = -1;

    private boolean isFinished = false;

    private String isMakeItToTheEnd = "ne";

    private int startTime = -1;

    private int endTime = -1;

    private static final String ORANGE = "Narandzasta";

    private static final String RED = "Crvena";

    private static final String GREEN = "Zelena";

    private static final String BLUE = "Plava";

    private static final String NO_PATH = "Nema putanje";

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
                colorReadable = RED;
                break;
            case "0xff0000ff":
                colorReadable = ORANGE;
                break;
            case "0x008000ff":
                colorReadable = GREEN;
                break;
            case "0x0000ffff":
                colorReadable = BLUE;
                break;
        }
    }

    private String getPathLikeString() {
        StringBuilder path = new StringBuilder();
        if(currentPath.size() > 0) {
        for(int i = 0; i < currentPath.size() - 1; i++) {
            path.append(currentPath.get(i)).append("-");
        }
            path.append(currentPath.get(currentPath.size() - 1));
        } else path = new StringBuilder(NO_PATH);
        return "(" + path + ")";
    }

    public Color getColor() {
        return color;
    }

    public void setNumberOfDiamonds(int numberOfDiamonds) {
        this.numberOfDiamonds = numberOfDiamonds;
    }

    public ImageView getImageView() {
        return imageView;
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

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getImageSource() {
        return imageSource;
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
    }

}
