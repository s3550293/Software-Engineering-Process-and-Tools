package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import program.Controller;
import program.DatabaseConnection;

public class CustomizeController  implements Initializable {
	private final Controller program = new Controller();
	
	@FXML
	AnchorPane root;
	
	@FXML
	Button btnBlue, btnPurp, btnGreen, btnOrng, btnUpload;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setCI();
	}
	
	public void setCI() {
		root.setStyle(program.setColor());
	}
	
	@FXML
	public void blue(){
		int id = program.business().getBusinessId();
		DatabaseConnection con = new DatabaseConnection();
		con.updateBO(id, 1);
	}
	
	@FXML
	public void purp(){
		int id = program.business().getBusinessId();
		DatabaseConnection con = new DatabaseConnection();
		con.updateBO(id, 2);
	}
	
	@FXML
	public void green(){
		int id = program.business().getBusinessId();
		DatabaseConnection con = new DatabaseConnection();
		con.updateBO(id, 3);
	}
	
	@FXML
	public void ong(){
		int id = program.business().getBusinessId();
		DatabaseConnection con = new DatabaseConnection();
		con.updateBO(id, 4);
	}
	
	
}
