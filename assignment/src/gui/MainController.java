package gui;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import program.Booking;
import program.Employee;
import program.EmployeeWorkingTime;
import program.Controller;
import program.DatabaseConnection;
import program.BusinessMenu;

import org.apache.log4j.Logger;

public class MainController implements Initializable{
	
	private  static Logger log = Logger.getLogger(MainController.class);
	private Controller program = new Controller();
	private DatabaseConnection connection = new DatabaseConnection();
	private Employee employee = null;
	public MainController() {}
	
	@FXML
	StackPane stkBusiness, stkCustomer;
	
	@FXML
	BorderPane boardPaneEmpAdd, boardPaneEmpOverview;
	
	@FXML
	Button btnRefreshBooking, btnSearchBookings, btnCancelBooking, btnLogout, btnRefreshEmployee, btnSearchEmployee, btnConfirm, btnViewEmpDetails;
	
	@FXML
	Label lblEmployeeID, lblEmployeeName, lblEmployeePayrate;
	
	@FXML
	ListView<Booking> listviewBookings;
	
	@FXML
	ListView<Employee> listviewEmployees;
	
	@FXML
	TextField txtSearchBookings, txtaddEmpFirstName, txtAddEmpLastName, txtAddEmpPayRate, txtSearchEmployee;
	
	@FXML
	CheckBox chkbxAddWorkingTimes;
	
	@FXML
	GridPane gridpWorkingTimes;
	
	@FXML
	ToggleButton btnSunMorning, btnSunAfternoon, btnSunEvening, btnMonMorning, btnMonAfternoon, btnMonEvening, btnTueMorning, btnTueAfternoon, btnTueEvening;
	@FXML
	ToggleButton btnWedMorning, btnWedAfternoon, btnWedEvening, btnThurMorning, btnThurAfternoon, btnThurEvening, btnFriMorning, btnFriAfternoon, btnFriEvening;
	@FXML
	ToggleButton btnSatMorning, btnSatAfternoon, btnSatEvening;
	
	/**
	 * initializes the stage
	 * @author Joseph
	 */
	public void initialize(URL url, ResourceBundle rb)
	{
		stkBusiness.setVisible(true);
		stkCustomer.setVisible(false);
		refreshBookingView();
		boolean var = login();
		if(var == true){
				if(program.getUser().getAccountType() == 1){
					stkBusiness.setVisible(true);
					stkCustomer.setVisible(false);
				}
				else{
					stkBusiness.setVisible(false);
					stkCustomer.setVisible(true);
				}
		}
		else{
			Platform.exit();
			System.exit(0);
		}
		loadListViewEmp("");
		listviewEmployees.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
            @Override
            public void changed(ObservableValue<? extends Employee> observable, Employee oldValue, Employee newValue) {
                if (newValue != null) {
                	employee = newValue;
                	lblEmployeeID.setText(Integer.toString(employee.getId()));
                	lblEmployeeName.setText(employee.getName());
                	lblEmployeePayrate.setText(Double.toString(employee.getPayRate()));
                }
            }
        });
		loadListViewBook();
		

	}
	private void loadListViewEmp(String name){
            ArrayList<Employee> empArray = new ArrayList<>(connection.getEmployees(name));
            ObservableList<Employee> empList = FXCollections.observableList(empArray);
            log.debug("LOGGER: List length:"+empArray.size());
            if(empList != null)
            {
            	listviewEmployees.setItems(empList);
	            listviewEmployees.setCellFactory(new Callback<ListView<Employee>, ListCell<Employee>>() {
	                @Override
	                public ListCell<Employee> call(ListView<Employee> p) {
	
	                    ListCell<Employee> cell = new ListCell<Employee>() {
	                        @Override
	                        protected void updateItem(Employee t, boolean bln) {
	                            super.updateItem(t, bln);
	                            if (t != null) {
	                                setText(t.getId() + " " + t.getName() + " " + t.getPayRate());
	                            }
	                        }
	                    };
	                    return cell;
	                }
	            });
            }
            else{
            	log.warn("Unable to load Employees");
            }
	}
	private void loadListViewBook(){
        ArrayList<Booking> bookArray = new ArrayList<>(connection.getAllBooking());
        ObservableList<Booking> bookList = FXCollections.observableList(bookArray);
        log.debug("LOGGER: List length:"+bookArray.size());
        if(bookList != null)
        {
        	listviewBookings.setItems(bookList);
        	listviewBookings.setCellFactory(new Callback<ListView<Booking>, ListCell<Booking>>() {
                @Override
                public ListCell<Booking> call(ListView<Booking> p) {

                    ListCell<Booking> cell = new ListCell<Booking>() {
                        @Override
                        protected void updateItem(Booking t, boolean bln) {
                            super.updateItem(t, bln);
                            if (t != null) {
                                setText(t.getBookingID() + " " + t.getCustomerId() + " " + program.convertDateToString(t.getDate()) + " "+ program.convertTimeToString(t.getStartTime())+" "+ t.getStatus());
                            }
                        }
                    };
                    return cell;
                }
            });
        }
        else{
        	log.warn("Unable to load Employees");
        }
}
	
	/**************
	 * 	LOGIN
	 **************/
	
	/**
	 * Returns User to login
	 * @author [Programmer]
	 */
	@FXML
	public boolean login()
	{
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
            if(program.getUser() != null){
            	return true;
            }
        }
		catch(IOException ioe) {
            log.warn(ioe.getMessage());
        }
        log.debug("false");
        return false;
	}
	
	/**************
	 * 	BOOKINGS
	 **************/
	
	/**
	 * Program Allows the user to search and displays bookings
	 * @author [Programmer]
	 */
	@FXML
	public void searchBookings()
	{
		//TODO
	}
	
	/**
	 * Cancels booking (does not remove the booking from view)
	 * @author [Programmer]
	 */
	@FXML
	public void cancelBooking()
	{
		//TODO
	}
	
	/**
	 * Displays and refreshes booking view to all
	 * @author [Programmer]
	 */
	@FXML
	public void refreshBookingView()
	{
		//TODO
	}
	
	/**
	 * Shows add employee
	 * note tabs should be disabled
	 * @author [Luke Mason]
	 */
	
	/**************
	 * 	EMPLOYEE
	 **************/
	@FXML
	public void showAddNewEmp()
	{
		boardPaneEmpAdd.setVisible(true);
		boardPaneEmpOverview.setVisible(false);
		//TODO
	}
	
	/**
	 * Returns User to manage employees
	 * @author [Luke Mason]
	 */
	@FXML
	public void cancelAddNewEmp()
	{
		boardPaneEmpAdd.setVisible(false);
		boardPaneEmpOverview.setVisible(true);
		//Resetting the add Employee board to default values
		txtaddEmpFirstName.setText("");
		txtAddEmpLastName.setText(""); 
		txtAddEmpPayRate.setText(""); 
		chkbxAddWorkingTimes.setSelected(false);
		btnSunMorning.setSelected(false);
		btnSunAfternoon.setSelected(false);
		btnSunEvening.setSelected(false);
		btnMonMorning.setSelected(false);
		btnMonAfternoon.setSelected(false);
		btnMonEvening.setSelected(false);
		btnTueMorning.setSelected(false);
		btnTueAfternoon.setSelected(false);
		btnTueEvening.setSelected(false);
		btnWedMorning.setSelected(false);
		btnWedAfternoon.setSelected(false);
		btnWedEvening.setSelected(false);
		btnThurMorning.setSelected(false);
		btnThurAfternoon.setSelected(false);
		btnThurEvening.setSelected(false);
		btnFriMorning.setSelected(false);
		btnFriAfternoon.setSelected(false);
		btnFriEvening.setSelected(false);
		btnSatMorning.setSelected(false);
		btnSatAfternoon.setSelected(false);
		btnSatEvening.setSelected(false);
	}
	
	/**
	 * Enables and disables working times
	 * @author [Joseph Garner]
	 */
	@FXML
	public void allowWorkingTimes()
	{
		if(chkbxAddWorkingTimes.isSelected())
		{
			gridpWorkingTimes.setDisable(false);
		}
		else
		{
			gridpWorkingTimes.setDisable(true);
		}
		//TODO
	}
	
	/**
	 * Creates an Employee
	 * @author [Luke Mason]
	 */
	@FXML 
	public void createEmp()
	{
		BusinessMenu bMenu = new BusinessMenu();
		boolean firstName = bMenu.checkEmployeeFirstOrLastName(txtaddEmpFirstName.getText());
		if(!firstName)
		{
			program.messageBox("ERROR", "First Name Invalid", "First Name Invalid","First Name entered is not a valid first name\nReason: First name contains invalid characters");
			return;
		}
		boolean lastName = bMenu.checkEmployeeFirstOrLastName(txtAddEmpLastName.getText());
		if(!lastName)
		{
			program.messageBox("ERROR", "Last Name Invalid", "Last Name Invalid","Last Name entered is not a valid last name\nReason: Last name contains invalid characters");
			return;
		}
		double payRate = bMenu.strPayRateToDouble(txtAddEmpPayRate.getText());
		boolean PayRate = bMenu.checkEmployeePayRate(payRate);
		if(!PayRate)
		{
			program.messageBox("ERROR", "Pay Rate Invalid", "Pay Rate Invalid","Pay rate entered is not a valid pay rate\nReason: Pay Rate is not 0 - 1000");
			return;
		}
		if(PayRate && firstName && lastName)
		{
			if(chkbxAddWorkingTimes.isSelected())
			{
				bMenu.option2AddEmployeeAndWorkingTimes(txtaddEmpFirstName.getText()
						,txtAddEmpLastName.getText(), payRate, btnSunMorning.isSelected(), btnSunAfternoon.isSelected()
						, btnSunEvening.isSelected(), btnMonMorning.isSelected(), btnMonAfternoon.isSelected(), btnMonEvening.isSelected()
						, btnTueMorning.isSelected(), btnTueAfternoon.isSelected(), btnTueEvening.isSelected(), btnWedMorning.isSelected()
						, btnWedAfternoon.isSelected(), btnWedEvening.isSelected(), btnThurMorning.isSelected(), btnThurAfternoon.isSelected()
						, btnThurEvening.isSelected(), btnFriMorning.isSelected(), btnFriAfternoon.isSelected(), btnFriEvening.isSelected()
						, btnSatMorning.isSelected(), btnSatAfternoon.isSelected(), btnSatEvening.isSelected());
				program.messageBox("SUCCESS", "New Employee Added", "New Employee Added",txtaddEmpFirstName.getText()+" "+txtAddEmpLastName.getText()+" with $"+payRate+"/h was Added!");

			}
			else
			{
				bMenu.option1AddEmployee(txtaddEmpFirstName.getText(),txtAddEmpLastName.getText(), payRate);	
				program.messageBox("SUCCESS", "SUCCESS", "New Employee Added",txtaddEmpFirstName.getText()+" "+txtAddEmpLastName.getText()+" with $"+payRate+"/h was Added!");
			}
			cancelAddNewEmp();
		}
		else
		{
			program.messageBox("ERROR", "Employee Details Invalid", "Employee Details Invalid","Employee Details entered are not a valid\nReason: First/Last/PayRate contain invalid characters or  is not between 0-1000");
		}
	}
	
	/**
	 * Resets the filtered employee list view
	 * @author [Luke Mason]
	 */
	@FXML
	public void refreshEmployeeView()
	{
		txtSearchEmployee.setText("");
		loadListViewEmp("");
	}
	
	/**
	 * Filters the list of employees
	 * @author [Luke Mason]
	 */
	@FXML
	public void searchEmployee()
	{
		String name = txtSearchEmployee.getText();
		loadListViewEmp(name);
	}
	
	/**
	 * Views Employees Details
	 * @author [Luke Mason]
	 */
	@FXML
	public void viewEmpDetails()
	{
		BusinessMenu bMenu = new BusinessMenu();
		program.messageBox("SUCCESS", "Not Implemented","","");

		int employeeID = -1;
		String eFirstName = "";
		String eLastName = "";
		String ePayRate = "";
		boolean v1 = false;
		boolean v2 = false;
		boolean v3 = false;
		boolean v4 = false;
		boolean v5 = false;
		boolean v6 = false;
		boolean v7 = false;
		boolean v8 = false;
		boolean v9 = false;
		boolean v10 = false;
		boolean v11 = false;
		boolean v12 = false;
		boolean v13 = false;
		boolean v14 = false;	
		boolean v15 = false;
		boolean v16 = false;
		boolean v17 = false;
		boolean v18 = false;
		boolean v19 = false;
		boolean v20 = false;
		boolean v21 = false;
		boolean v22 = false;
		
		txtaddEmpFirstName.setText(eFirstName);
		txtAddEmpLastName.setText(eLastName); 
		txtAddEmpPayRate.setText(ePayRate); 
		
		chkbxAddWorkingTimes.setSelected(v1);
		
		btnSunMorning.setSelected(v2);
		btnSunAfternoon.setSelected(v3);
		btnSunEvening.setSelected(v4);
		btnMonMorning.setSelected(v5);
		btnMonAfternoon.setSelected(v6);
		btnMonEvening.setSelected(v7);
		btnTueMorning.setSelected(v8);
		btnTueAfternoon.setSelected(v9);
		btnTueEvening.setSelected(v10);
		btnWedMorning.setSelected(v11);
		btnWedAfternoon.setSelected(v12);
		btnWedEvening.setSelected(v13);
		btnThurMorning.setSelected(v14);
		btnThurAfternoon.setSelected(v15);
		btnThurEvening.setSelected(v16);
		btnFriMorning.setSelected(v17);
		btnFriAfternoon.setSelected(v18);
		btnFriEvening.setSelected(v19);
		btnSatMorning.setSelected(v20);
		btnSatAfternoon.setSelected(v21);
		btnSatEvening.setSelected(v22);
		
		//lblEmployeeTitle.setText("Change Employee Details");
		//Get chosen employee ID
		ArrayList<EmployeeWorkingTime> workTimes = new ArrayList<EmployeeWorkingTime>();
		workTimes = bMenu.getEmployeeWorkTimes(employeeID);
		
	}
	
	/**
	 * Deletes Employee
	 * @author [Luke Mason]
	 */
	@FXML
	public void deleteEmplyee()
	{
		//TODO
	}
}
