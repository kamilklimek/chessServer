package com.chess.server;

import com.chess.server.models.Board;
import com.chess.server.models.Figure;
import com.chess.server.models.Point;
import com.chess.server.models.figures.Bishop;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        Board board = new Board();
        board.display();

        System.out.println();
        board.moveFigure(new Point(0,0), new Point(0,3));
        board.moveFigure(new Point(4,0), new Point(4, 4));
        board.display();


    }
}
