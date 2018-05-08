package com.chess.server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Server {

    static int numberClients = 0;
    ServerSocket serverSocket;
    int port;
    Map<Integer, ServerService> clients;

    public Server(int port){
        this.port = port;
        initServer();
        clients = new HashMap<>();
    }

    private void initServer() {

        try{
            serverSocket = new ServerSocket(port);
        }catch(IOException e){
            System.out.println("Error while creating a server socket on port: "+this.port+"\n. Perhaps port is already used.");
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
