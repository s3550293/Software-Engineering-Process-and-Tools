package program.Tests;

import program.Register;
import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.*;
import program.Database;
import program.DatabaseConnection;
import program.Main;

public class RegisterJunit
{
	private static Logger log = Logger.getLogger(RegisterJunit.class);
	Register reg = new Register();
	DatabaseConnection connect = new DatabaseConnection();
	@BeforeClass
	public static void ini()
	{
		log.setLevel(Level.DEBUG);
        BasicConfigurator.configure();
        log.info("Program Launch");
	}
	
	@Before
	public void setup()
	{
		connect.addUser("TestUser", "123456", 0);
	}
	@Test
	public void testPass1()
	{
		log.debug("Password Test 1");
		assertFalse(reg.checkPassword("123456"));
	}
	
	@Test
	public void testPass2()
	{
		log.debug("Password Test 2");
		assertFalse(reg.checkPassword("abcdefghi"));
	}

	@Test
	public void testPass3()
	{
		log.debug("Password Test 3");
		assertFalse(reg.checkPassword("WORDwordWORDword"));
	}
	
	@Test
	public void testPass4()
	{
		log.debug("Password Test 4");
		assertTrue(reg.checkPassword("Monday10"));
	}
	
	@Test
	public void testPass5()
	{
		log.debug("Password Test 5");
		assertFalse(reg.checkPassword(""));
	}
	
	@Test
	public void testUname1()
	{
		log.debug("Uname Test 1");
		assertTrue(reg.checkTakenUsername("TestUser"));
	}
	
	@Test
	public void testUname2()
	{
		log.debug("Uname Test 2");
		assertTrue(reg.checkTakenUsername("testuser"));
	}

	@Test
	public void testUname3()
	{
		log.debug("Uname Test 3");
		assertFalse(reg.checkTakenUsername("Charlie21"));
	}
	@After
	public void teardown()
	{
		connect.dropUser("TestUser");
	}
}
