package com.example.game;

import com.example.diamondcircle.DiamondCircleController;
import com.example.exception.ColumnNumbersException;
import com.example.gameTime.GameTime;
import com.example.player.Player;
import com.example.util.UtilHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.util.UtilHelper.logExceptions;

public class Game implements Serializable {

    public static Integer numberOfColumns;

    public static Integer numberOfPlayers;

    public static boolean gameIsFinished = false;

    private static final List<Field> PATH_FIELDS = new ArrayList<>();

    private static final String GAME_TIME = "Ukupno vrijeme trajanja igre: ";

    private static final String SECONDS = "s";

    public static int numberOfGames = 0;

    public static List<Field> getPathFields() {
        return PATH_FIELDS;
    }

    public static void readJSONFile(Integer numberOfColumns) {
        JSONParser jsonParser = new JSONParser();

        String matrixColumnNumbers = UtilHelper.readMatrix(numberOfColumns);

        try {
            if (matrixColumnNumbers != null) {
                try (FileReader reader = new FileReader(matrixColumnNumbers)) {
                    Object obj = jsonParser.parse(reader);

                    JSONArray fieldList = (JSONArray) obj;

                    fieldList.forEach(field -> parseFieldObject((JSONObject) field));

                } catch (IOException | ParseException e) {
                    logExceptions(Game.class, e);
                }
            } else {
                throw new ColumnNumbersException();
            }
        } catch (ColumnNumbersException e) {
            logExceptions(Game.class, e);
        }
    }

    private static void parseFieldObject(JSONObject field) {
        Integer xCoordinate = Integer
                .parseInt(String.valueOf(field.get("x")));
        Integer yCoordinate = Integer
                .parseInt(String.valueOf(field.get("y")));
        Integer idOfField = Integer
                .parseInt(String.valueOf(field.get("id")));
        Field fieldConst = new Field(xCoordinate, yCoordinate, idOfField);
        PATH_FIELDS.add(fieldConst);
    }

    private static String playersToString() {
        StringBuilder playersList = new StringBuilder();
        DiamondCircleController.listOfPlayers.sort(Comparator.comparingInt(Player::getCurrentPlayerId));
        for (int i = 0; i < DiamondCircleController.listOfPlayers.size(); i++) {
            playersList.append(DiamondCircleController.listOfPlayers.get(i));
        }
        return playersList.toString();
    }

    private static String gameTimeToString() {
        return GAME_TIME + GameTime.i + SECONDS;
    }

    @Override
    public String toString() {
        return playersToString() + "\n" + gameTimeToString();
    }
}
