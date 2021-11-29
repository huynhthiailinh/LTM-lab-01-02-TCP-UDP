/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tcpchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author z
 */
public class ChatServer {
    
    private String dataStringFromClient;
    private boolean isStarted;
    private ServerSocket serverWithoutUI;
    List<Client> clientList = new ArrayList<>();
    
    public ChatServer() {
        super();
    }
    
    public void startServer() {
        try {
            serverWithoutUI = new ServerSocket(2808);
            isStarted = true;
            System.out.println("Server is started!");
            while (isStarted) {
                Client st = new Client(serverWithoutUI.accept());
                new Thread(st).start();
                clientList.add(st);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Client implements Runnable {
        private Socket s;
        private DataInputStream dis;
        private DataOutputStream dos;
        private boolean isAcceptStarted = false;
        
        public Client(Socket s) {
            this.s = s;
        }
        
        public void send(String str) {
            try {
                dos.writeUTF(str);
            } catch (IOException e) {}
        }
        
        @Override
        public void run() {
            try {
                System.out.println("Connect successed!");
                isAcceptStarted = true;
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                
                while(isAcceptStarted) {
                    dataStringFromClient = dis.readUTF();
                    System.out.println(dataStringFromClient);
                    for (int index = 0; index < clientList.size(); index++) {
                        Client tempClient = clientList.get(index);
                        tempClient.send(dataStringFromClient);
                    }
                }
            } catch (Exception e) {
                clientList.remove(this);
            } finally {
                try {
                    if (s != null) s.close();
                    if (dis != null) dis.close();
                    if (dos != null) dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        new ChatServer().startServer();
    }
    
}
