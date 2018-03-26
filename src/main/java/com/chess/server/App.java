package com.chess.server;

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

        Bishop bishop = new Bishop(new Point(4,4));

        bishop.calculateAllAvailableMovements();

    }
}
