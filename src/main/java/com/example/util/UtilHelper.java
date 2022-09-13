package com.example.util;
import java.text.SimpleDateFormat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.Level;

public abstract class UtilHelper {

    private static final String MATRIX_PROPERTIES = "src/main/resources/matrix.properties";

    private static final String DATE_FORMAT = "dd-MM-yy-hh-mm-ss";

    private static final String DIRECTORY = "Logs/log";

    private static final String FILE_TYPE = ".log";

    private static final String MATRIX_10X10 = "matrix10";

    private static final String MATRIX_9X9 = "matrix9";

    private static final String MATRIX_8X8 = "matrix8";

    private static final String MATRIX_7X7 = "matrix7";

    public static String readMatrix(Integer numOfColumns) {

        try {
            Properties p = new Properties();
            p.load(new FileInputStream(MATRIX_PROPERTIES));

            switch (numOfColumns) {
                case 10:
                    return p.getProperty(MATRIX_10X10);
                case 9:
                    return p.getProperty(MATRIX_9X9);
                case 8:
                    return p.getProperty(MATRIX_8X8);
                case 7:
                    return p.getProperty(MATRIX_7X7);
                default: logExceptions(UtilHelper.class, new Exception());
            }

        } catch (Exception e) {
            logExceptions(UtilHelper.class, e);
        }

        return null;
    }

    public static void logExceptions(Class c, Exception e) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Logger logger = Logger.getLogger(c.getName());
        try {
            Handler handler = new FileHandler(DIRECTORY + dateFormat.format(new Date()) + FILE_TYPE);
            logger.addHandler(handler);
            logger.log(Level.SEVERE, e.getMessage());
        } catch(IOException ex) {
            ex.printStackTrace();
        }

    }
}
