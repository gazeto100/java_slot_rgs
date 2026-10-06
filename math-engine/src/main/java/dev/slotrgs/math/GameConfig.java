package dev.slotrgs.math;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Everything that defines a slot game's math: reels, visible rows, paytable and paylines.
 */
public record GameConfig(List<ReelStrip> reels, int rows, Paytable paytable, List<Line> lines) {

    public static final int REELS = 5;

    public GameConfig {
        reels = List.copyOf(reels);
        lines = List.copyOf(lines);
        Objects.requireNonNull(paytable, "paytable");

        if (reels.size() != REELS) {
            throw new IllegalArgumentException(
                    "Expected " + REELS + " reels, got " + reels.size());
        }
        if (rows < 1) {
            throw new IllegalArgumentException("Rows must be positive, got " + rows);
        }
        for (int reel = 0; reel < reels.size(); reel++) {
            if (reels.get(reel).size() < rows) {
                throw new IllegalArgumentException(
                        "Reel " + reel + " has fewer symbols than " + rows + " rows");
            }
        }

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("At least one line is required");
        }
        if (new HashSet<>(lines).size() != lines.size()) {
            throw new IllegalArgumentException("Lines must be distinct");
        }
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            if (line.rows().size() != reels.size()) {
                throw new IllegalArgumentException(
                        "Line " + i + " has " + line.rows().size() + " positions, expected " + reels.size());
            }
            for (int row : line.rows()) {
                if (row < 0 || row >= rows) {
                    throw new IllegalArgumentException(
                            "Line " + i + " has row " + row + " out of range [0, " + rows + ")");
                }
            }
        }
    }
}
