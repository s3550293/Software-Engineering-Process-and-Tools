package program;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class CustomerMenu {
	
	public CustomerMenu(){}
	
	@SuppressWarnings("resource")
	public void customerMenu(){
		Scanner scanner = new Scanner(System.in);
		
		System.out.printf("\n%-1s %s\n", "", "Customer Menu");
		System.out.printf("%s\n","---------------------------");
		System.out.printf("%-3s %-2s %s\n", "", "1.", "View Available Appointment Time");
		System.out.printf("%-3s %-2s %s\n", "", "2.", "Log Out");
		int selection = Integer.parseInt(scanner.nextLine());
		
		switch(selection)
		{
		case 1:
			displayBookingTimes();
			break;
		case 2:
			System.out.println("You are successfully logged out!");
			return;
		default:
			System.out.println("Option not available, please choose again");
		}
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
}

