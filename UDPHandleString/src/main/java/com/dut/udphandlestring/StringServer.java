package com.dut.udphandlestring;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class StringServer {
    
    public static DatagramSocket datagramSocket;
    
    public static void main(String[] args) {
        try {
            datagramSocket = new DatagramSocket(7778);
            System.out.println("Server is started!");
            while (true) {  
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                datagramSocket.receive(receivePacket);
                Thread thread = new Thread(new Client(receivePacket));
                thread.start();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
}

class Client implements Runnable {

    private DatagramPacket datagramPacket;

    public Client(DatagramPacket receivePacket) {
        this.datagramPacket = receivePacket;
    }

    @Override
    public void run() {
        try {
            byte[] sendData = new byte[1024];
            InetAddress IPAddress = datagramPacket.getAddress();
            int port = datagramPacket.getPort();
            
            String input = new String(datagramPacket.getData());
            String output = "Chuoi in hoa: " + chuoiHoa(input).trim() + "\n"
                    + "Chuoi thuong: " + chuoiThuong(input).trim() + "\n"
                    + "Chuoi vua hoa vua thuong: " + chuoiVuaHoaVuaThuong(input).trim() + "\n"
                    + "So tu cua chuoi: " + soTu(input);
            
            sendData = output.getBytes();
            
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            StringServer.datagramSocket.send(sendPacket);
        } catch (IOException e) {
            System.out.println(e);
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