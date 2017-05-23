package program;


public class Employee {
	/*
	 * Protected values used by child classes
	 */
	private int id;
	private double payRate;
	private String name;
	int businessID;
	public Employee(int id, String name, double payRate,int businessID)
	{
		this.id = id;
		this.name = name;
		this.payRate = payRate;
		this.businessID = businessID;
	}
	public Employee(){}
	public Employee(String name, double payRate,int businessID)
	{
		this.name = name;
		this.payRate = payRate;
		this.businessID = businessID;
	}
	public int getId()
	{
		return id;
	}
	public String getName()
	{
		if(name.equals(""))
		{
			return "Employee does not exist";
		}
		return name;
	}
	public double getPayRate()
	{
		return payRate;
	}
	public int getBusinessID()
	{
		return businessID;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setPayRate(double payRate)
	{
		this.payRate = payRate;
	}
	public String toString()
	{
		if(id == 0)
		{
			return "Sorry, Employees with that ID do not exist";
		}
		return "ID: " + id + "   Name: " + name + "   Pay Rate: $" + payRate;
	}
	
}