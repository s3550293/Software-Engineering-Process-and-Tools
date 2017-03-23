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

	/*
	@Test
	public void testEmpID() throws SQLException{
		
		assertFalse(employeeIDCheck(123));
		assertTrue(employeeIDCheck(223));
	}
	*/

}