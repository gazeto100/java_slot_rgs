package dev.slotrgs.math;

import java.util.List;

/**
 * Outcome of evaluating one set of reel stops.
 */
public record SpinResult(List<LineWin> lineWins) {

    public SpinResult {
        lineWins = List.copyOf(lineWins);
    }

    /** Sum of all wins in cents. */
    public long totalWin() {
        long total = 0;
        for (LineWin win : lineWins) {
            total = Math.addExact(total, win.amount());
        }
        return total;
    }
}
