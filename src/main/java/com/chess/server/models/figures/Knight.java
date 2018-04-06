package com.chess.server.models.figures;


import com.chess.server.models.Figure;
import com.chess.server.models.Point;

public class Knight extends Figure {

    public Knight(Point position, boolean isWhite) {
        super(position, "KNIGHT", isWhite);
    }
}
