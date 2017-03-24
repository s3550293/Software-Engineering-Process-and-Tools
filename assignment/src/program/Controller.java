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
		//Enter in the employees Full name correctly
		do
		{
			loopAgain = false;
			System.out.print("Enter in the new employee's first name only [/exit to quit] >> ");
			employeeFName = kb.nextLine().toLowerCase();
			//Attempting to see if the input is valid
			//Checking to see if the input contains any non-alphabetical characters e.g ?>!#%$#12345
			if(employeeFName.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");
				
				return false;
			}
			if(checkInputToContainInvalidChar(employeeFName))
			{
				System.out.println("The name you have entered contains non-alphabetical characters");
				System.out.println("Please try again");
				loopAgain = true;
			}
		}
		while(loopAgain);
		do
		{
			loopAgain = false;
			System.out.print("Enter in the new employee's last name only [/exit to quit] >> ");
			employeeLName = kb.nextLine().toLowerCase();
			//Attempting to see if the input is valid
			//Checking to see if the input contains any non-alphabetical characters e.g ?>!#%$#12345
			if(employeeLName.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");
				
				return false;
			}
			if(checkInputToContainInvalidChar(employeeLName))
			{
				System.out.println("The name you have entered contains non-alphabetical characters");
				System.out.println("Please try again");
				loopAgain = true;
			}
		}
		while(loopAgain);
		employeeName = employeeFName +" "+employeeLName;
		do
		{
			loopAgain = false;
			System.out.print("Enter in the pay rate of " + employeeName + " [/exit to quit] >> ");
			employeePayRate = kb.nextLine();
			//Attempting to change string into an integer
			//Checking to see if the amount contains any non-digit characters
			if(employeePayRate.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");
				
				return false;
			}
			employeePayRate2 = changeInputIntoValidDouble(employeePayRate);
			if(employeePayRate2<0)
			{
				System.out.println("The amount you have entered contains invalid characters, is less than 0 or greater that 10000 ");
				System.out.println("Please try again");
				loopAgain = true;
			}
		}
		while(loopAgain);
		do
		{
			loopAgain = false;
			System.out.println("What would you like to do now?");
			System.out.println("1. Save and Add " + employeeName +"'s working times for the next month");
			System.out.println("2. Save and Exit");
			System.out.println("3. Exit without Saving");
			System.out.print("Select an option >> ");
			int answer = kb.nextInt();
			kb.nextLine();
			switch(answer)
			{
				case 1:
					connect.addEmployee(employeeName, employeePayRate2);
					/*if(!(addWorkingTimeForNextMonth(numberOfEmployees)))
					{
						System.out.println("Exiting to main menu");
						return false;
					}*/
					System.out.println("ADD WORKING TIME FOR THIS EMPLOYEE NOT IMPLEMEMENTED, New Employee added");
					
					return true;
				case 2: connect.addEmployee(employeeName, employeePayRate2); 
							
					return true;
				case 3: 
					do
					{
						loopAgain = false;
						System.out.print("Are you sure you don't want to save the employee to the database?(Y/N)");
						String exit = kb.nextLine();
						if(exit.equalsIgnoreCase("y"))
						{
							
							return false;
						}
						else if(exit.equalsIgnoreCase("n"))
						{
							continue;
						}
						else
						{
							System.out.println("Please enter in \"y\" or \"n\" only");
							loopAgain = true;
						}
					}
					while(loopAgain);
				default: System.out.println("Invalid option, Try again"); 
						 loopAgain = true; 
					 	 break;
			}		
		}
		while(loopAgain);
		System.out.println("Unexpected Error: Please consult the developers");
		
		return false;
	}

	//Checks each letter in the string and see's if the letter is not in the alphabet(lower case) and is not in the alphabet(Upper case)
	//if it is not in either alphabet, then it returns true
	public boolean checkInputToContainInvalidChar(String string)
	{
		if(string.length()==0 || string.length() > 40)
		{
			return true;
		}
		for(int i = 0; i<string.length(); i++)
		{
			if((int)string.charAt(i)< 97 || (int)string.charAt(i)> 122)//checks if the char is not a lower case letter
			{
				if((int)string.charAt(i)< 65 || (int)string.charAt(i)> 90)//checks if the char is not an upper case letter
				{
					if((int)string.charAt(i)!= 32)//checks if the char is not a 'space'
					{
						return true;
					}
				}
			}
		}	
		return false;
	}
	
	//finds all employees by name and prints to screen then calls add  employee work time function
	public boolean findEmployeeByNameUI()
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
			//Attempting to see if the input is valid
			//Checking to see if the input contains any non-alphabetical characters e.g ?>!#%$#12345
			if(employeeName.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");
				
				return false;
			}
			ArrayList<Employee> employees = connect.getEmployees(employeeName);

			for(Employee employee: employees)
			{
			    System.out.println(employee.toString());
			    if(employees.size() == 0)
			    {
			    	System.out.println("Sorry but there are no matches for the name '"+employeeName+"'\n Please Try again");
			    	loopAgain = true;
			    	continue;
			    }
			}
			do
			{
				loopAgain2 = false;
				System.out.println("What employee would you like to add working times for?");
				System.out.print("Employee''s ID >> ");
				String employeeId = kb.nextLine();
				if(employeeId.equalsIgnoreCase("/exit"))
				{
					System.out.println("Exitting to main menu...");
					
					return false;
				}
				int id = changeInputIntoValidInt(employeeId);
				if(id < 0)
				{
					System.out.println("Invalid ID, Try again");
					loopAgain2 = true;
					continue;
				}
				boolean idExists = false;
				for(Employee employee: employees)
				{
					if(employee.getId()==id)
					{
						idExists = true;
						addWorkingTimesForNextMonth(id);
					}
				}
				if(!idExists)
				{
					System.out.println("ID MISMATCH: There is no ID that matches an employee you have just searched for\n Try Again");
					loopAgain2 = true;
				}
			}
			while(loopAgain2);
		}
		while(loopAgain);
		return false;
	}
	//Changes the string number into an Double
	//return -1 if the input is a negative number OR if the input contains non-numeric characters except decimal
	//Checks if the input contains more than 1 decimal
	public double changeInputIntoValidDouble(String string) 
	{
		try {
		      Double input = Double.parseDouble(string);
		      //Checking to see if the input is a negative, negatives are not used as inputs in this project
		      if(input < 0 || input > 1000)
		      {
		    	  return -1;
		      }
		      return input;
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	//Changes the string number into an Int
	//return -1 if the input is a negative number OR if the input contains non-numeric characters
	public int changeInputIntoValidInt(String string) 
	{
		try {
		      Integer input = Integer.parseInt(string);
		      //Checking to see if the input is a negative, negatives are not used as inputs in this project
		      if(input < 0)
		      {
		    	  return -1;
		      }
		      return input;
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	//Checking date and it's format before converting, if okay, then convert, return date
	public boolean checkNewDate(String date)
	{
		
		Date date1 = convertStringToDate(date);
		if(date1 == null)
		{
			System.out.println("This is an invalid date format, Try again (dd/mm/yyyy)");
			return false;
		}
		Calendar c=new GregorianCalendar();
		c.add(Calendar.DATE, 30);
		Date date2 =c.getTime();
		System.out.println(getDateDifference(date1,date2));
		System.out.println(date1);
		System.out.println(date2);
		if(date1.after(date2))
		{
			System.out.println("This date is more than 30 days in advance, Try again");
			return false;
		}
		return true;
	}
	
	//DAVID
	/*
	 * UI for adding employee working time for next month 
	 * Status: In development
	 */
	public boolean addWorkingTimesForNextMonth(int employeeId){
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		Controller controller = new Controller();
		DatabaseConnection connect = new DatabaseConnection();
		
		//check if the input employeeId exists in database
		boolean valid = false;
		int year, month, date, startTime, finishTime;
		Business bmenu = new Business();
		String yearStr = "", 
			   monthStr = "", 
			   dateStr = "", 
			   startTimeStr = "", 
			   finishTimeStr = "";
		String exitCommand = "/exit";
		
		do{
			while(valid == false){
				System.out.printf("Please Enter year:\n" , "user>>");
				yearStr = sc.nextLine();
				//convert to Integer
				year = (int)controller.changeInputIntoValidDouble(yearStr);
				boolean isInt = isOfTypeInt(year);
				if(isInt){
					valid = true;
				}else{
					System.out.println("Please enter an appropriate year!");
					valid = false;
				}
				/*
				 * -check if input is negative
				 * -check if input is this year
				 */
			}
			valid = false;
			while(valid == false){
				System.out.printf("Please Enter month:\n" , "user>>");
				monthStr = sc.nextLine();
				month = (int)controller.changeInputIntoValidDouble(monthStr);
				boolean isInt = isOfTypeInt(month);
				if(isInt){
					valid = true;
				}else{
					System.out.println("Please enter an appropriate month!");
					valid = false;
				}
				/*
				 * -check if input is negative
				 * -check if input is this month/next month
				 */
			}
			valid = false;
			while(valid == false){
				System.out.printf("Please Enter date(eg. DD/MM/YYYY:\n" , "user>>");
				dateStr = sc.nextLine();
				/*
				 * -need to convert into date time format
				 * -check if input is formatted correctly
				 * -check if input is within next month
				 */
			}
			valid = false;
			while(valid == false){
				System.out.printf("Please Enter start time(eg. HH:MM):\n" , "user>>");
				startTimeStr = sc.nextLine();
				/*
				 * -need to convert into date time format
				 * -check if input is formatted correctly
				 */
			}
			valid = false;
			while(valid == false){
				System.out.printf("Please Enter finish time(eg. HH:MM):\n" , "user>>");
				finishTimeStr = sc.nextLine();
				/*
				 * -need to convert into date time format
				 * -check if input is formatted correctly
				 */
			}
			valid = false;
		}while (!yearStr.equalsIgnoreCase(exitCommand)
				|| !monthStr.equalsIgnoreCase(exitCommand)
				|| !dateStr.equalsIgnoreCase(exitCommand)
				|| !startTimeStr.equalsIgnoreCase(exitCommand)
				|| !finishTimeStr.equalsIgnoreCase(exitCommand));
		
		return false;
	}

	public boolean isOfTypeInt(int num) {
	    try {
	        Integer.valueOf(num);
	        return true;
	    } catch (NumberFormatException numberFormatException) {
	        return false;
	    }
	}
	
	public boolean addWorkingTimesForEmployee()
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
			if(employeeName.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");
				return false;
			}
			//Attempting to see if the input is valid
			//Checking to see if the input contains any non-alphabetical characters e.g ?>!#%$#12345
			if(checkInputToContainInvalidChar(employeeName))
			{
				System.out.println("The name you have entered contains non-alphabetical characters");
				System.out.println("Please try again");
				loopAgain = true;
			}
		}
		while(loopAgain);
		connect.getEmployees(employeeName);
		/*do
		{
			loopAgain = false;
			System.out.print("Enter in the pay rate of " + employeeName + " [/exit to quit] >> ");
			employeePayRate = kb.nextLine();
			//Attempting to change string into an integer
			//Checking to see if the amount contains any non-digit characters
			if(employeePayRate.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");
				
				return false;
			}
			employeePayRate2 = changeInputIntoValidDouble(employeePayRate);
			if(employeePayRate2<0)
			{
				System.out.println("The amount you have entered contains invalid characters, is less than 0 or greater that 10000 ");
				System.out.println("Please try again");
				loopAgain = true;
			}
		}
		while(loopAgain);*/
		return false;
	}
	
	/*
	
	//JOSEPH
	 * Use Convert String to Date when entering data into the database
	 */
	public Date convertStringToDate(String date)
	{
		Date _date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try 
		{
			_date = sdf.parse(date);
		} 
		catch (ParseException e)
		{
			System.out.println(e.getMessage());
		}
		return _date;
	}
	
	/*
	 * Use Convert Date to String when reading/printing from database
	 */
	public String convertDateToString(Date date)
	{
		String _date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
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
		} 
		catch (ParseException e)
		{
			System.out.println(e.getMessage());
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
		try {
			long diff = time2.getTime() - time1.getTime();
			long diffMinutes = diff / (60 * 1000) % 60;
			val = diffMinutes;
			//System.out.print(diffHours + " hours, ");

		} catch (Exception e) {
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
		try {
			long diff = date2.getTime() - date1.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			val = diffDays;
			//System.out.print(diffDays + " days, ");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	
	
}
