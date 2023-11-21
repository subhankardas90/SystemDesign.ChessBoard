package main.com.java.subhankar.chess.piece;

import main.com.java.subhankar.chess.board.Board;
import main.com.java.subhankar.chess.board.Cell;
import main.com.java.subhankar.chess.board.Position;
import main.com.java.subhankar.chess.constant.Color;
import main.com.java.subhankar.chess.exception.IllegalCellException;

public class Rook extends Piece{
    public Rook(final Color color) {
        super(color);
    }

    @Override
    public boolean isValid(final Cell destination, final Board board) throws IllegalCellException {
        final Position fromPosition = getCell().getPosition();
        final Position toPosition = destination.getPosition();

        return isVerticalMove(fromPosition, toPosition, board) ||
                isHorizontalMove(fromPosition, toPosition, board);
    }

    @Override
    public boolean isKillValid(Cell destination, Board board) throws IllegalCellException {
        return isValid(destination, board);
    }

    private boolean isVerticalMove(final Position fromPosition,
                                   final Position toPosition,
                                   final Board board) throws IllegalCellException {
        if(fromPosition.getColumn() != toPosition.getColumn()) return false;

        final char column = fromPosition.getColumn();
        final int startRow = fromPosition.getRow();
        final int endRow = toPosition.getRow();
        final int direction = (startRow < endRow) ? 1 : -1;

        for(int i = startRow + direction; i != endRow; i += direction){
            if(board.getCell(Position.of(column, i)).getPiece().isPresent()) return false;
        }

        return true;
    }

    private boolean isHorizontalMove(final Position fromPosition,
                                     final Position toPosition,
                                     final Board board) throws IllegalCellException {
        if(fromPosition.getRow() != toPosition.getRow()) return false;

        final int row = fromPosition.getRow();
        final char startCol = fromPosition.getColumn();
        final char endCol = toPosition.getColumn();
        final int direction = (startCol < endCol) ? 1: -1;

        for(char i = (char)(startCol + direction); i != endCol; i += direction){
            if(board.getCell(Position.of(i, row)).getPiece().isPresent()) return false;
        }

        return true;
    }
}
