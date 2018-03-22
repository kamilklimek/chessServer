package com.chess.server.models.figures;


import com.chess.server.models.Figure;
import com.chess.server.models.Point;
/**
 * Created by kamil.klimek on 20.03.2018.
 */
public class Pawn extends Figure {

    public Pawn(Point point) {
        super(point, "PAWN");
    }
}
