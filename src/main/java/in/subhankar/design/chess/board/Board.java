package in.subhankar.design.chess.board;

import in.subhankar.design.chess.constant.Color;
import in.subhankar.design.chess.exception.IllegalCellException;
import in.subhankar.design.chess.exception.IllegalMoveException;
import in.subhankar.design.chess.piece.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board {

    private final List<List<Cell>> cells = new ArrayList<>();
    private final int rows;
    private final int columns;

    private List<Piece> whitePieces = new ArrayList<>();
    private List<Piece> blackPieces = new ArrayList<>();

    private King whiteKing;
    private King blackKing;

    public Board(){
        this(8, 8);
    }

    private Board(final int rows, final int columns) {
        this.rows = rows;
        this.columns = columns;

        for(int i=0; i<rows; i++){
            cells.add(new ArrayList<>());
            for(int j=0; j<columns; j++){
                final Color color = (i+j)%2 == 0 ? Color.WHITE: Color.BLACK;
                cells.get(i).add(new Cell(Position.of((char)('a'+j), i+1), color));
            }
        }
    }

    public Cell getCell(final Position position) throws IllegalCellException {
        final int row = position.getRow();
        final int column = position.getColumn();

        if(!isValidCell(position)) throw new IllegalCellException("Invalid Cell");

        return cells.get(row-1).get(column - 'a');
    }

    public void reset() throws IllegalCellException {
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                cells.get(i).get(j).clearPiece();
            }
        }

        addPiece(Position.of('a', 1), new Rook(Color.WHITE));
        addPiece(Position.of('b', 1), new Knight(Color.WHITE));
        addPiece(Position.of('c', 1), new Bishop(Color.WHITE));
        addPiece(Position.of('d', 1), new Queen(Color.WHITE));
        addPiece(Position.of('e', 1), new King(Color.WHITE));
        addPiece(Position.of('f', 1), new Bishop(Color.WHITE));
        addPiece(Position.of('g', 1), new Knight(Color.WHITE));
        addPiece(Position.of('h', 1),new Rook(Color.WHITE));

        addPiece(Position.of('a', 2), new Pawn(Color.WHITE));
        addPiece(Position.of('b', 2), new Pawn(Color.WHITE));
        addPiece(Position.of('c', 2), new Pawn(Color.WHITE));
        addPiece(Position.of('d', 2), new Pawn(Color.WHITE));
        addPiece(Position.of('e', 2), new Pawn(Color.WHITE));
        addPiece(Position.of('f', 2), new Pawn(Color.WHITE));
        addPiece(Position.of('g', 2), new Pawn(Color.WHITE));
        addPiece(Position.of('h', 2), new Pawn(Color.WHITE));

        addPiece(Position.of('a', 8), new Rook(Color.BLACK));
        addPiece(Position.of('b', 8), new Knight(Color.BLACK));
        addPiece(Position.of('c', 8), new Bishop(Color.BLACK));
        addPiece(Position.of('d', 8), new Queen(Color.BLACK));
        addPiece(Position.of('e', 8), new King(Color.BLACK));
        addPiece(Position.of('f', 8), new Bishop(Color.BLACK));
        addPiece(Position.of('g', 8), new Knight(Color.BLACK));
        addPiece(Position.of('h', 8), new Rook(Color.BLACK));

        addPiece(Position.of('a', 7), new Pawn(Color.BLACK));
        addPiece(Position.of('b', 7), new Pawn(Color.BLACK));
        addPiece(Position.of('c', 7), new Pawn(Color.BLACK));
        addPiece(Position.of('d', 7), new Pawn(Color.BLACK));
        addPiece(Position.of('e', 7), new Pawn(Color.BLACK));
        addPiece(Position.of('f', 7), new Pawn(Color.BLACK));
        addPiece(Position.of('g', 7), new Pawn(Color.BLACK));
        addPiece(Position.of('h', 7), new Pawn(Color.BLACK));
    }

    public void movePiece(final Move move, final Player player) throws IllegalCellException, IllegalMoveException {
        final Cell fromCell = getCell(move.getFromPosition());
        final Cell toCell = getCell(move.getToPosition());

        checkIfPieceWillNotChangePosition(move.getFromPosition(), move.getToPosition());
        checkIfPieceDoesNotExist(fromCell);
        checkIfPieceDoesNotBelongToPlayer(fromCell, player);
        checkIfPieceCanBeMovedAsPerMove(fromCell, move);

        if(isCastlingMove(fromCell, toCell)) {
            performCastling(move);
            return;
        }

        checkIfPieceAtDestinationBelongsToPlayer(toCell, player);

        processMove(fromCell, toCell, move);
    }

    public boolean isCheckMate(final Color color) throws IllegalCellException {
        final List<Piece> attackers = (color == Color.WHITE) ? blackPieces : whitePieces;
        final List<Piece> defenders = (color == Color.WHITE) ? whitePieces : blackPieces;
        final King defender = (color == Color.WHITE) ? whiteKing : blackKing;
        final Color opponentColor = (color == Color.WHITE) ? Color.BLACK : Color.WHITE;

        int invalidCells = 0;
        int attackedCells = 0;
        int filledCells = 0;
        int totalCells = 0;

        /* Check all positions defender king can take */
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                totalCells++;

                final char column = (char) (defender.getCell().getPosition().getColumn() + i);
                final int row = defender.getCell().getPosition().getRow() + j;

                final Position position = Position.of(column, row);

                /* Check if position is valid position */
                if(!isValidCell(Position.of(column, row))){
                    invalidCells++; continue;
                }

                /* king`s own position is filled */
                if(defender.getCell().getPosition().equals(position) &&
                        isPositionUnderAttack(color, position)){
                    filledCells++;
                    continue;
                }
                /* king's position is not under attack. No Checkmate */
                else if (defender.getCell().getPosition().equals(position)) return false;

                /* check if position is already occupied */
                final Optional<Piece> pieceAtPosition = getCell(position).getPiece();
                if(pieceAtPosition.isPresent()){
                    /* is it defendant piece */
                    if(color == pieceAtPosition.get().getColor()) filledCells++;
                    /* if it is a attacker piece, can defendant kill it */
                    else if(! isPositionUnderAttack(opponentColor, position)) filledCells++;
                    continue;
                }

                /* is this position attacked by the opponent */
                for(final Piece attacker: attackers){
                    if(attacker.isKillValid(getCell(Position.of(column, row)), this)){
                        /* can defendant piece kill the attacker */
                        if(!isPositionUnderAttack(opponentColor, attacker.getCell().getPosition())){
                            attackedCells++;
                            break;
                        }
                    }
                }
            }
        }

        if(invalidCells + filledCells == totalCells) return false;
        if(attackedCells + invalidCells + filledCells < totalCells) return false;
        return  true;
    }

    private boolean isPositionUnderAttack(final Color color,
                                          final Position position) {
        List<Piece> attackers = (color == Color.WHITE) ? blackPieces : whitePieces;
        return attackers.stream()
                .map(attacker -> attacker.isKillValid(getCell(position), this))
                .reduce(false, (a, b) -> a || b);
    }

    private void processMove(final Cell fromCell,
                             final Cell toCell,
                             final Move move) {
        final Optional<Piece> pieceKilled = toCell.getPiece();
        final Piece pieceMoved = fromCell.getPiece().get();

        pieceKilled.ifPresent(move::setPieceKilled);
        pieceKilled.ifPresent(piece -> piece.setCell(null));
        pieceKilled.ifPresent(piece -> {
            if(piece.getColor() == Color.WHITE) whitePieces.remove(piece);
            else blackPieces.remove(piece);
        });

        toCell.setPiece(pieceMoved);
        fromCell.clearPiece();
        pieceMoved.setCell(toCell);
        move.setPieceMoved(pieceMoved);
        pieceMoved.moved();
    }

    private boolean isCastlingMove(final Cell fromCell, final Cell toCell) throws IllegalCellException {
        final Optional<Piece> king = fromCell.getPiece();
        final Optional<Piece> rook = toCell.getPiece();
        return king.isPresent() && rook.isPresent() &&
                king.get() instanceof King && rook.get() instanceof Rook &&
                ((King) king.get()).isCastlingMove(toCell, this);
    }

    private void checkIfPieceAtDestinationBelongsToPlayer(final Cell toCell,
                                                          final Player player) throws IllegalMoveException {
        if(toCell.getPiece().isPresent() && toCell.getPiece().get().getColor().equals(player.getColor()))
            throw new IllegalMoveException("Piece at end position belongs to player");
    }

    private void checkIfPieceCanBeMovedAsPerMove(final Cell fromCell,
                                                 final Move move) throws IllegalMoveException, IllegalCellException {
        if(!fromCell.getPiece().get().isValid(getCell(move.getToPosition()), this))
            throw new IllegalMoveException("Move is invalid");
    }

    private void checkIfPieceDoesNotBelongToPlayer(final Cell fromCell,
                                                   final Player player) throws IllegalMoveException {
        final Piece piece = fromCell.getPiece().get();

        if(!piece.getColor().equals(player.getColor()))
            throw new IllegalMoveException("Piece does not belong to player");
    }

    private void checkIfPieceDoesNotExist(final Cell fromCell) throws IllegalMoveException {
        if(!fromCell.getPiece().isPresent())
            throw new IllegalMoveException("Piece does not exist at start position");
    }

    private void checkIfPieceWillNotChangePosition(final Position fromPosition,
                                                   final Position toPosition) throws IllegalMoveException {
        if(fromPosition.equals(toPosition))
            throw new IllegalMoveException("source and destination position should not be same");
    }

    private void performCastling(Move move) throws IllegalCellException {
        final Cell kingCell = getCell(move.getFromPosition());
        final Cell rookCell = getCell(move.getToPosition());

        final King king = (King) kingCell.getPiece().get();
        final Rook rook = (Rook) rookCell.getPiece().get();

        final int moveDirection = kingCell.getPosition().getColumn() < rookCell.getPosition().getColumn() ? 2 : -2;
        final Position toPosition = Position.of((char) (kingCell.getPosition().getColumn() + moveDirection), kingCell.getPosition().getRow());
        final Cell toCell = getCell(toPosition);

        toCell.setPiece(king);
        kingCell.setPiece(rook);
        rookCell.clearPiece();

        king.setCell(toCell);
        rook.setCell(kingCell);

        king.moved();
        rook.moved();

        king.setCastlingDone(true);
        move.setCastlingMove(true);
    }

    private void addPiece(final Position position, final Piece piece) throws IllegalCellException {
        getCell(position).setPiece(piece);

        if(piece.getColor() == Color.WHITE) whitePieces.add(piece);
        else blackPieces.add(piece);

        if(piece instanceof King && piece.getColor() == Color.WHITE) whiteKing = (King) piece;
        if(piece instanceof King && piece.getColor() == Color.BLACK) blackKing = (King) piece;

        piece.setCell(getCell(position));
    }

    private boolean isValidCell(final Position position){
        return position.getRow() >= 1 && position.getRow() <= rows &&
                position.getColumn() >= 'a' && position.getColumn() <= 'a' + columns - 1;
    }
}
