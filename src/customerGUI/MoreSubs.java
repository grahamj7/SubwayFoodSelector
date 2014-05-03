//////////////////////////////////////////////////////////////////////////////////////////////////////
//																									//
//	CLASS:			MoreSubsGUI.java																//
//	PACKAGE:		customerGUI																		//
//																									//
//	DESCRIPTION:	This will prompt the customer to make another sub or go to pay screen.			//
//	PRECONDITION:	Sandwich must exist																//	
//	POSTCONDITION:	Will bring up paymentGUI or SizeGUI depending on selection						//
//	REVISION DATE:	Nov. 15, 2013																	//
//	REVISION HISTORY:																				//
//		Nov. 15, 2013	Finalized code for submission												//
//																									//
//////////////////////////////////////////////////////////////////////////////////////////////////////

package customerGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.*;

public class MoreSubs  extends JFrame
{
	private static final long serialVersionUID = 1L;
	JLabel label;
	JTextField textBox;
	JButton Submit, Cancel;

	public void run(final CustomerGUI main)
	{
		setTitle("Add More Subs");
		setSize(500, 500);
		if(main.isServer)
			setLocationRelativeTo(null);
		else
		{
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation(screenSize.width/8, screenSize.height/4);
		}
		
		setUndecorated(true);
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (((JButton)e.getSource()).getText().equals("Pay Now"))
				{
					try {
						new PaymentGUI().run(main);
					} catch (RemoteException  e1) {}
				}
				else
				{
					try {
					Sandwich sub = new Sandwich();
					new SizeGUI().run(sub,  main);
					} catch (RemoteException e1) {}
				}
				dispose();
			}
		};

		JLabel label = new JLabel("Would you like to add another sub?");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("title", 0, 18));
		JButton more = new JButton("Create Another Sub?");more.addActionListener(listener);
		JButton pay = new JButton("Pay Now");pay.addActionListener(listener);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panel.setLayout(new GridLayout(3, 1));

		panel.add(label);
		panel.add(more);
		panel.add(pay);
		add(panel);
		setVisible(true);
	}
}
