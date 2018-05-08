package com.chess.server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {

    ServerSocket serverSocket;
    int port;
    List<ServerService> clients;

    public Server(int port){
        this.port = port;
        initServer();
        clients = new LinkedList<>();
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
           System.out.println("I am waiting for client connecting...");

           try{
               Socket client = serverSocket.accept();
               System.out.println("Client connected from IP: "+client.getInetAddress().toString());

               System.out.println("I create a service for this client.");
               ServerService clientService = new ServerService(client);
               clients.add(clientService);


           }catch(IOException e){
               System.out.println("Error while creating a client socket. " + e);
           }

       }


    }


}
