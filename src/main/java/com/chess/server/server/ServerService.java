package com.chess.server.server;

import com.chess.server.figures.Point;
import com.chess.server.game.Game;
import com.chess.server.game.Player;
import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerService extends Thread{


    int id;
    int gameId;
    Server server;
    Socket socket;
    Player player;
    OutputStream out;
    InputStream in;
    ServerService opponent;
    Game game;

    public ServerService(Server server, Socket client, int id) {
        this.server = server;
        socket = client;
        initServerService();
        this.id = id;
        this.gameId = -1;
        opponent = null;
        game = null;
    }

    private void initServerService() {
        try{
            out =socket.getOutputStream();
            in = socket.getInputStream();
        }catch(IOException e){
            System.out.println("Error while init server service. Problem with I/O.\n" + e);
        }

    }


    @Override
    public void run(){

        createPlayer();

       while(true){
           String command = read();

           boolean list = command.equals("list");
           boolean invite = command.contains("invite=");
           boolean exit = command.equals("exit");
           boolean surrender = command.equals("surrender");
           boolean available = command.equals("available=");
           boolean move = command.equals("move=");

           //not in game
           if(gameId < 0){

               if(list){
                   write(server.getAllClients());
               }else if(invite){
                   int id = Integer.parseInt(command.substring(7, command.length()-1));
                   invite(id);
               }else if(exit){
                   System.out.println("Disconnected " + socket.getInetAddress().toString());
                   try {
                       socket.close();
                       in.close();
                       out.close();
                       break;
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }


           }
           //in game
           else{

               while(game.hasNextTurn()){
                   String gameCommand = read();

                   if(surrender){
                       surrender();
                   }else if(available){
                       int x = Integer.parseInt(gameCommand.substring(10, 11));
                       int y = Integer.parseInt(gameCommand.substring(13, 14));
                       sendAvailable(x, y);
                   }else if(move && game.isTurnPlayerOne() == player.isWhite){
                       int x, y;

                       x = Integer.parseInt(gameCommand.substring(5, 6));
                       y = Integer.parseInt(gameCommand.substring(8, 9));
                       Point from = new Point(x, y);

                       x = Integer.parseInt(gameCommand.substring(11, 12));
                       y = Integer.parseInt(gameCommand.substring(14, 15));
                       Point to = new Point(x, y);

                       int moveResult = game.move(from, to);
                       write("moveresult="+moveResult);

                       if(moveResult<3 && moveResult >= 0){
                            game.nextTurn();
                       }else if(moveResult == 3){
                           write("pawnchange");
                           String figureId = read();
                           figureId = figureId.substring(0, figureId.length()-1);
                       }

                   }
               }

           }

       }
    }

    private void sendAvailable(int x, int y) {
        List<Point> movements = server.games.get(gameId).getAvailableMovements(new Point(x, y));
        write(movements.toString());
    }

    private void surrender() {
        if(gameId >= 0){
            game.surrender(player.isWhite);
            gameId = -1;
            opponent.gameId = -1;
            opponent = null;
            game = null;
        }else{
            write("You are not in a game!");
        }

    }

    private void invite(int id) {
        ServerService client = getClientById(id);
        opponent = client;
        System.out.println("Jego id: "+ id);
        System.out.println("Uzytkownik to" + client.player.getLogin());
        client.write("invitedby="+this.id);
        write("Uzytkownik o id: "+ this.id +" został zaproszony do rozgrywki".getBytes());

        String result = read();
        boolean responded = result.contains("respond=");

        if(responded){
            int resp = Integer.parseInt(result.substring(7, result.length()-1));
            if(resp == 1){
                System.out.println("Zaproszenie zostało przyjęte");
                write("invitedresult=1");
                int gameId = Server.gamesNumber++;
                this.gameId = gameId;
                client.gameId = gameId;

                player.isWhite = true;
                client.player.isWhite = false;

                Game game = new Game(player, client.player);
                this.game = game;
                server.games.put(gameId, game);

                write("board="+game.getBoardInString());
                client.write("board="+game.getBoardInString());

            }else{
                System.out.println("Zaproszenie nie zostalo przyjete");
                write("invitedresult=0");
            }


        }

    }

    public ServerService getClientById(int id){
        return server.clients.get(id);
    }

    public boolean write(String msg){
        byte [] byteMsg = msg.getBytes();
        try {
            out.write(byteMsg);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error while write msg: "+e);
            return false;
        }

        return true;
    }

    public String read(){
        byte [] bytes = new byte[1024];
        try {
            in.read(bytes);
        } catch (IOException e) {
            System.out.println("error while reading: "+e);
        }
        return new String(bytes).trim();
    }

    public void createPlayer(){
        try{

            byte [] buffor = new byte[1024];
            in.read(buffor);
            String readMsg = new String(buffor);
            String login = readMsg.trim();
            player = new Player(id, login);

            System.out.println("Utworzylem: "+player);

        } catch (IOException e) {
            System.out.println("Error: "+e);
        }
    }

    @Override
    public String toString() {
        return player.toString();
    }
}
