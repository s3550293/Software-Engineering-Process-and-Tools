package program;

public class EmployeeWorkingTime 
{
	private int id;
	private String date;
	private double startTime;
	private double endTime;
	
	public EmployeeWorkingTime(int id, String date, double startTime, double endTime)
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
	public String getDate()
	{
		return date;
	}
	public double getStartTime()
	{
		return startTime;
	}
	public double getEndTime()
	{
		return endTime;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public void setStartTime(double startTime)
	{
		this.startTime = startTime;
	}
	public void setEndTime(double endTime)
	{
		this.endTime = endTime;
	}
	public String toString()
	{
		return "ID: " + id + "   Date: " + date + "   Start Time: " + startTime+ "   End Time: " + endTime;
	}
}
