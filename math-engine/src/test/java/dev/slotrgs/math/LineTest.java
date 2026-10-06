package dev.slotrgs.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class LineTest {

    @Test
    void ofCreatesLineWithRowPerReel() {
        Line line = Line.of(0, 1, 2, 1, 0);

        assertEquals(List.of(0, 1, 2, 1, 0), line.rows());
        assertEquals(2, line.rowOn(2));
    }

    @Test
    void linesWithSameRowsAreEqual() {
        assertEquals(Line.of(1, 1, 1, 1, 1), Line.of(1, 1, 1, 1, 1));
    }

    @Test
    void rejectsEmptyLine() {
        assertThrows(IllegalArgumentException.class, () -> Line.of());
    }
}
