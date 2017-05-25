package gui;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import gui.IInterface.IUser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import program.Controller;
import program.Register;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class RegisterController implements Initializable {

	public static Logger log = Logger.getLogger(RegisterController.class);
	private Controller program = new Controller();
	private Register regProgram = new Register();

	@FXML
	TextField txtRegUsername, txtRegEmail, txtFirstName, txtLastName, txtMobileNumber;

	@FXML
	DatePicker dpRegDOB;

	@FXML
	ComboBox<String> cmbRegGender;
	@FXML
	ComboBox<String> cmbDay;
	@FXML
	ComboBox<String> cmbMonth;
	@FXML
	ComboBox<String> cmbYear;
	
	@FXML
	PasswordField pfRegPassword, pfRegConfPassword;

	@FXML
	Button btnCancel, btnRegister;

	/**
	 * initializes the stage
	 * 
	 * @author Joseph Ganer
	 */
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		List<String> listDay = new ArrayList<String>();
		for(int i =1; i<32;i++){listDay.add(Integer.toString(i));}
		ObservableList<String> obListDay = FXCollections.observableList(listDay);
		cmbDay.setItems(obListDay);
		List<String> listMonth = new ArrayList<String>();
		for(int i =1; i<13;i++){listMonth.add(Integer.toString(i));}
		ObservableList<String> obListMonth = FXCollections.observableList(listMonth);
		cmbMonth.setItems(obListMonth);
		List<String> list = new ArrayList<String>();
		DateFormat dayFormat = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		Date date = null;
		log.debug("LOGGER: Calculating Years");
	    for (int i = 0; i < 100; i++) {
	    	date = cal.getTime();
	    	list.add(dayFormat.format(date));
	        cal.add(Calendar.YEAR, -1);
	    }
		ObservableList<String> obList = FXCollections.observableList(list);
		cmbYear.setItems(obList);
		
		cmbMonth.valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(ObservableValue ov, String t, String t1) {
	        	log.debug("LOGGER: t1 value - "+t1);
	        	if(t1.equals("4") || t1.equals("6") || t1.equals("9") || t1.equals("11")){
	        		cmbDay.getItems().clear();
	        		List<String> listDay = new ArrayList<String>();
	        		for(int i =1; i<31;i++){listDay.add(Integer.toString(i));}
	        		ObservableList<String> obListDay = FXCollections.observableList(listDay);
	        		cmbDay.setItems(obListDay);
	        	}
	        	else if(t1.equals("2")){
	        		cmbDay.getItems().clear();
	        		List<String> listDay = new ArrayList<String>();
	        		for(int i =1; i<29;i++){listDay.add(Integer.toString(i));}
	        		ObservableList<String> obListDay = FXCollections.observableList(listDay);
	        		cmbDay.setItems(obListDay);
	        	}
	        	else{
	        		List<String> listDay = new ArrayList<String>();
	        		for(int i =1; i<32;i++){listDay.add(Integer.toString(i));}
	        		ObservableList<String> obListDay = FXCollections.observableList(listDay);
	        		cmbDay.setItems(obListDay);
	        	}
	        }
		});
		List<String> gender = new ArrayList<String>();
		gender.add("Female");
		gender.add("Male");
		gender.add("Other");
		ObservableList<String> obListGen = FXCollections.observableList(gender);
		cmbRegGender.setItems(obListGen);
	}

	
	/**
	 * Returns User to login
	 * 
	 * @author Joseph Garner
	 */
	@FXML
	public void cancel() {
		Stage regStage = (Stage) btnCancel.getScene().getWindow();
		regStage.close();
	}

	/**
	 * Registers User and returns to login
	 * 
	 * @author Joseph Garner
	 */
	@FXML
	public void register() {
		//TODO
		String date = null;
		log.debug("LOGGER: entered createUser function");
        if (program.checkInputToContainInvalidChar(txtFirstName.getText())) {
            program.messageBox("ERROR", "Error", "First Name field is empty or contains an invalid character", "");
            return;
        }
        if (program.checkInputToContainInvalidChar(txtLastName.getText())) {
            program.messageBox("ERROR", "Error", "Last Name field is empty or contains an invalid character", "");
            return;
        }
        System.out.println("BUSINESS ID = "+program.business().getBusinessId());
        if (regProgram.checkTakenUsername(txtRegUsername.getText(),program.business().getBusinessId())) {
            program.messageBox("ERROR", "Error", "Username already taken", "");
            return;
        }
        if (!program.CheckUsername(txtRegUsername.getText())) {
            program.messageBox("ERROR", "Error", "Invalid Characters in Username", "");
            return;
        }
        if (program.checkEmail(txtRegEmail.getText())) {
            program.messageBox("ERROR", "Error", "Invalid Email", "");
            return;
        }
        if(txtMobileNumber.getText().length() != 10)
        {
        	program.messageBox("ERROR", "Error", "Invalid Phone Number", "");
            return;
        }
        if(program.changeInputIntoValidInt(txtMobileNumber.getText().toString()) == -1)
        {
        	program.messageBox("ERROR", "Error", "Invalid Phone Number", "");
            return;
        }
        if(cmbDay.getSelectionModel().getSelectedItem() == null || cmbMonth.getSelectionModel().getSelectedItem() == null || cmbYear.getSelectionModel().getSelectedItem() == null){
        	program.messageBox("ERROR", "Error", "Invalid Date", "");
            return;
        }
        date = cmbDay.getSelectionModel().getSelectedItem() + "/" + cmbMonth.getSelectionModel().getSelectedItem() + "/" + cmbYear.getSelectionModel().getSelectedItem();
        if(!program.checkForBackToFuture(date))
        {
        	log.debug("LOGGER: doc...ah...are you telling me you built a TIME MACHINE...out of a delorean?!");
        	program.messageBox("ERROR", "Incorrect DOB", "Invalid DOB", "DOB is in the future");
        	return;
        }
        if(cmbRegGender.getSelectionModel().getSelectedItem() == null)
        {
        	program.messageBox("ERROR", "Error", "Invalid Gender", "");
            return;
        }
        if(!regProgram.checkPassword(pfRegPassword.getText())){
        	program.messageBox("ERROR", "Error", "Invalid Password", "Password must be longer then 6 characters and contain Uppercase, lowercase and number characters");
            return;
        }
        if(!pfRegPassword.getText().toString().equals(pfRegConfPassword.getText().toString()))
        {
        	log.debug("LOGGER: Password:"+pfRegPassword.getText());
        	log.debug("LOGGER: Conf Password:"+pfRegConfPassword.getText());
        	program.messageBox("ERROR", "Error", "Passwords Do No Match", "");
            return;
        }
        regProgram.registerUser(txtFirstName.getText(), txtLastName.getText(), txtRegUsername.getText(), txtRegEmail.getText(), txtMobileNumber.getText(), date, cmbRegGender.getSelectionModel().getSelectedItem(), pfRegPassword.getText(), program.business().getBusinessId());
        program.messageBox("INFO", "User Added", "User Added", "You have successfully created an account");
        cancel();
	}


}
