package program;

public class Service
{
	private int _id, _lengthMin;
	private String _name;
	private double _price;
	public Service(){}
	public Service(int id, String name, int lengthMin, double price)
	{
		_id = id;
		_name = name;
		_lengthMin = lengthMin;
		_price = price;
	}
	public Service(String name, int lengthMin, double price)
	{
		_name = name;
		_lengthMin = lengthMin;
		_price = price;
	}
	public int getID(){ return _id; }
	public void setID(int id){ _id = id; }
	
	public String getName(){ return _name; }
	public void setName(String name){ _name = name; }
	 
//	public String getDescpt(){ return _description; }
//	public void setDescrpt(String description){ _description = description; }
	
	public int getLengthMin(){ return _lengthMin; }
	public void setLengthMin(int lengthMin){ _lengthMin = lengthMin; }
	
	public double getPrice(){ return _price; }
	public void setPrice(double price){ _price = price; }
}
