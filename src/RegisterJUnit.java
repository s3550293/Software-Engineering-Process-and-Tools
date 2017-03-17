import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class RegisterJUnit
{
	Register registerUnit = new Register();
	String result_UN;
	boolean result_AT;
	
	@Before
	public void setup_UsernameTest()
	{
		result_UN = registerUnit.checkTakenUsername("one");
	}
	
	@Test
    public void testUsername()
	{
        assertEquals("one", result_UN);

    }
	
	@Before
	public void setup_TestAccountType()
	{
		result_AT = registerUnit.setAccountType(1);
	}
	
	@Test
	public void testAccountType()
	{
		assertEquals(true, result_AT);
		assertNotSame(false, result_AT);
	}
}
