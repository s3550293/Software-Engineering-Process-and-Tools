package program;


public class Customer extends User
{
	String _dob, _gender, _email;
	int businessID;
	public Customer(){}
	public Customer(int id, String fname, String lname, String phone, String dob, String gender, String email,int businessID)
	{
		super(id, fname, lname, phone, businessID);
		_dob = dob;
		_gender = gender;
		_email = email;
	}

	public String getDOB(){return _dob;}
	public String getGender(){return _gender;}
	public String getEmail(){return _email;}
	public void setDOB(String dob){_dob = dob;}
	public void setGender(String gender){_gender = gender;}
	public void setEmail(String email){_email = email;}
}
