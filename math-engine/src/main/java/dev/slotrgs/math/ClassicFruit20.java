package dev.slotrgs.math;

import java.util.List;
import java.util.Map;

import dev.slotrgs.math.Paytable.LinePays;

/**
 * Fixed parameters of the ClassicFruit20 game. Reel strips are added once they are tuned for RTP.
 */
public final class ClassicFruit20 {

    public static final int ROWS = 3;

    public static final Paytable PAYTABLE = new Paytable(Map.of(
            Symbol.CHERRY, new LinePays(8, 25, 100),
            Symbol.LEMON, new LinePays(8, 25, 100),
            Symbol.ORANGE, new LinePays(10, 30, 150),
            Symbol.PLUM, new LinePays(10, 30, 150),
            Symbol.GRAPES, new LinePays(20, 80, 300),
            Symbol.WATERMELON, new LinePays(20, 80, 300),
            Symbol.SEVEN, new LinePays(50, 200, 1000)));

    // Row per reel: 0 = top, 1 = middle, 2 = bottom.
    public static final List<Line> LINES = List.of(
            Line.of(1, 1, 1, 1, 1),
            Line.of(0, 0, 0, 0, 0),
            Line.of(2, 2, 2, 2, 2),
            Line.of(0, 1, 2, 1, 0),
            Line.of(2, 1, 0, 1, 2),
            Line.of(1, 0, 0, 0, 1),
            Line.of(1, 2, 2, 2, 1),
            Line.of(0, 0, 1, 2, 2),
            Line.of(2, 2, 1, 0, 0),
            Line.of(1, 2, 1, 0, 1),
            Line.of(1, 0, 1, 2, 1),
            Line.of(0, 1, 1, 1, 0),
            Line.of(2, 1, 1, 1, 2),
            Line.of(0, 1, 0, 1, 0),
            Line.of(2, 1, 2, 1, 2),
            Line.of(1, 1, 0, 1, 1),
            Line.of(1, 1, 2, 1, 1),
            Line.of(0, 0, 2, 0, 0),
            Line.of(2, 2, 0, 2, 2),
            Line.of(0, 2, 2, 2, 0));

    private ClassicFruit20() {
    }
}
