//////////////////////////////////////////////////////////////////////////////////////////////////////
//																									//
//	CLASS:			VegGUI.java																		//
//	PACKAGE:		customerGUI																		//
//																									//
//	DESCRIPTION:	This will prompt the customer to select the type of veg desired.				//
//	PRECONDITION:	Sandwich must exist.															//	
//	POSTCONDITION:	Will set Veg in sandwich object. Will bring up SauceGUI next.					//
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

public class VegGUI extends JFrame
{
	private static final long serialVersionUID = 1L;

	public void run(final Sandwich sub, final CustomerGUI main)
	{
		setTitle("Vegetables");
		setUndecorated(true);
		setAlwaysOnTop(true);
		if(main.isServer)
			setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		else
		{
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setSize(screenSize.width/2, screenSize.height);
		}setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,2));
		final LinkedList<String> vegetables = new LinkedList<String>();

		final JToggleButton lettuce = new JToggleButton("Lettuce");
		final JToggleButton spinach = new JToggleButton("Spinach");
		final JToggleButton tomatoes = new JToggleButton("Tomatoes");
		final JToggleButton cucumbers = new JToggleButton("Cucumbers");
		final JToggleButton greenPeppers = new JToggleButton("Green Peppers");
		final JToggleButton onions = new JToggleButton("Onions");
		final JToggleButton pickles = new JToggleButton("Pickles");
		final JToggleButton olives = new JToggleButton("Olives");
		final JToggleButton jalapenos = new JToggleButton("Jalapenos");
		final JToggleButton bananaPeppers = new JToggleButton("Banana Peppers");
		JButton done = new JButton("Done");

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){main.start.setEnabled(true);dispose();}});
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){new TypeGUI().run(sub, main); dispose();}});
		JPanel BackPanel = new JPanel();
		BackPanel.setLayout(new GridBagLayout());
		BackPanel.add(back);
		BackPanel.add(new JLabel(" "));
		BackPanel.add(cancel);

		done.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				if(lettuce.isSelected()){vegetables.add(lettuce.getText());}
				if(spinach.isSelected()){vegetables.add(spinach.getText());}
				if(tomatoes.isSelected()){vegetables.add(tomatoes.getText());}
				if(cucumbers.isSelected()){vegetables.add(cucumbers.getText());}
				if(greenPeppers.isSelected()){vegetables.add(greenPeppers.getText());}
				if(onions.isSelected()){vegetables.add(onions.getText());}
				if(pickles.isSelected()){vegetables.add(pickles.getText());}
				if(olives.isSelected()){vegetables.add(olives.getText());}
				if(jalapenos.isSelected()){vegetables.add(jalapenos.getText());}
				if(bananaPeppers.isSelected()){vegetables.add(bananaPeppers.getText());}

				try {
					sub.setVegetables(vegetables);
				} catch (RemoteException e) {
					System.err.println(e);
				}
				new SauceGUI().run(sub, main);
				dispose();
			}
		});

		panel.add(lettuce);
		panel.add(spinach);
		panel.add(tomatoes);
		panel.add(cucumbers);
		panel.add(greenPeppers);
		panel.add(onions);
		panel.add(pickles);
		panel.add(olives);
		panel.add(jalapenos);
		panel.add(bananaPeppers);	
		panel.add(done);

		panel.add(BackPanel);
		add(panel);
		setVisible(true);
	}
}
