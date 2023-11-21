package in.subhankar.design.chess.piece;

import in.subhankar.design.chess.board.Board;
import in.subhankar.design.chess.board.Cell;
import in.subhankar.design.chess.board.Position;
import in.subhankar.design.chess.constant.Color;
import in.subhankar.design.chess.exception.IllegalCellException;

public class King extends Piece {

    private boolean isCastlingDone;

    public King(Color color) {
        super(color);
        isCastlingDone = false;
    }

    @Override
    public boolean isValid(Cell destination, Board board) throws IllegalCellException {
        final Position start = getCell().getPosition();
        final Position end = destination.getPosition();

        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                if(end.getRow() == start.getRow() + i && end.getColumn() == start.getColumn() + j) return true;
            }
        }

        return isCastlingMove(destination, board);
    }

    public boolean isCastlingMove(Cell destination, Board board) throws IllegalCellException {
        if(isCastlingDone) return false;
        if(isMoved()) return false;
        if(!destination.getPiece().isPresent()) return false;
        if(!(destination.getPiece().get() instanceof Rook)) return false;
        if(destination.getPiece().get().getColor() != getColor()) return false;
        if(destination.getPiece().get().isMoved()) return false;

        int moveDirection = (destination.getPosition().getColumn() > getCell().getPosition().getColumn())? 1: -1;

        for(char i = (char)(getCell().getPosition().getColumn() + moveDirection); i != destination.getPosition().getColumn(); i += moveDirection){
            if(board.getCell(Position.of(i, getCell().getPosition().getRow())).getPiece().isPresent()) return false;
        }

        return true;
    }

    @Override
    public boolean isKillValid(Cell destination, Board board) throws IllegalCellException {
        return isValid(destination, board);
    }

    public void setCastlingDone(boolean castlingDone) {
        isCastlingDone = castlingDone;
    }
}
