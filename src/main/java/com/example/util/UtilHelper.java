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

    private static final String matrixProperties = "src/main/resources/matrix.properties";

    public static String readMatrix(Integer numOfColumns) {

        try {
            Properties p = new Properties();
            p.load(new FileInputStream(matrixProperties));

            switch (numOfColumns) {
                case 10:
                    return p.getProperty("matrix10");
                case 9:
                    return p.getProperty("matrix9");
                case 8:
                    return p.getProperty("matrix8");
                case 7:
                    return p.getProperty("matrix7");
                default: throw new Exception("Error");
            }

        } catch (Exception e) {
            logExceptions(UtilHelper.class, e);
        }

        return null;
    }

    public static void logExceptions(Class c, Exception e) { //dodati kod svih try-catch blokova

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
        Logger logger = Logger.getLogger(c.getName());
        try {
            Handler handler = new FileHandler("Logs/log" + dateFormat.format(new Date()) + ".log");
            logger.addHandler(handler);
            logger.log(Level.SEVERE, e.getMessage());
        } catch(IOException ex) {
            ex.printStackTrace();
        }

    }
}
