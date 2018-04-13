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

    public boolean checkIsMate(boolean isWhite){

        Figure king = findKingByColor(isWhite);
        for (Figure[] row:boardFigures
                ) {
            for (Figure figure:row){

                List<Point> figureMovements = chessAvailableMovements.getAvailableMovements(figure.getPosition());

                for (Point position: figureMovements
                     ) {
                    boolean kingXSumFigurePosition = king.getPosition().getPositionX() == position.getPositionY();
                    boolean kingYSumFigurePosition = king.getPosition().getPositionY() == position.getPositionY();

                    if(kingXSumFigurePosition && kingYSumFigurePosition){
                        return true;
                    }
                }

            }
        }



        return false;
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
