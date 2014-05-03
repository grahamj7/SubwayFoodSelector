package customerGUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.LinkedList;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import databaseconnection.databaseConnect;

public class PaymentGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	public databaseConnect DB;
	private double sum, tax;

	/**
	 * SQL Functions
	 * paid used sql function call to insert transaction into the database
	 * run uses sql function call to get the price of all the subs on the order from the database
	 */

	public void paid(CustomerGUI main, double cost) throws RemoteException, SQLException
	{
		int typeID = 0, size = 0, transID = 1;
		for(Sandwich sub: main.order)
			main.queue.add(sub);

		DB = new databaseConnect();

		DB.addTransaction(cost);	

		for(Sandwich sub : main.order)
		{
			transID = DB.getTransId();
			typeID = DB.getTypeId(sub.getMeat());
			size = sub.getSize();
			DB.addTransItem(typeID, size, transID);
		}
		DB.closeConnection();
	}

	public void run(final CustomerGUI main) throws RemoteException
	{
		setTitle("Payment");
		setUndecorated(true);
		if(main.isServer)
			setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		else
		{
			if(main.isServer)
				setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			else
			{
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				setSize(screenSize.width/2, screenSize.height);
			}
			}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 5));
		JPanel options = new JPanel();	options.setLayout(new GridLayout(6, 1));
		JPanel sandwich;

		LinkedList<Double> prices = new LinkedList<Double>();
		DB = new databaseConnect();
		for(Sandwich sub : main.order)
		{
			sub.vegetables.size();
			sandwich = new JPanel();
			sandwich.setLayout(new GridLayout((5+sub.vegetables.size()+sub.sauces.size()), 1));
			JLabel size = new JLabel(sub.getSize()+"-inch"); size.setHorizontalAlignment(JLabel.CENTER);sandwich.add(size);
			JLabel bread = new JLabel(sub.getBread()+" Bread"); bread.setHorizontalAlignment(JLabel.CENTER);sandwich.add(bread);
			JLabel meat = new JLabel(sub.getMeat()); meat.setHorizontalAlignment(JLabel.CENTER);sandwich.add(meat);
			JLabel cheese = new JLabel(sub.getCheese()); cheese.setHorizontalAlignment(JLabel.CENTER);sandwich.add(cheese);
			JLabel toast = new JLabel();if(sub.getToasted()){toast.setText("Toasted");}else{toast.setText("Not Toasted");} toast.setHorizontalAlignment(JLabel.CENTER);sandwich.add(toast);
			JLabel veg; for(String v : sub.getVegetables()){veg = new JLabel(v); veg.setHorizontalAlignment(JLabel.CENTER);sandwich.add(veg);}
			JLabel sauce; for(String s : sub.getSauce()){sauce = new JLabel(s); sauce.setHorizontalAlignment(JLabel.CENTER);sandwich.add(sauce);}
			panel.add(sandwich);
	
			try{prices.add(DB.getPrice(sub.getSize(), sub.getMeat()));
			}catch(SQLException e){e.printStackTrace();}
		}
		DB.closeConnection();

		sum = 0.0;
		tax = 0.1;
		for(double d : prices){sum += d;} tax = sum * tax;
		JPanel payment = new JPanel();	payment.setLayout(new GridLayout(3, 1));
		JLabel subTotal = new JLabel("SubTotal: " + String.format( "%.2f", sum ));	subTotal.setHorizontalAlignment(JLabel.CENTER);
		JLabel taxes = new JLabel("Tax: " + String.format( "%.2f", tax ));	taxes.setHorizontalAlignment(JLabel.CENTER);
		JLabel total = new JLabel("Total: " + String.format( "%.2f", (sum + tax)));	total.setHorizontalAlignment(JLabel.CENTER);
		payment.add(subTotal); payment.add(taxes); payment.add(total);

		JButton cash = new JButton("Cash");
		JButton credit = new JButton("Credit");
		JButton debit = new JButton("Debit");
		JButton back = new JButton("Back");	
		JButton cancel = new JButton("Cancel Sub");
		back.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (main.order.size() == 1)
					new SauceGUI().run(main.order.remove(), main);
				else
				{
					String[] options = new String[main.order.size()];
					for (int i = 0; i < options.length; i++)
					{
						Sandwich temp = main.order.get(i);
						try {
							options[i] = temp.getSize() + "-inch " + temp.getMeat();
						} catch (RemoteException e1) {}
					}
					int choice = JOptionPane.showOptionDialog(null, "Which sub do you want to edit?", "Choose a sub", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
					if(choice == -1)
					{
						try {
							new PaymentGUI().run(main);
						} catch (RemoteException e1) {}
					}
					else
						new SauceGUI().run(main.order.remove(choice), main);
				}
				dispose();
			}
		});

		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (main.order.size() == 1)
				{
					main.order.removeLast();
					main.start.setEnabled(true);
				}
				else
				{
					String[] options = new String[main.order.size()];
					for (int i = 0; i < options.length; i++)
					{
						Sandwich temp = main.order.get(i);
						try {
							options[i] = temp.getSize() + "-inch " + temp.getMeat();
						} catch (RemoteException e1) {}
					}
					int choice = JOptionPane.showOptionDialog(null, "Which sub do you want to edit?", "Choose a sub", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
					if(choice == -1)
					{
						try {
							new PaymentGUI().run(main);
						} catch (RemoteException e1) {}
					}
					else
					{
						main.order.remove(choice);
						new MoreSubs().run(main);
					}
				}
				dispose();
			}
		});

		ActionListener listener = new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				main.start.setEnabled(true);

				try {
					if(((JButton)event.getSource()).getText().equals("Cash")){paid(main, sum+tax);}
					else if(((JButton)event.getSource()).getText().equals("Credit")){paid(main, sum+tax);}
					else if(((JButton)event.getSource()).getText().equals("Debit")){paid(main, sum+tax);}
				} catch (RemoteException | SQLException e) {e.printStackTrace();}
				dispose();
			}
		};

		cash.addActionListener(listener);
		credit.addActionListener(listener);
		debit.addActionListener(listener);

		options.add(payment);
		options.add(cash);
		options.add(credit);
		options.add(debit);
		options.add(back);
		options.add(cancel);
		panel.add(options);
		add(panel);

		setVisible(true);
		DB.closeConnection();
	}
}
