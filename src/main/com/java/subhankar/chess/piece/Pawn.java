package main.com.java.subhankar.chess.piece;

import main.com.java.subhankar.chess.board.Board;
import main.com.java.subhankar.chess.board.Cell;
import main.com.java.subhankar.chess.board.Position;
import main.com.java.subhankar.chess.constant.Color;
import main.com.java.subhankar.chess.exception.IllegalCellException;

import static java.lang.Math.abs;
import static main.com.java.subhankar.chess.constant.Color.BLACK;

public class Pawn extends Piece{
    public Pawn(final Color color) {
        super(color);
    }

    @Override
    public boolean isValid(final Cell destination, final Board board) throws IllegalCellException {
        final Position start = getCell().getPosition();
        final Position end = destination.getPosition();
        final boolean doesPieceExistAtEndPosition = board.getCell(end).getPiece().isPresent();

        return isPawnMoving(start, end, doesPieceExistAtEndPosition) ||
                isPawnKilling(start, end, doesPieceExistAtEndPosition);
    }

    @Override
    public boolean isKillValid(Cell destination, Board board) throws IllegalCellException {
        final Position start = getCell().getPosition();
        final Position end = destination.getPosition();
        final boolean doesPieceExistAtEndPosition = board.getCell(end).getPiece().isPresent();

        return isPawnKilling(start, end, doesPieceExistAtEndPosition);
    }

    private boolean isPawnMoving(final Position start,
                                 final Position end,
                                 final boolean doesPieceExistAtEndPosition) {
        final int direction = getColor() == BLACK ? -1: 1;
        return start.getRow() + direction == end.getRow() &&
                start.getColumn() == end.getColumn() &&
                ! doesPieceExistAtEndPosition;
    }

    private boolean isPawnKilling(final Position start,
                                  final Position end,
                                  final boolean doesPieceExistAtEndPosition) {
        final int direction = getColor() == BLACK ? -1: 1;
        return start.getRow() + direction == end.getRow() &&
                abs(start.getColumn() - end.getColumn()) == 1 &&
                doesPieceExistAtEndPosition;
    }
}
