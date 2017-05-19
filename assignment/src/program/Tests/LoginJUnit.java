package program.Tests;

import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import program.Database;
import program.DatabaseConnection;
import program.Login;

public class LoginJUnit {

	private static Logger log = Logger.getLogger(LoginJUnit.class);
	Login ln = new Login();
	Database data = new Database("Test.db");
	DatabaseConnection connect = new DatabaseConnection();
	
	
	@Before
	public void setUp() throws Exception {
		
		log.setLevel(Level.DEBUG);
        BasicConfigurator.configure();
		// add a new customer
		connect.addUser("c1", "cccc", 0,2);
		// add a new business owner
		connect.addUser("b1", "bbbb", 1,2);
	}

	/*
	 *  0 : Customer
	 *  1 : Business Owner
	 * -1 : Username does not exist
	 * -2 : Incorrect Password
	 * -3 : Empty user name or password
	 */

	@Test
	public void testNonExistingUser(){
		log.debug("user does not exist"); 
		assertEquals((-1),(ln.logInProcess("c0",2, "cccc")));
	}
	
	@Test
	public void testIncorrectPassword(){
		log.debug("user exists but wrong password");
		assertEquals((-2),(ln.logInProcess("c1",2, "bbbb")));
	}
	
	@Test
	public void testCorrectUserAndPass(){
		log.debug("correct username and password for customer");
		assertEquals(0,(ln.logInProcess("c1",2, "cccc")));
				
		log.debug("correct username and password for business owner");
		assertEquals(1,(ln.logInProcess("b1",2, "bbbb")));
	}
	
	@Test
	public void testDeletedUser(){
		connect.deleteUser("c1",2);
		
		log.debug("delete user and log in again, user then no long exists");
		assertEquals((-1),(ln.logInProcess("c1",2, "cccc")));
	}
	
	@Test
	public void testEmptyUsernameAndPass() {
		
		log.debug("username filled but password not filled");
		assertEquals((-3),(ln.logInProcess("fill",2, "")));
		
		log.debug("username is empty but password filled");
		assertEquals((-3),(ln.logInProcess("",2, "fill")));
		
		log.debug("Both username and password are empty");
		assertEquals((-3),(ln.logInProcess("",2, "")));
	}
	
	@After
	public void after()
	{
		connect.dropUser("c1",2);
		connect.dropUser("b1",2);
	}
}
