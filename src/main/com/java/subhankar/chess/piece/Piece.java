package main.com.java.subhankar.chess.piece;

import main.com.java.subhankar.chess.board.Board;
import main.com.java.subhankar.chess.board.Cell;
import main.com.java.subhankar.chess.constant.Color;
import main.com.java.subhankar.chess.exception.IllegalCellException;

//This represents each piece in chess board
public abstract class Piece {

    private final Color color;
    private Cell cell;
    private Boolean isMoved;

    protected Piece(final Color color){
        this.color = color;
        isMoved = false;
    }

    public Color getColor() {
        return color;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public boolean isMoved() {
        return isMoved;
    }

    protected void setMoved(){
        this.isMoved = true;
    }

    public void moved(){ setMoved(); };
    public abstract boolean isValid(final Cell destination, final Board board) throws IllegalCellException;
    public abstract boolean isKillValid(final Cell destination, final Board board) throws IllegalCellException;
}
