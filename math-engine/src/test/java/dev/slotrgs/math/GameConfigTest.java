package dev.slotrgs.math;

import static dev.slotrgs.math.Symbol.CHERRY;
import static dev.slotrgs.math.Symbol.LEMON;
import static dev.slotrgs.math.Symbol.SEVEN;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dev.slotrgs.math.Paytable.LinePays;

class GameConfigTest {

    private static final ReelStrip REEL = new ReelStrip(List.of(CHERRY, LEMON, SEVEN));
    private static final List<ReelStrip> FIVE_REELS = Collections.nCopies(5, REEL);
    private static final Paytable PAYTABLE = new Paytable(Map.of(CHERRY, new LinePays(8, 25, 100)));
    private static final List<Line> LINES = List.of(Line.of(1, 1, 1, 1, 1), Line.of(0, 1, 2, 1, 0));

    @Test
    void acceptsValidConfig() {
        assertDoesNotThrow(() -> new GameConfig(FIVE_REELS, 3, PAYTABLE, LINES));
    }

    @Test
    void acceptsClassicFruit20LinesAndPaytable() {
        assertDoesNotThrow(() -> new GameConfig(
                FIVE_REELS, ClassicFruit20.ROWS, ClassicFruit20.PAYTABLE, ClassicFruit20.LINES));
    }

    @Test
    void rejectsWrongNumberOfReels() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameConfig(Collections.nCopies(4, REEL), 3, PAYTABLE, LINES));
    }

    @Test
    void rejectsNonPositiveRows() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameConfig(FIVE_REELS, 0, PAYTABLE, List.of(Line.of(0, 0, 0, 0, 0))));
    }

    @Test
    void rejectsReelShorterThanRows() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameConfig(FIVE_REELS, 4, PAYTABLE, LINES));
    }

    @Test
    void rejectsMissingPaytable() {
        assertThrows(NullPointerException.class,
                () -> new GameConfig(FIVE_REELS, 3, null, LINES));
    }

    @Test
    void rejectsNoLines() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameConfig(FIVE_REELS, 3, PAYTABLE, List.of()));
    }

    @Test
    void rejectsDuplicateLines() {
        assertThrows(IllegalArgumentException.class, () -> new GameConfig(
                FIVE_REELS, 3, PAYTABLE, List.of(Line.of(1, 1, 1, 1, 1), Line.of(1, 1, 1, 1, 1))));
    }

    @Test
    void rejectsLineWithWrongLength() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameConfig(FIVE_REELS, 3, PAYTABLE, List.of(Line.of(1, 1, 1, 1))));
    }

    @Test
    void rejectsLineWithRowOutOfRange() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameConfig(FIVE_REELS, 3, PAYTABLE, List.of(Line.of(1, 1, 3, 1, 1))));
    }
}
