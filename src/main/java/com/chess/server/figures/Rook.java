package com.chess.server.figures;


import com.chess.server.api.Castle;

;


public class Rook extends Figure implements Castle {

    private boolean moved;

    public Rook(Point point, boolean isWhite){
        super(point, "ROOK", isWhite);
        moved = false;
    }

    @Override
    public boolean isMoved() {
        return moved;
    }

    @Override
    public void setFigureMoved() {
        moved = true;
    }
}
