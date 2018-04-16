package com.chess.server.game;

import com.chess.server.figures.Figure;
import com.chess.server.figures.Point;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ChessCheckMate {

    Figure[][] boardFigures;
    ChessAvailableMovements chessAvailableMovements;

    public ChessCheckMate(Figure[][] boardFigures){
        this.boardFigures=boardFigures;
        chessAvailableMovements = new ChessAvailableMovements(this.boardFigures);
    }

    public boolean checkIsCheckMate(boolean isWhite){
        // is not check
        boolean isNotCheck = !checkIsMate(isWhite);
        if(isNotCheck) return false;

        Figure king = findKingByColor(isWhite);
        List<Point> kingsMovements = chessAvailableMovements.getAvailableMovements(boardFigures, king.getPosition());
        List<Point> kingsMovementsExcluded = excludeSharedMovements(kingsMovements, king.isWhite());

        //possible movements
        boolean hasPossibleMovements = kingsMovementsExcluded.size() > 0;
        if(hasPossibleMovements) return false;

        //beaten up enemy figures
        // ???

        //shield by ally figures


        Point pointToProtect = getSubstractionTwoList(kingsMovements, kingsMovementsExcluded);
        boolean kingCanBeProtected = canProtectKing(king, pointToProtect);

        if(kingCanBeProtected) return false;




        return true;
    }

    private boolean canProtectKing(Figure king, Point pointToProtect) {
        boolean kingsAlly = king.isWhite();
        List<Figure> kingsAllies = Board.getAllFiguresByColor(boardFigures, kingsAlly);

        for (Figure ally : kingsAllies
             ) {
            List<Point> availableMovements = chessAvailableMovements.getAvailableMovements(boardFigures, ally.getPosition());

            for (Point p:availableMovements
                 ) {
                boolean canProtectKing = Objects.equals(p, pointToProtect);
                if(canProtectKing) return true;
            }


        }


        return false;
    }

    private Point getSubstractionTwoList(List<Point> kingsMovements, List<Point> kingsMovementsExcluded) {

        for (Point kingMovement:kingsMovements
             ) {
            for (Point excludedKingMovement:kingsMovementsExcluded
                 ) {
                boolean pointsEqual = Objects.equals(kingMovement, excludedKingMovement);
                if(pointsEqual)
                    return kingMovement;
            }
        }

        return new Point(0, 0);
    }

    private List<Point> excludeSharedMovements(List<Point> king, boolean kingsColor) {
        List<Figure> kingsEnemies = Board.getAllFiguresByColor(boardFigures, !kingsColor);
        List<Point> kingsMovements = new LinkedList<>(king);

        boolean goToBreak = false;
        for(Iterator<Point> it = kingsMovements.iterator(); it.hasNext();){

            Point point = it.next();


            for (Figure figure : kingsEnemies
                    ) {

                if(goToBreak){
                goToBreak = false;
                break;
            }
                for(Point p : chessAvailableMovements.getAvailableMovements(boardFigures, figure.getPosition())){


                    boolean pointsEquals = Objects.equals(point, p);
                    if(pointsEquals){
                        it.remove();
                        boolean hasNext = it.hasNext();

                        if(hasNext)
                            point = it.next();
                        else{

                            goToBreak=true;
                            break;
                        }

                    }
                }


            }


        }


        return kingsMovements;
    }

    private boolean containsKingsMovement(Point point, List<Point> figureMovements) {
        for (Point p : figureMovements
                ) {
            boolean equals = Objects.equals(p, point);
            if(equals){
                return true;
            }
        }
        return false;
    }

    public boolean checkIsMate(Figure[][] anotherBoard, boolean isWhite){

        Figure king = findKingByColor(isWhite);
        for (Figure[] row:anotherBoard
                ) {
            for (Figure figure:row){

                List<Point> figureMovements = chessAvailableMovements.getAvailableMovements(anotherBoard, figure.getPosition());

                for (Point position: figureMovements
                        ) {
                    boolean kingXSumFigurePosition = king.getPosition().getPositionX() == position.getPositionX();
                    boolean kingYSumFigurePosition = king.getPosition().getPositionY() == position.getPositionY();

                    if(kingXSumFigurePosition && kingYSumFigurePosition){
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public boolean checkIsMate(boolean isWhite){
        return checkIsMate(boardFigures, isWhite);
    }


    private Figure findKingByColor(boolean isWhite){

        for (Figure[] row:boardFigures
                ) {
            for (Figure figure:row
                    ) {

                boolean kingColor = figure.isWhite() == isWhite;
                boolean isKing = figure.getTypeOfFigure() == "KING";

                if(isKing && isWhite){
                    return figure.copy();
                }

            }
        }

        return new Figure(new Point(0, 0), "EMPTY");
    }

}
