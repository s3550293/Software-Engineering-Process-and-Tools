package program;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import org.sqlite.util.StringUtils;

public class Controller
{
	public Controller(){}

	Scanner kb = new Scanner(System.in);

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
			// Attempting to see if the input is valid
			// Checking to see if the input contains any non-alphabetical
			// characters e.g ?>!#%$#12345
			if (employeeFName.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");//exit command

				return false;
			}
			if (checkInputToContainInvalidChar(employeeFName))//checking for invalid characters
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
			// Attempting to see if the input is valid
			// Checking to see if the input contains any non-alphabetical
			// characters e.g ?>!#%$#12345
			if (employeeLName.equalsIgnoreCase("/exit"))//exit command
			{
				System.out.println("Exitting to main menu...");

				return false;
			}
			if (checkInputToContainInvalidChar(employeeLName))//checking for invalid characters
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
			employeePayRate2 = changeInputIntoValidDouble(employeePayRate);//checking for invalid input
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
	 * @author Luke Mason
	 * Checks each letter in the string and see's if the letter is not in the alphabet(lower case) and is not in the alphabet(Upper case)
	 * @param string
	 * @return if input DOES contain invalid char, then it returns true. returns false if valid
	 */
	public boolean checkInputToContainInvalidChar(String string)
	{
		if (string.length() == 0 || string.length() > 40)
		{
			return true;
		}
		for (int i = 0; i < string.length(); i++)
		{
			if ((int) string.charAt(i) < 97 || (int) string.charAt(i) > 122)// checks if the letter is not a lowercase letter
			{
				if ((int) string.charAt(i) < 65 || (int) string.charAt(i) > 90)// checks if the letter is not an upper case letter
				{
					/*if ((int) string.charAt(i) != 32)// checks if the char is
					// not a 'space'
					{*/
						return true;
				}
			}
		}	
		return false;
	}

	/**
	 * @author Luke Mason
	 * Finds all employees by name input from UI and prints them to screen
	 * Calls add employee work time function to add a work times for next month
	 * @return true when /exit is called
	 */
	public boolean addWorkingTimesForEmployeeByName()
	{
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

				return true;
			}
			ArrayList<Employee> employees = connect.getEmployees(employeeName);
			System.out.println("~~~LIST OF EMPLOYEES~~~");
			if (employees.size() == 0)//if array list is empty then the user is prompted to try again
			{
				System.out.println(
						"Sorry but there are no matches for the name '" + employeeName + "'\n Please Try again");
				loopAgain = true;
				continue;
			}
			for (Employee employee : employees)//Displays all employees toString in the array list
			{
				System.out.println(employee.toString());
			}
			System.out.println("~~~~~~~~~~END~~~~~~~~~~");
			do//prompting user to pick an employee or /again to search for employees again
			{
				loopAgain2 = false;
				System.out.println("Which employee would you like to add working times for [/again to search again]?");
				System.out.print("Employee's ID >> ");
				String employeeId = kb.nextLine();
				if (employeeId.equalsIgnoreCase("/exit"))//exit command
				{
					System.out.println("Exitting to main menu...");
					return true;
				}
				if (employeeId.equalsIgnoreCase("/again"))//lets user search for employee again
				{
					loopAgain2 = false;
					loopAgain = true;
					continue;
				}
				int id = changeInputIntoValidInt(employeeId);//Converting id into a valid integer
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
		return false;
	}

	/**
	 * @author Luke Mason
	 * Changes the string number into an Double & Checks if the input contains more than 1 decimal
	 * @param string
	 * @return -1 if the input is a negative number OR if the input contains non-numeric characters except decimal
	 */
	public double changeInputIntoValidDouble(String string)
	{
		try
		{
			Double input = Double.parseDouble(string);
			// Checking to see if the input is a negative, negatives are not
			// used as inputs in this project
			if (input < 0 || input > 1000)
			{
				return -1;
			}
			return input;
		} catch (NumberFormatException e)
		{
			return -1;
		}
	}

	/**
	 * @author Luke Mason
	 * Changes the string number into an Integer
	 * @param string
	 * @return -1 if the input is a negative number OR if the input contains non-numeric characters
	 */
	public int changeInputIntoValidInt(String string)
	{
		try
		{
			Integer input = Integer.parseInt(string);
			// Checking to see if the input is a negative, negatives are not
			// used as inputs in this project
			if (input < 0)
			{
				return -1;
			}
			return input;
		} catch (NumberFormatException e)
		{
			return -1;
		}
	}

	/**
	 * @author Luke Mason
	 * Checking date and it's format before converting, if okay, then convert,
	 * @param date1
	 * @return false if date is > 30 days in future or if date < current date
	 */
	public boolean checkNewDate(Date date1)
	{
		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, 30);
		Date thirtyDaysIntoFuture = c.getTime();

		Calendar b = new GregorianCalendar();
		Date currentTime = b.getTime();

		if (date1.after(thirtyDaysIntoFuture))
		{
			System.out.println("This date is more than 30 days in advance, Try again");
			return false;
		}
		if (date1.before(currentTime))
		{
			System.out.println("You can't set a date in the past!, Try again");
			return false;
		}
		return true;
	}

	/**
	 * @author Luke Mason, David Heang
	 * UI for adding employee working times for next month
	 * @param employeeId
	 * @return only returns false if user somehow breaks out of infinite loop with out exiting function
	 */
	public boolean addWorkingTimesForNextMonth(int employeeId)
	{
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		Controller controller = new Controller();
		DatabaseConnection connect = new DatabaseConnection();

		// check if the input employeeId exists in database
		boolean valid = false, valid2 = false;
		String newDateStr = "";
		String newStartTimeStr = "";
		String newFinishTimeStr = "";
		int count =1;
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
					for(int i = 1;i<=7; i++)
					{
						dateArray[i-1] = sdf.format(c.getTime());//puts each date from every loop into array
						String time = sdf.format(c.getTime());
						System.out.println(i+": " + time);
						c.add(Calendar.DAY_OF_MONTH, i);
					}
					System.out.println("\n/exit to save and exit to main menu\n");
					System.out.print("Enter which day you want to assign the employee to >> ");
					String choiceStr = kb.nextLine();
					if (choiceStr.equalsIgnoreCase("/exit"))
					{
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
					System.out.println("\n");
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
						return true;
					}
					if(choiceStr.equalsIgnoreCase("/back"))
					{
						valid = true;
						valid2 = false;
						continue;
					}
					valid = checkWorkTimeChoice(choiceStr);
					if(!valid){continue;}
					String[] times = new String[2];
					times = allocateWorkTimes(choiceStr);
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
			++count;//counts what loop number is current and used to display to user
		}while(loop);//can only use /exit to get out of loop/function
		System.out.println("Save and Exitting to main menu ...");
		return false;
	}
	public String[] allocateWorkTimes(String choiceStr)
	{
		String[] times = new String[2];
		if(choiceStr.indexOf("M")!= -1)
		{
			times[0] = "8:00";
			times[1] = "12:00";
			if(choiceStr.indexOf("A")!= -1)
			{
				times[1] = "16:00";
				if(choiceStr.indexOf("E")!= -1)
				{
					times[1] = "20:00";
				}
			}
			else if(choiceStr.indexOf("E")!= -1)
			{
				times[1] = "20:00";
				if(choiceStr.indexOf("A")!= -1)
				{
					times[1] = "16:00";
				}
			}
			return times;
		}
		else if(choiceStr.indexOf("A")!= -1)
		{
			times[0] = "12:00";
			times[1] = "16:00";
			if(choiceStr.indexOf("E")!= -1)
			{
				times[1] = "20:00";
				if(choiceStr.indexOf("M")!= -1)
				{
					times[0] = "8:00";
				}
			}
			else if(choiceStr.indexOf("M")!= -1)
			{
				times[0] = "8:00";
				if(choiceStr.indexOf("E")!= -1)
				{
					times[1] = "20:00";
				}
			}
			return times;
		}
		else if(choiceStr.indexOf("E")!= -1)
		{
			times[0] = "16:00";
			times[1] = "20:00";
			if(choiceStr.indexOf("A")!= -1)
			{
				times[0] = "12:00";
				if(choiceStr.indexOf("M")!= -1)
				{
					times[0] = "8:00";
				}
			}
			else if(choiceStr.indexOf("M")!= -1)
			{
				times[0] = "8:00";
				if(choiceStr.indexOf("A")!= -1)
				{
					times[0] = "12:00";
				}
			}
			return times;
		}
		else
		{
			System.out.println("ERROR: Should not be here");
			return null;
		}
	}

	/**
	 * @author Luke Mason
	 * @param choiceStr //Accepts alphabet {M, A, E}
	 * @return false if string contains wrong alphabet or any duplicate symbol or length is > 3 or length is < 1
	 */
	public boolean checkWorkTimeChoice(String choiceStr)
	{
		int amount = 0; 
		int amount2 = 0;
		int amount3 = 0 ;
		int amount4 = choiceStr.length() - choiceStr.replace("M","").replace("A","").replace("E","").length();
		
		if(choiceStr.length()==3)
		{
			amount = choiceStr.length() - choiceStr.replace("M","").replace("A","").length();
			amount2 = choiceStr.length() - choiceStr.replace("M","").replace("E","").length();
			amount3 = choiceStr.length() - choiceStr.replace("E","").replace("A","").length();
			if(amount != 2 || amount2 != 2 || amount3 != 2 || amount4 != 3)
			{
				System.out.println("Invalid input!");
				return false;
			}
		}
		if(choiceStr.equals("ME")||choiceStr.equals("EM")||choiceStr.equals("MM")||choiceStr.equals("EE")||choiceStr.equals("AA"))
		{
			System.out.println("Invalid input!");
			return false;
		}
		if(choiceStr.length() == 1 && amount4 != 1)
		{
			System.out.println("Invalid input!");
			return false;
		}
		if(choiceStr.length()>3 || choiceStr.length()< 1)
		{
			System.out.println("Invalid input!");
			return false;
		}
		return true;
	}
	/*public boolean isOfTypeInt(int num)
	{
		try
		{
			Integer.valueOf(num);
			return true;
		} catch (NumberFormatException numberFormatException)
		{
			return false;
		}
	}

	/*public boolean addWorkingTimesForEmployee()
	{
		@SuppressWarnings("resource")
		Scanner kb = new Scanner(System.in);
		DatabaseConnection connect = new DatabaseConnection();
		String employeeName;
		boolean loopAgain;
		do
		{
			loopAgain = false;
			System.out.print("Search - Enter in Employee's name [/exit to quit] >> ");
			employeeName = kb.nextLine().toLowerCase();
			if (employeeName.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");
				return false;
			}
			// Attempting to see if the input is valid
			// Checking to see if the input contains any non-alphabetical
			// characters e.g ?>!#%$#12345
			if (checkInputToContainInvalidChar(employeeName))
			{
				System.out.println("The name you have entered contains non-alphabetical characters");
				System.out.println("Please try again");
				loopAgain = true;
			}
		} while (loopAgain);
		connect.getEmployees(employeeName);
		/*
		 * do { loopAgain = false; System.out.print("Enter in the pay rate of "
		 * + employeeName + " [/exit to quit] >> "); employeePayRate =
		 * kb.nextLine(); //Attempting to change string into an integer
		 * //Checking to see if the amount contains any non-digit characters
		 * if(employeePayRate.equalsIgnoreCase("/exit")) {
		 * System.out.println("Exitting to main menu...");
		 *
		 * return false; } employeePayRate2 =
		 * changeInputIntoValidDouble(employeePayRate); if(employeePayRate2<0) {
		 * System.out.
		 * println("The amount you have entered contains invalid characters, is less than 0 or greater that 10000 "
		 * ); System.out.println("Please try again"); loopAgain = true; } }
		 * while(loopAgain);
		 
		return false;
	}*/

	/**
	 * @author Joseph Garner
	 * Converts type String to type Date
	 * @param date
	 * @return Date date
	 */
	public Date convertStringToDate(String date)
	{
		Date _date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			_date = sdf.parse(date);
		} catch (ParseException e)
		{
			System.out.println("Convert To Date Error: " + e.getMessage());

		}
		return _date;
	}

	/**
	 * @author Joseph Garner
	 * Use Convert Date to String when reading/printing from database
	 * @param date
	 * @return String date
	 */
	public String convertDateToString(Date date)
	{
		String _date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		_date = sdf.format(date);
		return _date;
	}

	/**
	 * @author Joseph Garner
	 * Use Convert String to Time when entering into the database
	 * @param time
	 * @return Date time
	 */
	public Date convertStringToTime(String time)
	{
		Date _time = null;
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		try
		{
			_time = sdf.parse(time);
		} catch (ParseException e)
		{
			System.out.println(e.getMessage());
			return _time;
		}
		return _time;
	}
	/**
	 * @author Joseph Garner
	 * Use Convert Time to String when entering into the database
	 * @param time
	 * @return string time
	 */
	public String convertTimeToString(Date time)
	{
		String _time = null;
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		_time = sdf.format(time);
		return _time;
	}

	/**
	 * @author Joseph Garner
	 * used to compare two times and get the duration between them
	 * @param time1
	 * @param time2
	 * @return
	 */
	public long getTimeDifference(Date time1, Date time2)
	{
		long val = 0;
		try
		{
			long diff = time2.getTime() - time1.getTime();
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			val = diffMinutes;
			if (diffHours != 0)
			{
				val += diffHours * 60;
			}
			System.out.print(val + " minutes, ");

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return val;
	}

	
	/**
	 * @author Joseph Garner
	 * used to compare two dates and get the duration
	 * @param date1
	 * @param date2
	 * @return
	 */
	public long getDateDifference(Date date1, Date date2)
	{
		long val = 0;
		try
		{
			long diff = date2.getTime() - date1.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			val = diffDays;
			// System.out.print(diffDays + " days, ");

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return val;
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
			boolean flag = true;
			String days[] = new String[7];
			String today;
			for (int i = 0; i < 7; i++)
			{
				c.add(Calendar.DATE, 1);
				today = sdf.format(c.getTime());
				days[i] = today;
			}
			System.out.printf("\n%s", "ID");
			System.out.printf("%-2s %s", "", "Employee");
			System.out.printf("%-20s %s", "", days[0]);
			System.out.printf("%-3s %s", "", days[1]);
			System.out.printf("%-3s %s", "", days[2]);
			System.out.printf("%-3s %s", "", days[3]);
			System.out.printf("%-3s %s", "", days[4]);
			System.out.printf("%-3s %s", "", days[5]);
			System.out.printf("%-3s %s\n", "", days[6]);
			System.out.print(
					"-------------------------------------------------------------------------------------------------------------------------------------");
			ArrayList<EmployeeWorkingTime> workDays = new ArrayList<EmployeeWorkingTime>();
			for (Employee e : emList)
			{
				System.out.printf("\n%d %-2s %-20s", e.getId(), "", e.getName());
				workDays = connect.getEmployeeWorkingTimes(e.getId());
				for (int j = 0; j < 7; j++)
				{
					if (!workDays.isEmpty())
					{
						if (days[j].equals(matchDate(days[j], workDays)))
						{
							System.out.printf("%-8s %-5s", "", "Avail");
						} else
						{
							System.out.printf("%-8s %-5s", "", "-----");
						}
					} else
					{
						System.out.printf("%-8s %-5s", "", "-----");
					}
				}

			}
			Employee employee = new Employee();
			boolean tryLoop = true;
			String input;
			int empKey = 0;
			do
			{
				System.out.println("\nPlease enter employee id to view more or 'quit' to quit");
				input = sc.nextLine();
				if (input.equalsIgnoreCase("quit"))
				{
					return;
				} else
				{

					try
					{
						empKey = Integer.parseInt(input);
						tryLoop = false;
					} catch (Exception e)
					{
						System.out.println("Invalid Input");
					}
				}
			} while (tryLoop);
			employee = connect.getEmployee(empKey);
			workDays = connect.getEmployeeWorkingTimes(empKey);
			System.out.printf("\nName: %-15s Payrate: %-2.2f\n", employee.getName(), employee.getPayRate());
			System.out.printf("\n%-15s %-15s %s\n", "Date", "Start Time", "End Time");
			System.out.println("----------------------------------------------------");
			for (int j = 0; j < 7; j++)
			{
				System.out.printf("%s", days[j]);
				if (!workDays.isEmpty())
				{
					if (days[j].equals(matchDate(days[j], workDays)))
					{
						System.out.printf("%6s %-15s %s\n", "", getTime("start", days[j], workDays),
								getTime("end", days[j], workDays));
					} else
					{
						System.out.printf("%6s %-15s %s\n", "", "-----", "-----");
					}
				} else
				{
					System.out.printf("%6s %-15s %s\n", "", "-----", "-----");
				}
			}

			// sc.close();
		}
	}

	/**
	 * @author Bryan Soh
	 * 
	 * @param date
	 * @param workDays
	 * @return
	 */
	private String matchDate(String date, ArrayList<EmployeeWorkingTime> workDays)
	{
		for (EmployeeWorkingTime ew : workDays)
		{
			if (date.equals(convertDateToString(ew.getDate())))
			{
				return convertDateToString(ew.getDate());
			}
		}
		return "";

	}

	
	/**
	 * @author Bryan Soh
	 * 
	 * @param time
	 * @param date
	 * @param workDays
	 * @return
	 */
	private String getTime(String time, String date, ArrayList<EmployeeWorkingTime> workDays)
	{
		if (time.equalsIgnoreCase("start"))
		{
			for (EmployeeWorkingTime ew : workDays)
			{
				if (date.equals(convertDateToString(ew.getDate())))
				{
					return convertTimeToString(ew.getStartTime());
				}
			}
		} else
		{
			for (EmployeeWorkingTime ew : workDays)
			{
				if (date.equals(convertDateToString(ew.getDate())))
				{
					return convertTimeToString(ew.getEndTime());
				}
			}
		}
		return "";
	}

}
