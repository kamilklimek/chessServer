package com.chess.server;

import com.chess.server.figures.King;
import com.chess.server.figures.Point;
import com.chess.server.game.Board;
import com.chess.server.game.ChessCheckMate;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class CheckMateTests extends TestCase {


    @Test
    public void testSimpleRookAndKing(){
        Board board = new Board("boards/tests/rook_simple_mate.game");


        boolean isCheck = board.checkMate(true);

        assertTrue(isCheck);

    }

    @Test
    public void testCanMoveStopingHideKing(){
        Board board = new Board("boards/tests/can_move.game");



        Point from = new Point(0, 2);
        Point to = new Point(1, 2);

        boolean moveRookAndStopHideKingFromCheck = board.canMove(from, to);

        assertFalse(moveRookAndStopHideKingFromCheck);


    }

    @Test
    public void testCanMoveAndDontUnhideKing(){
        Board board = new Board("boards/tests/can_move.game");



        Point from = new Point(0, 2);
        Point to = new Point(0,3);

        boolean moveRookAndStopHideKingFromCheck = board.canMove(from, to);

        assertTrue(moveRookAndStopHideKingFromCheck);


    }

    @Test
    public void testSimpleCheckMate(){
        Board board = new Board("boards/tests/can_move_mate.game");

        assertTrue(board.checkIsCheckMate(true));

    }


    @Test
    public void testCheckMateWithBishop(){
        Board board = new Board("boards/tests/can_move_mate_bishop.game");
        assertFalse(board.checkIsCheckMate(true));

    }

    @Test
    public void testCheckMateWithBishopAndDefender(){
        Board board = new Board("boards/tests/check_defense_bishop.game");

        assertFalse(board.checkIsCheckMate(true));
    }

    @Test
    public void testCheckMateSoloKing(){
        Board board = new Board("boards/tests/check_solo_king.game");
        assertTrue(board.checkIsCheckMate(true));
    }

    @Test
    public void testCheckMateWithBeatenUpAttacker(){
        Board board = new Board("boards/tests/check_beaten_up_attacker.game");
        assertFalse(board.checkIsCheckMate(true));
    }

    @Test
    public void testCheckMateWithBeateUpAttackerByKing(){
        Board board = new Board("boards/tests/check_king_beaten_up_attacker.game");
        assertFalse(board.checkIsCheckMate(true));
    }

    @Test
    public void testCheckMateAnotherOne(){
        Board board = new Board("boards/tests/check_mate_one.game");
        assertFalse(board.checkIsCheckMate(true));
    }

    @Test
    public void testCheckMateAnotherTwo(){
        Board board = new Board("boards/tests/check_mate_two.game");
        assertFalse(board.checkIsCheckMate(true));
    }

    @Test
    public void testCheckMateAnotherThree(){
        Board board = new Board("boards/tests/check_mate_three.game");
        assertFalse(board.checkIsCheckMate(true));
    }

    @Test
    public void testCheckMateAnotherFour(){
        Board board = new Board("boards/tests/check_mate_four.game");
        assertFalse(board.checkIsCheckMate(true));

    }

    @Test
    public void testCheckMateAnotherFive(){
        Board board = new Board("boards/tests/check_mate_five.game");

        assertTrue(board.checkIsCheckMate(true));

    }
    @Test
    public void testCheckMateAnotherSix(){
        Board board = new Board("boards/tests/check_mate_six.game");

        assertFalse(board.checkIsCheckMate(true));

    }
    @Test
    public void testCheckMateAnotherSeven(){
        Board board = new Board("boards/tests/check_mate_seven.game");

        assertTrue(board.checkIsCheckMate(true));

    }
    @Test
    public void testCheckMateAnotherEight(){
        Board board = new Board("boards/tests/check_mate_eight.game");
        assertTrue(board.checkIsCheckMate(true));

    }
    @Test
    public void testCheckMateAnotherNine(){
        Board board = new Board("boards/tests/check_mate_nine.game");

        assertTrue(board.checkIsCheckMate(true));

    }
    @Test
    public void testCheckMateAnotherTen(){
        Board board = new Board("boards/tests/check_mate_ten.game");

        assertFalse(board.checkIsCheckMate(true));

    }
    @Test
    public void testCheckMateAnotherEleven(){
        Board board = new Board("boards/tests/check_mate_eleven.game");

        assertTrue(board.checkIsCheckMate(true));

    }
    @Test
    public void testCheckMateAnotherTwelve(){
        Board board = new Board("boards/tests/check_mate_twelve.game");

        assertTrue(board.checkIsCheckMate(true));

    }


}

