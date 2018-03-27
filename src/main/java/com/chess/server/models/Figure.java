package com.chess.server.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kamil.klimek on 20.03.2018.
 */
public class Figure {

    protected Point position;
    protected String typeOfFigure;
    protected List<Point> availableMovements;

    public Figure(Point position, String typeOfFigure){
        this.position = position;
        this.typeOfFigure = typeOfFigure;
        availableMovements = new ArrayList<Point>();
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getTypeOfFigure() {
        return typeOfFigure;
    }

    public List<Point> getAvailableMovements() {
        return availableMovements;
    }

    public void calculateAllAvailableMovements(){

        boolean figureIsPawn = typeOfFigure.equals("PAWN");
        boolean figureIsKnight = typeOfFigure.equals("KNIGHT");
        boolean figureIsKing = typeOfFigure.equals("KING");
        boolean figureIsBishop = typeOfFigure.equals("BISHOP");
        boolean figureisQueen = typeOfFigure.equals("QUEEN");
        boolean figureIsRook = typeOfFigure.equals("ROOK");

        if(figureIsPawn){
            availableMovements = calculatePawnMovements();
        }else if(figureIsBishop){
            availableMovements = calculateBishopMovements();
        }else if(figureIsKnight){
            availableMovements = calculateKnightMovements();
        }else if(figureIsRook){
            availableMovements = calculateRookMovements();
        }else if(figureIsKing){
            availableMovements = calculateKingMovements();
        }else if(figureisQueen){
            availableMovements = calculateQueenMovements();
        }

    }

    private List<Point> calculateBishopMovements(){
        List<Point> movements = new ArrayList<Point>();

        int bishopPositionX = position.getPositionX();
        int bishopPositionY = position.getPositionY();

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                boolean movementIsNotTheSamePosition = i+bishopPositionX != bishopPositionX && j+bishopPositionY != bishopPositionY;
                boolean movementsOnDiagonal = i+bishopPositionX == j+bishopPositionY;
                boolean movementsIsOnReversedDiagonal = true;
                if(movementsOnDiagonal || movementsIsOnReversedDiagonal && movementIsNotTheSamePosition ){
                    Point point = new Point(i, j);
                    movements.add(point);
                }


            }
        }


        return movements;
    }

    private List<Point> calculateRookMovements(){
        List<Point> movements = new ArrayList<Point>();

        return movements;
    }

    private List<Point> calculateQueenMovements(){
        List<Point> movements = new ArrayList<Point>();

        return movements;
    }

    protected List<Point> calculatePawnMovements(){
        List<Point> movements = new ArrayList<Point>();

        return movements;
    }

    private List<Point> calculateKingMovements(){
        List<Point> movements = new ArrayList<Point>();

        int kingPositionX = position.getPositionX();
        int kingPositionY = position.getPositionY();

        for(int i=kingPositionX-1;i<=kingPositionX+1;i++){
            for(int j=kingPositionY-1;j<=kingPositionY+1;j++){
                if(i < 0 || j < 0 || j > 8 || i > 8 || ( i == kingPositionX && j == kingPositionY)){
                    continue;
                }
                Point point = new Point(i, j);
                movements.add(point);
            }
        }

        return movements;
    }

    private List<Point> calculateKnightMovements(){
        List<Point> movements = new ArrayList<Point>();

        int knightPositionX = position.getPositionX();
        int knightPositionY = position.getPositionY();



        return movements;
    }

}
