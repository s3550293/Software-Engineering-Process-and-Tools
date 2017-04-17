package gui;

import java.io.IOException;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	 * @author Joseph Garner
	 */
	@FXML
	public void registerAction(){
		//TODO
		try {
            Stage secondaryStage = new Stage();
            //secondaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
            Parent root = FXMLLoader.load(getClass().getResource("registerLayout.fxml"));
            secondaryStage.setTitle("Register");
            secondaryStage.setResizable(false);
            secondaryStage.setScene(new Scene(root));
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.showAndWait();
        }
		catch(IOException ioe) {
            log.warn(ioe.getMessage());
        }
	}
	

}
