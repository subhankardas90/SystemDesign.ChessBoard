package main.com.java.subhankar.chess.board;

import main.com.java.subhankar.chess.constant.Color;

import java.util.Objects;

public class Player {
    private final String name;
    private final Color color;

    public Player(final String name, final Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        final Player player = (Player) o;
        return Objects.equals(name, player.name) && color == player.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color);
    }
}
