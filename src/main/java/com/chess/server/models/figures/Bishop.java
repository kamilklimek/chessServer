package com.chess.server.models.figures;


import com.chess.server.models.Figure;
import com.chess.server.models.Point;

public class Bishop extends Figure {

    public Bishop(Point position, boolean isWhite){
        super(position, "BISHOP", isWhite);
    }
}
