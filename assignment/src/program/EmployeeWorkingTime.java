package program;

import java.util.Date;

public class EmployeeWorkingTime 
{
	private int id, empID, dayOfWeek;
	private Date startTime, endTime;
	
	public EmployeeWorkingTime(){}
	public EmployeeWorkingTime(int id, int empID, int dayOfWeek, Date startTime, Date endTime)
	{
		this.id = id;
		this.empID = empID;
		this.dayOfWeek = dayOfWeek;
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
	public int getDayOfWeek()
	{
		return dayOfWeek;
	}
	public Date getStartTime()
	{
		return startTime;
	}
	public Date getEndTime()
	{
		return endTime;
	}
	public void setDayOfWeek(int dayOfWeek)
	{
		this.dayOfWeek = dayOfWeek;
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
		return "ID: " + id + "   Day Of Week: " + dayOfWeek + "   Start Time: " + startTime+ "   End Time: " + endTime;
	}
}
