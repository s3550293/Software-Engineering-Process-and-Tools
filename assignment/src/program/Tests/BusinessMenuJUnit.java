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
	public void splitTimeIntoThreeBlocks1()
	{
		String [] times = bMenu.splitTimeIntoThreeBlocks("08:12", "10:00");
		assertEquals(times[0],"08:12");
		assertEquals(times[1],"08:48");
		assertEquals(times[2],"09:24");
		assertEquals(times[3],"10:00");
	}
	@Test
	public void splitTimeIntoThreeBlocks2()
	{
		String [] times = bMenu.splitTimeIntoThreeBlocks("10:00", "10:59");
		assertEquals(times[0],"");
		assertEquals(times[1],"");
		assertEquals(times[2],"");
		assertEquals(times[3],"");
	}
	@Test
	public void splitTimeIntoThreeBlocks3()
	{
		String [] times = bMenu.splitTimeIntoThreeBlocks("11:00", "10:00");
		assertEquals(times[0],"");
		assertEquals(times[1],"");
		assertEquals(times[2],"");
		assertEquals(times[3],"");
	}
	@Test
	public void splitTimeIntoThreeBlocks4()
	{
		String [] times = bMenu.splitTimeIntoThreeBlocks("24:00", "24:30");
		assertEquals(times[0],"");
		assertEquals(times[1],"");
		assertEquals(times[2],"");
		assertEquals(times[3],"");
	}
	@Test
	public void splitTimeIntoThreeBlocks5()
	{
		String [] times = bMenu.splitTimeIntoThreeBlocks("00:10", "00:00");
		assertEquals(times[0],"");
		assertEquals(times[1],"");
		assertEquals(times[2],"");
		assertEquals(times[3],"");
	}
	@Test
	public void splitTimeIntoThreeBlocks6()
	{
		String [] times = bMenu.splitTimeIntoThreeBlocks("16:12", "20:33");
		assertEquals(times[0],"16:12");
		assertEquals(times[1],"17:39");
		assertEquals(times[2],"19:06");
		assertEquals(times[3],"20:33");
	}
	@Test
	public void splitTimeIntoThreeBlocks7()
	{
		String [] times = bMenu.splitTimeIntoThreeBlocks("00:00", "01:00");
		assertEquals(times[0],"00:00");
		assertEquals(times[1],"00:20");
		assertEquals(times[2],"00:40");
		assertEquals(times[3],"01:00");
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
		assertEquals(array[0],"08:00");
		assertEquals(array[1],"20:00");
	}
	@Test
	public void testGetStartEndTimes4()
	{
		String[] array = new String[2];
		array = bMenu.getStartEndTimes(2);
		assertEquals(array[0],"08:00");
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
		assertEquals(array[0],"08:00");
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
