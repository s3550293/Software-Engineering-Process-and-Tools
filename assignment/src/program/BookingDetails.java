package program;

import java.util.Date;

public class BookingDetails {

	private int id, bookingID, cusID;
	private Date date, startTime, endTime;
	
	public BookingDetails(){}
	public BookingDetails(int id, int bookingID, int cusID, Date date, Date startTime, Date endTime)
	{
		this.id = id;
		this.bookingID = bookingID;
		this.cusID = cusID;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getId()
	{
		return id;
	}
	public int getEmpID()
	{
		return bookingID;
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
	public String toString()
	{
		return "ID: " + id + "   Customer ID: " + cusID + "   Date: " + date + "   Start Time: " 
				+ startTime+ "   End Time: " + endTime;
	}
}