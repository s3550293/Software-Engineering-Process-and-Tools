package program;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class Client extends User
{
	private static Logger log = Logger.getLogger(Client.class);
	public Client(){}
	
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
			break;
		case 2:
			System.out.println("You are successfully logged out!");
			Login ln = new Login();
			ln.loginMenu();
			break;
		default:
			System.out.println("Option not available, please choose again");
		}
		scanner.close();
	}

}
