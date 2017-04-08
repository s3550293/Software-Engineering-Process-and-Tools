package program;


public class Customer extends User
{
	String _payment;
	public Customer(){}
	public Customer(int id, String fname, String lname, String phone, String payment)
	{
		super(id, fname, lname, phone);
		_payment = payment;
	}
	
	public String getPayment(){return _payment;}
	
	public void setPayment(String payment){_payment = payment;}

}
