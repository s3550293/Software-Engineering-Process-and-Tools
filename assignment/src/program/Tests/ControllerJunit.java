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
	public void testCheckInputToContainNonAlphabetChar1() 
	{
		assertFalse(controller.checkInputToContainInvalidChar("Luke"));
	}
	@Test
	public void testCheckInputToContainNonAlphabetChar2() 
	{
		assertFalse(controller.checkInputToContainInvalidChar("LukeyyyMason"));
	}
	@Test
	public void testCheckInputToContainNonAlphabetChar3() 
	{
		assertTrue(controller.checkInputToContainInvalidChar(""));
	}
	@Test
	public void testCheckInputToContainNonAlphabetChar4() 
	{
		assertTrue(controller.checkInputToContainInvalidChar(" "));
	}
	@Test
	public void testCheckInputToContainNonAlphabetChar5() 
	{
		assertTrue(controller.checkInputToContainInvalidChar("1010101LUKE"));
	}
	@Test
	public void testCheckInputToContainNonAlphabetChar6() 
	{
		assertTrue(controller.checkInputToContainInvalidChar("LUKEEEEEEEEEEEEEEEEEEEEEEEEEEE                                        "));
	}
	@Test
	public void testCheckInputToContainNonAlphabetChar7() 
	{
		assertTrue(controller.checkInputToContainInvalidChar("luke%@#$"));
	}
	@Test
	public void testChangeInputIntoValidDouble() 
	{
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("..0"));
	}
	@Test
	public void testChangeInputIntoValidDouble2() 
	{
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("0.."));
	}
	@Test
	public void testChangeInputIntoValidDouble3() 
	{
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("5..0"));
	}
	@Test
	public void testChangeInputIntoValidDouble4() 
	{
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("5.3.2"));
	}
	@Test
	public void testChangeInputIntoValidDouble5() 
	{
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("..532"));
	}
	@Test
	public void testChangeInputIntoValidDouble6() 
	{
		assertTrue(-1.0 == controller.changeInputIntoValidDouble(""));
	}
	@Test
	public void testChangeInputIntoValidDouble7() 
	{
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("lel"));
	}
	@Test
	public void testChangeInputIntoValidDouble8() 
	{
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("$"));
	}
	@Test
	public void testChangeInputIntoValidDouble9() 
	{
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("100$"));
	}
	@Test
	public void testChangeInputIntoValidDouble10() 
	{
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("-1"));
	}
	@Test
	public void testChangeInputIntoValidDouble11() 
	{
		assertTrue(-1.0 == controller.changeInputIntoValidDouble("10001"));
	}
	@Test
	public void testChangeInputIntoValidDouble12() 
	{
		assertTrue(0.0 == controller.changeInputIntoValidDouble("0."));
	}
	@Test
	public void testChangeInputIntoValidDouble13() 
	{
		assertTrue(0.0 == controller.changeInputIntoValidDouble(".0"));
	}
	@Test
	public void testChangeInputIntoValidDouble14() 
	{
		assertTrue(1000.0 == controller.changeInputIntoValidDouble("1000"));
	}
	@Test
	public void testChangeInputIntoValidDouble15() 
	{
		assertTrue(10.0 == controller.changeInputIntoValidDouble("10"));
	}
	@Test
	public void testChangeInputIntoValidDouble16() 
	{
		assertTrue(0.0 == controller.changeInputIntoValidDouble("0"));
	}
	@Test
	public void testChangeInputIntoValidInt1() 
	{
		assertTrue(-1 == controller.changeInputIntoValidInt("..0"));
	}
	@Test
	public void testChangeInputIntoValidInt2() 
	{
		assertTrue(-1 == controller.changeInputIntoValidInt("0.."));
	}
	@Test
	public void testChangeInputIntoValidInt3() 
	{
		assertTrue(-1 == controller.changeInputIntoValidInt("5..0"));
	}
	@Test
	public void testChangeInputIntoValidInt4() 
	{
		assertTrue(-1 == controller.changeInputIntoValidInt("5.3.2"));
	}
	@Test
	public void testChangeInputIntoValidInt5() 
	{
		assertTrue(-1 == controller.changeInputIntoValidInt("..532"));
	}
	@Test
	public void testChangeInputIntoValidInt6() 
	{
		assertTrue(-1 == controller.changeInputIntoValidInt(""));
	}
	@Test
	public void testChangeInputIntoValidInt7() 
	{
		assertTrue(-1 == controller.changeInputIntoValidInt("lel"));
	}
	@Test
	public void testChangeInputIntoValidInt8() 
	{
		assertTrue(-1 == controller.changeInputIntoValidInt("$"));
	}
	@Test
	public void testChangeInputIntoValidInt9() 
	{
		assertTrue(-1 == controller.changeInputIntoValidInt("100$"));
	}
	@Test
	public void testChangeInputIntoValidInt10() 
	{
		assertTrue(-1 == controller.changeInputIntoValidInt("-1"));
	}
	@Test
	public void testChangeInputIntoValidInt11() 
	{
		assertTrue(-1 == controller.changeInputIntoValidInt("0."));
	}
	@Test
	public void testChangeInputIntoValidInt12() 
	{
		assertTrue(-1 == controller.changeInputIntoValidInt(".0"));
	}
	@Test
	public void testChangeInputIntoValidInt13() 
	{
		assertTrue(10000 == controller.changeInputIntoValidInt("10000"));
	}
	@Test
	public void testChangeInputIntoValidInt14() 
	{
		assertTrue(10 == controller.changeInputIntoValidInt("10"));
	}
	@Test
	public void testChangeInputIntoValidInt15() 
	{
		assertTrue(0 == controller.changeInputIntoValidInt("0"));
	}
	/*@Test
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
		
	}*/
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
	@Test
	public void testCheckWorkTimeChoice1()
	{
		assertTrue(controller.checkWorkTimeChoice("MAE"));
	}
	@Test
	public void testCheckWorkTimeChoice2()
	{
		assertTrue(controller.checkWorkTimeChoice("MEA"));
	}
	@Test
	public void testCheckWorkTimeChoice3()
	{
		assertTrue(controller.checkWorkTimeChoice("AME"));
	}
	@Test
	public void testCheckWorkTimeChoice4()
	{
		assertTrue(controller.checkWorkTimeChoice("AEM"));
	}
	@Test
	public void testCheckWorkTimeChoice5()
	{
		assertTrue(controller.checkWorkTimeChoice("EAM"));
	}
	@Test
	public void testCheckWorkTimeChoice6()
	{
		assertTrue(controller.checkWorkTimeChoice("EMA"));
	}
	@Test
	public void testCheckWorkTimeChoice7()
	{
		assertTrue(controller.checkWorkTimeChoice("MA"));
	}
	@Test
	public void testCheckWorkTimeChoice8()
	{
		assertFalse(controller.checkWorkTimeChoice("ME"));
	}
	@Test
	public void testCheckWorkTimeChoice9()
	{
		assertTrue(controller.checkWorkTimeChoice("AM"));
	}
	@Test
	public void testCheckWorkTimeChoice10()
	{
		assertTrue(controller.checkWorkTimeChoice("AE"));
	}
	@Test
	public void testCheckWorkTimeChoice11()
	{
		assertTrue(controller.checkWorkTimeChoice("EA"));
	}
	@Test
	public void testCheckWorkTimeChoice12()
	{
		assertFalse(controller.checkWorkTimeChoice("EM"));
	}
	@Test
	public void testCheckWorkTimeChoice13()
	{
		assertTrue(controller.checkWorkTimeChoice("M"));
	}
	@Test
	public void testCheckWorkTimeChoice14()
	{
		assertTrue(controller.checkWorkTimeChoice("A"));
	}
	@Test
	public void testCheckWorkTimeChoice15()
	{
		assertTrue(controller.checkWorkTimeChoice("E"));
	}
	@Test
	public void testCheckWorkTimeChoice16()
	{
		assertFalse(controller.checkWorkTimeChoice("MM"));
	}
	@Test
	public void testCheckWorkTimeChoice17()
	{
		assertFalse(controller.checkWorkTimeChoice("EE"));
	}
	@Test
	public void testCheckWorkTimeChoice18()
	{
		assertFalse(controller.checkWorkTimeChoice("AA"));
	}
	@Test
	public void testCheckWorkTimeChoice19()
	{
		assertFalse(controller.checkWorkTimeChoice("MAM"));
	}
	@Test
	public void testCheckWorkTimeChoice20()
	{
		assertFalse(controller.checkWorkTimeChoice("AMA"));
	}
	@Test
	public void testCheckWorkTimeChoice21()
	{
		assertFalse(controller.checkWorkTimeChoice("EEA"));
	}
	@Test
	public void testCheckWorkTimeChoice22()
	{
		assertFalse(controller.checkWorkTimeChoice("MMMM"));
	}
	@Test
	public void testCheckWorkTimeChoice23()
	{
		assertFalse(controller.checkWorkTimeChoice(""));
	}
	@Test
	public void testCheckWorkTimeChoice26()
	{
		assertFalse(controller.checkWorkTimeChoice("f"));
	}
	@Test
	public void testCheckWorkTimeChoice27()
	{
		assertFalse(controller.checkWorkTimeChoice("$"));
	}
	@Test
	public void testCheckWorkTimeChoice28()
	{
		assertFalse(controller.checkWorkTimeChoice("ASE"));
	}
	@Test
	public void testCheckWorkTimeChoice29()
	{
		assertFalse(controller.checkWorkTimeChoice("M.A"));
	}
	@Test
	public void testAllocateWorkTimes1()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("M");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "12:00");
	}
	@Test
	public void testAllocateWorkTimes2()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("A");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "12:00");
		assertEquals(end, "16:00");
	}
	@Test
	public void testAllocateWorkTimes3()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("E");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "16:00");
		assertEquals(end, "20:00");
	}
	@Test
	public void testAllocateWorkTimes4()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("MA");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "16:00");
	}
	@Test
	public void testAllocateWorkTimes5()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("AM");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "16:00");
	}
	@Test
	public void testAllocateWorkTimes6()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("AE");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "12:00");
		assertEquals(end, "20:00");
	}
	@Test
	public void testAllocateWorkTimes7()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("EA");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "12:00");
		assertEquals(end, "20:00");
	}
	@Test
	public void testAllocateWorkTimes8()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("MAE");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "20:00");
	}
	public void testAllocateWorkTimes9()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("MEA");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "20:00");
	}
	public void testAllocateWorkTimes10()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("AME");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "20:00");
	}
	public void testAllocateWorkTimes11()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("AEM");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "20:00");
	}
	public void testAllocateWorkTimes12()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("EAM");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "20:00");
	}
	public void testAllocateWorkTimes13()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("EMA");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "20:00");
	}
	/*
	@Test
	public void testEmpID() throws SQLException{
		
		assertFalse(employeeIDCheck(123));
		assertTrue(employeeIDCheck(223));
	}
	*/

}