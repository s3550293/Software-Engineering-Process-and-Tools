package program;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Database
{
	private static Logger log = Logger.getLogger(Database.class);

	/*
	 * this class is to only be used once at the start Use DatabaseConneciton
	 * when accessing the database
	 */
	public Database(String filename)
	{
		log.setLevel(Level.WARN);
		createNewDatabase(filename);
	}

	/*
	 * connect to the database
	 */
	public void createNewDatabase(String filename)
	{
		/*
		 * sets the url and name of the database if the database doesn't exist
		 * one will be created
		 */
		String url = "jdbc:sqlite:db/" + filename;
		try (Connection connect = DriverManager.getConnection(url))
		{
			/*
			 * Checks connection to the database throws exception if unable to
			 * connect
			 */
			if (connect != null)
			{
				DatabaseMetaData meta = connect.getMetaData();
				log.info("The driver is " + meta.getDriverName()+"\n");
				log.info("New database has been created.\n");
			}
		} catch (SQLException sqle)
		{
			log.info(sqle.getMessage()+"\n");
		}
	}

	/*
	 * Function to create tables
	 */
	public void createTable(String filename)
	{
		String url = "jdbc:sqlite:db/" + filename;
		/*
		 * account type boolean 1 for business owner 0 for user
		 * 
		 * creating tables for users and user details to be remembered later
		 */
		String queryUser = "CREATE TABLE IF NOT EXISTS USERS (" + 
		 "userID integer PRIMARY KEY AUTOINCREMENT,"
				+ "username VARCHAR(30) NOT NULL," 
				+ "password VARCHAR(30) NOT NULL,"
				+ "accountType integer NOT NULL,"
				+ "businessID integer,"
				+ "FOREIGN KEY(businessID) REFERENCES business(businessID));";
		String queryUserDetails = "CREATE TABLE IF NOT EXISTS CLIENTDETAILS (" 
				+ "id integer NOT NULL,"
				+ "FName VARCHAR(30) NOT NULL,"
				+ "LName VARCHAR(30) NOT NULL," 
				+ "Email VARCHAR(30) NOT NULL," 
				+ "Phone VARCHAR(30) NOT NULL,"
				+ "DOB VARCHAR(30) NOT NULL,"
				+ "Gender VARCHAR(30) NOT NULL,"
				+ "FOREIGN KEY(id) REFERENCES USERS(userID),"
				+ "businessID integer,"
				+ "FOREIGN KEY(businessID) REFERENCES business(businessID));";
		String queryEmployees = "CREATE TABLE IF NOT EXISTS EMPLOYEES ("
				+ "employeeID integer PRIMARY KEY AUTOINCREMENT," 
				+ "name VARCHAR(40) NOT NULL,"
				+ "payRate DOUBLE NOT NULL,"
				+ "businessID integer,"
				+ "FOREIGN KEY(businessID) REFERENCES business(businessID));";
		String queryEmployeesWorkingTimes = "CREATE TABLE IF NOT EXISTS EMPLOYEES_WORKING_TIMES ("
				+ "id integer PRIMARY KEY AUTOINCREMENT," 
				+ "employeeID integer NOT NULL,"
				+ "dayOfWeek integer NOT NULL," 
				+ "startTime VARCHAR(20) NOT NULL," 
				+ "endTime VARCHAR(20) NOT NULL,"
				+ "businessID integer,"
				+ "FOREIGN KEY(employeeID) REFERENCES EMPLOYEES(employeeID),"
				+ "FOREIGN KEY(businessID) REFERENCES business(businessID));";
		String queryBookings = "CREATE TABLE IF NOT EXISTS BOOKINGS (" 
				+ "id integer PRIMARY KEY AUTOINCREMENT,"
				+ "userID integer NOT NULL,"
				+ "employeeID integer NOT NULL," 
				+ "date VARCHAR(20) NOT NULL," 
				+ "startTime VARCHAR(20) NOT NULL,"
				+ "endTime VARCHAR(20) NOT NULL," 
				+ "serviceID integer NOT NULL," 
				+ "status VARCHAR(20),"
				+ "businessID integer,"
				+ "FOREIGN KEY(businessID) REFERENCES business(businessID),"
				+ "FOREIGN KEY (userID) REFERENCES USERS(userID),"
				+ "FOREIGN KEY (employeeID) REFERENCES EMPLOYEES(employeeID),"
				+ "FOREIGN KEY (serviceID) REFERENCES SERVICES(id));";
		String queryServices = "CREATE TABLE IF NOT EXISTS SERVICES (" 
				+ "id integer PRIMARY KEY AUTOINCREMENT,"
				+ "service VARCHAR(40) NOT NULL,"
				+ "length integer NOT NULL,"
				+ "cost double NOT NULL,"
				+ "businessID integer,"
				+ "FOREIGN KEY(businessID) REFERENCES business(businessID));";
		String queryBookingServiceLink = "CREATE TABLE IF NOT EXISTS BSLINK (" 
				+ "bookingID integer NOT NULL,"
				+ "serviceID integer NOT NULL," 
				+ "FOREIGN KEY(bookingID) REFERENCES BOOKINGS(id),"
				+ "FOREIGN KEY(serviceID) REFERENCES SERVICES(id));";
		String queryBusinessOwner = "CREATE TABLE IF NOT EXISTS BUSINESS_OWNER (" 
				+ "ID integer NOT NULL,"
				+ "fName VARCHAR(30),"
				+ "lName VARCHAR(30),"
				+ "Phone VARCHAR(30),"
				+ "address VARCHAR(30),"
				+ "weekdayStart VARCHAR(20),"
				+ "weekdayEnd VARCHAR(20)," 
				+ "weekendStart VARCHAR(20),"
				+ "weekendEnd VARCHAR(20)"
				+ "FOREIGN KEY(ID) REFERENCES USERS(userID));";
		String queryBusiness = "CREATE TABLE IF NOT EXISTS BUSINESS (" 
				+"businessID interger PRIMARY KEY AUTOINCREMENT,"
				+"businessName VARCHAR(40));";
		/*
		 * Attempting to connect to the database so tables can be created
		 */
		try (Connection connect = DriverManager.getConnection(url); Statement smt = connect.createStatement())
		{
			//Creating Table 'USERS'
			smt.executeUpdate(queryUser);
			log.debug("Table 'Users' added");
			
			//Creating Table 'USERS_DETAILS'
			smt.executeUpdate(queryUserDetails);
			log.debug("Table 'User Details' added");
			
			//Creating Table 'EMPLOYEES'
			smt.executeUpdate(queryEmployees);
			log.debug("Table 'EMPLOYEES' added");
			
			//Creating Table 'EMPLOYEES_WORKING_TIMES'
			smt.executeUpdate(queryEmployeesWorkingTimes);
			log.debug("Table 'EMPLOYEES_WORKING_TIMES' added");
			
			//Creating Table 'BOOKINGS'
			smt.executeUpdate(queryBookings);
			log.debug("Table 'BOOKINGS' added");
			
			smt.executeUpdate(queryServices);
			log.debug("Table 'SERVICES' added");
			
			smt.executeUpdate(queryBookingServiceLink);
			log.debug("Table 'BS LINK' added");
			
			//Creating Table 'BUSINESS OWNER'
			smt.executeUpdate(queryBusinessOwner);
			log.debug("Table 'BUSINESS OWNER' added");
			
			//Creating Table 'BUSINESS'
			smt.executeUpdate(queryBusiness);
			log.debug("Table 'BUSINESS' added");
		} catch (SQLException sqle)
		{
			// System.out.println("ERROR: couldn't add table:
			// "+sqle.getMessage());
			log.warn("ERROR: couldn't add table: " + sqle.getMessage());
		}
	}

	/**
	 * used to add testing data at the start
	 */
	public void addData(String filename)
	{
		/*
		 * Attempting to connect to the database so tables can be created
		 */
		@SuppressWarnings("unused")
		Connection connect = null;
		String url = "jdbc:sqlite:db/" + filename;
		try
		{
			connect = DriverManager.getConnection(url);
		} catch (SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
	}

	/**
	 * Test Tables for Test Functions
	 */
	public void createTestTables(String filename)
	{
		String url = "jdbc:sqlite:db/" + filename;
		/*
		 * account type boolean 1 for business owner 0 for user
		 * 
		 * creating tables for users and user details to be remembered later
		 */
		String queryUser = "CREATE TABLE IF NOT EXISTS USERS (" + 
				 "userID integer PRIMARY KEY AUTOINCREMENT,"
						+ "username VARCHAR(30) NOT NULL," 
						+ "password VARCHAR(30) NOT NULL,"
						+ "accountType integer NOT NULL,"
						+ "businessID integer,"
						+ "FOREIGN KEY(businessID) REFERENCES business(businessID));";
				String queryUserDetails = "CREATE TABLE IF NOT EXISTS CLIENTDETAILS (" 
						+ "id integer NOT NULL,"
						+ "FName VARCHAR(30) NOT NULL,"
						+ "LName VARCHAR(30) NOT NULL," 
						+ "Email VARCHAR(30) NOT NULL," 
						+ "Phone VARCHAR(30) NOT NULL,"
						+ "DOB VARCHAR(30) NOT NULL,"
						+ "Gender VARCHAR(30) NOT NULL,"
						+ "FOREIGN KEY(id) REFERENCES USERS(userID),"
						+ "businessID integer,"
						+ "FOREIGN KEY(businessID) REFERENCES business(businessID));";
				String queryEmployees = "CREATE TABLE IF NOT EXISTS EMPLOYEES ("
						+ "employeeID integer PRIMARY KEY AUTOINCREMENT," 
						+ "name VARCHAR(40) NOT NULL,"
						+ "payRate DOUBLE NOT NULL,"
						+ "businessID integer,"
						+ "FOREIGN KEY(businessID) REFERENCES business(businessID));";
				String queryEmployeesWorkingTimes = "CREATE TABLE IF NOT EXISTS EMPLOYEES_WORKING_TIMES ("
						+ "id integer PRIMARY KEY AUTOINCREMENT," 
						+ "employeeID integer NOT NULL,"
						+ "dayOfWeek integer NOT NULL," 
						+ "startTime VARCHAR(20) NOT NULL," 
						+ "endTime VARCHAR(20) NOT NULL,"
						+ "businessID integer,"
						+ "FOREIGN KEY(employeeID) REFERENCES EMPLOYEES(employeeID),"
						+ "FOREIGN KEY(businessID) REFERENCES business(businessID));";
				String queryBookings = "CREATE TABLE IF NOT EXISTS BOOKINGS (" 
						+ "id integer PRIMARY KEY AUTOINCREMENT,"
						+ "userID integer NOT NULL,"
						+ "employeeID integer NOT NULL," 
						+ "date VARCHAR(20) NOT NULL," 
						+ "startTime VARCHAR(20) NOT NULL,"
						+ "endTime VARCHAR(20) NOT NULL," 
						+ "serviceID integer NOT NULL," 
						+ "status VARCHAR(20),"
						+ "businessID integer,"
						+ "FOREIGN KEY(businessID) REFERENCES business(businessID),"
						+ "FOREIGN KEY (userID) REFERENCES USERS(userID),"
						+ "FOREIGN KEY (employeeID) REFERENCES EMPLOYEES(employeeID),"
						+ "FOREIGN KEY (serviceID) REFERENCES SERVICES(id));";
				String queryServices = "CREATE TABLE IF NOT EXISTS SERVICES (" 
						+ "id integer PRIMARY KEY AUTOINCREMENT,"
						+ "service VARCHAR(40) NOT NULL,"
						+ "length integer NOT NULL,"
						+ "cost double NOT NULL,"
						+ "businessID integer,"
						+ "FOREIGN KEY(businessID) REFERENCES business(businessID));";
				String queryBookingServiceLink = "CREATE TABLE IF NOT EXISTS BSLINK (" 
						+ "bookingID integer NOT NULL,"
						+ "serviceID integer NOT NULL," 
						+ "FOREIGN KEY(bookingID) REFERENCES BOOKINGS(id),"
						+ "FOREIGN KEY(serviceID) REFERENCES SERVICES(id));";
				String queryBusinessOwner = "CREATE TABLE IF NOT EXISTS BUSINESS (" 
						+ "businessID integer PRIMARY KEY AUTOINCREMENT,"
						+ "fName VARCHAR(30),"
						+ "lName VARCHAR(30),"
						+ "Phone VARCHAR(30),"
						+ "address VARCHAR(30),"
						+ "weekdayStart VARCHAR(20),"
						+ "weekdayEnd VARCHAR(20)," 
						+ "weekendStart VARCHAR(20),"
						+ "weekendEnd VARCHAR(20),"
						+ "FOREIGN KEY(ID) REFERENCES USERS(userID));";
				String queryBusiness = "CREATE TABLE IF NOT EXISTS BUSINESS (" 
						+"businessID interger PRIMARY KEY AUTOINCREMENT,"
						+"businessName VARCHAR(40));";
		/*
		 * Attempting to connect to the database so tables can be created
		 */
		try (Connection connect = DriverManager.getConnection(url); Statement smt = connect.createStatement())
		{
			//Creating Table 'USERS'
			smt.executeUpdate(queryUser);
			log.debug("Table 'Users' added");
			
			//Creating Table 'USERS_DETAILS'
			smt.executeUpdate(queryUserDetails);
			log.debug("Table 'User Details' added");
			
			//Creating Table 'EMPLOYEES'
			smt.executeUpdate(queryEmployees);
			log.debug("Table 'EMPLOYEES' added");
			
			//Creating Table 'EMPLOYEES_WORKING_TIMES'
			smt.executeUpdate(queryEmployeesWorkingTimes);
			log.debug("Table 'EMPLOYEES_WORKING_TIMES' added");
			
			//Creating Table 'BOOKINGS'
			smt.executeUpdate(queryBookings);
			log.debug("Table 'BOOKINGS' added");
			
			smt.executeUpdate(queryServices);
			log.debug("Table 'SERVICES' added");
			
			smt.executeUpdate(queryBookingServiceLink);
			log.debug("Table 'BS LINK' added");
			
			//Creating Table 'BUSINESS OWNER'
			smt.executeUpdate(queryBusinessOwner);
			log.debug("Table 'BUSINESS OWNER' added");
			
			//Creating Table 'BUSINESS'
			smt.executeUpdate(queryBusiness);
			log.debug("Table 'BUSINESS' added");
		} catch (SQLException sqle)
		{
			// System.out.println("ERROR: couldn't add table:
			// "+sqle.getMessage());
			log.warn("ERROR: couldn't add table: " + sqle.getMessage());
		}
	}
}
