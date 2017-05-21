package program;

import java.util.Date;

public class BusinessOwner extends User {
	private String _address;
	private int _color;
	private Date _weekdayStart;
	private Date _weekdayEnd;
	private Date _weekendStart;
	private Date _weekendEnd;

	public BusinessOwner() {
	}

	public BusinessOwner(int ID, String fname, String lname, String phone, String address,
			Date weekdayStart, Date weekdayEnd, Date weekendStart, Date weekendEnd) {
		super(ID, fname, lname, phone);
		_address = address;
		_weekdayStart = weekdayStart;
		_weekdayEnd = weekdayEnd;
		_weekendStart = weekendStart;
		_weekendEnd = weekendEnd;
	}
	
	public BusinessOwner(String fname, String lname, String phone, String address,
			Date weekdayStart, Date weekdayEnd, Date weekendStart, Date weekendEnd) {
		super(fname, lname, phone);
		_address = address;
		_weekdayStart = weekdayStart;
		_weekdayEnd = weekdayEnd;
		_weekendStart = weekendStart;
		_weekendEnd = weekendEnd;
	}

	public int getID() {
		return _id;
	}
	
	public String getAddress() {
		return _address;
	}

	public Date getWeekdayStart() {
		return _weekdayStart;
	}

	public Date getWeekdayEnd() {
		return _weekdayEnd;
	}

	public Date getWeekendStart() {
		return _weekendStart;
	}

	public Date getWeekendEnd() {
		return _weekendEnd;
	}

	public void setWeekdayStart(Date weekdayS) {
		_weekdayStart = weekdayS;
	}

	public void setWeekdayEnd(Date weekdayE) {
		_weekdayEnd = weekdayE;
	}

	public void setWeekendStart(Date weekendS) {
		_weekendStart = weekendS;
	}

	public void setWeekendEnd(Date weekendE) {
		_weekendEnd = weekendE;
	}

	public void setAddress(String address) {
		_address = address;
	}
	
	public void color(int c){_color = c;}
	public int color(){return _color;}
}
