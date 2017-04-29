package program;

import java.util.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Login
{
	private static Logger log = Logger.getLogger(Login.class);
	private Register reg = new Register();
	private BusinessMenu bmenu = new BusinessMenu();
	private CustomerMenu cmenu = new CustomerMenu();
	private Controller controller = new Controller();
	
	public Login(){log.setLevel(Level.DEBUG);}
	/*
	 * loginMenu displays the user login menu to the user
	 */
	int counter=0;
	@SuppressWarnings("resource")
	public void loginMenu()
	{
		boolean flag = true; //Boolean set for the while loop to keep looping until the user makes the correct choice
		Scanner userInput = new Scanner(System.in);
		while(flag)
		{	
			System.out.printf("\n%-1s %s\n", "", "Login");
			System.out.printf("%s\n","---------------------------");
			System.out.printf("%-3s %-2s %s\n", "", "1.", "Login");
			System.out.printf("%-3s %-2s %s\n", "", "2.", "Register");
			System.out.printf("%-3s %-2s %s\n", "", "3.", "Exit");
			log.debug("Enter 4 to ini database");
			System.out.printf("%s\n%s", "Please choose a option between 1 and 3", "user> ");
			/*
			 * Try catch checks the user input, throws an error if the incorrect data type is entered
			 */
			try
			{
				int choice = Integer.parseInt(userInput.nextLine());
				switch(choice)
				{
					case 1:
						//login();
						break;
					case 2:
						//reg.registerUser();
						break;
					case 3:
						System.out.println("Exit the program...");
						System.exit(0);
						break;
					case 4:
						//ini();
						break;
					default:
						System.out.println("option not available, please choose again");
				}
			}
			catch(NumberFormatException ex)
			{
				System.out.println("Invalid input, please enter your choice again");
			}
		}
		//userInput.close();
	}
	/*
	@SuppressWarnings("resource")
	public void login()
	{

		Scanner scanner = new Scanner (System.in);
		
		String userName=null;
		String pass=null;
		System.out.printf("%s\n%s", "Please enter your username", "user> ");
		userName = scanner.nextLine();
		System.out.printf("%s\n%s", "Please enter your pass", "user> ");
		pass = scanner.nextLine();
		int result = logInProcess(userName,pass);
		if (result == 0){
			cmenu.customerMenu();
		}
		else if(result == 1){
			bmenu.companyMenu();
		}else{
			if(counter==9)
			{
				System.out.println("Too many failed attempts. Exiting..");
				System.exit(0);
			}
			counter+=1;
			tryAgainMenu();
		}
		//scanner.close();
	}
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
					//System.out.printf("\n%-1s %s\n", "", "Incorrect Password");
					return -2;
				}
			}
		}
		else if (userName.isEmpty() || pass.isEmpty()){
			System.out.printf("\n%-1s %s\n", "", "Please ensure all required fields are filled!");
			return -3;
		}
		else{
			System.out.printf("\n%-1s %s\n", "", "Username does not exist!");
		}
		return -1;
	}

	@SuppressWarnings("resource")
	public void tryAgainMenu()
	{
		Scanner scanner = new Scanner(System.in);
	
		System.out.printf("\n%-1s %s\n", "", "LogIn Failure");
		System.out.printf("%-3s %-2s %s\n", "", "1.", "Register");
		System.out.printf("%-3s %-2s %s\n", "", "2.", "Try Again");
		System.out.printf("%-3s %-2s %s\n", "", "3.", "Exit");
		System.out.printf("%s\n%s", "Please chose a option between 1 and 2", "user> ");
		int option = Integer.parseInt(scanner.nextLine());
		switch(option)
		{
		case 1:
			//reg.registerUser();
			break;
		case 2:
			//login();
			break;
		case 3:
			System.out.println("Exit the program...");
			System.exit(0);
			break;
		default:
			System.out.println("Option not available, please choose again");
		}
		//scanner.close();
	}
}
