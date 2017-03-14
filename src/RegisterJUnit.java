import org.junit.Test;
import static org.junit.Assert.*;

public class RegisterJUnit
{
	Register registerUnit = new Register();
	@Test
    public void testUsername()
	{
        String result = registerUnit.checkTakenUsername("one");
        assertEquals("one", result);
        result = registerUnit.checkTakenUsername("Pear");
        assertEquals("apple", result);

    }
	@Test
	public void testAccountType()
	{
		boolean result = registerUnit.setAccountType(1);
		assertEquals(true, result);
		result = registerUnit.setAccountType(2);
        assertEquals(false, result);
	}
}
