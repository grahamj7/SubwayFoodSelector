//////////////////////////////////////////////////////////////////////////////////////////////////////
//																									//
//	CLASS:			SauceGUI.java																	//
//	PACKAGE:		customerGUI																		//
//																									//
//	DESCRIPTION:	This will prompt the customer to select the type of sauces desired.				//
//	PRECONDITION:	Sandwich must exist																//	
//	POSTCONDITION:	Will set sauces in sandwich object. Will bring up MoreSubs next.				//
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
import java.util.LinkedList;

import javax.swing.*;

public class SauceGUI extends JFrame
{
	private static final long serialVersionUID = 1L;

	public void run(final Sandwich sub, final CustomerGUI main)	
	{
		setTitle("Sauce");
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
		panel.setLayout(new GridLayout(4,2));
		final LinkedList<String> sauces = new LinkedList<String>();

		final JToggleButton honeyMustard = new JToggleButton("Honey Mustard");
		final JToggleButton southwest = new JToggleButton("Chipotle Southwest");
		final JToggleButton onion = new JToggleButton("Sweet Onion");
		final JToggleButton mayo = new JToggleButton("Mayonaise");
		final JToggleButton liteMayo = new JToggleButton("Light Mayonaise");
		final JToggleButton ranch = new JToggleButton("Ranch");
		final JToggleButton mustard = new JToggleButton("Mustard");
		final JToggleButton oil = new JToggleButton("Oil");
		final JToggleButton vinegar = new JToggleButton("Red Wine Vinegar");
		final JToggleButton house = new JToggleButton("House Sandwich Sauce");
		JButton done = new JButton("Done");	
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){main.start.setEnabled(true);dispose();}});
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){new VegGUI().run(sub, main); dispose();}});
		JPanel BackPanel = new JPanel();
		BackPanel.setLayout(new GridBagLayout());
		BackPanel.add(back);
		BackPanel.add(new JLabel(" "));
		BackPanel.add(cancel);
		
		done.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				if(honeyMustard.isSelected()){sauces.add(honeyMustard.getText());}
				if(southwest.isSelected()){sauces.add(southwest.getText());}
				if(onion.isSelected()){sauces.add(onion.getText());}
				if(mayo.isSelected()){sauces.add(mayo.getText());}
				if(liteMayo.isSelected()){sauces.add(liteMayo.getText());}
				if(ranch.isSelected()){sauces.add(ranch.getText());}
				if(mustard.isSelected()){sauces.add(mustard.getText());}
				if(oil.isSelected()){sauces.add(oil.getText());}
				if(vinegar.isSelected()){sauces.add(vinegar.getText());}
				if(house.isSelected()){sauces.add(house.getText());}

				try {
					sub.setSauces(sauces);
				} catch (RemoteException e) {
					System.err.println(e);
				}
				dispose();
				main.order.addLast(sub);
				new MoreSubs().run(main);
			}
		});

		panel.add(honeyMustard);
		panel.add(southwest);
		panel.add(onion);
		panel.add(mayo);
		panel.add(liteMayo);
		panel.add(ranch);
		panel.add(mustard);
		panel.add(oil);
		panel.add(vinegar);
		panel.add(house);
		panel.add(done);
		panel.add(BackPanel);
		add(panel);
		setVisible(true);

	}
}
