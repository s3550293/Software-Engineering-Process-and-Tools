package program;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
		log.setLevel(Level.DEBUG);
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
		String queryBusiness = "CREATE TABLE IF NOT EXISTS BUSINESS (" 
				+"businessID integer PRIMARY KEY,"
				+"businessName VARCHAR(40));";
		String queryUser = "CREATE TABLE IF NOT EXISTS USERS (" + 
		 "userID integer PRIMARY KEY AUTOINCREMENT,"
				+ "username VARCHAR(30) NOT NULL," 
				+ "password VARCHAR(30) NOT NULL,"
				+ "accountType integer NOT NULL,"
				+ "businessID integer,"
				+ "FOREIGN KEY(businessID) REFERENCES BUSINESS(businessID));";
		String queryUserDetails = "CREATE TABLE IF NOT EXISTS CLIENTDETAILS (" 
				+ "id integer NOT NULL,"
				+ "FName VARCHAR(30) NOT NULL,"
				+ "LName VARCHAR(30) NOT NULL," 
				+ "Email VARCHAR(30) NOT NULL," 
				+ "Phone VARCHAR(30) NOT NULL,"
				+ "DOB VARCHAR(30) NOT NULL,"
				+ "Gender VARCHAR(30) NOT NULL,"
				+ "businessID integer,"
				+ "FOREIGN KEY(businessID) REFERENCES BUSINESS(businessID),"
				+ "FOREIGN KEY(id) REFERENCES USERS(userID));";
		String queryEmployees = "CREATE TABLE IF NOT EXISTS EMPLOYEES ("
				+ "employeeID integer PRIMARY KEY AUTOINCREMENT," 
				+ "name VARCHAR(40) NOT NULL,"
				+ "payRate DOUBLE NOT NULL,"
				+ "businessID integer,"
				+ "FOREIGN KEY(businessID) REFERENCES BUSINESS(businessID));";
		String queryEmployeesWorkingTimes = "CREATE TABLE IF NOT EXISTS EMPLOYEES_WORKING_TIMES ("
				+ "id integer PRIMARY KEY AUTOINCREMENT," 
				+ "employeeID integer NOT NULL,"
				+ "dayOfWeek integer NOT NULL," 
				+ "startTime VARCHAR(20) NOT NULL," 
				+ "endTime VARCHAR(20) NOT NULL,"
				+ "businessID integer,"
				+ "FOREIGN KEY(employeeID) REFERENCES EMPLOYEES(employeeID),"
				+ "FOREIGN KEY(businessID) REFERENCES BUSINESS(businessID));";
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
				+ "weekendEnd VARCHAR(20),"
				+ "color integer,"
				+ "image blob,"
				+ "FOREIGN KEY(ID) REFERENCES USERS(userID),"
				+ "FOREIGN KEY(color) REFERENCES COLOR(ID));";
		String queryColor = "CREATE TABLE IF NOT EXISTS COLOR ("
				+ "ID integer PRIMARY KEY AUTOINCREMENT,"
				+ "base1 varchar(10),"
				+ "base2 varchar(10),"
				+ "base3 varchar(10),"
				+ "base4 varchar(10));"; 
		
		String queryUserRoot = "INSERT INTO USERS(username, password, accountType,businessID) " + "VALUES('root','Monday10!',2,0)";
		
		String colorset1 = "INSERT INTO COLOR(base1, base2, base3, base4) "+"VALUES('#446CB3','black','White','#EDEDED')";
		String colorset2 = "INSERT INTO COLOR(base1, base2, base3, base4) "+"VALUES('#800080','black','White','#EDEDED')";
		String colorset3 = "INSERT INTO COLOR(base1, base2, base3, base4) "+"VALUES('#008B45','black','White','#EDEDED')";
		String colorset4 = "INSERT INTO COLOR(base1, base2, base3, base4) "+"VALUES('#FF7F24','black','White','#EDEDED')";
		/*
		 * Attempting to connect to the database so tables can be created
		 */
		try (Connection connect = DriverManager.getConnection(url); Statement smt = connect.createStatement())
		{
			//Creating Table 'BUSINESS'
			smt.executeUpdate(queryBusiness);
			log.debug("Table 'BUSINESS' added");
			
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
			
			
			smt.executeUpdate(queryColor);
			log.debug("Table 'Color' added");
			
			smt.executeUpdate(queryUserRoot);
			log.debug("root user added");
			
			smt.executeUpdate(colorset1);
			log.debug("Color set 1 added");
			
			smt.executeUpdate(colorset2);
			log.debug("Color set 2 added");
			
			smt.executeUpdate(colorset3);
			log.debug("Color set 3 added");
			
			smt.executeUpdate(colorset4);
			log.debug("Color set 4 added");
			
			
		} catch (SQLException sqle)
		{
			// System.out.println("ERROR: couldn't add table:
			// "+sqle.getMessage());
			log.warn("ERROR: couldn't add table: " + sqle.getMessage());
		}
	}
	
	
	
	
}
