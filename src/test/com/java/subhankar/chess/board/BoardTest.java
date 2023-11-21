package test.com.java.subhankar.chess.board;

import main.com.java.subhankar.chess.board.*;
import main.com.java.subhankar.chess.constant.Color;
import main.com.java.subhankar.chess.exception.IllegalCellException;
import main.com.java.subhankar.chess.exception.IllegalMoveException;
import main.com.java.subhankar.chess.piece.*;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;
import static org.hamcrest.CoreMatchers.is;
import static java.util.EnumSet.allOf;
import static main.com.java.subhankar.chess.constant.Color.BLACK;
import static main.com.java.subhankar.chess.constant.Color.WHITE;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTest {



    @Test
    void shouldBuildBoard() throws IllegalCellException {
        final Board board = new Board();

        for(char i='a'; i<='h'; i++){
            for(int j=1; j<=8; j++){
                final Cell cell = board.getCell(Position.of(i, j));
                assertThat(cell.getColor(), is((i + j) % 2 == 0 ? WHITE : BLACK));
                assertThat(cell.getPosition().getColumn(), is(i));
                assertThat(cell.getPosition().getRow(), is(j));
            }
        }
    }

    @Test
    void shouldGetCell() throws IllegalCellException, NoSuchFieldException, IllegalAccessException {
        final Board board = new Board();
        final Field cellsField = Board.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        final List<List<Cell>> cells = (List<List<Cell>>) cellsField.get(board);
        final Cell expectedCell = cells.get(4).get(5);

        assertThat(board.getCell(Position.of('f',5)), is(expectedCell));
    }

    @Test
    void shouldThrowExceptionOnInvalidCell() {
        final Board board = new Board();
        assertThrows(IllegalCellException.class, () -> board.getCell(Position.of('m', 10)));
    }

    @ParameterizedTest
    @MethodSource("methodProvider")
    void shouldResetBoard(String notation, Class type, Color color) throws IllegalCellException {
        final Board board = new Board();
        board.reset();

        assertThat(board.getCell(getPosition(notation)).getPiece().get(),
                allOf(instanceOf(type), hasProperty("color", is(color))));
    }

    private static Stream<Arguments> methodProvider() {
        return Stream.of(
                Arguments.of("a1", Rook.class, WHITE),
                Arguments.of("b1", Knight.class, WHITE),
                Arguments.of("c1", Bishop.class, WHITE),
                Arguments.of("d1", Queen.class, WHITE),
                Arguments.of("e1", King.class, WHITE),
                Arguments.of("f1", Bishop.class, WHITE),
                Arguments.of("g1", Knight.class, WHITE),
                Arguments.of("h1", Rook.class, WHITE),
                Arguments.of("a2", Pawn.class, WHITE),
                Arguments.of("b2", Pawn.class, WHITE),
                Arguments.of("c2", Pawn.class, WHITE),
                Arguments.of("d2", Pawn.class, WHITE),
                Arguments.of("e2", Pawn.class, WHITE),
                Arguments.of("f2", Pawn.class, WHITE),
                Arguments.of("g2", Pawn.class, WHITE),
                Arguments.of("h2", Pawn.class, WHITE),
                Arguments.of("a8", Rook.class, BLACK),
                Arguments.of("b8", Knight.class, BLACK),
                Arguments.of("c8", Bishop.class, BLACK),
                Arguments.of("d8", Queen.class, BLACK),
                Arguments.of("e8", King.class, BLACK),
                Arguments.of("f8", Bishop.class, BLACK),
                Arguments.of("g8", Knight.class, BLACK),
                Arguments.of("h8", Rook.class, BLACK),
                Arguments.of("a7", Pawn.class, BLACK),
                Arguments.of("b7", Pawn.class, BLACK),
                Arguments.of("c7", Pawn.class, BLACK),
                Arguments.of("d7", Pawn.class, BLACK),
                Arguments.of("e7", Pawn.class, BLACK),
                Arguments.of("f7", Pawn.class, BLACK),
                Arguments.of("g7", Pawn.class, BLACK),
                Arguments.of("h7", Pawn.class, BLACK)
        );
    }

    @Test
    void shouldNotMovePieceIfPositionsAreSame() throws IllegalCellException, IllegalMoveException {
        final Board board = new Board();
        board.reset();
        assertThrows(IllegalMoveException.class, () -> board.movePiece(
                new Move(getPosition("b2"), getPosition("b2"), new Player("test", WHITE)),
                new Player("test", WHITE)));

    }

    @Test
    void shouldNotMovePieceIfPieceDoesNotExist() throws IllegalCellException, IllegalMoveException {
        final Board board = new Board();
        board.reset();
        assertThrows(IllegalMoveException.class, () -> board.movePiece(
                new Move(getPosition("b3"), getPosition("b4"), new Player("test", WHITE)),
                new Player("test", WHITE)));

    }

    @Test
    void shouldNotMovePieceIfPieceDoesNotBelongToPlayer() throws IllegalCellException, IllegalMoveException {
        final Board board = new Board();
        board.reset();
        assertThrows(IllegalMoveException.class, () -> board.movePiece(
                new Move(getPosition("b2"), getPosition("b3"), new Player("test", BLACK)),
                new Player("test", BLACK)));

    }

    @Test
    void shouldNotMovePieceIfPieceCannotMoveToDestination() throws IllegalCellException, IllegalMoveException {
        final Board board = new Board();
        board.reset();
        assertThrows(IllegalMoveException.class, () -> board.movePiece(
                new Move(getPosition("b2"), getPosition("b5"), new Player("test", WHITE)),
                new Player("test", WHITE)));

    }

    @Test
    void shouldNotMovePieceIfPieceAtDestinationBelongsToPlayer() throws IllegalCellException, IllegalMoveException {
        final Board board = new Board();
        board.reset();
        board.getCell(getPosition("c3")).setPiece(new Pawn(WHITE));
        assertThrows(IllegalMoveException.class, () -> board.movePiece(
                new Move(getPosition("b2"), getPosition("c3"), new Player("test", WHITE)),
                new Player("test", WHITE)));

    }

    @Test
    void shouldProcessValidMove() throws IllegalCellException, IllegalMoveException {
        final Board board = new Board();
        board.reset();
        Piece pieceMoved = board.getCell(getPosition("b2")).getPiece().get();

        board.movePiece(
                new Move(getPosition("b2"), getPosition("b3"), new Player("test", WHITE)),
                new Player("test", WHITE));

        assertThat(pieceMoved.getCell().getPosition().getRow(), is(3));
        assertThat(pieceMoved.getCell().getPosition().getColumn(), is('b'));

    }

    @Test
    void shouldProcessValidQueenSideCastlingMove() throws IllegalCellException, IllegalMoveException {
        final Board board = new Board();
        board.reset();
        Piece king = board.getCell(getPosition("e1")).getPiece().get();
        Piece rook = board.getCell(getPosition("a1")).getPiece().get();

        board.getCell(getPosition("b1")).clearPiece();
        board.getCell(getPosition("c1")).clearPiece();
        board.getCell(getPosition("d1")).clearPiece();

        final Move move = new Move(getPosition("e1"), getPosition("a1"), new Player("test", WHITE));

        board.movePiece(move, new Player("test", WHITE));

        assertThat(king.getCell().getPosition().getRow(), is(1));
        assertThat(king.getCell().getPosition().getColumn(), is('c'));
        assertThat(rook.getCell().getPosition().getRow(), is(1));
        assertThat(rook.getCell().getPosition().getColumn(), is('e'));
        assertThat(move.isCastlingMove(), is(true));

    }

    @Test
    void shouldProcessValidKingSideCastlingMove() throws IllegalCellException, IllegalMoveException {
        final Board board = new Board();
        board.reset();
        Piece king = board.getCell(getPosition("e1")).getPiece().get();
        Piece rook = board.getCell(getPosition("h1")).getPiece().get();

        board.getCell(getPosition("f1")).clearPiece();
        board.getCell(getPosition("g1")).clearPiece();

        final Move move = new Move(getPosition("e1"), getPosition("h1"), new Player("test", WHITE));

        board.movePiece(move, new Player("test", WHITE));

        assertThat(king.getCell().getPosition().getRow(), is(1));
        assertThat(king.getCell().getPosition().getColumn(), is('g'));
        assertThat(rook.getCell().getPosition().getRow(), is(1));
        assertThat(rook.getCell().getPosition().getColumn(), is('e'));
        assertThat(move.isCastlingMove(), is(true));

    }
}
