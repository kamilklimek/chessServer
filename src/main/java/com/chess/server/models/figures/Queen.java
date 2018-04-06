package com.chess.server.models.figures;


import com.chess.server.models.Figure;
import com.chess.server.models.Point;

public class Queen extends Figure {

    public Queen(Point point, boolean isWhite){
        super(point, "QUEEN", isWhite);
    }

}
