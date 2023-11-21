package test.com.java.subhankar.chess;

class ChessTest {

    @Test
    public void shouldCompleteGame(){
        final Player whitePlayer = new Player("player 1", WHITE);
        final Player blackPlayer = new Player("player 2", BLACK);

        final Chess game = new Chess(whitePlayer, blackPlayer);
        assertThat(game.getStatus(), is(RESET));

        game.startGame();
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(whitePlayer, getPosition("f2"), getPosition("f3"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(blackPlayer, getPosition("e7"), getPosition("e6"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(whitePlayer, getPosition("g2"), getPosition("g3"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(blackPlayer, getPosition("e6"), getPosition("e5"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(whitePlayer, getPosition("g3"), getPosition("g4"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(blackPlayer, getPosition("d8"), getPosition("h4"));
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

        game.makeMove(whitePlayer, getPosition("f2"), getPosition("f3"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(blackPlayer, getPosition("e7"), getPosition("e6"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(whitePlayer, getPosition("f3"), getPosition("f4"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(blackPlayer, getPosition("e6"), getPosition("e5"));
        assertThat(game.getStatus(), is(ACTIVE));

        game.makeMove(whitePlayer, getPosition("f4"), getPosition("e5"));
        assertThat(game.getStatus(), is(ACTIVE));
    }

    @Test
    public void shouldThrowExceptionOnIllegalState(){
        final Player whitePlayer = new Player("player 1", WHITE);
        final Player blackPlayer = new Player("player 2", BLACK);

        final Chess game = new Chess(whitePlayer, blackPlayer);
        game.startGame();

        game.makeMove(whitePlayer, getPosition("f2"), getPosition("f3"));

        assertThrows(IllegalStateException.class,
                () -> game.startGame());
    }

}