package com.chess.server.server;

import com.chess.server.game.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    static int numberClients = 0;
    static int gamesNumber = 0;
    ServerSocket serverSocket;
    int port;
    Map<Integer, ServerService> clients;
    Map<Integer, Game> games;

    public Server(int port){
        this.port = port;
        initServer();
        clients = new LinkedHashMap<Integer, ServerService>();
        games = new HashMap<Integer, Game>();
    }

    private void initServer() {

        try{
            serverSocket = new ServerSocket(port);
        }catch(IOException e){
            System.out.println("Error while creating a server socket on port: "+this.port+".\nPerhaps port is already used.");
            System.exit(1);
        }
    }



    public void listening(){

       while(true){
           System.out.println("I am waiting for client...");

           try{
               Socket client = serverSocket.accept();
               System.out.println("Client connected from IP: "+client.getInetAddress().toString() +", and LocalPort: "+client.getPort());

               System.out.println("I create a service for this client.");
               ServerService clientService = new ServerService(this, client, numberClients);
               clientService.start();

               clients.put(numberClients, clientService);
               numberClients++;


           }catch(IOException e){
               System.out.println("Error while creating a client socket. " + e);
           }

       }


    }

    public String getAllClients(){
        StringBuilder result = new StringBuilder();

        result.append("Lista graczy:\n");
        for (Map.Entry<Integer, ServerService> entry : clients.entrySet()){

            ServerService client = entry.getValue();
            Integer key = entry.getKey();


            result.append(key);
            result.append(". ");
            result.append(client.player.getLogin().toString());
            result.append("\n");

        }


        return result.toString();
    }




}
