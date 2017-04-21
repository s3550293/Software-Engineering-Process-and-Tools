package gui;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class RegisterController implements Initializable {

	public static Logger log = Logger.getLogger(RegisterController.class);

	@FXML
	TextField txtRegUsername, txtRegEmail;

	@FXML
	DatePicker dpRegDOB;

	@FXML
	ComboBox<String> cmbRegGender;

	@FXML
	PasswordField pfRegPassword, pfRegConfPassword;

	@FXML
	Button btnCancel, btnRegister;

	/**
	 * initializes the stage
	 * 
	 * @author [Programmer]
	 */
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	/**
	 * Returns User to login
	 * 
	 * @author Joseph Garner
	 */
	@FXML
	public void cancel() {
		//TODO
	}

	/**
	 * Registers User and returns to login
	 * 
	 * @author Joseph Garner
	 */
	@FXML
	public void register() {
		//TODO
	}

}
