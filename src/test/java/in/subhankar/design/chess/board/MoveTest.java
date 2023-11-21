package in.subhankar.design.chess.board;

import in.subhankar.design.chess.piece.Pawn;
import in.subhankar.design.chess.piece.Piece;
import org.junit.jupiter.api.Test;

import static in.subhankar.design.chess.constant.Color.WHITE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

class MoveTest {

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