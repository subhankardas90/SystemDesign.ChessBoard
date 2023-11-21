package test.com.java.subhankar.chess.piece;

class PawnTest {

    @Mock
    private Board board;

    @Mock
    private Cell cell;

    @BeforeEach
    void init() throws IllegalCellException {
        initMocks(this);
        when(board.getCell(any(Position.class))).thenReturn(cell);
    }

    @ParameterizedTest(name = "[{index}] Pawns move from {0} to {1} while it jumps piece {2} is {3}")
    @MethodSource("moveProvider")
    void shouldValidateMove(final String startNotation,
                            final String endNotation,
                            final Color color,
                            final boolean killsPiece,
                            final boolean isValid) throws IllegalCellException {
        if(killsPiece) when(cell.getPiece()).thenReturn(Optional.of(new Pawn(WHITE)));
        else when(cell.getPiece()).thenReturn(Optional.empty());

        final Piece pawn = new Pawn(color);
        pawn.setCell(new Cell(getPosition(startNotation), WHITE));

        assertThat(pawn.isValid(new Cell(getPosition(endNotation), BLACK), board), is(isValid));
    }

    private static Stream<Arguments> moveProvider() {
        return Stream.of(
                Arguments.of("e5", "e6", WHITE, true, false),
                Arguments.of("e5", "e6", WHITE, false, true),
                Arguments.of("e5", "f6", WHITE, true, true),
                Arguments.of("e5", "d6", WHITE, true, true),
                Arguments.of("e5", "f6", WHITE, false, false),
                Arguments.of("e5", "d6", WHITE, false, false),
                Arguments.of("e5", "g3", WHITE, false, false),
                Arguments.of("e5", "e4", WHITE, false, false),
                Arguments.of("e5", "e4", BLACK, true, false),
                Arguments.of("e5", "e4", BLACK, false, true),
                Arguments.of("e5", "f4", BLACK, true, true),
                Arguments.of("e5", "d4", BLACK, true, true),
                Arguments.of("e5", "f4", BLACK, false, false),
                Arguments.of("e5", "d4", BLACK, false, false),
                Arguments.of("e5", "g4", BLACK, false, false),
                Arguments.of("e5", "e6", BLACK, false, false)
        );
    }

}