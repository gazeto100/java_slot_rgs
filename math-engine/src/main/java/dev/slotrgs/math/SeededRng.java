package dev.slotrgs.math;

import java.util.SplittableRandom;

/**
 * Deterministic RNG for tests and simulations: the same seed always gives the same sequence.
 * Not thread-safe; use one instance per thread.
 */
public final class SeededRng implements Rng {

    private final SplittableRandom random;

    public SeededRng(long seed) {
        this.random = new SplittableRandom(seed);
    }

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
