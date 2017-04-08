package program;


public class BusinessOwner extends User
{
	private String _business, _address;
  	public BusinessOwner(){}
 	public BusinessOwner(int id,String business, String fname, String lname, String phone, String address){
 		super(id, fname, lname, phone);
 		_business = business;
 		_address = address;
 	}
 	public int getID(){return _id;}
 	public String getBusiness(){return _business;}
 	public String getAddress(){return _address;}
 	
 	public void setBusiness(String business){_business = business;}
 	public void setAddress(String address){_address = address;}
}
