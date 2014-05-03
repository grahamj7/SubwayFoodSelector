//////////////////////////////////////////////////////////////////////////////////////////////////////
//																									//
//	CLASS:			CustomerGUI.java																//
//	PACKAGE:		customerGUI																		//
//																									//
//	DESCRIPTION:	This will prompt the customer to start the ordering process. 					//
//	PRECONDITION:	None																			//	
//	POSTCONDITION:	Will make new sandwich object. Will bring up SizeGUI next.						//
//	REVISION DATE:	Nov. 15, 2013																	//
//	REVISION HISTORY:																				//
//		Nov. 15, 2013	Finalized code for submission												//
//																									//
//////////////////////////////////////////////////////////////////////////////////////////////////////

package customerGUI;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.*;

import userInterfaces.SandwichQueueInterface;
import databaseconnection.databaseConnect;

public class CustomerGUI extends JFrame
{
	private static final long serialVersionUID = 1L;

	LinkedList<Sandwich> order;	
	LinkedList<String> name;
	SandwichQueueInterface queue;
	Sandwich sub;
	JButton start;
	boolean isServer;

	public CustomerGUI(SandwichQueueInterface sQ, boolean server) throws RemoteException
	{
		isServer = server;
		databaseConnect DB = new databaseConnect();
		try {
			name = DB.viewAvail();
		} catch (SQLException e1) {	e1.printStackTrace();}

		queue = sQ;
		setTitle("Customer Start GUI");
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(isServer)
			setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		else
		{
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setSize(screenSize.width/2, screenSize.height);
		}
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		start = new JButton("Press to start order");

		final CustomerGUI main = this;
		start.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				main.start.setEnabled(false);
				try {
					order = new LinkedList<Sandwich>();
					sub = new Sandwich();
					new SizeGUI().run(sub, main);

				} catch (RemoteException e) {
					System.err.println(e);
				}
			}
		});


		panel.add(start);

		add(panel);
		setVisible(true);	
	}

	public static void main(String[] args) throws RemoteException
	{
		SandwichQueue sQ = new SandwichQueue();
		new CustomerGUI(sQ, true);
	}


}
