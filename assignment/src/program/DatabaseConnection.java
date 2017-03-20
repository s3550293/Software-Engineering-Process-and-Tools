package program;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.*;

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
				System.out.println(output.getInt(4));
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
		
		public void addEmployee(String name, double payRate)
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
		
		public boolean employeeIDCheck(int id) throws SQLException {
			String EmployeeIDquery = "SELECT * FROM employeedetails";
			Statement stmt = null;
			String url = "jdbc:sqlite:db/company.db";
			
			try(Connection connect = DriverManager.getConnection(url)){
				stmt = connect.createStatement();
				ResultSet rs = stmt.executeQuery(EmployeeIDquery);
				
				//loop result set
				while(rs.next()){
					int empID = rs.getInt("emID");
					if(empID == id){
						System.out.println("id retrieved is " + id);
						return true;
					}
				}
				rs.close();
				
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
			return false;

		}
		
		@Before
		public void setUp()
		{
			//Wiping EMPLOYEES table at start of test in case of any changes manually made via SQLite
			try(Connection connect = this.connect(); Statement inject = connect.createStatement())
			{
				inject.executeUpdate("DROP TABLE EMPLOYEES");
				System.out.println("Dropped Table");
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle.getMessage());
			}
			//Creates ALL TABLES
			Database db = new Database("company.db");
			db.createTable("company.db");
		}
		@Test
		public void testAddEmployee_And_TestGetEmployee_And_TestEmployeeToString()
		{	
			//Adding 4 new employees to blank EMPLOYEES table
			addEmployee("Luke Mason", 1000);
			addEmployee("Jacob Boehm", 123);
			addEmployee("Jake Mason", 30);
			addEmployee("Leonardo Dicaprio", 12);
			
			//Assigning Employees to the employees in database
			Employee Luke_Mason = getEmployee(1);
			Employee Jacob_Boehm = getEmployee(2);
			Employee Jake_Mason = getEmployee(3);
			Employee Leonardo_Dicaprio = getEmployee(4);
			
			//Testing the toString() and get() methods against the different employees
			assertTrue(Luke_Mason.getId()==1);
			assertTrue(Luke_Mason.getPayRate()== 1000);
			assertTrue(Luke_Mason.getName().equals("Luke Mason"));
			assertTrue(Luke_Mason.toString().equals("ID: 1   Name: Luke Mason   Pay Rate: $1000.0"));
			
			assertTrue(Jacob_Boehm.getId()==2);
			assertTrue(Jacob_Boehm.getPayRate()== 123.0);
			assertTrue(Jacob_Boehm.getName().equals("Jacob Boehm"));
			assertTrue(Jacob_Boehm.toString().equals("ID: 2   Name: Jacob Boehm   Pay Rate: $123.0"));
		
			assertTrue(Jake_Mason.getId()==3);
			assertTrue(Jake_Mason.getPayRate()== 30);
			assertTrue(Jake_Mason.getName().equals("Jake Mason"));
			assertTrue(Jake_Mason.toString().equals("ID: 3   Name: Jake Mason   Pay Rate: $30.0"));
			
			assertTrue(Leonardo_Dicaprio.getId()==4);
			assertTrue(Leonardo_Dicaprio.getPayRate()== 12);
			assertTrue(Leonardo_Dicaprio.getName().equals("Leonardo Dicaprio"));
			assertTrue(Leonardo_Dicaprio.toString().equals("ID: 4   Name: Leonardo Dicaprio   Pay Rate: $12.0"));
			
			//Deleting Employee 2 & 3 from EMPLOYEE table
			try(Connection connect = this.connect(); Statement inject = connect.createStatement())
			{
				inject.executeUpdate("DELETE FROM EMPLOYEES WHERE name LIKE '%Jacob%' OR payRate LIKE 30;");
				System.out.println("Deleted specified employees");
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle.getMessage());
			}
			
			//Adding new employee After rows have been deleted
			addEmployee("Harry Potter",666);
			
			//Getting employee 5, even though we just deleted two employees
			Employee Harry_Potter = getEmployee(5);
			
			//Getting Employees 2 & 3 that were just deleted
			Employee Jacob_Boehm_2 = getEmployee(2);
			Employee Jake_Mason_2 = getEmployee(3);
		
			//Testing the output of getting employees that do not exist  E.G ID 2 & 3 (they were deleted just before)
			assertTrue(Jacob_Boehm_2.getId()==0);
			assertTrue(Jacob_Boehm_2.getName().equals("Employee does not exist"));
			assertTrue(Jacob_Boehm_2.toString().equals("Sorry, Employees with that ID do not exist"));
			
			assertTrue(Jake_Mason_2.getId()==0);
			assertTrue(Jake_Mason_2.getName().equals("Employee does not exist"));
			assertTrue(Jake_Mason_2.toString().equals("Sorry, Employees with that ID do not exist"));
			
			//Testing that the ID of the new employee is 5 and DOES NOT take on the ID of recently deleted employees 2 & 3
			assertTrue(Harry_Potter.getId()==5);
			assertTrue(Harry_Potter.getPayRate()== 666);
			assertTrue(Harry_Potter.getName().equals("Harry Potter"));
			assertTrue(Harry_Potter.toString().equals("ID: 5   Name: Harry Potter   Pay Rate: $666.0"));

			
		}
		@After
		public void tearDown()
		{
			//Deleting table EMPLOYEES after the Test has been executed correctly
			try(Connection connect = this.connect(); Statement inject = connect.createStatement())
			{
				inject.executeUpdate("DROP TABLE EMPLOYEES");
				System.out.println("Dropped Table");
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle.getMessage());
			}
		}
		
	
}
