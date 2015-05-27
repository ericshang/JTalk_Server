/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiPackage;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shang
 */
public class ClientListBox implements RMIClientListInterface, Runnable, Serializable {
    private ArrayList<Client> clients;
    private RMIClientListInterface remoteObject;
    
    public ClientListBox(){
        clients = new ArrayList<Client>();
        
    }

    @Override
    public void run() {
        
        remoteObject = new ClientListBox();
        //open this only when receiving data from client
        System.setProperty("java.security.policy","rmi.policy");
        if(System.getSecurityManager() == null){
            System.setSecurityManager( new  SecurityManager());
        }
        System.out.println("Policy established!");
        try{
            //this throws RemoteException 
            RMIClientListInterface stub = (RMIClientListInterface)UnicastRemoteObject
                                            .exportObject(remoteObject, 0);
            //start registry, it might throw exception if registry already existed
            LocateRegistry.createRegistry(1099);//on port 1099
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ClientList", stub);//register object stub
            System.out.println("Now the ClientList is turned on.");
            
            
            
            while(true){
                System.out.println(stub.getClients().size());
                for(Client c : stub.getClients()){
                    System.out.println("Client:"+c.getIpAddress());
                }
                System.out.println("Clients are: ");
                Thread.sleep(2000);
            }
            
        }
        catch(RemoteException e)
        {
            System.out.println(e);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientListBox.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //getters
    public ArrayList<Client> getClients(){
        return clients;
    }
    //setters
    @Override
    public void addClient(Client c){
        //check if client existed in the server
        for(Client client : clients){
            if(c.getIpAddress().equalsIgnoreCase(client.getIpAddress()) &&
                    c.getPort() == client.getPort())
            {
                return;
            }
        }
        clients.add(c);
    }
    public void removeClient(Client c){
        clients.remove(c);
    }    
}
