package managergui;

import java.sql.*;
import java.util.*;

import javax.swing.*;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.*;
import javax.swing.table.*;

import databaseconnection.databaseConnect;

public class ItemAvailability extends JPanel{


	private static final long serialVersionUID = 1L;
	public boolean DEBUG = false;
	public JTable table;
	public Vector<Object> columnNames = new Vector<Object>();
	public Vector<Object> data = new Vector<Object>();
	public JPanel panel = new JPanel(); 
	JFrame frame;

	/**
	 * Opens and new window that shows all the item 
	 * availability of each item on the menu  in a table format
	 */
	public ItemAvailability()
	{
		super();
		try {
			databaseConnect DB= new databaseConnect();
			ResultSet resultSet = DB.viewInfo("SandwichType");
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columns = metaData.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				columnNames.addElement(metaData.getColumnName(i));
			}
			while (resultSet.next()) {
				Vector<Object> row = new Vector<Object>(columns);
				for (int i = 1; i <= columns; i++) {
					Object str = resultSet.getObject(i);
					if(i==5)
					{
						if(str.toString().equals("1")){
							str="Yes";
						}
						else
						{
							str="No";
						}
					}
					row.addElement(str);
				}
				data.addElement(row);
			}
			resultSet.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		String str ="";
		Iterator<Object> iter=data.iterator();
		List<String> test =null;
		ArrayList<String> subs = new ArrayList<String>();
		int i=0;
		while(iter.hasNext())
		{
			str =(data.get(i).toString());
			test= Arrays.asList(str.split(","));
			subs.add(test.get(1).toString());
			iter.next();
			i++;
		}

		table = new JTable();
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {

				return false;
			}
		};
		tableModel.fireTableChanged(new TableModelEvent(tableModel));
		table.setModel(tableModel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		final String[] toPass = new String[subs.size()];
		for(int j=0; j < subs.size(); j++)
		{
			toPass[j]=subs.get(j).toString().trim();
		}

		JScrollPane scrollPane = new JScrollPane(table);        
		panel.add(scrollPane);
		frame = new JFrame("Item Availability");
		frame.add(panel);         //adding panel to the frame
		Toolkit tk = Toolkit.getDefaultToolkit();
		int x = ((int)tk.getScreenSize().getWidth());
		int y = ((int)tk.getScreenSize().getHeight());
		//table will scale according to screen size
		table.setPreferredScrollableViewportSize(new Dimension(x-(x/10), y-(y/5)));
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		frame.setVisible(true);  
		JButton changeButton = new JButton("Change Item Availability");
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new setAvailability(toPass);
			}
		});
		panel.add(changeButton);
		
		//Refreshes the table when button is pressed
		JButton Refresh = new JButton("Refresh");
		Refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new ItemAvailability();
			}
		});
		panel.add(Refresh);
	}

	public static void main(String[] args) {
		new ItemAvailability();
	}
}