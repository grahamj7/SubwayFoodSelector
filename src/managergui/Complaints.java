package managergui;

import java.sql.*;
import java.util.*;

import javax.swing.*;

import java.awt.Toolkit;
import java.awt.Dimension;

import javax.swing.table.*;

import databaseconnection.databaseConnect;


public class Complaints extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean DEBUG = false;
	public JTable table;
	public JTextField filterText;
	public JTextField statusText;
	public Vector<Object> columnNames = new Vector<Object>();
	public Vector<Object> data = new Vector<Object>();
	public JPanel panel = new JPanel(); 

	/**
	 * Creates a new window of all the complains that has been made
	 * in a table format
	 */
	public Complaints()
	{
		super();
		try {
			databaseConnect DB = new databaseConnect();
			ResultSet resultSet = DB.viewInfo("Feedback");
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columns = metaData.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				columnNames.addElement(metaData.getColumnName(i));
			}
			while (resultSet.next()) {
				Vector<Object> row = new Vector<Object>(columns);
				for (int i = 1; i <= columns; i++) {
					row.addElement(resultSet.getObject(i));
				}
				data.addElement(row);
			}
			resultSet.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		table = new JTable();
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {


			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {

				return false;
			}
		};

		table.setModel(tableModel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(table);        
		panel.add(scrollPane);               
		JFrame frame = new JFrame("Complaints");
		frame.add(panel);         //adding panel to the frame
		Toolkit tk = Toolkit.getDefaultToolkit();
		int x = ((int)tk.getScreenSize().getWidth());
		int y = ((int)tk.getScreenSize().getHeight());
		table.setPreferredScrollableViewportSize(new Dimension(x-(x/10), y-(y/3)));
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		frame.setVisible(true);  
	}

	public static void main(String[] args) {
		new Complaints();
	}
}