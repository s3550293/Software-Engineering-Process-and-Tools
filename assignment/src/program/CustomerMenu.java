package program;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CustomerMenu
{

	private static Logger log = Logger.getLogger(CustomerMenu.class);

	public CustomerMenu()
	{
		log.setLevel(Level.INFO);
	}
}
