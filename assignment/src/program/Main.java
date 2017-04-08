package program;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
/**
* The Appointment Booking System program implements an application that
* Allows a small business owner to manage their employee and client bookings
*
* @author  Bryan Soh, Panhaseth Heang, Luke Mason, Joseph Garner
* @version 0.5
* @since   09/04/2017
*/
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
         * This code will create the directory where the database will be stored when the user runs the jar
         */
        /*
        File varTmpDir = new File(System.getProperty("user.home")+"/resourcing");
        if(varTmpDir.exists() == false) {
            new File(System.getProperty("user.home")+"/resourcing").mkdir();
        }
		*/
		Login menu = new Login();
		menu.loginMenu();
		
	}
}
