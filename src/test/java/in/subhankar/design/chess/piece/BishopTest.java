package in.subhankar.design.chess.piece;

import in.subhankar.design.chess.board.Board;
import in.subhankar.design.chess.board.Cell;
import in.subhankar.design.chess.board.Position;
import in.subhankar.design.chess.exception.IllegalCellException;
import in.subhankar.design.chess.utils.PositionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.util.Optional;
import java.util.stream.Stream;

import static in.subhankar.design.chess.constant.Color.BLACK;
import static in.subhankar.design.chess.constant.Color.WHITE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class BishopTest {

    @Mock
    private Board board;

    @Mock
    private Cell cell;

    @BeforeEach
    void init() throws IllegalCellException {
        initMocks(this);
        when(board.getCell(any(Position.class))).thenReturn(cell);
    }

    @ParameterizedTest(name = "[{index}] Bishops move from {0} to {1} while it jumps piece {2} is {3}")
    @MethodSource("moveProvider")
    void shouldValidateMove(final String startNotation,
                            final String endNotation,
                            final boolean jumpsPiece,
                            final boolean isValid) throws IllegalCellException {
        if(jumpsPiece) when(cell.getPiece()).thenReturn(Optional.of(new Pawn(WHITE)));
        else when(cell.getPiece()).thenReturn(Optional.empty());

        final Piece bishop = new Bishop(WHITE);
        bishop.setCell(new Cell(PositionUtils.getPosition(startNotation), WHITE));

        assertThat(bishop.isValid(new Cell(PositionUtils.getPosition(endNotation), BLACK), board), is(isValid));
    }

    private static Stream<Arguments> moveProvider() {
        return Stream.of(
                Arguments.of("e5", "h8", true, false),
                Arguments.of("e5", "h8", false, true),
                Arguments.of("e5", "c3", true, false),
                Arguments.of("e5", "c3", false, true),
                Arguments.of("e5", "g3", true, false),
                Arguments.of("e5", "g3", false, true),
                Arguments.of("e5", "c7", true, false),
                Arguments.of("e5", "h13", false, false)
        );
    }

}