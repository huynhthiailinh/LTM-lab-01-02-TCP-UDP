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
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author z
 */
public class StringClient1 {
    
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket clientSocket = new DatagramSocket();
        
        InetAddress IPAddress = InetAddress.getByName("localhost");
        
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        
        System.out.println("Nhap chuoi: ");
        Scanner s = new Scanner(System.in);
        
        //Tao datagram cho noi dung yeu cau
        sendData = s.nextLine().getBytes();
        
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 7778);
        clientSocket.send(sendPacket); //gui du lieu cho server
        
        //Tao datagram rong
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        
        //Nhan du lieu tu server
        clientSocket.receive(receivePacket);
        
        //Lay du lieu tu packet nhan duoc
        String str = new String(receivePacket.getData());
        
        System.out.println(str);
        
    }
    
}
