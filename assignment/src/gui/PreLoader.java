package gui;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import program.Database;
import program.DatabaseConnection;

import java.io.File;

public class PreLoader extends Preloader{
    ProgressBar bar;
    Stage stage;

    private Scene createPreloaderScene() {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        return new Scene(p, 300, 150);
    }

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createPreloaderScene());
        stage.show();
        File varTmpData = new File("db/company.db");
        if(varTmpData.exists() == false) {
        	ini();
        }
        /*
        File varTmpDir = new File(System.getProperty("user.home")+"/resourcing");
        if(varTmpDir.exists() == false) {
            new File(System.getProperty("user.home")+"/resourcing").mkdir();
        }
        */
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        bar.setProgress(pn.getProgress());
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }
    
    private void ini()
    {
    	Database db = new Database("company.db");
		db.createTable("company.db");
		DatabaseConnection connect = new DatabaseConnection();
		connect.addUser("William", "Apples22", 0);
		connect.addUser("admin","Monday10!",1);
		connect.addEmployee("Luke Charles",100);
		connect.addEmployee("David Smith",100);
		connect.addEmployeeWorkingTime(1,"05/04/2017","9:00","17:00");
		connect.addEmployeeWorkingTime(1,"10/04/2017","8:00","14:00");

		connect.addEmployeeWorkingTime(2,"06/04/2017","10:30","12:30");
		connect.addEmployeeWorkingTime(2,"07/03/2017","11:30","15:30");
		connect.addBooking(1, "11/04/2017", "10:00", "11:00",0, "active");
		connect.addBooking(2, "15/04/2017", "11:00", "12:00",0, "active");
		connect.cancelBooking(4);
    }

}
