/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dut.udpchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author z
 */
public class ChatClient extends javax.swing.JFrame {
    
    private static final long serialVersionUID = 1L;
    private Socket s;
    private DataOutputStream socketOutputStream;
    private DataInputStream socketInputStream;
    private boolean bConnected = false;
    private Accept accept;
    
    @SuppressWarnings("unused")
    private Thread acceptThread;
    
    boolean isConnected = false;
    static String username = "";
    static boolean readyToSend = false;
    private boolean connected;
    
    private void sendMessage() {
        String tempStr;
        tempStr = jTextField.getText();
        
        try {
            socketOutputStream.writeUTF(">> " + username + ": " + tempStr);
            socketOutputStream.flush();
        } catch (IOException e1) {
            System.out.println("Can't output!!");
        }
        
        jTextField.setText("");
    }
    
    void connectionFailed() {
        jButtonLogin.setEnabled(true);
        jButtonLogOut.setEnabled(false);
        jButtonSend.setEnabled(false);
        connected = false;
    }
    

    /**
     * Creates new form ChatClient
     */
    public ChatClient() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelServer = new javax.swing.JLabel();
        jLabelPort = new javax.swing.JLabel();
        jTextFieldPort = new javax.swing.JTextField();
        jButtonSend = new javax.swing.JButton();
        jTextFieldUsername = new javax.swing.JTextField();
        jLabel = new javax.swing.JLabel();
        jLabelUsername = new javax.swing.JLabel();
        jTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea = new javax.swing.JTextArea();
        jButtonLogin = new javax.swing.JButton();
        jButtonLogOut = new javax.swing.JButton();
        jTextFieldServer = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelServer.setText("Server address:");

        jLabelPort.setText("Port number:");

        jTextFieldPort.setEditable(false);
        jTextFieldPort.setText("2808");

        jButtonSend.setText("Send");
        jButtonSend.setEnabled(false);
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });

        jLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel.setText("Bài 3: Chat UDP");

        jLabelUsername.setText("Username:");

        jTextField.setEditable(false);

        jTextArea.setEditable(false);
        jTextArea.setColumns(20);
        jTextArea.setRows(5);
        jScrollPane1.setViewportView(jTextArea);

        jButtonLogin.setText("Login");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jButtonLogOut.setText("Log out");
        jButtonLogOut.setEnabled(false);
        jButtonLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogOutActionPerformed(evt);
            }
        });

        jTextFieldServer.setEditable(false);
        jTextFieldServer.setText("localhost");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSend)
                        .addGap(12, 12, 12))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelServer, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabelUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextFieldServer, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelPort, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jTextFieldUsername)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonLogin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonLogOut)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSend)
                    .addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelServer)
                    .addComponent(jLabelPort)
                    .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLogOut)
                    .addComponent(jButtonLogin)
                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUsername))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendActionPerformed
        // TODO add your handling code here:
        System.out.println("sending ...");
        sendMessage();
        return;
    }//GEN-LAST:event_jButtonSendActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        // TODO add your handling code here:
        username = jTextFieldUsername.getText().trim();
        if (username.length() == 0) return;

        String server = jTextFieldServer.getText().trim();
        if (server.length() == 0) return;

        String portNumber = jTextFieldPort.getText().trim();
        if (portNumber.length() == 0) return;

        int port = 0;

        port = Integer.parseInt(portNumber);

        if (!connected) {
            try {
                s = new Socket(server, port);
                socketOutputStream = new DataOutputStream(s.getOutputStream());
                socketInputStream = new DataInputStream(s.getInputStream());
                isConnected = true;
                bConnected = true;
                jTextField.setEditable(true);
                jButtonSend.setEnabled(true);
                jButtonLogOut.setEnabled(true);
                jButtonLogin.setEnabled(false);
                jTextFieldUsername.setEditable(false);
                accept = new Accept(s);
                Thread acceptThread = new Thread(accept);
                acceptThread.start();
            } catch (UnknownHostException e1) {
                System.out.println("Miss server!");
            } catch (IOException e2) {
                System.out.println("Can't output");
            }
        }

    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogOutActionPerformed
        // TODO add your handling code here:
        if (isConnected) {
            bConnected = false;
            try {
                socketInputStream.close();
                socketOutputStream.close();
                jTextField.setEditable(false);
                jButtonSend.setEnabled(false);
                jButtonLogOut.setEnabled(false);
                jButtonLogin.setEnabled(true);
                jTextFieldUsername.setEditable(true);
                s.close();
            } catch (IOException ex) {
                System.out.println("Doesn't connected!");
            }

            connectionFailed();
            return;
        }
    }//GEN-LAST:event_jButtonLogOutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatClient().setVisible(true);
            }
        });
    }
    
    class Accept implements Runnable {
        Socket s;
        
        Accept(Socket s){
            this.s = s;
        }
        
        @Override
        public void run() {
            while(bConnected) {
                try {
                    String str = socketInputStream.readUTF();
                    jTextArea.append(str + "\n");
                } catch (SocketException e3){
                    System.out.println("bye!!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLogOut;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonSend;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabelPort;
    private javax.swing.JLabel jLabelServer;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea;
    private javax.swing.JTextField jTextField;
    private javax.swing.JTextField jTextFieldPort;
    private javax.swing.JTextField jTextFieldServer;
    private javax.swing.JTextField jTextFieldUsername;
    // End of variables declaration//GEN-END:variables
}