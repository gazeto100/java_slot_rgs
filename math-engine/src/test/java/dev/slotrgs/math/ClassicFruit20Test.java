package dev.slotrgs.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ClassicFruit20Test {

    @Test
    void hasTwentyLines() {
        assertEquals(20, ClassicFruit20.LINES.size());
    }

    @ParameterizedTest
    @CsvSource({
            "CHERRY, 8, 25, 100",
            "LEMON, 8, 25, 100",
            "ORANGE, 10, 30, 150",
            "PLUM, 10, 30, 150",
            "GRAPES, 20, 80, 300",
            "WATERMELON, 20, 80, 300",
            "SEVEN, 50, 200, 1000",
            "WILD, 0, 0, 0",
            "SCATTER, 0, 0, 0"})
    void paytableMatchesSpec(Symbol symbol, int three, int four, int five) {
        assertEquals(three, ClassicFruit20.PAYTABLE.multiplier(symbol, 3));
        assertEquals(four, ClassicFruit20.PAYTABLE.multiplier(symbol, 4));
        assertEquals(five, ClassicFruit20.PAYTABLE.multiplier(symbol, 5));
    }
}
