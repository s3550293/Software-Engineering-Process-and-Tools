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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import program.Business;
import program.BusinessMenu;
import program.BusinessOwner;
import program.Controller;
import program.DatabaseConnection;
import program.Register;

public class SetupController implements Initializable, ISetup {
	public final static Logger log = Logger.getLogger(SetupController.class);
	private final Controller program = new Controller();
	Business business = program.business();
	private DatabaseConnection connection = new DatabaseConnection();
	private static BusinessOwner businessOwner = new BusinessOwner();
	public final Register regpro = new Register();
	public final BusinessMenu bMenu = new BusinessMenu();
	private File fa = null;
	/*
	 * Oder of panes stkpWelcome > stkpDetails > stkpTimeSlot > (Setup Finishes and database is created) > stkpSelectColor
	 */
	@FXML
	ImageView imgView;
	
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
	public int assignOpenClosingTimesToGlobal(Date weekdayStart ,Date weekdayEnd, Date weekendStart ,Date weekendEnd)
	{
		//Change Dates to Strings
		String MFOpen = program.timeToStr(weekdayStart);
		String MFClose = program.timeToStr(weekdayEnd);
		String SSOpen = program.timeToStr(weekendStart);
		String SSClose = program.timeToStr(weekendEnd);
		//assign times for weekends and weekdays
		int a = assignOpenClosingTimesToWeekDays(MFOpen, MFClose);
		if(a == 0)
		{
			return 0;
		}
		int b = assignOpenClosingTimesToWeekEnds(SSOpen, SSClose);
		if(b == 0)
		{
			return 0;
		}
		return 1;
	}
	
	public int assignOpenClosingTimesToWeekDays(String MFOpen,String MFClose)
	{
		String[] times = program.splitTimeIntoThreeBlocks(MFOpen, MFClose);
		if(times[0].equals(""))
		{
			return 0;
		}
		bMenu.MFearly = times[0];
		bMenu.MFearlyMidDay = times[1];
		bMenu.MFlateMidDay = times[2];
		bMenu.MFlate = times[3];
		return 1;
	}
	public int assignOpenClosingTimesToWeekEnds(String SSOpen,String SSClose)
	{
		String[] times = program.splitTimeIntoThreeBlocks(SSOpen, SSClose);
		if(times[0].equals(""))
		{
			return 0;
		}
		bMenu.SSearly = times[0];
		bMenu.SSearlyMidDay = times[1];
		bMenu.SSlateMidDay = times[2];
		bMenu.SSlate = times[3];
		return 1;
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
			businessOwner.setBusinessID(business.getBusinessId());
			log.info("BusinessID = "+business.getBusinessId()+"\n");
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
			int id = businessOwner.getBusinessID();
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

			//Add to the database the new information
			int check = assignOpenClosingTimesToGlobal(businessOwner.getWeekdayStart(),businessOwner.getWeekdayEnd(),businessOwner.getWeekendStart(),businessOwner.getWeekendEnd());
			if(check == 0)
			{
				return;
			}
			connect.addBusinessOwner(id, fName, lName, phone, address, wds, wde, wes, wee);
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
		if(fa!=null){
			fa.renameTo(new File(System.getProperty("user.home")+"/resourcing/"+fa.getName()));
			connection.addImage(System.getProperty("user.home")+"/resourcing/"+fa.getName(), program.getUser().getBusinessID());
		}	
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
	
	@FXML
	public void addImage(){
		Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        try{
        	fa = fileChooser.showOpenDialog(stage);
        	Image image = new Image(fa.getPath());
            imgView.setImage(image);
        }
        catch(Exception e){
        	log.debug(e.getMessage());
        }
	}
	
	@FXML
	public void blue(){
		int id = program.getUser().getBusinessID();
		DatabaseConnection con = new DatabaseConnection();
		con.updateBO(id, 1);
		root.setStyle(program.setColor());
	}
	
	@FXML
	public void purp(){
		int id = program.getUser().getBusinessID();
		DatabaseConnection con = new DatabaseConnection();
		con.updateBO(id, 2);
		root.setStyle(program.setColor());
	}
	
	@FXML
	public void green(){
		int id = program.getUser().getBusinessID();
		DatabaseConnection con = new DatabaseConnection();
		con.updateBO(id, 3);
		root.setStyle(program.setColor());
	}
	
	@FXML
	public void ong(){
		int id = program.getUser().getBusinessID();
		DatabaseConnection con = new DatabaseConnection();
		con.updateBO(id, 4);
		root.setStyle(program.setColor());
	}
	
	
	
}
