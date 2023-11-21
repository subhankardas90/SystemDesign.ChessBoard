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

import java.util.stream.Stream;

import static in.subhankar.design.chess.constant.Color.BLACK;
import static in.subhankar.design.chess.constant.Color.WHITE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class KnightTest {

    @Mock
    private Board board;

    @Mock
    private Cell cell;

    @BeforeEach
    void init() throws IllegalCellException {
        initMocks(this);
        when(board.getCell(any(Position.class))).thenReturn(cell);
    }

    @ParameterizedTest(name = "[{index}] Knights move from {0} to {1} while it jumps piece {2} is {3}")
    @MethodSource("moveProvider")
    void shouldValidateMove(final String startNotation,
                            final String endNotation,
                            final boolean isValid) throws IllegalCellException {
        final Piece knight = new Knight(WHITE);
        knight.setCell(new Cell(PositionUtils.getPosition(startNotation), WHITE));

        assertThat(knight.isValid(new Cell(PositionUtils.getPosition(endNotation), BLACK), board), is(isValid));
    }

    private static Stream<Arguments> moveProvider() {
        return Stream.of(
                Arguments.of("e5", "g6", true),
                Arguments.of("e5", "g4", true),
                Arguments.of("e5", "c4", true),
                Arguments.of("e5", "c6", true),
                Arguments.of("e5", "f3", true),
                Arguments.of("e5", "d3", true),
                Arguments.of("e5", "f7", true),
                Arguments.of("e5", "d7", true),
                Arguments.of("e5", "h8", false)
        );
    }

}