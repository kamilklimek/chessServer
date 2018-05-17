package com.chess.server.game;

import com.chess.server.figures.Figure;
import com.chess.server.figures.Point;

import java.util.List;
import java.util.Scanner;

public class Game extends Board {

    private Player playerOne;
    private Player playerTwo;
    private boolean isTurnPlayersOne;
    private int surrender;

    public Game(Player p1, Player p2){
        super();
        this.playerOne = p1;
        this.playerTwo = p2;
        initGame();
        surrender = 0;

    }
    public Game(String fileName){
        super(fileName);
        initGame();
    }

    private void initGame() {
        isTurnPlayersOne = false;
    }


    public boolean isTurnPlayerOne(){
        return isTurnPlayersOne;
    }

    public boolean hasNextTurn(){
        return checkIsCheckMate(isTurnPlayersOne) ? false : surrender > 0 ? false : true;
    }

    public void nextTurn(){
        isTurnPlayersOne = !isTurnPlayersOne;
    }

    public int move(Point from, Point to, boolean isWhite){
        boolean isNotMyMove = isTurnPlayersOne != isWhite;
        if(isNotMyMove){
            return -1;
        }

        if(!hasNextTurn()){
            return -1;
        }


        int moveResult = moveFigure(from, to);
        if(moveResult>=0){
            nextTurn();
        }

        return moveResult;

    }

    private boolean isCheckMate(){
        return checkIsCheckMate(isTurnPlayersOne);
    }


    public void surrender(boolean isWhite) {
        surrender = isWhite ? 1 : 2;
    }
}
