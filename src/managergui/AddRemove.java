package managergui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import databaseconnection.databaseConnect;


@SuppressWarnings({"rawtypes", "unchecked"})
public class AddRemove extends JPanel{

	/**
	 * Opens a new window to add or remove a manager
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	public String sql;


	/**
	 * Create the application.
	 */
	public AddRemove() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Add or Remove Manager");
		frame.setBounds(100, 100, 528, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		firstNameTextField = new JTextField();
		firstNameTextField.setBounds(150, 43, 285, 24);
		frame.getContentPane().add(firstNameTextField);
		firstNameTextField.setColumns(50);
		lastNameTextField = new JTextField();
		lastNameTextField.setColumns(50);
		lastNameTextField.setBounds(150, 99, 285, 24);
		frame.getContentPane().add(lastNameTextField);
		final JComboBox comboBox = new JComboBox();
		comboBox.setMaximumRowCount(2);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Add", "Remove"}));
		comboBox.setBounds(150, 150, 283, 20);
		frame.getContentPane().add(comboBox);

		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sql = "";
				databaseConnect DB= new databaseConnect();
				String firstName=firstNameTextField.getText();
				String lastName=lastNameTextField.getText();
				firstName=firstName.replaceAll("\\s", ""); //Removing any white spaces the user might have entered
				lastName=lastName.replaceAll("\\s", "");
				Random rand = new Random();
				String userID=firstName.substring(0, Math.min(firstName.length(), 2))
						+lastName.substring(0, Math.min(lastName.length(), 2))
						+Integer.toString(rand.nextInt(9))
						+Integer.toString(rand.nextInt(9))+Integer.toString(rand.nextInt(9));
				userID=userID.toLowerCase();
				String userPassword=lastName.substring(0, Math.min(lastName.length(),3))+firstName.substring(0, Math.min(firstName.length(), 3));
				userPassword=userPassword.toLowerCase();


				/*checks if userID already exist in database if it does it generates a new one */
				try{
					while(DB.hasUser(userID))
					{
						userID=firstName.substring(0, Math.min(firstName.length(), 2))
								+lastName.substring(0, Math.min(lastName.length(), 2))
								+Integer.toString(rand.nextInt(9))
								+Integer.toString(rand.nextInt(9))+Integer.toString(rand.nextInt(99));
						userID=userID.toLowerCase();
					}
				}
				catch (Exception error){}
				userID=userID.toLowerCase();

				if(comboBox.getSelectedItem().equals("Add"))
				{
					try{
						DB.addManager(userID, userPassword, firstName, lastName);
					}
					catch(Exception error)
					{
						System.out.println(error.toString());
					}
				}
				else if(comboBox.getSelectedItem().equals("Remove")){
					new confirmRemove(firstName,lastName);
				}

			}
		});

		submitButton.setBounds(161, 227, 89, 23);
		frame.getContentPane().add(submitButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		cancelButton.setBounds(274, 227, 89, 23);
		frame.getContentPane().add(cancelButton);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFirstName.setBounds(31, 50, 79, 17);
		frame.getContentPane().add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLastName.setBounds(31, 106, 89, 17);
		frame.getContentPane().add(lblLastName);
		frame.setVisible(true);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddRemove window = new AddRemove();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
