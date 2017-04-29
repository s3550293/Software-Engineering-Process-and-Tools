package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
import program.Customer;
import program.DatabaseConnection;
import program.BusinessMenu;
import program.Service;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class MainController implements Initializable {

	private static Logger log = Logger.getLogger(MainController.class);
	private Controller program = new Controller();
	private DatabaseConnection connection = new DatabaseConnection();
	private Employee employee = null;
	private Booking booking = null;
	int globalEmployeeOption = 0;
	private static Booking newBook=new Booking(0,0,null,null,null,0,null);
	public MainController() {}
	
	/**************
	 * B Owner
	 **************/
	
	@FXML
	ToggleGroup bookingViewGroup = new ToggleGroup();
	
	@FXML
	RadioButton rbCurrentBook, rbPastBook;
	
	@FXML
	StackPane stkBusiness, stkCustomer;

	@FXML
	BorderPane boardPaneEmpAdd, boardPaneEmpOverview;

	@FXML
	Button btnRefreshBooking, btnSearchBookings, btnCancelBooking, btnLogout, btnRefreshEmployee, btnSearchEmployee,
			btnConfirm, btnViewEmpDetails;

	@FXML
	Label lblEmployeeID, lblEmployeeName, lblEmployeePayrate, lblEmployeeTitle;

	@FXML
	Label lblBookingID, lblBookingCustomerID, lblBookingDate, lblBookingStartTime, lblBookingEndTime, lblBookingStatus;

	@FXML
	ListView<Booking> listviewBookings;

	@FXML
	ListView<Employee> listviewEmployees;

	@FXML
	ComboBox<Date> cmbBookingDay;

	@FXML
	TextField txtSearchBookings, txtaddEmpFirstName, txtAddEmpLastName, txtAddEmpPayRate, txtSearchEmployee;

	@FXML
	CheckBox chkbxAddWorkingTimes, chbkViewAllBook;

	@FXML
	GridPane gridpWorkingTimes;

	@FXML
	ToggleButton btnSunMorning, btnSunAfternoon, btnSunEvening, btnMonMorning, btnMonAfternoon, btnMonEvening,
			btnTueMorning, btnTueAfternoon, btnTueEvening;
	@FXML
	ToggleButton btnWedMorning, btnWedAfternoon, btnWedEvening, btnThurMorning, btnThurAfternoon, btnThurEvening,
			btnFriMorning, btnFriAfternoon, btnFriEvening;
	@FXML
	ToggleButton btnSatMorning, btnSatAfternoon, btnSatEvening;
	
	@FXML
	Label lblServiceID, lblManServiceName, lblManServicePrice, lblServiceDura;
	
	@FXML
	ListView<Service> listviewManServices;
	
	@FXML
	Button btnRefershServices, btnDeleteService, btnAddService, btnSearchServices;
	
	@FXML
	TextField txtSearchService;
	
	@FXML
	Label lblCustFirstName, lblCustLastName, lblCustUsername, lblCustEmail, lblCustMobile, lblCustDOB, lblCustGender;
	
	@FXML
	ListView<Customer> listviewCustomers;
	
	@FXML
	Button btnSearchCustomer, btnDeleteCustomer, btnCreateBooking, btnRefershCustomers;
	
	@FXML
	TextField txtSearchCustomer;
	
	
	/**************
	 * Customer
	 **************/
	
	@FXML
	Button btnBookAppointment, btnConfirmBooking, btnNext, btnLogoutCustomer, btnBack, btnCancelAppoitment;
	
	@FXML
	Label lblCustomerName, lblDayDate, lblServiceName, lblServiceDur, lblServicePrice, lblCustBookingDate, lblBookingService, lblBookingPrice, lblBookingDur, lblBookingTime, lblBookingEmp;
	
	@FXML
	ComboBox<Date> cmbDayBooking;
	
	@FXML
	ComboBox<Employee> cmbPreferEmp;
	
	@FXML
	ListView<Service> listviewBookingServices;
	
	@FXML
	StackPane stkpnUserMenu, stkpnBookingMenu, stkpnDateService, stkpnTime, stkpnBookingConfirm;
	
	@FXML
	ToggleButton togbtnMorn, togbtnAft, togbtnEven, togbtnTimeSlot1, togbtnTimeSlot2, togbtnTimeSlot3, togbtnTimeSlot4, togbtnTimeSlot5, togbtnTimeSlot6, togbtnTimeSlot7, togbtnTimeSlot8;
	
	@FXML
	ToggleGroup timeODayGroup = new ToggleGroup();
	
	@FXML
	ToggleGroup timeGroup = new ToggleGroup();
	
	@FXML
	Label lblAvail1, lblAvail2, lblAvail3, lblAvail4, lblAvail5, lblAvail6, lblAvail7, lblAvail8;

	/**
	 * initializes the stage
	 * 
	 * @author Joseph
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		stkBusiness.setVisible(true);
		stkCustomer.setVisible(false);
		boolean var = login();
		loadDaySelect();
		loadallServices("");
		if (var == true) {
			if (program.getUser().getAccountType() == 1) {
				stkBusiness.setVisible(true);
				stkCustomer.setVisible(false);

				rbCurrentBook.setSelected(true);
				loadListViewEmp("");
				listviewEmployees.getSelectionModel().selectedItemProperty()
						.addListener(new ChangeListener<Employee>() {
							@Override
							public void changed(ObservableValue<? extends Employee> observable, Employee oldValue,
									Employee newValue) {
								if (newValue != null) {
									employee = newValue;
									lblEmployeeID.setText(Integer.toString(employee.getId()));
									lblEmployeeName.setText(employee.getName());
									lblEmployeePayrate.setText(Double.toString(employee.getPayRate()));
								}
							}
						});
				loadListViewBook();
				loadallCustomers();
				listviewBookings.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Booking>() {
					@Override
					public void changed(ObservableValue<? extends Booking> observable, Booking oldValue,
							Booking newValue) {
						if (newValue != null) {
							booking = newValue;
							lblBookingID.setText(Integer.toString(booking.getBookingID()));
							lblBookingCustomerID.setText(connection.getCustomer(booking.getCustomerId()).getFullName());
							lblBookingDate.setText(program.dateToStr(booking.getDate()));
							lblBookingStartTime.setText(program.timeToStr(booking.getStartTime()));
							lblBookingEndTime.setText(program.timeToStr(booking.getEndTime()));
							lblBookingStatus.setText(booking.getStatus());
						}
					}
				});

				listviewManServices.getSelectionModel().selectedItemProperty()
						.addListener(new ChangeListener<Service>() {
							@Override
							public void changed(ObservableValue<? extends Service> observable, Service oldValue,
									Service newValue) {
								if (newValue != null) {
									lblServiceID.setText(Integer.toString(newValue.getID()));
									lblManServiceName.setText(newValue.getName());
									lblManServicePrice.setText("$" + Double.toString(newValue.getPrice()));
									lblServiceDura.setText(Integer.toString(newValue.getLengthMin()));
								}
							}
						});
				listviewCustomers.getSelectionModel().selectedItemProperty()
						.addListener(new ChangeListener<Customer>() {
							@Override
							public void changed(ObservableValue<? extends Customer> observable, Customer oldValue,
									Customer newValue) {
								if (newValue != null) {
									lblCustFirstName.setText(newValue.getFName());
									lblCustLastName.setText(newValue.getLName());
									lblCustEmail.setText(newValue.getEmail());
									lblCustMobile.setText(newValue.getPhone());
									lblCustDOB.setText(newValue.getDOB());
									lblCustGender.setText(newValue.getGender());
								}
							}
						});
				cmbBookingDay.valueProperty().addListener(new ChangeListener<Date>() {
					@Override
					public void changed(ObservableValue ov, Date t, Date t1) {
						loadListViewBook();
					}
				});

				bookingViewGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
					public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
						loadDaySelect();
						loadListViewBook();
					}
				});

			} else {
				stkBusiness.setVisible(false);
				stkCustomer.setVisible(true);
				lblCustomerName.setText(connection.getCustomer(program.getUser().getID()).getFullName());
				cmbDayBooking.valueProperty().addListener(new ChangeListener<Date>() {
					@Override
					public void changed(ObservableValue ov, Date t, Date t1) {
						// TODO
					}
				});
				listviewBookingServices.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Service>() {
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
						lblDayDate.setText(program.dateToStr(cmbDayBooking.getSelectionModel().getSelectedItem()));
					}
				});
				togbtnMorn.setToggleGroup(timeODayGroup);
				togbtnAft.setToggleGroup(timeODayGroup);
				togbtnEven.setToggleGroup(timeODayGroup);
				newBook.setCus(program.getUser().getID());
				timeODayGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
					public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
						if(togbtnMorn.isSelected()){
							togbtnAft.setSelected(false);
							togbtnEven.setSelected(false);
							Calendar date = Calendar.getInstance();
							date.set(Calendar.HOUR_OF_DAY, 8);
							date.set(Calendar.MINUTE, 0);
					        for(int i = 0;i<8;i++){
					        	if(i == 0){togbtnTimeSlot1.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+"0");}
					        	if(i == 1){togbtnTimeSlot2.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE));}
					        	if(i == 2){togbtnTimeSlot3.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+"0");}
					        	if(i == 3){togbtnTimeSlot4.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE));}
					        	if(i == 4){togbtnTimeSlot5.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+"0");}
					        	if(i == 5){togbtnTimeSlot6.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE));}
					        	if(i == 6){togbtnTimeSlot7.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+"0");}
					        	if(i == 7){togbtnTimeSlot8.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE));}
					        	date.add(Calendar.MINUTE, 30);
					        }
						}
						else if(togbtnAft.isSelected()){
							togbtnMorn.setSelected(false);
							togbtnEven.setSelected(false);
							Calendar date = Calendar.getInstance();
							log.debug("LOGGER: date - "+date);
							date.set(Calendar.HOUR_OF_DAY, 12);
							date.set(Calendar.MINUTE, 00);
							log.debug("LOGGER: date - "+date.get(Calendar.HOUR_OF_DAY));
					        for(int i = 0;i<8;i++){
					        	if(i == 0){togbtnTimeSlot1.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+"0");}
					        	if(i == 1){togbtnTimeSlot2.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE));}
					        	if(i == 2){togbtnTimeSlot3.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+"0");}
					        	if(i == 3){togbtnTimeSlot4.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE));}
					        	if(i == 4){togbtnTimeSlot5.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+"0");}
					        	if(i == 5){togbtnTimeSlot6.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE));}
					        	if(i == 6){togbtnTimeSlot7.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+"0");}
					        	if(i == 7){togbtnTimeSlot8.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE));}
					        	date.add(Calendar.MINUTE, 30);
					        }
						}
						else{
							togbtnMorn.setSelected(false);
							togbtnAft.setSelected(false);
							Calendar date = Calendar.getInstance();
							log.debug("LOGGER: date - "+date);
							date.set(Calendar.HOUR_OF_DAY, 16);
							date.set(Calendar.MINUTE, 0);
							log.debug("LOGGER: date - "+date.get(Calendar.HOUR_OF_DAY));
					        for(int i = 0;i<8;i++){
					        	if(i == 0){togbtnTimeSlot1.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+"0");}
					        	if(i == 1){togbtnTimeSlot2.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE));}
					        	if(i == 2){togbtnTimeSlot3.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+"0");}
					        	if(i == 3){togbtnTimeSlot4.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE));}
					        	if(i == 4){togbtnTimeSlot5.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+"0");}
					        	if(i == 5){togbtnTimeSlot6.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE));}
					        	if(i == 6){togbtnTimeSlot7.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+"0");}
					        	if(i == 7){togbtnTimeSlot8.setText(date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE));}
					        	date.add(Calendar.MINUTE, 30);
					        }
					        checkBookingTime();
						}
					}
				});
				togbtnMorn.setSelected(true);
			}
		} else {
			Platform.exit();
			System.exit(0);
		}
	}

	/**
	 * Returns User to login
	 * 
	 * @author Joseph Garner
	 */
	private void loadListViewEmp(String name) {
		ArrayList<Employee> empArray = new ArrayList<>(connection.getEmployees(name));
		ObservableList<Employee> empList = FXCollections.observableList(empArray);
		log.debug("LOGGER: List length:" + empArray.size());
		if (empList != null) {
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
							else{
								listviewEmployees.setPlaceholder(new Label("No Employees"));
							}
						}
					};
					return cell;
				}
			});
		} else {
			log.warn("Unable to load Employees");
		}
	}

	/**
	 * Loads the current and past 7 days into a combo box
	 * 
	 * @author Joseph Garmer
	 */
	@FXML
	public void loadDaySelect() {
		SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
		Calendar date = Calendar.getInstance();
		ArrayList<Date> dateArray = new ArrayList<>();
		log.debug("LOGGER: rbPastBook - "+rbPastBook.isSelected());
		if (rbPastBook.isSelected()) {
			date.add(Calendar.DAY_OF_MONTH, -1);
			for (int i = 0; i < 7; i++) {
				log.debug("LOGGER: Date - " + date);
				dateArray.add(date.getTime());
				date.add(Calendar.DAY_OF_MONTH, -1);
			}
		} else {
			for (int i = 0; i < 7; i++) {
				log.debug("LOGGER: Date - " + date);
				dateArray.add(date.getTime());
				date.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		ObservableList<Date> dateList = FXCollections.observableList(dateArray);

		if (dateList != null) {
			cmbBookingDay.setItems(dateList);
			cmbBookingDay.getSelectionModel().select(0);
			cmbBookingDay.setCellFactory(new Callback<ListView<Date>, ListCell<Date>>() {

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
			cmbBookingDay.setButtonCell(new ListCell<Date>() {
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
			cmbDayBooking.setItems(dateList);
			cmbDayBooking.getSelectionModel().select(0);
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
			lblDayDate.setText(program.dateToStr(cmbDayBooking.getSelectionModel().getSelectedItem()));
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
	}

	/**
	 * loads bookings into list view
	 * 
	 * @author Joseph Garner
	 */
	private void loadListViewBook() {
		if(cmbBookingDay.getSelectionModel().getSelectedItem() != null)
		{
			listviewBookings.getItems().clear();
			Calendar cal = Calendar.getInstance();
			Date now = null;
			SimpleDateFormat dateView = new SimpleDateFormat("dd/MM/yyyy");
			ArrayList<Booking> bookArray = new ArrayList<>(connection.getAllBooking());
			ArrayList<Booking> bookArrayView = new ArrayList<>();
			for (Booking b : bookArray) {
				cal.setTime(b.getDate());
				now = cal.getTime();
				log.debug("LOGGER: selected day - " + cmbBookingDay.getSelectionModel().getSelectedItem());
				log.debug("LOGGER: booking date - " + dateView.format(now));
				if (dateView.format(now).equals(dateView.format(cmbBookingDay.getSelectionModel().getSelectedItem()))) {
					bookArrayView.add(b);
					log.debug("LOGGER: Booking added - " + b.toString());
				}
			}
			ObservableList<Booking> bookList = FXCollections.observableList(bookArrayView);
			log.debug("LOGGER: List length:" + bookArrayView.size());
			if (bookList != null) {
				listviewBookings.setItems(bookList);
				listviewBookings.setCellFactory(new Callback<ListView<Booking>, ListCell<Booking>>() {
					@Override
					public ListCell<Booking> call(ListView<Booking> p) {
	
						ListCell<Booking> cell = new ListCell<Booking>() {
							@Override
							protected void updateItem(Booking t, boolean bln) {
								super.updateItem(t, bln);
								if (t != null) {
									setText(t.getBookingID() + " " + connection.getCustomer(t.getCustomerId()).getFullName()
											+ " " + program.timeToStr(t.getStartTime()));
								}
								else{
									listviewEmployees.setPlaceholder(new Label("No Bookings"));
								}
							}
						};
						return cell;
					}
				});
			} else {
				log.warn("Unable to load Employees");
			}
		}
	}
	
	/**
	 * loads all bookings into list view
	 * 
	 * @author Joseph Garner
	 */
	public void loadAllBookings(){
		if(cmbBookingDay.getSelectionModel().getSelectedItem() != null)
		{
			listviewBookings.getItems().clear();
			ArrayList<Booking> bookArray = new ArrayList<>(connection.getAllBooking());
			ObservableList<Booking> bookList = FXCollections.observableList(bookArray);
			log.debug("LOGGER: List length:" + bookArray.size());
			if (bookList != null) {
				listviewBookings.setItems(bookList);
				listviewBookings.setCellFactory(new Callback<ListView<Booking>, ListCell<Booking>>() {
					@Override
					public ListCell<Booking> call(ListView<Booking> p) {
	
						ListCell<Booking> cell = new ListCell<Booking>() {
							@Override
							protected void updateItem(Booking t, boolean bln) {
								super.updateItem(t, bln);
								if (t != null) {
									setText(t.getBookingID() + " " + connection.getCustomer(t.getCustomerId()).getFullName()
											+ " " + program.timeToStr(t.getStartTime()));
								}
								else{
									listviewEmployees.setPlaceholder(new Label("No Bookings"));
								}
							}
						};
						return cell;
					}
				});
			} else {
				log.warn("Unable to load Employees");
			}
		}
	}
	
	/**
	 * loads all Services into list view
	 * 
	 * @author Joseph Garner
	 */
	@FXML
	public void loadallServices(String input){
		listviewManServices.getItems().clear();
		ArrayList<Service> serviceArray = new ArrayList<>(connection.getAllServices(input));
		ObservableList<Service> serviceList = FXCollections.observableList(serviceArray);
		log.debug("LOGGER: List length:" + serviceArray.size());
		if (serviceList != null) {
			listviewManServices.setItems(serviceList);
			listviewBookingServices.setItems(serviceList);
			listviewManServices.setCellFactory(new Callback<ListView<Service>, ListCell<Service>>() {
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
	
	/**
	 * loads all customers into list view
	 * 
	 * @author Joseph Garner
	 */
	@FXML
	public void loadallCustomers(){
		listviewCustomers.getItems().clear();
		ArrayList<Customer> serviceArray = new ArrayList<>(connection.getAllCustomer());
		ObservableList<Customer> serviceList = FXCollections.observableList(serviceArray);
		log.debug("LOGGER: List length:" + serviceArray.size());
		if (serviceList != null) {
			listviewCustomers.setItems(serviceList);
			listviewCustomers.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {
				@Override
				public ListCell<Customer> call(ListView<Customer> p) {

					ListCell<Customer> cell = new ListCell<Customer>() {
						@Override
						protected void updateItem(Customer t, boolean bln) {
							super.updateItem(t, bln);
							if (t != null) {
								log.debug("LOGGER: added:" + t.getFName());
								setText(t.getFName()+" "+t.getLName());
							}
							else{
								listviewEmployees.setPlaceholder(new Label("No Bookings"));
							}
						}
					};
					return cell;
				}
			});
		}
	}

	/**************
	 * LOGIN
	 **************/
	
	/**
	 * Logs the user out
	 * 
	 * @author [Programmer]
	 */
	@FXML
	public void logout()
	{
		//TODO
	}
	
	/**
	 * Returns User to login
	 * 
	 * @author [Programmer]
	 */
	@FXML
	public boolean login() {
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
			if (program.getUser() != null) {
				return true;
			}
		} catch (IOException ioe) {
			log.warn(ioe.getMessage());
		}
		log.debug("false");
		return false;
	}

	/**************
	 * BOOKINGS
	 **************/

	/**
	 * Displays all bookings to user
	 * 
	 * @author Joseph Garner
	 */
	@FXML
	public void viewAllBookings()
	{
		if(chbkViewAllBook.isSelected())
    	{
    		cmbBookingDay.setDisable(true);
    		rbPastBook.setDisable(true);
    		rbPastBook.setSelected(false);
    		rbCurrentBook.setDisable(true);
    		rbCurrentBook.setSelected(false);
    		loadAllBookings();
    	}
    	else{
    		cmbBookingDay.setDisable(false);
    		rbPastBook.setDisable(false);
    		rbCurrentBook.setDisable(false);
    		rbCurrentBook.setSelected(true);
    		loadListViewBook();
    	}
	}
	
	/**
	 * Program Allows the user to search and displays bookings
	 * 
	 * @author Joseph Garner
	 */
	@FXML
	public void searchBookings() {
		log.debug("LOGGER: Entered searchBookings function");
		listviewBookings.getItems().clear();
		Calendar cal = Calendar.getInstance();
		Date now = null;
		SimpleDateFormat dateView = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<Booking> bookArray = new ArrayList<>(connection.getAllBooking());
		ArrayList<Booking> bookArrayView = new ArrayList<>();
		String fname = null;
		String lname = null;
		String fullName = null;
		for (Booking b : bookArray) {
			cal.setTime(b.getDate());
			now = cal.getTime();
			fname = connection.getCustomer(b.getCustomerId()).getFName();
			lname = connection.getCustomer(b.getCustomerId()).getLName();
			fullName = fname + " " + lname;
			boolean checkF = program.searchMatch(fname.toUpperCase( ), txtSearchBookings.getText().toUpperCase( ));
			boolean checkL = program.searchMatch(lname.toUpperCase( ), txtSearchBookings.getText().toUpperCase( ));
			boolean checkFL = program.searchMatch(fullName.toUpperCase( ), txtSearchBookings.getText().toUpperCase( ));
			log.debug("LOGGER: Users Name - "+fullName);
			if (checkF || checkL || checkFL) {
				if(chbkViewAllBook.isSelected()){
					bookArrayView.add(b);
				}
				else if (!chbkViewAllBook.isSelected() && dateView.format(now).equals(dateView.format(cmbBookingDay.getSelectionModel().getSelectedItem()))){
					bookArrayView.add(b);
					log.debug("LOGGER: Booking added - " + b.toString());
				}
				else{}
			}
		}
		ObservableList<Booking> bookList = FXCollections.observableList(bookArrayView);
		log.debug("LOGGER: List length:" + bookArrayView.size());
		if (bookList != null) {
			listviewBookings.setItems(bookList);
			listviewBookings.setCellFactory(new Callback<ListView<Booking>, ListCell<Booking>>() {
				@Override
				public ListCell<Booking> call(ListView<Booking> p) {

					ListCell<Booking> cell = new ListCell<Booking>() {
						@Override
						protected void updateItem(Booking t, boolean bln) {
							super.updateItem(t, bln);
							if (t != null) {
								setText(t.getBookingID() + " " + connection.getCustomer(t.getCustomerId()).getFullName()
										+ " " + program.timeToStr(t.getStartTime()));
							}
						}
					};
					return cell;
				}
			});
		} else {
			program.messageBox("WARN", "Search Result", "No Search Results Found", "");
		}
	}

	/**
	 * Cancels booking (does not remove the booking from view)
	 * 
	 * @author [Programmer]
	 */
	@FXML
	public void cancelBooking() {
		log.debug("LOGGER: Entered cancelBooking function");
		if(listviewBookings.getSelectionModel().getSelectedIndex() < 0)
		{
			program.messageBox("WARN", "oops", "A Booking has not been Selected","Please Select a booking and try again");
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Cancel Booking");
		alert.setHeaderText("Cancel Selected Booking");
		alert.setContentText("Are you sure?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Booking book = null;
			book = listviewBookings.getSelectionModel().getSelectedItem();
			connection.cancelBooking(book.getBookingID());
			Alert feedback = new Alert(AlertType.INFORMATION);
			feedback.setTitle("Cancel Booking");
			feedback.setHeaderText("Booking has been cancelled");
			feedback.showAndWait();

		} else {
			return;
		}
		listviewBookings.getSelectionModel().clearSelection();
		loadListViewBook();
	}
	

	/**
	 * Displays and refreshes booking view to all
	 * 
	 * @author Joseph Garner
	 */
	@FXML
	public void refreshBookingView() {
		log.debug("LOGGER: Entered refreshBookingView function");
		txtSearchBookings.setText("");
		loadListViewBook();
	}

	/**
	 * Shows add employee
	 * note tabs should be disabled
	 * @author Luke Mason
	 */

	/**************
	 * EMPLOYEE
	 **************/
	@FXML
	public void showAddNewEmp() {
		boardPaneEmpAdd.setVisible(true);
		boardPaneEmpOverview.setVisible(false);
	}

	/**
	 * Returns User to manage employees
	 * @author Luke Mason
	 */
	@FXML
	public void cancelAddNewEmp() {
		boardPaneEmpAdd.setVisible(false);
		boardPaneEmpOverview.setVisible(true);
		//Resetting the add Employee board to default values
		txtaddEmpFirstName.setText("");
		txtAddEmpLastName.setText(""); 
		txtAddEmpPayRate.setText(""); 
		chkbxAddWorkingTimes.setSelected(false);
		setAllTogglesToFalse();
		globalEmployeeOption = 0;
		lblEmployeeTitle.setText("Add New Employee");
		gridpWorkingTimes.setDisable(true);
	}

	/**
	 * Enables and disables working times
	 * 
	 * @author [Joseph Garner]
	 */
	@FXML
	public void allowWorkingTimes() {
		if (chkbxAddWorkingTimes.isSelected()) {
			gridpWorkingTimes.setDisable(false);
		} else {
			gridpWorkingTimes.setDisable(true);
		}
	}

	/**
	 * Creates an Employee
	 * 
	 * @author Luke Mason
	 */
	@FXML
	public void createEmp() 
	{
		BusinessMenu bMenu = new BusinessMenu();
		boolean firstName = !program.checkInputToContainInvalidChar(txtaddEmpFirstName.getText());
		log.debug("First Name = "+firstName+"\n");
		if(!firstName)
		{
			program.messageBox("ERROR", "First Name Invalid", "First Name Invalid","First Name entered is not a valid first name\nReason: First name contains invalid characters");
			return;
		}
		boolean lastName = !program.checkInputToContainInvalidChar(txtAddEmpLastName.getText());
		log.debug("last Name = "+lastName+"\n");
		if(!lastName)
		{
			program.messageBox("ERROR", "Last Name Invalid", "Last Name Invalid","Last Name entered is not a valid last name\nReason: Last name contains invalid characters");
			return;
		}
		double payRate = bMenu.strPayRateToDouble(txtAddEmpPayRate.getText());
		log.debug("pay rate = "+payRate+"\n");
		boolean PayRate = bMenu.checkEmployeePayRate(payRate);
		log.debug("is pay rate okay? "+PayRate+"\n");
		if(!PayRate)
		{
			program.messageBox("ERROR", "Pay Rate Invalid", "Pay Rate Invalid","Pay rate entered is not a valid pay rate\nReason: Pay Rate is not 0 - 1000");
			return;
		}

		if(PayRate && firstName && lastName)
		{	
			log.debug("globalEmployeeOption = "+globalEmployeeOption+"\n");
			log.debug("chkboxWorkingTimes = "+chkbxAddWorkingTimes.isSelected()+"\n");
			if(chkbxAddWorkingTimes.isSelected())
			{
				if(!bMenu.checkWorkTimes(btnSunMorning.isSelected(), btnSunAfternoon.isSelected(), btnSunEvening.isSelected(), btnMonMorning.isSelected(), btnMonAfternoon.isSelected(), btnMonEvening.isSelected()
						, btnTueMorning.isSelected(), btnTueAfternoon.isSelected(), btnTueEvening.isSelected(), btnWedMorning.isSelected()
						, btnWedAfternoon.isSelected(), btnWedEvening.isSelected(), btnThurMorning.isSelected(), btnThurAfternoon.isSelected()
						, btnThurEvening.isSelected(), btnFriMorning.isSelected(), btnFriAfternoon.isSelected(), btnFriEvening.isSelected()
						, btnSatMorning.isSelected(), btnSatAfternoon.isSelected(), btnSatEvening.isSelected()))
				{
					return;
				}
				if(globalEmployeeOption == 0)
				{
					bMenu.addEmployee(txtaddEmpFirstName.getText(),txtAddEmpLastName.getText(), payRate);
					int id = bMenu.getLastEmployeeId();
					bMenu.addWorkingTimes(id,btnSunMorning.isSelected(), btnSunAfternoon.isSelected()
							, btnSunEvening.isSelected(), btnMonMorning.isSelected(), btnMonAfternoon.isSelected(), btnMonEvening.isSelected()
							, btnTueMorning.isSelected(), btnTueAfternoon.isSelected(), btnTueEvening.isSelected(), btnWedMorning.isSelected()
							, btnWedAfternoon.isSelected(), btnWedEvening.isSelected(), btnThurMorning.isSelected(), btnThurAfternoon.isSelected()
							, btnThurEvening.isSelected(), btnFriMorning.isSelected(), btnFriAfternoon.isSelected(), btnFriEvening.isSelected()
							, btnSatMorning.isSelected(), btnSatAfternoon.isSelected(), btnSatEvening.isSelected());
					program.messageBox("SUCCESS", "New Employee Added", "New Employee Added",txtaddEmpFirstName.getText()+" "+txtAddEmpLastName.getText()+" with $"+payRate+"/h was Added!"); 
				}
				else
				{				
					Employee employee = listviewEmployees.getSelectionModel().getSelectedItem();
					int employeeID = employee.getId();
					changeEmployeesDetails(employeeID,txtaddEmpFirstName.getText(), txtAddEmpLastName.getText(), payRate);
					bMenu.addWorkingTimes(employeeID,btnSunMorning.isSelected(), btnSunAfternoon.isSelected()
							, btnSunEvening.isSelected(), btnMonMorning.isSelected(), btnMonAfternoon.isSelected(), btnMonEvening.isSelected()
							, btnTueMorning.isSelected(), btnTueAfternoon.isSelected(), btnTueEvening.isSelected(), btnWedMorning.isSelected()
							, btnWedAfternoon.isSelected(), btnWedEvening.isSelected(), btnThurMorning.isSelected(), btnThurAfternoon.isSelected()
							, btnThurEvening.isSelected(), btnFriMorning.isSelected(), btnFriAfternoon.isSelected(), btnFriEvening.isSelected()
							, btnSatMorning.isSelected(), btnSatAfternoon.isSelected(), btnSatEvening.isSelected());					
						program.messageBox("SUCCESS", "Employee Details Updated", "Employee Details Updated",txtaddEmpFirstName.getText()+" "+txtAddEmpLastName.getText()+" with $"+payRate+"/h was updated!"); 
					lblEmployeeTitle.setText("Add New Employee");
					globalEmployeeOption = 0;
				}
			}
			else
			{
				if(globalEmployeeOption == 0)
				{
					bMenu.addEmployee(txtaddEmpFirstName.getText(),txtAddEmpLastName.getText(), payRate);	
					program.messageBox("SUCCESS", "SUCCESS", "New Employee Added",txtaddEmpFirstName.getText()+" "+txtAddEmpLastName.getText()+" with $"+payRate+"/h was Added!");
				}
				else
				{
					Employee employee = listviewEmployees.getSelectionModel().getSelectedItem();
					int employeeID = employee.getId();
					setAllTogglesToFalse();//wiping the work times
					changeEmployeesDetails(employeeID,txtaddEmpFirstName.getText(), txtAddEmpLastName.getText(), payRate);
					bMenu.addWorkingTimes(employeeID,btnSunMorning.isSelected(), btnSunAfternoon.isSelected()
							, btnSunEvening.isSelected(), btnMonMorning.isSelected(), btnMonAfternoon.isSelected(), btnMonEvening.isSelected()
							, btnTueMorning.isSelected(), btnTueAfternoon.isSelected(), btnTueEvening.isSelected(), btnWedMorning.isSelected()
							, btnWedAfternoon.isSelected(), btnWedEvening.isSelected(), btnThurMorning.isSelected(), btnThurAfternoon.isSelected()
							, btnThurEvening.isSelected(), btnFriMorning.isSelected(), btnFriAfternoon.isSelected(), btnFriEvening.isSelected()
							, btnSatMorning.isSelected(), btnSatAfternoon.isSelected(), btnSatEvening.isSelected());
					program.messageBox("SUCCESS", "Employee Details Updated", "Employee Details Updated",txtaddEmpFirstName.getText()+" "+txtAddEmpLastName.getText()+" with $"+payRate+"/h was updated!"); 
					lblEmployeeTitle.setText("Add New Employee");
					globalEmployeeOption = 0;
				}
			}
		}
		refreshEmployeeView();
		cancelAddNewEmp();
	}
	
	/**
	 * updates the employees names and payrates in database
	 * @param empID
	 * @param fName
	 * @param lName
	 * @param pRate
	 */
	public void changeEmployeesDetails(int empID,String fName, String lName, double pRate)
	{	
		String name = fName+" "+lName;
		connection.updateEmployeeName(empID, name);
		connection.updateEmployeePayRate(empID,pRate);
	}
	
	/**
	 * Sets all the workTime toggles to false
	 */
	public void setAllTogglesToFalse()
	{
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
	 * Resets the filtered employee list view
	 * @author Luke Mason
	 */
	@FXML

	public void refreshEmployeeView()
	{
		txtSearchEmployee.setText("");
		loadListViewEmp("");
		lblEmployeeID.setText("");
		lblEmployeeName.setText("");
		lblEmployeePayrate.setText("");
		lblEmployeeTitle.setText("");
	}

	/**
	 * Filters the list of employees
	 * @author Luke Mason
	 */
	@FXML

	public void searchEmployee()
	{
		String name = txtSearchEmployee.getText();
		loadListViewEmp(name);
	}

	/**
	 * Views Employees Details
	 * @author Luke Mason
	 */
	@FXML

	public void viewEmpDetails()
	{
		BusinessMenu bMenu = new BusinessMenu();		
		//Initializing variables
		int employeeID = -1;

		String ePayRate = "";
		lblEmployeeTitle.setText("Change Employee Details");
		//Checking if an item has been selected
		if(listviewEmployees.getSelectionModel().getSelectedIndex() < 0)
		{
			program.messageBox("WARN", "oops", "An Employee has not been Selected","Please Select an employee and try again");
			return;
		}
		Employee employee = listviewEmployees.getSelectionModel().getSelectedItem();
		employeeID = employee.getId();

		//Getting first and last name from full name string
		String empFullName = employee.getName();

		int index = empFullName.indexOf(' ');
		if(index < 0)
		{
			program.messageBox("ERROR", "No space in employee Name", "Please manually change that employee","Please UPDATE the employee name direct from database");
			return;
		}
		String eFirstName = empFullName.substring(0,index);

		String eLastName = empFullName.substring(index+1);

		//converting double to string
		double EPayRate = employee.getPayRate();

		ePayRate = ""+EPayRate;
		//Assigning textFields with known Strings
		txtaddEmpFirstName.setText(eFirstName);
		txtAddEmpLastName.setText(eLastName); 
		txtAddEmpPayRate.setText(ePayRate); 
		ArrayList<EmployeeWorkingTime> workTimes = new ArrayList<EmployeeWorkingTime>();
		//work time contains, WorkTimeID|EmployeeID|Date|StartTime|EndTime	
		workTimes = connection.getEmployeeWorkingTimes(employeeID);
		int checkBox = 0;
		System.out.println("Work Time Size = "+workTimes.size());
		if(workTimes.size() > 0)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String[] dateArray = new String[7];
			
			//Getting dates for first 7days
			Calendar c = Calendar.getInstance();
			c.setTime(workTimes.get(0).getDate());
			for(int i = 1;i<=7; i++)
			{
				dateArray[i-1] = sdf.format(c.getTime());//puts each date from every loop into array
				c.add(Calendar.DAY_OF_MONTH, 1);
			}		
			for(int j = 0; j<7; j++)
			{
				for(EmployeeWorkingTime wTime: workTimes)
				{
					String date = program.dateToStr(wTime.getDate());
					if(date.equals(dateArray[j]))
					{
						String startTime = program.timeToStr(wTime.getStartTime());
						String endTime = program.timeToStr(wTime.getEndTime());
						int timeBlock = bMenu.getTimeBlock(startTime,endTime);
						if(timeBlock == -1)
						{
							System.out.println("INVALID TIME BLOCK DETECTED");
						}
						else
						{
							Calendar d = Calendar.getInstance();
							Date date2 = program.strToDate(dateArray[j]);
							d.setTime(date2);
							int dayOfWeek = d.get(Calendar.DAY_OF_WEEK);
							changeButtonsOfDay(dayOfWeek, timeBlock);
							checkBox++;
						}
					}
				}
			}
		}
		if(checkBox == 0)
		{
			chkbxAddWorkingTimes.setSelected(false);
			gridpWorkingTimes.setDisable(true);
		}
		else
		{
			chkbxAddWorkingTimes.setSelected(true);
			gridpWorkingTimes.setDisable(false);
		}
		globalEmployeeOption = 1;
		showAddNewEmp();
	}

	/**
	 * sets a certain day's toggles according to a combination of morning, afternoon and evening
	 * @param dayOfWeek
	 * @param timeBlock
	 */
	public void changeButtonsOfDay(int dayOfWeek, int timeBlock)
	{
		switch(dayOfWeek)
		{
			case 1: 
				if(timeBlock == 1)
				{
					btnSunMorning.setSelected(true);
					btnSunAfternoon.setSelected(true);
					btnSunEvening.setSelected(true);
				}
				else if(timeBlock == 2)
				{
					btnSunMorning.setSelected(true);
					btnSunAfternoon.setSelected(true);
					btnSunEvening.setSelected(false);
				}
				else if(timeBlock == 3)
				{
					btnSunMorning.setSelected(false);
					btnSunAfternoon.setSelected(true);
					btnSunEvening.setSelected(true);
				}
				else if(timeBlock == 4)
				{
					btnSunMorning.setSelected(true);
					btnSunAfternoon.setSelected(false);
					btnSunEvening.setSelected(false);
				}
				else if(timeBlock == 5)
				{
					btnSunMorning.setSelected(false);
					btnSunAfternoon.setSelected(true);
					btnSunEvening.setSelected(false);
				}
				else if(timeBlock == 6)
				{
					btnSunMorning.setSelected(false);
					btnSunAfternoon.setSelected(false);
					btnSunEvening.setSelected(true);
				}
				break;
			case 2: 
				if(timeBlock == 1)
				{
					btnMonMorning.setSelected(true);
					btnMonAfternoon.setSelected(true);
					btnMonEvening.setSelected(true);
				}
				else if(timeBlock == 2)
				{
					btnMonMorning.setSelected(true);
					btnMonAfternoon.setSelected(true);
					btnMonEvening.setSelected(false);
				}
				else if(timeBlock == 3)
				{
					btnMonMorning.setSelected(false);
					btnMonAfternoon.setSelected(true);
					btnMonEvening.setSelected(true);
				}
				else if(timeBlock == 4)
				{
					btnMonMorning.setSelected(true);
					btnMonAfternoon.setSelected(false);
					btnMonEvening.setSelected(false);
				}
				else if(timeBlock == 5)
				{
					btnMonMorning.setSelected(false);
					btnMonAfternoon.setSelected(true);
					btnMonEvening.setSelected(false);
				}
				else if(timeBlock == 6)
				{
					btnMonMorning.setSelected(false);
					btnMonAfternoon.setSelected(false);
					btnMonEvening.setSelected(true);
				}
				break;
			case 3: 
				if(timeBlock == 1)
				{
					btnTueMorning.setSelected(true);
					btnTueAfternoon.setSelected(true);
					btnTueEvening.setSelected(true);
				}
				else if(timeBlock == 2)
				{
					btnTueMorning.setSelected(true);
					btnTueAfternoon.setSelected(true);
					btnTueEvening.setSelected(false);
				}
				else if(timeBlock == 3)
				{
					btnTueMorning.setSelected(false);
					btnTueAfternoon.setSelected(true);
					btnTueEvening.setSelected(true);
				}
				else if(timeBlock == 4)
				{
					btnTueMorning.setSelected(true);
					btnTueAfternoon.setSelected(false);
					btnTueEvening.setSelected(false);
				}
				else if(timeBlock == 5)
				{
					btnTueMorning.setSelected(false);
					btnTueAfternoon.setSelected(true);
					btnTueEvening.setSelected(false);
				}
				else if(timeBlock == 6)
				{
					btnTueMorning.setSelected(false);
					btnTueAfternoon.setSelected(false);
					btnTueEvening.setSelected(true);
				}
				break;
			case 4: 
				if(timeBlock == 1)
				{
					btnWedMorning.setSelected(true);
					btnWedAfternoon.setSelected(true);
					btnWedEvening.setSelected(true);
				}
				else if(timeBlock == 2)
				{
					btnWedMorning.setSelected(true);
					btnWedAfternoon.setSelected(true);
					btnWedEvening.setSelected(false);
				}
				else if(timeBlock == 3)
				{
					btnWedMorning.setSelected(false);
					btnWedAfternoon.setSelected(true);
					btnWedEvening.setSelected(true);
				}
				else if(timeBlock == 4)
				{
					btnWedMorning.setSelected(true);
					btnWedAfternoon.setSelected(false);
					btnWedEvening.setSelected(false);
				}
				else if(timeBlock == 5)
				{
					btnWedMorning.setSelected(false);
					btnWedAfternoon.setSelected(true);
					btnWedEvening.setSelected(false);
				}
				else if(timeBlock == 6)
				{
					btnWedMorning.setSelected(false);
					btnWedAfternoon.setSelected(false);
					btnWedEvening.setSelected(true);
				}
				break;
			case 5: 
				if(timeBlock == 1)
				{
					btnThurMorning.setSelected(true);
					btnThurAfternoon.setSelected(true);
					btnThurEvening.setSelected(true);
				}
				else if(timeBlock == 2)
				{
					btnThurMorning.setSelected(true);
					btnThurAfternoon.setSelected(true);
					btnThurEvening.setSelected(false);
				}
				else if(timeBlock == 3)
				{
					btnThurMorning.setSelected(false);
					btnThurAfternoon.setSelected(true);
					btnThurEvening.setSelected(true);
				}
				else if(timeBlock == 4)
				{
					btnThurMorning.setSelected(true);
					btnThurAfternoon.setSelected(false);
					btnThurEvening.setSelected(false);
				}
				else if(timeBlock == 5)
				{
					btnThurMorning.setSelected(false);
					btnThurAfternoon.setSelected(true);
					btnThurEvening.setSelected(false);
				}
				else if(timeBlock == 6)
				{
					btnThurMorning.setSelected(false);
					btnThurAfternoon.setSelected(false);
					btnThurEvening.setSelected(true);
				}
				break;
			case 6: 
				if(timeBlock == 1)
				{
					btnFriMorning.setSelected(true);
					btnFriAfternoon.setSelected(true);
					btnFriEvening.setSelected(true);
				}
				else if(timeBlock == 2)
				{
					btnFriMorning.setSelected(true);
					btnFriAfternoon.setSelected(true);
					btnFriEvening.setSelected(false);
				}
				else if(timeBlock == 3)
				{
					btnFriMorning.setSelected(false);
					btnFriAfternoon.setSelected(true);
					btnFriEvening.setSelected(true);
				}
				else if(timeBlock == 4)
				{
					btnFriMorning.setSelected(true);
					btnFriAfternoon.setSelected(false);
					btnFriEvening.setSelected(false);
				}
				else if(timeBlock == 5)
				{
					btnFriMorning.setSelected(false);
					btnFriAfternoon.setSelected(true);
					btnFriEvening.setSelected(false);
				}
				else if(timeBlock == 6)
				{
					btnFriMorning.setSelected(false);
					btnFriAfternoon.setSelected(false);
					btnFriEvening.setSelected(true);
				}
				break;
			case 7: 
				if(timeBlock == 1)
				{
					btnSatMorning.setSelected(true);
					btnSatAfternoon.setSelected(true);
					btnSatEvening.setSelected(true);
				}
				else if(timeBlock == 2)
				{
					btnSatMorning.setSelected(true);
					btnSatAfternoon.setSelected(true);
					btnSatEvening.setSelected(false);
				}
				else if(timeBlock == 3)
				{
					btnSatMorning.setSelected(false);
					btnSatAfternoon.setSelected(true);
					btnSatEvening.setSelected(true);
				}
				else if(timeBlock == 4)
				{
					btnSatMorning.setSelected(true);
					btnSatAfternoon.setSelected(false);
					btnSatEvening.setSelected(false);
				}
				else if(timeBlock == 5)
				{
					btnSatMorning.setSelected(false);
					btnSatAfternoon.setSelected(true);
					btnSatEvening.setSelected(false);
				}
				else if(timeBlock == 6)
				{
					btnSatMorning.setSelected(false);
					btnSatAfternoon.setSelected(false);
					btnSatEvening.setSelected(true);
				}
				break;
			default:
				program.messageBox("WARN", "Error: Something happened with dayOfWeek", "dayOfWeek did not register to a day","Please consult Luke Mason for the crap coding");
		}
	}
	
	/**
	 * Deletes Employee
	 * @author Luke Mason
	 */
	@FXML
	public void deleteEmplyee() 
	{
		log.debug("LOGGER: Entered deleteEmployee function");
		if(listviewEmployees.getSelectionModel().getSelectedIndex() < 0)
		{
			program.messageBox("WARN", "oops", "An Employee has not been Selected","Please Select an employee and try again");
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Employee");
		alert.setHeaderText("Delete Selected Employee");
		alert.setContentText("Are you sure?");

		Optional<ButtonType> result = alert.showAndWait();
		System.out.println(globalEmployeeOption);
		if (result.get() == ButtonType.OK) {
			Employee employee = listviewEmployees.getSelectionModel().getSelectedItem();
			int employeeID = employee.getId();
			connection.clearWorkTimes(employeeID);
			connection.deleteEmployee(employeeID);
			Alert feedback = new Alert(AlertType.INFORMATION);
			feedback.setTitle("Delete Employee");
			feedback.setHeaderText("Employee has been deleted");
			feedback.showAndWait();
			refreshEmployeeView();
			
		} 
		else 
		{
			return;
		}
	}
	
	/**************
	 * SERVICES
	 **************/
	
	/**
	 * Searches Services List
	 * @author [Joseph Garner]
	 */
	@FXML
	public void searchServices()
	{
		loadListViewEmp(txtSearchService.getText());
	}
	
	/**
	 * Creates a new service
	 * @author [Joseph Garner]
	 */
	@FXML
	public void addService()
	{
		try {
			Stage secondaryStage = new Stage();
			secondaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
			Parent root = FXMLLoader.load(getClass().getResource("serviceLayout.fxml"));
			secondaryStage.setTitle("");
			secondaryStage.setResizable(false);
			secondaryStage.setScene(new Scene(root));
			secondaryStage.initModality(Modality.APPLICATION_MODAL);
			secondaryStage.showAndWait();
			secondaryStage.setTitle("Login");
		} catch (IOException ioe) {
			log.warn(ioe.getMessage());
		}	
		log.debug("false");
		loadallServices("");
		
	}
	
	/**
	 * Deletes a Service
	 * @author [Luke Mason]
	 */
	@FXML
	public void deleteService()
	{
		log.debug("LOGGER: Entered deleteService function");
		if(listviewManServices.getSelectionModel().getSelectedIndex() < 0)
		{
			program.messageBox("WARN", "oops", "An Service has not been Selected","Please Select a Service and try again");
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Service");
		alert.setHeaderText("Delete Selected Service");
		alert.setContentText("Are you sure?");

		Optional<ButtonType> result = alert.showAndWait();
		System.out.println(globalEmployeeOption);
		if (result.get() == ButtonType.OK) {
			Service service = listviewManServices.getSelectionModel().getSelectedItem();
			int serviceID = service.getID();
			System.out.println("Service ID = "+serviceID);
			connection.deleteService(serviceID);
			Alert feedback = new Alert(AlertType.INFORMATION);
			feedback.setTitle("Delete Service");
			feedback.setHeaderText("Service has been deleted");
			feedback.showAndWait();
			lblServiceID.setText("");
			lblManServiceName.setText("");
			lblManServicePrice.setText("");
			lblServiceDura.setText("");
			refreshService();
		} 
		else 
		{
			return;
		}
	}
	
	/**
	 * Refresh all Services
	 * @author [Luke Mason]
	 */
	@FXML
	public void refreshService()
	{
		loadallServices("");
	}
	
	/**************
	 * BMENU CUSTOMERS
	 **************/
	
	
	/**
	 * Searches Customers
	 * @author [Programmer]
	 */
	@FXML
	public void searchCustomers()
	{
		listviewCustomers.getItems().clear();
		ArrayList<Customer> customerArray = new ArrayList<>(connection.getAllCustomer());
		ArrayList<Customer> customerArrayView = new ArrayList<>();
		String fname = null;
		String lname = null;
		String fullName = null;
		for (Customer c : customerArray) {
			fname = connection.getCustomer(c.getID()).getFName();
			lname = connection.getCustomer(c.getID()).getLName();
			fullName = fname + " " + lname;
			boolean checkF = program.searchMatch(fname.toUpperCase( ), txtSearchCustomer.getText().toUpperCase());
			boolean checkL = program.searchMatch(lname.toUpperCase( ), txtSearchCustomer.getText().toUpperCase());
			boolean checkFL = program.searchMatch(fullName.toUpperCase( ), txtSearchCustomer.getText().toUpperCase());
			log.debug("LOGGER: Users Name - "+fullName);
			if (checkF || checkL || checkFL) {
					customerArrayView.add(c);
			}
		}
		ObservableList<Customer> customerList = FXCollections.observableList(customerArrayView);
		log.debug("LOGGER: List length:" + customerArrayView.size());
		if (customerList != null) {
			listviewCustomers.setItems(customerList);
			listviewCustomers.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {
				@Override
				public ListCell<Customer> call(ListView<Customer> p) {

					ListCell<Customer> cell = new ListCell<Customer>() {
						@Override
						protected void updateItem(Customer t, boolean bln) {
							super.updateItem(t, bln);
							if (t != null) {
								log.debug("LOGGER: added:" + t.getFName());
								setText(t.getFName()+" "+t.getLName());
							}
							else{
								listviewEmployees.setPlaceholder(new Label("No Bookings"));
							}
						}
					};
					return cell;
				}
			});
		}
		else {
			program.messageBox("WARN", "Search Result", "No Search Results Found", "");
		}
	}
	
	/**
	 * Creates a booking for selected customer
	 * @author [Programmer]
	 */
	@FXML
	public void createBooking()
	{
		//TODO
	}
	
	
	
	/**************
	 * Customer
	 **************/
	/**
	 * Enters to booking phase
	 * @author Luke
	 */
	private void loadpreferedEmp()
	{
		//TODO
		ArrayList<Employee> dateArray = new ArrayList<>();
		ObservableList<Employee> dateList = FXCollections.observableList(dateArray);
		if (dateList != null) {
			cmbPreferEmp.setItems(dateList);
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
			cmbBookingDay.setButtonCell(new ListCell<Date>() {
				@Override
				protected void updateItem(Date t, boolean bln) {
					super.updateItem(t, bln);
					if (t != null) {
						setText("");
					} else {
						setText(null);
					}

				}
			});
		}
	}
	
	/**
	 * Enters to booking phase
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
	
	private void checkBookingTime()
	{
		if(newBook.getDate() != null){
			Date stD = null;
			Date enD = null;
			List<Booking> bookings = new ArrayList<Booking>(connection.getAllBooking());
			for(Booking b : bookings)
			{
				stD = b.getStartTime();
				enD = b.getEndTime();
				log.debug("LOGGER: start time - "+stD+" end time - "+enD);
				if(program.dateToStr(newBook.getDate()).equals(program.dateToStr(b.getDate())))
				{
					log.debug("LOGGER: Dates Match");
					if(togbtnMorn.isSelected()){
						if(stD.compareTo(program.strToTime(togbtnTimeSlot1.getText())) == 0){
							log.debug("LOGGER: Time Matchs 8:00");
							togbtnTimeSlot1.setDisable(true);
							lblAvail1.setText("Unavailable");
							lblAvail1.setStyle("-fx-text-fill: #ff0000;");
						}
						else if(stD.equals(program.strToTime(togbtnTimeSlot2.getText()))){
							togbtnTimeSlot2.setDisable(true);
							lblAvail2.setText("Unavailable");
							lblAvail2.setStyle("-fx-text-fill: #ff0000;");
						}
						else if(stD.equals(program.strToTime(togbtnTimeSlot3.getText()))){
							togbtnTimeSlot3.setDisable(true);
							lblAvail3.setText("Unavailable");
							lblAvail3.setStyle("-fx-text-fill: #ff0000;");
						}
						else if(stD.equals(program.strToTime(togbtnTimeSlot4.getText()))){
							togbtnTimeSlot4.setDisable(true);
							lblAvail4.setText("Unavailable");
							lblAvail4.setStyle("-fx-text-fill: #ff0000;");
						}
						else if(stD.equals(program.strToTime(togbtnTimeSlot5.getText()))){
							togbtnTimeSlot5.setDisable(true);
							lblAvail5.setText("Unavailable");
							lblAvail5.setStyle("-fx-text-fill: #ff0000;");
						}
						else if(stD.equals(program.strToTime(togbtnTimeSlot6.getText()))){
							togbtnTimeSlot6.setDisable(true);
							lblAvail6.setText("Unavailable");
							lblAvail6.setStyle("-fx-text-fill: #ff0000;");
						}
						else if(stD.equals(program.strToTime(togbtnTimeSlot7.getText()))){
							togbtnTimeSlot7.setDisable(true);
							lblAvail7.setText("Unavailable");
							lblAvail7.setStyle("-fx-text-fill: #ff0000;");
						}
						else if(stD.equals(program.strToTime(togbtnTimeSlot8.getText()))){
							togbtnTimeSlot8.setDisable(true);
							lblAvail8.setText("Unavailable");
							lblAvail8.setStyle("-fx-text-fill: #ff0000;");
						}
						
						if(enD.after(program.strToTime(togbtnTimeSlot2.getText()))){
							togbtnTimeSlot2.setDisable(true);
							lblAvail2.setText("Unavailable");
							lblAvail2.setStyle("-fx-text-fill: #ff0000;");
						}
						else if(enD.after(program.strToTime(togbtnTimeSlot3.getText()))){
							togbtnTimeSlot3.setDisable(true);
							lblAvail3.setText("Unavailable");
							lblAvail3.setStyle("-fx-text-fill: #ff0000;");
						}
						else if( enD.after(program.strToTime(togbtnTimeSlot4.getText()))){
							togbtnTimeSlot4.setDisable(true);
							lblAvail4.setText("Unavailable");
							lblAvail4.setStyle("-fx-text-fill: #ff0000;");
						}
						else if(enD.after(program.strToTime(togbtnTimeSlot5.getText()))){
							togbtnTimeSlot5.setDisable(true);
							lblAvail5.setText("Unavailable");
							lblAvail5.setStyle("-fx-text-fill: #ff0000;");
						}
						else if(enD.after(program.strToTime(togbtnTimeSlot6.getText()))){
							togbtnTimeSlot6.setDisable(true);
							lblAvail6.setText("Unavailable");
							lblAvail6.setStyle("-fx-text-fill: #ff0000;");
						}
						else if(enD.after(program.strToTime(togbtnTimeSlot7.getText()))){
							togbtnTimeSlot7.setDisable(true);
							lblAvail7.setText("Unavailable");
							lblAvail7.setStyle("-fx-text-fill: #ff0000;");
						}
						else if(enD.after(program.strToTime(togbtnTimeSlot8.getText()))){
							togbtnTimeSlot8.setDisable(true);
							lblAvail8.setText("Unavailable");
							lblAvail8.setStyle("-fx-text-fill: #ff0000;");
						}
						log.debug("LOGGER: Button 8 date - "+program.strToTime(togbtnTimeSlot8.getText()));
					}
					else if(togbtnAft.isSelected()){
						
					}
					else if(togbtnEven.isSelected()){
						
					}
					else{}
				}
			}
		}
	}
	
	/**
	 * Moves the customer forward
	 * @author Joseph Garner
	 */
	private void addBookingTime()
	{
		if(togbtnTimeSlot1.isSelected())
		{
			if(togbtnMorn.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot1.getText()));
				return;
			}
			if(togbtnAft.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot1.getText()));
				return;
			}
			if(togbtnEven.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot1.getText()));
				return;
			}
		}
		if(togbtnTimeSlot2.isSelected())
		{
			if(togbtnMorn.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot2.getText()));
				return;
			}
			if(togbtnAft.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot2.getText()));
				return;
			}
			if(togbtnEven.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot2.getText()));
				return;
			}
		}
		if(togbtnTimeSlot3.isSelected())
		{
			if(togbtnMorn.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot3.getText()));
				return;
			}
			if(togbtnAft.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot3.getText()));
				return;
			}
			if(togbtnEven.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot3.getText()));
				return;
			}
		}
		if(togbtnTimeSlot4.isSelected())
		{
			if(togbtnMorn.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot4.getText()));
				return;
			}
			if(togbtnAft.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot4.getText()));
				return;
			}
			if(togbtnEven.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot4.getText()));
				return;
			}
		}
		if(togbtnTimeSlot5.isSelected())
		{
			if(togbtnMorn.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot5.getText()));
				return;
			}
			if(togbtnAft.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot5.getText()));
				return;
			}
			if(togbtnEven.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot5.getText()));
				return;
			}
		}
		if(togbtnTimeSlot6.isSelected())
		{
			if(togbtnMorn.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot6.getText()));
				return;
			}
			if(togbtnAft.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot6.getText()));
				return;
			}
			if(togbtnEven.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot6.getText()));
				return;
			}
		}
		if(togbtnTimeSlot7.isSelected())
		{
			if(togbtnMorn.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot7.getText()));
				return;
			}
			if(togbtnAft.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot7.getText()));
				return;
			}
			if(togbtnEven.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot7.getText()));
				return;
			}
		}
		if(togbtnTimeSlot8.isSelected())
		{
			if(togbtnMorn.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot8.getText()));
				return;
			}
			if(togbtnAft.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot8.getText()));
				return;
			}
			if(togbtnEven.isSelected()){
				newBook.setStartTime(program.strToTime(togbtnTimeSlot8.getText()));
				return;
			}
		}
		
		
		
	}

	/**
	 * Moves the customer forward
	 * @author [Programmer]
	 */
	@FXML
	public void nextView(){
		Service service = listviewBookingServices.getSelectionModel().getSelectedItem();
		if(stkpnDateService.isVisible() && stkpnBookingMenu.isVisible())
		{
			stkpnDateService.setVisible(false);
			stkpnTime.setVisible(true);
			lblBookingService.setText(service.getName());
			lblBookingDur.setText(Integer.toString(service.getLengthMin()));
			lblBookingPrice.setText("$" + Double.toString(service.getPrice()));
			newBook.setService(service.getID());
			lblCustBookingDate.setText(program.dateToStr(cmbDayBooking.getSelectionModel().getSelectedItem()));
			newBook.setDate(cmbDayBooking.getSelectionModel().getSelectedItem());
			checkBookingTime();
			return;
		}
		if(stkpnTime.isVisible() && stkpnBookingMenu.isVisible())
		{
			stkpnTime.setVisible(false);
			stkpnBookingMenu.setVisible(false);
			stkpnBookingConfirm.setVisible(true);
			addBookingTime();
			log.debug("LOGGER: booking start time - "+program.timeToStr(newBook.getStartTime()));
			//TODO
			
			return;
		}
		
	}
	
	/**
	 * Moves the customer backward
	 * @author Joseph Garner
	 */
	@FXML
	public void backView(){
		if(stkpnDateService.isVisible() && stkpnBookingMenu.isVisible())
		{
			stkpnBookingMenu.setVisible(false);
			stkpnUserMenu.setVisible(true);
			return;
		}
		if(stkpnTime.isVisible() && stkpnBookingMenu.isVisible())
		{
			stkpnTime.setVisible(false);
			stkpnDateService.setVisible(true);
			return;
		}
		if(stkpnBookingConfirm.isVisible())
		{
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
	public void confirmBooking(){
		//TODO
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

		} else {
			return;
		}
		
	}
}
