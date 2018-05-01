package com.chess.server;

import com.chess.server.figures.Point;
import com.chess.server.game.Board;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class CastlingTests extends TestCase{



    @Test
    public void testKingsMovements(){
        Board board = new Board("boards/tests/can_move_king_castling.game");

        board.displayAllFigures();
        board.display();

        board.getAvailableMovements(new Point(4, 7));


        assertFalse(true);
    }
}
