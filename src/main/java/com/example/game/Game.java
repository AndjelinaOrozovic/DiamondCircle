package com.example.game;

import com.example.UtilHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Game {

    public static Integer numberOfColumns;

    public static Integer numberOfPlayers;

    private static List<Field> pathFields = new ArrayList<Field>();


    public static List<Field> getPathFields() {
        return pathFields;
    }

    public static void readJSONFile(Integer numberOfColumns) {
        JSONParser jsonParser = new JSONParser();

        String matrixColumnNumbers = UtilHelper.readMatrix(numberOfColumns);

        if (matrixColumnNumbers != null) {
            try (FileReader reader = new FileReader(matrixColumnNumbers)) {
                Object obj = jsonParser.parse(reader);

                JSONArray fieldList = (JSONArray) obj;

                fieldList.forEach(field -> parseFieldObject((JSONObject) field));

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        } else {
            //TODO: logovati svoj exception
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
        pathFields.add(fieldConst);
    }

}
