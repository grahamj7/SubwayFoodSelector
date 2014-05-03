package managergui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

import databaseconnection.databaseConnect;

@SuppressWarnings({"unchecked", "rawtypes"})
public class setAvailability extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public Vector<Object> data = new Vector<Object>();
	public JPanel contentPane;
	public Object selection = new Object();
	public String sql="";
	public int avail;

	/**
	 * Launches the application.
	 */
	public static void main (String[] args) {
		final String[] test= {"A","B","C","D"};
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setAvailability frame = new setAvailability(test);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create a combo box that allows the manager to choose 
	 * what item will be available or not
	 * @param list 
	 */
	public setAvailability(String[] list) {
		setTitle("Set Availability");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JComboBox listofSubs = new JComboBox();
		listofSubs.setModel(new DefaultComboBoxModel(list));
		listofSubs.setBounds(73, 44, 294, 34);
		contentPane.add(listofSubs);
		
		JButton setUnavailablebtn = new JButton("Set Unavailable");
		setUnavailablebtn.setBounds(73, 216, 130, 34);
		contentPane.add(setUnavailablebtn);
		selection = listofSubs.getSelectedItem();
		final databaseConnect DB= new databaseConnect();
		setUnavailablebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selection=listofSubs.getSelectedItem();

				try{
					DB.setAvail(selection.toString(), false);
				}
				catch (Exception error){
					System.err.println(error);
				}
			}
		});
		
		JButton setAvailablebtn = new JButton("Set Available");
		setAvailablebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selection=listofSubs.getSelectedItem();

				try{
					DB.setAvail(selection.toString(), true);	
				}
				catch (Exception error){
					System.err.println(error);
				}
				
			}
		});
	

		setAvailablebtn.setBounds(238, 216, 129, 34);
		contentPane.add(setAvailablebtn);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
}
