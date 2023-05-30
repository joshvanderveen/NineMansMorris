package test.model.board;

import model.board.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoordinateTest {
    @Test
    @DisplayName("Check compare coordinates compares actual coordinates and not references")
    void compareCoordinate() {
        Coordinate coordinate1 = new Coordinate(1, 1);
        Coordinate coordinate2 = new Coordinate(1, 2);
        Coordinate coordinate3 = new Coordinate(3, 2);
        Coordinate coordinate4 = new Coordinate(1, 1);

        assertTrue(coordinate1.compareCoordinate(coordinate4));
        assertFalse(coordinate1.compareCoordinate(coordinate2));
        assertFalse(coordinate1.compareCoordinate(coordinate3));
    }
}