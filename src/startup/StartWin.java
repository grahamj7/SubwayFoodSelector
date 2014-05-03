package startup;
import java.awt.event.*;
import java.rmi.RemoteException;

import javax.swing.*;

import userInterfaces.SandwichQueueInterface;

//import java.rmi.Naming;
//import java.rmi.RMISecurityManager;
//import userInterfaces.SandwichInterface;
//import userInterfaces.SandwichQueueInterface;

import StaffGUI.QueueListener;
import StaffGUI.StaffMain;
import managergui.managerlogin;
import customerGUI.CustomerGUI;
import customerGUI.SandwichQueue;


public class StartWin extends JFrame 
{
	private static final long serialVersionUID = 1L;
	//	SandwichQueue sQ;

	//Empty window to start program to. Maybe select which view to start from here? (customer, staff, manager..)
	public StartWin(final SandwichQueueInterface sQ, final boolean isServer)
	{	
		setTitle("SelectorWindow");
		setSize(300, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setAlignmentX(CENTER_ALIGNMENT);

		JButton button1 = new JButton("Customer Window");
		button1.setAlignmentX(CENTER_ALIGNMENT);
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
			{
				try {
					new CustomerGUI(sQ, isServer);
				} catch (RemoteException e1) {}
				dispose();
			}
		});

		JButton button2 = new JButton("Staff Window");
		button2.setAlignmentX(CENTER_ALIGNMENT);
		button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
			{
				StaffMain staff = new StaffMain(isServer);
				QueueListener listener = new QueueListener(staff, sQ);
				listener.start();
				//listener.run();
				dispose();
			}
		});

		JButton button3 = new JButton("Manager Window");
		button3.setAlignmentX(CENTER_ALIGNMENT);
		button3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
			{
				new managerlogin(sQ, isServer);
				dispose();
			}
		});

		JButton button4 = new JButton("Customer and Staff Windows");
		button4.setAlignmentX(CENTER_ALIGNMENT);
		button4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
			{
				try {
					new CustomerGUI(sQ, isServer);
				} catch (RemoteException e1) {}
				
				StaffMain staff = new StaffMain(isServer);
				QueueListener listener = new QueueListener(staff, sQ);
				listener.start();
				//listener.run();
				dispose();
			}
		});

		panel.add(Box.createVerticalGlue());
		if(isServer)
		{
			panel.add(button1);	panel.add(Box.createVerticalGlue());
			panel.add(button2);	panel.add(Box.createVerticalGlue());
		}
		else
		{
			panel.add(button4);panel.add(Box.createVerticalGlue());
		}

		panel.add(button3);
		panel.add(Box.createVerticalGlue());
		add(panel);
		setVisible(true);
	}
	public static void main(String[] args)
	{
		SandwichQueue sQ = null;
		try {
			sQ = new SandwichQueue();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		new StartWin(sQ, false);
	}
}
