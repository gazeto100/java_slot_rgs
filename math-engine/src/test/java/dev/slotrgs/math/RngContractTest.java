package dev.slotrgs.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Behavior every {@link Rng} implementation must have. Each implementation's test extends this class.
 */
abstract class RngContractTest {

    abstract Rng createRng();

    @Test
    void valuesStayWithinBound() {
        Rng rng = createRng();
        for (int i = 0; i < 10_000; i++) {
            int value = rng.nextInt(7);
            assertTrue(value >= 0 && value < 7, "Out of range: " + value);
        }
    }

    @Test
    void everyValueInRangeIsProduced() {
        Rng rng = createRng();
        boolean[] seen = new boolean[10];
        for (int i = 0; i < 10_000; i++) {
            seen[rng.nextInt(10)] = true;
        }
        for (int value = 0; value < seen.length; value++) {
            assertTrue(seen[value], "Never produced " + value);
        }
    }

    @Test
    void boundOfOneAlwaysGivesZero() {
        assertEquals(0, createRng().nextInt(1));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void rejectsNonPositiveBound(int bound) {
        Rng rng = createRng();
        assertThrows(IllegalArgumentException.class, () -> rng.nextInt(bound));
    }
}
