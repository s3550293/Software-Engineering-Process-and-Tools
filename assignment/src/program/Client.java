package program;

import java.util.Scanner;

public class Client extends User
{
	public Client(){}
	
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
			Login ln = new Login();
			ln.loginMenu();
			break;
		default:
			System.out.println("Option not available, please choose again");
		}
		scanner.close();
	}
}
