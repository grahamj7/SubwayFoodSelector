//////////////////////////////////////////////////////////////////////////////////////////////////////
//																									//
//	CLASS:			BreadGUI.java																	//
//	PACKAGE:		customerGUI																		//
//																									//
//	DESCRIPTION:	This will prompt the customer to make a selection for toasted or not.			//
//	PRECONDITION:	Sandwich must exist																//	
//	POSTCONDITION:	Toasted will be set in Sandwich class. Will bring up CheeseGUI afterwards		//
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ToastedGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public void run(final Sandwich sub, final CustomerGUI main) 
	{
		setTitle("Toasted");
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

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){main.start.setEnabled(true);dispose();}});
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){new BreadGUI().run(sub, main); dispose();}});
		JPanel BackPanel = new JPanel();
		BackPanel.setLayout(new GridBagLayout());
		BackPanel.add(back);
		BackPanel.add(new JLabel(" "));
		BackPanel.add(cancel);
		
		JButton toasted = new JButton("Toasted");
		JButton notToasted = new JButton("Not Toasted");
		
		toasted.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				try {
					sub.setToasted(true);
				} catch (RemoteException e) {
					System.err.println(e);
				}
				new CheeseGUI().run(sub, main);
				dispose();
			}
		});
	
		notToasted.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				try {
					sub.setToasted(false);
				} catch (RemoteException e) {
					System.err.println(e);
				}
				new CheeseGUI().run(sub, main);
				dispose();
			}
		});

		panel.add(toasted);
		panel.add(notToasted);
		panel.add(BackPanel);
		
		add(panel);
		setVisible(true);
		}
}
