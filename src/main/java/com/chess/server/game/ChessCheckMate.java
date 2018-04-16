package com.chess.server.game;

import com.chess.server.figures.Figure;
import com.chess.server.figures.Point;

import java.util.List;

public class ChessCheckMate {

    Figure[][] boardFigures;
    ChessAvailableMovements chessAvailableMovements;

    public ChessCheckMate(Figure[][] boardFigures){
        this.boardFigures=boardFigures;
        chessAvailableMovements = new ChessAvailableMovements(this.boardFigures);
    }

    public boolean checkIsCheckMate(boolean isWhite){
        boolean isNotCheck = checkIsMate(isWhite);
        if(isNotCheck) return false;

        Figure king = findKingByColor(isWhite);



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
                    return figure;
                }

            }
        }

        return new Figure(new Point(0, 0), "EMPTY");
    }

}
