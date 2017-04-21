package program.Tests;
import program.BusinessMenu;
import program.Controller;
import program.DatabaseConnection;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BusinessMenuJUnit {
	BusinessMenu bMenu = new BusinessMenu();
	@Before
	public void setUp()
	{
		
	}
	@Test
	public void testCheckEmployeeFirstOrLastName1()
	{
		assertFalse(bMenu.checkEmployeeFirstOrLastName(""));
	}
	@Test
	public void testCheckEmployeeFirstOrLastName2()
	{
		assertFalse(bMenu.checkEmployeeFirstOrLastName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
	@Test
	public void testCheckEmployeeFirstOrLastName3()
	{
		assertTrue(bMenu.checkEmployeeFirstOrLastName("a"));
	}
	@Test
	public void testCheckEmployeeFirstOrLastName4()
	{
		assertTrue(bMenu.checkEmployeeFirstOrLastName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
	@Test
	public void testCheckEmployeePayRate1()
	{
		assertFalse(bMenu.checkEmployeePayRate(-1));
	}
	@Test
	public void testCheckEmployeePayRate2()
	{
		assertTrue(bMenu.checkEmployeePayRate(0));
	}
}
