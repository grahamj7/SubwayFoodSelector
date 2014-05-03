//////////////////////////////////////////////////////////////////////////////////////////////////////
//																									//
//	CLASS:			TypeGUI.java																	//
//	PACKAGE:		customerGUI																		//
//																									//
//	DESCRIPTION:	This will prompt the customer to select the type of sub desired.				//
//	PRECONDITION:	Sandwich must exist																//	
//	POSTCONDITION:	Will set type in sandwich object. Will bring up VegGUI next.					//
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


public class TypeGUI extends JFrame
{
	private static final long serialVersionUID = 1L;

	public void run(final Sandwich sub, final CustomerGUI main)
	{
		setTitle("Type");
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
		panel.setLayout(new GridLayout(5,5));

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){main.start.setEnabled(true);dispose();}});
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){new CheeseGUI().run(sub, main); dispose();}});
		JPanel BackPanel = new JPanel();
		BackPanel.setLayout(new GridBagLayout());
		BackPanel.add(back);
		BackPanel.add(new JLabel(" "));
		BackPanel.add(cancel);
		
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					sub.setMeat(((JButton)e.getSource()).getText());
				} catch (RemoteException e1) {}
				new VegGUI().run(sub, main);
				dispose();
			}
		};

		JButton button;
		int count = 0;
		for (String i : main.name)
		{
			button = new JButton(i);
			button.addActionListener(listener);
			panel.add(button);
			count++;
		}

		if (count % 5 == 0)			
		{
			panel.add(new JPanel());
			panel.add(new JPanel());
			panel.add(BackPanel);
			panel.add(new JPanel());
			panel.add(new JPanel());
		}
		else if (count % 5 == 1)
		{
			panel.add(new JPanel());
			panel.add(BackPanel);
			panel.add(new JPanel());
			panel.add(new JPanel());
		}
		else if (count % 5 == 2)	
		{
			panel.add(new JPanel());
			panel.add(BackPanel);
			panel.add(new JPanel());
		}
		else if (count % 5 == 3)	
		{
			panel.add(new JPanel());
			panel.add(BackPanel);
		}
		else if (count % 5 == 4)	
		{
			panel.add(BackPanel);
		}



		add(panel);
		setVisible(true);
	}
}
