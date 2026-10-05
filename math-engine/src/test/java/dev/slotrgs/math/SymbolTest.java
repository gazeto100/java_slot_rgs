package dev.slotrgs.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class SymbolTest {

    @Test
    void containsExactlyTheSymbolsFromTheGameSpec() {
        List<Symbol> expected = List.of(
                Symbol.CHERRY, Symbol.LEMON, Symbol.ORANGE, Symbol.PLUM,
                Symbol.GRAPES, Symbol.WATERMELON, Symbol.SEVEN,
                Symbol.WILD, Symbol.SCATTER);

        assertEquals(expected, List.of(Symbol.values()));
    }
}
