package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
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
	private final DatabaseConnection con = new DatabaseConnection();
	
	@FXML
	AnchorPane root;
	
	@FXML
	Button btnBlue, btnPurp, btnGreen, btnOrng, btnUpload;
	
	@FXML
	ImageView imgView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setCI();
	}
	
	public void setCI() {
		root.setStyle(program.setColor());
	}
	
	@FXML
	public void addImage(){
		Stage stage = new Stage();
        stage.getIcons().add(new Image("/Images/ic_date_range_black_48dp_2x.png"));
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        BufferedImage in = null;
        File file = null;
        try {
            file = fileChooser.showOpenDialog(stage);
            in = ImageIO.read(file);
        } catch (Exception nulle) {
            
        }
        BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
        con.addImage(newImage,program.getUser().getID());
        Image image = SwingFXUtils.toFXImage(newImage, null);
        imgView.setImage(image);
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
