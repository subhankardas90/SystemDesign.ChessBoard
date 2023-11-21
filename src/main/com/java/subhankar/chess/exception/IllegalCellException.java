package main.com.java.subhankar.chess.exception;

public class IllegalCellException extends  RuntimeException{
    public IllegalCellException(final String message){
        super(message);
    }
}
