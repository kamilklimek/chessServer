package com.chess.server;

import com.chess.server.comparators.PointComparator;
import com.chess.server.models.Point;
import com.chess.server.models.figures.Bishop;
import com.chess.server.models.figures.Rook;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RookMovementsTests extends TestCase {

    @Test
    public void testStandardMovements(){
        Rook rook = new Rook(new Point(4, 4), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(4,0),
                        new Point(4,1),
                        new Point(4,2),
                        new Point(4,3),
                        new Point(4,5),
                        new Point(4,6),
                        new Point(4,7),
                        new Point(0, 4),
                        new Point(1, 4),
                        new Point(2, 4),
                        new Point(3, 4),
                        new Point(5, 4),
                        new Point(6, 4),
                        new Point(7, 4)
                )
        );

        rook.calculateAllAvailableMovements();
        List<Point> pointsFromRook = rook.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromRook, new PointComparator());


        assertEquals(expectedPoints, pointsFromRook);


    }

    @Test
    public void testLeftUpCorner(){
        Rook rook = new Rook(new Point(0, 0), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(0,4),
                        new Point(0,1),
                        new Point(0,2),
                        new Point(0,3),
                        new Point(0,5),
                        new Point(0,6),
                        new Point(0,7),
                        new Point(4, 0),
                        new Point(1, 0),
                        new Point(2, 0),
                        new Point(3, 0),
                        new Point(5, 0),
                        new Point(6, 0),
                        new Point(7, 0)
                )
        );

        rook.calculateAllAvailableMovements();
        List<Point> pointsFromRook = rook.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromRook, new PointComparator());


        assertEquals(expectedPoints, pointsFromRook);


    }



}
