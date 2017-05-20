package gui;

/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * CLASS TO BE DELETED
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import program.Controller;
import program.DatabaseConnection;

import org.apache.log4j.Logger;

public class MainController implements Initializable {
	
	private static Controller program = new Controller();
	private static Logger log = Logger.getLogger(MainController.class);
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		File varTmpData = new File("db/program.db");
		if (varTmpData.exists() == false) {
			boolean var = setup();
			if(var == false){
				Platform.exit();
				System.exit(0);
			}
		}
		boolean var = login();
		if (var == true) {
				if (program.getUser().getAccountType() == 1) {
					bOwnerWindow();
				} else {
					cUserWindow();
				}
		}
		else {
			Platform.exit();
			System.exit(0);
		}
		
	}
	
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
	
	/**************
	 * LOGIN
	 **************/


	/**
	 * Returns User to login
	 * 
	 * @author [Programmer]
	 */
	@FXML
	public boolean login() {
		log.debug("Login Started");
		try {
			Stage secondaryStage = new Stage();
			secondaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
			Parent root = FXMLLoader.load(getClass().getResource("loginLayout.fxml"));
			secondaryStage.setTitle("Login");
			secondaryStage.setResizable(false);
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
	
	private boolean bOwnerWindow(){
		try {
			Stage secondaryStage = new Stage();
			secondaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
			Parent root = FXMLLoader.load(getClass().getResource("businessLayout.fxml"));
			secondaryStage.setTitle("Business Application");
			secondaryStage.setResizable(false);
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
			secondaryStage.setResizable(false);
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