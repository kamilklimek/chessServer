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
        List<Point> kingsMovementsExcluded = excludeSharedMovements(kingsMovements, king);
        //possible movements
        System.out.println(kingsMovementsExcluded);
        boolean hasPossibleMovements = kingsMovementsExcluded.size() > 0;
        if(hasPossibleMovements) return false;

        //beaten up enemy figures
        // ???

        //shield by ally figures
        Figure attacker = getKingsAttacker(kingsMovements, king);
        boolean kingCanBeProtected = canProtectKing(king, attacker);
        if(kingCanBeProtected) return false;


        return true;
    }

    private Figure getKingsAttacker(List<Point> kingsMovements, Figure king) {
        Figure attacker = new Figure(new Point(0,0), "EMPTY");
        List<Figure> enemies = Board.getAllFiguresByColor(boardFigures, !king.isWhite());

        for (Figure enemy:enemies
             ) {

            List<Point> enemysMovements = chessAvailableMovements.getAvailableMovements(boardFigures, enemy.getPosition());

            for (Point enemysPoint:enemysMovements
                 ) {
                boolean attackKing = Objects.equals(king.getPosition(), enemysPoint);
                if(attackKing){
                    return enemy.copy();
                }

            }

        }

        return attacker;
    }

    private boolean canProtectKing(Figure king, Figure attacker) {
        boolean kingsAlly = king.isWhite();
        List<Figure> kingsAllies = Board.getAllFiguresByColor(boardFigures, kingsAlly);
        List<Figure> kingsAlliesWithoutKing = excludeKing(king, kingsAllies);

        for (Figure ally : kingsAlliesWithoutKing
             ) {
            List<Point> availableMovements = chessAvailableMovements.getAvailableMovements(boardFigures, ally.getPosition());
            for (Point p:availableMovements
                 ) {

                for (Point attackerPoint: chessAvailableMovements.getAvailableMovements(boardFigures, attacker.getPosition())
                     ) {

                    boolean canShieldOrBeatenUp = Objects.equals(attackerPoint, p);
                    if(canShieldOrBeatenUp)
                        return true;
                }
            }

        }

        return false;
    }

    private List<Figure> excludeKing(Figure king, List<Figure> kingsAllies) {
        List<Figure> kingsAlliesWithoutKing = new LinkedList<>(kingsAllies);
        for(Iterator<Figure> it = kingsAlliesWithoutKing.iterator(); it.hasNext(); ){
            Figure figure = it.next();
            boolean isWhite = figure.isWhite() == king.isWhite();
            boolean isKing = figure.getTypeOfFigure() == "KING";

            if(isKing && isWhite){
                it.remove();
                return kingsAlliesWithoutKing;
            }
        }

        return kingsAlliesWithoutKing;
    }

    private List<Point> excludeSharedMovements(List<Point> kingsMov, Figure king) {
        List<Figure> kingsEnemies = Board.getAllFiguresByColor(boardFigures, !king.isWhite());
        List<Point> kingsMovements = new LinkedList<>(kingsMov);


        boolean goToBreak = false;
        for(Iterator<Point> it = kingsMovements.iterator(); it.hasNext();){

            Point point = it.next();


            for (Figure figure : kingsEnemies
                    ) {

                if(goToBreak){
                    goToBreak = false;
                    break;
                }

                //can beat up and move by king?

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
