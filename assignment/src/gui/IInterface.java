package gui;

import java.net.URL;
import java.util.ResourceBundle;


public interface IInterface {
	
	public interface IUser{
		public void initialize(URL url, ResourceBundle rb);
		public boolean getUserWindow();
		public void logout();
	}
	
	public interface ISetup{
		public boolean getSetup();
	}
	
}



