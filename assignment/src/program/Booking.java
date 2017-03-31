package program;

import java.util.Date;

import org.apache.log4j.Logger;

public class Booking {
	
	private static Logger log = Logger.getLogger(Booking.class);
	private int bookingID, cusID;
	private Date date, startTime, endTime;
	private String desc;
	
	public Booking(){}
	public Booking(int bookingID, int cusID, Date date, Date startTime, Date endTime, String desc)
	{
		this.bookingID = bookingID;
		this.cusID = cusID;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.desc = desc;
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
	public String getDesc()
	{
		return desc;
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
	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	public String toString()
	{
		return "Booking ID: " + bookingID + "   Customer ID: " + cusID + "   Date: " + date + "   Start Time: " 
				+ startTime + "   End Time: " + endTime + "   Decription: " + desc;
	}
}