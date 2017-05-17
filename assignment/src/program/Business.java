package program;

public class Business {
	private String businessName;
	private int id;
	
	public Business(int id, String businessName)
	{
		this.id = id;
		this.businessName = businessName;
	}
	
	public Business(String businessName)
	{
		this.businessName = businessName;
	}
	
	public String getBusinessName()
	{
		return businessName;
	}
	
	public int getBusinessId()
	{
		return id;
	}
}
