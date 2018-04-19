package com.chess.server.game;

import com.chess.server.figures.Figure;
import com.chess.server.figures.King;
import com.chess.server.figures.Point;

import java.util.*;

public class ChessCheckMate {

    Figure[][] boardFigures;
    ChessAvailableMovements chessAvailableMovements;

    public ChessCheckMate(Figure[][] boardFigures){
        this.boardFigures=boardFigures;
        chessAvailableMovements = new ChessAvailableMovements(this.boardFigures);
    }

    public boolean checkIsCheckMate(boolean isWhite){
        //if not check -> cannot be check mate
        boolean isNotCheck = !checkIsMate(isWhite);
        if(isNotCheck) return false;

        Figure king = findKingByColor(isWhite);
        Point kingsPosition = king.getPosition();

        List<Point> kingMovements = chessAvailableMovements.getAvailableMovements(kingsPosition);
        List<Point> kingsMovementsExcludedProtectedEnemyFigures = excludeProtectedEnemyFigures(kingMovements, king);


        boolean kingsHasPossibleMovements = kingsMovementsExcludedProtectedEnemyFigures.size() > 0;
        System.out.println("Possibly movements: "+kingsMovementsExcludedProtectedEnemyFigures);
        if(kingsHasPossibleMovements) return false;







        return true;
    }

    private List<Point> excludeProtectedEnemyFigures(List<Point> kingMovements, Figure king) {

        List<Point> excludedMovements = new LinkedList<>(kingMovements);
        boolean kingsColor = king.isWhite();
        Set<Integer> indexToRemove = new LinkedHashSet<>();

        for (Point kingPoint:excludedMovements
             ) {

            List<Figure> enemyFigures = Board.getAllFiguresByColor(boardFigures, !kingsColor);
            for (Figure enemyfigure:enemyFigures
                 ) {
                List<Point> enemyMovements = chessAvailableMovements.getAvailableMovements(enemyfigure.getPosition());
                boolean containsKingMov = enemyMovements.contains(kingPoint);
                boolean isPawn = enemyfigure.getTypeOfFigure() == "PAWN";
                if(containsKingMov){

                    indexToRemove.add(excludedMovements.indexOf(kingPoint));

                }


            }
        }

        List<Integer> indexToRemoveReversed = new ArrayList<>(indexToRemove);
        Collections.reverse(indexToRemoveReversed);

        for(Integer index : indexToRemoveReversed){

            int indexInt = index.intValue();

            excludedMovements.remove(indexInt);

        }

        for (Iterator<Point> iterator = excludedMovements.iterator();iterator.hasNext();
             ) {
            Point kingMovement = iterator.next();
            boolean canMove = canMove(boardFigures, king.getPosition(), kingMovement);

            if(!canMove) {
                iterator.remove();
            }
        }



        return excludedMovements;
    }

    public boolean checkIsMate(Figure[][] anotherBoard, boolean isWhite){

        Figure king = findKingByColor(anotherBoard, isWhite);
        for (Figure[] row:anotherBoard
                ) {
            for (Figure figure:row){

                List<Point> figureMovements = chessAvailableMovements.getAvailableMovements(anotherBoard, figure.getPosition());

                for (Point position: figureMovements
                        ) {
                    boolean kingXSumFigurePosition = king.getPosition().getPositionX() == position.getPositionX();
                    boolean kingYSumFigurePosition = king.getPosition().getPositionY() == position.getPositionY();
                    boolean notEmpty = figure.getTypeOfFigure() != "EMPTY";

                    if(kingXSumFigurePosition && kingYSumFigurePosition && notEmpty){
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public boolean canMove(Figure[][] anotherBoard, Point from, Point to) {
        int fromX = from.getPositionX();
        int fromY = from.getPositionY();
        boolean isWhite = anotherBoard[fromY][fromX].isWhite();

        int toX = to.getPositionX();
        int toY = to.getPositionY();


        Figure[][] newBoard = Board.copyOriginalBoard(anotherBoard);
        newBoard[toY][toX] = anotherBoard[fromY][fromX].copy();
        newBoard[toY][toX].setPosition(to);
        newBoard[fromY][fromX] = new Figure(from, "EMPTY");

        boolean isMate = checkIsMate(newBoard, isWhite);
        return chessAvailableMovements.getAvailableMovements(from).contains(to) && !isMate;
    }

    public boolean checkIsMate(boolean isWhite){
        return checkIsMate(boardFigures, isWhite);
    }


    private Figure findKingByColor(Figure[][] figures, boolean isWhite){
        for (Figure[] row:figures
                ) {
            for (Figure figure:row
                    ) {

                boolean isKing = figure.getTypeOfFigure() == "KING";

                if(isKing && isWhite){
                    return figure.copy();
                }

            }
        }

        return new Figure(new Point(0, 0), "EMPTY");

    }

    private Figure findKingByColor(boolean isWhite){
        return findKingByColor(boardFigures, isWhite);
    }

}
