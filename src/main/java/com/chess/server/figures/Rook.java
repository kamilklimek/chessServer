package com.chess.server.figures;


import com.chess.server.api.Castle;

;import java.util.Objects;


public class Rook extends Figure{

    private boolean moved;

    public Rook(Point point, boolean isWhite){
        super(point, "ROOK", isWhite);
        moved = true;

        boolean isOnWhite = Objects.equals(point, new Point(0, 0)) || Objects.equals(point, new Point(7,0));
        boolean isOnBlack = Objects.equals(point, new Point(0,7)) || Objects.equals(point, new Point(7, 7));

        if(isOnWhite || isOnBlack){
            moved = false;
        }

    }


}
