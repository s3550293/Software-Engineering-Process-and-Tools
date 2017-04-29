package program;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CustomerMenu
{

	private static Logger log = Logger.getLogger(CustomerMenu.class);
	private Controller controller = new Controller();

	public CustomerMenu()
	{
		log.setLevel(Level.WARN);
	}

	@SuppressWarnings("resource")
	public void customerMenu()
	{
		Scanner scanner = new Scanner(System.in);
		do
		{
			System.out.printf("\n%-1s %s\n", "", "Customer Menu");
			System.out.printf("%s\n", "---------------------------");
			System.out.printf("%-3s %-2s %s\n", "", "1.", "View Available Appointment Time");
			System.out.printf("%-3s %-2s %s\n", "", "2.", "Log Out");
			int selection = Integer.parseInt(scanner.nextLine());

			switch (selection)
			{
			case 1:
				displayBookingTimes();
				break;
			case 2:
				System.out.println("You are successfully logged out!");
				Login ln = new Login();
				ln.loginMenu();
				break;
			default:
				System.out.println("Option not available, please choose again");
			}
		} while (true);
		// scanner.close();
	}

	@SuppressWarnings("resource")
	public void displayBookingTimes()
	{
		String str = "";
		boolean loopflag = true;
		boolean displayloop = true;
		Scanner kb = new Scanner(System.in);
		Calendar c = Calendar.getInstance();
		String days[] = new String[7];
		String today;
		String day = "";
		int date = 0;
		for (int i = 0; i < 7; i++)
		{
			c.add(Calendar.DATE, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			today = sdf.format(c.getTime());
			days[i] = today;
		}
		while (loopflag)
		{
			do
			{
				System.out.printf("\n%s", "NO.");
				System.out.printf("%-2s %s", "", "Date");
				System.out.printf("%-15s %s", "", "Morning");
				System.out.printf("%-3s %s", "", "Afternoon");
				System.out.printf("%-3s %s", "", "Evening");
				System.out.print("\n---------------------------------------------------------");
				for (int j = 0; j < 7; j++)
				{
					System.out.printf("\n%d.", j + 1);
					System.out.printf("%-2s %s", "", days[j]);
					System.out.printf("%-10s %s", "", "Avail");
					System.out.printf("%-5s %s", "", "Avail");
					System.out.printf("%-7s %s\n", "", "Avail");
				}
				System.out.println("Enter Date Number:");
				day = kb.nextLine();
				System.out.println("\n\nM - Morning: 8am - 12pm");
				System.out.println("A - Afternoon: 12pm - 4pm");
				System.out.println("E - Evening: 4pm - 8pm");
				System.out.println("Choose time to book - e.g MAE or EA etc");
				str = kb.nextLine();
				if (controller.checkWorkTimeChoice(str) && controller.changeInputIntoValidInt(day) > 0)
				{
					date = controller.changeInputIntoValidInt(day);
					break;
				} else
				{
					System.out.println("Invalid input please chose again");
					day = "";
				}
			} while (true);
			do
			{
				Calendar calstart = Calendar.getInstance();
				Calendar calend = Calendar.getInstance();
				String[] time = controller.allocateWorkTimes(str);
				log.debug(time[0]);
				log.debug(time[1]);
				int count = 1;
				calstart.setTime(controller.strToTime(time[0]));
				calend.setTime(controller.strToTime(time[1]));
				System.out.println("\nBooking Time");
				System.out.printf("\n%s", "NO.");
				System.out.printf("%-2s %s", "", "Time");
				System.out.printf("%-15s %s", "", "Availability");
				System.out.print("\n---------------------------------------------------------");
				while (displayloop)
				{
					if (controller.timeToStr(calstart.getTime())
							.equals(controller.timeToStr(calend.getTime())))
					{
						break;
					} 
					else
					{
						System.out.printf("\n%2d.", count);
						System.out.printf("%-2s %-10s", "", controller.timeToStr(calstart.getTime()));
						if(controller.checkBookingStartTime(calstart.getTime(),controller.strToDate(days[date - 1])))
						{
							System.out.printf("%-10s %s", "", "Avail\n");
						}
						else if(!controller.checkBookingStartTime(calstart.getTime(),controller.strToDate(days[date - 1])))
						{
							System.out.printf("%-10s %s", "", "-----\n");
						}
						
						calstart.add(Calendar.MINUTE, 60);
						count++;
					}
				}
				kb.nextLine();
				return;

			} while (true);
		}

	}


}
