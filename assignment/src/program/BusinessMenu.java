package program;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	 * @author Luke Mason
	 * Used to check information to add a new employee to database
	 * 
	 */
				
				/**
				 * Convert string to Double
				 * @param employeePayRate
				 * @return double
				 */
				public double strPayRateToDouble(String employeePayRate)
				{
					log.info("IN strPayRateToDouble\n");
					double employeePayRate2 = controller.changeInputIntoValidDouble(employeePayRate);//checking for invalid input
					log.info("OUT strPayRateToDouble\n");
					return employeePayRate2;
				}
				
				/**
				 * Checks if employee pay rate is less than 0 (Error)
				 * @param employeePayRate
				 * @return
				 */
				public boolean checkEmployeePayRate(double employeePayRate)
				{
					log.info("IN checkEmployeePayRate\n");
					if (employeePayRate < 0||employeePayRate>1000)
					{
						log.info("OUT checkEmployeePayRate\n");
						return false;
					}
					log.info("OUT checkEmployeePayRate\n");
					return true;
				}
				
				/**
				 * Adds an employee to database
				 * @param employeeFName
				 * @param employeeLName
				 * @param employeePayRate
				 */
				public void addEmployee(String employeeFName,String employeeLName,double employeePayRate)
				{
					log.info("IN addEmployee-bMenu\n");
					DatabaseConnection connect = new DatabaseConnection();
					String employeeName = employeeFName + " " + employeeLName;//concatenating first and last name into name
					connect.addEmployee(employeeName, employeePayRate);//adding employee
					log.info("OUT addEmployee-bMenu\n");
				}
				
				/**
				 * Gets the last employeeID in the database
				 */
				public int getLastEmployeeId()
				{
					log.info("IN getLastEmployeeId\n");
					log.debug("getting id from last employee");
					ArrayList<Employee> employees = connect.getEmployees("");//adding working times to employee just made
					//This is for if more than one employee has the same name as searched
					int id = -1;
					Employee lastEmp = new Employee();
					for(Employee emp: employees)
					{
						lastEmp = emp;
					}		
					id =lastEmp.getId();
					log.info("OUT getLastEmployeeId\n");
					return id;
				}
				
				/**
				 * Checks if morning or evening have been selected
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
				 * @return false if morning and evening are true and afternoon is false
				 */
				public boolean checkWorkTimes(boolean btnSunMorning,boolean btnSunAfternoon,boolean btnSunEvening,boolean btnMonMorning,boolean btnMonAfternoon,boolean btnMonEvening,boolean btnTueMorning,boolean btnTueAfternoon,boolean btnTueEvening,boolean btnWedMorning,boolean btnWedAfternoon,boolean btnWedEvening,boolean btnThurMorning,boolean btnThurAfternoon,boolean btnThurEvening,boolean btnFriMorning,boolean btnFriAfternoon,boolean btnFriEvening,boolean btnSatMorning,boolean btnSatAfternoon,boolean btnSatEvening)
				{
					log.info("IN checkWorkTimes\n");
					if(btnSunMorning && !btnSunAfternoon && btnSunEvening)
					{
						String day = "Sunday";
						controller.messageBox("ERROR", "Work Time Error", "Work time selected on "+day+" is invalid","Work time selected on "+day+" is invalid\n <Work times not added>\n Reason: Morning and Evening alone are two seperated blocks of time\n please select a one block of time and try again");
						log.info("OUT checkWorkTimes\n");
						return false;
					}
					else if(btnMonMorning && !btnMonAfternoon && btnMonEvening)
					{
						String day = "Monday";
						controller.messageBox("ERROR", "Work Time Error", "Work time selected on "+day+" is invalid","Work time selected on "+day+" is invalid\n <Work times not added>\n Reason: Morning and Evening alone are two seperated blocks of time\n please select a one block of time and try again");
						log.info("OUT checkWorkTimes\n");
						return false;
					}
					else if(btnTueMorning && !btnTueAfternoon && btnTueEvening)
					{
						String day = "Tuesday";
						controller.messageBox("ERROR", "Work Time Error", "Work time selected on "+day+" is invalid","Work time selected on "+day+" is invalid\n <Work times not added>\n Reason: Morning and Evening alone are two seperated blocks of time\n please select a one block of time and try again");
						log.info("OUT checkWorkTimes\n");
						return false;
					}
					else if(btnWedMorning && !btnWedAfternoon && btnWedEvening)
					{
						String day = "Wednesday";
						controller.messageBox("ERROR", "Work Time Error", "Work time selected on "+day+" is invalid","Work time selected on "+day+" is invalid\n <Work times not added>\n Reason: Morning and Evening alone are two seperated blocks of time\n please select a one block of time and try again");
						log.info("OUT checkWorkTimes\n");
						return false;
					}
					else if(btnThurMorning && !btnThurAfternoon && btnThurEvening)
					{
						String day = "Thursday";
						controller.messageBox("ERROR", "Work Time Error", "Work time selected on "+day+" is invalid","Work time selected on "+day+" is invalid\n <Work times not added>\n Reason: Morning and Evening alone are two seperated blocks of time\n please select a one block of time and try again");
						log.info("OUT checkWorkTimes\n");
						return false;
					}
					else if(btnFriMorning && !btnFriAfternoon && btnFriEvening)
					{
						String day = "Friday";
						controller.messageBox("ERROR", "Work Time Error", "Work time selected on "+day+" is invalid","Work time selected on "+day+" is invalid\n <Work times not added>\n Reason: Morning and Evening alone are two seperated blocks of time\n please select a one block of time and try again");
						log.info("OUT checkWorkTimes\n");
						return false;
					}
					else if(btnSatMorning && !btnSatAfternoon && btnSatEvening)
					{
						String day = "Saturday";
						controller.messageBox("ERROR", "Work Time Error", "Work time selected on "+day+" is invalid","Work time selected on "+day+" is invalid\n <Work times not added>\n Reason: Morning and Evening alone are two seperated blocks of time\n please select a one block of time and try again");
						log.info("OUT checkWorkTimes\n");
						return false;
					}
					log.info("OUT checkWorkTimes\n");
					return true;
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
				public boolean addWorkingTimes(int id,boolean btnSunMorning,boolean btnSunAfternoon,boolean btnSunEvening,boolean btnMonMorning,boolean btnMonAfternoon,boolean btnMonEvening,boolean btnTueMorning,boolean btnTueAfternoon,boolean btnTueEvening,boolean btnWedMorning,boolean btnWedAfternoon,boolean btnWedEvening,boolean btnThurMorning,boolean btnThurAfternoon,boolean btnThurEvening,boolean btnFriMorning,boolean btnFriAfternoon,boolean btnFriEvening,boolean btnSatMorning,boolean btnSatAfternoon,boolean btnSatEvening)
				{
					log.info("IN addWorkingTimes\n");
					//Checks if only morning and evening have been selected for one of the days, If so, then error
					//Can't test on JUnit because of message box
					connect.clearWorkTimes(id);	
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
						Date date = controller.strToDate(dateArray[i]);
						d.setTime(date);
						int dayOfWeek = d.get(Calendar.DAY_OF_WEEK);
						switch(dayOfWeek)
						{
							case 1: 
								log.debug("Added Work Time on Sunday\n");
								addDayWorkingTime("Sunday",id,dateArray[i],btnSunMorning, btnSunAfternoon, btnSunEvening);
								break;
							case 2: 
								log.debug("Added Work Time on Monday\n");
								addDayWorkingTime("Monday",id,dateArray[i],btnMonMorning, btnMonAfternoon, btnMonEvening);
								break;
							case 3: 
								log.debug("Added Work Time on Tuesday\n");
								addDayWorkingTime("Tuesday",id,dateArray[i],btnTueMorning, btnTueAfternoon, btnTueEvening);
								break;
							case 4: 
								log.debug("Added Work Time on Wednesday\n");
								addDayWorkingTime("Wednesday",id,dateArray[i],btnWedMorning, btnWedAfternoon, btnWedEvening);
								break;
							case 5: 
								log.debug("Added Work Time on Thursday\n");
								addDayWorkingTime("Thursday",id,dateArray[i],btnThurMorning, btnThurAfternoon, btnThurEvening);
								break;
							case 6: 
								log.debug("Added Work Time on Friday\n");
								addDayWorkingTime("Friday",id,dateArray[i],btnFriMorning, btnFriAfternoon, btnFriEvening);
								break;
							case 7: 
								log.debug("Added Work Time on Saturday\n");
								addDayWorkingTime("Saturday",id,dateArray[i],btnSatMorning, btnSatAfternoon, btnSatEvening);
								break;
							default:
								controller.messageBox("WARN", "Error: Something happened with dayOfWeek", "dayOfWeek did not register to a day","Please consult Luke Mason for the crap coding");
						}
					}
					log.info("OUT addWorkingTimes\n");
					return true;
				}
				
				/**
				 * used for getStartEndTimes by giving a number referring to time block
				 * @param morning
				 * @param afternoon
				 * @param evening
				 * @return number referring to a time block, -1 if time block is invalid
				 */
				public int getWorkTimes(boolean morning, boolean afternoon, boolean evening)
				{
					log.info("IN getWorkTimes\n");
					if(morning && afternoon && evening)
					{
						log.info("OUT getWorkTimes\n");
						return 1;
					}
					else if(morning && afternoon)
					{
						log.info("OUT getWorkTimes\n");
						return 2;
					}
					else if(afternoon && evening)
					{
						log.info("OUT getWorkTimes\n");
						return 3;
					}
					else if(morning && evening)
					{
						log.info("OUT getWorkTimes\n");
						return -1;
					}
					else if(morning)
					{
						log.info("OUT getWorkTimes\n");
						return 4;
					}
					else if(afternoon)
					{
						log.info("OUT getWorkTimes\n");
						return 5;
					}
					else if(evening)
					{
						log.info("OUT getWorkTimes\n");
						return 6;
					}
					else
					{
						log.info("OUT getWorkTimes\n");
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
					log.info("IN getStartEndTimes\n");
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
					log.info("OUT getStartEndTimes\n");
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
					log.info("IN set7DayRosterTime\n");
					Date Date = null;
					log.debug("Calling addTime\n");
					for(int i = 0; i<54;i++)//54 = 54 weeks in a year (setting work time every week for 54 weeks in advance)
					{
						connect.addEmployeeWorkingTime(employeeID, date, startTime, endTime);	
						Date = controller.strToDate(date);
						if(Date == null)
						{
							log.debug("Could not convert string to date\n");
							return false;
						}
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(Date);
						calendar.add(Calendar.DAY_OF_MONTH, 7);
						Date = calendar.getTime();
						date = controller.dateToStr(Date);
					}
					log.info("OUT set7DayRosterTime\n");
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
					log.info("IN addDayWorkingTime");
					// -Day- is not used but is there if needed
					String[] array = new String[2];
					int check = getWorkTimes(morning, afternoon, evening);
					array = getStartEndTimes(check);
					if(check == -1)
					{
						log.info("OUT addDayWorkingTime");
						return false;
					}
					if(array[0].equals(""))
					{
						log.info("OUT addDayWorkingTime");
						return false;//No working times assigned to this day
					}
					set7DayRosterTime(employeeID,date,array[0], array[1]);
					log.info("OUT addDayWorkingTime");
					return true;
				}
				
				/**
				 * Give a number representing a combination of morning,afternoon and evening
				 * @param startTime
				 * @param endTime
				 * @return
				 */
				public int getTimeBlock(String startTime, String endTime)
				{
					log.info("IN getTimeBlock");
						if(startTime.equals("08:00") && endTime.equals("20:00"))
						{
							log.info("OUT getTimeBlock");
							return 1;
						}
						else if(startTime.equals("08:00") && endTime.equals("16:00"))
						{
							log.info("OUT getTimeBlock");
							return 2;
						}
						else if(startTime.equals("12:00") && endTime.equals("20:00"))
						{
							log.info("OUT getTimeBlock");
							return 3;
						}
						else if(startTime.equals("08:00") && endTime.equals("12:00"))
						{
							log.info("OUT getTimeBlock");
							return 4;
						}
						else if(startTime.equals("12:00") && endTime.equals("16:00"))
						{
							log.info("OUT getTimeBlock");
							return 5;
						}
						else if(startTime.equals("16:00") && endTime.equals("20:00"))
						{
							log.info("OUT getTimeBlock");
							return 6;
						}
						else
						{	
							log.info("OUT getTimeBlock");
							return -1;
						}
				}
	
				
	

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
