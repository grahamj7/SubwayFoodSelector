//////////////////////////////////////////////////////////////////////////////////////////////////////
//																									//
//	CLASS:			SandwichGUI.java																//
//	PACKAGE:		customerGUI																		//
//																									//
//	DESCRIPTION:	This is the sandwich object that can be modified by the customer				//
//	PRECONDITION:	NONE																			//	
//	POSTCONDITION:	Will make a sandwich object when called											//
//	REVISION DATE:	Nov. 15, 2013																	//
//	REVISION HISTORY:																				//
//		Nov. 15, 2013	Finalized code for submission												//
//																									//
//////////////////////////////////////////////////////////////////////////////////////////////////////

package customerGUI;

import java.io.Serializable;
import java.util.LinkedList;
import java.rmi.*;

//import java.rmi.server.UnicastRemoteObject;
//import userInterfaces.*;



public class Sandwich implements Serializable
{
	private static final long serialVersionUID = -5158335263383339666L;
	
	public int size, transID;
	public String bread;
	public String meat;
	public String cheese;
	public boolean toasted;
	public LinkedList<String> vegetables;
	public LinkedList<String> extras;
	public LinkedList<String> sauces;

	public void setTransID(int ID) throws RemoteException
	{
		this.transID = ID;
	}

	public int getTransID() throws RemoteException
	{
		return transID;
	}
	
	public void setSize(int s) throws RemoteException{
		this.size = s;
	}
	
	public int getSize() throws RemoteException{
		return this.size;
	}
	
	public void setBread(String b) throws RemoteException{ 
		this.bread = b;
	}
	
	public String getBread() throws RemoteException{
		return this.bread;
	}
	
	public void setMeat(String m) throws RemoteException{
		this.meat = m;
	}
	
	public String getMeat() throws RemoteException{
		return this.meat;
	}
	
	public void setCheese(String c) throws RemoteException{
		this.cheese = c;
	}
	
	public String getCheese() throws RemoteException{
		return this.cheese;
	}
	
	public void setToasted(boolean t) throws RemoteException{
		this.toasted = t;
	}
	
	public boolean getToasted() throws RemoteException{ 
		return this.toasted;
	}

	public void addVegetables(String veg) throws RemoteException{
		this.vegetables.add(veg);
	}
	
	public void addSauce(String sauce) throws RemoteException{
		this.sauces.add(sauce);
	}
	
	public LinkedList<String> getVegetables() throws RemoteException{
		return this.vegetables;
	}
	
	public LinkedList<String> getSauce() throws RemoteException{
		return this.sauces;
	}

	public void addExtras(String ex) throws RemoteException{
		this.extras.add(ex);
	}
	
	public LinkedList<String> getExtras() throws RemoteException{
		return this.extras;
	}
	
	public void setExtras(LinkedList<String> ex) throws RemoteException{
		this.extras = ex;
	}
	
	public void setVegetables(LinkedList<String> veg) throws RemoteException{
		this.vegetables = veg;
	}
	
	public void setSauces(LinkedList<String> sauce) throws RemoteException{
		this.sauces = sauce;
	}

	public Sandwich() throws RemoteException
	{
		super();
		vegetables = new LinkedList<String>();
		extras = new LinkedList<String>();
		sauces = new LinkedList<String>();
				
	}
}
