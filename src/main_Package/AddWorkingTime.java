package main_Package;
import static org.junit.Assert.*;
import java.sql.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class AddWorkingTime {

	/* Create at least 6 tests 
	 * 
	 * 1. Check if the employee exists - EmployeeExistsTest()
	 * 2. Check Time bounds when assigning time - TimeOutOfBoundTest()
	 * 3. Check Date bounds(one month) when assigning date - DateOutOfBoundTest()
	 * 4. Check Days bounds(days in month) for input - DaysOutOfBoundTest()
	 * 5.
	 * 6.
	 * 
	 */
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database db = new Database("MockupDB");
		db.createTable("MockupDB");
		DatabaseConnection dc = new DatabaseConnection();
		dc.addUser("Panha", "abc", true);
		//true = business owner
		
		dc.getUser("Panha");
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void EmployeeExistsTest() {
		assertEquals(5,5);
	}
}

