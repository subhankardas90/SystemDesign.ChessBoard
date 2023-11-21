package in.subhankar.design.chess.board;

import org.junit.jupiter.api.Test;

import static in.subhankar.design.chess.constant.Color.WHITE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PlayerTest {

    @Test
    void shouldBuildPlayer(){
        final Player player = new Player("test", WHITE);

        assertThat(player, instanceOf(Player.class));
        assertThat(player.getName(), is("test"));
        assertThat(player.getColor(), is(WHITE));
    }

    @Test
    void shouldTestEqualsAndHashCodeOnPlayer(){
        final Player player1 = new Player("test", WHITE);
        final Player player2 = new Player("test", WHITE);
        final Player player3 = new Player("test1", WHITE);

        assertThat(player1.equals(player2), is(true));
        assertThat(player1.hashCode(), is(player2.hashCode()));
        assertThat(player1.equals(player3), is(false));
        assertThat(player1.hashCode(), is(not(player3.hashCode())));
    }
}