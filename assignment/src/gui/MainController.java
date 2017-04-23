package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import program.Booking;
import program.Controller;

import org.apache.log4j.Logger;

public class MainController implements Initializable{
	
	private  static Logger log = Logger.getLogger(MainController.class);
	private Controller program = new Controller();
	public MainController() {}
	
	@FXML
	StackPane stkBusiness, stkCustomer;
	
	@FXML
	BorderPane boardPaneEmpAdd, boardPaneEmpOverview;
	
	@FXML
	Button btnRefreshBooking, btnSearchBookings, btnCancelBooking, btnLogout, btnRefreshEmployee, btnSearchEmployee;
	
	@FXML
	ListView<Booking> listviewBookings;
	
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
	 * @author [Programmer]
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
	 * @author [Programmer]
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
	 * @author [Programmer]
	 */
	@FXML
	public void cancelAddNewEmp()
	{
		boardPaneEmpAdd.setVisible(false);
		boardPaneEmpOverview.setVisible(true);
		//TODO
	}
	
	/**
	 * Enables and disables working times
	 * @author [Programmer]
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
	 * Resets the filtered employee list view
	 * @author [Programmer]
	 */
	@FXML
	public void refreshEmployeeView()
	{
		//TODO
	}
	
	/**
	 * Filters the list of employees
	 * @author [Programmer]
	 */
	@FXML
	public void searchEmployee()
	{
		//TODO
	}
	
	/**
	 * Views Employees Details
	 * @author [Programmer]
	 */
	@FXML
	public void viewEmpDetails()
	{
		//TODO
	}
	
	/**
	 * Deletes Employee
	 * @author [Programmer]
	 */
	@FXML
	public void deleteEmplyee()
	{
		//TODO
	}
}
