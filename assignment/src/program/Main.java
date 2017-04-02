package program;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

public class Main
{
	public static Logger log = Logger.getLogger(Main.class);
	/*
	 * Log4J
	 * log has multiple different levels when displaying messages
	 * warn > info > debug
	 * 
	 * for example
	 * log.warn("[String]");
	 * log.info("[String]");
	 * log.debug("[String]");
	 * 
	 * setting the level in the main file will dictate which messags will be displayed
	 */
	public static void main(String[] args)
	{
		log.setLevel(Level.DEBUG);
        BasicConfigurator.configure();
        log.info("Program Launch");
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
		
	
		//bo = connect.getOneBooking(6);
		//System.out.println(bo.toString());
		/*
		User lame = connect.getUser("William");
		User bob = connect.getUser("david");
		System.out.println(bob.toString());
		System.out.println(lame.toString());
		Login login = new Login();
		login.loginMenu();
		*/
		Controller cont = new Controller();
		//cont.addNewEmployee();
        cont.checkBooking();
		/*Login menu = new Login();
		menu.loginMenu();*/
		
	}
}
