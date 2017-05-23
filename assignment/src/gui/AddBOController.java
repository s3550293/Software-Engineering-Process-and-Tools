package gui;


import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import program.Business;
import program.Controller;
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
		Stage stage= (Stage) txtBName.getScene().getWindow();
		DatabaseConnection con = new DatabaseConnection();
		Controller program = new Controller();
		if(txtUsername.getText().equals("root"))
		{
			program.messageBox("ERROR", "Error", "Username is invalid", "");
			return;
		}
		Business business = con.getBusiness(txtBName.getText().toUpperCase());
		if(business != null)
		{
			program.messageBox("ERROR", "Error", "Business Name is already Taken", "");
			return;
		}
		con.createBusiness(txtBName.getText().toUpperCase()); // Creating the business FIRST		
		business = con.getBusiness(txtBName.getText().toUpperCase());
		int businessID = business.getBusinessId();	
		con.addUser(txtUsername.getText(),passPassword.getText(), 1,businessID); //Creating the BO SECOND
		program.messageBox("SUCCESS", "Success", "Business '"+txtBName.getText().toUpperCase()+"' Created", "Username = "+txtUsername.getText()+" \nPassword = "+passPassword.getText());
		stage.close();//CLOSE WINDOW
	}

}
