package test.com.java.subhankar.chess.board;

import main.com.java.subhankar.chess.board.Move;
import main.com.java.subhankar.chess.board.Player;
import main.com.java.subhankar.chess.board.Position;
import main.com.java.subhankar.chess.piece.Pawn;
import main.com.java.subhankar.chess.piece.Piece;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static main.com.java.subhankar.chess.constant.Color.WHITE;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class MoveTest {
    @Test
    void shouldBuildMove(){
        final Move move = new Move(
                Position.of('A', 10),
                Position.of('B', 11),
                new Player("test", WHITE));

        assertThat(move, instanceOf(Move.class));
        assertThat(move.getFromPosition().equals(Position.of('A', 10)), is(true));
        assertThat(move.getToPosition().equals(Position.of('B', 11)), is(true));
        assertThat(move.getPlayer().equals(new Player("test", WHITE)), is(true));
        assertThat(move.getPieceKilled().isPresent(), is(false));

    }

    @Test
    void shouldSetPieceMoved(){
        final Move move = new Move(
                Position.of('A', 10),
                Position.of('B', 11),
                new Player("test", WHITE));
        final Piece piece = new Pawn(WHITE);
        move.setPieceMoved(piece);

        assertThat(move.getPieceMoved(), is(piece));

    }

    @Test
    void shouldSetPieceKilled(){
        final Move move = new Move(
                Position.of('A', 10),
                Position.of('B', 11),
                new Player("test", WHITE));
        final Piece piece = new Pawn(WHITE);
        move.setPieceKilled(piece);

        assertThat(move.getPieceKilled().isPresent(), is(true));
        assertThat(move.getPieceKilled().get(), is(piece));

    }

    @Test
    void shouldSetCastlingMove(){
        final Move move = new Move(
                Position.of('A', 10),
                Position.of('B', 11),
                new Player("test", WHITE));

        assertThat(move.isCastlingMove(), is(false));

        move.setCastlingMove(true);
        assertThat(move.isCastlingMove(), is(true));

    }
}
