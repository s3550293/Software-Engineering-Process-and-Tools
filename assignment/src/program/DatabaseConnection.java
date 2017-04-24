package program;

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
	public DatabaseConnection(){log.setLevel(Level.WARN);}
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
	public void addUser(String username, String password, int accountType)
	{
		/*
		 * account type boolean 1 for business owner 0 for user
		 */
		String query = "INSERT INTO users(username, password, accountType) " + "VALUES('"+username+"','"+password+"','"+accountType+"')";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			/*
			 * Sets the '?' values into the query
			 */
			inject.executeUpdate(query);
			//System.out.println("User Added");
			log.info("User Added\n");
		}
		catch(SQLException sqle)
		{
			//System.out.println(sqle.getMessage());
			log.warn(sqle.getMessage());
		}
	}
	/**
	 * Adds User to database
	 * @param username
	 * @param password
	 * @param accountType
	 */
	public void addUserDetails(int id, String fname, String lname, String email, String phone, String dob, String gender)
	{
		/*
		 * account type boolean 1 for business owner 0 for user
		 */
		String query = "INSERT INTO CLIENTDETAILS(id, FName, LName, Email, Phone, DOB, Gender) " + "VALUES("+id+",'"+fname+"','"+lname+"','"+email+"','"+phone+"','"+dob+"','"+gender+"')";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			/*
			 * Sets the '?' values into the query
			 */
			inject.executeUpdate(query);
			//System.out.println("User Added");
			log.info("User Added\n");
		}
		catch(SQLException sqle)
		{
			//System.out.println(sqle.getMessage());
			log.warn(sqle.getMessage());
		}
	}
	
	/**
	 * Gets where the user name keyword matches another users name
	 * @param username
	 * @return User Object
	 */
	public User getUser(String username)
	{
		int _id = 0;
		String _username = "null";
		String _password = "null";
		int _accountType = 0;
		String query = "SELECT * FROM users WHERE username like ?";
		//Creates a null user to return, this can be used to validate user at login
		User databaseUser = null;
		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setString(1,"%" + username + "%");
			ResultSet output = inject.executeQuery();
			while (output.next()){
				_id = output.getInt(1);
				_username = output.getString(2);
				_password = output.getString(3);
				_accountType = output.getInt(4);
			}
			databaseUser = new User(_id ,_username, _password, _accountType);
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
	 * This function finds a selection of employees that matches the string name
	 * @param name
	 * @return ArrayList<Employee> Objects
	 */
	public ArrayList<Employee> getEmployees(String name)
	{
		log.info("IN getEmployees\n");
		ArrayList<Employee> databaseEmployee = new ArrayList<Employee>();
		int id = 0;
		double payRate = 0;
		String query = "SELECT * FROM EMPLOYEES WHERE name like ? "; 

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setString(1,"%"+name+"%");
			ResultSet output = inject.executeQuery();
			while (output.next()){
				id = output.getInt(1);
				name = output.getString(2);
				payRate = output.getDouble(3);
				databaseEmployee.add(new Employee(id ,name, payRate));
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
			}
			databaseEmployee = new Employee(id ,name, payRate);
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
	public void addEmployee(String name, double payRate)
	{
		log.info("IN addEmployee\n");
		String query = "INSERT INTO EMPLOYEES(name, payRate) " + "VALUES ('" + name + "'," + payRate + ");";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			System.out.println("Employee '"+name+"' Added");
		}
		catch(SQLException sqle)
		{
			//System.out.println(sqle.getMessage());
			log.warn(sqle.getMessage());
		}
		log.info("OUT addEmployee\n");
	}
	
	/**
	 * Adds ONE employee working time to the database
	 * @param empID
	 * @param date
	 * @param startTime
	 * @param endTime
	 */
	public void addEmployeeWorkingTime(int empID, String date, String startTime, String endTime)
	{
		log.info("IN addEmployeeWorkingTimeToDatabase\n");
		String query = "INSERT INTO EMPLOYEES_WORKING_TIMES(employeeID, date, startTime, endTime) " + "VALUES ("+ empID +",'"+ date +"','"+ startTime +"','"+ endTime +"');";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			//System.out.println("Employee " + empID+ "'s working time Added");
		}
		catch(SQLException sqle)
		{
			//System.out.println(sqle.getMessage());
			//System.out.println(date);
			log.warn(sqle.getMessage());
			log.info(date);
		}
		log.info("OUT addEmployeeWorkingTimeToDatabase\n");
	}
	
	/**
	 * Delete user from database
	 * @param userName
	 */
	public void deleteUser(String userName)
	{
		log.info("IN deleteUser\n");
		String query = "DELETE FROM users WHERE username LIKE '" + userName + "'";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			System.out.println("User " + userName + " deleted!!");
		}
		catch(SQLException sqle)
		{
			//System.out.println(sqle.getMessage());
			log.warn(sqle.getMessage());
		}
		log.info("OUT deleteUser\n");
	}
	
	/**
	 * Delete Booking from database
	 * @param id - allows the user to delete a select booking
	 */
	public void deleteBooking(int id)
	{
		
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
		Date date, startTime, endTime;
		String query = "SELECT * FROM EMPLOYEES_WORKING_TIMES WHERE employeeID = ? "; 

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
				date = controller.convertStringToDate(output.getString(3));
				startTime = controller.convertStringToTime(output.getString(4));
				endTime = controller.convertStringToTime(output.getString(5));
				databaseWorkingTime.add(new EmployeeWorkingTime(id,empID,date,startTime,endTime));				
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
	
	/*/**
	 * Rounds a double to x amount of decimal places then return the rounded double
	 * @param value
	 * @param places
	 * @return
	 */
	/*public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}*/
	
	/**
	 * Drops table name from database
	 * @param tableName
	 * @return
	 */
	public boolean dropTable(String tableName)
	{
		String query = "DROP TABLE IF EXISTS '"+ tableName +"' ";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			System.out.println("Table "+ tableName +"");
			return true;
		}
		catch(SQLException sqle)
		{
			//System.out.println(sqle.getMessage());
			log.warn(sqle.getMessage());
			return false;
		}
	}
	
	/**
	 * Drops table name from database
	 * @param username
	 * @return
	 */
	public boolean dropUser(String username)
	{
		String query = "DELETE FROM USERS WHERE username like '"+ username +"' ";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			//System.out.println("Table "+ tableName +"");
			log.info("User "+username+" Dropped\n");
			return true;
		}
		catch(SQLException sqle)
		{
			//System.out.println(sqle.getMessage());
			log.warn(sqle.getMessage());
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
	public void addBooking (int userId, String date, String startTime, String endTime, int service, String status)
	{
		log.info("IN addBookingToDatabase\n");
		//bookingID is made in the database
		String query = "INSERT INTO BOOKINGS (userID,date,startTime,endTime, serviceID,status)" + "VALUES(" + userId + ",'" + date + "','" + startTime + "','" + endTime + "',"+service+",'" + status + "');";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			System.out.println("Booking Added");
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		log.info("OUT addBookingToDatabase\n");
	}
	
	/**
	 * Gets the employee's working times from database
	 * @param cusID
	 * @return ArrayList <Booking> Objects
	 */
	public ArrayList<Booking> getBooking (int customerID)
	{
		ArrayList<Booking> databaseBookingTime = new ArrayList<Booking>();
		int bookingID = 0;
		int cusID = 0;
		Date date, startTime, endTime;
		String desc;
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
				date = controller.convertStringToDate(output.getString(3));
				startTime = controller.convertStringToTime(output.getString(4));
				endTime = controller.convertStringToTime(output.getString(5));
				desc=output.getString(6);
				databaseBookingTime.add(new Booking(bookingID,cusID,date,startTime,endTime,desc));				
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		return databaseBookingTime;
	}
	
	/**
	 * Gets all booking object from database
	 * @param cusID
	 * @return ArrayList <Booking> Objects
	 */
	public ArrayList<Booking> getAllBooking ()
	{
		ArrayList<Booking> databaseBookingTime = new ArrayList<Booking>();
		int bookingID = 0;
		int cusID = 0;
		Date date, startTime, endTime;
		String desc;
		String query = "SELECT * FROM BOOKINGS"; 

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			//inject.setInt(1,customerID);
			ResultSet output = inject.executeQuery();
			while (output.next())
			{
				bookingID = output.getInt(1);
				cusID = output.getInt(2);
				date = controller.convertStringToDate(output.getString(3));
				startTime = controller.convertStringToTime(output.getString(4));
				endTime = controller.convertStringToTime(output.getString(5));
				desc=output.getString(6);
				databaseBookingTime.add(new Booking(bookingID,cusID,date,startTime,endTime,desc));				
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		return databaseBookingTime;
	}
	
	/**
	 * Gets one booking from database
	 * @param bookID
	 * @return one <Booking> Object
	 */
	public Booking getOneBooking(int bookID)
	{
		Booking getBooking = null;
		int bookingID = 0;
		int cusID = 0;
		Date date, startTime, endTime;
		String desc;
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
				date = controller.convertStringToDate(output.getString(3));
				startTime = controller.convertStringToTime(output.getString(4));
				endTime = controller.convertStringToTime(output.getString(5));
				desc=output.getString(6);
				getBooking = new Booking(bookingID,cusID,date,startTime,endTime,desc);				
			}
			output.close();
		}
		catch(SQLException sqle)
		{
			log.warn(sqle.getMessage());
		}
		return getBooking;
	}
	
	/*
	 * set booking status to 'cancel'
	 * @param bookID
	 * @return true or false
	 */
	public boolean cancelBooking(int bookID){
		ArrayList<Booking> bookList = new ArrayList<Booking>();
		bookList = getAllBooking();
		Boolean exists = false;
		for(Booking b : bookList){
			if(b.getBookingID() == bookID){
				exists = true;
			}
		}
		if(exists == false){
			System.out.println("\nBook ID " + bookID + " does not exists!");
			return false;
		}
		String query = "UPDATE BOOKINGS SET status = 'cancel' WHERE id = " + bookID;
		
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			System.out.println("Book ID "+ bookID +" cancelled");
			return true;
		}
		catch(SQLException sqle)
		{
			//System.out.println(sqle.getMessage());
			log.warn(sqle.getMessage());
			return false;
		}
	}
	/**
	 * @author Luke Mason
	 * @param EmployeeID
	 * @return
	 */
	public boolean clearWorkTimes(int employeeID)
	{
		log.info("IN deleteUser\n");
		String query = "DELETE FROM  EMPLOYEES_WORKING_TIMES WHERE employeeID = '" + employeeID + "'";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate(query);
			System.out.println("WorkTimes for ID " + employeeID + " deleted!!");
		}
		catch(SQLException sqle)
		{
			//System.out.println(sqle.getMessage());
			log.warn(sqle.getMessage());
		}
		log.info("OUT deleteUser\n");
		return false;
	}
	

}
