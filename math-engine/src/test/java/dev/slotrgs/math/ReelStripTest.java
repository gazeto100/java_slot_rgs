package dev.slotrgs.math;

import static dev.slotrgs.math.Symbol.CHERRY;
import static dev.slotrgs.math.Symbol.LEMON;
import static dev.slotrgs.math.Symbol.ORANGE;
import static dev.slotrgs.math.Symbol.PLUM;
import static dev.slotrgs.math.Symbol.SEVEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReelStripTest {

    private final ReelStrip strip = new ReelStrip(List.of(CHERRY, LEMON, ORANGE, PLUM, SEVEN));

    @Test
    void sizeIsNumberOfSymbols() {
        assertEquals(5, strip.size());
    }

    @Test
    void windowReturnsConsecutiveSymbolsFromStop() {
        assertEquals(List.of(LEMON, ORANGE, PLUM), strip.window(1, 3));
    }

    @Test
    void windowWrapsAroundEndOfStrip() {
        assertEquals(List.of(PLUM, SEVEN, CHERRY), strip.window(3, 3));
        assertEquals(List.of(SEVEN, CHERRY, LEMON), strip.window(4, 3));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 5})
    void windowRejectsStopOutOfRange(int stop) {
        assertThrows(IllegalArgumentException.class, () -> strip.window(stop, 3));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 6})
    void windowRejectsRowsOutOfRange(int rows) {
        assertThrows(IllegalArgumentException.class, () -> strip.window(0, rows));
    }

    @Test
    void rejectsEmptyStrip() {
        assertThrows(IllegalArgumentException.class, () -> new ReelStrip(List.of()));
    }

    @Test
    void rejectsNullSymbol() {
        assertThrows(NullPointerException.class,
                () -> new ReelStrip(Arrays.asList(CHERRY, null)));
    }

    @Test
    void isNotAffectedByChangesToSourceList() {
        List<Symbol> source = new ArrayList<>(List.of(CHERRY, LEMON));
        ReelStrip copy = new ReelStrip(source);

        source.set(0, SEVEN);

        assertEquals(List.of(CHERRY, LEMON), copy.symbols());
    }

    @Test
    void symbolsCannotBeModified() {
        assertThrows(UnsupportedOperationException.class, () -> strip.symbols().add(CHERRY));
    }
}
