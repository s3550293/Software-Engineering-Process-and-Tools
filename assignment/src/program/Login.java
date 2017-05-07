package program;

import java.util.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Login
{
	private static Logger log = Logger.getLogger(Login.class);
	private Controller controller = new Controller();
	
	public Login(){
		log.setLevel(Level.DEBUG);
	}
	
	/**
	 * 
	 * @param userName
	 * @param pass
	 * @return 0 for valid Customer
	 * 		   1 for valid Business Owner
	 *        -1 for non-existent user
	 *        -2 for incorrect password
	 *        -3 for empty user name or password
	 */
	public int logInProcess(String userName, String pass){
		boolean passCheck = false;
		DatabaseConnection connect = new DatabaseConnection();
		
	
		if(userName.equals(connect.getUser(userName).getUsername()))
		{
			while(passCheck==false)
			{
				if(pass.equals(connect.getUser(userName).getPassword()))
				{
					passCheck=true;
					if(connect.getUser(userName).getAccountType() == 1){
						controller.setUser(connect.getUser(userName));
						log.debug("LOGGER: User - "+connect.getUser(userName).getFullName());
						return 1;
					}else{
						controller.setUser(connect.getUser(userName));
						log.debug("LOGGER: User - "+connect.getUser(userName).getFullName());
						return 0;
					}
				}
				else
				{
					log.debug("Incorrect Password");
					return -2;
				}
			}
		}
		else if (userName.isEmpty() || pass.isEmpty()){
			log.debug("Please ensure all required fields are filled!");
			return -3;
		}
		else{
			log.debug("Username does not exist!");
		}
		return -1;
	}

}
