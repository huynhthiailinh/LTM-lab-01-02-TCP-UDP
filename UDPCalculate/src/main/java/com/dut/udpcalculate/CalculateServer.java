/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dut.udpcalculate;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author z
 */
public class CalculateServer implements Runnable {
    
    DatagramSocket serverSocket;
    DatagramPacket receivePacket;
    DatagramPacket sendPacket;

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
                
                String s = "";

                s = "Ket qua: " + execute(request);

                sendData = s.toString().getBytes("UTF-8");

                this.sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            } catch (IOException ex) {
                Logger.getLogger(CalculateServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public CalculateServer() {
        try {
            this.serverSocket = new DatagramSocket(7788);
            System.out.println("Server is started");
        } catch (Exception e) {
        }
    }
    
    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(new CalculateServer());
        thread.start();
    }
    
    private String execute(String str){
        String v = "";
        Stack<String> numst= new Stack<String>();
        Stack<Character> opest= new Stack<Character>();
        String num = "";
        
        for (int i= 0; i< str.length(); i++){
            char c= str.charAt(i);
            
            switch(c){
                default:
                    return "Error!";
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '.':
                    num += c;
                    if (i == str.length()-1) {
                        if (!opest.isEmpty()){
                            numst.push(getValue(numst.pop(), opest.pop(), num));
                            while (!opest.isEmpty()){
                                String n2= numst.pop(), n1= numst.pop();
                                numst.push(getValue(n1, opest.pop(), n2));
                            }
                        } else return num;
                    }
                    break;
                case '+':
                case '-':
                    if(i == 0) {
                        num += c;
                        break;
                    }
                case '*':
                case '/':
                case '(':
                    if (!num.equals("")) {
                        numst.push(num);
                        num = "";
                    }
                    if (opest.isEmpty()) {
                        opest.push(c);
                    } else {
                        if (c == '(') {
                            opest.push(c);
                            break;
                        }
                        while (!opest.isEmpty() && (opest.lastElement()!='(') && prio(c)<=prio(opest.lastElement())) {
                            String n2 = numst.pop(), n1= numst.pop();
                            numst.push(getValue(n1, opest.pop(), n2));
                        }
                        opest.push(c);
                    }
                    break;
                case ')':
                    if (!num.equals("")) {
                        numst.push(num);
                        num = "";
                    }
                    while((!opest.isEmpty()) && opest.lastElement()!='(') {
                        String n2= numst.pop(), n1= numst.pop();
                        numst.push(getValue(n1, opest.pop(), n2));
                    }
                    opest.pop();
            }
        }
        
        while (!opest.isEmpty()) {
            String n2= numst.pop(), n1= numst.pop();
            numst.push(getValue(n1, opest.pop(), n2));
        }
        v = numst.pop();
        return v;
    }
    
    private String getValue(String a, char o, String b) {
        float af = Float.valueOf(a);
        float bf = Float.valueOf(b);
        switch (o) {
            case '+':
                return String.valueOf(af + bf);
            case '-':
                return String.valueOf(af - bf);
            case '*':
                return String.valueOf(af * bf);
            case '/':
                if (bf == 0) return "devided by zero";
                return String.valueOf((float) af / bf);
            default:
                return "error";
        }
    }
    
    private int prio (char c) {
        if (c == '+' || c == '-') return 0;
        if (c == '(') return 1;
        if (c == '*' || c == '/') return 2;
        return -1;
    }
    
}
