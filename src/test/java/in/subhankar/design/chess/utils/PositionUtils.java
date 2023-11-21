package in.subhankar.design.chess.utils;

import in.subhankar.design.chess.board.Position;

public class PositionUtils {
    public static Position getPosition(final String notation){
        char column = Character.toLowerCase(notation.charAt(0));
        int row = Integer.parseInt(notation.substring(1));

        return Position.of(column, row);
    }
}
