package program;

import static org.junit.Assert.assertTrue;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseConnectionJUnit {
	
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
		System.out.println("\n\nAdd Employee And Test GetEmployee And Test EmployeeAttributes\n------------------------------------------------------------------------");
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
		connect.addEmployee("Harry Potter",666);
		
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
		assertTrue(Harry_Potter.getPayRate()== 666);
		assertTrue(Harry_Potter.getName().equals("Harry Potter"));
		assertTrue(Harry_Potter.toString().equals("ID: 5   Name: Harry Potter   Pay Rate: $666.0"));

		
	}
	@Test
	public void testGetEmployees()
	{
		System.out.println("\n\nGetEmployees\n------------------------------------------------------------------------");
		int count;
		//Adding Employees to test
		connect.addEmployee("Luke Mason", 1000);
		connect.addEmployee("Luke Boi", 24.57);
		
		//Searching employees with "luke"
		Employee[] employees = connect.getEmployees("luke");
		
		//Counting the number of employees in employees[]
		count = countEmployeesInArray(employees);
		
		//Expecting the amount of employees to be 2
		assertTrue(count == 2);
		assertTrue(employees[0].getId()== 1);
		assertTrue(employees[0].getName().equals("Luke Mason"));
		assertTrue(employees[0].getPayRate()== 1000);
		assertTrue(employees[0].toString().equals("ID: 1   Name: Luke Mason   Pay Rate: $1000.0"));
		assertTrue(employees[1].getId()== 2);
		assertTrue(employees[1].getName().equals("Luke Boi"));
		assertTrue(employees[1].getPayRate()== 24.57);
		assertTrue(employees[1].toString().equals("ID: 2   Name: Luke Boi   Pay Rate: $24.57"));
		
		//searching employees with "lol"
		Employee[] employees2 = connect.getEmployees("lol");
		count = countEmployeesInArray(employees2);
		assertTrue(count == 0);
		
		//searching employees with "boi"
		Employee[] employees3 = connect.getEmployees("boi");
		count = countEmployeesInArray(employees3);
		assertTrue(count == 1);
		
		
	}
	@Test
	public void testAddEmployeeWorkingTimeAndGetEmployeeWorkingTimes()
	{	
		System.out.println("\n\nAdd EmployeeWorkingTime And GetEmployeeWorkingTimes\n------------------------------------------------------------------------");
		int count;
		//Adding first employee to database
		connect.addEmployee("Luke",100);
		
		//Assigning working times to employee 1 ('Luke') to the database
		connect.addEmployeeWorkingTime(1,"01/01/2017",8.5,14);
		connect.addEmployeeWorkingTime(1,"02/01/2017",8.66666667,14.34);
		EmployeeWorkingTime[] LukesWorkingTimes = connect.getEmployeeWorkingTimes(1);
		
		//Counting how many working times are in array
		count = countWorkTimesInArray(LukesWorkingTimes);
		assertTrue(count == 2);
		//Checking each attribute of each working time
		assertTrue(LukesWorkingTimes[0].getId()==1);
		assertTrue(LukesWorkingTimes[0].getDate().equals("01/01/2017"));
		assertTrue(LukesWorkingTimes[0].getStartTime()== 8.5);
		assertTrue(LukesWorkingTimes[0].getEndTime()==14);
		
		assertTrue(LukesWorkingTimes[1].getId()==1);
		assertTrue(LukesWorkingTimes[1].getDate().equals("02/01/2017"));
		assertTrue(LukesWorkingTimes[1].getStartTime()== 8.66666667);
		assertTrue(LukesWorkingTimes[1].getEndTime()==14.34);

		
		connect.addEmployeeWorkingTime(1,"03/01/2017",8.5,14.355555555555);
		EmployeeWorkingTime[] LukesWorkingTimes2 = connect.getEmployeeWorkingTimes(1);
		count = countWorkTimesInArray(LukesWorkingTimes2);
		assertTrue(count == 3);
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
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
	}
}
