package main.com.java.subhankar.chess;

import main.com.java.subhankar.chess.board.Board;
import main.com.java.subhankar.chess.board.Move;
import main.com.java.subhankar.chess.board.Player;
import main.com.java.subhankar.chess.board.Position;
import main.com.java.subhankar.chess.constant.Color;
import main.com.java.subhankar.chess.constant.GameStatus;
import main.com.java.subhankar.chess.exception.IllegalCellException;
import main.com.java.subhankar.chess.exception.IllegalMoveException;
import main.com.java.subhankar.chess.piece.King;
import main.com.java.subhankar.chess.piece.Piece;

import java.util.ArrayList;
import java.util.List;

import static main.com.java.subhankar.chess.constant.Color.BLACK;
import static main.com.java.subhankar.chess.constant.Color.WHITE;
import static main.com.java.subhankar.chess.constant.GameStatus.*;

public class Chess {
    private final Board board;
    private final List<Player> players = new ArrayList<>();
    private final List<Move> moves = new ArrayList<>();
    private final List<Piece> killedPieces = new ArrayList<>();

    private Player currentPlayer;
    private GameStatus status;

    public Chess(final Player whitePlayer, final Player blackPlayer) throws IllegalCellException {
        players.add(whitePlayer);
        players.add(blackPlayer);
        board = new Board();

        board.reset();
        status = RESET;
    }

    public void startGame() throws IllegalStateException {
        if(status != RESET) throw new IllegalStateException("Game is already begun.");
        currentPlayer = players.get(0);
        status = ACTIVE;
    }

    public void makeMove(final Player player, final Position fromPosition, final Position toPosition) throws IllegalMoveException, IllegalCellException {
        if(status != ACTIVE) throw new IllegalMoveException("Game is already finished");
        if(!currentPlayer.equals(player)) throw new IllegalMoveException("Turn does not belong to player");

        final Move move = new Move(fromPosition, toPosition, player);
        board.movePiece(move, player);
        move.getPieceKilled().ifPresent(killedPieces::add);
        moves.add(move);

        Color opponentColor = player.getColor() == WHITE ? BLACK : WHITE;
        if(board.isCheckMate(opponentColor)){
            status = (opponentColor == WHITE) ? BLACK_WON : WHITE_WON;
        }
        else if(move.getPieceKilled().isPresent() && move.getPieceKilled().get() instanceof King){
            if(move.getPieceKilled().get().getColor() == WHITE) status = BLACK_WON;
            else status = WHITE_WON;
        }
        else changeTurn();

    }

    public void resetGame() throws IllegalCellException {
        status = RESET;
        currentPlayer = null;
        board.reset();
    }

    public GameStatus getStatus(){
        return status;
    }

    private void changeTurn() {
        if(players.get(0).equals(currentPlayer)) currentPlayer = players.get(1);
        else currentPlayer = players.get(0);
    }


}