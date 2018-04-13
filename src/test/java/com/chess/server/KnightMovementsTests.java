package com.chess.server;

import com.chess.server.comparators.PointComparator;
import com.chess.server.figures.Knight;
import com.chess.server.figures.Point;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KnightMovementsTests extends TestCase{

    @Test
    public void testStandardKnightMovements(){
        Knight knight = new Knight(new Point(4,4), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(3,2),
                        new Point(5,2),
                        new Point(2,3),
                        new Point(6,3),
                        new Point(2,5),
                        new Point(6,5),
                        new Point(3,6),
                        new Point(5,6)
                )
        );

        knight.calculateAllAvailableMovements();
        List<Point> pointsFromKnight = knight.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromKnight, new PointComparator());


        assertEquals(expectedPoints, pointsFromKnight);



    }

    @Test
    public void testLeftUpCornerKnightMovements(){
        Knight knight = new Knight(new Point(0,0), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(2,1),
                        new Point(1,2)
                )
        );

        knight.calculateAllAvailableMovements();
        List<Point> pointsFromKnight = knight.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromKnight, new PointComparator());


        assertEquals(expectedPoints, pointsFromKnight);



    }

    @Test
    public void testInLeftMiddleMovements(){
        Knight knight = new Knight(new Point(0,3), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(1,1),
                        new Point(2,2),
                        new Point(2,4),
                        new Point(1,5)
                )
        );

        knight.calculateAllAvailableMovements();
        List<Point> pointsFromKnight = knight.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromKnight, new PointComparator());


        assertEquals(expectedPoints, pointsFromKnight);



    }


}
