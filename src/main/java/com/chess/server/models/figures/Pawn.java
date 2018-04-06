package com.chess.server.models.figures;


import com.chess.server.models.Figure;
import com.chess.server.models.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends Figure {


    public Pawn(Point point, boolean isWhite) {
        super(point, "PAWN", isWhite);
    }




}
