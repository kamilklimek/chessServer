package com.chess.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static void main(String []args){

        try {
            Socket client = new Socket("127.0.0.1", 9999);

            OutputStream write = client.getOutputStream();

            byte [] msg = new byte [1024];
            msg = "To ja, kamil!".getBytes();

            write.write(msg);

            write.close();
            client.close();

        } catch (IOException e) {
            System.out.println("Cant connect to server: "+e);
        }
    }

}
