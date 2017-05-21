package gui;


import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
		con.addUser(txtUsername.getText(),passPassword.getText(), 1);
		int id = con.getUser(txtUsername.getText()).getID();
		con.createBusiness(id, txtBName.getText());
		con.userBO(id, id);
	}

}
