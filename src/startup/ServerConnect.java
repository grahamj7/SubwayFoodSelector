package startup;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import customerGUI.Sandwich;
import customerGUI.SandwichQueue;
import userInterfaces.SandwichQueueInterface;

@SuppressWarnings({"unused"})
public class ServerConnect {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * This begins the connection the the server on which the remote methods may be called
		 */

		try{
			//Set up the security for the server
			System.setProperty("java.security.policy", "total.policy");
			System.setSecurityManager(new RMISecurityManager());
			//Lookup the remote methods on the server and make a connection to the registry
			//System.out.println("Naming lookup sever...");
			//SandwichInterface sandwich = (SandwichInterface) Naming.lookup("//10.81.30.53:1099/main");
			SandwichQueueInterface sandwichQueue = (SandwichQueueInterface) Naming.lookup("//10.81.30.53:1099/subQueue");
			//System.out.println("Server Ok\nStart GUI");
			
			//System.out.println(sandwichQueue.getSize());
			
			//Sandwich sand = new Sandwich();
			//sandwichQueue.add(sand);
			//final Sandwich sand = new Sandwich();
			//UnicastRemoteObject.exportObject(sand);
			//sandwichQueue.create(sand);
			//System.out.println(sandwichQueue.getSize());
			
			/*SandwichQueue sQ = new SandwichQueue();
			Sandwich sand = new Sandwich();
			sQ.add(sand);
			System.out.println(sQ.getSize());
			sQ.add(sand);
			System.out.println(sQ.getSize());*/
			
			new StartWin(sandwichQueue, true);
			
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}

}
