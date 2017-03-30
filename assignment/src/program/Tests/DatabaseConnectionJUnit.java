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
		
	}
	@Test
	public void testAddEmployee_And_TestGetEmployee_And_TestEmployeeAttributes()
	{	
		System.out.println("\n\nAdd Employee And Test GetEmployee And Test Employee Attributes\n------------------------------------------------------------------------");
		//Adding 4 new employees to blank EMPLOYEES table
		connect.addEmployee("Luke Mason", 1000);
		connect.addEmployee("Jacob Boehm", 123);
		connect.addEmployee("Jake Mason", 30);
		connect.addEmployee("Leonardo Dicaprio", 12);
		
		//Assigning Employees to the employees in database
		Employee Luke_Mason = connect.getEmployee(1);
		Employee Jacob_Boehm = connect.getEmployee(2);
		Employee Jake_Mason = connect.getEmployee(3);
		Employee Leonardo_Dicaprio = connect.getEmployee(4);
		
		//Testing the toString() and get() methods against the different employees
		assertTrue(Luke_Mason.getId()==1);
		assertTrue(Luke_Mason.getPayRate()== 1000);
		assertTrue(Luke_Mason.getName().equals("Luke Mason"));
		assertTrue(Luke_Mason.toString().equals("ID: 1   Name: Luke Mason   Pay Rate: $1000.0"));
		
		assertTrue(Jacob_Boehm.getId()==2);
		assertTrue(Jacob_Boehm.getPayRate()== 123.0);
		assertTrue(Jacob_Boehm.getName().equals("Jacob Boehm"));
		assertTrue(Jacob_Boehm.toString().equals("ID: 2   Name: Jacob Boehm   Pay Rate: $123.0"));
	
		assertTrue(Jake_Mason.getId()==3);
		assertTrue(Jake_Mason.getPayRate()== 30);
		assertTrue(Jake_Mason.getName().equals("Jake Mason"));
		assertTrue(Jake_Mason.toString().equals("ID: 3   Name: Jake Mason   Pay Rate: $30.0"));
		
		assertTrue(Leonardo_Dicaprio.getId()==4);
		assertTrue(Leonardo_Dicaprio.getPayRate()== 12);
		assertTrue(Leonardo_Dicaprio.getName().equals("Leonardo Dicaprio"));
		assertTrue(Leonardo_Dicaprio.toString().equals("ID: 4   Name: Leonardo Dicaprio   Pay Rate: $12.0"));
		
		//Deleting Employee 2 & 3 from EMPLOYEE table
		try(Connection connect = this.connect(); Statement inject = connect.createStatement())
		{
			inject.executeUpdate("DELETE FROM EMPLOYEES WHERE name LIKE '%Jacob%' OR payRate = 30.0;");
			System.out.println("Deleted specified employees");
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
		
		//Adding new employee After rows have been deleted
		connect.addEmployee("Harry Potter",600);
		
		//Getting employee 5, even though we just deleted two employees
		Employee Harry_Potter = connect.getEmployee(5);
		
		//Getting Employees 2 & 3 that were just deleted
		Employee Jacob_Boehm_2 =connect.getEmployee(2);
		Employee Jake_Mason_2 = connect.getEmployee(3);
	
		//Testing the output of getting employees that do not exist  E.G ID 2 & 3 (they were deleted just before)
		assertTrue(Jacob_Boehm_2.getId()==0);
		assertTrue(Jacob_Boehm_2.getName().equals("Employee does not exist"));
		assertTrue(Jacob_Boehm_2.toString().equals("Sorry, Employees with that ID do not exist"));
		assertTrue(Jake_Mason_2.getId()==0);
		assertTrue(Jake_Mason_2.getName().equals("Employee does not exist"));
		assertTrue(Jake_Mason_2.toString().equals("Sorry, Employees with that ID do not exist"));
		
		//Testing that the ID of the new employee is 5 and DOES NOT take on the ID of recently deleted employees 2 & 3
		assertTrue(Harry_Potter.getId()==5);
		assertTrue(Harry_Potter.getPayRate()== 600);
		assertTrue(Harry_Potter.getName().equals("Harry Potter"));
		assertTrue(Harry_Potter.toString().equals("ID: 5   Name: Harry Potter   Pay Rate: $600.0"));

		
	}
	@Test
	public void testGetEmployees()
	{
		System.out.println("\n\nGetEmployees\n------------------------------------------------------------------------");
		
		//Adding Employees to test
		connect.addEmployee("Luke Mason", 1000);
		connect.addEmployee("Luke Boi", 24.57);
		connect.addEmployee("Mason Smith", 24.57);
		connect.addEmployee("Jane Smith", 24.57);
		
		//Needs to be changed to the new codebase
		//Searching employees with "luke"
		ArrayList<Employee> employees = connect.getEmployees("Mason");
		Employee emp = new Employee();
		
		assertTrue(employees.size() == 2);
		for(Employee e: employees)
		{
			if(e.getName().equals("Luke Mason"))
				emp = e;
		}
		
		assertTrue(emp.getId()== 1);
		assertTrue(emp.getName().equals("Luke Mason"));
		assertTrue(emp.getPayRate()== 1000);
		assertTrue(emp.toString().equals("ID: 1   Name: Luke Mason   Pay Rate: $1000.0"));
		
		for(Employee e: employees)
		{
			if(e.getName().equals("Mason Smith"))
				emp = e;
		}
		assertTrue(emp.getId()== 3);
		assertTrue(emp.getName().equals("Mason Smith"));
		assertTrue(emp.getPayRate()== 24.57);
		assertTrue(emp.toString().equals("ID: 3   Name: Mason Smith   Pay Rate: $24.57"));
		
		//searching employees with "lol"
		employees = connect.getEmployees("Steven Holger");
		assertTrue(employees.size() == 0);
		
		//searching employees with "boi"
		employees = connect.getEmployees("Smith");
		assertTrue(employees.size() == 2);
		
	}
	@Test
	public void testAddEmployeeWorkingTimeAndGetEmployeeWorkingTimes()
	{	
		System.out.println("\n\nAdd EmployeeWorkingTime And GetEmployeeWorkingTimes\n------------------------------------------------------------------------");
		connect.addEmployee("Luke Charles",100);
		connect.addEmployee("David Smith",100);
		//Needs to be changed to the new codebase
		//Assigning working times to employee 1 ('Luke') to the database
		ArrayList<Employee> employees = connect.getEmployees("Luke Charles");
		Employee emp = new Employee();
		for(Employee e: employees)
		{
			if(e.getName().equals("Luke Charles"))
				emp = e;
		}
		connect.addEmployeeWorkingTime(emp.getId(),"03/12/2017","9:50","17:25");
		connect.addEmployeeWorkingTime(emp.getId(),"02/03/2017","8:30","14:30");
		ArrayList<EmployeeWorkingTime> lukesWorkingTimes = connect.getEmployeeWorkingTimes(emp.getId());
		
		//Counting how many working times are in array
		assertEquals(2,lukesWorkingTimes.size());
		//Checking each attribute of each working time
		EmployeeWorkingTime emplWorking = new EmployeeWorkingTime();
		for(EmployeeWorkingTime empWork: lukesWorkingTimes)
		{
			if(empWork.getId() == 1)
				emplWorking = empWork;
		}
		assertTrue(emplWorking.getId()==1);
		assertEquals("03/12/2017", controller.convertDateToString(emplWorking.getDate()));
		assertEquals("09:50", controller.convertTimeToString(emplWorking.getStartTime()));
		assertEquals("17:25", controller.convertTimeToString(emplWorking.getEndTime()));
		
		for(EmployeeWorkingTime empWork: lukesWorkingTimes)
		{
			if(empWork.getId() == 2)
				emplWorking = empWork;
		}
		assertTrue(emplWorking.getId()==2);
		assertEquals("02/03/2017", controller.convertDateToString(emplWorking.getDate()));
		assertEquals("08:30", controller.convertTimeToString(emplWorking.getStartTime()));
		assertEquals("14:30", controller.convertTimeToString(emplWorking.getEndTime()));
	}
	@Test
	public void testAddBookingToDatabase ()
	{
		//addBooking (int userId, Date date, String startTime, String endTime, String description)
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
