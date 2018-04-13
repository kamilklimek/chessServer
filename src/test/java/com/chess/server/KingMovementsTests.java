package com.chess.server;

import com.chess.server.comparators.PointComparator;
import com.chess.server.figures.King;
import com.chess.server.figures.Point;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.*;

public class KingMovementsTests extends TestCase {

    @Test
    public void testNormalAvailableMovements(){
        King king = new King(new Point(5,5), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(4, 4),
                        new Point(5, 4),
                        new Point(6, 4),
                        new Point(4, 5),
                        new Point(6, 5),
                        new Point(4,6),
                        new Point(5, 6),
                        new Point(6,6)
                )
        );

        king.calculateAllAvailableMovements();
        List<Point> pointsFromKing = king.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromKing, new PointComparator());


        assertEquals(expectedPoints, pointsFromKing);

    };

    @Test
    public void testInDownLeftCornerAvaiableMovements(){
        King king = new King(new Point(0, 0), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(1, 0),
                        new Point(0, 1),
                        new Point(1, 1)
                )
        );

        king.calculateAllAvailableMovements();
        List<Point> pointsFromKing = king.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromKing, new PointComparator());


        assertEquals(expectedPoints, pointsFromKing);

    }

    @Test
    public void testInDownRightCornerAvaiableMovements(){
        King king = new King(new Point(7, 0), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(6, 0),
                        new Point(6, 1),
                        new Point(7, 1)
                )
        );

        king.calculateAllAvailableMovements();
        List<Point> pointsFromKing = king.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromKing, new PointComparator());


        assertEquals(expectedPoints, pointsFromKing);
    }

    @Test
    public void testInUpRightCornerAvaiableMovements(){
        King king = new King(new Point(7, 7), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(6, 7),
                        new Point(6, 6),
                        new Point(7, 6)
                )
        );

        king.calculateAllAvailableMovements();
        List<Point> pointsFromKing = king.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromKing, new PointComparator());


        assertEquals(expectedPoints, pointsFromKing);
    }

    @Test
    public void testInLeftUpCornerAvaiableMovements(){
        King king = new King(new Point(0, 7), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(0, 6),
                        new Point(1, 6),
                        new Point(1, 7)
                )
        );

        king.calculateAllAvailableMovements();
        List<Point> pointsFromKing = king.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromKing, new PointComparator());


        assertEquals(expectedPoints, pointsFromKing);
    }



}
