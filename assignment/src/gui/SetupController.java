package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import program.BusinessMenu;
import program.Database;
import program.DatabaseConnection;
import program.Service;

public class SetupController implements Initializable {
	
	/*
	 * Oder of panes stkpWelcome > stkpDetails > stkpTimeSlot > (Setup Finishes and database is created) > stkpSelectColor
	 */
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
			stkpTimeSlot.setVisible(true);
			stkpDetails.setVisible(false);
		}
		if(stkpTimeSlot.isVisible())
		{
			stkpSelectColor.setVisible(true);
			stkpTimeSlot.setVisible(false);
			createBO();
		}
	}
	/**
	 * Moves the user to the next view
	 * @author Joseph Garner
	 */
	@FXML
	public void back(){
		stkpTimeSlot.setVisible(false);
		stkpDetails.setVisible(true);
	}
	
	/**
	 * 
	 * @author [Programmer]
	 */
	@FXML
	public void createBO(){
		//TODO
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
