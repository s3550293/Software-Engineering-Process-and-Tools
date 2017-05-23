package gui;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gui.IInterface.IUser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import program.Booking;
import program.BusinessOwner;
import program.Controller;
import program.DatabaseConnection;
import program.Employee;
import program.Service;

public class CustomerController  implements Initializable, IUser{

	private static Logger log = Logger.getLogger(CustomerController.class);
	private static Controller program = new Controller();
	private DatabaseConnection connection = new DatabaseConnection();
	private Employee employee = null;
	private Booking booking = null;
	int globalEmployeeOption = 0;
	private static Booking newBook = new Booking();
	SetupController setupC = new SetupController();
	public CustomerController(){log.setLevel(Level.ALL);}
	
	@FXML
	AnchorPane root;
	
	@FXML
	ListView<Booking> listBookings;
	
	@FXML
	ListView<String> listviewTimeSlot;
	
	@FXML
	Button btnConfirmBooking, btnNext, btnLogoutCustomer, btnBack, btnCancelAppoitment,
			btnRToOwnMen;

	@FXML
	Label lblCustomerName, lblDayDate, lblServiceName, lblServiceDur, lblServicePrice, lblCustBookingDate,
			lblBookingService, lblBookingPrice, lblBookingDur, lblBookingTime, lblBookingEmp;

	@FXML
	ComboBox<Date> cmbDayBooking;

	@FXML
	ComboBox<Employee> cmbPreferEmp;

	@FXML
	ListView<Service> listviewBookingServices;

	@FXML
	StackPane stkpnUserMenu, stkpnBookingMenu, stkpnDateService, stkpnTime, stkpnBookingConfirm;

	@FXML
	Label lblBookConDate, lblBookConSer, lblBookConPri, lblBookConDur, lblBookConSTim, lblBookConEmp;

	@FXML
	ToggleButton togbtnMorn, togbtnAft, togbtnEven;

	@FXML
	ToggleGroup timeODayGroup = new ToggleGroup();

	@FXML
	ToggleGroup timeGroup = new ToggleGroup();

	@FXML
	Label lblAvail1, lblAvail2, lblAvail3, lblAvail4, lblAvail5, lblAvail6, lblAvail7, lblAvail8;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if(program.bmb == true){
			btnRToOwnMen.setVisible(true);
		}
		if(connection.getOneBusiness(program.getUser().getBusinessID()).color() >= 1){
			setCI();
		}
		BusinessOwner BO = connection.getOneBusiness(program.business().getBusinessId());
		if(BO == null)
		{
			return;
		}
		if(BO != null)
		{
			String wds = program.timeToStr(BO.getWeekdayStart());
			String wde = program.timeToStr(BO.getWeekdayEnd());
			String wes = program.timeToStr(BO.getWeekendStart());
			String wee = program.timeToStr(BO.getWeekendEnd());
			setupC.assignOpenClosingTimesToGlobal(wds, wde, wes, wee);
		}
		ToggleGroup group = new ToggleGroup();
		togbtnMorn.setToggleGroup(group);
		togbtnAft.setToggleGroup(group);
		togbtnEven.setToggleGroup(group);
		popListBook();
		lblCustomerName.setText(connection.getCustomer(program.getUser().getID()).getFullName());
		cmbDayBooking.valueProperty().addListener(new ChangeListener<Date>() {
			@Override
			public void changed(ObservableValue ov, Date t, Date t1) {
				// TODO
			}
		});
		listviewBookingServices.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<Service>() {
					@Override
					public void changed(ObservableValue<? extends Service> observable, Service oldValue,
							Service newValue) {
						if (newValue != null) {
							lblServiceName.setText(newValue.getName());
							lblServiceDur.setText(Integer.toString(newValue.getLengthMin()));
							lblServicePrice.setText("$" + Double.toString(newValue.getPrice()));
						}
					}
				});
		cmbDayBooking.valueProperty().addListener(new ChangeListener<Date>() {
			@Override
			public void changed(ObservableValue ov, Date t, Date t1) {
				if (t1 != null) {
					lblDayDate.setText(program.dateToStr(cmbDayBooking.getSelectionModel().getSelectedItem()));
				}
			}
		});
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				popTime();
			}
		});
		//togbtnMorn.setSelected(true);
		loadDaySelect();
		loadallServices();
		//listTogTDini(program.business().getBusinessId());
		//listTogTSini(program.business().getBusinessId());
	}
	
	@Override
	public void setCI() {
		root.setStyle(program.setColor());
		
	}
	
	private void popListBook(){
		ArrayList<Booking> arr = new ArrayList<>(connection.getBooking(program.getUser().getID()));
		ObservableList<Booking> list = FXCollections.observableList(arr);
		log.debug("LOGGER: List length:" + list.size());
		if (list != null) {
			listBookings.setItems(list);
			listBookings.setCellFactory(new Callback<ListView<Booking>, ListCell<Booking>>() {
				@Override
				public ListCell<Booking> call(ListView<Booking> p) {

					ListCell<Booking> cell = new ListCell<Booking>() {
						@Override
						protected void updateItem(Booking t, boolean bln) {
							super.updateItem(t, bln);
							if (t != null) {
								log.debug("LOGGER: added:" + t.getBookingID());
								setText("ID: "+t.getBookingID()+"\n"
										+ connection.getEmployee(t.getEmployeeID()).getName()+", "
												+ ""+program.timeToStr(t.getStartTime())+", "+program.dateToStr(t.getDate())+", "+connection.getService(t.getService()).getName());
							}
						}
					};
					return cell;
				}
			});
		}
	}
	
	@FXML
	public void loadDaySelect() {
		SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
		Calendar date = Calendar.getInstance();
		ArrayList<Date> dateArray = new ArrayList<>();
			for (int i = 0; i < 7; i++) {
				dateArray.add(date.getTime());
				date.add(Calendar.DAY_OF_MONTH, 1);
		}
		ObservableList<Date> dateList = FXCollections.observableList(dateArray);
		cmbDayBooking.setItems(dateList);
		cmbDayBooking.setCellFactory(new Callback<ListView<Date>, ListCell<Date>>() {

			@Override
			public ListCell<Date> call(ListView<Date> p) {

				final ListCell<Date> cell = new ListCell<Date>() {

					@Override
					protected void updateItem(Date t, boolean bln) {
						super.updateItem(t, bln);

						if (t != null) {
							setText(dayFormat.format(t.getTime()));
						} else {
							setText(null);
						}
					}

				};

				return cell;
			}
		});

		cmbDayBooking.setButtonCell(new ListCell<Date>() {
			@Override
			protected void updateItem(Date t, boolean bln) {
				super.updateItem(t, bln);
				if (t != null) {
					setText(dayFormat.format(t.getTime()));
				} else {
					setText(null);
				}

			}
		});
	}
	
	
	/**
	 * @author Joseph Garner
	 * @editor David
	 * Display Booking Time for the three shifts(morning, afternoon, evening) in 30 minutes interval
	 * @param calendar
	 */
	/*
	public void diplayBookingTime(Calendar date){
		for (int i = 0; i < 8; i++) {
			if (i == 0) {
				togbtnTimeSlot1.setText(
						date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + "0");
			}
			if (i == 1) {
				togbtnTimeSlot2
						.setText(date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE));
			}
			if (i == 2) {
				togbtnTimeSlot3.setText(
						date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + "0");
			}
			if (i == 3) {
				togbtnTimeSlot4
						.setText(date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE));
			}
			if (i == 4) {
				togbtnTimeSlot5.setText(
						date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + "0");
			}
			if (i == 5) {
				togbtnTimeSlot6
						.setText(date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE));
			}
			if (i == 6) {
				togbtnTimeSlot7.setText(
						date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + "0");
			}
			if (i == 7) {
				togbtnTimeSlot8
						.setText(date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE));
			}
			date.add(Calendar.MINUTE, 30);
		}
	}
	*/
	
	/**
	 * loads all Services into list view
	 * 
	 * @author Joseph Garner
	 */
	@FXML
	public void loadallServices() {
		ArrayList<Service> serviceArray = new ArrayList<>(connection.getAllServices(program.business().getBusinessId()));
		ObservableList<Service> serviceList = FXCollections.observableList(serviceArray);
		log.debug("LOGGER: List length:" + serviceArray.size());
		if (serviceList != null) {
			listviewBookingServices.setItems(serviceList);
			listviewBookingServices.setCellFactory(new Callback<ListView<Service>, ListCell<Service>>() {
				@Override
				public ListCell<Service> call(ListView<Service> p) {

					ListCell<Service> cell = new ListCell<Service>() {
						@Override
						protected void updateItem(Service t, boolean bln) {
							super.updateItem(t, bln);
							if (t != null) {
								log.debug("LOGGER: added:" + t.getName());
								setText(t.getName());
							}
						}
					};
					return cell;
				}
			});
		}
	}
	
	/**************
	 * Customer
	 **************/
	/**
	 * Enters to booking phase
	 * 
	 * @author Luke
	 */
	private void loadpreferedEmp(String startTime, String endTime, int businessID) {
		List<Employee> emArray;
		log.debug("LOGGER: date selected - " + lblCustBookingDate.getText());
		emArray = program.getAvailableEmployeesForSpecifiedTime(lblCustBookingDate.getText(), startTime, endTime, businessID);
		log.debug("LOGGER: Array Length - " + emArray.size());
		ObservableList<Employee> emList = FXCollections.observableList(emArray);
		if (emList != null) {
			cmbPreferEmp.setItems(emList);
			cmbPreferEmp.setCellFactory(new Callback<ListView<Employee>, ListCell<Employee>>() {

				@Override
				public ListCell<Employee> call(ListView<Employee> p) {

					final ListCell<Employee> cell = new ListCell<Employee>() {

						@Override
						protected void updateItem(Employee t, boolean bln) {
							super.updateItem(t, bln);

							if (t != null) {
								setText(t.getName());
							} else {
								setText(null);
							}
						}

					};

					return cell;
				}
			});
			cmbPreferEmp.setButtonCell(new ListCell<Employee>() {
				@Override
				protected void updateItem(Employee t, boolean bln) {
					super.updateItem(t, bln);
					if (t != null) {
						setText(t.getName());
					} else {
						setText(null);
					}

				}
			});
		}
	}

	/**
	 * Enters to booking phase
	 * 
	 * @author [Programmer]
	 */
	@FXML
	public void startBooking() {
		// TODO
		if (stkpnUserMenu.isVisible()) {
			stkpnUserMenu.setVisible(false);
			stkpnBookingMenu.setVisible(true);
		}
	}
	
	private void popTime(){
		ArrayList<String> arr = new ArrayList<>(checkBookingTime(program.business().getBusinessId()));
		ObservableList<String> list = FXCollections.observableList(arr);
		log.debug("LOGGER: List length:" + arr.size());
		if (list != null) {
			listviewTimeSlot.setItems(list);
			listviewTimeSlot.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
				@Override
				public ListCell<String> call(ListView<String> p) {

					ListCell<String> cell = new ListCell<String>() {
						@Override
						protected void updateItem(String t, boolean bln) {
							super.updateItem(t, bln);
							if (t != null) {
								log.debug("LOGGER: added:" +t);
								setText(t);
							}
						}
					};
					return cell;
				}
			});
		}
	}
	
	private ArrayList<String> getTimes(int slot){
		ArrayList<String> arr = new ArrayList<>();
		Calendar open = Calendar.getInstance();
		Calendar close = Calendar.getInstance();
		if(open.get(Calendar.DAY_OF_WEEK) == 1 || open.get(Calendar.DAY_OF_WEEK) == 7){
			open.setTime(connection.getOneBusiness(program.business().getBusinessId()).getWeekendStart());
			close.setTime(connection.getOneBusiness(program.business().getBusinessId()).getWeekendEnd());
		}
		else{
			open.setTime(connection.getOneBusiness(program.business().getBusinessId()).getWeekdayStart());
			close.setTime(connection.getOneBusiness(program.business().getBusinessId()).getWeekdayEnd());
		}
		Date dstart =  open.getTime();
		Date dclose =  close.getTime();
		String[] times = program.splitTimeIntoThreeBlocks(program.timeToStr(dstart), program.timeToStr(dclose));
		log.debug("Start: "+times[0]+" "+times[1]+" "+times[2]+" "+times[3]+" ");
		if(slot == 1){
			open.setTime(program.strToTime(times[0]));
			close.setTime(program.strToTime(times[1]));
			close.add(Calendar.MINUTE, -10);
		}
		else if(slot == 2){
			open.setTime(program.strToTime(times[1]));
			open.add(Calendar.MINUTE, -10);
			close.setTime(program.strToTime(times[2]));
			close.add(Calendar.MINUTE, -10);
		}
		else if(slot == 3){
			open.setTime(program.strToTime(times[2]));
			open.add(Calendar.MINUTE, -20);
			close.setTime(program.strToTime(times[3]));
		}
		String str;
		while(open.compareTo(close) != 1){
			if(open.get(Calendar.MINUTE) !=0){
				str = open.get(Calendar.HOUR_OF_DAY) + ":" + open.get(Calendar.MINUTE);
			}
			else{
				str = open.get(Calendar.HOUR_OF_DAY) + ":" + open.get(Calendar.MINUTE) + "0";
			}
			arr.add(str);
			log.debug("BOOKING TIMES: "+str);
			open.add(Calendar.MINUTE, 30);
		}
		return arr;
	}

	/**
	 * Check and display booking time during booking
	 * Disable toggle button, after booking
	 */
	private ArrayList<String> checkBookingTime(int businessID) {
		ArrayList<String> arr = new ArrayList<>();
						log.debug("LOGGER: Dates Match");
						if (togbtnMorn.isSelected()) {
							arr = getTimes(1);
							arr = checkStartTimeTogglesOnBooking(arr);
							arr = checkoverLap(true,arr);
						} else if (togbtnAft.isSelected()) {
							arr = getTimes(2);
							arr = checkStartTimeTogglesOnBooking(arr);
							arr = checkoverLap(true,arr);
							
						} else if (togbtnEven.isSelected()) {
							arr = getTimes(3);
							arr = checkStartTimeTogglesOnBooking(arr);
							arr = checkoverLap(true, arr);
						} else {
						}
		return arr;
	}
	
	/**
	 * Check EndTime of previous booking and disable buttons appropriately
	 * @param mornshift
	 * @param enD
	 * @param timeslot1
	 * @param timeslot2B
	 * @param timeslot2
	 * @param timeslot3B
	 * @param timeslot3
	 * @param timeslot4B
	 * @param timeslot4
	 * @param timeslot5B
	 * @param timeslot5
	 * @param timeslot6B
	 * @param timeslot6
	 * @param timeslot7B
	 * @param timeslot7
	 * @param timeslot8B
	 * @param timeslot8
	 */
	public ArrayList<String> checkoverLap(Boolean mornshift, ArrayList<String> arr){
		
		/*
		 *  In morning shift, the first timeslot cannot be disable initially.
		 *  but in other shifts, the first timeslot may be disable as the booking session is carried over from the previous shift.
		 */
		Date enD = null;
		for(int i=0;i<arr.size();i++){
			
				List<Booking> bookings = new ArrayList<Booking>(connection.getAllBooking(program.business().getBusinessId()));
				for (Booking b : bookings) {
					if(b!=null)
					{
						if (b.getStatus() != "canceled"){
							enD = b.getEndTime();
							if (program.dateToStr(newBook.getDate()).equals(program.dateToStr(b.getDate()))) {
								if (enD.after(program.strToTime(arr.get(i))) && enD.before(program.strToTime(arr.get(i+1)))) {
									if(arr.get(i).contains("\n") || arr.get(i).contains("\\n")){
										arr.set(i, arr.get(i)+"\nUnavailable");
									}
								}
							}
						}
					}
				}
				
			}
		return arr;
		
	}
	
	/**
	 * Check a specified toggle button for StartTime and disable them after booking
	 * @param <Date> Start Date
	 */
	public ArrayList<String> checkStartTimeTogglesOnBooking(ArrayList<String> arr){
		Date stD = null;
		for(int i=0;i<arr.size();i++){
			List<Booking> bookings = new ArrayList<Booking>(connection.getAllBooking(program.business().getBusinessId()));
			for (Booking b : bookings) {
				if(b!=null){
				if (b.getStatus() != "canceled"){
					stD = b.getStartTime();
					if (program.dateToStr(newBook.getDate()).equals(program.dateToStr(b.getDate()))) {
						if (stD.equals(program.strToTime(arr.get(i)))) {
							if(arr.get(i).contains("\n") || arr.get(i).contains("\\n")){
								arr.set(i, arr.get(i)+"\nUnavailable");
							}
						}
					}
				}
				}
			}
		}
		return arr;
	}

	/**
	 * Moves the customer forward
	 * 
	 * @author [Programmer]
	 */
	@FXML
	public void nextView() {
		if (listviewBookingServices.getSelectionModel().getSelectedItem() == null) {
			program.messageBox("ERROR", "Error", "A Service Has Not Been Chosen", "Please select a service");
			return;
		}
		if (cmbDayBooking.getSelectionModel().getSelectedItem() == null) {
			program.messageBox("ERROR", "Error", "A Date Has Not Been Chosen", "Please select a date");
			return;
		}
		Service service = listviewBookingServices.getSelectionModel().getSelectedItem();
		if (stkpnDateService.isVisible() && stkpnBookingMenu.isVisible()) {
			newBook.setCus(program.getUser().getID());
			stkpnDateService.setVisible(false);
			stkpnTime.setVisible(true);
			lblBookingService.setText(service.getName());
			lblBookingDur.setText(Integer.toString(service.getLengthMin()) + "min");
			lblBookingPrice.setText("$" + Double.toString(service.getPrice()));
			newBook.setService(service.getID());
			lblCustBookingDate.setText(program.dateToStr(cmbDayBooking.getSelectionModel().getSelectedItem()));
			newBook.setDate(cmbDayBooking.getSelectionModel().getSelectedItem());
			popTime();
			return;
		}
		if (listviewTimeSlot.getSelectionModel().getSelectedItem() == null) {
			program.messageBox("ERROR", "Error", "A Time Slot Has Not Been Chosen", "Please select a time slot");
			return;
		}
		if (cmbPreferEmp.getSelectionModel().getSelectedItem() == null) {
			program.messageBox("ERROR", "Error", "A Employee Has Not Been Chosen", "Please select a Employee");
			return;
		}
		if (stkpnTime.isVisible() && stkpnBookingMenu.isVisible()) {
			stkpnTime.setVisible(false);
			stkpnBookingMenu.setVisible(false);
			stkpnBookingConfirm.setVisible(true);
			newBook.setStartTime(program.strToDate(listviewTimeSlot.getSelectionModel().getSelectedItem()));
			Date date = program.calEnTime(newBook.getStartTime(), service.getLengthMin());
			newBook.setEndTime(date);
			newBook.setEmployee(cmbPreferEmp.getSelectionModel().getSelectedItem().getId());
			log.debug("LOGGER: emp ID - " + cmbPreferEmp.getSelectionModel().getSelectedItem().getId());
			lblBookConSer.setText(service.getName());
			lblBookConDur.setText(Integer.toString(service.getLengthMin()) + "min");
			lblBookConPri.setText("$" + Double.toString(service.getPrice()));
			lblBookConDate.setText(program.dateToStr(cmbDayBooking.getSelectionModel().getSelectedItem()));
			lblBookConSTim.setText(program.timeToStr(newBook.getStartTime()));
			lblBookConEmp.setText(cmbPreferEmp.getSelectionModel().getSelectedItem().getName());
			return;
		} else {}

	}

	/**
	 * Moves the customer backward
	 * 
	 * @author Joseph Garner
	 */
	@FXML
	public void backView() {
		if (stkpnDateService.isVisible() && stkpnBookingMenu.isVisible()) {
			lblBookingService.setText("");
			lblBookingDur.setText("");
			lblBookingPrice.setText("");
			lblCustBookingDate.setText("");
			listviewBookingServices.getSelectionModel().clearSelection();
			cmbDayBooking.getSelectionModel().clearSelection();
			stkpnBookingMenu.setVisible(false);
			stkpnUserMenu.setVisible(true);
			return;
		}
		if (stkpnTime.isVisible() && stkpnBookingMenu.isVisible()) {

			cmbPreferEmp.getSelectionModel().clearSelection();
			stkpnTime.setVisible(false);
			stkpnDateService.setVisible(true);
			return;
		}
		if (stkpnBookingConfirm.isVisible()) {
			stkpnBookingConfirm.setVisible(false);
			stkpnTime.setVisible(true);
			stkpnBookingMenu.setVisible(true);
			return;
		}

	}

	/**
	 * 
	 * @author [Programmer]
	 */
	@FXML
	public void confirmBooking() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Book Appointment");
		alert.setHeaderText("Confirm Apointment");
		alert.setContentText("Are you sure?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			newBook.setStatus("active");
			connection.createBooking(newBook);
			Alert feedback = new Alert(AlertType.INFORMATION);
			feedback.setTitle("Book Appointment");
			feedback.setHeaderText("Appointment has been made");
			feedback.showAndWait();
			listviewBookingServices.getSelectionModel().clearSelection();
			cmbDayBooking.getSelectionModel().clearSelection();
			stkpnDateService.setVisible(true);
			stkpnBookingConfirm.setVisible(false);
			stkpnUserMenu.setVisible(true);
			lblBookConSer.setText("");
			lblBookConDur.setText("");
			lblBookConPri.setText("");
			lblBookConDate.setText("");
			lblBookConSTim.setText("");
			lblBookConEmp.setText("");
			lblBookingService.setText("");
			lblBookingDur.setText("");
			lblBookingPrice.setText("");
			lblCustBookingDate.setText("");

		} else {
			return;
		}

	}

	/**
	 * Returns the business owner to their menus
	 * 
	 * @author Joseph Garner
	 */
	
	@FXML
	public void returnOwnMen() {
		Stage sc = (Stage) btnRToOwnMen.getScene().getWindow();
		sc.close();
	}
	
	
	/*
	private void listTogTSini(int businessID){
		togbtnMorn.setSelected(true);
		timeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				//addBookingTime();
				Date date = program.calEnTime(newBook.getStartTime(),
						connection.getService(newBook.getService()).getLengthMin());
				newBook.setEndTime(date);
				log.debug("LOGGER: Time Slot Start - " + togbtnTimeSlot1.getText());
				log.debug("LOGGER: Time Slot End - " + program.timeToStr(newBook.getEndTime()));
				if (togbtnTimeSlot1.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot1.getText(), program.timeToStr(newBook.getEndTime()),businessID);
				}
				if (togbtnTimeSlot2.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot2.getText(), program.timeToStr(newBook.getEndTime()),businessID);
				}
				if (togbtnTimeSlot3.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot3.getText(), program.timeToStr(newBook.getEndTime()),businessID);
				}
				if (togbtnTimeSlot4.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot4.getText(), program.timeToStr(newBook.getEndTime()),businessID);
				}
				if (togbtnTimeSlot5.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot5.getText(), program.timeToStr(newBook.getEndTime()),businessID);
				}
				if (togbtnTimeSlot6.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot6.getText(), program.timeToStr(newBook.getEndTime()),businessID);
				}
				if (togbtnTimeSlot7.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot7.getText(), program.timeToStr(newBook.getEndTime()),businessID);
				}
				if (togbtnTimeSlot8.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot8.getText(), program.timeToStr(newBook.getEndTime()),businessID);
				}
			}
		});
	}
	*/
	/*
	private void listTogTDini(int businessID){
		togbtnMorn.setToggleGroup(timeODayGroup);
		togbtnAft.setToggleGroup(timeODayGroup);
		togbtnEven.setToggleGroup(timeODayGroup);
		timeODayGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (togbtnMorn.isSelected()) {
					togbtnAft.setSelected(false);
					togbtnEven.setSelected(false);
					Calendar date = Calendar.getInstance();
					date.set(Calendar.HOUR_OF_DAY, 8);
					date.set(Calendar.MINUTE, 0);
					diplayBookingTime(date);
					checkBookingTime(businessID);
				} else if (togbtnAft.isSelected()) {
					togbtnMorn.setSelected(false);
					togbtnEven.setSelected(false);
					Calendar date = Calendar.getInstance();
					log.debug("LOGGER: date - " + date);
					date.set(Calendar.HOUR_OF_DAY, 12);
					date.set(Calendar.MINUTE, 00);
					log.debug("LOGGER: date - " + date.get(Calendar.HOUR_OF_DAY));
					diplayBookingTime(date);
					checkBookingTime(businessID);
				} else {
					togbtnMorn.setSelected(false);
					togbtnAft.setSelected(false);
					Calendar date = Calendar.getInstance();
					log.debug("LOGGER: date - " + date);
					date.set(Calendar.HOUR_OF_DAY, 16);
					date.set(Calendar.MINUTE, 0);
					log.debug("LOGGER: date - " + date.get(Calendar.HOUR_OF_DAY));
					diplayBookingTime(date);
					checkBookingTime(businessID);
				}
			}
		});
	}
	*/
	
	
	/**
	 * Logs the user out
	 * 
	 * @author Luke Mason
	 */
	@FXML
	public void logout() {
		//stage = (Stage) stkBusiness.getScene().getWindow();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("Logout of Account?");
		alert.setContentText("Are you sure?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			//stage.hide();
			program.setUser(null);
			Stage st = (Stage) btnLogoutCustomer.getScene().getWindow();
			st.close();
		} else {
			return;
		}
	}

	//@Override
	public boolean getUserWindow() {
		try {
			Stage secondaryStage = new Stage();
			secondaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
			Parent root = FXMLLoader.load(getClass().getResource("customerLayout.fxml"));
			secondaryStage.setTitle("Customer Application");
			secondaryStage.setMinWidth(800);
			secondaryStage.setMinHeight(650);
			secondaryStage.setMaxWidth(1000);
			secondaryStage.setMaxHeight(850);
			secondaryStage.setScene(new Scene(root));
			secondaryStage.initModality(Modality.APPLICATION_MODAL);
			secondaryStage.showAndWait();
			if (program.getUser() != null) {
				return true;
			}
		} catch (IOException ioe) {
			log.warn(ioe.getMessage());
		}
		log.debug("false");
		return false;
	}

	
	

}

