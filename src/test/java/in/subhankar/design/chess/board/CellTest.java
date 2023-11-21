package in.subhankar.design.chess.board;

import in.subhankar.design.chess.piece.Pawn;
import in.subhankar.design.chess.piece.Piece;
import org.junit.jupiter.api.Test;

import static in.subhankar.design.chess.constant.Color.BLACK;
import static in.subhankar.design.chess.constant.Color.WHITE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

class CellTest {

    @Test
    void shouldBuildCell(){
        final Cell cell = new Cell(Position.of('A', 10), WHITE);

        assertThat(cell, instanceOf(Cell.class));
        assertThat(cell.getPosition().equals(Position.of('A', 10)), is(true));
        assertThat(cell.getColor(), is(WHITE));
        assertThat(cell.getPiece().isPresent(), is(false));
    }

    @Test
    void shouldAddPiece(){
        final Cell cell = new Cell(Position.of('A', 10), WHITE);
        final Piece piece = new Pawn(BLACK);
        cell.setPiece(piece);

        assertThat(cell.getPiece().isPresent(), is(true));
        assertThat(cell.getPiece().get(), is(piece));
    }

    @Test
    void shouldClearPiece(){
        final Cell cell = new Cell(Position.of('A', 10), WHITE);
        final Piece piece = new Pawn(BLACK);
        cell.setPiece(piece);
        cell.clearPiece();

        assertThat(cell.getPiece().isPresent(), is(false));
    }



}