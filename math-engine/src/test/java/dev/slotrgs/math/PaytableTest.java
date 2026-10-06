package dev.slotrgs.math;

import static dev.slotrgs.math.Symbol.CHERRY;
import static dev.slotrgs.math.Symbol.SEVEN;
import static dev.slotrgs.math.Symbol.WILD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import dev.slotrgs.math.Paytable.LinePays;

class PaytableTest {

    private final Paytable paytable = new Paytable(Map.of(
            CHERRY, new LinePays(8, 25, 100),
            SEVEN, new LinePays(50, 200, 1000)));

    @ParameterizedTest
    @CsvSource({
            "CHERRY, 3, 8",
            "CHERRY, 4, 25",
            "CHERRY, 5, 100",
            "SEVEN, 3, 50",
            "SEVEN, 4, 200",
            "SEVEN, 5, 1000"})
    void returnsMultiplierForThreeFourAndFive(Symbol symbol, int count, int expected) {
        assertEquals(expected, paytable.multiplier(symbol, count));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void fewerThanThreeDoesNotPay(int count) {
        assertEquals(0, paytable.multiplier(SEVEN, count));
    }

    @Test
    void symbolWithoutEntryDoesNotPay() {
        assertEquals(0, paytable.multiplier(WILD, 5));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 6})
    void rejectsCountOutOfRange(int count) {
        assertThrows(IllegalArgumentException.class, () -> paytable.multiplier(CHERRY, count));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 25, 100",
            "8, 5, 100",
            "8, 25, 10"})
    void linePaysMustBePositiveAndNonDecreasing(int three, int four, int five) {
        assertThrows(IllegalArgumentException.class, () -> new LinePays(three, four, five));
    }

    @Test
    void rejectsEmptyPaytable() {
        assertThrows(IllegalArgumentException.class, () -> new Paytable(Map.of()));
    }

    @Test
    void rejectsNullPays() {
        Map<Symbol, LinePays> source = new HashMap<>();
        source.put(CHERRY, null);

        assertThrows(NullPointerException.class, () -> new Paytable(source));
    }

    @Test
    void isNotAffectedByChangesToSourceMap() {
        Map<Symbol, LinePays> source = new HashMap<>(Map.of(CHERRY, new LinePays(8, 25, 100)));
        Paytable copy = new Paytable(source);

        source.put(CHERRY, new LinePays(1, 1, 1));

        assertEquals(8, copy.multiplier(CHERRY, 3));
    }

    @Test
    void paysCannotBeModified() {
        assertThrows(UnsupportedOperationException.class,
                () -> paytable.pays().put(WILD, new LinePays(1, 1, 1)));
    }
}
