/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dut.udphandlestring;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author z
 */
public class StringServer implements Runnable {
    
    DatagramSocket serverSocket;
    DatagramPacket receivePacket;
    DatagramPacket sendPacket;

    public StringServer() {
        try {
            this.serverSocket = new DatagramSocket(7778);
            System.out.println("Server is started");
        } catch (Exception e) {
        }
    }
    
    private static String thuongHoa(String str) {
        String temp = "";
        for (int i=0; i<str.length(); i++) {
            char ch = str.charAt(i);
            if (ch>='a' && ch<='z') {
                ch = (char) (ch-32);
            }
            temp += ch;
        }
        return temp;
    }
    
    private static String vuaHoaVuaThuong(String str) {
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
    
    private static int soTu(String st) {
        char c;
        int dem = 0;
        for (int j = st.length()-1; j>=0; j--) {
            c = st.charAt(j);
            if (c == ' ') 
                dem++;
        }
        dem++;
        return dem;
    }
    
    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(new StringServer());
        thread.start();
    }

    @Override
    public void run() {
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        while (true) {
            //Tao goi rong de nhan du lieu tu client
            this.receivePacket = new DatagramPacket(receiveData, receiveData.length);
            
            try {
                //Nhan du lieu tu client
                serverSocket.receive(receivePacket);
                
                //Lay dia chi ip cua may client
                InetAddress IPAddress = receivePacket.getAddress();

                //Lay port cua chuong trinh client
                int port = receivePacket.getPort();
                
                String request = new String(receivePacket.getData(), "UTF-8");
                
                String s="";

                s += "Thuong hoa: " + thuongHoa(request).trim() + "\n";
                s += "Vua hoa vua thuong: " + vuaHoaVuaThuong(request).trim() + "\n";
                s += "So tu: " + soTu(request);

                System.out.println(s);
                sendData = s.toString().getBytes("UTF-8");

                this.sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            } catch (IOException ex) {
                Logger.getLogger(StringServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
