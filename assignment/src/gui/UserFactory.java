package gui;

public class UserFactory{
	
	public IUser getUser(String userType){
		if(userType == null){
			return null;
		}
		if(userType.equalsIgnoreCase("Customer")){
			return new CustomerController();
		}
		if(userType.equalsIgnoreCase("BusinessOwner")){
			return new BusinessController();
		}
		//add super user
		/*
		if(userType.equalsIgnoreCase("SuperUser")){
			return new AddSuperUserController();
		}*/
		
		return null;
	}
	
}
