package dev.slotrgs.math;

/**
 * Source of randomness for choosing reel stops. Nothing else in a round is random.
 */
public interface Rng {

    /**
     * Returns a uniformly distributed value in {@code [0, bound)}.
     *
     * @throws IllegalArgumentException if {@code bound} is not positive
     */
    int nextInt(int bound);
}
