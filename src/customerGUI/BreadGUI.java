//////////////////////////////////////////////////////////////////////////////////////////////////////
//																									//
//	CLASS:			BreadGUI.java																	//
//	PACKAGE:		customerGUI																		//
//																									//
//	DESCRIPTION:	This will prompt the customer to make a selection for type of bread.			//
//	PRECONDITION:	Sandwich must exist																//	
//	POSTCONDITION:	Bread type will be set in Sandwich class. Will bring up ToastedGUI afterwards	//
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

public class BreadGUI extends JFrame
{
	private static final long serialVersionUID = 1L;

	public void run(final Sandwich sub, final CustomerGUI main)
	{
		setTitle("Bread");
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
		panel.setLayout(new GridLayout(3,2));
		JButton wheat = new JButton("9-grain Wheat");
		JButton honeyOat = new JButton("9-grain Honey Oat");
		JButton italian = new JButton("Italian");
		JButton herbsAndCheese = new JButton("Italian Herbs & Cheese");
		JButton flatbread = new JButton("Flatbread");

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){main.start.setEnabled(true);dispose();}});
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){new SizeGUI().run(sub, main); dispose();}});
		JPanel BackPanel = new JPanel();
		BackPanel.setLayout(new GridBagLayout());
		BackPanel.add(back);
		BackPanel.add(new JLabel(" "));
		BackPanel.add(cancel);
		
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				try{
					sub.setBread(((JButton)arg0.getSource()).getText());
				}catch(RemoteException e){}
				new ToastedGUI().run(sub, main);
				dispose();
			}
		};

		wheat.addActionListener(listener);
		honeyOat.addActionListener(listener);
		italian.addActionListener(listener);
		herbsAndCheese.addActionListener(listener);
		flatbread.addActionListener(listener);


		panel.add(wheat);
		panel.add(honeyOat);
		panel.add(italian);
		panel.add(herbsAndCheese);
		panel.add(flatbread);

		panel.add(BackPanel);
		add(panel);
		setVisible(true);	
	}
}
