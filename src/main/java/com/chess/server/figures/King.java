package com.chess.server.figures;


import com.chess.server.api.Castle;

public class King extends Figure implements Castle {

    public King(Point point, boolean isWhite){
        super(point, "KING", isWhite);
    }

    @Override
    public boolean isMoved() {
        return false;
    }

    @Override
    public void setFigureMoved() {

    }
}
