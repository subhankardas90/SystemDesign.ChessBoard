package test.com.java.subhankar.chess.board;

import main.com.java.subhankar.chess.board.Cell;
import main.com.java.subhankar.chess.board.Position;
import main.com.java.subhankar.chess.piece.Pawn;
import main.com.java.subhankar.chess.piece.Piece;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static main.com.java.subhankar.chess.constant.Color.BLACK;
import static main.com.java.subhankar.chess.constant.Color.WHITE;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class CellTest {
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
