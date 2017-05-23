package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import program.Business;
import program.BusinessMenu;
import program.Controller;
import program.Database;
import program.DatabaseConnection;
import program.Login;
import program.Service;


public class LoginController implements Initializable {
	public static Logger log = Logger.getLogger(LoginController.class);
	private Login loginFuction = new Login();
	private Controller program = new Controller();
	private DatabaseConnection con = new DatabaseConnection();
	private int loginCount = 0;
	UserFactory userFactory = new UserFactory();
	
	@FXML
	ImageView imgView;
	
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
	
	private final Image defaultImage = new Image("images/ic_person_black_48dp_2x.png");
	
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
			ini();
		}
		popcmb();
		cmbBusiness.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Business>() {
			@Override
			public void changed(ObservableValue<? extends Business> observable, Business oldValue, Business newValue) {
				if (newValue != null) {
					program.business(newValue);
					if(con.getlogo(newValue.getBusinessId())!=null){
						program.business(newValue);
						FileInputStream fis;
						try {
								fis = new FileInputStream(con.getlogo(newValue.getBusinessId()));
								Image image = new Image(fis);
								imgView.setImage(image);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
					else{
						imgView.setImage(defaultImage);
					}
				}
			}
		});
	}
	
	private Business cmbVal(){
		return cmbBusiness.getSelectionModel().getSelectedItem();
	}
	
	private void popcmb(){
		ArrayList<Business> arr = new ArrayList<>(con.getAllBusiness());
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
			cmbBusiness.setButtonCell(new ListCell<Business>() {
				@Override
				protected void updateItem(Business t, boolean bln) {
					super.updateItem(t, bln);
					if (t != null) {
						setText(t.getBusinessName());
					} else {
						setText(null);
					}

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
		int businessID = -1;
		if(txtUserLogin.getText().equals("root"))
		{
			businessID = 0;
		}
		else
		{
			if (cmbBusiness.getSelectionModel().getSelectedItem() == null) {
	            program.messageBox("ERROR", "Error", "Please Select a business", "");
	            businessID = -1;
	        }
			else
			{
				businessID = cmbVal().getBusinessId();
				System.out.println("BUSINESS ID = "+businessID);
			}
		}
		if(businessID == -1)
		{
			return;
		}
			int loginCheck = loginFuction.logInProcess(txtUserLogin.getText(), txtPassLogin.getText(), businessID);
			if(loginCount < 10)
			{
				if(loginCheck == -2 || loginCheck == -4){
					lblError.setVisible(true);
					loginCount += 1;
					log.debug("LOGGER: login count: "+loginCount);
				}
				else if(loginCheck == 1 || loginCheck == 0 || loginCheck == 2)
				{
					log.debug("LOGGER: Login is equal to 1 or 0 or 2");
					lblError.setVisible(false);
					loginstage.hide();
					if (program.getUser().getAccountType() == 1) 
					{
						log.debug("LOGGER: Account Type = 1");
							IUser businessowner = userFactory.getUser("BusinessOwner");
							businessowner.getUserWindow();			
					} 
					else if(program.getUser().getAccountType() == 0) 
					{		
						log.debug("LOGGER: Account Type = 0");
							IUser customer = userFactory.getUser("Customer");
							customer.getUserWindow();
					} 
					else if(program.getUser().getAccountType() == 2)
					{
						log.debug("LOGGER: Account Type = 2");
						IUser admin = userFactory.getUser("SuperUser");
						admin.getUserWindow();
						popcmb();
					}
				}
				else{
					log.debug("LOGGER: Login returning a value different from -2, 1 or 0");
					lblError.setVisible(true);
					loginCount += 1;
				}
			}
			else
			{
				program.messageBox("WARN", "Login Error", "Maximum login attempts reached", "The maximum login attemps has been reached, the program will now terminate");
				Platform.exit();
				System.exit(0);
			}
		txtUserLogin.setText("");
		txtPassLogin.setText("");
		cmbBusiness.getSelectionModel().clearSelection();
		loginstage.show();
		
	}
	
	/**
	 * Loads Register window
	 * @author Joseph Garner
	 */
	@FXML
	public void registerAction(){
		if (cmbBusiness.getSelectionModel().getSelectedItem() == null) {
            program.messageBox("ERROR", "Error", "Please Select a business", "");
            return;
        }
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
	
	public void ini()
    {
		DatabaseConnection connect = new DatabaseConnection();
		BusinessMenu bMenu = new BusinessMenu();
			
		connect.createBusiness("YARRAVILLE CLINIC");
		Business business = connect.getBusiness("YARRAVILLE CLINIC");
		int businessID = business.getBusinessId();
		
		connect.addUser("heyhey", "S123456789", 1,businessID);	
		connect.addBusinessOwner(businessID, "Luke", "Mason", "0417125229", "80 Stanton Grove Lilydale", "05:00", "17:30", "05:00", "19:00");
		connect.addUser("William", "Apples22", 0,businessID); // customer attached to Business 2
		connect.addUser("Hannah", "Apples22", 0,businessID); // customer attached to Business 2
		connect.addUserDetails(3, "William", "Porter", "will@mail.com", "0452368593", "01/01/2002", "Male", businessID);
		connect.addUserDetails(4, "Hannah", "Hawlking", "hannah@mail.com", "0452136859", "20/04/1995", "Famale", businessID);
		connect.addUser("Test1234", "Apples22", 0,6);
		connect.addEmployee("Luke Charles",25,businessID);
		connect.addEmployee("David Smith",26.6,businessID);
		connect.addEmployee("Will Turner",15,businessID);
		connect.addEmployee("Rob Pointer",14,businessID);
		connect.addEmployee("Adam Mason",12,businessID);
		connect.addEmployee("David Chang",17,businessID);
		connect.addEmployee("Joseph Tun",17,businessID);
		connect.addEmployee("Casey Pointer",17,2);
		connect.addEmployee("Danyon Glenk",10,3);
		connect.addEmployee("Justin Lui",24,3);
		connect.addEmployee("Jan Misso",15.7,3);
		connect.addEmployee("Harry Nancarrow",19,3);
		connect.addEmployee("Tom Gates",18.54,7);
		connect.addEmployee("Emma Snelling",16.3,3);
		connect.addEmployee("Laura Rite",15.2,4);
		connect.addEmployee("Harry Potter",18,4);
		
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
		
		

		connect.addService(new Service("Trim",30,25,2));
		connect.addService(new Service("Wash",45,30,2));
		connect.addService(new Service("Cut and Style",90,60,2));
		connect.addService(new Service("Check Up",30,25,1));
		connect.addService(new Service("flu shot",45,30,1));
		connect.addService(new Service("Smoko break with an employee maaaaaaate",90,60,1));
		connect.addService(new Service("Service 1",30,25,3));
		connect.addService(new Service("Service 2",45,30,3));
		connect.addService(new Service("Service 3",90,60,3));
		connect.addService(new Service("Service 1",30,25,4));
		connect.addService(new Service("Service 2",45,30,4));
		connect.addService(new Service("Service 3",90,60,4));
		
		
		connect.addBooking(3,2, "21/04/2017", "10:00", "10:40", 1,"active",1);
		connect.addBooking(3,2, "22/04/2017", "11:00", "11:59", 2,"canceled",1);
		connect.addBooking(3,1, "23/04/2017", "10:00", "10:40", 3,"canceled",1);
		connect.addBooking(3,2, "24/04/2017", "11:00", "11:59", 2,"canceled",1);
		connect.addBooking(4,3, "25/04/2017", "8:00", "8:40", 2, "active",1);
		connect.addBooking(4,2, "26/04/2017", "8:00", "8:40", 2, "canceled",1);
	
		//connect.cancelBooking(2);
    }
	

}
