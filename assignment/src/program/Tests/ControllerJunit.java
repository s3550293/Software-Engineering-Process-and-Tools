package program.Tests;

import static org.junit.Assert.*;

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
		assertTrue(10000.0 == controller.changeInputIntoValidDouble("10000"));
		assertTrue(10.0 == controller.changeInputIntoValidDouble("10"));
		assertTrue(0.0 == controller.changeInputIntoValidDouble("0"));
		
	}
	@Test
	public void testDateConvertFunc()
	{
		assertEquals("01/01/2012",controller.convertDateToString(controller.convertStringToDate("01/01/2012")));
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
	}

	/*
	@Test
	public void testEmpID() throws SQLException{
		
		assertFalse(employeeIDCheck(123));
		assertTrue(employeeIDCheck(223));
	}
	*/

}