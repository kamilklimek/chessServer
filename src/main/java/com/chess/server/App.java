package com.chess.server;

import com.chess.server.figures.Figure;
import com.chess.server.figures.Point;
import com.chess.server.game.Board;

import java.util.List;
import java.util.Scanner;


public class App 
{
    public static void main( String[] args ) {

        Scanner in = new Scanner(System.in);
        Board board = new Board();
        List<Figure> figures;
        boolean turnWhitePlayer = true;
        while(true){
            board.display();

            figures = turnWhitePlayer ?  board.getAllWhiteFigures() : board.getAllBlackFigures();

            System.out.println("Wszystkie twoje figury i ich pozycje: ");
            for (Figure figure : figures
                 ) {
                System.out.println(figure.getPosition() + ", " + figure.getTypeOfFigure());
            }

            int x, y;
            int destinationX, destinationY;

            while(true){

                System.out.println("Skad [x]: " );
                x = in.nextInt();

                System.out.println("Skad [Y]: " );
                y = in.nextInt();

                System.out.println("Dokad [x]: " );
                destinationX = in.nextInt();

                System.out.println("Dokad [y]: " );
                destinationY = in.nextInt();

                boolean moved = board.moveFigure(new Point(x,y), new Point(destinationX, destinationY)) == 0;
                boolean movedAndBeatenUp = board.moveFigure(new Point(x,y), new Point(destinationX, destinationY)) == 1;
                boolean cantMove = board.moveFigure(new Point(x,y), new Point(destinationX, destinationY)) == -1;

                if(moved){
                    System.out.println("Udalo sie przesunac. ");
                    break;
                }else if(movedAndBeatenUp){
                    System.out.println("Udalo sie przesunac i zbic");
                    break;
                }else if(cantMove){
                    System.out.println("Nie mozesz sie tam ruszyc");
                }

            }


            turnWhitePlayer = !turnWhitePlayer;

        }

    }
}
