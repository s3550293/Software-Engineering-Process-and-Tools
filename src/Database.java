import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database
{
	public Database(String filename)
	{
		createNewDatabase(filename);
	}
	/*
	 * connect to the database
	 */
	public static void createNewDatabase(String filename)
	{
		/*
		 * sets the url and name of the database
		 * if the database doesn't exist one will be created
		 */
		String url = "jdbc:sqlite:db/"+filename;
		try(Connection connect = DriverManager.getConnection(url))
		{
			/*
			 * Checks connection to the database throws exception if unable to connect
			 */
			if(connect != null)
			{
				DatabaseMetaData meta = connect.getMetaData();
                System.out.println("The driver is " + meta.getDriverName());
                System.out.println("New database has been created."); 
			}
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
	}
	
	/*
	 * Function to create tables
	 */
	public void createTable(String filename)
	{
		String url = "jdbc:sqlite:db/"+filename;
		String sql = "CREATE TABLE IF NOT EXSITS users (\n"
						+"id integer PRIMARY KEY,\n"
						+"username text NOT NULL\n,"
						+"password text NOT NULL\n"
						+"accountType boolean NOT NULL);";
	}
}
