package program.Tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import program.Controller;
import program.Database;
import program.DatabaseConnection;
import program.Employee;
public class ControllerJunit {
	
	private static Logger log = Logger.getLogger(Controller.class);
	Controller controller = new Controller();
	DatabaseConnection connect2 = new DatabaseConnection();
	DatabaseConnectionJUnit connection2 = new DatabaseConnectionJUnit();
	@Before
	public void setUp()
	{
		log.info("\n\nSet Up\n--------------\n");
		//Wiping EMPLOYEES table at start of test in case of any changes manually made via SQLite
		try(Connection connect = connection2.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate("DROP TABLE IF EXISTS EMPLOYEES");
			log.info("Dropped Employees Table\n");
			inject.executeUpdate("DROP TABLE IF EXISTS EMPLOYEES_WORKING_TIMES");
			log.info("Dropped 'Working Times' Table\n");
			inject.executeUpdate("DROP TABLE IF EXISTS BOOKINGS");
			log.info("Dropped 'Bookings' Table\n");
		}
		catch(SQLException sqle)
		{
			log.info(sqle.getMessage()+"\n");
		}
		//Creates ALL TABLES
		Database db = new Database("company.db");
		db.createTable("company.db");
		connect2.addEmployee("Test one",25);
		connect2.addEmployee("Test two",43);
		
		connect2.addEmployeeWorkingTime(1,"05/06/2017","08:00","16:00");
		connect2.addEmployeeWorkingTime(1,"06/06/2017","08:00","20:00");
		connect2.addEmployeeWorkingTime(1,"07/06/2017","08:00","16:00");
		connect2.addEmployeeWorkingTime(1,"08/06/2017","12:00","20:00");
		connect2.addEmployeeWorkingTime(1,"09/06/2017","08:00","16:00");
		connect2.addEmployeeWorkingTime(1,"10/06/2017","08:00","20:00");

		connect2.addEmployeeWorkingTime(2,"05/06/2017","08:00","16:00");
		connect2.addEmployeeWorkingTime(2,"06/06/2017","08:00","20:00");
		connect2.addEmployeeWorkingTime(2,"07/06/2017","08:00","16:00");
		connect2.addEmployeeWorkingTime(2,"08/06/2017","12:00","20:00");
		connect2.addEmployeeWorkingTime(2,"09/06/2017","08:00","16:00");
		connect2.addEmployeeWorkingTime(2,"10/06/2017","08:00","20:00");
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
	public void testCheckInputToContainNonAlphabetChar8()
	{
		assertTrue(controller.checkInputToContainInvalidChar("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
	@Test
	public void testCheckInputToContainNonAlphabetChar9()
	{
		assertFalse(controller.checkInputToContainInvalidChar("a"));
	}
	@Test
	public void testCheckInputToContainNonAlphabetChar13()
	{
		assertFalse(controller.checkInputToContainInvalidChar("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
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
	
	@Test
	public void testDateConvertFunc1()
	{
		assertEquals("01/01/2012",controller.dateToStr(controller.strToDate("01/01/2012")));
	}
	@Test
	public void testDateConvertFunc2()
	{
		assertEquals("01/01/2012",controller.dateToStr(controller.strToDate("1/01/2012")));
	}
	@Test
	public void testDateConvertFunc3()
	{
		assertEquals("30/12/2020",controller.dateToStr(controller.strToDate("30/12/2020")));
	}
	@Test
	public void testDateConvertFunc4()
	{
		assertEquals("12/12/2012",controller.dateToStr(controller.strToDate("12/12/2012")));
	}
	@Test
	public void testDateConvertFunc5()
	{
		assertEquals("01:30",controller.timeToStr(controller.strToTime("1:30")));
	}
	@Test
	public void testDateConvertFunc6()
	{
		assertEquals("10:01",controller.timeToStr(controller.strToTime("10:01")));
	}
	@Test
	public void testDateConvertFunc7()
	{
		assertEquals("12:12",controller.timeToStr(controller.strToTime("12:12")));
	}
	@Test
	public void testDateConvertFunc8()
	{
		assertEquals("13:45",controller.timeToStr(controller.strToTime("13:45")));
	}
	@Test
	public void testDateConvertFunc9()
	{
		assertEquals("23:59",controller.timeToStr(controller.strToTime("23:59")));
	}
	@Test
	public void testDateConvertFunc10()
	{
		assertEquals(60 , controller.getTimeDifference(controller.strToTime("1:00"),controller.strToTime("2:00")));
	}
	@Test
	public void testDateConvertFunc11()
	{
		assertEquals(660 , controller.getTimeDifference(controller.strToTime("12:00"),controller.strToTime("23:00")));
	}
	@Test
	public void testDateConvertFunc12()
	{
		assertEquals(120 , controller.getTimeDifference(controller.strToTime("11:00"),controller.strToTime("13:00")));
	}
	@Test
	public void testDateConvertFunc13()
	{
		assertEquals(730 , controller.getDateDifference(controller.strToDate("01/01/01"),controller.strToDate("01/01/03")));
	}
	@Test
	public void testDateConvertFunc14()
	{
		assertEquals(7 , controller.getDateDifference(controller.strToDate("23/12/17"),controller.strToDate("30/12/17")));
	}
	@Test
	public void testDateConvertFunc15()
	{
		assertEquals(2 , controller.getDateDifference(controller.strToDate("01/01/99"),controller.strToDate("03/01/99")));
	}
	@Test
	public void testDateConvertFunc16()
	{
		assertEquals(null,controller.strToDate("a/01/2012"));
	}
	@Test
	public void testDateConvertFunc17()
	{
		assertEquals(null,controller.strToDate("//03/20000"));
	}
	@Test
	public void testDateConvertFunc18()
	{
		assertEquals(null,controller.strToDate("19//2/1001"));
	}
	@Test
	public void testDateConvertFunc19()
	{
		assertEquals(null,controller.strToDate("stupid"));
	}
	@Test
	public void testDateConvertFunc20()
	{
		assertEquals(null,controller.strToTime("01/01/2012"));
	}
	@Test
	public void testDateConvertFunc21()
	{
		assertEquals(null,controller.strToTime("stf:fds"));
	}
	@Test
	public void testDateConvertFunc22()
	{
		assertEquals(null,controller.strToTime("ok:ok"));
	}
	@Test
	public void testDateConvertFunc23()
	{
		assertEquals(null,controller.strToTime("2012"));
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
	@Test
	public void testAllocateWorkTimes9()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("MEA");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "20:00");
	}
	@Test
	public void testAllocateWorkTimes10()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("AME");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "20:00");
	}
	@Test
	public void testAllocateWorkTimes11()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("AEM");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "20:00");
	}
	@Test
	public void testAllocateWorkTimes12()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("EAM");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "20:00");
	}
	@Test
	public void testAllocateWorkTimes13()
	{
		String[] times = new String[2];
		times = controller.allocateWorkTimes("EMA");
		String start = times[0];
		String end = times[1];
		assertEquals(start, "8:00");
		assertEquals(end, "20:00");
	}
	@Test
	public void test1GetTimeFrom1970() throws ParseException
	{		
		Calendar date2 = Calendar.getInstance();
		date2.set(1998+1900, 4, 2, 8, 0);
		date2.set(Calendar.SECOND, 0);
		date2.set(Calendar.MILLISECOND, 0);
		long amount = date2.getTimeInMillis();
		Date date = controller.strToDate("02/04/1998");
		Date time = controller.strToTime("08:00");
		assertEquals(amount,controller.getTimeFrom1970(date, time));
	}
	@Test
	public void test2GetTimeFrom1970() throws ParseException
	{		
		Calendar date2 = Calendar.getInstance();
		date2.set(2005+1900, 6, 22, 16, 0);
		date2.set(Calendar.SECOND, 0);
		date2.set(Calendar.MILLISECOND, 0);
		long amount = date2.getTimeInMillis();
		Date date = controller.strToDate("22/06/2005");
		Date time = controller.strToTime("16:00");
		assertEquals(amount,controller.getTimeFrom1970(date, time));
	}
	@Test
	public void test3GetTimeFrom1970() throws ParseException
	{		
		Calendar date2 = Calendar.getInstance();
		date2.set(2056+1900, 12, 30, 12, 0);
		date2.set(Calendar.SECOND, 0);
		date2.set(Calendar.MILLISECOND, 0);
		long amount = date2.getTimeInMillis();
		Date date = controller.strToDate("30/12/2056");
		Date time = controller.strToTime("12:00");
		assertEquals(amount,controller.getTimeFrom1970(date, time));
	}
	@Test
	public void test4GetTimeFrom1970() throws ParseException
	{		
		Calendar date2 = Calendar.getInstance();
		date2.set(2018+1900, 6, 15, 16, 0);
		date2.set(Calendar.SECOND, 0);
		date2.set(Calendar.MILLISECOND, 0);
		long amount = date2.getTimeInMillis();
		Date date = controller.strToDate("15/06/2018");
		Date time = controller.strToTime("16:00");
		assertEquals(amount,controller.getTimeFrom1970(date, time));
	}
	@Test
	public void test5GetTimeFrom1970() throws ParseException
	{		
		Calendar date2 = Calendar.getInstance();
		date2.set(2018+1900, 1, 15, 20, 0);
		date2.set(Calendar.SECOND, 0);
		date2.set(Calendar.MILLISECOND, 0);
		long amount = date2.getTimeInMillis();
		Date date = controller.strToDate("15/01/2018");
		Date time = controller.strToTime("20:00");
		assertEquals(amount,controller.getTimeFrom1970(date, time));
	}
	@Test
	public void testGetAvailableEmployeesForSpecifiedTime1()
	{
		ArrayList<Employee> employees = new ArrayList<Employee>();
		employees = controller.getAvailableEmployeesForSpecifiedTime("06/06/2017", "15:15", "20:00");
		assertEquals(1,employees.get(0).getId());
		assertEquals(2,employees.get(1).getId());
	}
	@Test
	public void testGetAvailableEmployeesForSpecifiedTime2()
	{
		ArrayList<Employee> employees = new ArrayList<Employee>();
		employees = controller.getAvailableEmployeesForSpecifiedTime("06/06/2017", "15:15", "20:01");
		assertEquals(0,employees.size());
	}
	@Test
	public void testGetAvailableEmployeesForSpecifiedTime3()
	{
		ArrayList<Employee> employees = new ArrayList<Employee>();
		employees = controller.getAvailableEmployeesForSpecifiedTime("06/06/2017", "08:00", "15:00");
		assertEquals(1,employees.get(0).getId());
		assertEquals(2,employees.get(1).getId());
	}
	@Test
	public void testGetAvailableEmployeesForSpecifiedTime4()
	{
		ArrayList<Employee> employees = new ArrayList<Employee>();
		employees = controller.getAvailableEmployeesForSpecifiedTime("06/06/2017", "07:59", "15:00");
		log.debug(employees.size()+"\n");
		assertEquals(0,employees.size());
	}
	@Test
	public void testGetAvailableEmployeesForSpecifiedTime5()
	{
		ArrayList<Employee> employees = new ArrayList<Employee>();
		connect2.addBooking(1,1, "06/06/2017", "08:00", "9:59", 0, "active");
		employees = controller.getAvailableEmployeesForSpecifiedTime("06/06/2017", "10:00", "15:00");
		assertEquals(1,employees.get(0).getId());
		assertEquals(2,employees.get(1).getId());
	}
	@Test
	public void testGetAvailableEmployeesForSpecifiedTime6()
	{
		ArrayList<Employee> employees = new ArrayList<Employee>();
		connect2.addBooking(2,1, "06/06/2017", "08:00", "10:01", 0, "active");
		employees = controller.getAvailableEmployeesForSpecifiedTime("06/06/2017", "10:00", "15:00");
		assertEquals(2,employees.get(0).getId());
		log.debug(employees.size()+"\n");
		assertEquals(1,employees.size());
	}
	@Test
	public void testGetAvailableEmployeesForSpecifiedTime7()
	{
		ArrayList<Employee> employees = new ArrayList<Employee>();
		connect2.addBooking(2,1, "06/06/2017", "14:59", "16:00", 0, "active");
		employees = controller.getAvailableEmployeesForSpecifiedTime("06/06/2017", "10:00", "15:00");
		assertEquals(2,employees.get(0).getId());
		log.debug(employees.size()+"\n");
		assertEquals(1,employees.size());
	}
	@After
	public void tearDown()
	{
		log.info("\n\nTear Down\n--------------\n");
		//Deleting table EMPLOYEES after the Test has been executed correctly
		try(Connection connect = this.connection2.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate("DROP TABLE EMPLOYEES");
			log.info("Dropped Employees Table\n");
			inject.executeUpdate("DROP TABLE EMPLOYEES_WORKING_TIMES");
			log.info("Dropped 'Working Times' Table\n");
			inject.executeUpdate("DROP TABLE IF EXISTS BOOKINGS");
			log.info("Dropped 'Bookings' Table\n");
		}
		catch(SQLException sqle)
		{
			log.info(sqle.getMessage()+"\n");
		}
	}

}