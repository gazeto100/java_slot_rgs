package dev.slotrgs.math;

import java.security.SecureRandom;

/**
 * Production RNG backed by {@link SecureRandom}: unpredictable and thread-safe.
 */
public final class SecureRng implements Rng {

    private final SecureRandom random = new SecureRandom();

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
