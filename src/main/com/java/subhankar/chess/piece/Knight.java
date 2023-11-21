package main.com.java.subhankar.chess.piece;

import main.com.java.subhankar.chess.board.Board;
import main.com.java.subhankar.chess.board.Cell;
import main.com.java.subhankar.chess.board.Position;
import main.com.java.subhankar.chess.constant.Color;
import main.com.java.subhankar.chess.exception.IllegalCellException;

import static java.lang.Math.abs;

public class Knight extends Piece{
    public Knight(Color color) {
        super(color);
    }

    @Override
    public boolean isValid(Cell destination, Board board) {
        final Position start = getCell().getPosition();
        final Position end = destination.getPosition();

        if(abs(end.getRow() - start.getRow()) == 2 && abs(end.getColumn() - start.getColumn()) == 1) return true;
        if(abs(end.getRow() - start.getRow()) == 1 && abs(end.getColumn() - start.getColumn()) == 2) return true;
        return false;
    }

    @Override
    public boolean isKillValid(Cell destination, Board board) throws IllegalCellException {
        return isValid(destination, board);
    }
}
