package dev.slotrgs.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class SeededRngTest extends RngContractTest {

    @Override
    Rng createRng() {
        return new SeededRng(42);
    }

    @Test
    void sameSeedGivesSameSequence() {
        assertEquals(draw(new SeededRng(42), 100), draw(new SeededRng(42), 100));
    }

    @Test
    void differentSeedsGiveDifferentSequences() {
        assertNotEquals(draw(new SeededRng(1), 100), draw(new SeededRng(2), 100));
    }

    @Test
    void distributionIsUniform() {
        int bound = 10;
        int draws = 100_000;
        long[] counts = new long[bound];
        Rng rng = new SeededRng(42);
        for (int i = 0; i < draws; i++) {
            counts[rng.nextInt(bound)]++;
        }

        double expected = (double) draws / bound;
        double chiSquare = 0;
        for (long count : counts) {
            chiSquare += (count - expected) * (count - expected) / expected;
        }

        // Critical value for 9 degrees of freedom at p = 0.001.
        assertTrue(chiSquare < 27.88, "Chi-square too high: " + chiSquare);
    }

    private static List<Integer> draw(Rng rng, int count) {
        List<Integer> values = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            values.add(rng.nextInt(1_000_000));
        }
        return values;
    }
}
