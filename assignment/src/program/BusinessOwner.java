package program;

import java.util.Date;

public class BusinessOwner extends User
{
	private String _business, _address;
	private Date _weekdayStart;
	private Date _weekdayEnd;
	private Date _weekendStart;
	private Date _weekendEnd;
  	public BusinessOwner(){}
 	public BusinessOwner(int id,String business, String fname, String lname, String phone, String address,Date weekdayStart,Date weekdayEnd,Date weekendStart,Date weekendEnd){
 		super(id, fname, lname, phone);
 		_business = business;
 		_address = address;
 		_weekdayStart=weekdayStart;
		_weekdayEnd=weekdayEnd;
		_weekendStart=weekendStart;
		_weekendEnd=weekendEnd;
 	}
 	public int getID(){return _id;}
 	public String getBusiness(){return _business;}
 	public String getAddress(){return _address;}
 	
 	public Date getWeekdayStart()
	{
		return _weekdayStart;
	}
	public Date getWeekdayEnd()
	{
		return _weekdayEnd;
	}
	public Date getWeekendStart()
	{
		return _weekendStart;
	}
	public Date getWeekendEnd()
	{
		return _weekendEnd;
	}
	
	public void setWeekdayStart(Date weekdayS)
	{
		_weekdayStart=weekdayS;
	}
	
	public void setWeekdayEnd(Date weekdayE)
	{
		_weekdayEnd=weekdayE;
	}
	
	public void setWeekendStart(Date weekendS)
	{
		_weekdayStart=weekendS;
	}
	
	public void setWeekdendEnd(Date weekendE)
	{
		_weekdayStart=weekendE;
	}
 	
 	public void setBusiness(String business){_business = business;}
 	public void setAddress(String address){_address = address;}
}
