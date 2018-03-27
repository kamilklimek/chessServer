package com.chess.server;

import com.chess.server.comparators.PointComparator;
import com.chess.server.models.Point;
import com.chess.server.models.figures.Bishop;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BishopMovementsTests extends TestCase {

    @Test
    public void testStandardAvaiableMovements(){
        Bishop bishop = new Bishop(new Point(4,4));

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(0,0),
                        new Point(1,1),
                        new Point(2,2),
                        new Point(3,3),
                        new Point(5,5),
                        new Point(6,6),
                        new Point(7,7),
                        new Point(0,8),
                        new Point(1,7),
                        new Point(2,6),
                        new Point(3,5),
                        new Point(5,3),
                        new Point(6,2),
                        new Point(7,1),
                        new Point(8,0)
                )
        );

        bishop.calculateAllAvailableMovements();
        List<Point> pointsFromBishop = bishop.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromBishop, new PointComparator());


        assertEquals(expectedPoints, pointsFromBishop);
    }


}
