package com.chess.server;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    Socket socket;
    OutputStream out;
    InputStream in;

    public Client(){

        try {
            socket = new Socket("127.0.0.1", 9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();

    }

    public void init(){
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void sendMessageToServer(String msg){

        byte [] msgBytes;
        msgBytes = msg.getBytes();

        try {
            out.write(msgBytes);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String []args){
        Client client = new Client();

        try {
            Scanner in = new Scanner(System.in);

            System.out.println("Podaj login: ");
            String msg = in.nextLine();
            client.sendMessageToServer(msg);

            while(true){

                String command = in.nextLine();
                System.out.println("Komenda: "+command);
                if(command.equals("list")){
                    client.out.write("list".getBytes());


                    byte [] bytes = new byte[1024];
                    client.in.read(bytes);
                    String result = new String(bytes).trim();
                    System.out.println(result);


                }else if(command.equals("exit")){
                   // client.out.print("exit");
                    client.socket.close();
                    break;
                }

            }


            client.out.close();
            client.socket.close();

        } catch (IOException e) {
            System.out.println("Cant connect to server: "+e);
        }
    }
}
