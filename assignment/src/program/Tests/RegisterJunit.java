package program.Tests;

import program.Register;
import static org.junit.Assert.*;
import org.junit.*;
import program.Database;
import program.DatabaseConnection;

public class RegisterJunit
{
	Register reg = new Register();
	Database data = new Database("Test.db");
	DatabaseConnection connect = new DatabaseConnection();
	
	@Before
	public void setup()
	{
		data.createTable("Test.db");
		connect.addUser("TestUser", "123456", 0);
		connect.addUser("Simba", "12345678", 0);
	}
	@Test
	public void test()
	{
		assertTrue(reg.checkPassword("123456"));
		assertTrue(reg.checkPassword("abcdefghi"));
		assertTrue(reg.checkPassword("THEBIRD"));
		assertTrue(reg.checkPassword("WORDwordWORDword"));
		
		assertFalse(reg.checkPassword("1234"));
		assertFalse(reg.checkPassword(""));
		assertFalse(reg.checkPassword(" "));
		
		assertTrue(reg.checkTakenUsername("TestUser"));
		assertTrue(reg.checkTakenUsername("testuser"));
		assertTrue(reg.checkTakenUsername("Simba"));
		assertTrue(reg.checkTakenUsername("simba"));
		assertFalse(reg.checkTakenUsername("Charlie"));
		assertFalse(reg.checkTakenUsername("Ben"));
	}
	@After
	public void after()
	{
		//connect.dropTable("users");
	}
}
