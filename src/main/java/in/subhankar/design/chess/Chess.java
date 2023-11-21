package in.subhankar.design.chess;

import in.subhankar.design.chess.constant.Color;
import in.subhankar.design.chess.constant.GameStatus;
import in.subhankar.design.chess.exception.IllegalCellException;
import in.subhankar.design.chess.exception.IllegalMoveException;
import in.subhankar.design.chess.exception.IllegalStateException;
import in.subhankar.design.chess.piece.King;
import in.subhankar.design.chess.piece.Piece;
import in.subhankar.design.chess.board.Board;
import in.subhankar.design.chess.board.Move;
import in.subhankar.design.chess.board.Player;
import in.subhankar.design.chess.board.Position;

import java.util.ArrayList;
import java.util.List;

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
        status = GameStatus.RESET;
    }

    public void startGame() throws IllegalStateException {
        if(status != GameStatus.RESET) throw new IllegalStateException("Game is already begun.");
        currentPlayer = players.get(0);
        status = GameStatus.ACTIVE;
    }

    public void makeMove(final Player player, final Position fromPosition, final Position toPosition) throws IllegalMoveException, IllegalCellException {
        if(status != GameStatus.ACTIVE) throw new IllegalMoveException("Game is already finished");
        if(!currentPlayer.equals(player)) throw new IllegalMoveException("Turn does not belong to player");

        final Move move = new Move(fromPosition, toPosition, player);
        board.movePiece(move, player);
        move.getPieceKilled().ifPresent(killedPieces::add);
        moves.add(move);

        Color opponentColor = player.getColor() == Color.WHITE ? Color.BLACK : Color.WHITE;
        if(board.isCheckMate(opponentColor)){
            status = (opponentColor == Color.WHITE) ? GameStatus.BLACK_WON : GameStatus.WHITE_WON;
        }
        else if(move.getPieceKilled().isPresent() && move.getPieceKilled().get() instanceof King){
            if(move.getPieceKilled().get().getColor() == Color.WHITE) status = GameStatus.BLACK_WON;
            else status = GameStatus.WHITE_WON;
        }
        else changeTurn();

    }

    public void resetGame() throws IllegalCellException {
        status = GameStatus.RESET;
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
