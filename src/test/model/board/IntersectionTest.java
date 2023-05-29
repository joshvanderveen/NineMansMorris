package test.model.board;

import junit.framework.TestCase;
import model.Player;
import model.board.Coordinate;
import model.board.Intersection;
import model.board.Path;
import model.board.Piece;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class IntersectionTest {

    Coordinate coordinate1;
    Coordinate coordinate2;
    Coordinate coordinate3;
    Intersection intersection1;
    Intersection intersection2;
    Intersection intersection3;
    Path path1;
    Path path2;
    Path path3;
    Path path4;
    Piece piece1;
    Piece piece2;
    Piece piece3;
    Player player1;
    Player player2;

    @BeforeEach
    public void setUp() {
        coordinate1 = new Coordinate(1, 1);
        coordinate2 = new Coordinate(1, 2);
        coordinate3 = new Coordinate(1, 3);

        intersection1 = new Intersection(1, coordinate1);
        intersection2 = new Intersection(2, coordinate2);
        intersection3 = new Intersection(3, coordinate3);

        path1 = new Path(intersection1, intersection2);
        path2 = new Path(intersection2, intersection1);
        path3 = new Path(intersection2, intersection3);
        path4 = new Path(intersection3, intersection2);

        intersection1.addPath(path1);
        intersection2.addPath(path2);
        intersection2.addPath(path3);
        intersection3.addPath(path4);

        player1 = new Player("Player 1", Color.RED);
        player2 = new Player("Player 2", Color.BLUE);

        piece1 = new Piece(player1);
        piece2 = new Piece(player1);
        piece3 = new Piece(player1);
    }


    @Test
    @DisplayName("Check intersection is given id")
    public void getId() {
        assertEquals(1, intersection1.getId());
        assertEquals(2, intersection2.getId());
    }

    @Test
    @DisplayName("Check intersection has correct coordinates")
    public void getCoordinates() {
        assertEquals(new Coordinate(1, 1), intersection1.getCoordinate());
        assertEquals(new Coordinate(1, 2), intersection2.getCoordinate());
    }

    @Test
    @DisplayName("Check intersection has correct amount of paths")
    public void getPaths() {
        assertEquals(1, intersection1.getPaths().size());
        assertEquals(2, intersection2.getPaths().size());
        assertEquals(1, intersection1.getPaths().size());
    }

    @Test
    @DisplayName("Check if two intersections are connected")
    public void checkConnected() {
        assertEquals(true, intersection1.checkDirectlyConnected(intersection2));
        assertEquals(true, intersection2.checkDirectlyConnected(intersection1));

        assertEquals(false, intersection1.checkDirectlyConnected(intersection3));
        assertEquals(false, intersection3.checkDirectlyConnected(intersection1));

        assertEquals(true, intersection1.checkConnectedInALine(intersection3));
        assertEquals(true, intersection3.checkConnectedInALine(intersection1));
        assertEquals(true, intersection1.checkConnectedInALine(intersection2));
    }

    @Test
    @DisplayName("Check an intersection is at a specified coordinate")
    public void checkIntersectionAtCoordinate() {
        assertEquals(true, intersection1.intersectionAtCoordinate(coordinate1));
        assertEquals(false, intersection1.intersectionAtCoordinate(coordinate2));
    }

    @Test
    @DisplayName("Check if an intersection is in a mill")
    public void checkIntersectionInMill() {
        intersection1.setPiece(piece1);
        intersection2.setPiece(piece2);
        intersection3.setPiece(piece3);
        ArrayList<Intersection> intersections = new ArrayList<>();

        ArrayList<Intersection> mill =  intersection1.checkIfConnectedMill(intersections, 3);

        ArrayList<Intersection> expected = new ArrayList<>();
        expected.add(intersection1);
        expected.add(intersection2);
        expected.add(intersection3);

        assertEquals(true, mill.containsAll(expected));
    }

    @Test
    @DisplayName("Check angle between two intersections")
    public void checkAngleBetweenIntersections() {
        assertEquals(90, intersection1.getAngleToOtherIntersection(intersection2), 1);
        assertEquals(90, intersection1.getAngleToOtherIntersection(intersection3), 1);
        assertEquals(270, intersection3.getAngleToOtherIntersection(intersection1), 1);
    }
}
