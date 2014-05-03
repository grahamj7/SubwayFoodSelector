//////////////////////////////////////////////////////////////////////////////////////////////////////
//																									//
//	CLASS:			SizeGUI.java																	//
//	PACKAGE:		customerGUI																		//
//																									//
//	DESCRIPTION:	This will prompt the customer to make a selection for size of sub.				//
//	PRECONDITION:	Sandwich must exist																//	
//	POSTCONDITION:	Bread size will be set in Sandwich class. Will bring up BreadGUI afterwards		//
//	REVISION DATE:	Nov. 15, 2013																	//
//	REVISION HISTORY:																				//
//		Nov. 15, 2013	Finalized code for submission												//
//																									//
//////////////////////////////////////////////////////////////////////////////////////////////////////


package customerGUI;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.*;

public class SizeGUI extends JFrame
{
	private static final long serialVersionUID = 1L;

	public void run(final Sandwich sub, final CustomerGUI main)
	{
		setTitle("Size");
		setUndecorated(true);
		setAlwaysOnTop(true);
		if(main.isServer)
			setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		else
		{
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setSize(screenSize.width/2, screenSize.height);
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));

		JButton sixInch = new JButton("6-inch");
		JButton twelveInch = new JButton("12-inch");
		
		sixInch.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				try 
				{
					sub.setSize(6);
				} 
				catch (RemoteException e) 
				{
					System.err.println(e);
				}
				new BreadGUI().run(sub, main);
				dispose();
			}
		});
	
		twelveInch.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				try 
				{
					sub.setSize(12);
				} 
				catch (RemoteException e) 
				{
					System.err.println(e);
				}
				new BreadGUI().run(sub, main);
				dispose();
			}
		});

		panel.add(sixInch);
		panel.add(twelveInch);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){main.start.setEnabled(true);dispose();}});
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){main.start.setEnabled(true);dispose();}});
		JPanel BackPanel = new JPanel();
		BackPanel.setLayout(new GridBagLayout());
		BackPanel.add(back);
		BackPanel.add(new JLabel(" "));
		BackPanel.add(cancel);
		
		panel.add(BackPanel);
		add(panel);
		setVisible(true);
	}
}
