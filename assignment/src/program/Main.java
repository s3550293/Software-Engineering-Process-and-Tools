package program;

//import java.util.ArrayList;

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
		
		/*
		 * DatabaseConnection is to be used to connect and get data from the database
		 */
		/*
		 * other functions similar to addUser will be added later ie add booking
		 */
		
		Database db = new Database("company.db");
		db.createTable("company.db");
		DatabaseConnection connect = new DatabaseConnection();
		connect.addUser("William", "Apples22", 0);
		connect.addUser("bo1","123456",1);
		connect.addEmployee("Luke Charles",100);
		connect.addEmployee("David Smith",100);
		connect.addEmployeeWorkingTime(1,"28/03/2017","9:50","17:25");
		connect.addEmployeeWorkingTime(1,"29/03/2017","8:30","14:30");

		connect.addEmployeeWorkingTime(2,"01/04/2017","10:30","12:30");
		connect.addEmployeeWorkingTime(2,"26/03/2017","11:30","15:30");
		connect.addBooking(11, "01/04/2017", "10:30", "11:30", "first");
		Booking bo = new Booking();
		bo = connect.getOneBooking(6);
		System.out.println(bo.toString());
		/*
		User lame = connect.getUser("William");
		User bob = connect.getUser("david");
		System.out.println(bob.toString());
		System.out.println(lame.toString());
		Login login = new Login();
		login.loginMenu();
		*/
		//Controller cont = new Controller();
		//cont.addNewEmployee();
		Login menu = new Login();
		menu.loginMenu();
		
	}
}
