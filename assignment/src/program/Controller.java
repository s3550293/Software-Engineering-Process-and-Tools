package program;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Level;
//import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javafx.scene.control.Alert;


public class Controller
{
	private static Logger log = Logger.getLogger(Controller.class);
	public Controller(){ log.setLevel(Level.DEBUG);}
	private static User _user = null;
	public User getUser(){return _user;}
	public void setUser(User user){_user = user;}
	private static Booking _booking = null;
	public Booking getBooking(){return _booking;}
	Scanner kb = new Scanner(System.in);

	/**
	 * @author Luke Mason
	 * Checks each letter in the string and see's if the letter is not in the alphabet(lower case) and is not in the alphabet(Upper case)
	 * @param string
	 * @return if input DOES contain invalid char, then it returns true. returns false if valid
	 */
	public boolean checkInputToContainInvalidChar(String string)
	{
		if (string.length() < 1 || string.length() > 40)
		{
			return true;
		}
		for (int i = 0; i < string.length(); i++)
		{
			// checks if the letter is not an upper case letter
			if ((int) string.charAt(i) < 65 || (int) string.charAt(i) > 122 || ((int) string.charAt(i) < 97 &&(int) string.charAt(i) > 90))// checks if the letter is not a lowercase letter
			{
					return true;					
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
	 * @author Joseph Garner
	 * Converts type String to type Date
	 * @param date
	 * @return Date date
	 */
	public Date strToDate(String date)
	{
		Date _date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			_date = sdf.parse(date);
		} catch (ParseException e)
		{
			log.warn("Convert To Date Error: " + e.getMessage()+"\n");

		}
		return _date;
	}

	/**
	 * @author Joseph Garner
	 * Use Convert Date to String when reading/printing from database
	 * @param date
	 * @return String date
	 */
	public String dateToStr(Date date)
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
	public Date strToTime(String string)
	{
		Date _time=null;
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		try
		{
			_time = sdf.parse(string);
		} catch (ParseException e)
		{
			log.warn(e.getMessage()+"\n");
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
	public String timeToStr(Date time)
	{
		String _time = null;
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		_time = sdf.format(time);
		return _time;
	}

	/**
	 * @author Joseph Garner
	 * 
	 * @param day - day of week e.g monday = 2
	 * @param workDays
	 * @return
	 */
	public int matchDay(int day, List<EmployeeWorkingTime> workDays)
	{
		for (EmployeeWorkingTime ew : workDays)
		{
			if (day == ew.getDayOfWeek())
			{
				return ew.getDayOfWeek();
			}
		}
		return 0;

	}
		
	/**
	 * displays an alert box to the user
	 * @author Joseph Garner
	 */
	public void messageBox(String type, String title, String header, String message)
    {
        Alert alert = null;
        if(type.equals("INFO"))
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
        }
        else if(type.equals("WARN"))
        {
            alert = new Alert(Alert.AlertType.WARNING);
        }
        else if(type.equals("ERROR"))
        {
            alert = new Alert(Alert.AlertType.ERROR);
        }
        else
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
        }
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	/**
	 * displays an alert box to the user
	 * @author Joseph Garner
	 */
	public boolean checkEmail(String email)
	{
		if(email.length() >= 7)
		{
			for (int i = 0; i < email.length(); i++)
			{
				if (email.charAt(i) == '@')
				{
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Compares parts of a string to searching
	 * @author Joseph Garner
	 */
	public boolean searchMatch(String data, String input)
	{
		for (int i = 0; i < input.length(); i++)
		{
			log.debug("LOGGER: char - "+data.charAt(i));
			if (data.charAt(i) != input.charAt(i)){
				log.debug("LOGGER: char - "+data.charAt(i));
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if date entered is in the future
	 * @author Joseph Garner
	 */
	public boolean checkForBackToFuture(String date)
	{
		Date _date = strToDate(date);
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		if(today.after(_date))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * @author Luke Mason
	 * combines date and time to create new date then gets milliseconds from it
	 * @param date
	 * @param time
	 * @return milliseconds from 1970
	 */
	public long getTimeFrom1970(Date date, Date time)
	{
		log.info("IN getTimeFrom1970");
		String dateStr = dateToStr(date);
		int first = dateStr.indexOf("/");
		int second = dateStr.indexOf("/",first+1);
		String dayOfMonthStr = dateStr.substring(0,first);
		String monthStr = dateStr.substring(first+1,second);
		String yearStr = dateStr.substring(second+1);	
		String timeStr = timeToStr(time);
		int semicolon = timeStr.indexOf(":");
		log.info("index = "+semicolon+"\n");
		String hourStr = timeStr.substring(0,semicolon);
		String minuteStr = timeStr.substring(semicolon+1);
		log.info("hourStr = "+hourStr+"\n");
		int year = Integer.parseInt(yearStr);
		int month = Integer.parseInt(monthStr);
		int dayOfMonth = Integer.parseInt(dayOfMonthStr);
		int hour = Integer.parseInt(hourStr);
		int minute = Integer.parseInt(minuteStr);
		Calendar c = Calendar.getInstance();
		log.info("("+year+"+1900 , "+month+" , "+dayOfMonth+" , "+hour+" , "+minute+")");
		c.set(year+1900,month,dayOfMonth,hour,minute);//Setting new date
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		log.info("OUT getTimeFrom1970");
		return c.getTimeInMillis();
	}
	
	/**
	 * @author Luke Mason
	 * converts a dat into the day it represents, e.g 05/05/2017 -> Friday -> 6
	 */
	public int dateToDay(String date)
	{
		int dayOfWeek = 0;
		Calendar d = Calendar.getInstance();
		Date date2 = strToDate(date);
		if(date2 == null)
		{
			return dayOfWeek;
		}
		d.setTime(date2);
		dayOfWeek = d.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek;
	}
	
	/**
	 * @author Luke Mason
	 * gets upcoming day's date
	 * @param day
	 * @return
	 */
	public String getUpcomingStrDateForDay(int day)
	{
		String date = null;
		Calendar c = Calendar.getInstance();
		for (int i = 0; i < 7; i++) 
		{
			int iDay = c.get(Calendar.DAY_OF_WEEK);// puts each date from every loop into array
			if(day == iDay)
			{
				date = dateToStr(c.getTime());
				break;
			}
			 c.add(Calendar.DAY_OF_MONTH, 1);
		}
		return date;
	}
	
	/**
	 * @author Luke Mason
	 * Gets Employees that are available to provide service on given date
	 * @param date
	 * @param startTime
	 * @param endTime
	 * @return List of Available Employee's object
	 */
	public List<Employee> getAvailableEmployeesForSpecifiedTime(String date, String startTime, String endTime)
	{
		int day = dateToDay(date);
		DatabaseConnection connect = new DatabaseConnection();
		log.info("IN getAvailableEmployeesForBooking");
		ArrayList<Employee> employeesWorkingInTimeBlock = new ArrayList<>();
		ArrayList<EmployeeWorkingTime> workTimesOnDay;
		ArrayList<Booking> bookingsOnDate;
		ArrayList<Employee> employeesNotAvailable = new ArrayList<>();
		workTimesOnDay = connect.getWorkTimesOnDay(day);
		bookingsOnDate = connect.getActiveBookingsOnDate(date);
		Date date2 = strToDate(date);
		Date startTime2 = strToTime(startTime);
		log.debug("Booking Start Time = "+startTime2+"\n");
		Date endTime2 = strToTime(endTime);
		log.debug("Booking End Time = "+endTime2+"\n");
		long bookingStartTime = getTimeFrom1970(date2, startTime2);
		long bookingEndTime = getTimeFrom1970(date2, endTime2);
		//Check if employees are already booked for that time and add to 'employeesNotAvailable' list
		employeesNotAvailable = checkForBookedEmployeeThenAddToList(bookingsOnDate, bookingStartTime, employeesNotAvailable);
		for(Employee emp: employeesNotAvailable)
		{
			log.info(emp);
		}
		if(!workTimesOnDay.isEmpty())
		{
			for(EmployeeWorkingTime ewt: workTimesOnDay)
			{
				//Getting work time start and end time
				int day1 = ewt.getDayOfWeek();
				int day2 = ewt.getDayOfWeek();
				String strDate1 = getUpcomingStrDateForDay(day1);
				String strDate2 = getUpcomingStrDateForDay(day2);
				Date Date1 = strToDate(strDate1);
				Date Date2 = strToDate(strDate2);
				long employeeStartTime = getTimeFrom1970(Date1,ewt.getStartTime());
				long employeeEndTime = getTimeFrom1970(Date2,ewt.getEndTime());
				
				if(checkValidBoundBetweenBookingAndEmployeeTimes(bookingStartTime, bookingEndTime, employeeStartTime, employeeEndTime)){
					boolean available = true;
					for(Employee emp: employeesNotAvailable)
					{
						if(emp.getId() == ewt.getEmpID())//If employee is already taken
						{
							available = false;
						}
					}
					if(available)
					{
						employeesWorkingInTimeBlock.add(connect.getEmployee(ewt.getEmpID()));
					}
				}
			}		
		}
		log.info("OUT getAvailableEmployeesForBooking");	
		return employeesWorkingInTimeBlock;
	}
	
	/**
	 * @author Luke Mason
	 * @editor David
	 * Check if employee are already booked for the time slot
	 * @param bookingsOnDate
	 * @param bookingStartTime
	 * @param employeesNotAvailable
	 * @return List of Employee's object
	 */
	public ArrayList<Employee> checkForBookedEmployeeThenAddToList(ArrayList<Booking> bookingsOnDate, long bookingStartTime, ArrayList<Employee> employeesNotAvailable){
		DatabaseConnection connect = new DatabaseConnection();
		//Check if employees are already booked for that time
		if(!bookingsOnDate.isEmpty())
		{
			for(Booking bk: bookingsOnDate)
			{
				//compare booking end time to booking start time
				long booking2EndTime = getTimeFrom1970(bk.getDate(), bk.getEndTime());
				if(booking2EndTime > bookingStartTime)//If index booking end time is NOT less than booking start time(or equal to)
				{
					log.debug(booking2EndTime+"\n");
					log.debug(bookingStartTime+"\n");
					employeesNotAvailable.add(connect.getEmployee(bk.getEmployeeID()));
				}
			}
		}
		return employeesNotAvailable;
	}
	
	/**
	 * @author Luke Mason
	 * @editor David 
	 * Check if booking time's bound is equal or within the employee working time's bound.
	 * @param bookingStartTime
	 * @param bookingEndTime
	 * @param employeeStartTime
	 * @param employeeEndTime
	 * @return true or false
	 */
	public boolean checkValidBoundBetweenBookingAndEmployeeTimes(long bookingStartTime, long bookingEndTime, long employeeStartTime, long employeeEndTime){
		if(bookingEndTime <= employeeEndTime)//If booking end time is less than employee work end time(or equal to)
		{
			log.debug("Booking Date End Time = "+bookingEndTime+"\n");
			log.debug("<=Employee Work End Time = "+employeeEndTime+"\n");
			if(bookingStartTime >= employeeStartTime)//If booking start time is later than employee work start time(or equal to)
			{
				log.debug("Booking Date Start Time = "+bookingStartTime+"\n");
				log.debug(">=Employee Work Start Time = "+employeeStartTime+"\n");
				return true;
			}
		}
		return false;
	}
	
	
	public Date calEnTime(Date staTime, int length)
	{
		Date date = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(staTime);
		cal.add(Calendar.MINUTE, length);
		date = strToTime(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
		return date;
	}
	
	/**
	 * Sets the GUI color
	 * @return
	 */
	  public String setColor(){
		  DatabaseConnection c = new DatabaseConnection();
	        int val = _user.getID();
	        log.trace("ID "+val);
	        String[] arr = c.getColor(1);
	        String ret = "";
	        for(int i=0;i<arr.length;i++){
	            log.debug(arr[i]);
	            ret += "-fx-base"+i+": "+arr[i]+"; ";
	        }
	        return ret;
	    }
}
