package com.example.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.util.UtilHelper.logExceptions;

public abstract class ResultsUtil {

    private static final String RESULTS_PATH = "." + File.separator + "Results" + File.separator;

    private static final String DATE_FORMAT = "dd.MM.yy_hh_mm_ss";

    private static final String FILE_TYPE = ".txt";

    private static final String FILE_PREFIX = "IGRA_";

    public static File[] getResultsFiles() {
        File resultsDirectory = new File(RESULTS_PATH);
        if(!resultsDirectory.exists()) {
            resultsDirectory.mkdir();
        }
        return resultsDirectory.listFiles();
    }

    public static void serializeResults(String gameResults) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        String fileName = FILE_PREFIX + simpleDateFormat.format(new Date()) + FILE_TYPE;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(RESULTS_PATH + fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(gameResults);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            logExceptions(ResultsUtil.class, e);
        }
    }

    public static String deserializeResults(String fileWithResults) {
        String gameResults = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(RESULTS_PATH + fileWithResults);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            gameResults = (String) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            logExceptions(ResultsUtil.class, e);
        }

        return gameResults;
    }

}
