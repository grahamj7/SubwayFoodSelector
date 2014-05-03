/**
 * This class is used to connect to the database
 * Eample to create a new sql statement:
 * databaseConnect test= new databaseConnect();
 * String sql="SELECT * FROM tableName ORDER BY column DESC;";
 * Statement statement = test.conn.createStatement();
 * ResultSet resultSet = statement.executeQuery(sql);
 * 
 */

package databaseconnection;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.LinkedList;

public class databaseConnect {
	public Connection conn;
	public final String url="jdbc:mysql://edjo.usask.ca:3306/cmpt370_group11";
	public final String user="cmpt370_group11";
	public final String password="tkwuiql832";
	
	/**
	 * Gets all the details from either SandwichType, Feedback or Transaction history
	 * @param table - is the table name of which information to take from
	 */
	public ResultSet viewInfo(String table) throws RemoteException, SQLException
	{
		final String query;
		
		if(table.equals("SandwichType")){
			query = "SELECT * FROM  SandwichType";
		}
		else if(table.equals("Transactions"))
		{
			query = "SELECT * FROM Transactions ORDER BY activity DESC";
		}
		else
		{
			query = "SELECT * FROM Feedback ORDER BY activity DESC";
		}
		
		/*
		 * Setting up the query, eliminates sql injection.
		 */
		final PreparedStatement ps = this.conn.prepareStatement(query);
		return ps.executeQuery();	
	}	
	/**
	 * Checcks if the user input the correct username and password 
	 * @param userID is the username to be checked with the database
	 * @param usrpassword is the password to be checked with the database
	 * @return true if user exists, false otherwise
	 * @throws RemoteException - RMI errors
	 * @throws SQLException - Database errors
	 */
	public boolean userValidation(String userID, String usrpassword) throws RemoteException, SQLException
	{
		/*
		 *  Setting up t he query, eliminates sql injection.
		 * 
		 */
		
		final String query ="SELECT Count(*) FROM ManagerAccounts WHERE user_id = ? && passwrd = ?";
		final PreparedStatement ps= this.conn.prepareStatement(query);
		ps.setString(1, userID);
		ps.setString(2, usrpassword);
		final ResultSet result = ps.executeQuery();
		/*
		 * If there is anything in the result, user and password is correct 
		 */
		if(result.next())
		{
			final int count=result.getInt(1);
			if(count==1){
				return true; //user and password  match 
			}
			else
				return false; //user and password do not match 
		}
		
		/*
		 * Else userid and password do no match; 
		 */
		return false;
	}
	/**
	 * Checks if the user exists in the database
	 * @param user is the user name to check if it exists
	 * @return returns true if the user exists, false otherwise
	 */
	public boolean hasUser(String user) throws RemoteException, SQLException
	{
		
		/*
		 * Setting up the query, eliminates sql injection.
		 */
		final String query = "SELECT count(*) FROM ManagerAccounts WHERE user_id = ?";
		final PreparedStatement ps = this.conn.prepareStatement(query);
		ps.setString(1, user);
		final ResultSet result = ps.executeQuery();
		
		/*
		 * If there is anything in the result, user must exist
		 */
		if(result.next()){
			final int count = result.getInt(1);
			if(count == 1){
				return true;
			}
			else{
				return false;
			}
		}
		
		/*
		 * Else user doesn't exist
		 */
		return false;
	}
	
	/**
	 * Sets the specific item in stock or not.
	 * @param orderID - is the sandwich type number to be updated
	 * @param stock - is whether the sandwich is currently out of stock or in stock
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public void setAvail(String type, boolean stock) throws RemoteException, SQLException
	{
		final String query;
		
		if(stock){
			query = "UPDATE SandwichType SET inStock=1 WHERE name = ?";
		}
		else {
			query = "UPDATE SandwichType SET inStock=0 WHERE name = ?";
		}
		
		/*
		 * Setting up the query, eliminates sql injection.
		 */
		final PreparedStatement ps = this.conn.prepareStatement(query);
		ps.setString(1, type);
		ps.executeUpdate();
		
	}
	
	/**
	 * Adds the manager to the database
	 * @param userId - the managers username to log onto the software
	 * @param pw - is the managers password to the account
	 * @param fName - First name of the manager
	 * @param lName - Last name of the manager
	 * @throws RemoteException - RMI error
	 * @throws SQLException - Database error
	 */
	public void addManager(String userId, String pw, String fName, String lName) throws RemoteException, SQLException
	{
		final String query = "INSERT INTO ManagerAccounts VALUES ( ? , ? , ? , ? )";

		/*
		 * Setting up the query, eliminates sql injection.
		 */
		final PreparedStatement ps = this.conn.prepareStatement(query);
		ps.setString(1, userId);
		ps.setString(2, pw);
		ps.setString(3, fName);
		ps.setString(4, lName);
		ps.executeUpdate();
		
	}

	/**
	 * Removes the Manager from the database
	 * @param user - is the username of the manager, It's unique
	 * @throws RemoteException - RMI errors
	 * @throws SQLException - Database errors
	 */
	public void removeManager(String user) throws RemoteException, SQLException
	{
		final String query = "DELETE FROM ManagerAccounts WHERE user_id = ?";

		/*
		 * Setting up the query, eliminates sql injection.
		 */
		final PreparedStatement ps = this.conn.prepareStatement(query);
		ps.setString(1, user);
		ps.executeUpdate();
		
	}
	
	/**
	 * Selects from the database and gets all the Available Sandwiches
	 * @throws SQLException 
	 */
	public LinkedList<String> viewAvail() throws RemoteException, SQLException
	{
		final String query = "SELECT name FROM SandwichType WHERE inStock = 1;";
		final PreparedStatement ps = this.conn.prepareStatement(query);
		final ResultSet result = ps.executeQuery();		
		LinkedList<String> name = new LinkedList<String>();
		
		if (result.next())
		{
			do{  
				name.add(result.getString(1));
			} while (result.next());  
		}
		return name;
	}

	/**
	 * Gets the price of the sub
	 * @param size - is either 6 or 12 inch subs
	 * @param type - is the type of sub that is being looked up
	 * @return the price of the sub
	 * @throws RemoteException - RMI errors
	 * @throws SQLException - Database errors
	 */
	public double getPrice(int size, String type) throws RemoteException, SQLException
	{
		/*
		 * Setting up the query, eliminates sql injection.
		 */
		final String query = "SELECT cost? FROM SandwichType WHERE name = ?";
		final PreparedStatement ps = this.conn.prepareStatement(query);
		ps.setInt(1, size);
		ps.setString(2, type);
		final ResultSet result = ps.executeQuery();
		
		/*
		 * Must get one back
		 */
		if(result.next()){
			final double price = result.getFloat(1);
			return price;
		}
		
		/*
		 * Failure in getting price
		 */
		throw new SQLException();	
	}
	
	/**
	 * Returns the id number of the specific sandwich
	 * @param name is the sandwich type
	 * @return the id of the sandwich
	 * @throws SQLException
	 */
	public int getTypeId(String name) throws RemoteException, SQLException
	{
		final String query = "SELECT order_id FROM SandwichType WHERE name = ?";
		final PreparedStatement ps = this.conn.prepareStatement(query);
		ps.setString(1, name);
		final ResultSet result = ps.executeQuery();
		
		/*
		 * Must get one back
		 */
		if(result.next()){
			final int id = result.getInt(1);
			return id;
		}

		/*
		 * Failure in getting price
		 */
		throw new SQLException();
	}
	
	/**
	 * Adds the type id, size and Tran
	 * @param typeID is the Id number of the sandwich
	 * @param size is the size of the sandwich
	 * @param transID is the Order id the sandwich is part of
	 * @throws RemoteException - RMI error
	 * @throws SQLException - Database error
	 */
	public void addTransItem(int typeID, int size, int transID) throws RemoteException, SQLException 
	{
		final String query = "INSERT INTO TransItems VALUES ( ? , ? , ? )";
		
		/*
		 * Setting up the query, eliminates sql injection.
		 */
		final PreparedStatement ps = this.conn.prepareStatement(query);
		ps.setInt(1, transID);
		ps.setInt(2, typeID);
		ps.setInt(3, size);
		ps.executeUpdate();
	}
	
	/**
	 * Adds the transaction to the database
	 * @param cost is the price of that specific order
	 * @throws RemoteException - RMI error
	 * @throws SQLException - Database error
	 */
	public void addTransaction(double cost) throws RemoteException, SQLException 
	{
		final String query = "INSERT INTO Transactions (totalCost, activity) VALUES ( ? , NOW())";
		
		/*
		 * Setting up the query, eliminates sql injection.
		 */
		final PreparedStatement ps = this.conn.prepareStatement(query);
		ps.setDouble(1, cost);
		ps.executeUpdate();
	}
	
	/**
	 * Gets the Id number of the transaction
	 * @return the id number
	 * @throws RemoteException - RMI error
	 * @throws SQLException - Database error
	 */
	public int getTransId() throws RemoteException, SQLException
	{
		final String query = "SELECT MAX(trans_id) FROM Transactions";
		final PreparedStatement ps = this.conn.prepareStatement(query);
		final ResultSet result = ps.executeQuery();
		
		/*
		 * Must get one back
		 */
		if(result.next()){
			final int id = result.getInt(1);
			return id;
		}

		/*
		 * Failure in getting price
		 */
		throw new SQLException();
	}

	public databaseConnect()
	{
		this.getConnection();
	}
	public void getConnection()
	{
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url,user,password);

		}
		catch(Exception e){
			System.out.println(e.toString()); 
		}
	}
	public void closeConnection() 
	{
		try{
			conn.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	public void Debug(){
		try{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url,user,password);
			String sql="SELECT * FROM test ORDER BY column1 DESC;";
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			if (resultSet.next()){  
				System.out.println(resultSet.getMetaData().getColumnLabel(1));
				do{  
					System.out.println(resultSet.getString(1));
				} while (resultSet.next());  
			} else { 
			}
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}

	}
	public static void main(String args[])
	{
		databaseConnect test = new databaseConnect();
		test.getConnection();
		test.Debug();
		test.closeConnection();

	}
}
