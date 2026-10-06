package dev.slotrgs.math;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Line pays: multiplier of the line bet for 3, 4 or 5 matching symbols from the leftmost reel.
 * Symbols without an entry (e.g. WILD, SCATTER) never pay on a line.
 */
public record Paytable(Map<Symbol, LinePays> pays) {

    public static final int MAX_COUNT = 5;

    public record LinePays(int three, int four, int five) {

        public LinePays {
            if (three <= 0 || four < three || five < four) {
                throw new IllegalArgumentException(
                        "Pays must be positive and non-decreasing: " + three + "/" + four + "/" + five);
            }
        }
    }

    public Paytable {
        Map<Symbol, LinePays> copy = Map.copyOf(pays);
        if (copy.isEmpty()) {
            throw new IllegalArgumentException("Paytable must not be empty");
        }
        pays = Collections.unmodifiableMap(new EnumMap<>(copy));
    }

    /**
     * Returns the line-bet multiplier for {@code count} consecutive {@code symbol}s
     * starting from the leftmost reel, or 0 if the combination does not pay.
     */
    public int multiplier(Symbol symbol, int count) {
        if (count < 1 || count > MAX_COUNT) {
            throw new IllegalArgumentException("Count " + count + " out of range [1, " + MAX_COUNT + "]");
        }
        LinePays linePays = pays.get(symbol);
        if (linePays == null) {
            return 0;
        }
        return switch (count) {
            case 3 -> linePays.three();
            case 4 -> linePays.four();
            case 5 -> linePays.five();
            default -> 0;
        };
    }
}
