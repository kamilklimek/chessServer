package com.chess.server.models.figures;


import com.chess.server.models.Figure;
import com.chess.server.models.Point;
/**
 * Created by kamil.klimek on 20.03.2018.
 */
public class King extends Figure {

    public King(Point point){
        super(point, "KING");
    }
}
