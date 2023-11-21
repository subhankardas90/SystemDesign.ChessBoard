package in.subhankar.design.chess.board;

import in.subhankar.design.chess.piece.Piece;

import java.util.Optional;

public class Move {
    private final Position fromPosition;
    private final Position toPosition;
    private Piece pieceMoved;
    private Piece pieceKilled;
    private final Player player;
    private boolean isCastlingMove;

    public Move(Position fromPosition, Position toPosition, Player player) {
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
        this.player = player;
        this.isCastlingMove = false;
    }

    public Position getFromPosition() {
        return fromPosition;
    }

    public Position getToPosition() {
        return toPosition;
    }

    public Piece getPieceMoved() {
        return pieceMoved;
    }

    public Optional<Piece> getPieceKilled() {
        return Optional.ofNullable(pieceKilled);
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isCastlingMove() {
        return isCastlingMove;
    }

    public void setPieceMoved(Piece pieceMoved) {
        this.pieceMoved = pieceMoved;
    }

    public void setPieceKilled(Piece pieceKilled) {
        this.pieceKilled = pieceKilled;
    }

    public void setCastlingMove(boolean castlingMove) {
        isCastlingMove = castlingMove;
    }
}
