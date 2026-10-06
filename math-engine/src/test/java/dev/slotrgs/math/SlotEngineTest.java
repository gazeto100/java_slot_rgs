package dev.slotrgs.math;

import static dev.slotrgs.math.Symbol.CHERRY;
import static dev.slotrgs.math.Symbol.GRAPES;
import static dev.slotrgs.math.Symbol.LEMON;
import static dev.slotrgs.math.Symbol.ORANGE;
import static dev.slotrgs.math.Symbol.PLUM;
import static dev.slotrgs.math.Symbol.SEVEN;
import static dev.slotrgs.math.Symbol.WATERMELON;
import static dev.slotrgs.math.Symbol.WILD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Uses the ClassicFruit20 paytable and lines. Grids are written as the player sees them: top, middle, bottom row.
 */
class SlotEngineTest {

    private static final String GAME_ID = "test-game";
    private static final List<Integer> ZERO_STOPS = List.of(0, 0, 0, 0, 0);

    @Test
    void noMatchingSymbolsGivesNoWin() {
        SlotEngine engine = engineShowing(
                List.of(CHERRY, LEMON, ORANGE, PLUM, GRAPES),
                List.of(CHERRY, LEMON, ORANGE, PLUM, GRAPES),
                List.of(CHERRY, LEMON, ORANGE, PLUM, GRAPES));

        SpinResult result = engine.evaluate(ZERO_STOPS, 10);

        assertEquals(List.of(), result.lineWins());
        assertEquals(0, result.totalWin());
    }

    @Test
    void countsOnlyConsecutiveSymbolsFromLeftmostReel() {
        SlotEngine engine = engineShowing(
                List.of(CHERRY, LEMON, ORANGE, PLUM, GRAPES),
                List.of(SEVEN, SEVEN, SEVEN, PLUM, SEVEN),
                List.of(CHERRY, LEMON, ORANGE, PLUM, GRAPES));

        SpinResult result = engine.evaluate(ZERO_STOPS, 10);

        assertEquals(List.of(new LineWin(0, SEVEN, 3, 500)), result.lineWins());
    }

    @Test
    void fiveOfAKindPaysFiveMultiplier() {
        SlotEngine engine = engineShowing(
                List.of(CHERRY, CHERRY, CHERRY, CHERRY, CHERRY),
                List.of(LEMON, ORANGE, PLUM, GRAPES, WATERMELON),
                List.of(LEMON, ORANGE, PLUM, GRAPES, WATERMELON));

        SpinResult result = engine.evaluate(ZERO_STOPS, 1);

        assertEquals(List.of(new LineWin(1, CHERRY, 5, 100)), result.lineWins());
    }

    @Test
    void multipleLineWinsAreSummed() {
        SlotEngine engine = engineShowing(
                List.of(GRAPES, GRAPES, GRAPES, GRAPES, CHERRY),
                List.of(LEMON, CHERRY, PLUM, LEMON, CHERRY),
                List.of(ORANGE, ORANGE, ORANGE, SEVEN, CHERRY));

        SpinResult result = engine.evaluate(ZERO_STOPS, 5);

        assertEquals(List.of(
                new LineWin(1, GRAPES, 4, 400),
                new LineWin(2, ORANGE, 3, 50)), result.lineWins());
        assertEquals(450, result.totalWin());
    }

    @Test
    void wildSubstitutesForLineSymbol() {
        SlotEngine engine = engineShowing(
                List.of(CHERRY, LEMON, ORANGE, PLUM, GRAPES),
                List.of(SEVEN, WILD, SEVEN, WILD, SEVEN),
                List.of(CHERRY, LEMON, ORANGE, PLUM, GRAPES));

        SpinResult result = engine.evaluate(ZERO_STOPS, 1);

        assertEquals(List.of(new LineWin(0, SEVEN, 5, 1000)), result.lineWins());
    }

    @Test
    void wildSubstitutesOnEveryLineThroughIt() {
        SlotEngine engine = engineShowing(
                List.of(CHERRY, WILD, WILD, LEMON, GRAPES),
                List.of(PLUM, ORANGE, GRAPES, ORANGE, LEMON),
                List.of(SEVEN, GRAPES, ORANGE, GRAPES, CHERRY));

        SpinResult result = engine.evaluate(ZERO_STOPS, 1);

        // Line 1 (top row) and line 5 (middle, then top on reels 2-4) both pass through the two WILDs.
        assertEquals(List.of(
                new LineWin(1, CHERRY, 3, 8),
                new LineWin(5, PLUM, 3, 10)), result.lineWins());
    }

    @Test
    void eachReelUsesItsOwnStop() {
        ReelStrip strip = new ReelStrip(List.of(SEVEN, CHERRY, LEMON, ORANGE, PLUM));
        SlotEngine engine = new SlotEngine(new GameConfig(GAME_ID, 1,
                Collections.nCopies(5, strip), 3, ClassicFruit20.PAYTABLE, ClassicFruit20.LINES));

        // Reels 1-3 show SEVEN/CHERRY/LEMON; reels 4-5 show PLUM/SEVEN/CHERRY.
        SpinResult result = engine.evaluate(List.of(0, 0, 0, 4, 4), 1);

        assertEquals(List.of(
                new LineWin(0, CHERRY, 3, 8),
                new LineWin(1, SEVEN, 3, 50),
                new LineWin(2, LEMON, 3, 8)), result.lineWins());
    }

    @Test
    void rejectsWrongNumberOfStops() {
        SlotEngine engine = anyEngine();
        assertThrows(IllegalArgumentException.class, () -> engine.evaluate(List.of(0, 0, 0, 0), 1));
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void rejectsNonPositiveLineBet(long lineBet) {
        SlotEngine engine = anyEngine();
        assertThrows(IllegalArgumentException.class, () -> engine.evaluate(ZERO_STOPS, lineBet));
    }

    @Test
    void rejectsStopOutOfRange() {
        SlotEngine engine = anyEngine();
        assertThrows(IllegalArgumentException.class, () -> engine.evaluate(List.of(0, 0, 3, 0, 0), 1));
    }

    private static SlotEngine anyEngine() {
        return engineShowing(
                List.of(CHERRY, LEMON, ORANGE, PLUM, GRAPES),
                List.of(CHERRY, LEMON, ORANGE, PLUM, GRAPES),
                List.of(CHERRY, LEMON, ORANGE, PLUM, GRAPES));
    }

    /** Builds 3-symbol reels so that stops 0,0,0,0,0 show exactly the given rows. */
    private static SlotEngine engineShowing(List<Symbol> top, List<Symbol> middle, List<Symbol> bottom) {
        List<ReelStrip> reels = new ArrayList<>();
        for (int reel = 0; reel < GameConfig.REELS; reel++) {
            reels.add(new ReelStrip(List.of(top.get(reel), middle.get(reel), bottom.get(reel))));
        }
        return new SlotEngine(new GameConfig(GAME_ID, 1, reels, 3, ClassicFruit20.PAYTABLE, ClassicFruit20.LINES));
    }
}
