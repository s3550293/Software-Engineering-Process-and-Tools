package program;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Business
{
	
	private static Logger log = Logger.getLogger(Business.class);
	private boolean flag = true;
	private Controller controller = new Controller();
	public Business(){log.setLevel(Level.WARN);}
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
			System.out.printf("%-3s %-2s %s\n", "", "5.", "Cancel Bookings");
			System.out.printf("%-3s %-2s %s\n", "", "6.", "Add Employee Working Times");
			System.out.printf("%-3s %-2s %s\n", "", "7.", "Log Out");
			System.out.printf("%s\n%s", "Please choose between 1 and 6", "user> ");
			int selection = Integer.parseInt(scanner.nextLine());
			
			switch(selection)
			{
			case 1:
				displayAvailability();
				break;
			case 2:
				addNewEmployee();
				break;
			case 3:
				checkBooking();
				break;
			case 4:
				//Todo
				break;
			case 5:
				controller.cancelBooking();
				break;
			case 6:
				addWorkingTimesForEmployeeByName();
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
	
	/**
	 *@author Luke Mason
	 * Finds all employees by name input from UI and prints them to screen
	 * Calls add employee work time function to add a work times for next month
	 * @return true when /exit is called
	 */
	public boolean addWorkingTimesForEmployeeByName()
	{
		@SuppressWarnings("resource")
		Scanner kb = new Scanner(System.in);
		log.info("IN addWorkingTimesForEmployeeByName\n");
		DatabaseConnection connect = new DatabaseConnection();
		String employeeName;
		boolean loopAgain;
		boolean loopAgain2;
		do//Loop prompt for employee username search
		{
			loopAgain = false;
			System.out.println("/exit to exit");
			System.out.print("Employee Name >> ");
			employeeName = kb.nextLine().toLowerCase();
			// Attempting to see if the input is valid
			// Checking to see if the input contains any non-alphabetical
			// characters e.g ?>!#%$#12345
			if (employeeName.equalsIgnoreCase("/exit"))//exit command
			{
				System.out.println("Exitting to main menu...");
				log.info("OUT addWorkingTimesForEmployeeByName\n");
				return true;
			}
			ArrayList<Employee> employees = connect.getEmployees(employeeName);
			if (employees.size() == 0)//if array list is empty then the user is prompted to try again
			{
				System.out.println(
						"Sorry but there are no matches for the name '" + employeeName + "'\n Please Try again");
				loopAgain = true;
				continue;
			}
			else
			{
				System.out.println("~~~LIST OF EMPLOYEES~~~\n");
				for (Employee employee : employees)//Displays all employees toString in the array list
				{
					System.out.println(employee.toString());
				}
				System.out.println("\n~~~~~~~~~~END~~~~~~~~~~");
			}
			do//prompting user to pick an employee or /again to search for employees again
			{
				loopAgain2 = false;
				System.out.println("Which employee would you like to add working times for [/again to search again]?");
				System.out.print("Employee's ID >> ");
				String employeeId = kb.nextLine();
				if (employeeId.equalsIgnoreCase("/exit"))//exit command
				{
					System.out.println("Exitting to main menu...");
					log.info("OUT addWorkingTimesForEmployeeByName\n");
					return true;
				}
				if (employeeId.equalsIgnoreCase("/again"))//lets user search for employee again
				{
					loopAgain2 = false;
					loopAgain = true;
					continue;
				}
				int id = controller.changeInputIntoValidInt(employeeId);//Converting id into a valid integer
				if (id < 0)//if id is invalid, prompts user for id again
				{
					System.out.println("Invalid ID, Try again");
					loopAgain2 = true;
					continue;
				}
				boolean idExists = false;
				for (Employee employee : employees)//checks to see if id exists in the selection of employees in array
				{
					if (employee.getId() == id)//calls working time functions and starts adding them for matched employee
					{
						idExists = true;
						addWorkingTimesForNextMonth(id);
					}
				}
				if (!idExists)//if id is not matched, prompts user to try again
				{
					System.out.println(
							"ID MISMATCH: There is no ID that matches an employee you have just searched for\n Try Again");
					loopAgain2 = true;
				}
			} while (loopAgain2);
		} while (loopAgain);
		log.info("OUT addWorkingTimesForEmployeeByName\n");
		return false;
	}
	
	 /**
	 * @author Luke Mason
	 * Prompts user for sufficient information in order to add a new employee to database
	 * @return false if the program breaks out of loop. True if /exit is called or user exits
	 */
	public boolean addNewEmployee()
	{
		@SuppressWarnings("resource")
		Scanner kb = new Scanner(System.in);
		DatabaseConnection connect = new DatabaseConnection();
		boolean loopAgain;
		String employeeFName;
		String employeeLName;
		String employeeName;
		String employeePayRate;
		double employeePayRate2;
		// Loop until first name is valid
		do
		{
			loopAgain = false;
			System.out.print("Enter in the new employee's first name only [/exit to quit] >> ");
			employeeFName = kb.nextLine().toLowerCase();
			if (employeeFName.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");//exit command

				return false;
			}
			if (controller.checkInputToContainInvalidChar(employeeFName))//checking for invalid characters
			{
				System.out.println("The name you have entered contains non-alphabetical characters");
				System.out.println("Please try again");
				loopAgain = true;
			}
		} while (loopAgain);
		do//Loop until last name is valid
		{
			loopAgain = false;
			System.out.print("Enter in the new employee's last name only [/exit to quit] >> ");
			employeeLName = kb.nextLine().toLowerCase();
			if (employeeLName.equalsIgnoreCase("/exit"))//exit command
			{
				System.out.println("Exitting to main menu...");

				return false;
			}
			if (controller.checkInputToContainInvalidChar(employeeLName))//checking for invalid characters
			{
				System.out.println("The name you have entered contains non-alphabetical characters");
				System.out.println("Please try again");
				loopAgain = true;
			}
		} while (loopAgain);
		employeeName = employeeFName + " " + employeeLName;//concatenating first and last name into name
		do//Loop until pay rate is valid
		{
			loopAgain = false;
			System.out.print("Enter in the pay rate of " + employeeName + " [/exit to quit] >> ");
			employeePayRate = kb.nextLine();
			// Attempting to change string into an integer
			// Checking to see if the amount contains any non-digit characters
			if (employeePayRate.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");//exit command

				return false;
			}
			employeePayRate2 = controller.changeInputIntoValidDouble(employeePayRate);//checking for invalid input
			if (employeePayRate2 < 0)
			{
				System.out.println(
						"The amount you have entered contains invalid characters, is less than 0 or greater that 1000 ");
				System.out.println("Please try again");
				loopAgain = true;
			}
		} while (loopAgain);
		do//Loop until valid option is selected
		{
			loopAgain = false;
			System.out.println("What would you like to do now?");
			System.out.println("1. Save and Add " + employeeName + "'s working times for the next month");
			System.out.println("2. Save and Exit");
			System.out.println("3. Exit without Saving");
			System.out.print("Select an option >> ");
			int answer = kb.nextInt();
			kb.nextLine();
			switch (answer)
			{
			case 1:
				connect.addEmployee(employeeName, employeePayRate2);//adding employee
				ArrayList<Employee> employees = connect.getEmployees(employeeName);//adding working times to employee just made
				if(employees.size() > 1)//If employee just made has exact same name as an existing employee then returns to main menu
				{
					System.out.println("ERROR: There are two employees in the database with the same name, cannot add working times");
					return true;
				}
				for (Employee employee : employees) 
				{
					if(addWorkingTimesForNextMonth(employee.getId()))//exit command is called  
					  {
						  System.out.println("Saved and exiting to main menu");
					  }
				}
				return true;
			case 2:
				connect.addEmployee(employeeName, employeePayRate2);//adding employee
				System.out.println("Employee " + employeeName + " added!");
				return true;
			case 3:
				do//Loop until input is valid
				{
					loopAgain = false;
					System.out.print("Are you sure you don't want to save the employee to the database?(Y/N)");
					String exit = kb.nextLine();
					if (exit.equalsIgnoreCase("y"))
					{
						return false;
					} else if (exit.equalsIgnoreCase("n"))
					{
						continue;
					} else
					{
						System.out.println("Please enter in \"y\" or \"n\" only");
						loopAgain = true;
					}
				} while (loopAgain);
			default:
				System.out.println("Invalid option, Try again");
				loopAgain = true;
				break;
			}
		} while (loopAgain);
		System.out.println("Unexpected Error: Please consult the developers");

		return false;
	}

	/**
	 * @author Luke Mason, David (Panhaseth Heang)
	 * UI for adding employee working times for next month
	 * @param employeeId
	 * @return only returns false if user somehow breaks out of infinite loop with out exiting function
	 */
	public boolean addWorkingTimesForNextMonth(int employeeId)
	{
		@SuppressWarnings("resource")
		Scanner kb = new Scanner(System.in);
		log.info("IN addWorkingTimesForNextMonth\n");
		Controller controller = new Controller();
		DatabaseConnection connect = new DatabaseConnection();

		// check if the input employeeId exists in database
		boolean valid = false, valid2 = false;
		String newDateStr = "";
		String newStartTimeStr = "";
		String newFinishTimeStr = "";
		boolean loop = false;
		do//Loop, exit with /exit
		{
			do{
				valid2 = true;
				do
				{
					valid = true;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String[] dateArray = new String[7];
					Calendar c = Calendar.getInstance();
					System.out.println("----Next 7 Days----");
					for(int i = 1;i<=7; i++)
					{
						dateArray[i-1] = sdf.format(c.getTime());//puts each date from every loop into array
						String time = sdf.format(c.getTime());
						System.out.println(i+": " + time);
						c.add(Calendar.DAY_OF_MONTH, 1);
					}
					System.out.println("\n/exit to save and exit to main menu\n");
					System.out.print("Select a day >> ");
					String choiceStr = kb.nextLine();
					if (choiceStr.equalsIgnoreCase("/exit"))
					{
						log.info("OUT addWorkingTimesForNextMonth\n");
						return true;
					}
					int choice = controller.changeInputIntoValidInt(choiceStr);
					if (choice < 1 || choice > 7)
					{
						System.out.println("Invalid input!");
						valid = false;
						continue;
					}
					newDateStr = dateArray[choice-1];
				}
				while(!valid);
				do
				{
					valid2 = true;
					System.out.print("\n");
					System.out.println("M - Morning: 8am - 12pm");
					System.out.println("A - Afternoon: 12pm - 4pm");
					System.out.println("E - Evening: 4pm - 8pm");
					System.out.println("\n/exit to save and exit to main menu");
					System.out.println("/back to re-enter Time block(s) again\n");
					System.out.println("\"ME\" or \"EM\" are invalid time blocks");
					System.out.print("Choose time blocks to assign employee\n Type answer as a word - e.g MAE or EA etc >> ");
					String choiceStr = kb.nextLine().toUpperCase();
					choiceStr = choiceStr.replace(" ", "").replace(",","");
					if (choiceStr.equalsIgnoreCase("/exit"))
					{
						log.info("OUT addWorkingTimesForNextMonth\n");
						return true;
					}
					if(choiceStr.equalsIgnoreCase("/back"))
					{
						valid = true;
						valid2 = false;
						continue;
					}
					valid = controller.checkWorkTimeChoice(choiceStr);
					if(!valid){continue;}
					String[] times = new String[2];
					times = controller.allocateWorkTimes(choiceStr);
					if(times == null)
					{
						valid = false;
						continue;
					}
					newStartTimeStr = times[0];
				    newFinishTimeStr = times[1];
				}
				while(!valid);
			}
			while(!valid2);//Used for /back command
			connect.addEmployeeWorkingTime(employeeId, newDateStr, newStartTimeStr, newFinishTimeStr);
			System.out.println("Employee Work time: "+newStartTimeStr+" - "+newFinishTimeStr+" is now added on "+newDateStr);
			do
			{
				valid = true;
				System.out.println("\nWould you like to add another work time for the employee?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				String choiceStr = kb.nextLine();
				int choice = controller.changeInputIntoValidInt(choiceStr);
				if (choice < 1 || choice > 2)
				{
					System.out.println("Please enter in 1 or 2");
					valid = false;
					continue;
				}
				switch(choice)
				{
					case 1: loop = true; break;
					case 2: loop = false; break;
					default: System.out.println("Please enter in 1 or 2"); valid = false;
				}
			}
			while(!valid);
		}while(loop);//can only use /exit to get out of loop/function
		System.out.println("Save and Exitting to main menu ...");
		log.info("OUT addWorkingTimesForNextMonth\n");
		return false;
	}
	
	/**
	 * @author Bryan Soh
	 * 
	 * @return
	 */
	public void displayAvailability()
	{
		boolean loopflag = true;
		while (loopflag)
		{
			Scanner sc = new Scanner(System.in);
			DatabaseConnection connect = new DatabaseConnection();
			ArrayList<Employee> emList = connect.getEmployees("");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			String days[] = new String[7];
			String today;
			for (int i = 0; i < 7; i++) {
				today = sdf.format(c.getTime());
				c.add(Calendar.DATE, 1);
				days[i] = today;
			}
			ArrayList<EmployeeWorkingTime> workDays = new ArrayList<EmployeeWorkingTime>();
			controller.displayDetailedWorking_Date(days, emList, workDays);
			boolean tryLoop = true;
			do {
				Employee employee = new Employee();
				String input;
				int empKey = 0;
				System.out.println("\nPlease enter employee id to view more or 'quit' to quit");
				input = sc.nextLine();
				if (input.equalsIgnoreCase("quit")) {
					return;
				}

				try {
					Integer.parseInt(input);
				} catch (NumberFormatException e) {
					tryLoop = true;
					System.out.println("Invalid Input");
					;
					break;
				}

				empKey = Integer.parseInt(input);
				
				controller.displayDetailedWorking_Time( empKey,  employee, input,emList,workDays, days,  tryLoop,  loopflag);
				if(tryLoop)
				{
					System.out.println("Invalid Input");
					loopflag=true;
					break;
				}	
			} while (tryLoop);
		}
	}
	
	/**
	 * @author Bryan
	 * Prompt user for previous or next 7 days booking
	 * @param 
	 * @return void
	 */
	public void checkBooking()
	{
		Scanner sc = new Scanner(System.in);
		boolean tryLoop = true;
		String input;
		do
		{
			System.out.println("\nPlease enter 'p' to view past 7 days or 'n' to view next 7 days");
			input = sc.nextLine();
			if (input.equalsIgnoreCase("quit"))
			{
				return;
			} else
			{

				if(input.equals("p"))
				{
					controller.checkPreviousBooking();
					tryLoop=false;
				}
				else if (input.equals("n"))
				{
					controller.checkNextBooking();
					tryLoop=false;
				}
			}
		} while (tryLoop);
		
	}
	
	
}
