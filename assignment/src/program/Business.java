package program;

import java.util.Scanner;

public class Business
{

	public Business(){ companyMenu(); }
	
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
			//System.exit(0);
			break;
		default:
			System.out.println("Option not available, please choose again");
		}
		scanner.close();
	}
}
