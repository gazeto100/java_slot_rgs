package dev.slotrgs.math;

import java.util.Arrays;
import java.util.List;

/**
 * A payline: the row index (0 = top) it passes through on each reel, from left to right.
 */
public record Line(List<Integer> rows) {

    public Line {
        rows = List.copyOf(rows);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Line must not be empty");
        }
    }

    public static Line of(int... rows) {
        return new Line(Arrays.stream(rows).boxed().toList());
    }

    public int rowOn(int reel) {
        return rows.get(reel);
    }
}
