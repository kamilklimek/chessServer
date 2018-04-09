package com.chess.server;

import com.chess.server.comparators.PointComparator;
import com.chess.server.models.*;
import com.chess.server.models.figures.Bishop;

import java.util.ArrayList;
import java.util.List;


public class App 
{
    public static void main( String[] args ) {

       Board board = new Board();
        board.displayAllFigures();

       System.out.println();

       // board.moveFigure(new Point(0,0), new Point(0,3));
        //board.moveFigure(new Point(4,0), new Point(4, 4));

        board.display();

        PointComparator pointComparator = new PointComparator();
        /*Bishop bishop = new Bishop(new Point(0, 0), true);
        List<Point> getAllPoints = bishop.getAvailableMovements();

        List<Point> newListPoints = new ArrayList<>();

        for(Point point : getAllPoints){
            if(pointComparator.compare(point, new Point(3,3)) < 0){
                newListPoints.add(point);
            }

        }


        System.out.println("==== ALL POINTS ==== ");
        for (Point point : getAllPoints){
            System.out.println(point.toString());
        }

        System.out.println("==== NEWs POINTS ==== ");
        for (Point point : newListPoints){
            System.out.println(point.toString());
        }*/


        System.out.println("==== COMPARING POINTS ==== ");
        System.out.println("[4,1] > [5,0] " + pointComparator.compare(new Point(4,1), new Point(5,0)));
        System.out.println("[3,1] > [3,0] " + pointComparator.compare(new Point(3,1), new Point(3,0)));
        System.out.println("[2,1] > [1,0] " + pointComparator.compare(new Point(2,1), new Point(1,0)));
        System.out.println("[1,2] > [0,2] " + pointComparator.compare(new Point(1,2), new Point(0,2)));


        System.out.println("[5,2] > [6,2] " + pointComparator.compare(new Point(5,2), new Point(6,2)));
        System.out.println("[1,4] > [0,5] " + pointComparator.compare(new Point(1,4), new Point(0,5)));
        System.out.println("[5,4] > [6,5] " + pointComparator.compare(new Point(5,4), new Point(6,5)));
        System.out.println("[3,5] > [3,6] " + pointComparator.compare(new Point(3,5), new Point(3,6)));

    }
}
