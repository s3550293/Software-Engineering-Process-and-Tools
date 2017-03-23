package program;

//import javafx.application.Application;

public class Main
{
	public static void main(String[] args)
	{
		Controller cont = new Controller();
		System.out.println(cont.convertToDate("12/01/2017"));
		System.out.println(cont.displayDate(cont.convertToDate("12/01/2017")));
		
		/*
		Database data = new Database("company.db");
		DatabaseConnection connect = new DatabaseConnection();
		data.createTable("company.db");
		connect.addUser("Simba", "123456", 0);
		connect.addUser("Scar", "123456", 1);
		Login menu = new Login();
		menu.loginMenu();
		*/
	}
}
