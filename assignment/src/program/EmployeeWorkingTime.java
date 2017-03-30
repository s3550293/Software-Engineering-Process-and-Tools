package program;

import java.util.Date;

import org.apache.log4j.Logger;

public class EmployeeWorkingTime 
{
	private static Logger log = Logger.getLogger(Main.class);
	private int id, empID;
	private Date date, startTime, endTime;
	
	public EmployeeWorkingTime(){}
	public EmployeeWorkingTime(int id, int empID, Date date, Date startTime, Date endTime)
	{
		this.id = id;
		this.empID = empID;
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
		return empID;
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
