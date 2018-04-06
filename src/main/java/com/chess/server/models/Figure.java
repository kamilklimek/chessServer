package com.chess.server.models;

import java.util.*;

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
        boolean figureIsQueen = typeOfFigure.equals("QUEEN");
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
        }else if(figureIsQueen){
            availableMovements = calculateQueenMovements();
        }

    }

    private List<Point> calculateBishopMovements(){
        List<Point> movements = new ArrayList<Point>();

        int bishopPositionX = position.getPositionX();
        int bishopPositionY = position.getPositionY();

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){

                boolean movementIsNotTheSamePosition = j != bishopPositionX && i != bishopPositionY;
                boolean movementsOnDiagonal = i+bishopPositionX == j+bishopPositionY;
                boolean movementsIsOnReversedDiagonal = bishopPositionX - j == i - bishopPositionY;

                if( (movementsOnDiagonal || movementsIsOnReversedDiagonal ) && movementIsNotTheSamePosition ){
                    Point point = new Point(j, i);
                    movements.add(point);
                }


            }
        }


        return movements;
    }

    private List<Point> calculateRookMovements(){
        List<Point> movements = new ArrayList<Point>();

        int rookPositionX = position.getPositionX();
        int rookPositionY = position.getPositionY();

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                boolean pointsHorizontally = i == rookPositionY && j != rookPositionX;
                boolean pointsVertically = j == rookPositionX && i != rookPositionY;

                if(pointsHorizontally || pointsVertically){
                    Point point = new Point(j, i);
                    movements.add(point);

                }


            }
        }

        return movements;
    }

    private List<Point> calculateQueenMovements(){
        List<Point> movements = new ArrayList<Point>();

        int queenPositionX = position.getPositionX();
        int queenPositionY = position.getPositionY();


        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                boolean horizontally = queenPositionX == j && queenPositionY != i;
                boolean vertically = queenPositionY == i && queenPositionX != j;
                boolean onDiagonal = i + queenPositionX == j+queenPositionY && i != queenPositionY;
                boolean onReversedDiagonal = queenPositionX - j == i - queenPositionY && j != queenPositionX;

                if(horizontally || vertically || onDiagonal || onReversedDiagonal){
                    Point point = new Point(j,i);
                    movements.add(point);
                }

            }

        }

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
                if(i < 0 || j < 0 || j >= 8 || i >= 8 || ( i == kingPositionX && j == kingPositionY)){
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

        List<Point> mask = new ArrayList<>(Arrays.asList(
                new Point(-2,-1),
                new Point(-2,1),
                new Point(-1,2),
                new Point(1,2),
                new Point(2,1),
                new Point(2,-1),
                new Point(1,-2),
                new Point(-1,-2)
        ));


        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){

                for (Point point: mask
                     ) {
                    int pointX = point.getPositionX();
                    int pointY = point.getPositionY();

                    boolean equalsToXMask = knightPositionX + pointX == j;
                    boolean equalsToYMask = knightPositionY + pointY == i;

                    if(equalsToXMask && equalsToYMask ){
                        movements.add(new Point(j,i));
                    }



                }

            }
        }


        return movements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figure figure = (Figure) o;
        return Objects.equals(position, figure.position) &&
                Objects.equals(typeOfFigure, figure.typeOfFigure) &&
                Objects.equals(availableMovements, figure.availableMovements);
    }

    @Override
    public int hashCode() {

        return Objects.hash(position, typeOfFigure, availableMovements);
    }
}
