import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
	
	public void addUser(String username, String password, boolean accountType)
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
		String _username = "John";
		String _password = "Smith";
		boolean _accountType = false;
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
				_accountType = output.getBoolean(4);
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
	//and displays the employees with their relative ID's
	//getEmployee(int ID) follows on from this.
	public String findEmployee(String name)
	{
		return name;
	}
	public Employee getEmployee(int employeeID)
	{
		Employee databaseEmployee = null;
		int id = 0;
		String name = "test";
		int payRate = 0;
		String query = "SELECT * FROM EMPLOYEES WHERE employeeID like ?"; 

		try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			//crates a user from the found information
			inject.setInt(1,employeeID);
			ResultSet output = inject.executeQuery();
			while (output.next()){
				id = output.getInt(1);
				name = output.getString(2);
				payRate = output.getInt(3);
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
	
	public void addEmployee(String name, int payRate)
	{
		String query = "INSERT INTO EMPLOYEES(name, payRate) " + "VALUES ('" + name + "'," + payRate + ");";
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			/*
			 * Sets the '?' values into the query
			 */
			inject.executeUpdate(query);
			System.out.println("Employee '"+name+"' Added");
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
	}
	
	@Before
	public void setUp()
	{
		Database db = new Database("company.db");
		db.createTable("company.db");
	}
	@Test
	public void testAddEmployeeAndTestGetEmployee()
	{	
		addEmployee("Luke Mason", 1000);
		addEmployee("Jacob Boehm", 123);
		addEmployee("Jake Mason", 30);
		addEmployee("Leonardo Decaprio", 12);
		
		Employee Luke_Mason = getEmployee(1);
		Employee Jacob_Boehm = getEmployee(2);
		Employee Jake_Mason = getEmployee(3);
		Employee Leonardo_Decaprio = getEmployee(4);
		
		assertTrue(Luke_Mason.toString().equals("ID: 1   Name: Luke Mason   Pay Rate: $1000"));
		assertTrue(Jacob_Boehm.toString().equals("ID: 2   Name: Jacob Boehm   Pay Rate: $123"));
		assertTrue(Jake_Mason.toString().equals("ID: 3   Name: Jake Mason   Pay Rate: $30"));
		assertTrue(Leonardo_Decaprio.toString().equals("ID: 4   Name: Leonardo Decaprio   Pay Rate: $12"));
	}
	@After
	public void tearDown()
	{
		//Connection connect = this.connect(); Statement inject = connect.createStatement();
		//inject.executeUpdate("DROP TABLE EMPLOYEES;");
	}
}
