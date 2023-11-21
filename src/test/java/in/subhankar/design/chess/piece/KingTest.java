package in.subhankar.design.chess.piece;

import in.subhankar.design.chess.board.Board;
import in.subhankar.design.chess.board.Cell;
import in.subhankar.design.chess.board.Position;
import in.subhankar.design.chess.exception.IllegalCellException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.util.stream.Stream;

import static in.subhankar.design.chess.constant.Color.BLACK;
import static in.subhankar.design.chess.constant.Color.WHITE;
import static in.subhankar.design.chess.utils.PositionUtils.getPosition;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class KingTest {

    @Mock
    private Board board;

    @Mock
    private Cell cell;

    @BeforeEach
    void init() throws IllegalCellException {
        initMocks(this);
        when(board.getCell(any(Position.class))).thenReturn(cell);
    }

    @ParameterizedTest(name = "[{index}] King move from {0} to {1} is {2}")
    @MethodSource("moveProvider")
    void shouldValidateMove(final String startNotation,
                            final String endNotation,
                            final boolean isValid) throws IllegalCellException {

        final Piece king = new King(WHITE);
        king.setCell(new Cell(getPosition(startNotation), WHITE));

        assertThat(king.isValid(new Cell(getPosition(endNotation), BLACK), board), is(isValid));
    }

    private static Stream<Arguments> moveProvider() {
        return Stream.of(
                Arguments.of("e5", "e6", true),
                Arguments.of("e5", "e4", true),
                Arguments.of("e5", "f5", true),
                Arguments.of("e5", "d5", true),
                Arguments.of("e5", "f6", true),
                Arguments.of("e5", "f4", true),
                Arguments.of("e5", "d4", true),
                Arguments.of("e5", "d6", true),
                Arguments.of("e5", "g9", false)
        );
    }
}