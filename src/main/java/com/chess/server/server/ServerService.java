package com.chess.server.server;

import com.chess.server.game.Player;
import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.Socket;

public class ServerService extends Thread{


    int id;
    Server server;
    Socket socket;
    Player player;
    OutputStream out;
    InputStream in;

    public ServerService(Server server, Socket client, int id) {
        this.server = server;
        socket = client;
        initServerService();
        this.id = id;
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
           try{
                byte []bytes = new byte[1024];
                in.read(bytes);
                String readMsg = new String(bytes);

                String result = readMsg.trim().toLowerCase();
                System.out.println("Otrzymalem od clienta: "+readMsg.trim());
                if(result.equals("list")){

                    byte [] sendMsg = server.getAllClients().getBytes();
                    out.write(sendMsg);
                    System.out.println("Wysłano listę graczy do użytkownika");

                }else if(result.substring(0,5).equals("invite")){

                    int invitedPlayerId = Integer.parseInt(result.substring(7));

                    ServerService invitedPlayer =  server.clients.get(invitedPlayerId);
                    invitedPlayer.out.write(("Invite by player: "+ this.id).getBytes());
                    //invite player

                }else if(result.equals("exit")){
                    out.write(("You are disconnected from server.").getBytes());
                    socket.close();
                    break;
                }



           } catch (IOException e) {
               System.out.println("Error: " + e);
           }

       }
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
