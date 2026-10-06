package dev.slotrgs.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Evaluates spins for one game. The result depends only on the config, the stops and the bet,
 * so any round can be replayed from its stored stops.
 */
public final class SlotEngine {

    private final GameConfig config;

    public SlotEngine(GameConfig config) {
        this.config = Objects.requireNonNull(config, "config");
    }

    /**
     * Evaluates line wins for the given stops.
     *
     * @param stops   one stop per reel
     * @param lineBet bet per line in cents
     */
    public SpinResult evaluate(List<Integer> stops, long lineBet) {
        if (stops.size() != GameConfig.REELS) {
            throw new IllegalArgumentException(
                    "Expected " + GameConfig.REELS + " stops, got " + stops.size());
        }
        if (lineBet <= 0) {
            throw new IllegalArgumentException("Line bet must be positive, got " + lineBet);
        }

        List<List<Symbol>> grid = visibleGrid(stops);
        List<LineWin> lineWins = new ArrayList<>();
        for (int lineIndex = 0; lineIndex < config.lines().size(); lineIndex++) {
            Line line = config.lines().get(lineIndex);
            Symbol first = grid.get(0).get(line.rowOn(0));

            int count = 1;
            while (count < GameConfig.REELS && grid.get(count).get(line.rowOn(count)) == first) {
                count++;
            }

            int multiplier = config.paytable().multiplier(first, count);
            if (multiplier > 0) {
                lineWins.add(new LineWin(lineIndex, first, count, Math.multiplyExact(multiplier, lineBet)));
            }
        }
        return new SpinResult(lineWins);
    }

    /** Visible symbols indexed as {@code grid.get(reel).get(row)}. */
    private List<List<Symbol>> visibleGrid(List<Integer> stops) {
        List<List<Symbol>> grid = new ArrayList<>(GameConfig.REELS);
        for (int reel = 0; reel < GameConfig.REELS; reel++) {
            grid.add(config.reels().get(reel).window(stops.get(reel), config.rows()));
        }
        return grid;
    }
}
