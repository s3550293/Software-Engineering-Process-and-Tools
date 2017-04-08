package program;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CustomerMenu {
	
	private static Logger log = Logger.getLogger(CustomerMenu.class);
	
	public CustomerMenu(){log.setLevel(Level.WARN);}
	@SuppressWarnings("resource")
	public void customerMenu()
	{
		Scanner scanner = new Scanner(System.in);
				do
				{		
						System.out.printf("\n%-1s %s\n", "", "Customer Menu");
						System.out.printf("%s\n","---------------------------");
						System.out.printf("%-3s %-2s %s\n", "", "1.", "View Available Appointment Time");
						System.out.printf("%-3s %-2s %s\n", "", "2.", "Log Out");
						int selection = Integer.parseInt(scanner.nextLine());
						
						switch(selection)
						{
						case 1:
							customerBooking();
							break;
						case 2:
							System.out.println("You are successfully logged out!");
							Login ln = new Login();
							ln.loginMenu();
							break;
						default:
							System.out.println("Option not available, please choose again");
						}
				}
				while(true);
				//scanner.close();
	}

	@SuppressWarnings("resource")
	public void displayBookingTimes()
	{
		boolean loopflag = true;
		while (loopflag)
		{
			Scanner kb = new Scanner(System.in);
			Calendar c = Calendar.getInstance();
			String days[] = new String[7];
			String today;
			for (int i = 0; i < 7; i++)
			{
				c.add(Calendar.DATE, 1);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				today = sdf.format(c.getTime());
				days[i] = today;
			}
			System.out.printf("\n%s", "NO.");
			System.out.printf("%-2s %s", "", "Date");
			System.out.printf("%-15s %s", "", "Morning");
			System.out.printf("%-3s %s", "", "Afternoon");
			System.out.printf("%-3s %s", "", "Evening");
			System.out.print("\n---------------------------------------------------------");
			for (int j = 0; j < 7; j++)
			{
					System.out.printf("\n%d.", j+1);
					System.out.printf("%-2s %s", "", days[j]);
					System.out.printf("%-10s %s", "", "Avail");
					System.out.printf("%-5s %s", "", "Avail");
					System.out.printf("%-7s %s", "", "Avail");
			}
		}
	}
	
	@SuppressWarnings("resource")
	private void customerBooking()
	{
		Scanner kb = new Scanner(System.in);
		Calendar c = Calendar.getInstance();
		String days[] = new String[7];
		String today;
		for (int i = 0; i < 7; i++)
		{
			c.add(Calendar.DATE, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			today = sdf.format(c.getTime());
			days[i] = today;
		}
		System.out.printf("\n%s", "NO.");
		System.out.printf("%-2s %s", "", "Date");
		System.out.printf("%-15s %s", "", "Morning");
		System.out.printf("%-3s %s", "", "Afternoon");
		System.out.printf("%-3s %s", "", "Evening");
		System.out.print("\n---------------------------------------------------------");
		for (int j = 0; j < 7; j++)
		{
			if(j ==3)
			{
				System.out.printf("\n%d.", j+1);
				System.out.printf("%-2s %s", "", days[j]);
				System.out.printf("%-10s %s", "", "Avail");
				System.out.printf("%-5s %s", "", "-----");
				System.out.printf("%-7s %s", "", "Avail");
			}
			else
			{
				System.out.printf("\n%d.", j+1);
				System.out.printf("%-2s %s", "", days[j]);
				System.out.printf("%-10s %s", "", "Avail");
				System.out.printf("%-5s %s", "", "Avail");
				System.out.printf("%-7s %s", "", "Avail");
			}
		}
		System.out.println("\n\nM - Morning: 8am - 12pm");
		System.out.println("A - Afternoon: 12pm - 4pm");
		System.out.println("E - Evening: 4pm - 8pm");
		System.out.println("Choose time to book - e.g MAE or EA etc");
		kb.nextLine();
		System.out.println("You selected "+days[3]+" M");
		System.out.println("\nSelect Services");
	    System.out.printf("\n%s", "NO.");
		System.out.printf("%-2s %s", "", "Service Name");
		System.out.printf("%-12s %s", "", "Length");
		System.out.printf("%-3s %s", "", "Price");
		System.out.print("\n---------------------------------------------------------");
	    System.out.printf("\n%s", "1.");
		System.out.printf("%-2s %-10s", "", "Clean");
		System.out.printf("%-15s %s", "", "30min");
		System.out.printf("%-5s %s", "", "$90");
		System.out.printf("\n%s", "2.");
		System.out.printf("%-2s %-10s", "", "Root Canal");
		System.out.printf("%-15s %s", "", "90min");
		System.out.printf("%-5s %s", "", "$300");
		System.out.printf("\n%s", "1.");
		System.out.printf("%-2s %-10s", "", "Checkup");
		System.out.printf("%-15s %s", "", "10min");
		System.out.printf("%-5s %s", "", "$500");
		System.out.println("\n\nSelect Service");
		kb.nextLine();
		System.out.println("You selected Root Canal\n");
		System.out.println("Booking Time");
		System.out.printf("\n%s", "NO.");
		System.out.printf("%-2s %s", "", "Time");
		System.out.printf("%-15s %s", "", "Availability");
		System.out.print("\n---------------------------------------------------------");
		System.out.printf("\n%s.", "1");
		System.out.printf("%-2s %-10s", "", "8:00");
		System.out.printf("%-10s %s", "", "Avail");
		System.out.printf("\n%s.", "2");
		System.out.printf("%-2s %-10s", "", "9:00");
		System.out.printf("%-10s %s", "", "-----");
		System.out.printf("\n%s.", "3");
		System.out.printf("%-2s %-10s", "", "10:00");
		System.out.printf("%-10s %s", "", "Avail");
		System.out.printf("\n%s.", "4");
		System.out.printf("%-2s %-10s", "", "11:00");
		System.out.printf("%-10s %s", "", "-----\n");
		kb.nextLine();
		
	}

}
