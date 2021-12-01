package com.dut.tcphandlestring;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StringServer {
    
    private String input;
    private String output;
    private boolean isStarted;
    private ServerSocket serverSocket;
    
    public StringServer() {
        super();
    }
    
    public void startServer() {
        try {
            serverSocket = new ServerSocket(2808);
            isStarted = true;
            System.out.println("Server is started!");
            while (isStarted) {
                Client st = new Client(serverSocket.accept());
                new Thread(st).start();
            }
        }  catch (IOException e) {
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
                    input = dis.readUTF();
                    output = "Chuỗi: " + input
                        + "\nChuỗi in hoa: " + this.chuoiHoa(input)
                        + "\nChuỗi thường: " + this.chuoiThuong(input)
                        + "\nChuỗi vừa hoa vừa thường: " + this.chuoiVuaHoaVuaThuong(input)
                        + "\nSố từ của chuỗi: " + this.soTu(input);
                    this.send(output);
                }

            } catch (Exception e) {
                System.out.println(e);
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
    
    public static void main(String[] args) {
        new StringServer().startServer();
    }
    
}
