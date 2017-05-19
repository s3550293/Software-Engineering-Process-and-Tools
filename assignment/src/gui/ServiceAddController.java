package gui;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import program.Business;
import program.Controller;
import program.DatabaseConnection;
import program.Service;


public class ServiceAddController {
	private static Logger log = Logger.getLogger(MainController.class);
	@FXML
	TextField txtSerNam, txtSerDur, txtSerPri;
	
	/**
	 * Creates a new service
	 * @author [Luke Mason]
	 */
	@FXML
	public void addService(Business business)
	{
		Controller program = new Controller();
		DatabaseConnection connect = new DatabaseConnection();
		Stage stage= (Stage) txtSerNam.getScene().getWindow();
		boolean serviceName = !program.checkInputToContainInvalidChar(txtSerNam.getText());
		log.debug("Service Name = "+serviceName+"\n");
		if(!serviceName)
		{
			program.messageBox("ERROR", "Service Name Invalid", "Service Name Invalid","Service Name entered is not a valid service name\nReason: Service name contains invalid characters");
			return;
		}
		int duration = program.changeInputIntoValidInt(txtSerDur.getText());
		log.debug("duration = "+duration+"\n");
		if(duration < 5 || duration > 60)
		{
			program.messageBox("ERROR", "Duration Invalid", "Duration Invalid","Duration entered is not a valid length\nReason: Duration is not 5 - 60 minutes");
			return;
		}
		double price = program.changeInputIntoValidDouble(txtSerPri.getText());
		if(price < 0 || price > 1000)		
		{
			program.messageBox("ERROR", "Price Invalid", "Price Invalid","Price entered is not a valid amount\nReason: Price is not 0 - 1000");
			return;
		}
		Service service = new Service(txtSerNam.getText(), duration, price, business.getBusinessId());
		connect.addService(service);
		program.messageBox("SUCCESS", "Service Added!", "Service Added!","Service has been added");	
		stage.close();
	}
	
	/**
	 * Cancels creating a service
	 * @author [Programmer]
	 */
	@FXML 
	public void cancelService()
	{
		Stage stage= (Stage) txtSerNam.getScene().getWindow();
		txtSerNam.setText("");
		txtSerDur.setText("");
		txtSerPri.setText("");
		stage.close();
	}

}
