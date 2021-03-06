package com.chess.server;

import com.chess.server.comparators.PointComparator;
import com.chess.server.figures.Bishop;
import com.chess.server.figures.Point;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BishopMovementsTests extends TestCase {

    @Test
    public void testStandardAvaiableMovements(){
       Bishop bishop = new Bishop(new Point(4,4), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(0,0),
                        new Point(1,1),
                        new Point(2,2),
                        new Point(3,3),
                        new Point(5,5),
                        new Point(6,6),
                        new Point(7,7),
                        new Point(1,7),
                        new Point(2,6),
                        new Point(3,5),
                        new Point(5,3),
                        new Point(6,2),
                        new Point(7,1)
                )
        );

        bishop.calculateAllAvailableMovements();
        List<Point> pointsFromBishop = bishop.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromBishop, new PointComparator());


        assertEquals(expectedPoints, pointsFromBishop);
    }

    @Test
    public void testLeftUpCornerAvailableMovements(){
        Bishop bishop = new Bishop(new Point(0,0), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(1,1),
                        new Point(2,2),
                        new Point(3,3),
                        new Point(4,4),
                        new Point(5,5),
                        new Point(6,6),
                        new Point(7,7)
                )
        );

        bishop.calculateAllAvailableMovements();
        List<Point> pointsFromBishop = bishop.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromBishop, new PointComparator());


        assertEquals(expectedPoints, pointsFromBishop);
    }

    @Test
    public void testRightUpCornerAvailableMovements(){
        Bishop bishop = new Bishop(new Point(7,0), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(6, 1),
                        new Point(5, 2),
                        new Point(4, 3),
                        new Point(3, 4),
                        new Point(2, 5),
                        new Point(1, 6),
                        new Point(0, 7)
                )
        );

        bishop.calculateAllAvailableMovements();
        List<Point> pointsFromBishop = bishop.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromBishop, new PointComparator());


        assertEquals(expectedPoints, pointsFromBishop);
    }




}
