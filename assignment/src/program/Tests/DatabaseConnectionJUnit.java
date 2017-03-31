package program.Tests;

import program.Controller;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.DriverManager;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import program.Database;
import program.DatabaseConnection;
import program.Employee;
import program.EmployeeWorkingTime;
import program.Main;

public class DatabaseConnectionJUnit {
	
	private static Logger log = Logger.getLogger(Main.class);
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
	DatabaseConnection connect = new DatabaseConnection();
	
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
	public void testAddBookingToDatabase ()
	{
		//addBooking (integer userId, Date date, String startTime, String endTime, String description)
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
