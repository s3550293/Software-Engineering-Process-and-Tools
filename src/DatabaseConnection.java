import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	public void getUser(String username, String password, boolean accountType)
	{
		String query = ""; //ToDo
		
		try (Connection conn = this.connect(); Statement extract = conn.createStatement(); ResultSet output = extract.executeQuery(sql))
		{
			
		}
		catch(SQLException sqle)
		{
			
		}
	}
}
