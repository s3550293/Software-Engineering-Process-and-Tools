package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import program.Controller;
import program.Login;


public class LoginController implements Initializable {
	public static Logger log = Logger.getLogger(LoginController.class);
	private Login loginFuction = new Login();
	private Controller program = new Controller();
	private int loginCount = 0;
	
	@FXML
	TextField txtUserLogin;
	@FXML
	PasswordField txtPassLogin;
	@FXML
	Button btnLogin, btnRegister;
	@FXML
	Label lblError;
	
	public void initialize(URL url, ResourceBundle rb)
	{
		lblError.setVisible(false);
	}
	
	/**
	 * Logs User in
	 * @author Joseph Garner
	 */
	@FXML
	public void loginAction() {
		//TODO
		Stage loginstage = (Stage) btnLogin.getScene().getWindow();
		int loginCheck = loginFuction.logInProcess(txtUserLogin.getText(), txtPassLogin.getText());
		if(loginCount < 10){
			if(loginCheck == -2){
				lblError.setVisible(true);
				loginCount += 1;
				log.debug("LOGGER: login count - "+loginCount);
			}
			else if(loginCheck == 1 || loginCheck == 0){
				log.debug("LOGGER: Login is equal to 1 or 0");
				lblError.setVisible(false);
				loginstage.close();
			}
			else{
				log.debug("LOGGER: Login returning a value different from -2, 1 or 0");
				lblError.setVisible(true);
				loginCount += 1;
			}
		}
		else{
			program.messageBox("WARN", "Login Error", "Maximum login attempts reached", "The maximum login attemps has been reached, the program will now terminate");
			Platform.exit();
			System.exit(0);
		}
		
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
            secondaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
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
