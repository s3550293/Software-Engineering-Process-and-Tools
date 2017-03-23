package program;

import java.util.Date;

public class EmployeeWorkingTime 
{
	private int id;
	private Date date;
	private Date startTime;
	private Date endTime;
	
	public EmployeeWorkingTime(int id, Date date, Date startTime, Date endTime)
	{
		this.id = id;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getId()
	{
		return id;
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
		return "ID: " + id + "   Date: " + date + "   Start Time: " + startTime+ "   End Time: " + endTime;
	}
}
