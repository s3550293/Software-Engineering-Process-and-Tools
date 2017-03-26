package program;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Controller
{
	public Controller(){}

	Scanner kb = new Scanner(System.in);

	/*
	 *
	 * LUKE MASON
	 *
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
		// Enter in the employees Full name correctly
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
				System.out.println("Exitting to main menu...");

				return false;
			}
			if (checkInputToContainInvalidChar(employeeFName))
			{
				System.out.println("The name you have entered contains non-alphabetical characters");
				System.out.println("Please try again");
				loopAgain = true;
			}
		} while (loopAgain);
		do
		{
			loopAgain = false;
			System.out.print("Enter in the new employee's last name only [/exit to quit] >> ");
			employeeLName = kb.nextLine().toLowerCase();
			// Attempting to see if the input is valid
			// Checking to see if the input contains any non-alphabetical
			// characters e.g ?>!#%$#12345
			if (employeeLName.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");

				return false;
			}
			if (checkInputToContainInvalidChar(employeeLName))
			{
				System.out.println("The name you have entered contains non-alphabetical characters");
				System.out.println("Please try again");
				loopAgain = true;
			}
		} while (loopAgain);
		employeeName = employeeFName + " " + employeeLName;
		do
		{
			loopAgain = false;
			System.out.print("Enter in the pay rate of " + employeeName + " [/exit to quit] >> ");
			employeePayRate = kb.nextLine();
			// Attempting to change string into an integer
			// Checking to see if the amount contains any non-digit characters
			if (employeePayRate.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");

				return false;
			}
			employeePayRate2 = changeInputIntoValidDouble(employeePayRate);
			if (employeePayRate2 < 0)
			{
				System.out.println(
						"The amount you have entered contains invalid characters, is less than 0 or greater that 1000 ");
				System.out.println("Please try again");
				loopAgain = true;
			}
		} while (loopAgain);
		do
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
				connect.addEmployee(employeeName, employeePayRate2);
				ArrayList<Employee> employees = connect.getEmployees(employeeName);
				if(employees.size() > 1)
				{
					System.out.println("ERROR: There are two employees in the database with the same name, cannot add working times");
					return true;
				}
				for (Employee employee : employees) 
				{
					if(!(addWorkingTimesForNextMonth(employee.getId()))) 
					  {
						  System.out.println("Save and exiting to main menu"); 
					  }
				}
				return true;
			case 2:
				connect.addEmployee(employeeName, employeePayRate2);
				System.out.println("Employee " + employeeName + " added!");
				return true;
			case 3:
				do
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

	// Checks each letter in the string and see's if the letter is not in the
	// alphabet(lower case) and is not in the alphabet(Upper case)
	// if it is not in either alphabet, then it returns true
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

	// finds all employees by name and prints to screen then calls add employee
	// work time function
	public boolean addWorkingTimesForEmployeeByName()
	{
		DatabaseConnection connect = new DatabaseConnection();
		String employeeName;
		boolean loopAgain;
		boolean loopAgain2;
		do
		{
			loopAgain = false;
			System.out.println("~~~Search for Employee~~~[/exit to quit]");
			System.out.print("Employee Name >> ");
			employeeName = kb.nextLine().toLowerCase();
			// Attempting to see if the input is valid
			// Checking to see if the input contains any non-alphabetical
			// characters e.g ?>!#%$#12345
			if (employeeName.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");

				return false;
			}
			ArrayList<Employee> employees = connect.getEmployees(employeeName);
			System.out.println("~~~LIST OF EMPLOYEES~~~");
			for (Employee employee : employees)
			{
				
				System.out.println(employee.toString());
				if (employees.size() == 0)
				{
					System.out.println(
							"Sorry but there are no matches for the name '" + employeeName + "'\n Please Try again");
					loopAgain = true;
					continue;
				}
			}
			System.out.println("~~~~~~~~~~END~~~~~~~~~~");
			do
			{
				loopAgain2 = false;
				System.out.println("Which employee would you like to add working times for?");
				System.out.print("Employee's ID >> ");
				String employeeId = kb.nextLine();
				if (employeeId.equalsIgnoreCase("/exit"))
				{
					System.out.println("Exitting to main menu...");
					return false;
				}
				int id = changeInputIntoValidInt(employeeId);
				if (id < 0)
				{
					System.out.println("Invalid ID, Try again");
					loopAgain2 = true;
					continue;
				}
				boolean idExists = false;
				for (Employee employee : employees)
				{
					if (employee.getId() == id)
					{
						idExists = true;
						addWorkingTimesForNextMonth(id);
					}
				}
				if (!idExists)
				{
					System.out.println(
							"ID MISMATCH: There is no ID that matches an employee you have just searched for\n Try Again");
					loopAgain2 = true;
				}
			} while (loopAgain2);
		} while (loopAgain);
		return false;
	}

	// Changes the string number into an Double
	// return -1 if the input is a negative number OR if the input contains
	// non-numeric characters except decimal
	// Checks if the input contains more than 1 decimal
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

	// Changes the string number into an Int
	// return -1 if the input is a negative number OR if the input contains
	// non-numeric characters
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

	// Checking date and it's format before converting, if okay, then convert,
	// return date
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

	// DAVID
	/*
	 * UI for adding employee working time for next month Status: In development
	 */
	public boolean addWorkingTimesForNextMonth(int employeeId)
	{
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		Controller controller = new Controller();
		DatabaseConnection connect = new DatabaseConnection();

		// check if the input employeeId exists in database
		boolean valid, valid2;
		int year = 2017, month = 1, day = 1;
		Date startTime = null, finishTime = null, newDate = null;
		Business bmenu = new Business();
		String newDateStr;
		String newStartTimeStr;
		String newFinishTimeStr;
		int count =1;
		boolean infiniteloop = true;
		do
		{
			System.out.println("/exit TO SAVE & EXIT ANYTIME");
			System.out.println("~~~~Adding Work Time "+count+" ~~~~");
			do
			{
				valid = false;
				while (valid == false)
				{
					System.out.print("Please Enter year:\nyear >> ");
					String yearStr = sc.nextLine();
					if (yearStr.equalsIgnoreCase("/exit"))
					{
						return true;
					}
					// convert to Integer
					year = controller.changeInputIntoValidInt(yearStr);
					if (year > 0)// will be -1 if the input is invalid
					{
						valid = true;
					} else
					{
						System.out.println("Innappropriate year!");
						valid = false;
					}
	
				
				}
				valid = false;
				while (valid == false)
				{
					System.out.print("Please Enter month (1-12):\nmonth >> ");
					String monthStr = sc.nextLine();
					if (monthStr.equalsIgnoreCase("/exit"))
					{
						return true;
					}
					month = controller.changeInputIntoValidInt(monthStr);
					if (month > 0 && month < 13)// will be -1 if the input is invalid
					{
						valid = true;
					} else
					{
						System.out.println("Innappropriate month!");
						valid = false;
					}
					
				}
				valid = false;
				while (valid == false)
				{
					System.out.print("Please Enter day:\nday >> ");
					String dayStr = sc.nextLine();
					if (dayStr.equalsIgnoreCase("/exit"))
					{
						return true;
					}
					day = controller.changeInputIntoValidInt(dayStr);
					if (day > 0 && day < 32)
					{
						valid = true;
					} else
					{
						System.out.println("Innappropriate day!");
						valid = false;
					}
				
				}
	
				// Making a date from year, month and day integers
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month-1);
				cal.set(Calendar.DAY_OF_MONTH, day);
				newDate = cal.getTime();
				if (!checkNewDate(newDate))
				{
					valid = false;
				}
			} while (!valid);
			do
			{
				valid2 = false;
				valid = false;
				while (valid == false)
				{
					int hours = 0;
					int minutes = 0;
					valid = true;
					do
					{
						valid = true;
						System.out.print("Please Enter start time (0-23)\nHours >> ");
						String startTimeHourStr = sc.nextLine();
						if (startTimeHourStr.equalsIgnoreCase("/exit"))
						{
							System.out.println("Exitting to main menu...");
							return true;
						}
						hours = changeInputIntoValidInt(startTimeHourStr);
						if (hours < 0 || hours > 23)
						{
							System.out.println("Incorrect amount of hours, Try Again");
							valid = false;
						}
					} while (!valid);
					do
					{
						valid = true;
						System.out.print("Minutes(0-59) >> ");
						String startTimeMinStr = sc.nextLine();
						if (startTimeMinStr.equalsIgnoreCase("/exit"))
						{
							return true;
						}
						minutes = changeInputIntoValidInt(startTimeMinStr);
						if (minutes < 0 || minutes > 59)
						{
							System.out.println("Incorrect amount of hours, Try Again");
							valid = false;
						}
					} while (!valid);
	
					String hoursStr = String.format("%02d", hours);
					String minsStr = String.format("%02d", minutes);
					String startTimeStr = hoursStr + ":" + minsStr;
					startTime = convertStringToTime(startTimeStr);
					if (startTime == null)
					{
						System.out.println("This time is not in the correct format HH:MM, Try Again");
						valid = false;
						continue;
					}
				}
				valid = false;
				while (valid == false)
				{
					int hours = 0;
					int minutes = 0;
					valid = true;
					do
					{
						valid = true;
						System.out.print("Please Enter finish time\nHours >> ");
						String startTimeHourStr = sc.nextLine();
						if (startTimeHourStr.equalsIgnoreCase("/exit"))
						{
							return true;
						}
						hours = changeInputIntoValidInt(startTimeHourStr);
						if (hours < 0 || hours > 23)
						{
							System.out.println("Incorrect amount of hours, Try Again");
							valid = false;
						}
					} while (!valid);
					do
					{
						valid = true;
						System.out.print("Minutes >> ");
						String finishTimeMinStr = sc.nextLine();
						if (finishTimeMinStr.equalsIgnoreCase("/exit"))
						{
							return true;
						}
						minutes = changeInputIntoValidInt(finishTimeMinStr);
						if (minutes < 0 || minutes > 59)
						{
							System.out.println("Incorrect amount of hours, Try Again");
							valid = false;
						}
					} while (!valid);
	
					String hoursStr = String.format("%02d", hours);
					String minsStr = String.format("%02d", minutes);
					String finishTimeStr = hoursStr + ":" + minsStr;
					finishTime = convertStringToTime(finishTimeStr);
					if (finishTime == null)
					{
						System.out.println("This time is not in the correct format HH:MM, Try Again");
						valid = false;
						continue;
					}
				}
	
			} while (valid2);
	
			newDateStr = convertDateToString(newDate);
			newStartTimeStr = convertTimeToString(startTime);
			newFinishTimeStr = convertTimeToString(finishTime);
			connect.addEmployeeWorkingTime(employeeId, newDateStr, newStartTimeStr, newFinishTimeStr);
			++count;
		}while(infiniteloop);
		return true;
	}

	public boolean checkNewStartTime(Date startTime)
	{
		return false;
	}

	public boolean checkNewEndTime(Date endTime)
	{
		return false;
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

	/*
	 *
	 * //JOSEPH Use Convert String to Date when entering data into the database
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

	/*
	 * Use Convert Date to String when reading/printing from database
	 */
	public String convertDateToString(Date date)
	{
		String _date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		_date = sdf.format(date);
		return _date;
	}

	/*
	 * Use Convert String to Time when entering into the database
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

	public String convertTimeToString(Date time)
	{
		String _time = null;
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		_time = sdf.format(time);
		return _time;
	}

	/*
	 * used to compare two times and get the duration
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

	/*
	 * used to compare two dates and get the duration
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
