/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dut.tcphandlestring;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author z
 */
public class StringServer implements Runnable {
    
    ServerSocket serverSocket;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;

    public StringServer() throws IOException {
        this.serverSocket = new ServerSocket(8977);
        System.out.println("Server is started");
    }
    
    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(new StringServer());
        thread.start();
    }

    @Override
    public void run() {
        String input, output;
        while(true) {
            try {
                this.socket = this.serverSocket.accept();
                this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
                this.dataInputStream = new DataInputStream(socket.getInputStream());
                
                input = this.dataInputStream.readUTF();
                System.out.println();
                output = "Chuỗi: " + input
                        + "\nChuỗi in hoa: " + this.chuoiHoa(input)
                        + "\nChuỗi thường: " + this.chuoiThuong(input)
                        + "\nChuỗi vừa hoa vừa thường: " + this.chuoiVuaHoaVuaThuong(input)
                        + "\nSố từ của chuỗi: " + this.soTu(input);

                this.dataOutputStream.writeUTF(output);
            } catch (IOException ex) {
                Logger.getLogger(StringServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
    public String chuoiVuaHoaVuaThuong(String str) {
        char c;
        String result = "";
        for (int i=0; i<str.length(); i++) {
            c  = str.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char) (c-32);
            } else if (c >= 'A' && c <= 'Z') {
                c = (char) (c+32);
            }
            result += c;
        }
        return result;
    }
    
    public String chuoiHoa(String str) {
        char c;
        String result = "";
        for (int i=0; i<str.length(); i++) {
            c  = str.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char) (c-32);
            }
            result += c;
        }
        return result;
    }
    
    public String chuoiThuong(String str) {
        char c;
        String result = "";
        for (int i=0; i<str.length(); i++) {
            c  = str.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                c = (char) (c+32);
            }
            result += c;
        }
        return result;
    }
    
    public int soTu(String str) {
        int result = 0;
        for (String s : str.split(" ")) {
            if (!s.trim().equals("")) {
                result++;
            }
        }
        return result;
    }
    
}
