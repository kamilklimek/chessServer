package com.chess.server.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerService extends Thread{

    Socket socket;

    public ServerService(Socket client) {
        socket = client;
    }

    @Override
    public void run(){

        try{
            InputStream in = socket.getInputStream();

            byte [] readMsg = new byte[1024];
            in.read(readMsg);

            System.out.println("Client send msg: " + readMsg);

            in.close();

        } catch (IOException e) {
            System.out.println("Error: "+e);
        }

    }
}
