package dev.slotrgs.math;

/**
 * A winning payline.
 *
 * @param lineIndex index into {@link GameConfig#lines()}, 0-based
 * @param symbol    the paying symbol
 * @param count     how many consecutive symbols from the leftmost reel
 * @param amount    win in cents
 */
public record LineWin(int lineIndex, Symbol symbol, int count, long amount) {
}
