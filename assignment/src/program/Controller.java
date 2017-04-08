package program;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//import java.util.GregorianCalendar;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class Controller
{
	private static Logger log = Logger.getLogger(Controller.class);
	public Controller(){log.setLevel(Level.WARN);}

	Scanner kb = new Scanner(System.in);

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
	 * Changes the string number into an Double & Checks if the input contains more than 1 decimal
	 * @param string
	 * @return -1 if the input is a negative number OR if the input contains non-numeric characters except decimal
	 */
	public double changeInputIntoValidDouble(String string)
	{
		log.info("IN changeStringToDouble\n");
		try
		{
			Double input = Double.parseDouble(string);
			// Checking to see if the input is a negative, negatives are not
			// used as inputs in this project
			if (input < 0 || input > 1000)
			{
				log.info("OUT changeStringToDouble\n");
				return -1;
			}
			return input;
		} catch (NumberFormatException e)
		{
			log.info("OUT changeStringToDouble\n");
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
		log.info("IN changeStringToINT\n");
		try
		{
			Integer input = Integer.parseInt(string);
			// Checking to see if the input is a negative, negatives are not
			// used as inputs in this project
			if (input < 0)
			{
				log.info("OUT changeStringToINT\n");
				return -1;
			}
			return input;
		} catch (NumberFormatException e)
		{
			log.info("OUT changeStringToINT\n");
			return -1;
		}
	}

	/**
	 * @author Luke Mason
	 * @param choiceStr
	 * @return two string in array, startTime = [0] and endTime - [1]
	 */
	public String[] allocateWorkTimes(String choiceStr)
	{
		log.info("IN allocateWorkTimes\n");
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
			log.info("OUT allocateWorkTimes\n");
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
			log.info("OUT allocateWorkTimes\n");
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
			log.info("OUT allocateWorkTimes\n");
			return times;
		}
		else
		{
			System.out.println("ERROR: Should not be here");
			log.info("OUT allocateWorkTimes\n");
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
		log.info("IN checkWorkTimeChoice\n");
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
				log.info("OUT checkWorkTimeChoice\n");
				return false;
			}
		}
		if(choiceStr.equals("ME")||choiceStr.equals("EM")||choiceStr.equals("MM")||choiceStr.equals("EE")||choiceStr.equals("AA"))
		{
			System.out.println("Invalid input!");
			log.info("OUT checkWorkTimeChoice\n");
			return false;
		}
		if(choiceStr.length() == 1 && amount4 != 1)
		{
			System.out.println("Invalid input!");
			log.info("OUT checkWorkTimeChoice\n");
			return false;
		}
		if(choiceStr.length()>3 || choiceStr.length()< 1)
		{
			System.out.println("Invalid input!");
			log.info("OUT checkWorkTimeChoice\n");
			return false;
		}
		log.info("OUT checkWorkTimeChoice\n");
		return true;
	}

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
	 * @param string
	 * @return Date time
	 */
	public Date convertStringToTime(String string)
	{
		Date _time = null;
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		try
		{
			_time = sdf.parse(string);
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
	 * @author Joseph Garner
	 * 
	 * @param date
	 * @param workDays
	 * @return
	 */
	public String matchDate(String date, ArrayList<EmployeeWorkingTime> workDays)
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
	 * @author Joseph Garner
	 * 
	 * @param time
	 * @param date
	 * @param workDays
	 * @return
	 */
	public String getTime(String time, String date, ArrayList<EmployeeWorkingTime> workDays)
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
	
	/**
	 * @author Bryan
	 * Loop to display bookings for previous 7days
	 * @param 
	 * @return 
	 */
	public void checkPreviousBooking() {
		boolean loopflag = true;
		while (loopflag) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			String pDays[] = new String[7];
			String today;
			for (int i = 0; i < 7; i++) {
				c.add(Calendar.DATE, -1);
				today = sdf.format(c.getTime());
				pDays[i] = today;
			}
			displayBooking(pDays);
		}
	}
	
	/**
	 * @author Bryan
	 * Loop to display bookings for next 7days
	 * @param 
	 * @return 
	 */
	public void checkNextBooking()
	{
		boolean loopflag = true;
		while (loopflag)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			String nDays[] = new String[7];
			String today;
			for (int i = 0; i <7 ; i++)
			{
				today = sdf.format(c.getTime());
				c.add(Calendar.DATE, 1);
				nDays[i] = today;
			}
			displayBooking(nDays);
		}
	}
	
	/**
	 * @author Bryan
	 * Method to display bookings
	 * @param int, String[]
	 * @return void
	 */
	@SuppressWarnings("resource")
	public void displayBooking(String[] days){
		Scanner sc = new Scanner(System.in);
		boolean loopflag = true;
		DatabaseConnection connect = new DatabaseConnection();
		ArrayList<Booking> bookList = connect.getAllBooking();
		BusinessMenu business=new BusinessMenu();
		//display bookings within selected dates
		displayDetailedBooking_Date(bookList, days);
		
		boolean tryLoop = true;
		do{
		Booking bookings = new Booking();
		String input;
		int bookKey = 0;
		System.out.println("\nPlease enter booking id to view more or 'quit' to quit");
		input = sc.nextLine();
		if (input.equalsIgnoreCase("quit")) {
			 business.companyMenu();
		}
		
		  try { 
			  Integer.parseInt(input); } catch(NumberFormatException e) {
			  	tryLoop=true;
			  	System.out.println("Invalid Input");
				  ; break; }
		 
		bookKey = Integer.parseInt(input);
		//display bookings times within a day
		displayDetailedBooking_StartEndTime(bookKey,bookings, input,bookList,days,tryLoop,loopflag);
		if(tryLoop)
		{
			System.out.println("Invalid Input");
			loopflag=true;
			break;
		}	
	}while(tryLoop);
		
		
	}

	/**
	 * @author Bryan Soh, David(Panhaseth Heang)
	 * 
	 * @param 
	 * @return integer(book id)
	 */
	@SuppressWarnings("resource")
	public int checkNextBooking_GetBookID()
	{
		boolean loopflag = true;
		while (loopflag)
		{
			//get all the bookings 
			//display all the bookings within 7days before and after
			Scanner sc = new Scanner(System.in);
			DatabaseConnection connect = new DatabaseConnection();
			ArrayList<Booking> bookList = connect.getAllBooking();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			String nDays[] = new String[7];
			String today;
			for (int i = 0; i <7 ; i++)
			{
				c.add(Calendar.DATE, 1);
				today = sdf.format(c.getTime());
				nDays[i] = today;
			}
			
			displayDetailedBooking_Date(bookList, nDays);
			
			Booking bookings = new Booking();
			boolean tryLoop = true;
			do{
				String input;
				int bookKey = 0;
				System.out.println("\nPlease enter booking id to view more or 'quit' to quit");
				input = sc.nextLine();
				if (input.equalsIgnoreCase("quit")) {
					return 0;
				}
				
				try 
				{ 
					  Integer.parseInt(input); 
				} 
				catch(NumberFormatException e) 
				{
					  	tryLoop=true;
					  	System.out.println("Invalid Input");
						break; 
				}
				bookKey = Integer.parseInt(input);
				displayDetailedBooking_StartEndTime(bookKey,bookings, input,bookList,nDays,tryLoop,loopflag);
						
				return bookKey;
			}while(tryLoop);
		}
		return 0;
	}
	
	/**
	 * @author Bryan Soh, David(Panhaseth Heang)
	 * Display date overview table of booking for 7 days
	 * @param 
	 * @return 
	 */
	public void displayDetailedBooking_Date(ArrayList<Booking> bookList, String nDays[]){
		System.out.printf("\n%s", "ID");
		System.out.printf("%-2s %s", "", "Customer ID");
		System.out.printf("%-20s %s", "", nDays[0]);
		for(int i=1;i<nDays.length;i++)
		{
			System.out.printf("%-3s %s", "", nDays[i]);
		}
		System.out.print(
				"\n----------------------------------------------------------------------------------------------------------------------------------------");
		
		for (Booking b : bookList)
		{
			System.out.printf("\n%d %-2s %-24s", b.getBookingID(), "", b.getCustomerId());
			
			for (int j = 0; j < nDays.length; j++)
			{
				String bookedDays=convertDateToString(b.getDate());
				if (!bookList.isEmpty())
				{
					if (nDays[j].equals(bookedDays) && b.getStatus().equals("active"))
					{
						System.out.printf("%-8s %-5s", "", "Booked");
						
					}
					else
					{
						System.out.printf("%-8s %-5s", "", "-----");
					}
				} else
				{
					System.out.printf("%-8s %-5s", "", "-----");
				}
			}
		}
	}
	
	/**
	 * @author Bryan Soh, David(Panhaseth Heang)
	 * A detailed display after user select book id, shows start time and end time of booking
	 * @param 
	 * @return 
	 */
	public void displayDetailedBooking_StartEndTime(int bookKey, Booking bookings, String input, ArrayList<Booking> bookList, String nDays[], Boolean tryLoop, Boolean loopflag)
	{
		
		DatabaseConnection connect = new DatabaseConnection();
		
		for (int b = 0; b < bookList.size(); b++) {
			if (bookList.get(b).getBookingID() == bookKey) {
				bookings = connect.getOneBooking(bookKey);
				System.out.printf("\nBookID: %-15s CusID: %-2s\n", bookings.getBookingID(),
						bookings.getCustomerId());
				System.out.printf("\n%-15s %-15s %s\n", "Date", "Start Time", "End Time");
				System.out.println("----------------------------------------------------");
				for (int j = 0; j < nDays.length; j++) {
					System.out.printf("%s", nDays[j]);
					if (bookings != null) {
						if (nDays[j].equals(convertDateToString(bookings.getDate()))) {
							String startTime = convertTimeToString(bookings.getStartTime());
							String endTime = convertTimeToString(bookings.getEndTime());
							System.out.printf("%6s %-15s %s\n", "", startTime, endTime);
						} else {
							System.out.printf("%6s %-15s %s\n", "", "-----", "-----");
						}
					} else {
						System.out.printf("%6s %-15s %s\n", "", "-----", "-----");
					}
				}
				tryLoop = false;
				b = bookList.size();
			}
		}
	}
	
	/**
	 * @author David(Panhaseth Heang)
	 * get booking id and set its status to 'cancel'
	 * @param 
	 * @return
	 */
	@SuppressWarnings("resource")
	public void cancelBooking(){
		DatabaseConnection conn = new DatabaseConnection();
		Scanner sc = new Scanner(System.in);
		Boolean loop = false;
		
		do{
			int bookID = checkNextBooking_GetBookID();
			if(bookID == 0){
				return;
			}
			System.out.println("\nPlease enter 'cancel' to cancel book id " + bookID + " or 'return'");
			String input = sc.nextLine();
			if(input.equalsIgnoreCase("cancel")){
				conn.cancelBooking(bookID);
				
			}else if(input.equalsIgnoreCase("return"))
			{
				loop = false;
			}else{
				System.out.println("Please enter an appropriate input!");
				continue;
			}
				
		}while(loop == false);
		
	}
	

	/**
	 * @author Bryan Soh, David(Panhaseth Heang)
	 * Display date overview table of availability for 7 days
	 * @param 
	 * @return 
	 */
	public void displayDetailedWorking_Date(String[] days,ArrayList<Employee> emList,ArrayList<EmployeeWorkingTime> workDays) {
		DatabaseConnection connect = new DatabaseConnection();
		System.out.printf("\n%s", "ID");
		System.out.printf("%-2s %s", "", "Employee");
		System.out.printf("%-20s %s", "", days[0]);
		for (int i = 1; i < days.length; i++) {
			System.out.printf("%-3s %s", "", days[i]);
		}
		System.out.print(
				"\n----------------------------------------------------------------------------------------------------------------------------------------");
		for (Employee e : emList) {
			System.out.printf("\n%d %-2s %-20s", e.getId(), "", e.getName());
			workDays = connect.getEmployeeWorkingTimes(e.getId());
			for (int j = 0; j < 7; j++) {
				if (!workDays.isEmpty()) {
					if (days[j].equals(matchDate(days[j], workDays))) {
						System.out.printf("%-8s %-5s", "", "Avail");
					} else {
						System.out.printf("%-8s %-5s", "", "-----");
					}
				} else {
					System.out.printf("%-8s %-5s", "", "-----");
				}
			}

		}
	}
	
	/**
	 * @author Bryan Soh,David(Panhaseth Heang)
	 * A detailed display after user select employee id, shows start time and end time of booking
	 * @param 
	 * @return 
	 */
	public void displayDetailedWorking_Time(int empKey, Employee employee, String input, ArrayList<Employee> emList,ArrayList<EmployeeWorkingTime> workDays, String days[], Boolean tryLoop, Boolean loopflag){
		DatabaseConnection connect = new DatabaseConnection();
		
		for (int b = 0; b < emList.size(); b++) {
			if (emList.get(b).getId() == empKey) {
				employee = connect.getEmployee(empKey);
				workDays = connect.getEmployeeWorkingTimes(empKey);
				System.out.printf("\nName: %-15s Payrate: %-2.2f\n", employee.getName(), employee.getPayRate());
				System.out.printf("\n%-15s %-15s %s\n", "Date", "Start Time", "End Time");
				System.out.println("----------------------------------------------------");
				for (int j = 0; j < 7; j++) {
					System.out.printf("%s", days[j]);
					if (!workDays.isEmpty()) {
						if (days[j].equals(matchDate(days[j], workDays))) {
							System.out.printf("%6s %-15s %s\n", "", getTime("start", days[j], workDays),
									getTime("end", days[j], workDays));
						} else {
							System.out.printf("%6s %-15s %s\n", "", "-----", "-----");
						}
					} else {
						System.out.printf("%6s %-15s %s\n", "", "-----", "-----");
					}
				}
				tryLoop = false;
				b = workDays.size();
			}
		}
	}
	
	public Boolean checkBookingStartTime(Date time, Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String _date = sdf.format(date);
		sdf = new SimpleDateFormat("HH:mm");
		String _time = sdf.format(time);
		log.debug("Time: "+_time+" Date: "+_date);
		DatabaseConnection connect = new DatabaseConnection();
		Boolean val = true;
		for(Booking b: connect.getAllBooking())
		{
			log.debug("Booking ID: "+b.getBookingID());
			if(convertDateToString(b.getDate()).equals(_date))
			{
				if(convertTimeToString(b.getStartTime()).equals(_time))
				{
					return false;
				}
			}
		}
		
		return val;
	}
}
