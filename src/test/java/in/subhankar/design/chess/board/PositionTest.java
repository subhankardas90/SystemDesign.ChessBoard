package in.subhankar.design.chess.board;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PositionTest {

    @Test
    void shouldBuildPosition(){
        final Position position = Position.of('A', 10);

        assertThat(position, instanceOf(Position.class));
        assertThat(position.getRow(), is(10));
        assertThat(position.getColumn(), is('A'));
    }

    @Test
    void shouldTestEqualsAndHashCodeOnPlayer(){
        final Position position1 = Position.of('A', 10);
        final Position position2 = Position.of('A', 10);
        final Position position3 = Position.of('B', 11);

        assertThat(position1.equals(position2), is(true));
        assertThat(position1.hashCode(), is(position2.hashCode()));
        assertThat(position1.equals(position3), is(false));
        assertThat(position1.hashCode(), is(not(position3.hashCode())));
    }


}