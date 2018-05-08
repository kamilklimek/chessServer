package com.chess.server;

import com.chess.server.figures.Figure;
import com.chess.server.figures.Point;
import com.chess.server.game.Board;
import com.chess.server.game.ChessAvailableMovements;
import com.chess.server.game.ChessCheckMate;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CastlingTests extends TestCase{



    @Test
    public void testKingsMovements(){
        Board board = new Board("boards/tests/can_move_king_castling.game");


        List<Point> expectedMovements = new LinkedList<>(Arrays.asList(
                new Point(2, 7)
        ));



        assertTrue(board.getAvailableMovements(new Point(4,7)).containsAll(expectedMovements));
    }
}
