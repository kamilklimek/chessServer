package com.chess.server.figures;


import com.chess.server.api.Castle;

import java.util.Objects;

public class King extends Figure implements Castle {

    private boolean moved;

    public King(Point point, boolean isWhite){
        super(point, "KING", isWhite);
        moved = true;

        boolean isOnWhite = Objects.equals(point, new Point(3, 0));
        boolean isOnBlack = Objects.equals(point, new Point(4, 7));

        if(isOnWhite || isOnBlack){
            moved = false;
        }
    }

    @Override
    public boolean isMoved() {
        return moved;
    }

    @Override
    public void setFigureMoved() {
        moved=true;
    }
}
