package src;

//import javafx.application.Application;

public class Main
{
	public static void main(String[] args)
	{
		/*
		 * Database class should only be used once at the start, once the database is created there is no need for it
		 * start using "DatabaseConnection" once the database exists
		 * tables are to be writtien before completeion
		 */
		Database db = new Database("company.db");
		db.createTable("company.db");
		
		/*
		 * DatabaseConnection is to be used to connect and get data from the database
		 */
		DatabaseConnection connect = new DatabaseConnection();
		/*
		 * other functions similar to addUser will be added later ie add booking
		 */
		//connect.addUser("William", "Apples22", false);
		
		
		User bob = connect.getUser("William");
		System.out.println(bob.toString());
		Login login = new Login();
		login.loginMenu();
	}
}
