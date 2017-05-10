package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import program.BusinessMenu;
import program.BusinessOwner;
import program.Controller;
import program.Database;
import program.DatabaseConnection;
import program.Register;
import program.Service;

public class SetupController implements Initializable {
	public final static Logger log = Logger.getLogger(SetupController.class);
	private final Controller program = new Controller();
	private final  DatabaseConnection connection = new DatabaseConnection();
	private static BusinessOwner business = new BusinessOwner();
	public final Register regpro = new Register();
	/*
	 * Oder of panes stkpWelcome > stkpDetails > stkpTimeSlot > (Setup Finishes and database is created) > stkpSelectColor
	 */
	@FXML
	AnchorPane root;
	
	@FXML
	StackPane stkpWelcome, stkpDetails, stkpTimeSlot, stkpLogin, stkpSelectColor;
	
	@FXML
	TextField txtFNam, txtLNam, txtBNam, txtBPho, txtUserNam, txtPass, txtConPass;
	
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
		//createDB();
		//ini();
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
			if (program.checkInputToContainInvalidChar(txtBNam.getText().toString())) {
				program.messageBox("ERROR", "Error", "Business Name field is empty or contains an invalid character", "");
				return;
			}
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
			business.setFName(txtFNam.getText());
			business.setLName(txtLNam.getText());
			business.setBusiness(txtBNam.getText());
			business.setPhone(txtBPho.getText());
			business.setAddress(txtaBAdre.getText());

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
			
			business.setWeekdayStart(program.strToTime(cmbMFOpen.getSelectionModel().getSelectedItem()));
			business.setWeekdayEnd(program.strToTime(cmbMFClose.getSelectionModel().getSelectedItem()));
			business.setWeekendStart(program.strToTime(cmbSSOpen.getSelectionModel().getSelectedItem()));
			business.setWeekendEnd(program.strToTime(cmbSSClose.getSelectionModel().getSelectedItem()));
			if(business.getWeekdayStart().after(business.getWeekdayEnd()))
			{
				program.messageBox("ERROR", "Error", "Invalid choice. Open hours later than closing hours", "");
				return;
			}
			if(business.getWeekendStart().after(business.getWeekendEnd()))
			{
				program.messageBox("ERROR", "Error", "Invalid choice. Open hours later than closing hours", "");
				return;
			}
			stkpLogin.setVisible(true);
			stkpTimeSlot.setVisible(false);
			return;
		}
		if(stkpLogin.isVisible())
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
	        business.setUsern(txtUserNam.getText());
	        business.setPass(txtPass.getText());
			stkpSelectColor.setVisible(true);
			stkpLogin.setVisible(false);
			createDB();
			createBO();
			return;
		}
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
		if(stkpLogin.isVisible()){
			stkpTimeSlot.setVisible(true);
			stkpLogin.setVisible(false);
		}
		
	}
	
	/**
	 * 
	 * @author Bryan
	 */
	@FXML
	public void createBO() {
		// TODO
		connection.addUser(business.getUsername(), business.getPassword(), 1);
		business.setID(connection.getUser(business.getUsername()).getID());
		connection.createBusiness(business);
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
	
	
	private void createDB(){
		Database db = new Database("company.db");
		db.createTable("company.db");
	}
	private void ini()
    {
		DatabaseConnection connect = new DatabaseConnection();
		BusinessMenu bMenu = new BusinessMenu();
		connect.addUser("admin","Monday10!",1);
		connect.addUser("William", "Apples22", 0);
		connect.addUser("Hannah", "Apples22", 0);
		
		connect.addEmployee("Luke Charles",25);
		connect.addEmployee("David Smith",26.6);
		connect.addEmployee("Will Turner",15);
		connect.addEmployee("Rob Pointer",14);
		connect.addEmployee("Adam Mason",12);
		connect.addEmployee("David Chang",17);
		connect.addEmployee("Joseph Tun",17);
		connect.addEmployee("Casey Pointer",17);
		connect.addEmployee("Danyon Glenk",10);
		connect.addEmployee("Justin Lui",24);
		connect.addEmployee("Jan Misso",15.7);
		connect.addEmployee("Harry Nancarrow",19);
		connect.addEmployee("Tom Gates",18.54);
		connect.addEmployee("Emma Snelling",16.3);
		connect.addEmployee("Laura Rite",15.2);
		connect.addEmployee("Harry Potter",18);
		
		bMenu.addDayWorkingTime(1,1,true,true,false);
		bMenu.addDayWorkingTime(1,2,true,true,false);
		bMenu.addDayWorkingTime(1,3,true,false,false);
		bMenu.addDayWorkingTime(1,4,true,false,false);
		bMenu.addDayWorkingTime(1,5,true,false,false);
		bMenu.addDayWorkingTime(1,6,false,true,true);
		bMenu.addDayWorkingTime(1,7,true,true,false);
		
		bMenu.addDayWorkingTime(2,1,true,true,false);
		bMenu.addDayWorkingTime(2,2,true,true,false);
		bMenu.addDayWorkingTime(2,3,true,false,false);
		bMenu.addDayWorkingTime(2,4,true,false,false);
		bMenu.addDayWorkingTime(2,5,true,false,false);
		bMenu.addDayWorkingTime(2,6,false,true,true);
		bMenu.addDayWorkingTime(2,7,true,true,false);
		
		bMenu.addDayWorkingTime(3,1,true,true,false);
		bMenu.addDayWorkingTime(3,2,true,true,false);
		bMenu.addDayWorkingTime(3,3,true,false,false);
		bMenu.addDayWorkingTime(3,4,true,false,false);
		bMenu.addDayWorkingTime(3,5,true,false,false);
		bMenu.addDayWorkingTime(3,6,false,true,true);
		bMenu.addDayWorkingTime(3,7,true,true,false);
		
		bMenu.addDayWorkingTime(4,5,false,true,false);
		bMenu.addDayWorkingTime(4,6,false,true,true);
		
		bMenu.addDayWorkingTime(5,1,true,true,false);
		bMenu.addDayWorkingTime(5,7,true,true,true);
		
		bMenu.addDayWorkingTime(6,1,true,true,false);
		bMenu.addDayWorkingTime(6,2,true,true,false);
		bMenu.addDayWorkingTime(6,3,true,false,false);
		bMenu.addDayWorkingTime(6,4,true,false,false);
		bMenu.addDayWorkingTime(6,5,true,false,false);
		bMenu.addDayWorkingTime(6,6,false,true,true);
		bMenu.addDayWorkingTime(6,7,true,true,false);
		
		bMenu.addDayWorkingTime(7,1,true,true,false);
		bMenu.addDayWorkingTime(7,6,true,true,true);
		
		bMenu.addDayWorkingTime(8,5,false,true,false);
		bMenu.addDayWorkingTime(8,6,false,true,true);
		
		bMenu.addDayWorkingTime(9,4,true,true,false);
		bMenu.addDayWorkingTime(9,6,true,true,true);
		
		bMenu.addDayWorkingTime(10,3,false,true,false);
		bMenu.addDayWorkingTime(10,4,false,true,true);
		
		bMenu.addDayWorkingTime(11,1,true,true,false);
		bMenu.addDayWorkingTime(11,2,true,true,false);
		bMenu.addDayWorkingTime(11,3,true,true,false);
		bMenu.addDayWorkingTime(11,4,true,false,false);
		bMenu.addDayWorkingTime(11,5,true,false,false);
		bMenu.addDayWorkingTime(11,6,true,false,false);
		bMenu.addDayWorkingTime(11,7,false,true,true);
		
		
		bMenu.addDayWorkingTime(12,2,false,true,false);
		bMenu.addDayWorkingTime(12,4,false,true,true);
		
		bMenu.addDayWorkingTime(13,5,true,true,false);
		bMenu.addDayWorkingTime(13,7,true,true,true);
		
		bMenu.addDayWorkingTime(14,1,true,true,false);
		bMenu.addDayWorkingTime(14,2,true,true,false);
		bMenu.addDayWorkingTime(14,3,true,false,false);
		bMenu.addDayWorkingTime(14,4,true,false,false);
		bMenu.addDayWorkingTime(14,5,true,false,false);
		bMenu.addDayWorkingTime(14,6,false,true,true);
		bMenu.addDayWorkingTime(14,7,true,true,false);
		
		bMenu.addDayWorkingTime(15,1,true,true,false);
		bMenu.addDayWorkingTime(15,7,true,true,true);
		
		bMenu.addDayWorkingTime(16,4,false,true,false);
		bMenu.addDayWorkingTime(16,6,false,true,true);
		
		connect.addUserDetails(2, "William", "Porter", "will@mail.com", "0452368593", "01/01/2002", "Male");
		connect.addUserDetails(3, "Hannah", "Hawlking", "hannah@mail.com", "0452136859", "20/04/1995", "Famale");

		connect.addSerice(new Service("Trim",30,25));
		connect.addSerice(new Service("Wash",45,30));
		connect.addSerice(new Service("Cut and Style",90,60));
		
		connect.addBooking(2,1, "21/04/2017", "10:00", "10:40", 1,"active");
		connect.addBooking(2,2, "22/04/2017", "11:00", "11:59", 2,"canceled");
		connect.addBooking(2,1, "23/04/2017", "10:00", "10:40", 3,"canceled");
		connect.addBooking(2,2, "24/04/2017", "11:00", "11:59", 2,"canceled");
		connect.addBooking(2,3, "25/04/2017", "8:00", "8:40", 2, "active");
		connect.addBooking(2,2, "26/04/2017", "8:00", "8:40", 2, "canceled");
		connect.addBooking(2,3, "27/04/2017", "10:00", "10:40", 1,"active");
		connect.addBooking(2,2, "28/04/2017", "11:00", "11:59", 1,"active");
		connect.addBooking(2,3, "29/04/2017", "8:00", "8:40", 1, "canceled");
		
		connect.addBooking(2,1, "24/05/2017", "8:00", "8:40", 2, "active");
		connect.addBooking(3,1, "16/07/2017", "10:00", "10:40", 3,"active");
		connect.addBooking(3,2, "14/05/2017", "11:00", "11:59", 1,"active");
		connect.addBooking(3,1, "4/05/2017", "10:00", "10:40", 2,"active");
		connect.addBooking(3,2, "6/05/2017", "11:00", "11:59", 3,"active");
		


		
		//connect.cancelBooking(2);
    }
}
