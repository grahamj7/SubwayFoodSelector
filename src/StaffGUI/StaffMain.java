package StaffGUI;
import java.awt.*;
import java.rmi.RemoteException;

import javax.swing.*;
import javax.swing.border.Border;

import userInterfaces.SandwichQueueInterface;

import customerGUI.Sandwich;
import customerGUI.SandwichQueue;

public class StaffMain extends Thread
{
	/**
	 * The main variables that this class requires
	 * mainFrame - The main frame that holds the staff GUI
	 * title - the title for each order window that shows in the panels order1-4
	 * mainPanel - the mainPanel in the StaffMain frame that holds all of the order windows
	 * order1-4 - the order windows that hold the order panels
	 * innerPanel1-4 - the panels that display each subs order
	 * orderBorder - the border that surrounds each order window to separate each order window
	 * cyanFont - the font for the order titles
	 */
	JFrame mainFrame;
	JLabel title;
	JPanel mainPanel;
	JPanel order1, order2, order3, order4;
	StaffPanel innerPanel1, innerPanel2, innerPanel3, innerPanel4, offScreenPanel;
	Border orderBorder;
	Font cyanFont;
	boolean isServer;

	/**
	 * Create the frame to show the staffGUI and display the orders to the staff
	 */
	public StaffMain(boolean server)
	{
		isServer = server;

		mainFrame = new JFrame();
		// Remove the minimize, maximize and close buttons from the window
		mainFrame.setUndecorated(true);
		// Set default close operation to exit the program
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set the frame to maximize when it opens on the server, other wise make it half the size
		if(isServer)
			mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		else
		{
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			mainFrame.setSize(screenSize.width/2, screenSize.height);
			mainFrame.setLocation(screenSize.width/2, 0);
		}

		// Set the title of the frame
		mainFrame.setTitle("StaffMain");
		// Create the panel to go inside the frame
		mainPanel = new JPanel();
		// Set the layout scheme for the main panel
		mainPanel.setLayout(new GridLayout(1, 4));
		// Create the border to surround each order screen
		orderBorder = BorderFactory.createLineBorder(Color.WHITE, 5, false);

		// Create the 4 panels for the order screens
		order1 = new JPanel();	order2 = new JPanel();	order3 = new JPanel();	order4 = new JPanel();
		// Set the background to black for each order screen
		order1.setBackground(Color.BLACK);	order2.setBackground(Color.BLACK);	order3.setBackground(Color.BLACK);	order4.setBackground(Color.BLACK);
		// Add border to each order screen
		order1.setBorder(orderBorder);	order2.setBorder(orderBorder);	order3.setBorder(orderBorder);	order4.setBorder(orderBorder);	
		// Set the layout for each of the order screens
		order1.setLayout(new BoxLayout(order1, BoxLayout.PAGE_AXIS));	order2.setLayout(new BoxLayout(order2, BoxLayout.PAGE_AXIS));	order3.setLayout(new BoxLayout(order3, BoxLayout.PAGE_AXIS));	order4.setLayout(new BoxLayout(order4, BoxLayout.PAGE_AXIS));

		// Create a font to use for the titles of the order screens
		cyanFont = new Font("cyan", Font.HANGING_BASELINE, 30);
		// Create title for the first order screen
		title = new JLabel("Order 1");	title.setForeground(Color.CYAN);	title.setFont(cyanFont);	title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		// Add the title to the first order screen
		order1.add(title);
		// Create title for the second order screen
		title = new JLabel("Order 2");	title.setForeground(Color.CYAN);	title.setFont(cyanFont);	title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		// Add the title to the second order screen
		order2.add(title);
		// Create title for the third order screen
		title = new JLabel("Order 3");	title.setForeground(Color.CYAN);	title.setFont(cyanFont);	title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		// Add the title to the third order screen
		order3.add(title);
		// Create title for the fourth order screen
		title = new JLabel("Order 4");	title.setForeground(Color.CYAN);	title.setFont(cyanFont);	title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		// Add the title to the fourth order screen
		order4.add(title);

		// Create a panel that is always ready to be used to keep the order screens up to date with new orders
		offScreenPanel = new StaffPanel(5, this);
		// Create inner panels for each of the order screens
		innerPanel1 = new StaffPanel(1, this);	innerPanel2 = new StaffPanel(2, this);	innerPanel3 = new StaffPanel(3, this);	innerPanel4 = new StaffPanel(4, this);
		// Add the inner panels to the order screens
		order1.add(innerPanel1);	order2.add(innerPanel2);	order3.add(innerPanel3);	order4.add(innerPanel4);		
		// Add the order screens to the main panel
		mainPanel.add(order1);	mainPanel.add(order2);	mainPanel.add(order3);	mainPanel.add(order4);	
		// Add the main panel to the frame
		mainFrame.add(mainPanel);
		// Make the frame visible
		mainFrame.setVisible(true);
	}

	/**
	 * Run the program  to continually check for new sandwiches in the 
	 * queue and add them to the staff screen if it isn't already full
	 * @param program - the staffMain frame this function is being run by
	 * @param sQ - the queue that this function gets its sandwiches from
	 * @throws RemoteException - this method can throw an error if it loses connectivity with the server
	 */
	public void runMain(StaffMain program, SandwichQueueInterface sQ) throws RemoteException
	{
		// Continually check for new subs in the sandwich queue, even if it's empty
		// When there is room for another order to be added to the screen or to the off screen panel get the next sub
		if(!sQ.getQueue().isEmpty() && program.offScreenPanel.getComponentCount() == 0)
		{
			// Get the next sub from the queue
			Sandwich sub = sQ.remove();
			// Create a new panel to hold the new sub
			program.offScreenPanel = new StaffPanel(5, program);
			// Build the order in the new panel
			program.offScreenPanel.BuildOrder(sub);

			// Find where the first empty space is, put the new panel in that position, repaint and revalidate that order window
			if(program.innerPanel1.getComponentCount() == 0)
			{
				program.order1.remove(program.innerPanel1);
				program.innerPanel1 = program.offScreenPanel;
				program.innerPanel1.screenNumber = 1;
				program.order1.add(program.innerPanel1);
				program.innerPanel1.repaint();
				program.order1.revalidate();
				program.order1.repaint();
				program.mainFrame.repaint();
				program.offScreenPanel = new StaffPanel(5, program);
			}
			else if (program.innerPanel2.getComponentCount() == 0)
			{
				program.order2.remove(program.innerPanel2);
				program.innerPanel2 = program.offScreenPanel;
				program.innerPanel2.screenNumber  = 2;
				program.order2.add(program.innerPanel2);
				program.innerPanel2.repaint();
				program.order2.revalidate();
				program.order2.repaint();
				program.mainFrame.repaint();
				program.offScreenPanel = new StaffPanel(5, program);
			}
			else if(program.innerPanel3.getComponentCount() == 0)
			{
				program.order3.remove(program.innerPanel3);
				program.innerPanel3 = program.offScreenPanel;
				program.innerPanel3.screenNumber  = 3;
				program.order3.add(program.innerPanel3);
				program.innerPanel3.repaint();
				program.order3.revalidate();
				program.order3.repaint();
				program.mainFrame.repaint();
				program.offScreenPanel = new StaffPanel(5, program);
			}
			else if(program.innerPanel4.getComponentCount() == 0)
			{
				program.order4.remove(program.innerPanel4);
				program.innerPanel4 = program.offScreenPanel;
				program.innerPanel4.screenNumber  = 4;
				program.order4.add(program.innerPanel4);
				program.innerPanel4.repaint();
				program.order4.revalidate();
				program.order4.repaint();
				program.mainFrame.repaint();
				program.offScreenPanel = new StaffPanel(5, program);
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public void run()
	{}

	/**
	 * For Testing purposes only, create 10 sandwiches, put them 
	 * in a queue, run the program to make sure that they all get 
	 * put on the screen in order they were entered in the queue 
	 * and make sure clearing and shuffling work properly
	 * @param args - picks up any extra strings that were added when calling the routine
	 */
	public static void main(String[] args) 
	{
		// Start the staffMain frame
		StaffMain program = new StaffMain(false);
		// Create a sandwich that can be remade again and again
		Sandwich sub = null;
		//  Start the sandwich queue
		SandwichQueue sQ;
		try {
			sQ = new SandwichQueue();
			// Create 10 subs and add them all to the queue
			for(int i = 1; i <= 10; i++)
			{
				sub = new Sandwich();	sub.setBread("Itallian");	sub.setMeat("CCC");	sub.setCheese("Cheddar");	sub.setToasted(true);
				sub.addSauce("Mayo"); sub.addSauce("Sauce"); sub.addVegetables("Tomatoes"); sub.addVegetables("Lettuce");	sub.setSize(6);
				sub.setSize(i);	
				sQ.add(sub);
			}
			// Run the program using the premade sandwich queue
			QueueListener listener = new QueueListener(program, sQ);
			listener.start();
			//program.runMain(program, sQ);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
