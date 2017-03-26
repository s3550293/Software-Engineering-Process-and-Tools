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
		assertFalse(controller.checkInputToContainInvalidChar("Luke"));
		assertFalse(controller.checkInputToContainInvalidChar("LukeyyyMason"));
		
		assertTrue(controller.checkInputToContainInvalidChar(""));
		assertTrue(controller.checkInputToContainInvalidChar(" "));
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
		assertTrue(-1 == controller.changeInputIntoValidInt("0."));
		assertTrue(-1 == controller.changeInputIntoValidInt(".0"));
		
		assertTrue(10000 == controller.changeInputIntoValidInt("10000"));
		assertTrue(10 == controller.changeInputIntoValidInt("10"));
		assertTrue(0 == controller.changeInputIntoValidInt("0"));	
	}
	@Test
	public void testCheckNewDate()
	{
		Date date;
		Calendar a=new GregorianCalendar();
		a.add(Calendar.DATE, 28);
		date =a.getTime();
		assertTrue(controller.checkNewDate(date));
		
		Calendar b=new GregorianCalendar();
		b.add(Calendar.DATE, 28);
		date =b.getTime();
		assertTrue(controller.checkNewDate(date));
		
		Calendar c=new GregorianCalendar();
		c.add(Calendar.DATE, 29);
		date =c.getTime();
		assertTrue(controller.checkNewDate(date));
		
		Calendar d =new GregorianCalendar();
		d.add(Calendar.DATE, 30);
		date =d.getTime();
		assertTrue(controller.checkNewDate(date));
		
		Calendar e =new GregorianCalendar();
		e.add(Calendar.DATE, 31);
		date =e.getTime();
		assertFalse(controller.checkNewDate(date));
		
	}
	@Test
	public void testDateConvertFunc()
	{
		assertEquals("01/01/2012",controller.convertDateToString(controller.convertStringToDate("01/01/2012")));
		assertEquals("01/01/2012",controller.convertDateToString(controller.convertStringToDate("1/01/2012")));
		assertEquals("30/12/2020",controller.convertDateToString(controller.convertStringToDate("30/12/2020")));
		assertEquals("12/12/2012",controller.convertDateToString(controller.convertStringToDate("12/12/2012")));
		
		assertEquals("01:30",controller.convertTimeToString(controller.convertStringToTime("1:30")));
		assertEquals("10:01",controller.convertTimeToString(controller.convertStringToTime("10:01")));
		assertEquals("12:12",controller.convertTimeToString(controller.convertStringToTime("12:12")));
		assertEquals("13:45",controller.convertTimeToString(controller.convertStringToTime("13:45")));
		assertEquals("23:59",controller.convertTimeToString(controller.convertStringToTime("23:59")));
		
		assertEquals(60 , controller.getTimeDifference(controller.convertStringToTime("1:00"),controller.convertStringToTime("2:00")));
		assertEquals(660 , controller.getTimeDifference(controller.convertStringToTime("12:00"),controller.convertStringToTime("23:00")));
		assertEquals(120 , controller.getTimeDifference(controller.convertStringToTime("11:00"),controller.convertStringToTime("13:00")));
		
		assertEquals(730 , controller.getDateDifference(controller.convertStringToDate("01/01/01"),controller.convertStringToDate("01/01/03")));
		assertEquals(7 , controller.getDateDifference(controller.convertStringToDate("23/12/17"),controller.convertStringToDate("30/12/17")));
		assertEquals(2 , controller.getDateDifference(controller.convertStringToDate("01/01/99"),controller.convertStringToDate("03/01/99")));
		
		assertEquals(null,controller.convertStringToDate("a/01/2012"));
		assertEquals(null,controller.convertStringToDate("//03/20000"));
		assertEquals(null,controller.convertStringToDate("19//2/1001"));
		assertEquals(null,controller.convertStringToDate("stupid"));
		
		assertEquals(null,controller.convertStringToTime("01/01/2012"));
		assertEquals(null,controller.convertStringToTime("stf:fds"));
		assertEquals(null,controller.convertStringToTime("ok:ok"));
		assertEquals(null,controller.convertStringToTime("2012"));
		
	}

	/*
	@Test
	public void testEmpID() throws SQLException{
		
		assertFalse(employeeIDCheck(123));
		assertTrue(employeeIDCheck(223));
	}
	*/

}