package in.subhankar.design.chess.board;

import java.util.Objects;

public class Position {
    private final char column;
    private final int row;

    public static Position of(final char column, final int row){
        return new Position(column, row);
    }

    private Position(char column, int row) {
        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        final Position position = (Position) o;
        return column == position.column &&
                row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
