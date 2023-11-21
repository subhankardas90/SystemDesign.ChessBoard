package in.subhankar.design.chess.piece;

import in.subhankar.design.chess.board.Board;
import in.subhankar.design.chess.board.Cell;
import in.subhankar.design.chess.board.Position;
import in.subhankar.design.chess.constant.Color;
import in.subhankar.design.chess.exception.IllegalCellException;

import static java.lang.Math.abs;
import static java.lang.Math.getExponent;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color);
    }

    @Override
    public boolean isValid(Cell destination, Board board) throws IllegalCellException {
        Position start = getCell().getPosition();
        Position end = destination.getPosition();

        return (travelsVertically(start, end) && ! jumpsOverPieceVertically(start, end, board)) ||
                (travelsHorizontally(start, end) && ! jumpsOverPieceHorizontally(start, end, board)) ||
                (travelsDiagonal(start, end) && ! jumpsOverPieceDiagonally(start, end, board));
    }

    @Override
    public boolean isKillValid(Cell destination, Board board) throws IllegalCellException {
        return isValid(destination, board);
    }

    private boolean travelsDiagonal(Position start, Position end) {
        return abs(start.getRow() - end.getRow()) == abs(start.getColumn() - end.getColumn());
    }

    private boolean jumpsOverPieceDiagonally(Position start, Position end, Board board) throws IllegalCellException {
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

    private boolean travelsVertically(final Position fromPosition,
                                   final Position toPosition) {
        return fromPosition.getColumn() == toPosition.getColumn();
    }

    private boolean jumpsOverPieceVertically(Position start, Position end, Board board) throws IllegalCellException {
        final char column = start.getColumn();
        final int startRow = start.getRow();
        final int endRow = end.getRow();
        final int direction = (startRow < endRow) ? 1 : -1;

        for(int i = startRow + direction; i != endRow; i += direction){
            if(board.getCell(Position.of(column, i)).getPiece().isPresent()) return true;
        }

        return false;
    }

    private boolean travelsHorizontally(final Position fromPosition,
                                     final Position toPosition) {
        return fromPosition.getRow() == toPosition.getRow();


    }

    private boolean jumpsOverPieceHorizontally(Position start, Position end, Board board) throws IllegalCellException {
        final int row = start.getRow();
        final char startCol = start.getColumn();
        final char endCol = end.getColumn();
        final int direction = (startCol < endCol) ? 1: -1;

        for(char i = (char)(startCol + direction); i != endCol; i += direction){
            if(board.getCell(Position.of(i, row)).getPiece().isPresent()) return true;
        }

        return false;
    }
}
