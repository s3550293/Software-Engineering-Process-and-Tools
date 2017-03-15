package testCases;
import main_Package.*;

import static org.junit.Assert.*;
import java.sql.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddWorkingTimeDates {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database db = new Database("MockupDB");
		
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
	public void checkIfEmployeeExists() {
		//
	}

}
