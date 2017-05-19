package program;


public class User
{
	/*
	 * Protected values used by child classes
	 */
	protected int _id;
	protected String _fname, _lname, _phone, _payment;
	protected String _username, _password;
	protected int _accountType;
	protected int businessID;
	public User(int id, String username, String password, int accountType,int businessID)
	{
		_id = id;
		_username = username;
		_password = password;
		_accountType = accountType;
	}
	public User(String username, String password, int accountType,int businessID)
	{
		_username = username;
		_password = password;
		_accountType = accountType;
	}
	public User(int id, String fname, String lname, String phone,int businessID)
	{
		_id = id;
		_fname = fname;
		_lname = lname;
		_phone = phone;
	}
	public User(String fname, String lname, String phone,int businessID)
	{
		_fname = fname;
		_lname = lname;
		_phone = phone;
	}
	public User(){}
	
	public int getID(){return _id;}
	
	public String toString()
	{
		return _username + " " + _password + " " + _accountType;
	}
	public String getUsername() 
	{
		
		return _username;
	}
	
	public int getAccountType(){
		return _accountType;
	}
	
	public String getPassword() 
	{
		return _password;
	}
	public String getFullName(){return _fname+" "+_lname;}
	public String getFName(){return _fname;}
	public String getLName(){return _lname;}
	public String getPhone(){return _phone;}
	public int getBusinessID(){return businessID;};
	public void setID(int id){_id = id;}
	public void setFName(String fname){_fname = fname;}
	public void setLName(String lname){_lname = lname;}
	public void setPhone(String phone){_phone = phone;}
	public void setBusinessID(int id) {businessID = id;}
	public void setUsern(String uname){_username = uname;}
	public void setPass(String pass){_password = pass;}
}
