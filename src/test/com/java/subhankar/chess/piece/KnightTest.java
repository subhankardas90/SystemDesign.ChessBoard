package test.com.java.subhankar.chess.piece;

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
        knight.setCell(new Cell(getPosition(startNotation), WHITE));

        assertThat(knight.isValid(new Cell(getPosition(endNotation), BLACK), board), is(isValid));
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
