package testCases;
import main_Package.*;
import static org.junit.Assert.*;
import java.sql.*;

import org.junit.*;

public class AddWorkingTimeDates {
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
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Database db = new Database("MockupDB");
		//set up database which contains a set of employee
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void EmployeeExistsTest() {
		//retrieve employeeID for DB when search if the employee exists
	}
	
	

}