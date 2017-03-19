
public class Employee {

	/*
	 * Protected values used by child classes
	 */
	protected int id, payRate;
	protected String name;
	protected boolean _accountType;
	public Employee(int id, String name, int payRate)
	{
		this.id = id;
		this.name = name;
		this.payRate = payRate;
	}
	public Employee(){}
	
	public String toString()
	{
		if(id == 0)
		{
			return "Sorry, Employees with that ID do not exist";
		}
		return "ID: " + id + "   Name: " + name + "   Pay Rate: $" + payRate;
	}
}
