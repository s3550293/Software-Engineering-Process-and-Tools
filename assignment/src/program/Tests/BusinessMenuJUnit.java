package program.Tests;
import program.BusinessMenu;


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
		assertFalse(true == bMenu.checkEmployeeFirstOrLastName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
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
	@Test
	public void testSet7DayRosterTime1()
	{
		assertTrue(bMenu.set7DayRosterTime(57,"02/12/2017", "8:00", "16:00"));
	}
	@Test
	public void testSet7DayRosterTime2()
	{
		assertFalse(bMenu.set7DayRosterTime(57,"lol/04/2017", "8:00", "16:00"));
	}
	@Test
	public void testSet7DayRosterTime3()
	{
		assertFalse(bMenu.set7DayRosterTime(57,"", "8:00", "16:00"));
	}
	@Test
	public void testCheckWorkTimes1()
	{
		assertEquals(1,bMenu.getWorkTimes(true,true,true));
	}
	@Test
	public void testCheckWorkTimes2()
	{
		assertEquals(2,bMenu.getWorkTimes(true,true,false));
	}
	@Test
	public void testCheckWorkTimes3()
	{
		assertEquals(3,bMenu.getWorkTimes(false,true,true));
	}
	@Test
	public void testCheckWorkTimes4()
	{
		assertEquals(4,bMenu.getWorkTimes(true,false,false));
	}
	@Test
	public void testCheckWorkTimes5()
	{
		assertEquals(5,bMenu.getWorkTimes(false,true,false));
	}
	@Test
	public void testCheckWorkTimes6()
	{
		assertEquals(6,bMenu.getWorkTimes(false,false,true));
	}
	@Test
	public void testCheckWorkTimes7()
	{
		assertEquals(-1,bMenu.getWorkTimes(true,false,true));
	}
	@Test
	public void testCheckWorkTimes8()
	{
		assertEquals(0,bMenu.getWorkTimes(false,false,false));
	}
	@Test
	public void testGetStartEndTimes0()
	{
		String[] array = new String[2];
		array = bMenu.getStartEndTimes(-1);
		assertEquals(array[0],"");
		assertEquals(array[1],"");
	}
	@Test
	public void testGetStartEndTimes1()
	{
		String[] array = new String[2];
		array = bMenu.getStartEndTimes(7);
		assertEquals(array[0],"");
		assertEquals(array[1],"");
	}
	@Test
	public void testGetStartEndTimes2()
	{
		String[] array = new String[2];
		array = bMenu.getStartEndTimes(0);
		assertEquals(array[0],"");
		assertEquals(array[1],"");
	}
	@Test
	public void testGetStartEndTimes3()
	{
		String[] array = new String[2];
		array = bMenu.getStartEndTimes(1);
		assertEquals(array[0],"8:00");
		assertEquals(array[1],"20:00");
	}
	@Test
	public void testGetStartEndTimes4()
	{
		String[] array = new String[2];
		array = bMenu.getStartEndTimes(2);
		assertEquals(array[0],"8:00");
		assertEquals(array[1],"16:00");
	}
	@Test
	public void testGetStartEndTimes5()
	{
		String[] array = new String[2];
		array = bMenu.getStartEndTimes(3);
		assertEquals(array[0],"12:00");
		assertEquals(array[1],"20:00");
	}
	@Test
	public void testGetStartEndTimes6()
	{
		String[] array = new String[2];
		array = bMenu.getStartEndTimes(4);
		assertEquals(array[0],"8:00");
		assertEquals(array[1],"12:00");
	}
	@Test
	public void testGetStartEndTimes7()
	{
		String[] array = new String[2];
		array = bMenu.getStartEndTimes(5);
		assertEquals(array[0],"12:00");
		assertEquals(array[1],"16:00");
	}
	@Test
	public void testGetStartEndTimes8()
	{
		String[] array = new String[2];
		array = bMenu.getStartEndTimes(6);
		assertEquals(array[0],"16:00");
		assertEquals(array[1],"20:00");
	}
}
