package program;

import java.util.*;

import org.apache.log4j.Logger;

public class Login
{
	private static Logger log = Logger.getLogger(Login.class);
	private Register reg = new Register();
	private Business bmenu = new Business();
	private Client cmenu = new Client();
	public Login(){}
	/*
	 * loginMenu displays the user login menu to the user
	 */
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
						login();
						break;
					case 2:
						reg.registerUser();
						break;
					case 3:
						System.out.println("Exit the program...");
						System.exit(0);
						break;
					case 4:
						ini();
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
			tryAgainMenu();
		}
		//scanner.close();
	}
	
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
						return 1;
						
					}else{
						return 0;
					}
				}
				else
				{
					System.out.printf("\n%-1s %s\n", "", "Incorrect Password");
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
			reg.registerUser();
			break;
		case 2:
			login();
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
	public void ini()
	{
		Database db = new Database("company.db");
		db.createTable("company.db");
		DatabaseConnection connect = new DatabaseConnection();
		connect.addUser("William", "Apples22", 0);
		connect.addUser("bo1","123456",1);
		connect.addEmployee("Luke Charles",100);
		connect.addEmployee("David Smith",100);
		connect.addEmployeeWorkingTime(1,"28/03/2017","9:50","17:25");
		connect.addEmployeeWorkingTime(1,"29/03/2017","8:30","14:30");

		connect.addEmployeeWorkingTime(2,"01/04/2017","10:30","12:30");
		connect.addEmployeeWorkingTime(2,"26/03/2017","11:30","15:30");
		connect.addBooking(1, "07/04/2017", "10:30", "11:30", "active");
		connect.addBooking(2, "08/04/2017", "11:30", "12:30", "active");
	}
}
