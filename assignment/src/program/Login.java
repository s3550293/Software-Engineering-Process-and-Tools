package program;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Login
{
	private static Logger log = Logger.getLogger(Login.class);
	private final Controller program = new Controller();
	public Login(){
		log.setLevel(Level.DEBUG);
	}
	
	/**
	 * @author David, Bryan
	 * Validate the user name and password if they are valid or invalid, and determine user's type
	 * @param userName
	 * @param pass
	 * @return 0 for valid Customer
	 * 		   1 for valid Business Owner
	 *        -1 for nonexistent user
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
						program.setUser(connect.getUser(userName));
						log.debug("LOGGER: User - "+connect.getUser(userName).getFullName());
						return 1;
					}else if(connect.getUser(userName).getAccountType() == 0){
						program.setUser(connect.getUser(userName));
						log.debug("LOGGER: User - "+connect.getUser(userName).getFullName());
						return 0;
					}
					else if(connect.getUser(userName).getAccountType() == 2){
						program.setUser(connect.getUser(userName));
						log.debug("LOGGER: User - "+connect.getUser(userName).getFullName());
						return 2;
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
