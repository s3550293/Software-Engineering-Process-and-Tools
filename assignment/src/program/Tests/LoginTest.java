package program.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import program.Database;
import program.DatabaseConnection;
import program.Login;

public class LoginTest {

	Login ln = new Login();
	Database data = new Database("Test.db");
	DatabaseConnection connect = new DatabaseConnection();
	
	
	@Before
	public void setUp() throws Exception {
		
		
		data.createTable("Test.db");
		// add a new customer
		connect.addUser("c1", "cccc", 0);
		// add a new business owner
		connect.addUser("b1", "bbbb", 1);
	}


	@Test
	public void test() {
		
		/*
		 *  0 : Customer
		 *  1 : Business Owner
		 * -1 : Username does not exist
		 * -2 : Incorrect Password
		 */
		
		// wrong username and existing password
		assertEquals((-1),(ln.logInProcess("c0", "cccc")));
		
		// wrong username and password
		assertEquals((-2),(ln.logInProcess("c1", "bbbb")));
		
		// correct username and password for customer
		assertEquals(0,(ln.logInProcess("c1", "cccc")));
		
		// correct username and password for business owner
		assertEquals(1,(ln.logInProcess("b1", "bbbb")));
		
		connect.deleteUser("c1");
		
		// delete user and log in again
		assertEquals((-1),(ln.logInProcess("c1", "cccc")));
		
		// username filled but password not filled
		assertEquals((-3),(ln.logInProcess("fill", "")));
		
		// username is empty but pass filled
		assertEquals((-3),(ln.logInProcess("", "fill")));
		
		// all inputs are empty
		assertEquals((-3),(ln.logInProcess("", "")));

	}
	@After
	public void after()
	{
		/*
		connect.dropTable("users");
		connect.dropTable("clientdetails");
		connect.dropTable("EMPLOYEES");
		connect.dropTable("EMPLOYEES_WORKING_TIMES");
		*/
	}
}
