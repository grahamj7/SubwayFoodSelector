//////////////////////////////////////////////////////////////////////////////////////////////////////
//																									//
//	CLASS:			SandwichQueueGUI.java															//
//	PACKAGE:		customerGUI																		//
//																									//
//	DESCRIPTION:	This is used to keep a linked list of sandwich Sandwichs for an order				//
//	PRECONDITION:	Sandwich must exist																//	
//	POSTCONDITION:	Will make a linked list of sandwiches											//
//	REVISION DATE:	Nov. 15, 2013																	//
//	REVISION HISTORY:																				//
//		Nov. 15, 2013	Finalized code for submission												//
//																									//
//////////////////////////////////////////////////////////////////////////////////////////////////////


package customerGUI;
import java.util.LinkedList;
import java.rmi.*;
import java.rmi.server.*;

import userInterfaces.SandwichQueueInterface;
import customerGUI.Sandwich;

public class SandwichQueue extends UnicastRemoteObject implements SandwichQueueInterface 
{
	private static final long serialVersionUID = 1L;

	public LinkedList<Sandwich> queue;
	public int size;
	
	public SandwichQueue() throws RemoteException
	{
		this.size = 0;
		this.queue = new LinkedList<Sandwich>();
	}
	public LinkedList<Sandwich> getQueue(){
		return queue;
	}
	public int getSize(){
		return queue.size();
	}
	/*public void add(LinkedList<Sandwich> lLSB){
		for (Sandwich s: lLSB){
			this.add(s);
		}
	}*/
	public void add(Sandwich s){
		this.queue.addLast(s);
	}
	public void create(Object o) throws RemoteException
	{
		Sandwich sub = null;
		//  Start the sandwich queue
		try {
			//sQ = new SandwichQueue();
			
			sub = new Sandwich();	sub.setBread("Itallian");	sub.setMeat("CCC");	sub.setCheese("Cheddar");	sub.setToasted(true);
			sub.addSauce("Mayo"); sub.addSauce("Sauce"); sub.addVegetables("Tomatoes"); sub.addVegetables("Lettuce");	sub.setSize(6);
			sub.setSize(12);	
			queue.add(sub);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	public Sandwich remove(){
		Sandwich sub = this.queue.removeFirst();
		return sub;
	}
}
