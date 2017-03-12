import java.util.*;

public class Login
{
	public Login()
	{
		//code
	}
	/*
	 * loginMenu displays the user login menu to the user
	 */
	public void loginMenu()
	{
		boolean flag = true; //Boolean set for the while loop to keep looping until the user makes the correct choice
		System.out.printf("%-1s %s\n", "", "Company Login");
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
						//Todo
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
}
