package in.subhankar.design.chess.piece;

import in.subhankar.design.chess.board.Board;
import in.subhankar.design.chess.board.Cell;
import in.subhankar.design.chess.board.Position;
import in.subhankar.design.chess.constant.Color;
import in.subhankar.design.chess.exception.IllegalCellException;

import static java.lang.Math.abs;

public class Bishop extends Piece {
    public Bishop(Color color) {
        super(color);
    }

    @Override
    public boolean isValid(Cell destination, Board board) throws IllegalCellException {
        final Position start = getCell().getPosition();
        final Position end = destination.getPosition();

        if(!travelsDiagonal(start, end) || jumpsOverPiece(start, end, board)) return false;

        return true;
    }

    @Override
    public boolean isKillValid(Cell destination, Board board) throws IllegalCellException {
        return isValid(destination, board);
    }

    private boolean jumpsOverPiece(Position start, Position end, Board board) throws IllegalCellException {
        int rowIncrement = end.getRow() > start.getRow()? 1: -1;
        int columnIncrement = end.getColumn() > start.getColumn()? 1: -1;

        int currentRow = start.getRow() + rowIncrement;
        char currentColumn = (char)(start.getColumn() + columnIncrement);
        while(currentRow != end.getRow()){
            if(board.getCell(Position.of(currentColumn, currentRow)).getPiece().isPresent()) return true;
            currentRow += rowIncrement;
            currentColumn += columnIncrement;
        }

        return false;
    }

    private boolean travelsDiagonal(Position start, Position end) {
        return abs(start.getRow() - end.getRow()) == abs(start.getColumn() - end.getColumn());
    }
}
