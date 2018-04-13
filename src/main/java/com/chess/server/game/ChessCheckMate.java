package com.chess.server.game;

import com.chess.server.figures.Figure;
import com.chess.server.figures.Point;

public class ChessCheckMate {

    Figure[][] boardFigures;

    public ChessCheckMate(Figure[][] boardFigures){
        this.boardFigures=boardFigures;
    }

    public boolean checkIsMate(boolean isWhite){

        Figure king = findKingByColor(isWhite);
        for (Figure[] row:boardFigures
                ) {
            for (Figure figure:row){

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

                if(isKing && isWhite);
                    return figure;

            }
        }

        return new Figure(new Point(0, 0), "EMPTY");
    }

}
