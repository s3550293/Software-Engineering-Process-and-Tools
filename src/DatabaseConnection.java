import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * follow this link to find out how to add tables and access tables
 * http://www.sqlitetutorial.net/sqlite-java/
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
		String query = "SELECT username, password, accountType FROM users WHERE username = ?"; //ToDo
		User databaseUser = null;
		try (Connection conn = this.connect(); PreparedStatement inject  = conn.prepareStatement(query))
		{
			//Sets '?' to username
			inject.setString(1, username);
			ResultSet output  = inject.executeQuery();
			databaseUser = new User(output.getString("username"), output.getString("password"), output.getBoolean("accountType"));
		}
		catch(SQLException sqle)
		{
			
		}
		return databaseUser;
	}
}
