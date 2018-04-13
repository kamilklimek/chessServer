package com.chess.server;

import com.chess.server.game.Board;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class CheckMate extends TestCase {

    @Test
    public void testSimpleRookAndKing(){
        Board board = new Board("boards/tests/rook_simple_mate.game");


        boolean isCheck = board.checkMate(true);

        assertTrue(isCheck);


    }

}

