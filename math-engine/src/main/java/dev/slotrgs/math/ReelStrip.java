package dev.slotrgs.math;

import java.util.ArrayList;
import java.util.List;

/**
 * A circular reel strip. Position {@code size() - 1} is followed by position 0.
 *
 * <p>A stop is the index of the symbol shown in the top visible row.
 */
public record ReelStrip(List<Symbol> symbols) {

    public ReelStrip {
        symbols = List.copyOf(symbols);
        if (symbols.isEmpty()) {
            throw new IllegalArgumentException("Reel strip must not be empty");
        }
    }

    public int size() {
        return symbols.size();
    }

    /**
     * Returns the {@code rows} symbols visible when the reel stops at {@code stop},
     * from top to bottom, wrapping around the end of the strip.
     */
    public List<Symbol> window(int stop, int rows) {
        if (stop < 0 || stop >= size()) {
            throw new IllegalArgumentException(
                    "Stop " + stop + " out of range [0, " + size() + ")");
        }
        if (rows < 1 || rows > size()) {
            throw new IllegalArgumentException(
                    "Rows " + rows + " out of range [1, " + size() + "]");
        }

        List<Symbol> visible = new ArrayList<>(rows);
        for (int row = 0; row < rows; row++) {
            visible.add(symbols.get((stop + row) % size()));
        }
        return List.copyOf(visible);
    }
}
