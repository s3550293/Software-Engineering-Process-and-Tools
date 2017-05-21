package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import program.Controller;
import program.DatabaseConnection;

public class CustomizeController  implements Initializable {
	private final Controller program = new Controller();
	private final DatabaseConnection connection = new DatabaseConnection();
	private final DatabaseConnection con = new DatabaseConnection();
	private File fa = null;
	
	@FXML
	AnchorPane root;
	
	@FXML
	Button btnBlue, btnPurp, btnGreen, btnOrng, btnUpload;
	
	@FXML
	ImageView imgView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(con.getlogo(program.getUser().getID())!=null)
		{
			FileInputStream fis;
			try {
				fis = new FileInputStream(con.getlogo(program.getUser().getID()));
				Image image = new Image(fis);
	            imgView.setImage(image);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(connection.getOneBusiness(program.getUser().getBusinessID()).color() > 0){
			root.setStyle(program.setColor());
		}
	}
	
	public void setCI() {
		root.setStyle(program.setColor());
	}
	
	@FXML
	public void confirm(){
		if(fa!=null){
			fa.renameTo(new File(System.getProperty("user.home")+"/resourcing/"+fa.getName()));
			con.addImage(System.getProperty("user.home")+"/resourcing/"+fa.getName(), program.getUser().getID());
		}	
		Stage st = (Stage) root.getScene().getWindow();
		st.close();

	}
	
	@FXML
	public void addImage(){
		Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        try{
        	fa = fileChooser.showOpenDialog(stage);
        	Image image = new Image(fa.getPath());
            imgView.setImage(image);
        }
        catch(Exception e){}
	}
	
	@FXML
	public void blue(){
		int id = program.business().getBusinessId();
		DatabaseConnection con = new DatabaseConnection();
		con.updateBO(id, 1);
		root.setStyle(program.setColor());
	}
	
	@FXML
	public void purp(){
		int id = program.business().getBusinessId();
		DatabaseConnection con = new DatabaseConnection();
		con.updateBO(id, 2);
		root.setStyle(program.setColor());
	}
	
	@FXML
	public void green(){
		int id = program.business().getBusinessId();
		DatabaseConnection con = new DatabaseConnection();
		con.updateBO(id, 3);
		root.setStyle(program.setColor());
	}
	
	@FXML
	public void ong(){
		int id = program.business().getBusinessId();
		DatabaseConnection con = new DatabaseConnection();
		con.updateBO(id, 4);
		root.setStyle(program.setColor());
	}
	
	
}
