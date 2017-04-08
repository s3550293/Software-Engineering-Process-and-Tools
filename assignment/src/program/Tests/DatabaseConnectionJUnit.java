package program.Tests;

import program.Booking;
import program.Controller;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.DriverManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import program.Database;
import program.DatabaseConnection;
import program.Employee;
import program.EmployeeWorkingTime;

public class DatabaseConnectionJUnit {
	
	Controller controller = new Controller();
	private Connection connect()
	{
		/*
		 * creates a connection to the database to be used multiple times in the class
		 */
		String url = "jdbc:sqlite:db/company.db";
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(url);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return connect;
	}
	/*
	 * OutDated Code
	public int countEmployeesInArray(Employee[] employees)
	{
		int counter = 0;
		for (int i = 0; i < employees.length; i ++)
		{
		    if (employees[i] != null)
		    {
		        counter ++;
		    }
		}
		return counter;
	}
	public int countWorkTimesInArray(EmployeeWorkingTime[] employeeWorkingTime)
	{
		int counter = 0;
		for (int i = 0; i < employeeWorkingTime.length; i ++)
		{
		    if (employeeWorkingTime[i] != null)
		    {
		        counter ++;
		    }
		}
		return counter;
	}
	*/
	DatabaseConnection connect = new DatabaseConnection("company.db");
	
	@Before
	public void setUp()
	{
		System.out.println("\n\nSet Up\n--------------");
		//Wiping EMPLOYEES table at start of test in case of any changes manually made via SQLite
		try(Connection connect = connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate("DROP TABLE IF EXISTS EMPLOYEES");
			System.out.println("Dropped Employees Table");
			inject.executeUpdate("DROP TABLE IF EXISTS EMPLOYEES_WORKING_TIMES");
			System.out.println("Dropped 'Working Times' Table");
			inject.executeUpdate("DROP TABLE IF EXISTS BOOKINGS");
			System.out.println("Dropped 'Bookings' Table");
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
		//Creates ALL TABLES
		Database db = new Database("company.db");
		db.createTable("company.db");
		connect.addEmployee("Luke Mason", 1000);//Creating a set of employees
		connect.addEmployee("Luke Boi", 24.57);
		connect.addEmployee("Mason Smith", 24.57);
		connect.addEmployee("Jane Smith", 24.57);
		connect.addEmployeeWorkingTime(1,"03/12/2017","9:50","17:25");//Assigning employee Luke with 2 working times
		connect.addEmployeeWorkingTime(1,"02/03/2017","8:30","14:30");
		connect.addBooking(1, "04/04/2017", "10:30", "12:00", 0,"active");
		connect.addBooking(2, "04/05/2017", "11:30", "12:30", 0,"active");
		connect.addBooking(3, "05/04/2017", "10:30", "12:00", 0,"active");
	}
	@Test
	public void testTestEmployeeAttributesLuke1()
	{
		Employee Luke_Mason = connect.getEmployee(1);
		assertTrue(Luke_Mason.getId()==1);
	}
	public void testTestEmployeeAttributesLuke2()
	{
		Employee Luke_Mason = connect.getEmployee(1);
		assertTrue(Luke_Mason.getPayRate()== 1000);
	}
	@Test
	public void testTestEmployeeAttributesLuke3()
	{
		Employee Luke_Mason = connect.getEmployee(1);
		assertTrue(Luke_Mason.getName().equals("Luke Mason"));
	}
	@Test
	public void testTestEmployeeAttributesLuke4()
	{
		Employee Luke_Mason = connect.getEmployee(1);
		assertTrue(Luke_Mason.toString().equals("ID: 1   Name: Luke Mason   Pay Rate: $1000.0"));
	}
	@Test//Testing the output of getting employees that do not exist  E.G ID 2
	public void testTestNonExistentEmployeeAttributesJacob1()
	{
		Employee Jacob_Boehm =connect.getEmployee(20);
		assertTrue(Jacob_Boehm.getId()==0);
	}
	@Test
	public void testTestNonExistentEmployeeAttributesJacob2()
	{
		Employee Jacob_Boehm =connect.getEmployee(20);
		assertTrue(Jacob_Boehm.getName().equals("Employee does not exist"));
	}
	@Test
	public void testTestNonExistentEmployeeAttributesJacob3()
	{
		Employee Jacob_Boehm =connect.getEmployee(20);
		assertTrue(Jacob_Boehm.toString().equals("Sorry, Employees with that ID do not exist"));
	}
	
	@Test
	public void testGetEmployees1()
	{
		System.out.println("\n\nGetEmployees\n------------------------------------------------------------------------");
		ArrayList<Employee> employees = connect.getEmployees("Smith");
		assertTrue(employees.size() == 2);
	}
	@Test
	public void testGetEmployees2()
	{
		ArrayList<Employee> employees = connect.getEmployees("Mason");
		assertTrue(employees.size() == 2);
	}
	@Test
	public void testGetEmployees3()
	{
		ArrayList<Employee> employees = connect.getEmployees("Mason");
		Employee emp = new Employee();
		for(Employee e: employees)
		{
			if(e.getName().equals("Luke Mason"))
				emp = e;
		}
		assertTrue(emp.getId()== 1);
	}
	@Test
	public void testGetEmployees4()
	{
		ArrayList<Employee> employees = connect.getEmployees("Mason");
		Employee emp = new Employee();
		for(Employee e: employees)
		{
			if(e.getName().equals("Luke Mason"))
				emp = e;
		}
		assertTrue(emp.getName().equals("Luke Mason"));
	}
	@Test
	public void testGetEmployees5()
	{
		ArrayList<Employee> employees = connect.getEmployees("Mason");
		Employee emp = new Employee();
		for(Employee e: employees)
		{
			if(e.getName().equals("Luke Mason"))
				emp = e;
		}
		assertTrue(emp.getPayRate()== 1000);
	}
	@Test
	public void testGetEmployees6()
	{
		ArrayList<Employee> employees = connect.getEmployees("Mason");
		Employee emp = new Employee();
		for(Employee e: employees)
		{
			if(e.getName().equals("Luke Mason"))
				emp = e;
		}
		assertTrue(emp.toString().equals("ID: 1   Name: Luke Mason   Pay Rate: $1000.0"));
	}
	@Test
	public void testGetEmployees7()
	{
		ArrayList<Employee> employees = connect.getEmployees("Steven Holger");
		assertTrue(employees.size() == 0);
	}
	
	@Test
	public void testGetEmployeeWorkingTimes1()
	{	
		ArrayList<Employee> employees = connect.getEmployees("Luke");
		Employee emp = new Employee();
		for(Employee e: employees)
		{
			if(e.getName().equals("Luke Mason"))
				emp = e;
		}
		ArrayList<EmployeeWorkingTime> lukesWorkingTimes = connect.getEmployeeWorkingTimes(emp.getId());
		assertEquals(2,lukesWorkingTimes.size());
	}
	@Test
	public void testGetEmployeeWorkingTimes2()
	{	
		ArrayList<EmployeeWorkingTime> lukesWorkingTimes = connect.getEmployeeWorkingTimes(1);
		EmployeeWorkingTime emplWorking = new EmployeeWorkingTime();
		for(EmployeeWorkingTime empWork: lukesWorkingTimes)
		{
			if(empWork.getId() == 1)
				emplWorking = empWork;
		}
		assertTrue(emplWorking.getId()==1);
	}
	@Test
	public void testGetEmployeeWorkingTimes3()
	{	
		ArrayList<EmployeeWorkingTime> lukesWorkingTimes = connect.getEmployeeWorkingTimes(1);
		EmployeeWorkingTime emplWorking = new EmployeeWorkingTime();
		for(EmployeeWorkingTime empWork: lukesWorkingTimes)
		{
			if(empWork.getId() == 1)
				emplWorking = empWork;
		}
		assertTrue(emplWorking.getId()==1);
		assertEquals("03/12/2017", controller.convertDateToString(emplWorking.getDate()));
	}
	@Test
	public void testGetEmployeeWorkingTimes4()
	{	
		ArrayList<EmployeeWorkingTime> lukesWorkingTimes = connect.getEmployeeWorkingTimes(1);
		EmployeeWorkingTime emplWorking = new EmployeeWorkingTime();
		for(EmployeeWorkingTime empWork: lukesWorkingTimes)
		{
			if(empWork.getId() == 1)
				emplWorking = empWork;
		}
		assertEquals("09:50", controller.convertTimeToString(emplWorking.getStartTime()));
	}
	@Test
	public void testGetEmployeeWorkingTimes5()
	{	
		ArrayList<EmployeeWorkingTime> lukesWorkingTimes = connect.getEmployeeWorkingTimes(1);
		EmployeeWorkingTime emplWorking = new EmployeeWorkingTime();
		for(EmployeeWorkingTime empWork: lukesWorkingTimes)
		{
			if(empWork.getId() == 1)
				emplWorking = empWork;
		}
		assertEquals("17:25", controller.convertTimeToString(emplWorking.getEndTime()));
	}
	@Test
	public void testAddBookingToDatabase_CustomerID()
	{	
		ArrayList<Booking> bookingLists = new ArrayList<Booking>();
		bookingLists = connect.getAllBooking();
		
		assertEquals(bookingLists.get(0).getCustomerId(),1);
		assertEquals(bookingLists.get(1).getCustomerId(),2);
		assertEquals(bookingLists.get(2).getCustomerId(),3);
	}
	
	@Test
	public void testAddBookingToDatabase_Date()
	{
		ArrayList<Booking> bookingLists = new ArrayList<Booking>();
		bookingLists = connect.getAllBooking();
		
		assertEquals(controller.convertDateToString(bookingLists.get(0).getDate()),"04/04/2017");
		assertEquals(controller.convertDateToString(bookingLists.get(1).getDate()),"04/05/2017");
		assertEquals(controller.convertDateToString(bookingLists.get(2).getDate()),"05/04/2017");
	}
	
	@Test
	public void testAddBookingToDatabase_StartTimeAndEndTime()
	{
		ArrayList<Booking> bookingLists = new ArrayList<Booking>();
		bookingLists = connect.getAllBooking();
		
		assertEquals(controller.convertTimeToString(bookingLists.get(0).getStartTime()),"10:30");
		assertEquals(controller.convertTimeToString(bookingLists.get(0).getEndTime()),"12:00");
		assertEquals(controller.convertTimeToString(bookingLists.get(1).getStartTime()),"11:30");
		assertEquals(controller.convertTimeToString(bookingLists.get(1).getEndTime()),"12:30");
		assertEquals(controller.convertTimeToString(bookingLists.get(2).getStartTime()),"10:30");
		assertEquals(controller.convertTimeToString(bookingLists.get(2).getEndTime()),"12:00");
	}
	
	@Test
	public void testAddBookingToDatabase_Status()
	{
		ArrayList<Booking> bookingLists = new ArrayList<Booking>();
		bookingLists = connect.getAllBooking();
		
		assertEquals(bookingLists.get(0).getStatus(), "active");
		assertEquals(bookingLists.get(1).getStatus(), "active");
		assertEquals(bookingLists.get(2).getStatus(), "active");
	}
	
	@Test
	public void testCancelBooking_BookIDExists()
	{
		ArrayList<Booking> books = new ArrayList<Booking>();
		assertTrue(connect.cancelBooking(1));
		assertTrue(connect.cancelBooking(3));
		
		books = connect.getAllBooking();
		assertEquals("cancel",books.get(0).getStatus());
		assertEquals("active",books.get(1).getStatus());
		assertEquals("cancel",books.get(2).getStatus());	
	}
	
	@Test
	public void testCancelBooking_BookIDNotExist(){
		assertFalse(connect.cancelBooking(111));
		assertFalse(connect.cancelBooking(122));
		assertFalse(connect.cancelBooking(0));
		assertFalse(connect.cancelBooking(9999));
	}
	
	@After
	public void tearDown()
	{
		System.out.println("\n\nTear Down\n--------------");
		//Deleting table EMPLOYEES after the Test has been executed correctly
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate("DROP TABLE EMPLOYEES");
			System.out.println("Dropped Employees Table");
			inject.executeUpdate("DROP TABLE EMPLOYEES_WORKING_TIMES");
			System.out.println("Dropped 'Working Times' Table");
			inject.executeUpdate("DROP TABLE IF EXISTS BOOKINGS");
			System.out.println("Dropped 'Bookings' Table");
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
	}
	
}
