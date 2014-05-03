package managergui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import databaseconnection.databaseConnect;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class confirmRemove extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField manageridtextfield;
	public String userID;
	public String sql;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					confirmRemove frame = new confirmRemove("test","tset");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public String getUserID(){
		return userID;
	}
	public confirmRemove(final String firstName, final String lastName) {
		setTitle("Confirm Remove?");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		manageridtextfield = new JTextField();
		manageridtextfield.setBounds(117, 88, 243, 23);
		contentPane.add(manageridtextfield);
		manageridtextfield.setColumns(50);
		
		final databaseConnect DB= new databaseConnect();
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				userID= manageridtextfield.getText();
				userID=userID.replaceAll("\\s", "");
				try{
					DB.removeManager(userID);
					dispose();
				}
				catch(Exception error)
				{
					System.out.println(error.toString());
				}
			}
		});
		submitButton.setBounds(136, 199, 89, 23);
		contentPane.add(submitButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		cancelButton.setBounds(234, 199, 89, 23);
		contentPane.add(cancelButton);
		
		JLabel ManagerID = new JLabel("Manager ID");
		ManagerID.setFont(new Font("Tohoma", Font.BOLD, 13));
		ManagerID.setBounds(10, 89, 87, 19);
		contentPane.add(ManagerID);
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
