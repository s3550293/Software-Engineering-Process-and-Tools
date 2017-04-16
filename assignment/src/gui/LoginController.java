package gui;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	public static Logger log = Logger.getLogger(LoginController.class);
	
	@FXML
	TextField txtUserLogin;
	@FXML
	PasswordField txtPassLogin;
	@FXML
	Button btnLogin, btnRegister;
	
	/**
	 * Logs User in
	 * @author [Programmer]
	 */
	@FXML
	public void loginAction() {
		//TODO
	}
	
	/**
	 * Loads Register window
	 * @author [Programmer]
	 */
	@FXML
	public void registerAction(){
		//TODO
	}
	

}
