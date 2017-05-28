package test.java.Tests;

import static org.junit.Assert.*;

import org.junit.*;

import program.Employee;

public class EmployeeJUnit 
{
	Employee employee;
	Employee employee2;
	Employee employee3;
	Employee employee4;
	@Before
	public void setup()
	{
		employee = new Employee(0, "lukey", 14.56,2);
		employee2 = new Employee(1, "lukey2", 124,2);
		employee3 = new Employee(2, "lukey3", 345,2);
		employee4 = new Employee(3, "lukey4", 16,2);
	}
	@Test
	public void testGetId()
	{
		assertEquals(0,employee.getId());
		assertEquals(1, employee2.getId());
		assertEquals(2, employee3.getId());
		assertEquals(3, employee4.getId());
	}
	@Test
	public void testGetName()
	{
		assertEquals("lukey", employee.getName());
		assertEquals("lukey2", employee2.getName());
		assertEquals("lukey3", employee3.getName());
		assertEquals("lukey4", employee4.getName());
	}
	@Test
	public void testGetPayRate()
	{
		assertTrue(14.56 == employee.getPayRate());
		assertTrue(124 == employee2.getPayRate());
		assertTrue(345 == employee3.getPayRate());
		assertTrue(16 == employee4.getPayRate());
	}
	@Test
	public void testSetName()
	{
		employee.setName("1");
		employee2.setName("2");
		employee3.setName("3");
		employee4.setName("4");
		
		assertEquals("1", employee.getName());
		assertEquals("2", employee2.getName());
		assertEquals("3", employee3.getName());
		assertEquals("4", employee4.getName());
	}
	@Test
	public void testSetPayRate()
	{
		employee.setPayRate(1);
		employee2.setPayRate(2);
		employee3.setPayRate(3.03);
		employee4.setPayRate(4.55);
		assertTrue(1 == employee.getPayRate());
		assertTrue(2 == employee2.getPayRate());
		assertTrue(3.03 == employee3.getPayRate());
		assertTrue(4.55 == employee4.getPayRate());
	}
}

