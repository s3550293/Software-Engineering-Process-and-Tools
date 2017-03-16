package main_Package;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		String query = "INSERT INTO users(userID,username, password, accountType), VALUES(null,?,?,?)";
		try(Connection connect = this.connect(); PreparedStatement inject = connect.prepareStatement(query))
		{
			/*
			 * Sets the '?' values into the query
			 */
			inject.setString(1, username);
			inject.setString(2, password);
			inject.setBoolean(3, accountType);
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
	}
	public User getUser(String username)
	{
		String query = "SELECT username, password, accountType FROM users WHERE username = ?";
		//Creates a null user to return, this can be used to validate user at login
		User databaseUser = null;
		try (Connection conn = this.connect(); PreparedStatement inject  = conn.prepareStatement(query))
		{
			//Sets '?' to user name in the query
			inject.setString(1, username);
			ResultSet output  = inject.executeQuery();
			//crates a user from the found information
			databaseUser = new User(output.getString("username"), output.getString("password"), output.getBoolean("accountType"));
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
		return databaseUser;
	}
}
