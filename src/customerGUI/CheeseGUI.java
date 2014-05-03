//////////////////////////////////////////////////////////////////////////////////////////////////////
//																									//
//	CLASS:			CheeseGUI.java																	//
//	PACKAGE:		customerGUI																		//
//																									//
//	DESCRIPTION:	This will prompt the customer to select the type of cheese desired.				//
//	PRECONDITION:	Sandwich must exist																//	
//	POSTCONDITION:	Will set cheese type in sandwich class. Will bring up TypeGUI next.				//
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

public class CheeseGUI extends JFrame
{
	private static final long serialVersionUID = 1L;

	public void run(final Sandwich sub, final CustomerGUI main)
	{
		setTitle("Cheese");
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
		JButton american = new JButton("American");
		JButton cheddar = new JButton("Cheddar");
		JButton none = new JButton("No Cheese");
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){main.start.setEnabled(true);dispose();}});
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){new ToastedGUI().run(sub, main); dispose();}});
		JPanel BackPanel = new JPanel();
		BackPanel.setLayout(new GridBagLayout());
		BackPanel.add(back);
		BackPanel.add(new JLabel(" "));
		BackPanel.add(cancel);
		
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				try {
					sub.setCheese(((JButton)event.getSource()).getText());
					new TypeGUI().run(sub, main);
				} catch (RemoteException e) {}
				dispose();
			}
		};

		american.addActionListener(listener);
		cheddar.addActionListener(listener);
		none.addActionListener(listener);
		
		panel.add(american);
		panel.add(cheddar);
		panel.add(none);
		panel.add(BackPanel);
		
		add(panel);
		setVisible(true);
	}
}
