package managergui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import startup.StartWin;
import userInterfaces.SandwichQueueInterface;
import databaseconnection.databaseConnect;

public class managerlogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userfield;
	private JPasswordField passfield;
	private JLabel lblNewLabel;
	private String userID;
	private String password;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SandwichQueueInterface sQ = null;
					managerlogin frame = new managerlogin(sQ, false);
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
	public managerlogin(final SandwichQueueInterface sQ, final boolean isServer) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userfield = new JTextField();
		userfield.setBounds(132, 42, 211, 20);
		contentPane.add(userfield);
		userfield.setColumns(10);
		
		passfield = new JPasswordField();
		passfield.setBounds(132, 136, 211, 20);
		contentPane.add(passfield);
		passfield.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(29, 139, 93, 14);
		contentPane.add(lblPassword);
		
		lblNewLabel = new JLabel("User I.D.");
		lblNewLabel.setBounds(29, 45, 93, 14);
		contentPane.add(lblNewLabel);
		
		JButton loginbtn = new JButton("Log In");
		loginbtn.setBounds(132, 227, 89, 23);
		contentPane.add(loginbtn);
		final databaseConnect DB= new databaseConnect();
		loginbtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				userID= userfield.getText();
				userID=userID.replaceAll("\\s", "");
				password=passfield.getText();
				userID=userID.replaceAll("\\s", "");
				try{
					if(DB.userValidation(userID, password))
					{
						new ManagerSelector(sQ, isServer);
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Incorrect user or password");
					}
					
				}
				catch(Exception error)
				{
					System.out.println(error.toString());
				}
			}
		});
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(254, 227, 89, 23);
		contentPane.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new StartWin(sQ, isServer);
				dispose();
			}
		});
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
