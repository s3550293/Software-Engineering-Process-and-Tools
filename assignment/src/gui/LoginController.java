package gui;

import java.io.File;
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
	UserFactory userFactory = new UserFactory();
	@FXML
	TextField txtUserLogin;
	@FXML
	PasswordField txtPassLogin;
	@FXML
	Button btnLogin, btnRegister;
	@FXML
	Label lblError;
	
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		log.debug("LOGGER: Entered ini");
		lblError.setVisible(false);
		File varTmpData = new File("db/company.db");
		if (varTmpData.exists() == false) {
			log.debug("LOGGER: File doesnt exist");
			boolean var = setup();
			if(var == false){
				log.debug("LOGGER: setup faild");
				Platform.exit();
				System.exit(0);
			}
		}
	}
	
	/**
	 * Check login detail and log the user in, 
	 * and terminate the application if maximum failed attempts reach
	 * @author Joseph Garner
	 */
	@FXML
	public void loginAction() {
		Stage loginstage = (Stage) btnLogin.getScene().getWindow();
		int loginCheck = loginFuction.logInProcess(txtUserLogin.getText(), txtPassLogin.getText());
		if(loginCount < 10){
			if(loginCheck == -2){
				lblError.setVisible(true);
				loginCount += 1;
				log.debug("LOGGER: login count - "+loginCount);
			}
			else if(loginCheck == 1 || loginCheck == 0 || loginCheck == 2){
				log.debug("LOGGER: Login is equal to 1 or 0");
				lblError.setVisible(false);
				loginstage.hide();
				if (program.getUser().getAccountType() == 1) {
					IUser businessowner = userFactory.getUser("BusinessOwner");
					businessowner.getUserWindow();
				} else if(program.getUser().getAccountType() == 0) {
					IUser customer = userFactory.getUser("Customer");
					customer.getUserWindow();
				} else if(program.getUser().getAccountType() == 2){
					rootWindow();
				}
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
		txtUserLogin.setText("");
		txtPassLogin.setText("");
		loginstage.show();
		
	}
	
	/**
	 * Loads Register window
	 * @author Joseph Garner
	 */
	@FXML
	public void registerAction(){
		try {
            Stage secondaryStage = new Stage();
            secondaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
            Parent root = FXMLLoader.load(getClass().getResource("registerLayout.fxml"));
            log.debug("LOGGER: FXML Loaded");
            secondaryStage.setTitle("Register");
            secondaryStage.setResizable(false);
            secondaryStage.setScene(new Scene(root));
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            log.debug("LOGGER: Ready to show");
            secondaryStage.showAndWait();
        }
		catch(IOException ioe) {
            log.warn(ioe.getMessage());
            log.debug("LOGGER: Creation Fail");
        }
	}
	
	/*
	private boolean bOwnerWindow(){
		try {
			Stage secondaryStage = new Stage();
			secondaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
			Parent root = FXMLLoader.load(getClass().getResource("businessLayout.fxml"));
			secondaryStage.setTitle("Business Application");
			secondaryStage.setMinWidth(800);
			secondaryStage.setMinHeight(650);
			secondaryStage.setMaxWidth(1000);
			secondaryStage.setMaxHeight(850);
			secondaryStage.setScene(new Scene(root));
			secondaryStage.initModality(Modality.APPLICATION_MODAL);
			secondaryStage.showAndWait();
			if (program.getUser() != null) {
				return true;
			}
		} catch (IOException ioe) {
			log.warn(ioe.getMessage());
		}
		log.debug("false");
		return false;
	}*/
	
	/*
	private boolean cUserWindow(){
		try {
			Stage secondaryStage = new Stage();
			secondaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
			Parent root = FXMLLoader.load(getClass().getResource("customerLayout.fxml"));
			secondaryStage.setTitle("Customer Application");
			secondaryStage.setMinWidth(800);
			secondaryStage.setMinHeight(650);
			secondaryStage.setMaxWidth(1000);
			secondaryStage.setMaxHeight(850);
			secondaryStage.setScene(new Scene(root));
			secondaryStage.initModality(Modality.APPLICATION_MODAL);
			secondaryStage.showAndWait();
			if (program.getUser() != null) {
				return true;
			}
		} catch (IOException ioe) {
			log.warn(ioe.getMessage());
		}
		log.debug("false");
		return false;
	}*/
	/**
	 * Launches the setup window
	 * @return
	 */
	@FXML
	public boolean setup() {
		log.debug("Setup Started");
		try {
			Stage secondaryStage = new Stage();
			secondaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
			Parent root = FXMLLoader.load(getClass().getResource("setupLayout.fxml"));
			secondaryStage.setTitle("Setup");
			secondaryStage.setResizable(false);
			secondaryStage.setScene(new Scene(root));
			secondaryStage.initModality(Modality.APPLICATION_MODAL);
			secondaryStage.showAndWait();
			File varTmpData = new File("db/program.db");
			if (varTmpData.exists() == false) {
				log.debug("false");
				return false;
			}
		} catch (IOException ioe) {
			log.warn(ioe.getMessage());
		}
		return true;
	}
	
	public boolean rootWindow(){
		try {
			Stage secondaryStage = new Stage();
			secondaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
			Parent root = FXMLLoader.load(getClass().getResource("rootLayout.fxml"));
			secondaryStage.setTitle("Customer Application");
			secondaryStage.setMinWidth(800);
			secondaryStage.setMinHeight(650);
			secondaryStage.setMaxWidth(1000);
			secondaryStage.setMaxHeight(850);
			secondaryStage.setScene(new Scene(root));
			secondaryStage.initModality(Modality.APPLICATION_MODAL);
			secondaryStage.showAndWait();
			if (program.getUser() != null) {
				return true;
			}
		} catch (IOException ioe) {
			log.warn(ioe.getMessage());
		}
		log.debug("false");
		return false;
	}
	

}
