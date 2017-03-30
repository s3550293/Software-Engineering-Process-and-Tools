package program;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class Customer {
	
	private static Logger log = Logger.getLogger(Main.class);
	
	public Customer(){}
	
	public void customerMenu()
	{
		Scanner scanner = new Scanner(System.in);
				
				System.out.printf("\n%-1s %s\n", "", "Customer Menu");
				System.out.printf("%s\n","---------------------------");
				System.out.printf("%-3s %-2s %s\n", "", "1.", ".......");
				System.out.printf("%-3s %-2s %s\n", "", "2.", ".......");
				System.out.printf("%-3s %-2s %s\n", "", "3.", ".......");
				System.out.printf("%-3s %-2s %s\n", "", "4.", ".......");
				System.out.printf("%-3s %-2s %s\n", "", "5.", ".......");
				System.out.printf("%-3s %-2s %s\n", "", "6.", "Log Out");
				System.out.printf("%s\n%s", "Please choose between 1 and 6", "user> ");
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
					Login login = new Login();
					login.loginMenu();
					break;
				default:
					System.out.println("Option not available, please choose again");
				}
				scanner.close();
	}

}
