package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.log4j.Logger;

public class MainController implements Initializable{
	
	private  static Logger log = Logger.getLogger(MainController.class);
	public MainController() {}
	
	
	public void initialize(URL url, ResourceBundle rb)
	{
		 login();

	}
	
	public boolean login()
	{
		log.debug("Login Started");
		try {
            Stage secondaryStage = new Stage();
            //secondaryStage.getIcons().add(new Image("/Images/ic_date_range_black_48dp_2x.png"));
            Parent root = FXMLLoader.load(getClass().getResource("loginLayout.fxml"));
            secondaryStage.setTitle("Login");
            secondaryStage.setResizable(false);
            secondaryStage.setScene(new Scene(root));
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.showAndWait();
            /*
            if( User exists ) {
                show correct window
                set user to logged in user
                return true
                log.debug("logging: login success");
            }
            */
            return false;
        }
		catch(IOException ioe) {
            log.warn(ioe.getMessage());
        }
        log.debug("false");
        return false;
	}
}
