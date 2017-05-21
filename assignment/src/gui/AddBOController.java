package gui;


import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import program.Business;
import program.DatabaseConnection;

public class AddBOController {
	
	@FXML
	TextField  txtBName, txtUsername;
	
	@FXML
	PasswordField passPassword, passConf;
	
	@FXML
	public void cancel(){
		Stage stage= (Stage) txtBName.getScene().getWindow();
		txtBName.setText("");
		txtUsername.setText("");
		passPassword.setText("");
		passConf.setText("");
		stage.close();
	}
	
	@FXML
	public void create(){
		DatabaseConnection con = new DatabaseConnection();
		con.createBusiness(txtBName.getText()); // Creating the business FIRST
		Business business = con.getBusiness(txtBName.getText());
		int businessID = business.getBusinessId();
		
		con.addUser(txtUsername.getText(),passPassword.getText(), 1,businessID); //Creating the BO SECOND
	}

}
