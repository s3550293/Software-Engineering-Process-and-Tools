package program;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * follow this link to find out how to add tables and access tables
 * http://www.sqlitetutorial.net/sqlite-java/
 * this is where I will leave my sanity
 */
public class DatabaseConnection
{
	public DatabaseConnection(){}
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
            System.out.println(sqle.getMessage());
        }
        return connect;
	}
	
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
			System.out.println("User Added");
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
	}
	
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
			System.out.println("Getting User: "+sqle.getMessage());
		}
		return databaseUser;
	}
	
		//This function finds a selection of employees that matches the string name 
		//returns an array of object Employee
		public Employee[] getEmployees(String name)
		{
			Employee[] databaseEmployee = new Employee[1000];
			int i = 0;
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
					databaseEmployee[i] = new Employee(id ,name, payRate);
					++i;
				}
				output.close();
			}
			catch(SQLException sqle)
			{
				System.out.println("Getting Employee: "+sqle.getMessage());
			}
			return databaseEmployee;
		}

		//Gets the employee with matching ID from database and returns it as an Employee Object
		public Employee getEmployee(int employeeID)
		{
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
				System.out.println("Getting Employee: "+sqle.getMessage());
			}
			return databaseEmployee;
		}
		
		//Adds an employee to the database with the RAW parameters [parameters are not tested]
		public void addEmployee(String name, double payRate)
		{
			String query = "INSERT INTO EMPLOYEES(name, payRate) " + "VALUES ('" + name + "'," + payRate + ");";
			try(Connection connect = this.connect(); Statement inject = connect.createStatement())
			{
				inject.executeUpdate(query);
				System.out.println("Employee '"+name+"' Added");
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle.getMessage());
			}
		}
		
		//Adds ONE employee working time to the database with the RAW parameters [parameters are not tested]
		public void addEmployeeWorkingTime(int id, String date, double startTime, double endTime)
		{
			String query = "INSERT INTO EMPLOYEES_WORKING_TIMES " + "VALUES (" + id + ",'" + date + "'," + startTime + "," + endTime + ");";
			try(Connection connect = this.connect(); Statement inject = connect.createStatement())
			{
				inject.executeUpdate(query);
				System.out.println("Employee " + id+ "'s working time Added");
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle.getMessage());
			}
		}
		
		//Delete user from database
		public void deleteUser(String userName)
		{
			String query = "DELETE FROM users WHERE username LIKE '" + userName + "'";
			try(Connection connect = this.connect(); Statement inject = connect.createStatement())
			{
				inject.executeUpdate(query);
				System.out.println("User " + userName + " deleted!!");
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle.getMessage());
			}
		}
		
		//Gets the employee's working times from database and returns it as an array of EmployeeWorkingTime
		public EmployeeWorkingTime[] getEmployeeWorkingTimes(int employeeId)
		{
			EmployeeWorkingTime[] databaseWorkingTime = new EmployeeWorkingTime[10000];
			int i = 0;
			int id = 0;
			String date;
			double startTime;
			double endTime;
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
					date = output.getString(2);
					startTime = output.getDouble(3);
					endTime = output.getDouble(4);
					databaseWorkingTime[i] = new EmployeeWorkingTime(id ,date,startTime,endTime);
					++i;				
				}
				output.close();
			}
			catch(SQLException sqle)
			{
				System.out.println("Getting Working Time: "+sqle.getMessage());
			}
			return databaseWorkingTime;
		}
		
		//Rounds a double to x amount of decimal places then return the rounded double
		public static double round(double value, int places) {
		    if (places < 0) throw new IllegalArgumentException();

		    long factor = (long) Math.pow(10, places);
		    value = value * factor;
		    long tmp = Math.round(value);
		    return (double) tmp / factor;
		}
		public boolean dropTable(String tableName)
		{
			String query = "DROP TABLE IF EXISTS '"+ tableName +"' ";
			try(Connection connect = this.connect(); Statement inject = connect.createStatement())
			{
				/*
				/* Sets the '?' values into the query
		 		*/
				inject.executeUpdate(query);
				System.out.println("Table "+ tableName +"");
				return true;
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle.getMessage());
				return false;
			}
		}
		
}
