package com.chess.server.game;

import com.chess.server.figures.Figure;
import com.chess.server.figures.King;
import com.chess.server.figures.Point;

import java.util.LinkedList;
import java.util.List;

public class ChessAvailableMovements {

    private ChessExclusiveMovements chessExclusiveMovements;
    private Figure[][] boardFields;
    private Board board;
    private ChessCheckMate chessCheckMate;

    public ChessAvailableMovements(Figure[][]boardFields, Board board, ChessCheckMate chessCheckMate){
        this.boardFields = boardFields;
        chessExclusiveMovements = new ChessExclusiveMovements(this.boardFields);
        this.board = board;
        this.chessCheckMate = chessCheckMate;
    }

    public ChessAvailableMovements(Figure[][]boardFields){
        this.boardFields = boardFields;
        chessExclusiveMovements = new ChessExclusiveMovements(this.boardFields);
    }


    /**
     * Function calculate (with exclusion points) available point
     * where figure can move
     *
     * @param point - calculate movements for arg point
     * @return list of available movements
     */

    public List<Point> getAvailableMovements(Figure[][] anotherBoard, Point point) {
        int positionX = point.getPositionX();
        int positionY = point.getPositionY();

        Figure figure = anotherBoard[positionY][positionX];
        boolean figureIsWhite = figure.isWhite();

        List<Point> pseudoMovements = figure.getAvailableMovements();
        List<Point> allFiguresInPseudoMovements = getAllFiguresFromPointList(anotherBoard, pseudoMovements);

        List<Point> movementsWithAllyFigures = chessExclusiveMovements.exclusiveUnAvailablePoint(figure, pseudoMovements, allFiguresInPseudoMovements);
        List<Point> availableMovements = chessExclusiveMovements.exclusiveAllyFigures(anotherBoard, figureIsWhite, movementsWithAllyFigures);
        availableMovements = chessExclusiveMovements.exclusivePawnBeatenUp(anotherBoard, figure, availableMovements);


        return availableMovements;
    }

    public List<Point> getAvailableMovements(Point point) {
        return getAvailableMovements(boardFields, point);
    }





    private List<Point> getAllFiguresFromPointList(Figure[][] anotherBoard, List<Point> pseudoMovements) {
        List<Point> figuresFromPseudoMovements = new LinkedList<>();

        for (Point p : pseudoMovements) {
            int pointX = p.getPositionX();
            int pointY = p.getPositionY();

            boolean figureStandsOnPoint = anotherBoard[pointY][pointX].getTypeOfFigure() != "EMPTY";

            if (figureStandsOnPoint) {
                figuresFromPseudoMovements.add(p);
            }
        }

        return figuresFromPseudoMovements;
    }
}
