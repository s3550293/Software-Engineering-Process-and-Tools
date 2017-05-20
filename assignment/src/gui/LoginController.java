package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import gui.IInterface.IUser;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import program.Business;
import program.Controller;
import program.Database;
import program.DatabaseConnection;
import program.Login;


public class LoginController implements Initializable {
	public static Logger log = Logger.getLogger(LoginController.class);
	private Login loginFuction = new Login();
	private Controller program = new Controller();
	private DatabaseConnection con = new DatabaseConnection();
	private SetupController setup = new SetupController();
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
	@FXML
	ComboBox<Business> cmbBusiness;
	
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		String file = "company.db";
		log.debug("LOGGER: Entered ini");
		lblError.setVisible(false);
		File varTmpData = new File("db/company.db");
		if (varTmpData.exists() == false) {
			Database data = new Database(file);
			data.createNewDatabase(file);
			data.createTable(file);
			setup.ini();
		}
		popcmb();
		cmbBusiness.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Business>() {
			@Override
			public void changed(ObservableValue<? extends Business> observable, Business oldValue, Business newValue) {
				if (newValue != null) {
					//TODO
				}
			}
		});
	}
	
	private void popcmb(){
		ArrayList<Business> arr = new ArrayList<>(con.getBusiness());
		ObservableList<Business> list = FXCollections.observableList(arr);
		if (list != null) {
			cmbBusiness.setItems(list);
			cmbBusiness.setCellFactory(new Callback<ListView<Business>, ListCell<Business>>() {
				@Override
				public ListCell<Business> call(ListView<Business> p) {

					ListCell<Business> cell = new ListCell<Business>() {
						@Override
						protected void updateItem(Business t, boolean bln) {
							super.updateItem(t, bln);
							if (t != null) {
								setText(t.getBusinessName());
							} else {
								cmbBusiness.setPlaceholder(new Label("No Businesses"));
							}
						}
					};
					return cell;
				}
			});
		} else {
			log.warn("Unable to load Employees");
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
		int loginCheck = loginFuction.logInProcess(txtUserLogin.getText(),program.business().getBusinessId(), txtPassLogin.getText());
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
					IUser admin = userFactory.getUser("SuperUser");
					admin.getUserWindow();
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
	}
	
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
	
	/*
	
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
	}*/
	

}
