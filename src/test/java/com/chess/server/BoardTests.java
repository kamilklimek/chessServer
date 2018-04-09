package com.chess.server;

import com.chess.server.comparators.PointComparator;
import com.chess.server.models.Board;
import com.chess.server.models.Point;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardTests extends TestCase {

    @Test
    public void testRookInGetAllAvaiableMovements(){
        Board board = new Board();

        List<Point> expectedPoints = new ArrayList<>();

        //punkt 0,0 dla wieży
        List<Point> pointsFromBoard = board.getAvailableMovements(new Point(0, 0));

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromBoard, new PointComparator());



        assertEquals(expectedPoints, pointsFromBoard);



    }

    @Test
    public void testBishopInGetAllAvailableMovements(){
        Board board = new Board();

        List<Point> expectedPoints = new ArrayList<>();

        //punkt 0,2 dla gońca
        List<Point> pointsFromBoard = board.getAvailableMovements(new Point(0,2));

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromBoard, new PointComparator());

        for(Point p : pointsFromBoard){
            System.out.println(p.toString());
        }

        for(Point p : expectedPoints){
            System.out.println(p.toString());
        }

        assertEquals(expectedPoints, pointsFromBoard);



    }

    @Test
    public void testBlackBishopInGetAllAvailableMovements(){
        Board board = new Board();

        List<Point> expectedPoints = new ArrayList<>();

        //punkt 7,2 dla czarnego gońca
        List<Point> pointsFromBoard = board.getAvailableMovements(new Point(7,2));

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromBoard, new PointComparator());

        for(Point p : pointsFromBoard){
            System.out.println(p.toString());
        }

        for(Point p : expectedPoints){
            System.out.println(p.toString());
        }

        assertEquals(expectedPoints, pointsFromBoard);



    }

    @Test
    public void testBishopCenterGetAllAvailableMovements(){
        Board board = new Board("boards/tests/bishop_center.game");

        List<Point> expectedPoints = new ArrayList<>(Arrays.asList(
                new Point(2,1),
                new Point(4,1),
                new Point(2,3),
                new Point(4,3),
                new Point(5,4)
        ));

        //punkt 3,2 dla gońca
        List<Point> pointsFromBoard = board.getAvailableMovements(new Point(3,2));

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromBoard, new PointComparator());


        assertEquals(expectedPoints, pointsFromBoard);

    }

    @Test
    public void testKingGetAllAvailableMovements(){
        Board board = new Board("boards/tests/king_leftcorner.game");

        //król 0,4
        List<Point> expectedPoints = new ArrayList<>(Arrays.asList(
                new Point(5,0),
                new Point(5,1)
        ));

        List<Point> pointsFromBoard = board.getAvailableMovements(new Point(4,0));

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromBoard, new PointComparator());
        assertEquals(expectedPoints, pointsFromBoard);

    }

    @Test
    public void testKnightGetAllAvailableMovements(){
        Board board = new Board("boards/tests/knight_leftcorent.game");

        //król 0,4
        List<Point> expectedPoints = new ArrayList<>(Arrays.asList(
                new Point(0,2),
                new Point(2,2),
                new Point(3,1)
        ));

        List<Point> pointsFromBoard = board.getAvailableMovements(new Point(1,0));

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromBoard, new PointComparator());
        assertEquals(expectedPoints, pointsFromBoard);

    }

    @Test
    public void testPawnGetAllAvailableMovements(){
        Board board = new Board("boards/tests/pawn_center.game");

        //pion 6,4
        List<Point> expectedPoints = new ArrayList<>(Arrays.asList(
                new Point(5,4),
                new Point(5,5)
        ));

        List<Point> pointsFromBoard = board.getAvailableMovements(new Point(5,6));

        Collections.sort(expectedPoints, new PointComparator());
        Collections.sort(pointsFromBoard, new PointComparator());
        assertEquals(expectedPoints, pointsFromBoard);

    }
}
