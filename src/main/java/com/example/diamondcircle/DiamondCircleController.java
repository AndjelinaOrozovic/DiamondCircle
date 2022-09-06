package com.example.diamondcircle;

import com.example.card.Card;
import com.example.card.SetOfCards;
import com.example.figures.Figure;
import com.example.game.Field;
import com.example.game.Game;
import com.example.gameTime.GameTime;
import com.example.ghostFigure.GhostFigure;
import com.example.player.InitializingPlayersWithFigures;
import com.example.player.Player;
import com.example.playerThread.PlayerThread;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.diamondcircle.StartWindowController.diamondCircleController;
import static javafx.scene.text.TextAlignment.CENTER;

public class DiamondCircleController implements Initializable {
    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private GridPane gameMatrix;

    @FXML
    private ListView<Figure> listView; //prepraviti public u privete ispravnije je

    public static Field[][] matrixOfImageViews = new Field[][]{};

    @FXML
    private ImageView cardImage;

    @FXML
    private Label gameTimeLabel;

    @FXML
    private Label cardDescription;

    @FXML
    private Button pauseStartButton;

    @FXML
    private Label numberOfGamesLabel;

    public static boolean pauseStart = false;

    private static int counter = 0;

    public static List<Player> listOfPlayers;

    private static SetOfCards setOfCards = new SetOfCards();

    private static final String cardSource = "src/main/resources/startGame.png";

    private static final String figureFxml = "current-figure-path.fxml";

    private static final int R_CONSTANT = 135;

    private static final int G_CONSTANT = 206;

    private static final int B_CONSTANT = 235;

    private static final String NUMBER_OF_GAMES_TEXT = "Trenutni broj odigranih igara: ";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setImage(cardSource, cardImage);
        startMainWindow(Game.numberOfPlayers, Game.numberOfColumns);
        gameTimeLabel.setText("Vrijeme trajanja igre: 0s");
        gameTimeLabel.setTextAlignment(CENTER);
        gameTimeLabel.setWrapText(true);
        numberOfGamesLabel.setText(NUMBER_OF_GAMES_TEXT + Game.numberOfGames);
        try {
            listOfPlayers = InitializingPlayersWithFigures.initializePlayerWithFigures(Game.numberOfPlayers);
            Label[] labels = {label1, label2, label3, label4};
            for (int i = 0; i < listOfPlayers.size(); i++) {
                labels[i].setText(listOfPlayers.get(i).getName());
                labels[i].setTextFill(listOfPlayers.get(i).getFigures().get(0).getColor());
            }
            initializeListView();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startMainWindow(Integer numberOfPlayers, Integer numberOfColumns) {

        Game.readJSONFile(numberOfColumns);

        Label[] labels = {label1, label2, label3, label4};
        Color[] colors = {Color.RED, Color.ORANGE, Color.GREEN, Color.BLUE};

        for (Label label : labels) {
            label.setTextFill(Color.LIGHTGRAY);
            label.setFont(new Font("Arial", 20));
        }

        for (int i = 0; i < numberOfPlayers; i++) {
            labels[i].setTextFill(colors[i]);
        }

        startColoring(numberOfColumns);
    }

    public void startColoring(int numberOfColumns) {

        int r = R_CONSTANT;
        int g = G_CONSTANT;
        int b = B_CONSTANT;

        matrixOfImageViews = new Field[numberOfColumns][numberOfColumns];

        for (int p = 0; p < Game.getPathFields().size(); p++) {
            for (int i = 0; i < numberOfColumns; i++) {
                for (int j = 0; j < numberOfColumns; j++) {

                    String blueStyle = "-fx-background-color: rgb( " + r + "," + g + "," + b + ");" + "-fx-border-color: black;";

                    Field pane = new Field();

                    //Label labelId = new Label();

                    pane.setPrefWidth(1000.0 / numberOfColumns);
                    pane.setPrefHeight(1000.0 / numberOfColumns);

                    if (Game.getPathFields().get(p).getIdOfField() >= 0) {
                        if (Game.getPathFields().get(p).getxFieldCoordinate() == i && Game.getPathFields().get(p).getyFieldCoordinate() == j) {
                            //labelId.setText(String.valueOf(Game.getPathFields().get(p).getIdOfField()));
                            pane.setStyle(blueStyle);
                            pane.setxFieldCoordinate(j);
                            pane.setyFieldCoordinate(i);
                            pane.setIdOfField(p);
                            pane.setFieldNumber(i * numberOfColumns + j + 1);
                            matrixOfImageViews[i][j] = pane;
                            //matrixOfImageViews[i][j].getChildren().addAll(labelId);
                            gameMatrix.add(pane, j, i);
                            //gameMatrix.add(labelId, j, i);
                        }

                    }
                }
            }
            r -= 2;
            g -= 5;
            b -= 3;
        }

        for (int i = 0; i < numberOfColumns; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                Label labelfieldNumber = new Label();
                labelfieldNumber.setText(String.valueOf(i * numberOfColumns + j + 1));
                gameMatrix.add(labelfieldNumber, j, i);
            }
        }

    }

    public void initializeListView() {
        ObservableList<Figure> observableFigures = FXCollections.observableArrayList();
        for (Player p : listOfPlayers) {
            for (Figure f : p.getFigures()) {
                observableFigures.add(f);
            }
        }
        listView.setItems(observableFigures);
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Figure item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setBackground(Background.EMPTY);
                } else {
                    String color = item.getColor().toString();
                    switch (color) {
                        case "0xffa500ff":
                            setText("Orange: " + item.getClass().getSimpleName() + item.idFigure);
                            String orangeStyle = "-fx-background-color: LIGHTSALMON";
                            setStyle(orangeStyle);
                            break;
                        case "0xff0000ff":
                            setText("Red: " + item.getClass().getSimpleName() + item.idFigure);
                            String redStyle = "-fx-background-color: LIGHTCORAL";
                            setStyle(redStyle);
                            break;
                        case "0x008000ff":
                            setText("Green: " + item.getClass().getSimpleName() + item.idFigure);
                            String lightStyle = "-fx-background-color: LIGHTGREEN";
                            setStyle(lightStyle);
                            break;
                        case "0x0000ffff":
                            setText("Blue: " + item.getClass().getSimpleName() + item.idFigure);
                            String blueStyle = "-fx-background-color: LIGHTBLUE";
                            setStyle(blueStyle);
                            break;
                    }

                    setOnMouseClicked(e -> {
                        figureClicked(item);
                    });
                }
            }
        });
    }

    private Stage figureStage;

    private Parent root;

    private void figureClicked(Figure item) {
        try {
            Figure clickedFigure = item;
            CurrentFigureController.selectedFigure = clickedFigure;

            FXMLLoader loader = new FXMLLoader(getClass().getResource(figureFxml));
            root = loader.load();
            figureStage = new Stage();
            figureStage.setTitle("Predjeni put figure");
            figureStage.setScene(new Scene(root));
            figureStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startThreads(ActionEvent actionEvent) throws InterruptedException {

        pauseStart = !pauseStart;

        if (!pauseStart) {
            Platform.runLater(() -> {
                pauseStartButton.setText("Pokreni");
            });
        } else {
            Platform.runLater(() -> {
                pauseStartButton.setText("Zaustavi");
            });
        }

        if (counter == 0) {
            GameTime gameTime = new GameTime(gameTimeLabel);
            GhostFigure ghostFigure = new GhostFigure();
            PlayerThread playerThread = new PlayerThread();
            gameTime.start();
            ghostFigure.start();
            playerThread.start();
            counter++;
        }

        if (pauseStart) {
            synchronized (matrixOfImageViews) {
                matrixOfImageViews.notifyAll();
            }
        }

    }

    public static void setImage(String imageSource, ImageView imageView) {
        File file = new File(imageSource);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    public static boolean hasPlayersForPlaying() {
        boolean isPlaying = false;
        for (Player p : listOfPlayers) {
            if (p.isPlaying()) {
                isPlaying = true;
            }
        }
        return isPlaying;
    }

    public static Field getFieldByFieldId(int fieldId) {
        Field field = null;
        for (int i = 0; i < Game.numberOfColumns; i++) {
            for (int j = 0; j < Game.numberOfColumns; j++) {
                if (Game.getPathFields().get(fieldId).getIdOfField() >= 0 && Game.getPathFields().get(fieldId).getxFieldCoordinate() == i && Game.getPathFields().get(fieldId).getyFieldCoordinate() == j) {
                    field = matrixOfImageViews[i][j];
                }
            }
        }
        return field;
    }

    public void setCardImageView(Card nextCard, String cardSource) {
        cardSource = nextCard.getCardSource();  //dobavlja source karte
        setImage(cardSource, cardImage);
    }

    public Card getNextCard(String cardSource) {
        Card card = setOfCards.getListOfCards().get(0);
        setCardImageView(card, cardSource);
        setOfCards.getListOfCards().remove(0);
        setOfCards.getListOfCards().add(card);
        return card;
    }

    public void setFieldView(Field field, Figure currentFigure) {
        Platform.runLater(() -> {
            if (field.isHasDiamond()) {
                field.getChildren().clear();
                field.setHasDiamond(false);
            }
            ImageView iv = currentFigure.getImageView();
            field.getChildren().add(iv);
            AnchorPane.setBottomAnchor(iv, 1.0);
            AnchorPane.setRightAnchor(iv, 1.0);
        });
    }

    public void clearFieldFromFigure(int fieldId) {
        Platform.runLater(() -> {
            diamondCircleController.getFieldByFieldId(fieldId).getChildren().clear();
        });
    }

    public void setCardDescriptionLabel(Player currentPlayer, Figure currentFigure, int numberForMoving, int currentFieldId) {
        Field currentField;
        Field nextField;
        if (currentFieldId >= 24) {
            currentField = getFieldByFieldId(24);
            nextField = getFieldByFieldId(24);
        } else if((currentFieldId + numberForMoving) >= 24) {
            currentField = getFieldByFieldId(currentFieldId);
            nextField = getFieldByFieldId(24);
        }
        else {
            currentField = getFieldByFieldId(currentFieldId);
            nextField = getFieldByFieldId(currentFieldId + numberForMoving - 1);
        }
        Platform.runLater(() -> {
            cardDescription.setText("Na potezu je igrac: " + currentPlayer.getName() + ", figura: " + currentFigure.idFigure +
                    ", prelazi: " + numberForMoving + " polja, pomjera se sa pozicije: " + currentField.getFieldNumber() + ", na poziciju: " + nextField.getFieldNumber());
        });
    }

    public void setActivePlayer(Player currentPlayer) {

        Border activePlayer = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        Border inactivePlayer = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        if ((currentPlayer.getName()).equals(label1.getText())) {
            Platform.runLater(() ->
            {
                label1.setBorder(activePlayer);
                label2.setBorder(inactivePlayer);
                label3.setBorder(inactivePlayer);
                label4.setBorder(inactivePlayer);
            });
            if (!currentPlayer.isPlaying()) {
                label1.setTextFill(Color.GRAY);
            }
        } else if ((currentPlayer.getName()).equals(label2.getText())) {
            Platform.runLater(() ->
            {
                label2.setBorder(activePlayer);
                label1.setBorder(inactivePlayer);
                label3.setBorder(inactivePlayer);
                label4.setBorder(inactivePlayer);
            });
            if (!currentPlayer.isPlaying()) {
                label2.setTextFill(Color.GRAY);
            }
        } else if ((currentPlayer.getName()).equals(label3.getText())) {
            Platform.runLater(() ->
            {
                label3.setBorder(activePlayer);
                label2.setBorder(inactivePlayer);
                label1.setBorder(inactivePlayer);
                label4.setBorder(inactivePlayer);
            });
            if (!currentPlayer.isPlaying()) {
                label3.setTextFill(Color.GRAY);
            }
        } else if ((currentPlayer.getName()).equals(label4.getText())) {
            Platform.runLater(() ->
            {
                label4.setBorder(activePlayer);
                label2.setBorder(inactivePlayer);
                label3.setBorder(inactivePlayer);
                label1.setBorder(inactivePlayer);
            });
            if (!currentPlayer.isPlaying()) {
                label4.setTextFill(Color.GRAY);
            }
        }
    }

    public void makeLabelsGray() {
        Border inactivePlayer = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        Platform.runLater(() -> {
        label1.setTextFill(Color.GRAY);
        label1.setBorder(inactivePlayer);
        label2.setTextFill(Color.GRAY);
        label2.setBorder(inactivePlayer);
        label3.setTextFill(Color.GRAY);
        label3.setBorder(inactivePlayer);
        label4.setTextFill(Color.GRAY);
        label4.setBorder(inactivePlayer);
        cardDescription.setText("IGRA JE ZAVRSENA!");
        });
        for (int p = 0; p < Game.getPathFields().size(); p++) {
            for (int i = 0; i < Game.numberOfColumns; i++) {
                for (int j = 0; j < Game.numberOfColumns; j++) {
                    if (Game.getPathFields().get(p).getIdOfField() >= 0 && Game.getPathFields().get(p).getxFieldCoordinate() == i && Game.getPathFields().get(p).getyFieldCoordinate() == j && matrixOfImageViews[i][j].isHasDiamond()) {
                        final int firstCord = i;
                        final int secCoordinate = j;
                        Platform.runLater(() -> {
                            matrixOfImageViews[firstCord][secCoordinate].getChildren().clear();
                            matrixOfImageViews[firstCord][secCoordinate].setHasDiamond(false);
                        });
                    }
                }
            }
        }
    }

    public void updateNumberOfGames() {
        Platform.runLater(() -> numberOfGamesLabel.setText(NUMBER_OF_GAMES_TEXT + Game.numberOfGames));
    }

}


