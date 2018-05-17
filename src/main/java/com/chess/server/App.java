package com.chess.server;

import com.chess.server.figures.Figure;
import com.chess.server.figures.Point;
import com.chess.server.game.Board;
import com.chess.server.game.Game;
import com.chess.server.server.Server;

import java.util.List;
import java.util.Scanner;


public class App 
{
    public static void main( String[] args ) {

       //Game game = new Game("boards/tests/can_move_king_castling.game");

       //game.play();

        int port = 10000;
        if(args.length > 0 ){
            port = Integer.parseInt(args[0]);
        }


        Server server = new Server(port);
        server.listening();

    }
}
