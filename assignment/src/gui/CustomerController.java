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

import org.apache.log4j.Logger;

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
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import program.Booking;
import program.Controller;
import program.DatabaseConnection;
import program.Employee;
import program.Service;

public class CustomerController  implements Initializable, IUser{

	private static Logger log = Logger.getLogger(MainController.class);
	private static Controller program = new Controller();
	private DatabaseConnection connection = new DatabaseConnection();
	private Employee employee = null;
	private Booking booking = null;
	int globalEmployeeOption = 0;
	private static Booking newBook = new Booking();
	
	@FXML
	Button btnBookAppointment, btnConfirmBooking, btnNext, btnLogoutCustomer, btnBack, btnCancelAppoitment,
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
	ToggleButton togbtnMorn, togbtnAft, togbtnEven, togbtnTimeSlot1, togbtnTimeSlot2, togbtnTimeSlot3, togbtnTimeSlot4,
			togbtnTimeSlot5, togbtnTimeSlot6, togbtnTimeSlot7, togbtnTimeSlot8;

	@FXML
	ToggleGroup timeODayGroup = new ToggleGroup();

	@FXML
	ToggleGroup timeGroup = new ToggleGroup();

	@FXML
	Label lblAvail1, lblAvail2, lblAvail3, lblAvail4, lblAvail5, lblAvail6, lblAvail7, lblAvail8;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
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
		loadDaySelect();
		listTogTDini();
		listTogTSini();
	}
	
	@FXML
	public void loadDaySelect() {
		SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
		Calendar date = Calendar.getInstance();
		ArrayList<Date> dateArray = new ArrayList<>();
			for (int i = 0; i < 7; i++) {
				log.debug("LOGGER: Date - " + date);
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
	
	/**
	 * loads all Services into list view
	 * 
	 * @author Joseph Garner
	 */
	@FXML
	public void loadallServices(String input) {
		ArrayList<Service> serviceArray = new ArrayList<>(connection.getAllServices(input));
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
	private void loadpreferedEmp(String startTime, String endTime) {
		// TODO
		List<Employee> emArray;
		log.debug("LOGGER: date selected - " + lblCustBookingDate.getText());
		emArray = program.getAvailableEmployeesForSpecifiedTime(lblCustBookingDate.getText(), startTime, endTime);
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

	/**
	 * Check and display booking time during booking
	 * Disable toggle button, after booking
	 */
	private void checkBookingTime() {
		togbtnTimeSlot1.setDisable(false);
		togbtnTimeSlot2.setDisable(false);
		togbtnTimeSlot3.setDisable(false);
		togbtnTimeSlot4.setDisable(false);
		togbtnTimeSlot5.setDisable(false);
		togbtnTimeSlot6.setDisable(false);
		togbtnTimeSlot7.setDisable(false);
		togbtnTimeSlot8.setDisable(false);
		lblAvail1.setText("Available");
		lblAvail1.setStyle("-fx-text-fill: #008b00;");
		lblAvail2.setText("Available");
		lblAvail2.setStyle("-fx-text-fill: #008b00;");
		lblAvail3.setText("Available");
		lblAvail3.setStyle("-fx-text-fill: #008b00;");
		lblAvail4.setText("Available");
		lblAvail4.setStyle("-fx-text-fill: #008b00;");
		lblAvail5.setText("Available");
		lblAvail5.setStyle("-fx-text-fill: #008b00;");
		lblAvail6.setText("Available");
		lblAvail6.setStyle("-fx-text-fill: #008b00;");
		lblAvail7.setText("Available");
		lblAvail7.setStyle("-fx-text-fill: #008b00;");
		lblAvail8.setText("Available");
		lblAvail8.setStyle("-fx-text-fill: #008b00;");

		if (newBook.getDate() != null) {
			Date stD = null;
			Date enD = null;
			List<Booking> bookings = new ArrayList<Booking>(connection.getAllBooking());
			for (Booking b : bookings) {
				stD = b.getStartTime();
				enD = b.getEndTime();
				log.debug("LOGGER: start time - " + stD + " end time - " + enD);
				if (b.getStatus() != "canceled") {
					if (program.dateToStr(newBook.getDate()).equals(program.dateToStr(b.getDate()))) {
						log.debug("LOGGER: Dates Match");
						if (togbtnMorn.isSelected()) {
							checkStartTimeTogglesOnBooking(stD);
							
							checkEndTimeForEachBookingSessionAndDisableToggleIfOverlapping(true, enD, "", 
									"8:31", togbtnTimeSlot2.getText(), "9:01", togbtnTimeSlot3.getText(), 
									"9:31", togbtnTimeSlot4.getText(), "10:01", togbtnTimeSlot5.getText(),
									"10:31", togbtnTimeSlot6.getText(), "11:01", togbtnTimeSlot7.getText(), 
									"11:31", togbtnTimeSlot8.getText());
							
							log.debug("LOGGER: Button 8 date - " + program.strToTime(togbtnTimeSlot8.getText()));
						} else if (togbtnAft.isSelected()) {
							checkStartTimeTogglesOnBooking(stD);
							checkEndTimeForEachBookingSessionAndDisableToggleIfOverlapping(true, enD, togbtnTimeSlot1.getText(), 
									"12:31", togbtnTimeSlot2.getText(), "13:01", togbtnTimeSlot3.getText(), 
									"13:31", togbtnTimeSlot4.getText(), "14:01", togbtnTimeSlot5.getText(),
									"14:31", togbtnTimeSlot6.getText(), "15:01", togbtnTimeSlot7.getText(), 
									"15:31", togbtnTimeSlot8.getText());
							
						} else if (togbtnEven.isSelected()) {
							checkStartTimeTogglesOnBooking(stD);
							checkEndTimeForEachBookingSessionAndDisableToggleIfOverlapping(true, enD, togbtnTimeSlot1.getText(), 
									"16:31", togbtnTimeSlot2.getText(), "17:01", togbtnTimeSlot3.getText(), 
									"17:31", togbtnTimeSlot4.getText(), "18:01", togbtnTimeSlot5.getText(),
									"18:31", togbtnTimeSlot6.getText(), "19:01", togbtnTimeSlot7.getText(), 
									"19:31", togbtnTimeSlot8.getText());
						} else {
						}
					}
				}
			}
		}
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
	public void checkEndTimeForEachBookingSessionAndDisableToggleIfOverlapping
			(Boolean mornshift, Date enD, String timeslot1,
			String timeslot2B, String timeslot2, String timeslot3B, String timeslot3, 
			String timeslot4B, String timeslot4, String timeslot5B, String timeslot5, 
			String timeslot6B, String timeslot6, String timeslot7B, String timeslot7,
			String timeslot8B, String timeslot8){
		
		/*
		 *  In morning shift, the first timeslot cannot be disable initially.
		 *  but in other shifts, the first timeslot may be disable as the booking session is carried over from the previous shift.
		 */
		if(!mornshift){
			if (enD.after(program.strToTime(timeslot1))
					&& enD.before(program.strToTime(timeslot2B))) {
				togbtnTimeSlot1.setDisable(true);
				lblAvail1.setText("Unavailable");
				lblAvail1.setStyle("-fx-text-fill: #ff0000;");
			}
		}
		
        if (enD.after(program.strToTime(timeslot2))
				&& enD.before(program.strToTime(timeslot3B))) {
			togbtnTimeSlot2.setDisable(true);
			lblAvail2.setText("Unavailable");
			lblAvail2.setStyle("-fx-text-fill: #ff0000;");
		}
		if (enD.after(program.strToTime(timeslot3))
				&& enD.before(program.strToTime(timeslot4B))) {
			togbtnTimeSlot3.setDisable(true);
			lblAvail3.setText("Unavailable");
			lblAvail3.setStyle("-fx-text-fill: #ff0000;");
		}
		if (enD.after(program.strToTime(timeslot4))
				&& enD.before(program.strToTime(timeslot5B))) {
			togbtnTimeSlot4.setDisable(true);
			lblAvail4.setText("Unavailable");
			lblAvail4.setStyle("-fx-text-fill: #ff0000;");
		}
		if (enD.after(program.strToTime(timeslot5))
				&& enD.before(program.strToTime(timeslot6B))) {
			togbtnTimeSlot5.setDisable(true);
			lblAvail5.setText("Unavailable");
			lblAvail5.setStyle("-fx-text-fill: #ff0000;");
		}
		if (enD.after(program.strToTime(timeslot6))
				&& enD.before(program.strToTime(timeslot7B))) {
			togbtnTimeSlot6.setDisable(true);
			lblAvail6.setText("Unavailable");
			lblAvail6.setStyle("-fx-text-fill: #ff0000;");
		}
		if (enD.after(program.strToTime(timeslot7))
				&& enD.before(program.strToTime(timeslot8B))) {
			togbtnTimeSlot7.setDisable(true);
			lblAvail7.setText("Unavailable");
			lblAvail7.setStyle("-fx-text-fill: #ff0000;");
		}
		if (enD.after(program.strToTime(timeslot8))) {
			togbtnTimeSlot8.setDisable(true);
			lblAvail8.setText("Unavailable");
			lblAvail8.setStyle("-fx-text-fill: #ff0000;");
		}
	}
	
	/**
	 * Check a specified toggle button for StartTime and disable them after booking
	 * @param <Date> Start Date
	 */
	public void checkStartTimeTogglesOnBooking(Date stD){
		if (stD.compareTo(program.strToTime(togbtnTimeSlot1.getText())) == 0) {
			log.debug("LOGGER: Time Matchs 8:00");
			togbtnTimeSlot1.setDisable(true);
			lblAvail1.setText("Unavailable");
			lblAvail1.setStyle("-fx-text-fill: #ff0000;");
		} else if (stD.equals(program.strToTime(togbtnTimeSlot2.getText()))) {
			togbtnTimeSlot2.setDisable(true);
			lblAvail2.setText("Unavailable");
			lblAvail2.setStyle("-fx-text-fill: #ff0000;");
		} else if (stD.equals(program.strToTime(togbtnTimeSlot3.getText()))) {
			togbtnTimeSlot3.setDisable(true);
			lblAvail3.setText("Unavailable");
			lblAvail3.setStyle("-fx-text-fill: #ff0000;");
		} else if (stD.equals(program.strToTime(togbtnTimeSlot4.getText()))) {
			togbtnTimeSlot4.setDisable(true);
			lblAvail4.setText("Unavailable");
			lblAvail4.setStyle("-fx-text-fill: #ff0000;");
		} else if (stD.equals(program.strToTime(togbtnTimeSlot5.getText()))) {
			togbtnTimeSlot5.setDisable(true);
			lblAvail5.setText("Unavailable");
			lblAvail5.setStyle("-fx-text-fill: #ff0000;");
		} else if (stD.equals(program.strToTime(togbtnTimeSlot6.getText()))) {
			togbtnTimeSlot6.setDisable(true);
			lblAvail6.setText("Unavailable");
			lblAvail6.setStyle("-fx-text-fill: #ff0000;");
		} else if (stD.equals(program.strToTime(togbtnTimeSlot7.getText()))) {
			togbtnTimeSlot7.setDisable(true);
			lblAvail7.setText("Unavailable");
			lblAvail7.setStyle("-fx-text-fill: #ff0000;");
		} else if (stD.equals(program.strToTime(togbtnTimeSlot8.getText()))) {
			togbtnTimeSlot8.setDisable(true);
			lblAvail8.setText("Unavailable");
			lblAvail8.setStyle("-fx-text-fill: #ff0000;");
		}
	}

	/**
	 * Moves the customer forward
	 * 
	 * @author Joseph Garner
	 */
	private void addBookingTime() {
		if (togbtnTimeSlot1.isSelected()) {
			if (togbtnMorn.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot1.getText()));
				return;
			}
			if (togbtnAft.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot1.getText()));
				return;
			}
			if (togbtnEven.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot1.getText()));
				return;
			}
		}
		if (togbtnTimeSlot2.isSelected()) {
			if (togbtnMorn.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot2.getText()));
				return;
			}
			if (togbtnAft.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot2.getText()));
				return;
			}
			if (togbtnEven.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot2.getText()));
				return;
			}
		}
		if (togbtnTimeSlot3.isSelected()) {
			if (togbtnMorn.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot3.getText()));
				return;
			}
			if (togbtnAft.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot3.getText()));
				return;
			}
			if (togbtnEven.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot3.getText()));
				return;
			}
		}
		if (togbtnTimeSlot4.isSelected()) {
			if (togbtnMorn.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot4.getText()));
				return;
			}
			if (togbtnAft.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot4.getText()));
				return;
			}
			if (togbtnEven.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot4.getText()));
				return;
			}
		}
		if (togbtnTimeSlot5.isSelected()) {
			if (togbtnMorn.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot5.getText()));
				return;
			}
			if (togbtnAft.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot5.getText()));
				return;
			}
			if (togbtnEven.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot5.getText()));
				return;
			}
		}
		if (togbtnTimeSlot6.isSelected()) {
			if (togbtnMorn.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot6.getText()));
				return;
			}
			if (togbtnAft.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot6.getText()));
				return;
			}
			if (togbtnEven.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot6.getText()));
				return;
			}
		}
		if (togbtnTimeSlot7.isSelected()) {
			if (togbtnMorn.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot7.getText()));
				return;
			}
			if (togbtnAft.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot7.getText()));
				return;
			}
			if (togbtnEven.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot7.getText()));
				return;
			}
		}
		if (togbtnTimeSlot8.isSelected()) {
			if (togbtnMorn.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot8.getText()));
				return;
			}
			if (togbtnAft.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot8.getText()));
				return;
			}
			if (togbtnEven.isSelected()) {
				newBook.setStartTime(program.strToTime(togbtnTimeSlot8.getText()));
				return;
			}
		}

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
			checkBookingTime();
			return;
		}
		if (timeGroup.getSelectedToggle() == null) {
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
			addBookingTime();
			Date date = program.calEnTime(newBook.getStartTime(), service.getLengthMin());
			newBook.setEndTime(date);
			newBook.setEmployee(cmbPreferEmp.getSelectionModel().getSelectedItem().getId());
			log.debug("LOGGER: emp ID - " + cmbPreferEmp.getSelectionModel().getSelectedItem().getId());
			log.debug("LOGGER: emp ID  booking- " + newBook.getEmployee());
			lblBookConSer.setText(service.getName());
			lblBookConDur.setText(Integer.toString(service.getLengthMin()) + "min");
			lblBookConPri.setText("$" + Double.toString(service.getPrice()));
			lblBookConDate.setText(program.dateToStr(cmbDayBooking.getSelectionModel().getSelectedItem()));
			lblBookConSTim.setText(program.timeToStr(newBook.getStartTime()));
			lblBookConEmp.setText(cmbPreferEmp.getSelectionModel().getSelectedItem().getName());
			return;
		} else {
		}

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
			togbtnTimeSlot1.setSelected(false);
			togbtnTimeSlot2.setSelected(false);
			togbtnTimeSlot3.setSelected(false);
			togbtnTimeSlot4.setSelected(false);
			togbtnTimeSlot5.setSelected(false);
			togbtnTimeSlot6.setSelected(false);
			togbtnTimeSlot7.setSelected(false);
			togbtnTimeSlot8.setSelected(false);
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
		log.debug("LOGGER: emp ID  booking- " + newBook.getEmployee());
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			newBook.setStatus("active");
			log.debug("LOGGER: emp ID  booking- " + newBook.getEmployee());
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
			togbtnTimeSlot1.setSelected(false);
			togbtnTimeSlot2.setSelected(false);
			togbtnTimeSlot3.setSelected(false);
			togbtnTimeSlot4.setSelected(false);
			togbtnTimeSlot5.setSelected(false);
			togbtnTimeSlot6.setSelected(false);
			togbtnTimeSlot7.setSelected(false);
			togbtnTimeSlot8.setSelected(false);
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
		/*
		btnRToOwnMen.setDisable(true);
		stkBusiness.setVisible(true);
		stkCustomer.setVisible(false);
		btnRToOwnMen.setDisable(true);
		*/
	}
	
	
	
	private void listTogTSini(){
		togbtnMorn.setSelected(true);
		togbtnTimeSlot1.setToggleGroup(timeGroup);
		togbtnTimeSlot2.setToggleGroup(timeGroup);
		togbtnTimeSlot3.setToggleGroup(timeGroup);
		togbtnTimeSlot4.setToggleGroup(timeGroup);
		togbtnTimeSlot5.setToggleGroup(timeGroup);
		togbtnTimeSlot6.setToggleGroup(timeGroup);
		togbtnTimeSlot7.setToggleGroup(timeGroup);
		togbtnTimeSlot8.setToggleGroup(timeGroup);
		timeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				addBookingTime();
				Date date = program.calEnTime(newBook.getStartTime(),
						connection.getService(newBook.getService()).getLengthMin());
				newBook.setEndTime(date);
				log.debug("LOGGER: Time Slot Start - " + togbtnTimeSlot1.getText());
				log.debug("LOGGER: Time Slot End - " + program.timeToStr(newBook.getEndTime()));
				if (togbtnTimeSlot1.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot1.getText(), program.timeToStr(newBook.getEndTime()));
				}
				if (togbtnTimeSlot2.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot2.getText(), program.timeToStr(newBook.getEndTime()));
				}
				if (togbtnTimeSlot3.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot3.getText(), program.timeToStr(newBook.getEndTime()));
				}
				if (togbtnTimeSlot4.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot4.getText(), program.timeToStr(newBook.getEndTime()));
				}
				if (togbtnTimeSlot5.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot5.getText(), program.timeToStr(newBook.getEndTime()));
				}
				if (togbtnTimeSlot6.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot6.getText(), program.timeToStr(newBook.getEndTime()));
				}
				if (togbtnTimeSlot7.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot7.getText(), program.timeToStr(newBook.getEndTime()));
				}
				if (togbtnTimeSlot8.isSelected()) {
					loadpreferedEmp(togbtnTimeSlot8.getText(), program.timeToStr(newBook.getEndTime()));
				}
			}
		});
	}
	
	private void listTogTDini(){
		togbtnMorn.setToggleGroup(timeODayGroup);
		togbtnAft.setToggleGroup(timeODayGroup);
		togbtnEven.setToggleGroup(timeODayGroup);
		timeODayGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				togbtnTimeSlot1.setSelected(false);
				togbtnTimeSlot2.setSelected(false);
				togbtnTimeSlot3.setSelected(false);
				togbtnTimeSlot4.setSelected(false);
				togbtnTimeSlot5.setSelected(false);
				togbtnTimeSlot6.setSelected(false);
				togbtnTimeSlot7.setSelected(false);
				togbtnTimeSlot8.setSelected(false);
				if (togbtnMorn.isSelected()) {
					togbtnAft.setSelected(false);
					togbtnEven.setSelected(false);
					Calendar date = Calendar.getInstance();
					date.set(Calendar.HOUR_OF_DAY, 8);
					date.set(Calendar.MINUTE, 0);
					diplayBookingTime(date);
					checkBookingTime();
				} else if (togbtnAft.isSelected()) {
					togbtnMorn.setSelected(false);
					togbtnEven.setSelected(false);
					Calendar date = Calendar.getInstance();
					log.debug("LOGGER: date - " + date);
					date.set(Calendar.HOUR_OF_DAY, 12);
					date.set(Calendar.MINUTE, 00);
					log.debug("LOGGER: date - " + date.get(Calendar.HOUR_OF_DAY));
					diplayBookingTime(date);
					checkBookingTime();
				} else {
					togbtnMorn.setSelected(false);
					togbtnAft.setSelected(false);
					Calendar date = Calendar.getInstance();
					log.debug("LOGGER: date - " + date);
					date.set(Calendar.HOUR_OF_DAY, 16);
					date.set(Calendar.MINUTE, 0);
					log.debug("LOGGER: date - " + date.get(Calendar.HOUR_OF_DAY));
					diplayBookingTime(date);
					checkBookingTime();
				}
			}
		});
	}
	
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

	@Override
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

