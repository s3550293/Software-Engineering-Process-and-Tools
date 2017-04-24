package program;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ListIterator;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class BusinessMenu
{
	
	private static Logger log = Logger.getLogger(BusinessMenu.class);
	private boolean flag = true;
	private Controller controller = new Controller();
	DatabaseConnection connect = new DatabaseConnection();
	public BusinessMenu(){log.setLevel(Level.WARN);}
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
				//addNewEmployee();
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
				//addWorkingTimesForEmployeeByName();
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
	 * Find Employees By Name, Then pick One employee By ID
	 * @param String employeeName
	 * @param String employeeId
	 */
				/**
				 * Checks to see if there is 0 matches with the input name
				 * @param employeeName
				 * @return false when 0 matches, true when 1 or more matches
				 */
				public boolean checkEmployeesWithName(String employeeName)
				{
					DatabaseConnection connect = new DatabaseConnection();
					ArrayList<Employee> employees = connect.getEmployees(employeeName);
					if (employees.size() == 0)//if array list is empty then the user is prompted to try again
					{
						//ERROR MESSAGE
						controller.messageBox("WARN", "Match Error", "Employee name entered has no matches","Sorry but there are no matches for the name '" + employeeName + ", Please Try again");
						return false;
					}
					return true;
				}
				/**
				 * Finds all employees by name input from UI and prints them to screen 
				 * @param employeeName
				 * @return array list employees so that the next function can use that list
				 */
				public ArrayList<Employee> viewEmployeesWithName(String employeeName)
				{
					DatabaseConnection connect = new DatabaseConnection();
					log.info("IN viewEmployeesWithName\n");
					ArrayList<Employee> employees = connect.getEmployees(employeeName);
					//System.out.println("~~~LIST OF EMPLOYEES~~~\n");
					for (Employee employee : employees)//Displays all employees toString in the array list
					{
						System.out.println(employee.toString());
						//Prints Each Employee's Details
					}
					//System.out.println("\n~~~~~~~~~~END~~~~~~~~~~");
					return employees;
				}
				/**
				 * Converts string ID to Int ID
				 * @param employeeId
				 * @return
				 */
				public int StrIdToInt(String employeeId)
				{
					int id = controller.changeInputIntoValidInt(employeeId);//Converting id into a valid integer
					return id;
				}
				/**
				 * Shows if employeeID matches an employee in the list
				 * @param employees
				 * @param employeeId
				 * @return False when employeeId does not match a shown employee from viewEmployeesWithName, True when matched.
				 */
				public boolean CheckPickEmployeeFromList(ArrayList<Employee> employees, int employeeId)
				{
					if (employeeId < 0)//if id is invalid, prompts user for id again
					{
						controller.messageBox("WARN", "Choosing Error", "EmployeeID entered is not valid","EmployeeID entered is not valid, Try again");
						//ERROR MESSAGE
						return false;
					}
					for (Employee employee : employees)//checks to see if id exists in the selection of employees in array
					{
						if (employee.getId() == employeeId)//calls working time functions and starts adding them for matched employee
						{
							return true;
						}
					}
					//System.out.println("ID MISMATCH: There is no ID that matches an employee you have just searched for\n Try Again");
					//ERROR MESSAGE
					log.info("OUT addWorkingTimesForEmployeeByName\n");
					return false;
				}
	
	




	/**
	 * @author Luke Mason
	 * Used to check information to add a new employee to database
	 * @return false if the program breaks out of loop. True if /exit is called or user exits
	 */
	/**
				 * Error checks the first OR last name
				 * @param employeeFLName
				 * @return false when invalid name, True when valid
				 */
				public boolean checkEmployeeFirstOrLastName(String employeeFLName)
				{
					if ((controller.checkInputToContainInvalidChar(employeeFLName)))//checking for invalid characters
					{
						//ERROR MESSAGE
						//controller.messageBox("WARN", "Match Error", "Employee name Contains invalid characters","The name you have entered contains non-alphabetical characters, Please try again");
						return false;
					}
					return true;
				}
				/**
				 * Convert string to Double
				 * @param employeePayRate
				 * @return double
				 */
				public double strPayRateToDouble(String employeePayRate)
				{
					double employeePayRate2 = controller.changeInputIntoValidDouble(employeePayRate);//checking for invalid input
					return employeePayRate2;
				}
				/**
				 * Checks if employee pay rate is less than 0 (Error)
				 * @param employeePayRate
				 * @return
				 */
				public boolean checkEmployeePayRate(double employeePayRate)
				{
					if (employeePayRate < 0)
					{
						//ERROR MESSAGE
						//controller.messageBox("WARN", "Pay Rate Error", "Employee pay rate is invalid","The amount you have entered contains invalid characters, is less than 0 or is greater than 1000, Please Try Again");
						//System.out.println("The amount you have entered contains invalid characters, is less than 0 or greater that 1000 ");
						//System.out.println("Please try again");
						return false;
					}
					return true;
				}
				/**
				 * Adds an employee to database
				 * @param employeeFName
				 * @param employeeLName
				 * @param employeePayRate
				 */
				public void option1AddEmployee(String employeeFName,String employeeLName,double employeePayRate)
				{
					DatabaseConnection connect = new DatabaseConnection();
					String employeeName = employeeFName + " " + employeeLName;//concatenating first and last name into name
					connect.addEmployee(employeeName, employeePayRate);//adding employee
				}
				/**
				 * Adds employee to database and calls change Work Time roster for employee
				 * @param employeeFName
				 * @param employeeLName
				 * @param employeePayRate
				 */
				public void option2AddEmployeeAndWorkingTimes(String employeeFName
				,String employeeLName,double employeePayRate,boolean btnSunMorning,boolean btnSunAfternoon
				,boolean btnSunEvening,boolean btnMonMorning,boolean btnMonAfternoon,boolean btnMonEvening
				,boolean btnTueMorning,boolean btnTueAfternoon,boolean btnTueEvening,boolean btnWedMorning
				,boolean btnWedAfternoon,boolean btnWedEvening,boolean btnThurMorning,boolean btnThurAfternoon
				,boolean btnThurEvening,boolean btnFriMorning,boolean btnFriAfternoon,boolean btnFriEvening
				,boolean btnSatMorning,boolean btnSatAfternoon,boolean btnSatEvening)
				{
					DatabaseConnection connect = new DatabaseConnection();
					option1AddEmployee(employeeFName,employeeLName,employeePayRate);
					String employeeName = employeeFName + " " + employeeLName;//concatenating first and last name into name
					ArrayList<Employee> employees = connect.getEmployees(employeeName);//adding working times to employee just made
					ListIterator<Employee> employees2 = employees.listIterator();
					//This is for if more than one employee has the same name as searched
					int id = -1;
					while(employees2.hasNext())
					{
						//Adds working times to the LAST employee (should be the recent one just added)
						if(!employees2.hasNext())
						{
							id =((Employee)employees2).getId();
						} 
					}
					if(id == -1)
					{
						controller.messageBox("WARN", "Finding last employee Error", "Couldn't get ID of last employee in array ","Please consult Luke Mason as he programmed this piece of shit");	
					}
					else
					{
						connect.clearWorkTimes(id);
						addWorkingTimes(id,btnSunMorning,btnSunAfternoon,btnSunEvening,btnMonMorning,btnMonAfternoon,btnMonEvening,btnTueMorning,btnTueAfternoon, btnTueEvening,btnWedMorning,btnWedAfternoon, btnWedEvening, btnThurMorning, btnThurAfternoon, btnThurEvening, btnFriMorning, btnFriAfternoon, btnFriEvening, btnSatMorning, btnSatAfternoon, btnSatEvening);
					}
					
				}
				/**
				 * Passes all the button values in its parameters and then assigns work times according to those times to the specified id.
				 * @param id
				 * @param btnSunMorning
				 * @param btnSunAfternoon
				 * @param btnSunEvening
				 * @param btnMonMorning
				 * @param btnMonAfternoon
				 * @param btnMonEvening
				 * @param btnTueMorning
				 * @param btnTueAfternoon
				 * @param btnTueEvening
				 * @param btnWedMorning
				 * @param btnWedAfternoon
				 * @param btnWedEvening
				 * @param btnThurMorning
				 * @param btnThurAfternoon
				 * @param btnThurEvening
				 * @param btnFriMorning
				 * @param btnFriAfternoon
				 * @param btnFriEvening
				 * @param btnSatMorning
				 * @param btnSatAfternoon
				 * @param btnSatEvening
				 */
				public void addWorkingTimes(int id,boolean btnSunMorning,boolean btnSunAfternoon,boolean btnSunEvening,boolean btnMonMorning,boolean btnMonAfternoon,boolean btnMonEvening,boolean btnTueMorning,boolean btnTueAfternoon,boolean btnTueEvening,boolean btnWedMorning,boolean btnWedAfternoon,boolean btnWedEvening,boolean btnThurMorning,boolean btnThurAfternoon,boolean btnThurEvening,boolean btnFriMorning,boolean btnFriAfternoon,boolean btnFriEvening,boolean btnSatMorning,boolean btnSatAfternoon,boolean btnSatEvening)
				{
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String[] dateArray = new String[7];
					Calendar c = Calendar.getInstance();
					for(int i = 1;i<=7; i++)
					{
						dateArray[i-1] = sdf.format(c.getTime());//puts each date from every loop into array
						c.add(Calendar.DAY_OF_MONTH, 1);
					}
					for(int i =0; i<dateArray.length;i++)
					{
						Calendar d = Calendar.getInstance();
						Date date = controller.convertStringToDate(dateArray[i]);
						d.setTime(date);
						int dayOfWeek = d.get(Calendar.DAY_OF_WEEK);
						switch(dayOfWeek)
						{
							case 1: 
								addDayWorkingTime("Sunday",id,dateArray[i],btnSunMorning, btnSunAfternoon, btnSunEvening);
								break;
							case 2: 
								addDayWorkingTime("Monday",id,dateArray[i],btnMonMorning, btnMonAfternoon, btnMonEvening);
								break;
							case 3: 
								addDayWorkingTime("Tuesday",id,dateArray[i],btnTueMorning, btnTueAfternoon, btnTueEvening);
								break;
							case 4: 
								addDayWorkingTime("Wednesday",id,dateArray[i],btnWedMorning, btnWedAfternoon, btnWedEvening);
								break;
							case 5: 
								addDayWorkingTime("Thursday",id,dateArray[i],btnThurMorning, btnThurAfternoon, btnThurEvening);
								break;
							case 6: 
								addDayWorkingTime("Friday",id,dateArray[i],btnFriMorning, btnFriAfternoon, btnFriEvening);
								break;
							case 7: 
								addDayWorkingTime("Saturday",id,dateArray[i],btnSatMorning, btnSatAfternoon, btnSatEvening);
								break;
							default:
								controller.messageBox("WARN", "Error: Something happened with dayOfWeek", "dayOfWeek did not register to a day","Please consult Luke Mason for the crap coding");
						}			
					}
				}
				/**
				 * assigns time blocks to a single time block 
				 * @param morning
				 * @param afternoon
				 * @param evening
				 * @return number referring to a time block, -1 if time block is invalid
				 */
				public int checkWorkTimes(boolean morning, boolean afternoon, boolean evening)
				{
					if(morning && afternoon && evening)
					{
						return 1;
					}
					else if(morning && afternoon)
					{
						return 2;
					}
					else if(afternoon && evening)
					{
						return 3;
					}
					else if(morning && evening)
					{
						return -1;
					}
					else if(morning)
					{
						return 4;
					}
					else if(afternoon)
					{
						return 5;
					}
					else if(evening)
					{
						return 6;
					}
					else
					{
						return 0;
					}
				}
				/**
				 * Assigns timeBlock number to a startTime and endTime
				 * @param checkTimes
				 * @return
				 */
				public String[] getStartEndTimes(int checkTimes)
				{
					String[] array = new String[2];
					array[0] = "";//Start Time
					array[1] = "";//End Time
					switch(checkTimes)
					{
						case 0:
							break;
						case 1: array[0] = "8:00";
								array[1] = "20:00";
								break;
						case 2: 
							array[0] = "8:00";
							array[1] = "16:00";
								break;
						case 3: 
							array[0] = "12:00";
							array[1] = "20:00";
								break;
						case 4: 
							array[0] = "8:00";
							array[1] = "12:00";
								break;
						case 5: 
							array[0] = "12:00";
							array[1] = "16:00";
								break;
						case 6: 
							array[0] = "16:00";
							array[1] = "20:00";
								break;
						default:
							//controller.messageBox("WARN", "Error: Something happened with dayOfWeek", "dayOfWeek did not register to a day","Please consult Luke Mason for the crap coding");
							break;
					}
					return array;
				}
				/**
				* Sets the date as a work time and the same for next week and week after for a year ahead
				 * @author Luke Mason
				 * @param date
				 * @return
				 */
				public boolean set7DayRosterTime(int employeeID,String date, String startTime, String endTime)
				{
					Date Date;
					for(int i = 0; i<54;i++)//54 = 54 weeks in a year (setting work time every week for 54 weeks in advance)
					{
						connect.addEmployeeWorkingTime(employeeID, date, startTime, endTime);
						Date = controller.convertStringToDate(date);
						if(Date == null)
						{
							return false;
						}
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(Date);
						calendar.add(Calendar.DAY_OF_MONTH, 7);
						Date = calendar.getTime();
						date = controller.convertDateToString(Date);
					}
					return true;
				}
				/**
				 * checks return value of checkWorkingTimes and getStartEndTimes. Then Calls set7dayRosterTime
				 * @param day
				 * @param employeeID
				 * @param date
				 * @param morning
				 * @param afternoon
				 * @param evening
				 * @return
				 */
				public boolean addDayWorkingTime(String day,int employeeID,String date,boolean morning, boolean afternoon, boolean evening)
				{
					String[] array = new String[2];
					int check = checkWorkTimes(morning, afternoon, evening);
					array = getStartEndTimes(check);
					if(check == -1)
					{
						controller.messageBox("WARN", "Work Time Error", "Work time selected on "+day+" is invalid","Work time selected on "+day+" is invalid <"+day+" Work times not added>, Morning and Evening are not valid, please select a joined ONE block of time and try again");
						return false;
					}
					if(array[0].equals(""))
					{
						return false;//No working times assigned to this day
					}
					set7DayRosterTime(employeeID,date,array[0], array[1]);
					return true;
				}
			
	
	
	/**
	 * @author Luke Mason, David (Panhaseth Heang)
	 * UI for adding employee working times for next month
	 * @param employeeId
	 * @return only returns false if user somehow breaks out of infinite loop with out exiting function
	 */
	/*public boolean changeWorkingTimeRoster(int employeeId)
	{
		//@SuppressWarnings("resource")
		//Scanner kb = new Scanner(System.in);
		log.info("IN addWorkingTimesForNextMonth\n");
		Controller controller = new Controller();
		DatabaseConnection connect = new DatabaseConnection();

		// check if the input employeeId exists in database
		//boolean valid = false, valid2 = false;
		String newDateStr = "";
		String newStartTimeStr = "";
		String newFinishTimeStr = "";
		//boolean loop = false;
		//do//Loop, exit with /exit
		//{
		//	do{
		//		valid2 = true;
		//		do
			//	{
				//	valid = true;
	public void viewNext7Days()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String[] dateArray = new String[7];
		Calendar c = Calendar.getInstance();
		System.out.println("----Next 7 Days----");
		for(int i = 1;i<=7; i++)
		{
			dateArray[i-1] = sdf.format(c.getTime());//puts each date from every loop into array
			String time = sdf.format(c.getTime());
			//System.out.println(i+": " + time);
			//PRINT EACH DAY OUT TO GUI
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
					//System.out.println("\n/exit to save and exit to main menu\n");
					//System.out.print("Select a day >> ");
					//String choiceStr = kb.nextLine();
					/*if (choiceStr.equalsIgnoreCase("/exit"))
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
	}*/
	

	/**
	 * @author Bryan Soh
	 * 
	 * @return
	 */
	@SuppressWarnings("resource")
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
					loopflag = true;
					System.out.println("Invalid Input.");
					break;
				}

				empKey = Integer.parseInt(input);
				
				if(controller.displayDetailedWorking_Time( empKey,  employee, input,emList,workDays, days,  tryLoop,  loopflag))
				{
					System.out.println("Invalid Input");
					loopflag=true;
					break;
				}
				else{
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
	@SuppressWarnings("resource")
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
