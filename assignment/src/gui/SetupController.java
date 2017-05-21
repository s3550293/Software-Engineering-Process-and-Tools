package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import gui.IInterface.ISetup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import program.Business;
import program.BusinessMenu;
import program.BusinessOwner;
import program.Controller;
import program.DatabaseConnection;
import program.Register;
import program.Service;

public class SetupController implements Initializable, ISetup {
	public final static Logger log = Logger.getLogger(SetupController.class);
	private final Controller program = new Controller();
	private static Business business = new Business();
	private static BusinessOwner businessOwner = new BusinessOwner();
	public final Register regpro = new Register();
	public final BusinessMenu bMenu = new BusinessMenu();
	/*
	 * Oder of panes stkpWelcome > stkpDetails > stkpTimeSlot > (Setup Finishes and database is created) > stkpSelectColor
	 */
	@FXML
	AnchorPane root;
	
	@FXML
	StackPane stkpWelcome, stkpDetails, stkpTimeSlot, stkpSelectColor;
	
	@FXML
	TextField txtFNam, txtLNam, txtBNam, txtBPho;
	
	@FXML
	TextArea txtaBAdre;
	
	@FXML
	ComboBox<String> cmbMFOpen, cmbMFClose, cmbSSOpen, cmbSSClose;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		dispMFOpen();
		dispMFClose();
		dispSSOpen();
		dispSSClose();
	}
	
	/**
	 * Assigning the Business opening and closing hours for the business
	 * @author Luke Mason
	 * @param weekdayStart
	 * @param weekdayEnd
	 * @param weekendStart
	 * @param weekendEnd
	 */
	public void assignOpenClosingTimesToGlobal(Date weekdayStart ,Date weekdayEnd, Date weekendStart ,Date weekendEnd)
	{
		//Change Dates to Strings
		String MFOpen = program.timeToStr(weekdayStart);
		String MFClose = program.timeToStr(weekdayEnd);
		String SSOpen = program.timeToStr(weekendStart);
		String SSClose = program.timeToStr(weekendEnd);
		System.out.println(MFOpen);
		System.out.println(MFClose);
		System.out.println(SSOpen);
		System.out.println(SSClose);
		//assign times for weekends and weekdays
		assignOpenClosingTimesToWeekDays(MFOpen, MFClose);
		assignOpenClosingTimesToWeekEnds(SSOpen, SSClose);
	}
	
	public void assignOpenClosingTimesToWeekDays(String MFOpen,String MFClose)
	{
		String[] times = splitTimeIntoThreeBlocks(MFOpen, MFClose);
		bMenu.MFearly = times[0];
		bMenu.MFearlyMidDay = times[1];
		bMenu.MFlateMidDay = times[2];
		bMenu.MFlate = times[3];
	}
	public void assignOpenClosingTimesToWeekEnds(String SSOpen,String SSClose)
	{
		String[] times = splitTimeIntoThreeBlocks(SSOpen, SSClose);
		bMenu.SSearly = times[0];
		bMenu.SSearlyMidDay = times[1];
		bMenu.SSlateMidDay = times[2];
		bMenu.SSlate = times[3];
	}
	
	/**
	 * Gets a start time and end time and splits the time between them into 3 blocks which are bounded by 4 different times.
	 * @param startTime - HH:MM
	 * @param endTime - HH:MM
	 * @return
	 */
	public String[] splitTimeIntoThreeBlocks(String startTime, String endTime)
	{
		int MINIMUM_OPEN_TIME = 60; //means that the business must be open for at least 60 minutes a day
		//A-a represents startTime, B-b represents endTime
		String[] times = {"","","",""};
		//Getting substring hours
		String A = startTime.substring(0,2);
		String B = endTime.substring(0,2);
		//Getting substring minutes
		String a = startTime.substring(3);
		String b = endTime.substring(3);
		//converting strings into integers
		int AA = Integer.parseInt(A);
		int BB = Integer.parseInt(B);
		int aa = Integer.parseInt(a);
		int bb = Integer.parseInt(b);
		//getting minutes from hours
		int AAA = AA * 60;
		int BBB = BB * 60;
		//getting total minutes
		int aaa = AAA + aa;
		int bbb = BBB + bb;
		//Error checking
		if((bbb - aaa < MINIMUM_OPEN_TIME)||(aaa > 1380 || bbb > 1440))
		{
			log.warn("endTime, startTime are invalid\n");
			return times;
		}
		//Getting time minutes between startTime aaa and endTime bbb
		int timeDifference = bbb - aaa;
		//getting 1 third of time difference
		int thirdOfTime = timeDifference/3;
		//Assigning times
		times[0] = startTime;
		//converting minutes into HH:MM
		int middayEarlyTime = aaa + thirdOfTime;
		int middayLateTime = middayEarlyTime + thirdOfTime;
		int hours;
		int minutes;
		String formattedHours;
		String formattedMinutes;
		//Formatting numbers to two digitse.g 02 04 06 10 12 etc	
		hours = middayEarlyTime/60;
		minutes = middayEarlyTime%60;
		formattedHours = String.format("%02d", hours);
		formattedMinutes = String.format("%02d", minutes);
		//Assigning time for middayEarlyTime
		times[1] = formattedHours+":"+formattedMinutes;
		//Formatting numbers to two digitse.g 02 04 06 10 12 etc
		hours = middayLateTime/60;
		minutes = middayLateTime%60;
		formattedHours = String.format("%02d", hours);
		formattedMinutes = String.format("%02d", minutes);
		//Assigning time for middayLateTime
		times[2] = formattedHours+":"+ formattedMinutes;
		times[3] = endTime;
		return times;
	}
	/**
	 * Moves the user to the starting view
	 * @author Joseph Garner
	 */
	@FXML
	public void stSet(){
		stkpWelcome.setVisible(false);
		stkpDetails.setVisible(true);
	}
	/**
	 * Moves the user to the next view
	 * @author Joseph Garner
	 */
	@FXML
	public void next(){
		//TODO Input Checks
		
		if(stkpDetails.isVisible())
		{
			if (program.checkInputToContainInvalidChar(txtFNam.getText().toString())) {
				program.messageBox("ERROR", "Error", "First Name field is empty or contains an invalid character", "");
				return;
			}
			if (program.checkInputToContainInvalidChar(txtLNam.getText().toString())) {
				program.messageBox("ERROR", "Error", "Last Name field is empty or contains an invalid character", "");
				return;
			}
			/*if (program.checkInputToContainInvalidChar(txtBNam.getText().toString())) {
				program.messageBox("ERROR", "Error", "Business Name field is empty or contains an invalid character", "");
				return;
			}*/
			if(txtBPho.getText().length() != 10)
	        {
	        	program.messageBox("ERROR", "Error", "Invalid Phone Number", "");
	            return;
	        }
			if(program.changeInputIntoValidInt(txtBPho.getText().toString()) == -1)
	        {
	        	program.messageBox("ERROR", "Error", "Invalid Phone Number", "");
	            return;
	        }
			if(txtaBAdre.getText().length() ==0){
				program.messageBox("ERROR", "Error", "Address field is empty or contains an invalid character", "");
				return;
			}
			businessOwner.setFName(txtFNam.getText());
			businessOwner.setLName(txtLNam.getText());
			businessOwner.setPhone(txtBPho.getText());
			businessOwner.setAddress(txtaBAdre.getText());

			stkpTimeSlot.setVisible(true);
			stkpDetails.setVisible(false);
			return;
		}
		if(stkpTimeSlot.isVisible())
		{
			if (cmbMFOpen.getSelectionModel().getSelectedItem() == null) {
				program.messageBox("ERROR", "Error", "Opening Hours Has Not Been Chosen", "Please select a time");
				return;
			}
			if (cmbMFClose.getSelectionModel().getSelectedItem() == null) {
				program.messageBox("ERROR", "Error", "Closing Hours Has Not Been Chosen", "Please select a time");
				return;
			}
			if (cmbSSOpen.getSelectionModel().getSelectedItem() == null) {
				program.messageBox("ERROR", "Error", "Opening Hours Has Not Been Chosen", "Please select a time");
				return;
			}
			if (cmbSSClose.getSelectionModel().getSelectedItem() == null) {
				program.messageBox("ERROR", "Error", "Closing Hours Has Not Been Chosen", "Please select a time");
				return;
			}
			
			businessOwner.setWeekdayStart(program.strToTime(cmbMFOpen.getSelectionModel().getSelectedItem()));
			businessOwner.setWeekdayEnd(program.strToTime(cmbMFClose.getSelectionModel().getSelectedItem()));
			businessOwner.setWeekendStart(program.strToTime(cmbSSOpen.getSelectionModel().getSelectedItem()));
			businessOwner.setWeekendEnd(program.strToTime(cmbSSClose.getSelectionModel().getSelectedItem()));
			if(businessOwner.getWeekdayStart().after(businessOwner.getWeekdayEnd()))
			{
				program.messageBox("ERROR", "Error", "Invalid choice. Open hours later than closing hours", "");
				return;
			}
			if(businessOwner.getWeekendStart().after(businessOwner.getWeekendEnd()))
			{
				program.messageBox("ERROR", "Error", "Invalid choice. Open hours later than closing hours", "");
				return;
			}
			DatabaseConnection connect = new DatabaseConnection();
			
			String wds = program.timeToStr(businessOwner.getWeekdayStart());
			String wde = program.timeToStr(businessOwner.getWeekdayEnd());
			String wes = program.timeToStr(businessOwner.getWeekendStart());
			String wee = program.timeToStr(businessOwner.getWeekendEnd());
			int id = business.getBusinessId();
			String fName = businessOwner.getFName();
			String lName = businessOwner.getLName();
			String phone = businessOwner.getPhone();
			String address = businessOwner.getAddress();
			
			System.out.println("WDS = "+wds);
			System.out.println("WDE = "+wde);
			System.out.println("WES = "+wes);
			System.out.println("WEE = "+wee);
			System.out.println("ID = "+id);
			System.out.println("firstName = "+fName);
			System.out.println("lastName = "+lName);
			System.out.println("Phone = "+phone);
			System.out.println("Address = "+address);

			connect.addBusinessOwner(id, fName, lName, phone, address, wds, wde, wes, wee);
			//Add to the database the new information
			assignOpenClosingTimesToGlobal(businessOwner.getWeekdayStart(),businessOwner.getWeekdayEnd(),businessOwner.getWeekendStart(),businessOwner.getWeekendEnd());
			stkpTimeSlot.setVisible(false);
			stkpSelectColor.setVisible(true);
			return;
		}
		/*if(stkpLogin.isVisible())
		{
	        if (program.checkInputToContainInvalidChar(txtUserNam.getText().toString())) {
	            program.messageBox("ERROR", "Error", "Invalid Username", "");
	            return;
	        }
	        if(!regpro.checkPassword(txtPass.getText())){
	        	program.messageBox("ERROR", "Error", "Invalid Password", "Password must be longer then 6 characters and contain Uppercase, lowercase and number characters");
	            return;
	        }
	        if(!txtPass.getText().toString().equals(txtConPass.getText().toString()))
	        {
	        	log.debug("LOGGER: Password:"+txtPass.getText());
	        	log.debug("LOGGER: Conf Password:"+txtConPass.getText());
	        	program.messageBox("ERROR", "Error", "Passwords Do No Match", "");
	            return;
	        }
	        businessOwner.setUsern(txtUserNam.getText());
	        businessOwner.setPass(txtPass.getText());
			stkpSelectColor.setVisible(true);
			stkpLogin.setVisible(false);
			createDB();
			createBO();
			return;
		}*/
	}

	/**
	 * Moves the user to the next view
	 * @author Joseph Garner
	 */
	@FXML
	public void back(){
		if(stkpTimeSlot.isVisible()){
			stkpTimeSlot.setVisible(false);
			stkpDetails.setVisible(true);
			return;
		}
	}

	
	/**
	 * Closes the setup and start the program
	 * @author Joseph Garner
	 */
	@FXML
	public void openRun(){
		Stage setSt = (Stage) cmbMFOpen.getScene().getWindow();
		setSt.close();
	}
	
	private ArrayList<String> timeArr(){
		ArrayList<String> val = new ArrayList<>();
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 5);
		date.set(Calendar.MINUTE, 0);
		String time = null;
		for(int i = 0; i<39;i++)
		{
			time = date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE);
			if (((i & 1)== 0)){
				time = time+"0";
			}
			val.add(time);
			date.add(Calendar.MINUTE, 30);
		}
		return val;
	}
	
	
	private void dispMFOpen(){
		ObservableList<String> tl = FXCollections.observableList(timeArr());
		if(tl != null)
		{
			cmbMFOpen.setItems(tl);
			cmbMFOpen.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
	
				@Override
				public ListCell<String> call(ListView<String> p) {
	
					final ListCell<String> cell = new ListCell<String>() {
	
						@Override
						protected void updateItem(String t, boolean bln) {
							super.updateItem(t, bln);
	
							if (t != null) {
								setText(t);
							} else {
								setText(null);
							}
						}
	
					};
	
					return cell;
				}
			});
			cmbMFOpen.setButtonCell(new ListCell<String>() {
				@Override
				protected void updateItem(String t, boolean bln) {
					super.updateItem(t, bln);
					if (t != null) {
						setText(t);
					} else {
						setText(null);
					}
	
				}
			});
		}
	}
	private void dispMFClose(){
		ObservableList<String> tl = FXCollections.observableList(timeArr());
		if(tl != null)
		{
			cmbMFClose.setItems(tl);
			cmbMFClose.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
	
				@Override
				public ListCell<String> call(ListView<String> p) {
	
					final ListCell<String> cell = new ListCell<String>() {
	
						@Override
						protected void updateItem(String t, boolean bln) {
							super.updateItem(t, bln);
	
							if (t != null) {
								setText(t);
							} else {
								setText(null);
							}
						}
	
					};
	
					return cell;
				}
			});
			cmbMFClose.setButtonCell(new ListCell<String>() {
				@Override
				protected void updateItem(String t, boolean bln) {
					super.updateItem(t, bln);
					if (t != null) {
						setText(t);
					} else {
						setText(null);
					}
	
				}
			});
		}
	}
	private void dispSSOpen(){
		ObservableList<String> tl = FXCollections.observableList(timeArr());
		if(tl != null)
		{
			cmbSSOpen.setItems(tl);
			cmbSSOpen.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
	
				@Override
				public ListCell<String> call(ListView<String> p) {
	
					final ListCell<String> cell = new ListCell<String>() {
	
						@Override
						protected void updateItem(String t, boolean bln) {
							super.updateItem(t, bln);
	
							if (t != null) {
								setText(t);
							} else {
								setText(null);
							}
						}
	
					};
	
					return cell;
				}
			});
			cmbSSOpen.setButtonCell(new ListCell<String>() {
				@Override
				protected void updateItem(String t, boolean bln) {
					super.updateItem(t, bln);
					if (t != null) {
						setText(t);
					} else {
						setText(null);
					}
	
				}
			});
		}
	}
	private void dispSSClose(){
		ObservableList<String> tl = FXCollections.observableList(timeArr());
		if(tl != null)
		{
			cmbSSClose.setItems(tl);
			cmbSSClose.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
	
				@Override
				public ListCell<String> call(ListView<String> p) {
	
					final ListCell<String> cell = new ListCell<String>() {
	
						@Override
						protected void updateItem(String t, boolean bln) {
							super.updateItem(t, bln);
	
							if (t != null) {
								setText(t);
							} else {
								setText(null);
							}
						}
	
					};
	
					return cell;
				}
			});
			cmbSSClose.setButtonCell(new ListCell<String>() {
				@Override
				protected void updateItem(String t, boolean bln) {
					super.updateItem(t, bln);
					if (t != null) {
						setText(t);
					} else {
						setText(null);
					}
	
				}
			});
		}
	}
	
	@FXML
	public void chgCol(){
		log.debug("LOGGER Change color");
		root.setStyle("-fx-base1: #42f4b0; -fx-base3: Black;");
		//root.setStyle("-fx-base3: Black;");
	}
	
	
	public boolean getSetup() {
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
	
	
}
