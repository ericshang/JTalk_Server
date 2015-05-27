/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtalkserver;

import rmiPackage.ClientListBox;

/**
 *
 * @author Shang
 */
public class JTalkServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        //create a new thread to main client list
        ClientListBox clb = new ClientListBox();
        Thread ClientList = new Thread(clb);
        ClientList.start();
        
        messageReceiver receiver = new messageReceiver();
        Thread receiverThread = new Thread(receiver);
        receiverThread.start();
        
    }
    
}
