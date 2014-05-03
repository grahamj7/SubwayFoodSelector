package userInterfaces;

import java.rmi.*;
import java.util.LinkedList;

import customerGUI.Sandwich;

public interface SandwichQueueInterface extends Remote {
	public int getSize() throws RemoteException;
	public LinkedList<Sandwich> getQueue() throws RemoteException;
	//public void add(LinkedList<Sandwich> lLSB) throws RemoteException;
	public void add(Sandwich s) throws RemoteException;
	public void create(Object o) throws RemoteException;
	public Sandwich remove() throws RemoteException;

}
