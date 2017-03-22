package program;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database
{
	/*
	 * this class is to only be used once at the start
	 * Use DatabaseConneciton when accessing the database
	 */
	public Database(String filename)
	{
		createNewDatabase(filename);
	}
	/*
	 * connect to the database
	 */
	public void createNewDatabase(String filename)
	{
		/*
		 * sets the url and name of the database
		 * if the database doesn't exist one will be created
		 */
		String url = "jdbc:sqlite:db/"+filename;
		try(Connection connect = DriverManager.getConnection(url))
		{
			/*
			 * Checks connection to the database throws exception if unable to connect
			 */
			if(connect != null)
			{
				DatabaseMetaData meta = connect.getMetaData();
                System.out.println("The driver is " + meta.getDriverName());
                System.out.println("New database has been created."); 
			}
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
	}
	
	/*
	 * Function to create tables
	 */
	public void createTable(String filename)
	{
		String url = "jdbc:sqlite:db/"+filename;
		/*
		 * account type boolean 1 for business owner 0 for user
		 * 
		 * creating tables for users and user details to be remembered later
		 */
		String queryUser = "CREATE TABLE IF NOT EXISTS users ("
						+"userID integer PRIMARY KEY AUTOINCREMENT,"
						+"username text NOT NULL,"
						+"password text NOT NULL,"
						+"accountType integer NOT NULL);";
		String queryUserDetails = "CREATE TABLE IF NOT EXISTS clientdetails ("
						+"id integer NOT NULL,"
						+"username text NOT NULL,"
						+"Address text NOT NULL,"
						+"Phone number boolean NOT NULL,"
						+ "FOREGIN KEY(id) REFERNECES users(userID));";
		String queryEmployees = "CREATE TABLE IF NOT EXISTS EMPLOYEES ("
				+"employeeID integer PRIMARY KEY AUTOINCREMENT,"
				+"name VARCHAR(40) NOT NULL,"
				+"payRate DOUBLE NOT NULL);";
				//+"PRIMARY KEY(employeeID));";

		String queryEmployeesWorkingTimes = "CREATE TABLE IF NOT EXISTS EMPLOYEES_WORKING_TIMES ("
							+"employeeID integer NOT NULL,"
							+"date VARCHAR(12) NOT NULL,"
							+"startTime DOUBLE NOT NULL,"
							+"endTime DOUBLE NOT NULL,"
							+"FOREIGN KEY(employeeID) REFERENCES employees(employeeID));";

		
		
		/*
		 * Attempting to connect to the database so tables can be created
		 */
		try(Connection connect = DriverManager.getConnection(url); Statement smt = connect.createStatement())
		{
			//Creating Table 'USERS'
			smt.executeUpdate(queryUser);
			System.out.println("Table 'Users' added");
			
			//Creating Table 'USERS_DETAILS'
			//smt.executeUpdate(queryUserDetails);
			//System.out.println("Table 'User Details added");
			
			//Creating Table 'EMPLOYEES'
			smt.executeUpdate(queryEmployees);
			System.out.println("Table 'EMPLOYEES' added");
			
			//Creating Table 'EMPLOYEES_WORKING_TIMES'
			smt.executeUpdate(queryEmployeesWorkingTimes);
			System.out.println("Table 'EMPLOYEES_WORKING_TIMES' added");
		}
		catch(SQLException sqle)
		{
			System.out.println("Adding Table: "+sqle.getMessage());
		}
	}
	/*
	 * used to add testing data at the start
	 */
	public void addData(String filename)
	{
		/*
		 * Attempting to connect to the database so tables can be created
		 */
		@SuppressWarnings("unused")
		Connection connect = null;
		String url = "jdbc:sqlite:db/"+filename;
		try
		{
			connect = DriverManager.getConnection(url);
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
	}
	
	/*
	 * Test Functions
	 */
	
	public void createTestTables(String filename)
	{
		String url = "jdbc:sqlite:db/"+filename;
		/*
		 * account type boolean 1 for business owner 0 for user
		 * 
		 * creating tables for users and user details to be remembered later
		 */
		String queryUser = "CREATE TABLE IF NOT EXISTS users ("
						+"userID integer PRIMARY KEY AUTOINCREMENT,"
						+"username text NOT NULL,"
						+"password text NOT NULL,"
						+"accountType integer NOT NULL);";
		String queryUserDetails = "CREATE TABLE IF NOT EXISTS clientdetails ("
						+"id integer NOT NULL,"
						+"username text NOT NULL,"
						+"Address text NOT NULL,"
						+"Phone number boolean NOT NULL,"
						+ "FOREGIN KEY(id) REFERNECES users(userID));";
		String queryEmployees = "CREATE TABLE IF NOT EXISTS EMPLOYEES ("
				+"employeeID integer PRIMARY KEY AUTOINCREMENT,"
				+"name VARCHAR(40) NOT NULL,"
				+"payRate integer NOT NULL);";

		String queryEmployeesWorkingTimes = "CREATE TABLE IF NOT EXISTS EMPLOYEES_WORKING_TIMES ("
							+"employeeID INT NOT NULL,"
							+"date VARCHAR(12) NOT NULL,"
							+"startTime VARCHAR(10) NOT NULL,"
							+"endTime VARCHAR(10) NOT NULL,"
							+"FOREIGN KEY(employeeID) REFERENCES employees(employeeID));";

		
		
		/*
		 * Attempting to connect to the database so tables can be created
		 */
		try(Connection connect = DriverManager.getConnection(url); Statement smt = connect.createStatement())
		{
			//Creating Table 'USERS'
			smt.executeUpdate(queryUser);
			System.out.println("Table 'Users' added");
			
			//Creating Table 'USERS_DETAILS'
			smt.executeUpdate(queryUserDetails);
			System.out.println("Table 'User Details added");
			
			//Creating Table 'EMPLOYEES'
			smt.executeUpdate(queryEmployees);
			System.out.println("Table 'EMPLOYEES' added");
			
			//Creating Table 'EMPLOYEES_WORKING_TIMES'
			smt.executeUpdate(queryEmployeesWorkingTimes);
			System.out.println("Table 'EMPLOYEES_WORKING_TIMES' added");
		}
		catch(SQLException sqle)
		{
			System.out.println("Adding Table: "+sqle.getMessage());
		}
	}
}
