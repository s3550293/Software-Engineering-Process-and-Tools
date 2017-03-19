package program;

public class Employee 
{
	private int _id;
	private String _name;
	private String _payRate;
	
	public Employee(){}
	public Employee(int id, String name, String payRate)
	{
		_id = id;
		_name = name;
		_payRate = payRate;
	}
	public Employee(String name, String payRate)
	{
		_name = name;
		_payRate = payRate;
	}
	
	public int getID()
	{
		return _id;
	}
}
