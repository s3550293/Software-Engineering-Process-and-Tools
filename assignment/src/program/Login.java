package program;

import java.util.*;

public class Login
{
	public Login()
	{
	}
	/*
	 * loginMenu displays the user login menu to the user
	 */
	public void loginMenu()
	{
		boolean flag = true; //Boolean set for the while loop to keep looping until the user makes the correct choice
		System.out.printf("\n%-1s %s\n", "", "Company Login");
		System.out.printf("%s\n","---------------------------");
		System.out.printf("%-3s %-2s %s\n", "", "1.", "Login");
		System.out.printf("%-3s %-2s %s\n", "", "2.", "Register");
		System.out.printf("%-3s %-2s %s\n", "", "3.", "Exit");
		Scanner userInput = new Scanner(System.in);
		while(flag)
		{	
			System.out.printf("%s\n%s", "Please chose a option between 1 and 2", "user> ");
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
						//Todo
						break;
					case 3:
						System.exit(0);
						break;
					default:
						System.out.println("option not available, please choose again");
				}
			}
			catch(NumberFormatException ex)
			{
				System.out.println("Invlid input, please enter your choice again");
			}
		}
		userInput.close();
	}
	
	public void login()
	{
		DatabaseConnection connect = new DatabaseConnection();
		Scanner scanner = new Scanner (System.in);
		

		String userName=null;
		String pass=null;
		boolean passCheck=false;
		System.out.printf("%s\n%s", "Please enter your username", "user> ");
		userName = scanner.nextLine();
		if(userName.equals(connect.getUser(userName).getUsername()))
		{
			while(passCheck==false)
			{
				System.out.printf("%s\n%s", "Please enter password", "user> ");
				pass = scanner.nextLine();
				if(pass.equals(connect.getUser(userName).getPassword()))
				{
					passCheck=true;
					if(connect.getUser(userName).getAccountType() == 1){
						companyMenu();
					}else{
						customerMenu();
					}
				}
				else
				{
					System.out.printf("\n%-1s %s\n", "", "Incorrect Password");
					passCheck=false;
				}
			}
		}
		else
		{
			System.out.printf("\n%-1s %s\n", "", "Username does not exist");
			System.out.printf("%-3s %-2s %s\n", "", "1.", "Register");
			System.out.printf("%-3s %-2s %s\n", "", "2.", "Try Again");
			System.out.printf("%-3s %-2s %s\n", "", "3.", "Exit");
			System.out.printf("%s\n%s", "Please chose a option between 1 and 2", "user> ");
			int option = Integer.parseInt(scanner.nextLine());
			switch(option)
			{
			case 1:
				//Todo
				break;
			case 2:
				login();
				break;
			case 3:
				System.exit(0);
				break;
			default:
				System.out.println("Option not available, please choose again");
			}
		}
		scanner.close();
	}
	
	public void customerMenu(){
Scanner scanner = new Scanner(System.in);
		
		System.out.printf("\n%-1s %s\n", "", "Customer Menu");
		System.out.printf("%s\n","---------------------------");
		System.out.printf("%-3s %-2s %s\n", "", "1.", ".......");
		System.out.printf("%-3s %-2s %s\n", "", "2.", ".......");
		System.out.printf("%-3s %-2s %s\n", "", "3.", ".......");
		System.out.printf("%-3s %-2s %s\n", "", "4.", ".......");
		System.out.printf("%-3s %-2s %s\n", "", "5.", ".......");
		System.out.printf("%-3s %-2s %s\n", "", "6.", "Log Out");
		int selection = Integer.parseInt(scanner.nextLine());
		
		switch(selection)
		{
		case 1:
			//Todo
			break;
		case 2:
			//Todo
			break;
		case 3:
			//Todo
			break;
		case 4:
			//Todo
			break;
		case 5:
			//Todo
			break;
		case 6:
			System.exit(0);
			break;
		default:
			System.out.println("Option not available, please choose again");
		}
		scanner.close();
	}
	
	
	public void companyMenu()
	{
		Scanner scanner = new Scanner(System.in);
		
		System.out.printf("\n%-1s %s\n", "", "Company Menu");
		System.out.printf("%s\n","---------------------------");
		System.out.printf("%-3s %-2s %s\n", "", "1.", "Check Employee Availability");
		System.out.printf("%-3s %-2s %s\n", "", "2.", "Add New Employee/Working time");
		System.out.printf("%-3s %-2s %s\n", "", "3.", "Check Bookings");
		System.out.printf("%-3s %-2s %s\n", "", "4.", "Make Bookings");
		System.out.printf("%-3s %-2s %s\n", "", "5.", "Change Employee Working Time");
		System.out.printf("%-3s %-2s %s\n", "", "6.", "Log Out");
		int selection = Integer.parseInt(scanner.nextLine());
		
		switch(selection)
		{
		case 1:
			//Todo
			break;
		case 2:
			//Todo
			break;
		case 3:
			//Todo
			break;
		case 4:
			//Todo
			break;
		case 5:
			//Todo
			break;
		case 6:
			System.exit(0);
			break;
		default:
			System.out.println("Option not available, please choose again");
		}
		scanner.close();
	}
	
}