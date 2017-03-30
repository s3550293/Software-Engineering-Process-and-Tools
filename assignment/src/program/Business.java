package program;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class Business
{
	
	private static Logger log = Logger.getLogger(Main.class);
	private boolean flag = true;
	private Controller controller = new Controller();
	public Business(){}
	public void companyMenu()
	{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while(flag)
		{
			System.out.printf("\n%-1s %s\n", "", "Company Menu");
			System.out.printf("%s\n","---------------------------");
			System.out.printf("%-3s %-2s %s\n", "", "1.", "Check Employee Availability over next 7 days");
			System.out.printf("%-3s %-2s %s\n", "", "2.", "Add New Employee/Working time");
			System.out.printf("%-3s %-2s %s\n", "", "3.", "Check Bookings");
			System.out.printf("%-3s %-2s %s\n", "", "4.", "Make Bookings");
			System.out.printf("%-3s %-2s %s\n", "", "5.", "Delete Bookings");
			System.out.printf("%-3s %-2s %s\n", "", "6.", "Add Employee Working Times");
			System.out.printf("%-3s %-2s %s\n", "", "7.", "Log Out");
			System.out.printf("%s\n%s", "Please choose between 1 and 6", "user> ");
			int selection = Integer.parseInt(scanner.nextLine());
			
			switch(selection)
			{
			case 1:
				controller.displayAvailability();
				break;
			case 2:
				controller.addNewEmployee();
				break;
			case 3:
				//Todo
				break;
			case 4:
				//Todo
				break;
			case 5:
				deleteBooking();
				break;
			case 6:
				controller.addWorkingTimesForEmployeeByName();
				break;
			case 7:
				flag = false;
				break;
			default:
				System.out.println("Option not available, please choose again");
			}
			//scanner.close();
		}
	}
	
	/**
	 * @author Joseph Garner
	 */
	public void deleteBooking()
	{
		
	}
	
	
}
