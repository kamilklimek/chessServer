package com.chess.server;

import com.chess.server.comparators.PointComparator;
import com.chess.server.models.Point;
import com.chess.server.models.figures.Queen;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QueenMovementsTests extends TestCase {


    @Test
    public void testLeftUpCorner(){
        Queen queen = new Queen(new Point(0, 0), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(1,1),
                        new Point(2,2),
                        new Point(3,3),
                        new Point(4,4),
                        new Point(5,5),
                        new Point(6,6),
                        new Point(7,7),
                        new Point(0,1),
                        new Point(0,2),
                        new Point(0,3),
                        new Point(0,4),
                        new Point(0,5),
                        new Point(0,6),
                        new Point(0,7),
                        new Point(1,0),
                        new Point(2,0),
                        new Point(3,0),
                        new Point(4,0),
                        new Point(5,0),
                        new Point(6,0),
                        new Point(7,0)
                )
        );

        queen.calculateAllAvailableMovements();
        List<Point> pointsFromQueen = queen.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromQueen, new PointComparator());


        assertEquals(expectedPoints, pointsFromQueen);


    }


}
