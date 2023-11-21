package in.subhankar.design.chess.board;

import in.subhankar.design.chess.constant.Color;
import in.subhankar.design.chess.piece.Piece;

import java.util.Optional;

public class Cell {
    private final Position position;
    private final Color color;

    private Piece piece;

    public Cell(final Position position, final Color color) {
        this.position = position;
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public Optional<Piece> getPiece() {
        return Optional.ofNullable(piece);
    }

    public void clearPiece(){
        this.piece = null;
    }

    public void setPiece(final Piece piece){
        this.piece = piece;
    }


}
