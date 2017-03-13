
public class Main
{
	public static void main(String[] args)
	{
		/*
		 * Database class should only be used once at the start, once the database is created there is no need for it
		 * start using "DatabaseConnection" once the database exists
		 */
		Database db = new Database("company.db");
		Login login = new Login();
		login.loginMenu();
	}
}
