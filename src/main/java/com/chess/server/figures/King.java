package com.chess.server.figures;


import com.chess.server.api.Castle;

public class King extends Figure implements Castle {

    private boolean moved;

    public King(Point point, boolean isWhite){
        super(point, "KING", isWhite);
        moved = false;
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
