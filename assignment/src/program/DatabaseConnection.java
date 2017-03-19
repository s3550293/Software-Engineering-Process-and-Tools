package program;

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
	
	//Counts how many employees there currently are in the database and generates 
		//the next ID according to that number e.g 15 current employees, next id = "00016"
		public String returnNextEmployeeId()
		{
			/* SELECT COUNT(employeeID) FROM employee; 
			 */
			// int amountOfEmployees;
			String id = "";
			return id;
		}
		
		public void addEmployeeToDatabase(String name, int payRate)
		{
			/*
			 * account type boolean 1 for business owner 0 for user
			 */
			String query = "INSERT INTO employeedetails(name, address, phoneNumber) " + "VALUES('"+name+"','"+payRate+"')";
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
		
		public boolean addWorkingTimeForNextMonth(int Id)
		{
			return false;
		}
		
		public Employee getEmployee(String name)
		{
			int _id = 0;
			String _name = "";
			String _payRate = "";
			String query = "SELECT * FROM employeedetails WHERE username like ?";
			//Creates a null user to return, this can be used to validate user at login
			Employee databaseEmployee = null;
			try (Connection connect = this.connect(); PreparedStatement  inject  = connect.prepareStatement(query))
			{
				//Sets '?' to user name in the query
				//crates a user from the found information
				inject.setString(1,"%" + name + "%");
				ResultSet output = inject.executeQuery();
				while (output.next()){
					_id = output.getInt(1);
					_name = output.getString(2);
					_payRate = output.getString(3);
				}
				databaseEmployee = new Employee(_id ,_name, _payRate);
				output.close();
			}
			catch(SQLException sqle)
			{
				System.out.println("Getting User: "+sqle.getMessage());
			}
			return databaseEmployee;
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
		
	
}
