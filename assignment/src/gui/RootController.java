package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import program.Business;
import program.BusinessOwner;
import program.Controller;
import program.DatabaseConnection;
import program.Service;

public class RootController implements Initializable, IUser {
	
	private static Logger log = Logger.getLogger(RootController.class);
	private final Controller program = new Controller();
	private final DatabaseConnection con = new DatabaseConnection();
	
	@FXML
	ListView<Business> listviewBO;
	
	@FXML
	Button btnRefresh, btnSearch, btnDelete, btnCreate, btnLogout;
	
	@FXML
	Label lblID, lblName, lblUsername;
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		loadListView();
		listviewBO.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Business>() {
			@Override
			public void changed(ObservableValue<? extends Business> observable, Business oldValue,Business newValue) {
				if (newValue != null) {
					lblID.setText(""+newValue.getBusinessId());
					lblName.setText(newValue.getBusinessName());
				}
			}
		});
	}
	
	/**
	 * Returns User to login
	 * 
	 * @author Joseph Garner
	 */
	@FXML
	public void loadListView() {
		ArrayList<Business> arr = new ArrayList<>(con.getAllBusiness());
		ObservableList<Business> list = FXCollections.observableList(arr);
		log.debug("LOGGER: List length:" + arr.size());
		if (list != null) {
			listviewBO.setItems(list);
			listviewBO.setCellFactory(new Callback<ListView<Business>, ListCell<Business>>() {
				@Override
				public ListCell<Business> call(ListView<Business> p) {

					ListCell<Business> cell = new ListCell<Business>() {
						@Override
						protected void updateItem(Business t, boolean bln) {
							super.updateItem(t, bln);
							if (t != null) {
								setText(t.getBusinessName());
							} else {
								listviewBO.setPlaceholder(new Label("No Buisness Owners"));
							}
						}
					};
					return cell;
				}
			});
		} else {
			log.warn("Unable to load BusinessOwners");
		}
	}
	
	@Override
	public void setCI() {}
	
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
			Stage st = (Stage) btnLogout.getScene().getWindow();
			st.close();
		} else {
			return;
		}
	}
	
	@FXML
	public void refresh(){
		loadListView();
	}
	
	@FXML
	public void delete(){
		int id = listviewBO.getSelectionModel().getSelectedItem().getBusinessId();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("Delete Business?");
		alert.setContentText("Are you sure?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			con.deleteUser(id);
			refresh();
		} else {
			return;
		}
	}
	
	@FXML
	public void create(){
		try {
			Stage secondaryStage = new Stage();
			secondaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
			Parent root = FXMLLoader.load(getClass().getResource("addBOLayout.fxml"));
			secondaryStage.setTitle("Customer Application");
			secondaryStage.setMinWidth(300);
			secondaryStage.setMinHeight(200);
			secondaryStage.setMaxWidth(500);
			secondaryStage.setMaxHeight(400);
			secondaryStage.setScene(new Scene(root));
			secondaryStage.initModality(Modality.APPLICATION_MODAL);
			secondaryStage.showAndWait();
		} catch (IOException ioe) {
			log.warn(ioe.getMessage());
		}
		log.debug("false");
	}

	@Override
	public boolean getUserWindow() {
		try {
			Stage secondaryStage = new Stage();
			secondaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
			Parent root = FXMLLoader.load(getClass().getResource("rootLayout.fxml"));
			secondaryStage.setTitle("Business Application");
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
