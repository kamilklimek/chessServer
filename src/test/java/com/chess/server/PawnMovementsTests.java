package com.chess.server;

import com.chess.server.comparators.PointComparator;
import com.chess.server.figures.Pawn;
import com.chess.server.figures.Point;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PawnMovementsTests extends TestCase{

    @Test
    public void testWhitePawnFromStartLine(){
        Pawn pawn = new Pawn(new Point(4,1), true);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(4,2),
                        new Point(4,3),
                        new Point(3,2),
                        new Point(5,2)
                )
        );

        pawn.calculateAllAvailableMovements();
        List<Point> pointsFromPawn = pawn.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromPawn, new PointComparator());


        assertEquals(expectedPoints, pointsFromPawn);



    }

    @Test
    public void testBlackPawnWithLineStart(){
        Pawn pawn = new Pawn(new Point(4,6), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(4,5),
                        new Point(4,4),
                        new Point(3,5),
                        new Point(5,5)
                )
        );

        pawn.calculateAllAvailableMovements();
        List<Point> pointsFromPawn = pawn.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromPawn, new PointComparator());


        assertEquals(expectedPoints, pointsFromPawn);



    }

    @Test
    public void testBlackPawnFromNotStartLine(){
        Pawn pawn = new Pawn(new Point(4,5), false);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(4,4),
                        new Point(5,4),
                        new Point(3,4)
                )
        );

        pawn.calculateAllAvailableMovements();
        List<Point> pointsFromPawn = pawn.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromPawn, new PointComparator());


        assertEquals(expectedPoints, pointsFromPawn);



    }

    @Test
    public void testWhitePawnFromNotStartLine(){
        Pawn pawn = new Pawn(new Point(4,3), true);

        List<Point> expectedPoints = new ArrayList<Point>(
                Arrays.asList(
                        new Point(4,4),
                        new Point(5,4),
                        new Point(3,4)
                )
        );

        pawn.calculateAllAvailableMovements();
        List<Point> pointsFromPawn = pawn.getAvailableMovements();

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromPawn, new PointComparator());


        assertEquals(expectedPoints, pointsFromPawn);



    }
}
