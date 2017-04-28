package program;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sun.javafx.application.LauncherImpl;

import gui.MainApplication;
import gui.PreLoader;

import org.apache.log4j.BasicConfigurator;

/**
 * The Appointment Booking System program implements an application that Allows
 * a small business owner to manage their employee and client bookings
 *
 * @author Bryan Soh, Panhaseth Heang, Luke Mason, Joseph Garner
 * @version 1.0
 * @since 15/04/2017
 */
public class Main {
	public static Logger log = Logger.getLogger(Main.class);

	/*
	 * Log4J log has multiple different levels when displaying messages warn >
	 * info > debug
	 * 
	 * for example log.warn("[String]"); log.info("[String]");
	 * log.debug("[String]");
	 * 
	 * setting the level in the main file will dictate which messages will be
	 * displayed
	 */
	public static void main(String[] args) {
		/*
		 * Login menu = new Login(); menu.loginMenu();
		 */
		log.setLevel(Level.WARN);
		BasicConfigurator.configure();
		log.info("Program Launch");
		LauncherImpl.launchApplication(MainApplication.class, PreLoader.class, args);
		log.info("Program Close");

	}
}
