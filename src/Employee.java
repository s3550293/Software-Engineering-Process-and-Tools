import org.junit.*;

public class Employee {

	/*
	 * Protected values used by child classes
	 */
	protected int id;
	protected double payRate;
	protected String name;
	public Employee(int id, String name, double payRate)
	{
		this.id = id;
		this.name = name;
		this.payRate = payRate;
	}
	public Employee(){}
	
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
