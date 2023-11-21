package main.com.java.subhankar.chess.exception;

public class IllegalMoveException extends RuntimeException{

    public IllegalMoveException(final String message){
        super(message);
    }
}
