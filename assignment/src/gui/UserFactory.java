package gui;

import gui.IInterface.ISetup;
import gui.IInterface.IUser;

public class UserFactory{
	
	public IUser getUser(String userType){
		if(userType == null){
			return null;
		}
		if(userType.equalsIgnoreCase("Customer")){
			return (IUser) new CustomerController();
		}
		if(userType.equalsIgnoreCase("BusinessOwner")){
			return (IUser) new BusinessController();
		}
		if(userType.equalsIgnoreCase("SuperUser")){
			return (IUser) new RootController();
		}
		
		return null;
	}
	public ISetup getSetup(String setup){
		if(setup == "SetUp"){
			return (ISetup) new SetupController();
		}
		return null;
	}
	
}
