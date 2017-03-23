package program.Tests;

import program.Controller;
import static org.junit.Assert.*;
import org.junit.*;

public class ControllerJUnit
{
	Controller cont = new Controller();
	
	@Before
	public void setup()
	{
		
	}
	
	@Test
	public void test()
	{
		assertEquals("12/01/2017", cont.displayDate(cont.convertToDate("12/01/2017")));
		assertEquals("12/01/2017", cont.displayDate(cont.convertToDate("/01/2017")));
	}
	
	@After
	public void tearDown()
	{
		
	}
}
