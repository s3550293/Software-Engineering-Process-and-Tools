package program.Tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import program.Controller;
public class ControllerJunit {
	
	Controller controller = new Controller();
	
	@Before
	public void setUp()
	{

	}
	@Test
	public void testCheckInputToContainNonAlphabetChar() 
	{
		assertFalse(controller.checkInputToContainInvalidChar("Luke Mason"));
		assertFalse(controller.checkInputToContainInvalidChar("LukeyyyMason"));
		
		assertTrue(controller.checkInputToContainInvalidChar(""));
		assertTrue(controller.checkInputToContainInvalidChar("1010101LUKE"));
		assertTrue(controller.checkInputToContainInvalidChar("LUKEEEEEEEEEEEEEEEEEEEEEEEEEEE                                        "));
		assertTrue(controller.checkInputToContainInvalidChar("luke%@#$"));
	}

	@Test
	public void testChangeInputIntoValidDouble() 
	{
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("..0"));
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("0.."));
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("5..0"));
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("5.3.2"));
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("..532"));
		assertTrue(-1.0 == controller.changeInputIntoValidDouble(""));
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("lel"));
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("$"));
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("100$"));
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("-1"));
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("10001"));
		
		assertTrue(0.0 == controller.changeInputIntoValidDouble("0."));
		assertTrue(0.0 == controller.changeInputIntoValidDouble(".0"));
		assertTrue(1000.0 == controller.changeInputIntoValidDouble("1000"));
		assertTrue(10.0 == controller.changeInputIntoValidDouble("10"));
		assertTrue(0.0 == controller.changeInputIntoValidDouble("0"));
		
	}
	@Test
	public void testChangeInputIntoValidInt()
	{
		assertTrue(-1 == controller.changeInputIntoValidInt("..0"));
		assertTrue(-1 == controller.changeInputIntoValidInt("0.."));
		assertTrue(-1 == controller.changeInputIntoValidInt("5..0"));
		assertTrue(-1 == controller.changeInputIntoValidInt("5.3.2"));
		assertTrue(-1 == controller.changeInputIntoValidInt("..532"));
		assertTrue(-1 == controller.changeInputIntoValidInt(""));
		assertTrue(-1 == controller.changeInputIntoValidInt("lel"));
		assertTrue(-1 == controller.changeInputIntoValidInt("$"));
		assertTrue(-1 == controller.changeInputIntoValidInt("100$"));
		assertTrue(-1 == controller.changeInputIntoValidInt("-1"));
		
		assertTrue(0 == controller.changeInputIntoValidInt("0."));
		assertTrue(0 == controller.changeInputIntoValidInt(".0"));
		assertTrue(10000 == controller.changeInputIntoValidInt("10000"));
		assertTrue(10 == controller.changeInputIntoValidInt("10"));
		assertTrue(0 == controller.changeInputIntoValidInt("0"));
	}
	@Test
	public void testCheckNewDate()
	{
		assertFalse(controller.checkNewDate("lolol"));
		
		//Calculating the date 29 days from now
		Calendar c=new GregorianCalendar();
		Date x =c.getTime();
		System.out.println(x);
		String date = controller.convertDateToString(x);
		assertTrue(controller.checkNewDate(date));
				
		//Calculating the date 29 days from now
		c.add(Calendar.DATE, 29);
		x =c.getTime();
		date = controller.convertDateToString(x);
		assertTrue(controller.checkNewDate(date));
		
		//Calculating the date 31 days from now
		c.add(Calendar.DATE, 31);
		x =c.getTime();
		date = controller.convertDateToString(x);
		assertFalse(controller.checkNewDate(date));
	}
	@Test
	public void testDateConvertFunc()
	{
		assertEquals("01/01/12",controller.convertDateToString(controller.convertStringToDate("01/01/12")));
		assertEquals("30/12/20",controller.convertDateToString(controller.convertStringToDate("30/12/20")));
		assertEquals("12/12/12",controller.convertDateToString(controller.convertStringToDate("12/12/12")));
		
		assertEquals("01:30",controller.convertTimeToString(controller.convertStringToTime("1:30")));
		assertEquals("10:01",controller.convertTimeToString(controller.convertStringToTime("10:01")));
		assertEquals("12:12",controller.convertTimeToString(controller.convertStringToTime("12:12")));
		assertEquals("13:45",controller.convertTimeToString(controller.convertStringToTime("13:45")));
		assertEquals("23:59",controller.convertTimeToString(controller.convertStringToTime("23:59")));

		assertEquals(1 , controller.getTimeDifference(controller.convertStringToTime("1:00"),controller.convertStringToTime("2:00")));
		assertEquals(11 , controller.getTimeDifference(controller.convertStringToTime("12:00"),controller.convertStringToTime("23:00")));
		assertEquals(2 , controller.getTimeDifference(controller.convertStringToTime("11:00"),controller.convertStringToTime("13:00")));
		
		assertEquals(730 , controller.getDateDifference(controller.convertStringToDate("01/01/01"),controller.convertStringToDate("01/01/03")));
		assertEquals(7 , controller.getDateDifference(controller.convertStringToDate("23/12/17"),controller.convertStringToDate("30/12/17")));
		assertEquals(2 , controller.getDateDifference(controller.convertStringToDate("01/01/99"),controller.convertStringToDate("03/01/99")));
	}

	/*
	@Test
	public void testEmpID() throws SQLException{
		
		assertFalse(employeeIDCheck(123));
		assertTrue(employeeIDCheck(223));
	}
	*/

}