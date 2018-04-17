package com.chess.server.game;

import com.chess.server.figures.Figure;
import com.chess.server.figures.Point;

import java.util.List;

public class Game extends Board {

    Player playerOne;
    Player playerTwo;
    boolean isTurnPlayersOne;

    public Game(){
        super();
        playerOne = new Player("P1");
        playerTwo = new Player("P2");
        isTurnPlayersOne = true;
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


}
