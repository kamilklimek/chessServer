package com.chess.server.game;

import com.chess.server.comparators.PointComparator;
import com.chess.server.figures.Figure;
import com.chess.server.figures.King;
import com.chess.server.figures.Point;
import com.chess.server.figures.Rook;

import java.util.*;

public class ChessExclusiveMovements {

    private Figure[][] boardFields;

    public ChessExclusiveMovements(Figure[][] board){
        this.boardFields = board;
    }

    public List<Point> exclusivePawnBeatenUp(Figure[][] anotherBoard, Figure figure, List<Point> availableMovements) {
        if(figure.getTypeOfFigure() != "PAWN")
            return availableMovements;

        List<Point> resultPoints = availableMovements;
        List<Point> pawnBeatenUpMask = new LinkedList<Point>(Arrays.asList(
                new Point(-1, 1),
                new Point(1, 1)
        ));

        boolean figureIsBlack = !figure.isWhite();

        if(figureIsBlack){
            for (Point p :pawnBeatenUpMask
                    ) {
                p.setPositionY(p.getPositionY()*-1);
            }
        }

        for(Point mask : pawnBeatenUpMask){
            int positionX = mask.getPositionX() + figure.getPosition().getPositionX();
            int positionY = mask.getPositionY() + figure.getPosition().getPositionY();

            boolean positionHigherThanZero = positionX >= 0 && positionX <= 7 && positionY >= 0 && positionY <=7;
            boolean maskPointIsEmpty = positionHigherThanZero && anotherBoard[positionY][positionX].getTypeOfFigure() == "EMPTY";
            boolean listContainsPointMask = positionHigherThanZero && resultPoints.contains(new Point(positionX, positionY));

            if(maskPointIsEmpty && listContainsPointMask){
                resultPoints.remove(new Point(positionX, positionY));
            }

        }
        return resultPoints;
    }


    public List<Point> exclusiveAllyFigures(Figure[][] anotherBoard, boolean figureIsWhite, List<Point> movementsWithAllyFigures) {

        for(Iterator<Point> it = movementsWithAllyFigures.iterator(); it.hasNext();){
            Point p = it.next();

            int pX = p.getPositionX();
            int pY = p.getPositionY();

            Figure checkingFigure = anotherBoard[pY][pX];
            boolean isNotEmpty = checkingFigure.getTypeOfFigure() != "EMPTY";
            boolean isTheSameColor = checkingFigure.isWhite() == figureIsWhite;

            if(isNotEmpty && isTheSameColor){
                it.remove();
            }


        }

        return movementsWithAllyFigures;
    }
    public List<Point> exclusivePawnBeatenUp(Figure figure, List<Point> availableMovements)  {
        return exclusivePawnBeatenUp(boardFields, figure, availableMovements);
    }


    public List<Point> exclusiveAllyFigures(boolean figureIsWhite, List<Point> movementsWithAllyFigures) {
       return exclusiveAllyFigures(boardFields, figureIsWhite, movementsWithAllyFigures);
    }

    public List<Point> exclusiveUnAvailablePoint(Figure figure, List<Point> pseudoMovements, List<Point> allFiguresInPseudoMovements) {
        PointComparator pointComparator = new PointComparator();
        boolean figureIsKing = figure.getTypeOfFigure() == "KING";
        boolean figureIsKnight = figure.getTypeOfFigure() == "KNIGHT";

        if(figureIsKing || figureIsKnight){
            return pseudoMovements;
        }


        for (Iterator<Point> it = pseudoMovements.listIterator(); it.hasNext(); ) {
            Point p = it.next();
            int directionFromFigureToPoint = ChessExclusiveMovements.getDirection(figure.getPosition(), p);

            for (Point pointsFigures : allFiguresInPseudoMovements) {
                int directionFromFigureToFigure = ChessExclusiveMovements.getDirection(figure.getPosition(), pointsFigures);

                boolean pointsAreOnTheSameDirection = directionFromFigureToFigure == directionFromFigureToPoint;


                if (pointsAreOnTheSameDirection) {

                    boolean pointIsFromHigherHalf = directionFromFigureToPoint >= 1 && directionFromFigureToFigure <= 4;
                    boolean pointIsFromLowerHalf = directionFromFigureToFigure >= 5 && directionFromFigureToFigure <= 8;

                    boolean pointIsLowerThanFigure = pointComparator.compare(p, pointsFigures) < 0;
                    boolean pointIsHigherThanFigure = pointComparator.compare(p, pointsFigures) > 0;

                    if (pointIsFromHigherHalf && pointIsLowerThanFigure) {
                        it.remove();
                        break;
                    } else if ( pointIsFromLowerHalf && pointIsHigherThanFigure) {
                        it.remove();
                        break;
                    }

                }
            }
        }

        return pseudoMovements;
    }

    public static int getDirection(Point from, Point destination){
        int fromX = from.getPositionX();
        int fromY = from.getPositionY();

        int destinationX = destination.getPositionX();
        int destinationY = destination.getPositionY();

        //up
        if(fromY > destinationY){

            //left
            if(fromX > destinationX){
                return 1;
            }

            //right
            if(fromX < destinationX){
                return 3;
            }

            //center
            return 2;
        }

        //center

        if(fromY == destinationY){

            //left
            if(fromX > destinationX){
                return 4;
            }

            //right
            return 5;

        }

        //bottom
        if(fromY < destinationY){

            //left
            if(fromX > destinationX){
                return 6;
            }

            //right
            if(fromX < destinationX){
                return 8;
            }

            //center
            return 7;

        }




        return 0;
    }


}
