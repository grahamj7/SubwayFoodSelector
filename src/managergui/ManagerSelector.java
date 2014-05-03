package managergui;

import java.awt.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import startup.StartWin;
import userInterfaces.SandwichQueueInterface;

import java.awt.event.*;
import java.io.IOException;

/**
 * Opens a window of options of what the manager can choose to do
 */

@SuppressWarnings({"unused"})
public class ManagerSelector extends JFrame {
	
	public final int Height=500; 
	public final int Width=600; 

	private static final long serialVersionUID = 1L;

	
	public ManagerSelector(final SandwichQueueInterface sQ, final boolean isServer)
	{
		setTitle("Manager Selector");
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2,5,5));
		panel.setAlignmentX(CENTER_ALIGNMENT);
		
		JButton translog = new JButton("Transaction log");
		//button1.setAlignmentX(CENTER_ALIGNMENT);
		JButton complaints = new JButton("Complaints");
		//button2.setAlignmentX(CENTER_ALIGNMENT);
		//button3.setAlignmentX(CENTER_ALIGNMENT);
		JButton itemsavailable = new JButton("Items available");
		//button4.setAlignmentX(CENTER_ALIGNMENT);
		JButton refund = new JButton("Refunds");
		//button5.setAlignmentX(CENTER_ALIGNMENT);
		JButton newManager = new JButton("Add/Remove manager");
		JButton Exit = new JButton("Exit");

		//panel.add(Box.createVerticalGlue());
		panel.add(translog);
		panel.add(complaints);
		panel.add(itemsavailable);
		panel.add(refund);
		panel.add(newManager);
		panel.add(Exit);
		add(panel);
		setVisible(true);
		
		/*
		 * Opens a new window to view transaction log
		 */
		translog.addActionListener(new
				ActionListener()
				{
					public void actionPerformed (ActionEvent event)
					{
						new TransactionLog();
					}
				});
		
		/*
		 * Opens a new window to view complaints
		 */
		complaints.addActionListener(new
				ActionListener()
				{
					public void actionPerformed (ActionEvent event)
					{
						new Complaints();
					}
				});
		
		/*
		 * Opens a new window to view the availability of the item menus
		 */
		itemsavailable.addActionListener(new
				ActionListener()
				{
					public void actionPerformed (ActionEvent event)
					{
						new ItemAvailability();
					}
				});
		
		/*
		 * Opens a new window to allow refunds
		 */
		refund.addActionListener(new
				ActionListener()
				{
					public void actionPerformed (ActionEvent event)
					{
						new Refund();
		
					}
				});
		
		/*
		 * Opens a new window to add or remove managers
		 */
		newManager.addActionListener(new
				ActionListener()
				{
					public void actionPerformed (ActionEvent event)
					{new AddRemove();
						
						//AddRemove addReManager = new AddRemove();
						//addReManager.initialize();
					}
				});
		
		/*
		 * Closes the program
		 */
		Exit.addActionListener(new
				ActionListener()
				{
					public void actionPerformed (ActionEvent event)
					{
						new StartWin(sQ, isServer);
						dispose();
					}
				});
	}
	public static void main(String[] args) {
	SandwichQueueInterface sQ = null;
		new ManagerSelector(sQ, false);
	}
	
}
