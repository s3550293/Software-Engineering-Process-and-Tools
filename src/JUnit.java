

import static org.junit.Assert.*;
import org.junit.*;

public class JUnit {

	
	@Before
	public void setUp()
	{

	}
	public boolean checkEmployeeName(String string) 
	{
		return false;
	}
	public boolean checkInputToBeInteger(String string) 
	{

		return false;
	}
	public boolean checkEmployeePayRate(int i) {
		
		return false;
	}
	
	@Test
	public void testCheckEmployeeName() 
	{
		assertFalse(checkEmployeeName("1010101LUKE"));
		assertFalse(checkEmployeeName(""));
		assertFalse(checkEmployeeName(null));
		assertFalse(checkEmployeeName("LUKEEEEEEEEEEEEEEEEEEEEEEEEEEE"));
		
		assertTrue(checkEmployeeName("Luke Mason"));
		assertTrue(checkEmployeeName("LukeyyyMason"));
	}

	@Test
	public void testCheckInputToBeInteger() 
	{
		assertFalse(checkInputToBeInteger(null));
		assertFalse(checkInputToBeInteger(""));
		assertFalse(checkInputToBeInteger("lel"));
		assertFalse(checkInputToBeInteger("$"));
		
		assertTrue(checkInputToBeInteger("-1111"));
		assertTrue(checkInputToBeInteger("10000000"));
		assertTrue(checkInputToBeInteger("10"));
		assertTrue(checkInputToBeInteger("0"));
	}
	@Test
	public void testCheckEmployeePayRate() 
	{
		assertFalse(checkEmployeePayRate(-1));
		assertFalse(checkEmployeePayRate(1000000));
		
		assertTrue(checkEmployeePayRate(0));
		assertTrue(checkEmployeePayRate(10));
	}
}