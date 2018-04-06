package com.chess.server.models.figures;


import com.chess.server.models.Figure;
import com.chess.server.models.Point;;


public class Rook extends Figure {

    public Rook(Point point, boolean isWhite){
        super(point, "ROOK", isWhite);
    }

}
