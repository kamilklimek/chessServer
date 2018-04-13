package com.chess.server.game;

import com.chess.server.figures.Figure;
import com.chess.server.figures.Point;

import java.util.LinkedList;
import java.util.List;

public class ChessAvailableMovements {

    private ChessExclusiveMovements chessExclusiveMovements;
    private Figure[][] boardFields;

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

    public List<Point> getAvailableMovements(Point point) {
        int positionX = point.getPositionX();
        int positionY = point.getPositionY();

        Figure figure = boardFields[positionY][positionX];
        boolean figureIsWhite = figure.isWhite();

        List<Point> pseudoMovements = figure.getAvailableMovements();
        List<Point> allFiguresInPseudoMovements = getAllFiguresFromPointList(pseudoMovements);

        List<Point> movementsWithAllyFigures = chessExclusiveMovements.exclusiveUnAvailablePoint(figure, pseudoMovements, allFiguresInPseudoMovements);
        List<Point> availableMovements = chessExclusiveMovements.exclusiveAllyFigures(figureIsWhite, movementsWithAllyFigures);
        availableMovements = chessExclusiveMovements.exclusivePawnBeatenUp(figure, availableMovements);

        return availableMovements;
    }





    private List<Point> getAllFiguresFromPointList(List<Point> pseudoMovements) {
        List<Point> figuresFromPseudoMovements = new LinkedList<>();

        for (Point p : pseudoMovements) {
            int pointX = p.getPositionX();
            int pointY = p.getPositionY();

            boolean figureStandsOnPoint = boardFields[pointY][pointX].getTypeOfFigure() != "EMPTY";

            if (figureStandsOnPoint) {
                figuresFromPseudoMovements.add(p);
            }
        }

        return figuresFromPseudoMovements;
    }
}
