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
	 * 		   2 for valid Superuser/Admin
	 *        -1 for nonexistent user
	 *        -2 for incorrect password
	 *        -3 for empty user name or password
	 */
	public int logInProcess(String userName, String pass,int businessID){
		DatabaseConnection connect = new DatabaseConnection();
		User user = connect.getUser(userName);
		if(userName.equals(user.getUsername()))
		{
			if(pass.equals(user.getPassword()))
			{
				if(user.getAccountType() == 2){
					program.setUser(user);
					log.debug("LOGGER: User - "+connect.getUser(userName).getFullName());
					return 0;
				} 
				if(businessID == user.getBusinessID())
				{
					if(user.getAccountType() == 1){
						program.setUser(user);
						log.debug("LOGGER: User - "+connect.getUser(userName).getFullName());
						return 1;
					}
					else if(user.getAccountType() == 0){
						program.setUser(user);
						log.debug("LOGGER: User - "+connect.getUser(userName).getFullName());
						return 2;
					}
				}
				else
				{
					log.debug("Incorrect UserName or Password");
					return -2;
				}
			}
			else
			{
				log.debug("Incorrect Username or Password");
				return -2;
			}
		}
		else if (userName.isEmpty() || pass.isEmpty()){
			log.debug("Please ensure all required fields are filled!");
			return -3;
		}
		else{
			log.debug("Incorrect Username or Password");
		}
		return -1;
	}

}
