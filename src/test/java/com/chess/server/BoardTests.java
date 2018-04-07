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
        List<Point> pointsFromBoard = board.getAllAvaiableMovements(new Point(0, 0));

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
}
