import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * http://www.sqlitetutorial.net/sqlite-java/
 */
public class DatabaseConnection
{
	public DatabaseConnection()
	{
		connect();
	}
	public static void connect()
	{
		/*
		 * sets the url and name of the database
		 * if the database doesn't exist one will be created
		 */
		String url = "jdbc:sqlite:db/company.db";
		try(Connection connect = DriverManager.getConnection(url))
		{
			/*
			 * Checks connection to the database throws exception if unable to connect
			 */
			if(connect != null)
			{
				System.out.println("Connected");
			}
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
	}
	
	public void selectTable()
	{
		
	}
}
