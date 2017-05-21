package program;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gui.IInterface.IUser;

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
	public int logInProcess(String userName, String pass, int businessID){
		DatabaseConnection connect = new DatabaseConnection();
	
		User user = connect.getUser(userName,businessID);
		log.info(userName+"\n");
		log.info(pass+"\n");
		log.info(businessID+"\n");	
		if(user == null)
		{
			log.debug("NO USER FOUND WITH THAT INPUT\n");
			return -4;
		}
		if(userName.equals(user.getUsername()))
		{
			if(pass.equals(user.getPassword()))
			{
				if(businessID == user.getBusinessID())
				{
					if(user.getAccountType() == 2){
						program.setUser(user);
						program.setBusiness(connect.getBusiness(businessID));
						log.debug("LOGGER: User 2 - "+connect.getUser(userName,businessID).getUsername());
						return 2;
					} 			
					else if(user.getAccountType() == 1){
						program.setUser(user);
						program.setBusiness(connect.getBusiness(businessID));
						log.debug("LOGGER: User 1 - "+connect.getUser(userName,businessID).getUsername());
						return 1;
					}
					else if(user.getAccountType() == 0){
						program.setUser(user);
						program.setBusiness(connect.getBusiness(businessID));
						log.debug("LOGGER: User 0 - "+connect.getUser(userName,businessID).getUsername());
						return 0;
					}
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
