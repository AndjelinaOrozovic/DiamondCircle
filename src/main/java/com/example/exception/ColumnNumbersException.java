package com.example.exception;

public class ColumnNumbersException extends Exception {

    private static final String ERROR_MESSAGE = "There was an error with number of columns.";

    public ColumnNumbersException() { super(ERROR_MESSAGE);}

    public ColumnNumbersException(String message) { super((message));}

}
