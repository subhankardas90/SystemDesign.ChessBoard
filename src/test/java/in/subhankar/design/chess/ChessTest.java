package in.subhankar.design.chess;

import in.subhankar.design.chess.board.Player;
import in.subhankar.design.chess.exception.IllegalStateException;
import in.subhankar.design.chess.utils.PositionUtils;
import org.junit.jupiter.api.Test;

import static in.subhankar.design.chess.constant.Color.BLACK;
import static in.subhankar.design.chess.constant.Color.WHITE;
import static in.subhankar.design.chess.constant.GameStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class ChessTest {

    @Test
    public void shouldCompleteGame(){
        final Player whitePlayer = new Player("player 1", WHITE);
        final Player blackPlayer = new Player("player 2", BLACK);

        final Chess game = new Chess(whitePlayer, blackPlayer);
        assertThat(game.getStatus(), is(RESET));

        game.startGame();
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(whitePlayer, PositionUtils.getPosition("f2"), PositionUtils.getPosition("f3"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(blackPlayer, PositionUtils.getPosition("e7"), PositionUtils.getPosition("e6"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(whitePlayer, PositionUtils.getPosition("g2"), PositionUtils.getPosition("g3"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(blackPlayer, PositionUtils.getPosition("e6"), PositionUtils.getPosition("e5"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(whitePlayer, PositionUtils.getPosition("g3"), PositionUtils.getPosition("g4"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(blackPlayer, PositionUtils.getPosition("d8"), PositionUtils.getPosition("h4"));
        assertThat(game.getStatus(), is(BLACK_WON));
    }

    @Test
    public void shouldTestKill(){
        final Player whitePlayer = new Player("player 1", WHITE);
        final Player blackPlayer = new Player("player 2", BLACK);

        final Chess game = new Chess(whitePlayer, blackPlayer);
        assertThat(game.getStatus(), is(RESET));

        game.startGame();
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(whitePlayer, PositionUtils.getPosition("f2"), PositionUtils.getPosition("f3"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(blackPlayer, PositionUtils.getPosition("e7"), PositionUtils.getPosition("e6"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(whitePlayer, PositionUtils.getPosition("f3"), PositionUtils.getPosition("f4"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(blackPlayer, PositionUtils.getPosition("e6"), PositionUtils.getPosition("e5"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(whitePlayer, PositionUtils.getPosition("f4"), PositionUtils.getPosition("e5"));
        assertThat(game.getStatus(), is(ACTIVE));
    }

    @Test
    public void shouldThrowExceptionOnIllegalState(){
        final Player whitePlayer = new Player("player 1", WHITE);
        final Player blackPlayer = new Player("player 2", BLACK);

        final Chess game = new Chess(whitePlayer, blackPlayer);
        game.startGame();

        game.makeMove(whitePlayer, PositionUtils.getPosition("f2"), PositionUtils.getPosition("f3"));

        assertThrows(IllegalStateException.class,
                () -> game.startGame());
    }

}