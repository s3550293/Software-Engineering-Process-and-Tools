package program;

import java.util.Date;

import org.apache.log4j.Logger;

public class Booking {
	
	private static Logger log = Logger.getLogger(Main.class);
	private int bookingID, cusID;
	private Date date, startTime, endTime;
	private String status;
	
	public Booking(){}
	public Booking(int bookingID, int cusID, Date date, Date startTime, Date endTime, String status)
	{
		this.bookingID = bookingID;
		this.cusID = cusID;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
	}

	
	public int getBookingID()
	{
		return bookingID;
	}
	public int getCustomerId()
	{
		return cusID;
	}
	public Date getDate()
	{
		return date;
	}
	public Date getStartTime()
	{
		return startTime;
	}
	public Date getEndTime()
	{
		return endTime;
	}
	public String getStatus()
	{
		return status;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String toString()
	{
		return "Booking ID: " + bookingID + "   Customer ID: " + cusID + "   Date: " + date + "   Start Time: " 
				+ startTime + "   End Time: " + endTime + "   Status: " + status;
	}
}