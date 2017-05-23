package program;

import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/*
 * follow this link to find out how to add tables and access tables
 * http://www.sqlitetutorial.net/sqlite-java/
 * this is where I will leave my sanity
 */


/**
 * Consists of functions that add and extract information to and from the database
 * @author Luke Mason, Joseph Garner
 *
 */

public class DatabaseConnection
{
	private static Logger log = Logger.getLogger(DatabaseConnection.class);
	private Controller controller = new Controller();
	public DatabaseConnection(){log.setLevel(Level.DEBUG);}
	private Connection connect()
	{
		/*
		 * creates a connection to the database to be used multiple times in the class
		 */
		String url = "jdbc:sqlite:db/company.db";
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(url);
        } catch (SQLException sqle) {
            //System.out.println(sqle.getMessage());
            log.warn(sqle.getMessage());
        }
        return connect;
	}
	
	/**
	 * Adds User to database
	 * @param username
	 * @param password
	 * @param accountType
	 */
	public void addUser(String username, String password, int accountType, int businessID)
	{
		log.info("IN addUser\n");
		/*
		 * account type boolean 1 for business owner, 0 for user
		 */
		String query = "INSERT INTO USERS(username, password, accountType, businessID) " + "VALUES('"+username+"','"+password+"',"+accountType+","+businessID+")";
		executeQuery(query, "User Added\n");
		log.info("OUT addUser\n");
	}
	
	/**
	 * Adds User to database
	 * @param username
	 * @param password
	 * @param accountType
	 */
	public void addUserDetails(int id, String fname, String lname, String email, String phone, String dob, String gender)
	{
		log.info("IN addUserDetails\n");
		/*
		 * account type boolean 1 for business owner 0 for user
		 */
		User user = getUser(id);
		String query = "INSERT INTO CLIENTDETAILS(id, FName, LName, Email, Phone, DOB, Gender, businessID) " + "VALUES("+id+",'"+fname+"','"+lname+"','"+email+"','"+phone+"','"+dob+"','"+gender+"',"+user.getBusinessID()+")";
		executeQuery(query, "User Added\n");
		log.info("OUT addUserDetails\n");
	}
	
	/**
	 * Adds User to database
	 * @param username
	 * @param password
	 * @param accountType
	 */
	public void addUserDetails(int id, String fname, String lname, String email, String phone, String dob, String gender, int businessID)
	{
		log.info("IN addUserDetails\n");
		/*
		 * account type boolean 1 for business owner 0 for user
		 */
		User user = getUser(id);
		String query = "INSERT INTO CLIENTDETAILS(id, FName, LName, Email, Phone, DOB, Gender, businessID) " + "VALUES("+id+",'"+fname+"','"+lname+"','"+email+"','"+phone+"','"+dob+"','"+gender+"',"+businessID+")";
		executeQuery(query, "User Added\n");
		log.info("OUT addUserDetails\n");
	}
	
	/**
	 * Gets where the user name keyword matches another users name
	 * @param username
	 * @return User Object
	 */
	public User getUser(int id)
	{
		log.info("IN getUser\n");
		int _id = 0;
		String _username = "null";
		String _password = "null";
		int _accountType = 0;
		int businessID = -1;
		String query = "SELECT * FROM users WHERE userID = ?";
		//Creates a null user to return, this can be used to validate user at login
		User databaseUser = null;
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setInt(1,id);
			ResultSet output = inject.executeQuery();
			while (output.next()){
				_id = output.getInt(1);
				_username = output.getString(2);
				_password = output.getString(3);
				_accountType = output.getInt(4);
				businessID = output.getInt(5);
			}
			databaseUser = new User(_id ,_username, _password, _accountType, businessID);
			output.close();
		}
		catch(SQLException sqle)
		{
			//System.out.println("Getting User: "+sqle.getMessage());
			log.warn(sqle.getMessage());
		}
		log.info("OUT getUser\n");
		return databaseUser;
	}
	
	/**
	 * Gets where the user name keyword matches another users name
	 * @param username
	 * @return User Object
	 */
	public User getUser(int BID, int val)
	{
		log.info("IN getUser\n");
		int _id = 0;
		String _username = "null";
		String _password = "null";
		int _accountType = 0;
		int businessID = -1;
		String query = "SELECT * FROM users WHERE businessID = ? AND accountType = ?";
		//Creates a null user to return, this can be used to validate user at login
		User databaseUser = null;
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setInt(1,BID);
			inject.setInt(2,val);
			ResultSet output = inject.executeQuery();
			while (output.next()){
				_id = output.getInt(1);
				_username = output.getString(2);
				_password = output.getString(3);
				_accountType = output.getInt(4);
				businessID = output.getInt(5);
			}
			databaseUser = new User(_id ,_username, _password, _accountType, businessID);
			output.close();
		}
		catch(SQLException sqle)
		{
			//System.out.println("Getting User: "+sqle.getMessage());
			log.warn(sqle.getMessage());
		}
		log.info("OUT getUser\n");
		return databaseUser;
	}
	
	/**
	 * Gets where the user name keyword matches another users name
	 * @param username
	 * @return User Object
	 */
	public User getUser(String username, int businessID)
	{
		log.info("IN getUser\n");
		
		String query = "SELECT * FROM users WHERE username like '"+username+"' AND businessID = "+businessID;
		//Creates a null user to return, this can be used to validate user at login
		User databaseUser = null;
		databaseUser= userlogin(query);
		log.info("OUT getUser\n");
		return databaseUser;
	}
	
	private User  userlogin(String query){
		int _id = 0;
		String _username = "null";
		String _password = "null";
		int _accountType = 0;
		int _businessID = 0;
		User databaseUser = null;
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			ResultSet output = inject.executeQuery();
			while (output.next()){
				_id = output.getInt(1);
				_username = output.getString(2);
				_password = output.getString(3);
				_accountType = output.getInt(4);
				_businessID = output.getInt(5);
				
			}
			databaseUser = new User(_id ,_username, _password, _accountType, _businessID);
			output.close();
		}
		catch(SQLException sqle)
		{
			//System.out.println("Getting User: "+sqle.getMessage());
			log.warn(sqle.getMessage());
		}
		return databaseUser;
	}
	
	/**
	 * Gets where the user name keyword matches another users name
	 * @param id
	 * @return User Object
	 */
	public User getCustomer(int id)
	{
		log.info("IN getCustomer\n");
		int _id = 0;
		String _FName = "null";
		String _LName = "null";
		String _Email = "null";
		String _Phone = "null";
		String _DOB = "null";
		String _Gender = "null";
		String query = "SELECT * FROM CLIENTDETAILS WHERE id = ?";
		int businessID = -1;
		//Creates a null user to return, this can be used to validate user at login
		Customer databaseCustomer = null;
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setInt(1,id);
			ResultSet output = inject.executeQuery();
			while (output.next()){
				_id = output.getInt(1);
				_FName = output.getString(2);
				_LName = output.getString(3);
				_Email = output.getString(4);
				_Phone = output.getString(5);
				_DOB = output.getString(6);
				_Gender = output.getString(7);
				businessID = output.getInt(8);
			}
			databaseCustomer = new Customer(_id ,_FName, _LName, _Phone, _DOB, _Gender, _Email, businessID);
			output.close();
		}
		catch(SQLException sqle)
		{
			//System.out.println("Getting User: "+sqle.getMessage());
			log.warn(sqle.getMessage());
		}
		log.info("OUT getCustomer\n");
		return databaseCustomer;
	}
	/**
	 * Gets where the user name keyword matches another users name
	 * @param id
	 * @return User Object
	 */
	public ArrayList<Customer> getAllCustomer(int businessID)
	{
		log.info("IN getCustomer\n");
		ArrayList<Customer> customers = new ArrayList<Customer>();
		int _id = 0;
		String _FName = "null";
		String _LName = "null";
		String _Email = "null";
		String _Phone = "null";
		String _DOB = "null";
		String _Gender = "null";
		String query = "SELECT * FROM CLIENTDETAILS WHERE businessID = "+businessID+";";
		//Creates a null user to return, this can be used to validate user at login
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			ResultSet output = inject.executeQuery();
			while (output.next()){
				_id = output.getInt(1);
				_FName = output.getString(2);
				_LName = output.getString(3);
				_Email = output.getString(4);
				_Phone = output.getString(5);
				_DOB = output.getString(6);
				_Gender = output.getString(7);
				customers.add(new Customer(_id ,_FName, _LName, _Phone, _DOB, _Gender, _Email, businessID));
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			//System.out.println("Getting User: "+sqle.getMessage());
			log.warn(sqle.getMessage());
		}
		log.info("OUT getCustomer\n");
		return customers;
	}
	
	/**
	 * This function finds a selection of employees that matches the string name
	 * @param name
	 * @return ArrayList<Employee> Objects
	 */
	public ArrayList<Employee> getEmployees(String name, int businessID)
	{
		log.info("IN getEmployees\n");
		ArrayList<Employee> databaseEmployee = new ArrayList<Employee>();
		int id = 0;
		double payRate = 0;
		String query = "SELECT * FROM EMPLOYEES WHERE name like ? AND businessID = ?"; 

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setString(1,"%"+name+"%");
			inject.setInt(2,businessID);
			ResultSet output = inject.executeQuery();
			while (output.next()){
				id = output.getInt(1);
				name = output.getString(2);
				payRate = output.getDouble(3);
				databaseEmployee.add(new Employee(id ,name, payRate, businessID));
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			//System.out.println("Getting Employee: "+sqle.getMessage());
			log.warn(sqle.getMessage());
		}
		log.info("OUT getEmployees\n");
		return databaseEmployee;
	}

	/**
	 * Gets the employee with matching ID from database
	 * @param employeeID
	 * @return Employee Object
	 */
	public Employee getEmployee(int employeeID)
	{
		log.info("IN getEmployee\n");
		Employee databaseEmployee = null;
		int id = 0;
		String name = "";
		double payRate = 0;
		int businessID = -1;
		String query = "SELECT * FROM EMPLOYEES WHERE employeeID like ? "; 

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setInt(1,employeeID);
			ResultSet output = inject.executeQuery();
			while (output.next()){
				id = output.getInt(1);
				name = output.getString(2);
				payRate = output.getDouble(3);
				businessID = output.getInt(4);
			}
			databaseEmployee = new Employee(id ,name, payRate, businessID);
			output.close();
		}
		catch(SQLException sqle)
		{
			//System.out.println("Getting Employee: "+sqle.getMessage());
			log.warn(sqle.getMessage());
		}
		log.info("OUT getEmployee\n");
		return databaseEmployee;
	}
	
	/**
	 * Adds an employee to the database
	 * @param name
	 * @param payRate
	 */
	public void addEmployee(String name, double payRate, int businessID)
	{
		log.info("IN addEmployee\n");
		String query = "INSERT INTO EMPLOYEES(name, payRate, businessID) " + "VALUES ('" + name + "'," + payRate + "," + businessID +");";
		executeQuery(query, "Employee '"+name+"' Added\n");
		log.info("OUT addEmployee\n");
	}
	
	/**
	 * Adds ONE employee working time to the database
	 * @param empID
	 * @param date
	 * @param startTime
	 * @param endTime
	 */
	public void addEmployeeWorkingTime(int empID, int dayOfWeek, String startTime, String endTime, int businessID)
	{
		log.info("IN addEmployeeWorkingTimeToDatabase\n");
		String query = "INSERT INTO EMPLOYEES_WORKING_TIMES(employeeID, dayOfWeek, startTime, endTime, businessID) " + "VALUES ("+ empID +","+ dayOfWeek +",'"+ startTime +"','"+ endTime +"',"+businessID+");";
		executeQuery(query, "Work Time Added - EmpID " + empID+ " Day Of Week: "+dayOfWeek+" Start Time: "+startTime+" End Time: "+endTime+"\n");
		log.info("OUT addEmployeeWorkingTimeToDatabase\n");
	}
	
	/**
	 * Delete user from database
	 * @param userName
	 */
	public void deleteUser(String userName, int businessID)
	{
		log.info("IN deleteUser\n");
		String query = "DELETE FROM users WHERE username LIKE '" + userName + "' AND businessID = "+businessID+";";
		executeQuery(query, "User " + userName + " deleted\n");
		log.info("OUT deleteUser\n");
	}
	
	/**
	 * Gets the employee's working times from database
	 * @param employeeId
	 * @return ArrayList <EmployeeWorkingTime> Objects
	 */
	public ArrayList<EmployeeWorkingTime> getEmployeeWorkingTimes(int employeeId)
	{
		log.info("IN getEmployeeWorkingTimes\n");
		ArrayList<EmployeeWorkingTime> databaseWorkingTime = new ArrayList<EmployeeWorkingTime>();
		int id = 0;
		int empID = 0;
		int dayOfWeek = 0;
		Date startTime, endTime;
		int businessID = -1;
		String query = "SELECT * FROM EMPLOYEES_WORKING_TIMES WHERE employeeID = ?"; 

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setInt(1,employeeId);
			ResultSet output = inject.executeQuery();
			while (output.next())
			{
				id = output.getInt(1);
				empID = output.getInt(2);
				dayOfWeek = output.getInt(3);
				startTime = controller.strToTime(output.getString(4));
				endTime = controller.strToTime(output.getString(5));
				businessID = output.getInt(6);
				databaseWorkingTime.add(new EmployeeWorkingTime(id,empID,dayOfWeek,startTime,endTime, businessID));				
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			//System.out.println("Getting Working Time: "+sqle.getMessage());
			log.warn(sqle.getMessage());
		}
		log.info("OUT getEmployeeWorkingTimes\n");
		return databaseWorkingTime;
	}
	
	public boolean dropTable(String tableName)
	{
		log.info("IN dropTable\n");
		String query = "DROP TABLE IF EXISTS '"+ tableName +"' ";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			log.info("Table "+ tableName +"Added\n");
			log.info("OUT dropTable\n");
			return true;
		}
		catch(SQLException sqle)
		{
			//System.out.println(sqle.getMessage());
			log.warn(sqle.getMessage());
			log.info("OUT dropTable\n");
			return false;
		}
		
	}
	
	/**
	 * Drops table name from database
	 * @param username
	 * @return
	 */
	public boolean dropUser(String username, int businessID)
	{
		log.info("IN dropUser\n");
		String query = "DELETE FROM USERS WHERE username like '"+ username +"' AND businessID = ?";
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			inject.setInt(1, businessID);		
			//System.out.println("Table "+ tableName +"");
			log.info("User "+username+" Dropped\n");
			log.info("OUT dropUser\n");
			return true;
		}
		catch(SQLException sqle)
		{
			//System.out.println(sqle.getMessage());
			log.warn(sqle.getMessage());
			log.info("OUT dropUser\n");
			return false;
		}
	}
	/**
	 * Adds one booking to the database
	 * @param userId
	 * @param date
	 * @param startTime
	 * @param endTime
	 * @param description
	 */
	public void addBooking (int userId,int empID, String date, String startTime, String endTime, int service, String status, int businessID)
	{
		log.info("IN addBookingToDatabase\n");
		//bookingID is made in the database
		String query = "INSERT INTO BOOKINGS (userID,employeeID,date,startTime,endTime, serviceID,status, businessID)" + "VALUES(" + userId + ","+empID+",'" + date + "','" + startTime + "','" + endTime + "',"+service+",'" + status + "',"+businessID+");";
		executeQuery(query, "Booking Added - User ID: "+userId+" Date: "+date+" Start Time: "+startTime+" End Time: "+endTime+" ServiceNmb: "+service+" Status: "+status+"\n");
		log.info("OUT addBookingToDatabase\n");
	}
	
	/**
	 * Gets a booking from database
	 * @param cusID
	 * @return ArrayList <Booking> Objects
	 */
	public ArrayList<Booking> getBooking (int customerID)
	{
		log.info("IN getBooking\n");
		ArrayList<Booking> databaseBookingTime = new ArrayList<Booking>();
		int bookingID = 0;
		int cusID = 0;
		int empID = 0;
		Date date, startTime, endTime;
		String desc;
		int service;
		int bID;
		String query = "SELECT * FROM BOOKINGS WHERE userID = ? "; 

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setInt(1,customerID);
			ResultSet output = inject.executeQuery();
			while (output.next())
			{
				bookingID = output.getInt(1);
				cusID = output.getInt(2);
				empID = output.getInt(3);
				date = controller.strToDate(output.getString(4));
				startTime = controller.strToTime(output.getString(5));
				endTime = controller.strToTime(output.getString(6));
				service = output.getInt(7);
				desc=output.getString(8);
				bID = output.getInt(9);
				databaseBookingTime.add(new Booking(bookingID,cusID,empID,date,startTime,endTime,service,desc,bID));				
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.info("OUT getBooking\n");
		return databaseBookingTime;
	}
	
	/**
	 * Gets all booking object from database
	 * @param cusID
	 * @return ArrayList <Booking> Objects
	 */
	public ArrayList<Booking> getAllBooking (int businessID)
	{
		log.info("IN getAllBooking\n");
		ArrayList<Booking> databaseBookingTime = new ArrayList<Booking>();
		int bookingID = 0;
		int cusID = 0;
		int empID = 0;
		Date date, startTime, endTime;
		String desc;
		int service;
		int bID;
		String query = "SELECT * FROM BOOKINGS WHERE businessID = ?"; 

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			//inject.setInt(1,customerID);
			inject.setInt(1, businessID);
			ResultSet output = inject.executeQuery();
			while (output.next())
			{
				bookingID = output.getInt(1);
				cusID = output.getInt(2);
				empID = output.getInt(3);
				date = controller.strToDate(output.getString(4));
				startTime = controller.strToTime(output.getString(5));
				endTime = controller.strToTime(output.getString(6));
				service = output.getInt(7);
				desc=output.getString(8);
				bID = output.getInt(9);
				databaseBookingTime.add(new Booking(bookingID,cusID,empID,date,startTime,endTime,service,desc,bID));				
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.info("OUT getAllBooking\n");
		return databaseBookingTime;
	}
	
	/**
	 * Gets one booking from database
	 * @param bookID
	 * @return one <Booking> Object
	 */
	public Booking getOneBooking(int bookID)
	{
		log.info("IN getOneBooking\n");
		Booking getBooking = null;
		int bookingID = 0;
		int cusID = 0;
		int empID = 0;
		Date date, startTime, endTime;
		int service;
		String desc;
		int bID;
		String query = "SELECT * FROM BOOKINGS WHERE id = ?"; 

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setInt(1, bookID);
			ResultSet output = inject.executeQuery();
			while (output.next())
			{
				bookingID = output.getInt(1);
				cusID = output.getInt(2);
				empID = output.getInt(3);
				date = controller.strToDate(output.getString(4));
				startTime = controller.strToTime(output.getString(5));
				endTime = controller.strToTime(output.getString(6));
				service = output.getInt(7);
				desc=output.getString(8);
				bID = output.getInt(9);
				getBooking = new Booking(bookingID,cusID,empID,date,startTime,endTime,service,desc,bID);				
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.info("OUT getOneBooking\n");
		return getBooking;
	}
	
	/**
	 * set booking status to 'cancel'
	 * @param bookID
	 * @return true or false
	 */
	public boolean cancelBooking(int bookID)
	{
		log.info("IN cancelBooking\n");
		Booking booking = getOneBooking(bookID);
		ArrayList<Booking> bookList = getAllBooking(booking.getBusinessID());
		Boolean exists = false;
		//check if the booking exists using bookID
		for(Booking b : bookList){
			if(b.getBookingID() == bookID){
				exists = true;
			}
		}
		if(exists == false){
			log.debug("Book ID " + bookID + " does not exists!\n");
			log.info("OUT cancelBooking\n");
			return false;
		}
		String query = "UPDATE BOOKINGS SET status = 'canceled' WHERE id = " + bookID;
		
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			log.debug("Book ID "+ bookID +" cancelled\n");
			log.info("OUT cancelBooking\n");
			return true;
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
			log.info("OUT cancelBooking\n");
			return false;
		}
	}
	/**
	 * Clear an employee's working time using employeeID
	 * @author Luke Mason
	 * @param EmployeeID
	 * @return Boolean true or false
	 */
	public boolean clearWorkTimes(int employeeID)
	{
		log.info("IN deleteUser\n");
		String query = "DELETE FROM  EMPLOYEES_WORKING_TIMES WHERE employeeID = '" + employeeID + "';";
		executeQuery(query, "WorkTimes for ID " + employeeID + " deleted!!\n");
		log.info("OUT deleteUser\n");
		return false;
	}
	
	/**
	 * Updates Employees Name
	 * @param empID
	 * @param name
	 */
	public void updateEmployeeName(int empID,String name)
	{
		log.info("IN updateEmployeeName\n");
		String query = "UPDATE employees SET name = '"+name+"' WHERE employeeID = "+empID+";";
		executeQuery(query, "name for ID " + empID + " updated\n");
		log.info("OUT updateEmployeeName\n");
	}
	
	/**
	 * Updates employee's pay rate
	 * @param empID
	 * @param pRate
	 */
	public void updateEmployeePayRate(int empID, double pRate)
	{
		log.info("IN updateEmployeePayRate\n");
		String query = "UPDATE employees SET payRate = '"+pRate+"' WHERE employeeID = "+empID+";";
		executeQuery(query, "name for ID " + empID + " updated\n");
		log.info("OUT updateEmployeePayRate\n");
	}
	
	/**
	 * Deletes specified employee and there working times
	 * @param employeeID
	 * @return
	 */
	public boolean deleteEmployee(int employeeID)
	{
		log.info("IN deleteEmployee\n");
		String query = "DELETE FROM EMPLOYEES WHERE employeeID = '" + employeeID + "';";
		executeQuery(query, "Employee " + employeeID + " deleted!\n");
		log.info("OUT deleteEmployee\n");
		return false;
	}
	
	/**
	 * Gets all working times from database
	 * @param employeeId
	 * @return ArrayList <EmployeeWorkingTime> Objects
	 */
	public ArrayList<EmployeeWorkingTime> getAllWorkingTimes(int businessID)
	{
		log.info("IN getAllWorkingTimes\n");
		ArrayList<EmployeeWorkingTime> databaseWorkingTime = new ArrayList<EmployeeWorkingTime>();
		int id = 0;
		int empID = 0;
		int dayOfWeek = 0;
		Date startTime, endTime;
		String query = "SELECT * FROM EMPLOYEES_WORKING_TIMES WHERE businessID = ?;"; 

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setInt(1, businessID);
			ResultSet output = inject.executeQuery();
			while (output.next())
			{
				id = output.getInt(1);
				empID = output.getInt(2);
				dayOfWeek = output.getInt(3);
				startTime = controller.strToTime(output.getString(4));
				endTime = controller.strToTime(output.getString(5));
				databaseWorkingTime.add(new EmployeeWorkingTime(id,empID,dayOfWeek,startTime,endTime, businessID));		
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			//System.out.println("Getting Working Time: "+sqle.getMessage());
			log.warn(sqle.getMessage());
		}
		log.info("OUT getAllWorkingTimes\n");
		return databaseWorkingTime;
	}
	
	/**
	 * Get Work Times on given day
	 * @param Date
	 * @return array of work times
	 * @author Luke Mason
	 */
	public ArrayList<EmployeeWorkingTime> getWorkTimesOnDay(int day, int businessID)
	{
		log.info("IN getWorkTimesOnDate");
		ArrayList<EmployeeWorkingTime> workTimesOnDate = new ArrayList<EmployeeWorkingTime>();
		ArrayList<EmployeeWorkingTime> workTimes = new ArrayList<EmployeeWorkingTime>();
		workTimes = getAllWorkingTimes(businessID);
		for(EmployeeWorkingTime ewt: workTimes)
		{
			int dayOfWeek = ewt.getDayOfWeek();
			if(dayOfWeek == day)
			{
				workTimesOnDate.add(ewt);
			}
		}
		log.info("OUT getWorkTimesOnDate");
		return workTimesOnDate;
	}
	
	/**
	 * Get Active Bookings on given date
	 * @param Date
	 * @return array of bookings
	 * @author Luke Mason
	 */
	public ArrayList<Booking> getActiveBookingsOnDate(String Date,int businessID)
	{
		log.info("IN getActiveBookingsOnDate");
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		ArrayList<Booking> ActiveBookingsOnDate = new ArrayList<Booking>();
		bookings = getAllBooking(businessID);//gets all bookings from database
		for(Booking bk: bookings)
		{
			Date date = bk.getDate();
			String dateStr = controller.dateToStr(date);
			if(dateStr.equals(Date))
			{
				if(bk.getStatus().equals("active")&& bk.getBusinessID() == businessID)
				{
					ActiveBookingsOnDate.add(bk);
				}
			}
		}
		log.info("OUT getActiveBookingsOnDate");
		return ActiveBookingsOnDate;
	}
	
	/**
	 * adds a service to the database
	 * @param Service Object
	 * @author Joseph Garner
	 */
	public void addService(Service service)
	{
		String query = "INSERT INTO SERVICES(service, length, cost, businessID) " + "VALUES('"+service.getName()+"',"+service.getLengthMin()+","+service.getPrice()+","+service.getBusinessID()+")";
		executeQuery(query, "Service Added\n");
	}
	
	/**
	 * Execute Query on the database
	 * @param query
	 * @param loginfo
	 */
	public void executeQuery(String query, String loginfo){
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			log.info(loginfo);
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
	}
	
	/**
	 * Gets All services
	 * @return array of services
	 * @author Joseph Garner
	 */
	public ArrayList<Service> getAllServices(int businessID)
	{
		int id = 0;
		String _service = "null";
		int length = 0;
		double cost = 0;
		ArrayList<Service> services = new ArrayList<Service>();
		String query = "SELECT * FROM Services WHERE service like ? AND businessID = ?;"; 
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			inject.setString(1, "%"+""+"%");
			inject.setInt(2, businessID);
			ResultSet output = inject.executeQuery();
			while (output.next())
			{
				id = output.getInt(1);
				_service = output.getString(2);
				length = output.getInt(3);
				cost = output.getDouble(4);
				services.add(new Service(id, _service, length, cost, businessID));
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		return services;
	}
	
	/**
	 * Gets a service
	 * @return service Object
	 * @param Service ID
	 * @author Joseph Garner
	 */
	public Service getService(int id)
	{
		Service service = new Service();
		int _id = 0;
		String _service = "null";
		int length = 0;
		double cost = 0;
		int businessID = -1;
		String query = "SELECT * FROM SERVICES WHERE id = ?"; 

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setInt(1, id);
			ResultSet output = inject.executeQuery();
			while (output.next())
			{
				_id = output.getInt(1);
				_service = output.getString(2);
				length = output.getInt(3);
				cost = output.getDouble(4);
				businessID = output.getInt(5);
				service = new Service(_id, _service, length, cost, businessID);			
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		return service;
	}
	
	/**
	 * Gets a service
	 * @return service Object
	 * @param Service Name
	 * @author Joseph Garner
	 */
	public Service getService(String name, int businessID)
	{
		Service service = new Service();
		int id = 0;
		String _service = "null";
		int length = 0;
		double cost = 0;
		String query = "SELECT * FROM SERVICES WHERE service like ? AND businessID = ?"; 

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setString(1,"%"+name+"%");
			inject.setInt(2,businessID);
			ResultSet output = inject.executeQuery();
			while (output.next())
			{
				id = output.getInt(1);
				_service = output.getString(2);
				length = output.getInt(3);
				cost = output.getDouble(4);
				service = new Service(id, _service, length, cost, businessID);			
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		return service;
	}
	
	/**
	 * Deletes specified service
	 * @param id
	 */
	public void deleteService(int id)
	{
		log.info("IN deleteService\n");
		String query = "DELETE FROM SERVICES WHERE id = '" + id + "';";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			log.info("Service " + id + " deleted!\n");
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.info("OUT deleteService\n");
	}
	
	/**
	 * Create booking in database using booking object
	 * @param booking
	 * @return
	 * @author Bryan
	 */
	public void createBooking(Booking book){
		log.info("IN addBookingToDatabase\n");
		//bookingID is made in the database
		String query = "INSERT INTO BOOKINGS (userID,employeeID,date,startTime,endTime, serviceID,status)" + "VALUES(" + book.getCustomerId() + ","+book.getEmployeeID()+",'" + controller.dateToStr(book.getDate()) + "','" + controller.timeToStr(book.getStartTime()) + "','" + controller.timeToStr(book.getEndTime()) + "',"+book.getService()+",'" + book.getStatus() + "');";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			log.info("Booking Added - User ID: "+book.getCustomerId()+" Date: "+book.getDate()+" Start Time: "+book.getStartTime()+" End Time: "+book.getEndTime()+" ServiceNmb: "+book.getService()+" Status: "+book.getStatus()+"\n");
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.info("OUT addBookingToDatabase\n");
	}
	
	/**
	 * add Business 
	 * @param bName, fName, lName, phone, address, weekdayStart, weekdayEnd, weekdayEnd, weekendStart, endTime, weekendEnd
	 * @return 
	 * @author Bryan
	 */	
	public void addBusinessOwner(int id, String fName,String lName,String phone,String address,String weekdayStart,String weekdayEnd,String weekendStart ,String weekendEnd){
		log.info("IN addBusinessToDatabase\n");
		//BusinessID is made in the database
		String query = "INSERT INTO BUSINESS_OWNER (ID,fName,lName,phone,address,weekdayStart,weekdayEnd,weekendStart, weekendEnd, color)" + "VALUES("+id+",'"+ fName + "','"+ lName + "','"+ phone + "','"+ address + "','"+weekdayStart+"','" + weekdayEnd + "','" + weekendStart + "','"+weekendEnd+"', 1);";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			log.info("BusinessOwner Added - weekdayStart: "+weekdayStart+" weekdayEnd: "+weekdayEnd+" weekendStart: "+weekendStart+" weekendEnd: "+weekendEnd+"\n");
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.info("OUT addBusinessToDatabase\n");
		
	}
	
	/**
	 * Gets all Business object from database
	 * @param 
	 * @return ArrayList <Business> Objects
	 */
	public ArrayList<BusinessOwner> getAllBusinessOwner()
	{
		log.info("IN getAllBusiness\n");
		ArrayList<BusinessOwner> databaseBusiness = new ArrayList<BusinessOwner>();
		int busID = 0;
		String fName,lName,phone,address;
		Date weekDayStart,weekDayEnd,weekendStart,weekendEnd;
		String query = "SELECT * FROM BUSINESS_OWNER"; 
		int color = 1;
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			ResultSet output = inject.executeQuery();
			while (output.next())
			{
				busID = output.getInt(1);
				fName= output.getString(2);
				lName= output.getString(3);
				phone= output.getString(4);
				address= output.getString(5);
				weekDayStart = controller.strToTime(output.getString(6));
				weekDayEnd = controller.strToTime(output.getString(7));
				weekendStart = controller.strToTime(output.getString(8));
				weekendEnd = controller.strToTime(output.getString(9));
				color = output.getInt(10);
				BusinessOwner getBusiness= new BusinessOwner(busID,fName,lName, phone, address,weekDayStart,weekDayEnd,weekendStart,weekendEnd);	
				getBusiness.color(color);
				databaseBusiness.add(getBusiness);				
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.info("OUT getAllBusiness\n");
		return databaseBusiness;
	}
	
	/**
	 * Gets one business from database
	 * @param busID
	 * @return one <Business> Object
	 */
	public BusinessOwner getOneBusiness(int busID)
	{
		log.info("IN getOneBusiness\n");
		BusinessOwner getBusiness = null;
		int bID = 0;
		String fName,lName,phone,address;
		Date weekDayStart,weekDayEnd,weekendStart,weekendEnd;
		String query = "SELECT * FROM BUSINESS_OWNER WHERE id = ?"; 
		int color = 1;
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//creates a user from the found information
			inject.setInt(1, busID);
			ResultSet output = inject.executeQuery();
			while (output.next())
			{
				bID = output.getInt(1);
				fName= output.getString(2);
				lName= output.getString(3);
				phone= output.getString(4);
				address= output.getString(5);
				weekDayStart = controller.strToTime(output.getString(6));
				weekDayEnd = controller.strToTime(output.getString(7));
				weekendStart = controller.strToTime(output.getString(8));
				weekendEnd = controller.strToTime(output.getString(9));
				color = output.getInt(10);
				getBusiness= new BusinessOwner(bID,fName,lName, phone, address,weekDayStart,weekDayEnd,weekendStart,weekendEnd);
				getBusiness.color(color);
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.info("OUT getOneBusiness\n");
		return getBusiness;
	}
	
	/**
	 * add Business
	 * @param 
	 * @return 
	 * @author Bryan
	 */	
	public void createBusiness(String businessName2){
		log.info("IN addBusinessToDatabase\n");
		//BusinessID is made in the database
		String query = "INSERT INTO BUSINESS (businessName)" + "VALUES('"+businessName2+"');";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			log.info("Business Added - Business Name: "+businessName2+"\n");
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.info("OUT addBusinessToDatabase\n");	
	}
	
	public Business getBusiness(String businessName)
	{
		Business business = null;
		String query = "SELECT * FROM BUSINESS WHERE businessName = ?";
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			inject.setString(1, businessName);
			ResultSet output = inject.executeQuery();
			while (output.next())
			{
				int businessID = output.getInt(1);
				businessName= output.getString(2);
				business= new Business(businessID,businessName);				
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.info("GetBusiness screwed up in database connection\n");
			log.warn(sqle.getMessage());
		}
		return business;
	}
	
	public ArrayList<Business> getAllBusiness(){
		ArrayList<Business> bis = new ArrayList<>();
		String query = "SELECT * FROM BUSINESS"; 
		int id;
		String name;
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query)){
			
			ResultSet o = inject.executeQuery();
			while (o.next())
			{
				id = o.getInt(1);
				name = o.getString(2);
				bis.add(new Business(id, name));
			}
			
		}catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		return bis;
	}
	
	public Business getBusiness(int i){
		Business bis = null;
		String query = "SELECT * FROM BUSINESS WHERE businessID = "+i; 
		int id;
		String name;
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query)){
			
			ResultSet o = inject.executeQuery();
			while (o.next())
			{
				id = o.getInt(1);
				name = o.getString(2);
				bis = new Business(id, name);
			}
			
		}catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		return bis;
	} 
	
	public void updateBO(int id, int color)
	{
		log.info("IN updateEmployeePayRate\n");
		String query = "UPDATE BUSINESS_OWNER SET COLOR = '"+color+"' WHERE ID = "+id+";";
		executeQuery(query, "name for ID " + id + " updated\n");
		log.info("OUT Updated Color\n");
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public String[] getColor(int id){
        String query = "SELECT * FROM COLOR WHERE ID = ?";
        String[] val = new String[4];
        try (Connection connect = this.connect(); PreparedStatement inject  = connect.prepareStatement(query))
        {
            inject.setInt(1,id);
            ResultSet o = inject.executeQuery();
            log.debug(o.getInt(1));
            val[0] = o.getString(2);
            val[1] = o.getString(3);
            val[2] = o.getString(4);
            val[3] = o.getString(5);

        }
        catch(SQLException sqle)
        {
            log.fatal(sqle.getMessage());
        }
        return val;
    }
	
	public void userBO(int id, int bid)
	{
		log.info("IN Update UBO\n");
		String query = "UPDATE USERS SET businessID = '"+bid+"' WHERE userID = "+id+";";
		executeQuery(query, "name for ID " + id + " updated\n");
		log.info("OUT Updated User\n");
	}
	
	public void addImage(String val, int id){
		log.info("IN Update UBO\n");
		String query = "UPDATE BUSINESS_OWNER SET image = '"+val+"' WHERE ID = "+id+";";
		executeQuery(query, "Image for " + id + " updated\n");
		log.info("OUT added image User\n");
	}
    
    
    public String getlogo(int id){
    	BufferedImage bis = null;
		String query = "SELECT image FROM BUSINESS_OWNER WHERE ID = "+id; 
		String val = null;
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query)){
			
			ResultSet o = inject.executeQuery();
			while (o.next())
			{
				val = o.getString(1);
				
			}
			
		}catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		return val;
    }
    
    /**
	 * Delete user from database
	 * @param userName
	 */
    public void deleteUser(int businessID)
	{
		log.info("IN deleteUser\n");
		String query = "DELETE FROM BUSINESS WHERE businessID = "+businessID+";";
		String query1 = "DELETE FROM users WHERE businessID = "+businessID+";";
		//int id = getOneBusiness(getUser(businessID,1).getID()).getID();
		BusinessOwner b = getOneBusiness(businessID);
		if(b!=null){
			int id = b.getID();
			String query2 = "DELETE FROM BUSINESS_OWNER WHERE businessID = "+id+";";
			executeQuery(query2, "BUSINESS_OWNER " + businessID + " deleted\n");
		}
		String query3 = "DELETE FROM CLIENTDETAILS WHERE businessID = "+businessID+";";
		String query4 = "DELETE FROM EMPLOYEES WHERE businessID = "+businessID+";";
		String query5 = "DELETE FROM BOOKINGS WHERE businessID = "+businessID+";";
		String query6 = "DELETE FROM SERVICES WHERE businessID = "+businessID+";";
		executeQuery(query, "Business " + businessID + " deleted\n");
		executeQuery(query3, "CLIENTDETAILS " + businessID + " deleted\n");
		executeQuery(query1, "User " + businessID + " deleted\n");
		executeQuery(query4, "EMPLOYEES " + businessID + " deleted\n");
		executeQuery(query5, "BOOKINGS " + businessID + " deleted\n");
		executeQuery(query6, "SERVICES " + businessID + " deleted\n");
		log.info("OUT deleteUser\n");
	}
}
