package gui;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBOController {
	
	@FXML
	TextField  txtFristName, txtLName, txtUsername;
	
	@FXML
	PasswordField passPassword, passConf;
	
	@FXML
	public void cancel(){
		Stage stage= (Stage) txtFristName.getScene().getWindow();
		txtFristName.setText("");
		txtLName.setText("");
		txtUsername.setText("");
		passPassword.setText("");
		passConf.setText("");
		stage.close();
	}
	
	@FXML
	public void create(){
		//TODO
	}

}
