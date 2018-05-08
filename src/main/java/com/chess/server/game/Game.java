package com.chess.server.game;

import com.chess.server.figures.Figure;
import com.chess.server.figures.Point;

import java.util.List;
import java.util.Scanner;

public class Game extends Board {

    Player playerOne;
    Player playerTwo;
    boolean isTurnPlayersOne;

    public Game(){
        super();
        initGame();

    }
    public Game(String fileName){
        super(fileName);
        initGame();
    }

    private void initGame() {
        playerOne = new Player("P1");
        playerTwo = new Player("P2");
        isTurnPlayersOne = false;
    }



    public boolean isTurnPlayerOne(){
        return isTurnPlayersOne;
    }

    public boolean hasNextTurn(){
        return isCheckMate(isTurnPlayersOne) ? false : true;
    }

    public void nextTurn(){
        isTurnPlayersOne = !isTurnPlayersOne;
    }

    public int move(Point from, Point to){
        return moveFigure(from, to);
    }

    private boolean isCheckMate(boolean isTurnPlayersOne){
        return checkIsCheckMate(isTurnPlayersOne);
    }

    public boolean play(){

        Scanner in = new Scanner(System.in);
        while(true){

            if(checkIsCheckMate(isTurnPlayersOne)){
                System.out.println("Szach mat???");
                return !isTurnPlayerOne();
            }

            System.out.println("Stan rozgrywki: ");
            display();

            System.out.println("Wszystkie twoje figury: ");
            List<Figure> figure = getAllFiguresByColor(isTurnPlayersOne);

            figure.stream()
                    .forEach(System.out::println);

            while(true){
                int x, y;
                int destinationX, destinationY;

                System.out.println("Skad [x]: " );
                x = in.nextInt();

                System.out.println("Skad [Y]: " );
                y = in.nextInt();

                System.out.println("Dokad [x]: " );
                destinationX = in.nextInt();

                System.out.println("Dokad [y]: " );
                destinationY = in.nextInt();

                int movedResult = moveFigure(new Point(x,y), new Point(destinationX, destinationY));

                boolean moved = movedResult == 0;
                boolean movedAndBeatenUp =  movedResult == 1;
                boolean cantMove = movedResult == -1;
                boolean pawnArrived = movedResult == 3;
                boolean castling = movedResult == 2;

                if(moved){
                    System.out.println("Udalo sie przesunac. ");
                    break;
                }else if(movedAndBeatenUp){
                    System.out.println("Udalo sie przesunac i zbic");
                    break;
                }else if(cantMove){
                    System.out.println("Nie mozesz sie tam ruszyc");
                }else if(castling){
                    System.out.println("Roszada");
                    break;
                }else if(pawnArrived){
                    //tutaj zrobic wymiane piona
                    System.out.println("Wymiana piona");
                }
            }

            isTurnPlayersOne = !isTurnPlayersOne;

        }


    }


}
