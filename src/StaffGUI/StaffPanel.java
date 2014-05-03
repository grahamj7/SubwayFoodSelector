package StaffGUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.*;

import customerGUI.Sandwich;


public class StaffPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Basic variables that are required for StaffPanel to operate correctly
	 * options - the options that the user can select (Completed or Cancelled)
	 * choiceMessage - message that shows on the dialog box for choosing if the sub was completed or cancelled
	 * choice Title - title that shows in the dialog box for choosing if the sub was completed or cancelled
	 * subCompleted - true or false, marks the panel if the sub has been completed yet
	 * screenNumber - records which screen this panel is being displayed on
	 * parent - the order window that holds the panel
	 * success - marks the success of the sub to be reported to the database
	 */
	String[] options;
	String choiceMessage;
	String choiceTitle;
	Boolean subCompleted;
	int screenNumber;
	StaffMain parent;
	boolean success;
	
	/**
	 * Creation of the innerPanels that display the subs to be made by the staff, 
	 * this includes a making each panel a listener to perform an action when it is clicked on
	 * @param oN - the screenNumber that this panel is to be displayed on
	 * @param p - the parent that this panel is to be displayed on, also should match up with the screenNumber
	 */
	public StaffPanel (int oN, StaffMain p)
	{
		// Create a layout for the Panels in the order windows
		setLayout(new GridLayout(10, 1));
		// Set the default background color of the panel to black
		setForeground(Color.BLACK);
		// Set the screen number to identify which position it is in
		screenNumber = oN;
		// Create option names for the pop up when you click on an order
		options = new String[2];	options[0] = "Cancel";	options[1] = "Completed";
		// Set the default value of the panel to not completed yet
		subCompleted = false;
		// Title for the choices pop-up window
		choiceTitle = "Select Option";
		// Message for the choices pop-up window
		choiceMessage = "Is the order finished?";
		// Set the parent of the panel to its order window
		parent = p;
		// Set the background on each of the inner panels. The inner panels are just blue for now to make sure that they are the right size
		setBackground(Color.BLACK);
		// Make each of the inner panels listener so they can be clicked
		MouseAdapter listener = new MouseAdapter()
		{	
			public void mousePressed(MouseEvent me)
			{
				// Check if the panel is empty, if it is don't do anything
				if(((StaffPanel)me.getComponent()).getComponentCount() == 0)
				{}
				// If the sub has already been completed clear it
				else if (((StaffPanel) me.getComponent()).subCompleted == true)
				{
					// Clear the panel from the screen and shuffle the other panels to fill up the empty slot
					Clear ((StaffPanel) me.getComponent(), parent);
				}
				// Sub has been finished so mark it completed
				else
				{
//					int choice = JOptionPane.showOptionDialog(me.getComponent(), choiceMessage, choiceTitle, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, 2);
//					if (choice == 0)
//					{
//						Cancel((StaffPanel) me.getComponent(), parent);
//					}
//					else if (choice == 1)
					{
						// Change the background on the panel to mark the sub complete
						Completed((StaffPanel) me.getComponent());
					}
				}
			}};
			// Add the Listener to each panel
			addMouseListener(listener);
	}

	/**
	 * Clearing the order from the screen, then calls shufflePanels
	 * @param CurPanel - current Panel that is being cleared from the screen
	 * @param parent - the panel that holds all of the order windows
	 */
	public void Clear(StaffPanel CurPanel, StaffMain parent)
	{
		// Change the status to not completed
		CurPanel.subCompleted = false;
		// Set the background back to the defalt black
		CurPanel.setBackground(Color.BLACK);
		// Remove all components from the panel
		CurPanel.removeAll();
		// Call the shuffle panels method to keep the screen up to date
		ShufflePanels(CurPanel, parent);
	}
	
	/**
	 * Changes the background on the current panel to red so the staff 
	 * knows the sub is completed and ready for pickup
 	 * Also it reports to the database that the sub was cancelled (not implemented here as it conflicted with our database)
	 * @param CurPanel - current panel that is being marked completed
	 */
	public void Completed(StaffPanel CurPanel)
	{
		// Mark the sub completed
		CurPanel.subCompleted = true;
		// Change the color of the panel to show the staff it's completed
		CurPanel.setBackground(Color.GREEN);
		// Set the panel's success to true, not used here as it would conflict with the database
		//CurPanel.success = true;
		// Report the success of the order to the database, not used here as it would conflict with the database
		//ReportOrder(CurPanel);
	}

	/**
	 * Cancels the order and removes the current panel from the 
	 * order window, then it calls shufflePanels
	 * Also it reports to the database that the sub was cancelled (not implemented here as it conflicted with our database)
	 * @param CurPanel - the current panel that is being cancelled
	 * @param parent - the panel that holds all of the order windows
	 */
	public void Cancel(StaffPanel CurPanel, StaffMain parent)
	{	
		// Set the completed status to the default false
		CurPanel.subCompleted = false;
		// Remove all components from the panel
		CurPanel.removeAll();
		// Set the panel background to the default Black
		CurPanel.setBackground(Color.BLACK);
		// Repaint the panel to keep it updated
		CurPanel.repaint();
		// Call the shuffle panels method to keep the screen up to date
		ShufflePanels(CurPanel, parent);
		// Set the panel's success to false, not used here as it would conflict with the database
		//CurPanel.success = false;
		// Report the success of the order to the database, not used here as it would conflict with the database
		//ReportOrder(CurPanel);
	}

	/**
	 * Shuffles panels forward (towards Order 1), starting at where the current Panel is, 
	 * and stops when it has filled all 4 order windows with a panel (whether or not that panel is empty)
	 * @param CurPanel - the starting point for the method to start shuffling the other panels forward
	 * @param parent - the panel that holds all of the order windows
	 */
	public void ShufflePanels(StaffPanel CurPanel, StaffMain parent)
	{
		// Sequence of events for each panel being shuffled
		// Remove the Panel from the order
		// Assign the next panel to the current panel's spot
		// Assign the current panel the correct screenNumber
		// Add the new panel to the current order screen
		// Repaint the order screen so the new panel displays correctly
		
		// Order 1 has been cleared so shuffle all panels down to replace it and insert the off screen panel for Order 4
		if(CurPanel.screenNumber == 1)
		{
			parent.order1.remove(parent.innerPanel1);
			parent.innerPanel1 = parent.innerPanel2;
			parent.innerPanel1.screenNumber = 1;
			parent.order1.add(parent.innerPanel1);
			parent.order1.repaint();
			parent.innerPanel2 = parent.innerPanel3;
			parent.innerPanel2.screenNumber = 2;
			parent.order2.remove(parent.innerPanel2);
			parent.order2.add(parent.innerPanel2);
			parent.order2.repaint();
			parent.innerPanel3 = parent.innerPanel4;
			parent.innerPanel3.screenNumber = 3;
			parent.order3.remove(parent.innerPanel2);
			parent.order3.add(parent.innerPanel3);
			parent.order3.repaint();
			parent.innerPanel4 = parent.offScreenPanel;
			parent.innerPanel4.screenNumber = 4;
			parent.order4.remove(parent.innerPanel2);
			parent.order4.add(parent.innerPanel4);
			parent.order4.revalidate();
			parent.order4.repaint();
			parent.offScreenPanel = new StaffPanel(5, parent);
		}
		// Order 2 has been cleared so shuffle panels 3 and 4 down to cover it and insert the off screen panel for Order 4
		else if (CurPanel.screenNumber == 2)
		{
			parent.order2.remove(parent.innerPanel2);
			parent.innerPanel2 = parent.innerPanel3;
			parent.innerPanel2.screenNumber = 2;
			parent.order2.add(parent.innerPanel2);
			parent.order2.repaint();
			parent.innerPanel3 = parent.innerPanel4; 
			parent.innerPanel3.screenNumber = 3;
			parent.order3.remove(parent.innerPanel3); 
			parent.order3.add(parent.innerPanel3);
			parent.order3.repaint();
			parent.innerPanel4 = parent.offScreenPanel; 
			parent.innerPanel4.screenNumber = 4;
			parent.order4.remove(parent.innerPanel4); 
			parent.order4.add(parent.innerPanel4);
			parent.order4.revalidate();
			parent.order4.repaint();
			parent.offScreenPanel = new StaffPanel(5, parent);
		}
		// Order 3 has been cleared so shuffle Order 4 into it's place and insert the off screen panel for Order 4
		else if (CurPanel.screenNumber == 3)
		{
			parent.order3.remove(parent.innerPanel3); 
			parent.innerPanel3 = parent.innerPanel4; 
			parent.innerPanel3.screenNumber = 3;
			parent.order3.add(parent.innerPanel3);
			parent.order3.repaint();
			parent.innerPanel4 = parent.offScreenPanel; 
			parent.innerPanel4.screenNumber = 4;
			parent.order4.remove(parent.innerPanel4); 
			parent.order4.add(parent.innerPanel4);
			parent.order4.revalidate();
			parent.order4.repaint();
			parent.offScreenPanel = new StaffPanel(5, parent);
		}
		// Order 4 has been cleared so insert the off screen panel in its place
		else if (CurPanel.screenNumber == 4)
		{
			parent.order4.remove(parent.innerPanel4);
			parent.innerPanel4 = parent.offScreenPanel; 
			parent.innerPanel4.screenNumber = 4;
			parent.order4.add(parent.innerPanel4);
			parent.order4.revalidate();
			parent.order4.repaint();
			parent.offScreenPanel = new StaffPanel(5, parent);
		}
		// An error occurred and we need to throw an exception
		else {throw new RuntimeException("");}
	}

	/**
	 * Builds the order that is displayed in the panel so that the staff can see what to make
	 * 
	 * @param sub - the sub that needs to be made
	 * @throws RemoteException - potential error if there is a problem connecting to the server
	 */
	public void BuildOrder(Sandwich sub) throws RemoteException
	{
		// Set the panel to be not visible while it's being built
		this.setVisible(false);
		// Create a layout that suits each individual sub
		this.setLayout(new GridLayout((5+sub.vegetables.size()+sub.sauces.size()), 1));
		// Get the size of the sub and also make it show up as white and in the center of the panel
		JLabel size = new JLabel(sub.getSize()+"-inch"); size.setForeground(Color.WHITE); size.setHorizontalAlignment(JLabel.CENTER);
		// Add the size to the panel
		this.add(size);
		// Get the bread for the sub and make it show up as white and in the center of the panel
		JLabel bread = new JLabel(sub.getBread()); bread.setForeground(Color.WHITE); bread.setHorizontalAlignment(JLabel.CENTER);
		// Add the bread to the panel
		this.add(bread);
		// Get the meat type from the sub, make it show up as white and in the center of the panel
		JLabel meat = new JLabel(sub.getMeat()); meat.setForeground(Color.WHITE); meat.setHorizontalAlignment(JLabel.CENTER);
		// Add the meat to the panel
		this.add(meat);
		// Get the cheese for the sub, make it show up as white and in the center of the panel
		JLabel cheese = new JLabel(sub.getCheese()); cheese.setForeground(Color.WHITE); cheese.setHorizontalAlignment(JLabel.CENTER);
		// Add the cheese to the panel
		this.add(cheese);
		// Create the toasted label, make it white and move it to the center of the panel
		JLabel toasted = new JLabel(""); toasted.setForeground(Color.WHITE); toasted.setHorizontalAlignment(JLabel.CENTER);
		// Determine if the sub is toasted or not
		if(sub.getToasted())
			toasted.setText("Toasted");
		else
			toasted.setText("Not Toasted");
		// Add toasted to the panel
		this.add(toasted);
		// For each vegetable that was selected, create a label for it and add it to the panel
		for(String veg : sub.getVegetables())
		{
			JLabel vegList = new JLabel(veg);	vegList.setForeground(Color.RED); vegList.setHorizontalAlignment(JLabel.CENTER);
			this.add(vegList);
		}
		// For each sauce that was selected, create a label for it and add it to the panel
		for(String sauce : sub.getSauce())
		{
			JLabel sauceList = new JLabel(sauce);	sauceList.setForeground(Color.MAGENTA); sauceList.setHorizontalAlignment(JLabel.CENTER);
			this.add(sauceList);
		}
		// Refresh the panel to make sure everything will show up correctly
		this.repaint();
		// Set the panel visible so that it can be seen by the staff
		this.setVisible(true);
	}
}
