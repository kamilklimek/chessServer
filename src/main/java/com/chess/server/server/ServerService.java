package com.chess.server.server;

import com.chess.server.figures.Point;
import com.chess.server.game.Game;
import com.chess.server.game.Player;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ServerService extends Thread{
    private int id;
    private int gameId;
    private Server server;
    private Socket socket;
    protected Player player;
    private OutputStream out;
    private InputStream in;
    private ServerService opponent;
    private Game game;
    private boolean isInGame;
    private boolean isConnected;

    public ServerService(Server server, Socket client, int id) {
        this.server = server;
        socket = client;
        initServerService();
        this.id = id;
        this.gameId = -1;
        opponent = null;
        game = null;
        this.isInGame=false;
        this.isConnected = true;
    }

    private void initServerService() {
        try{
            out = socket.getOutputStream();
            in = socket.getInputStream();
        }catch(IOException e){
            System.out.println("Error while init server service. Problem with I/O.\n" + e);
        }

    }


    @Override
    public void run(){

        String login = read();
        createPlayer(login);

       while(isConnected){
           String command = read();

           if(!isConnected){
               break;
           }

           System.out.println("Serwis o ID: "+this.id);
           System.out.println("Command: "+command);

           boolean sendList = command.equals("list");
           boolean surrender = command.equals("surrender");
           boolean exit = command.equals("exit");
           boolean move = command.contains("move");
           boolean sendAvailableMovements = command.contains("available");
           boolean invitePlayer = command.contains("invite");

           if(isInGame){

               if(sendAvailableMovements){
                   int figurePositionX = Integer.parseInt(command.substring(11, 12));
                   int figurePositionY = Integer.parseInt(command.substring(13, 14));
                   Point figurePosition = new Point(figurePositionX, figurePositionY);

                   System.out.println("POSITION: "+figurePosition.toString());

                   sendAvailableMovements(figurePosition);
               }else if(move){

                   int fromX = Integer.parseInt(command.substring(6, 7));
                   int fromY = Integer.parseInt(command.substring(8, 9));

                   int toX= Integer.parseInt(command.substring(11, 12));
                   int toY= Integer.parseInt(command.substring(13, 14));

                   String idFigure = command.substring(15,18);



                   Point from = new Point(fromX, fromY);
                   Point to = new Point(toX, toY);
                   System.out.println("FROM: "+from.toString());
                   System.out.println("TO: "+to.toString());
                   System.out.println("ID figury: "+idFigure);

                   move(from, to, idFigure);

               }else if(surrender){
                   surrender();
               }/*else{
                   write("undefinded command are in game");
               }*/



           }else{

               if(sendList){
                   sendPlayerList();
               }else if(exit){

                   closeConnection();
                   break;

               }else if(invitePlayer){
                   int id = Integer.parseInt(command.substring(7, command.length()));
                   invitePlayer(id);
               }/*else{
                   write("undefinded command are in game:" + command);
               }*/
           }

       }

        closeConnection();
    }

    private void closeConnection() {
        try {
            out.close();
            in.close();
            socket.close();
            if(isInGame){
                this.server.games.remove(this.gameId);
            }

            this.server.clients.remove(this.id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void surrender() {
        write("result=lose");
        this.opponent.write("result=win");

        server.games.remove(gameId);

        this.opponent.isInGame = false;
        this.opponent.gameId = -1;
        this.opponent.game = null;
        this.opponent.opponent = null;

        this.gameId = -1;
        this.opponent = null;
        this.game = null;
        this.isInGame = false;


    }

    private void move(Point from, Point to, String idFigure) {

        int result = game.move(from, to, player.isWhite);

        boolean cantMove = result == -1;
        boolean isNotYourMove = result == -3;
        boolean moveWithoutBeatenUp = result == 0;
        boolean moveAndBeatenUp = result == 1;
        boolean castling = result == 2;
        boolean pawnChanging = result == 3;


        String resulted = "moved=";
        if(cantMove || isNotYourMove){
            resulted+=0;
        }
        else if(moveWithoutBeatenUp){
            resulted+=1;
        }else if(moveAndBeatenUp){
            resulted+=2;
        }else if(castling){
            resulted+=3;
        }else if(pawnChanging){
            resulted+=4;
        }

        resulted+="["+from.getPositionX()+","+from.getPositionY()+"]["+to.getPositionX()+","+to.getPositionY()+"]"+idFigure+"#";
        write(resulted);
        opponent.write(resulted);

        System.out.println("Byl result przy ruchu: "+resulted);

    }

    private void sendAvailableMovements(Point figurePosition) {
        List<Point> availableMovements = game.getAvailableMovements(figurePosition);

        String result = String.valueOf(availableMovements.size()) + ":";

        for (Point point:availableMovements
                ) {
            int positionX = point.getPositionX();
            int positionY = point.getPositionY();

            result += "[";
            result += positionX;
            result += ",";
            result += positionY;
            result += "]";
        }

        result += "#";

        write(result);
        System.out.println("Wyslalem: "+result);

    }

    private void invitePlayer(int id) {

        boolean playerDoesNotExist = !server.clients.containsKey(id);

        if(playerDoesNotExist){
            write("inviteresult=false");
        }

        ServerService invitedPlayer = getClientById(id);
        invitedPlayer.write("invitedby="+this.id);
        System.out.println("Czekam na wiadomosc od klienta: ");
        String answer = invitedPlayer.read();
       // String answer = read();
        System.out.println("Otrzymalem wiadomosc zwrotna: "+answer);

        if(answer.equals("accept")){
            write("inviteresult=true");
            createGame(id);
        }else{
            System.out.println("Nie utworzylme gry");
            write("inviteresult=false");
        }



    }

    private void createGame(int id) {
        opponent = getClientById(id);
        this.player.isWhite = true;
        opponent.player.isWhite = false;

        game = new Game(this.player, opponent.player);
        int gameId = Server.gamesNumber++;
        server.games.put(gameId, game);

        this.gameId=gameId;
        this.isInGame=true;

        opponent.game = this.game;
        opponent.gameId = gameId;
        opponent.opponent = this;
        opponent.isInGame = true;

        System.out.println("Utworzono gre dla graczy o ID: " + opponent.id + ", oraz :"+this.id + ". \nGra ma ID: "+gameId);

    }

    private void sendPlayerList() {

        String result = String.valueOf(server.clients.size()) + ":";
        for (Map.Entry<Integer, ServerService> client : server.clients.entrySet()
                ) {

            result += client.getKey();
            result += "-";
            result += client.getValue().player.getLogin();
            result += ":";
        }

        result = result.substring(0, result.length()-1);
        result += "#";

        write(result);
        System.out.println("Wyslano: "+result);

    }

    public ServerService getClientById(int id){
        return server.clients.get(id);
    }


    public void createPlayer(String login){
         player = new Player(id, login);
         System.out.println("Utworzylem: "+player);
    }

    public boolean write(String msg){
        byte [] byteMsg = msg.getBytes();
        try {
            out.write(byteMsg);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error while write msg: "+e);
            this.isConnected = false;
            return false;
        }

        return true;
    }

    public String read(){
        byte [] bytes = new byte[1024];
        try {
            in.read(bytes);
        } catch (IOException e) {
            System.out.println("Error while reading: "+e);
            this.isConnected = false;
        }
        return new String(bytes).trim();
    }


    @Override
    public String toString() {
        return player.toString();
    }
}
