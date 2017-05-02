package gui;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import program.BusinessMenu;
import program.Database;
import program.DatabaseConnection;
import program.Service;

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
        File varTmpDir = new File(System.getProperty("user.home")+"/resourcing");
        if(varTmpDir.exists() == false) {
            new File(System.getProperty("user.home")+"/resourcing").mkdir();
        }
        File varTmpData = new File(System.getProperty("user.home")+"/resourcing"+"/company.db");
        if(varTmpData.exists() == false) {
        	ini();
        }
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
		BusinessMenu bMenu = new BusinessMenu();
		connect.addUser("admin","Monday10!",1);
		connect.addUser("William", "Apples22", 0);
		connect.addUser("Hannah", "Apples22", 0);
		
		connect.addEmployee("Luke Charles",25);
		connect.addEmployee("David Smith",26.6);
		connect.addEmployee("Will Turner",15);
		connect.addEmployee("Rob Pointer",14);
		connect.addEmployee("Adam Mason",12);
		connect.addEmployee("David Chang",17);
		connect.addEmployee("Joseph Tun",17);
		connect.addEmployee("Casey Pointer",17);
		connect.addEmployee("Danyon Glenk",10);
		connect.addEmployee("Justin Lui",24);
		connect.addEmployee("Jan Misso",15.7);
		connect.addEmployee("Harry Nancarrow",19);
		
		bMenu.set7DayRosterTime(1,"03/04/2017","08:00","16:00");
		bMenu.set7DayRosterTime(1,"04/04/2017","08:00","16:00");
		bMenu.set7DayRosterTime(1,"05/04/2017","08:00","12:00");
		bMenu.set7DayRosterTime(1,"06/04/2017","08:00","12:00");
		bMenu.set7DayRosterTime(1,"07/04/2017","08:00","12:00");
		bMenu.set7DayRosterTime(1,"08/04/2017","12:00","20:00");
		bMenu.set7DayRosterTime(1,"09/04/2017","08:00","16:00");
		
		bMenu.set7DayRosterTime(2,"03/04/2017","08:00","16:00");
		bMenu.set7DayRosterTime(2,"04/04/2017","08:00","16:00");
		bMenu.set7DayRosterTime(2,"05/04/2017","08:00","12:00");
		bMenu.set7DayRosterTime(2,"06/04/2017","08:00","12:00");
		bMenu.set7DayRosterTime(2,"07/04/2017","08:00","12:00");
		bMenu.set7DayRosterTime(2,"08/04/2017","12:00","20:00");
		bMenu.set7DayRosterTime(2,"09/04/2017","08:00","16:00");
		
		bMenu.set7DayRosterTime(3,"03/04/2017","08:00","16:00");
		bMenu.set7DayRosterTime(3,"04/04/2017","08:00","16:00");
		bMenu.set7DayRosterTime(3,"05/04/2017","08:00","12:00");
		bMenu.set7DayRosterTime(3,"06/04/2017","08:00","12:00");
		bMenu.set7DayRosterTime(3,"07/04/2017","08:00","12:00");
		bMenu.set7DayRosterTime(3,"08/04/2017","12:00","20:00");
		bMenu.set7DayRosterTime(3,"09/04/2017","08:00","16:00");
		
		bMenu.set7DayRosterTime(4,"06/04/2017","12:00","16:00");
		bMenu.set7DayRosterTime(4,"07/04/2017","12:00","20:00");
		
		bMenu.set7DayRosterTime(5,"05/04/2017","08:00","16:00");
		bMenu.set7DayRosterTime(5,"10/04/2017","08:00","20:00");
		
		bMenu.set7DayRosterTime(6,"03/04/2017","08:00","16:00");
		bMenu.set7DayRosterTime(6,"04/04/2017","08:00","16:00");
		bMenu.set7DayRosterTime(6,"05/04/2017","08:00","12:00");
		bMenu.set7DayRosterTime(6,"06/04/2017","08:00","12:00");
		bMenu.set7DayRosterTime(6,"07/04/2017","08:00","12:00");
		bMenu.set7DayRosterTime(6,"08/04/2017","12:00","20:00");
		bMenu.set7DayRosterTime(6,"09/04/2017","08:00","16:00");
		
		bMenu.set7DayRosterTime(7,"05/04/2017","08:00","16:00");
		bMenu.set7DayRosterTime(7,"10/04/2017","08:00","20:00");
		
		bMenu.set7DayRosterTime(10,"06/04/2017","12:00","16:00");
		bMenu.set7DayRosterTime(10,"07/04/2017","12:00","20:00");
		
		connect.addUserDetails(2, "William", "Porter", "will@mail.com", "0452368593", "01/01/2002", "Male");
		connect.addUserDetails(3, "Hannah", "Hawlking", "hannah@mail.com", "0452136859", "20/04/1995", "Famale");

		connect.addSerice(new Service("Trim",30,25));
		connect.addSerice(new Service("Wash",45,30));
		connect.addSerice(new Service("Color",50,90));
		connect.addSerice(new Service("Cut and Style",60,60));
		
		connect.addBooking(2,1, "21/04/2017", "10:00", "10:40", 1,"active");
		connect.addBooking(2,2, "22/04/2017", "11:00", "11:59", 2,"canceled");
		connect.addBooking(2,1, "23/04/2017", "10:00", "10:40", 3,"canceled");
		connect.addBooking(2,2, "24/04/2017", "11:00", "11:59", 2,"canceled");
		connect.addBooking(2,3, "25/04/2017", "8:00", "8:40", 2, "active");
		connect.addBooking(2,2, "26/04/2017", "8:00", "8:40", 2, "canceled");
		connect.addBooking(2,3, "27/04/2017", "10:00", "10:40", 1,"active");
		connect.addBooking(2,2, "28/04/2017", "11:00", "11:59", 1,"active");
		connect.addBooking(2,3, "29/04/2017", "8:00", "8:40", 1, "canceled");
		
		connect.addBooking(2,1, "24/05/2017", "8:00", "8:40", 2, "active");
		connect.addBooking(3,1, "16/07/2017", "10:00", "10:40", 3,"active");
		connect.addBooking(3,2, "14/05/2017", "11:00", "11:59", 1,"active");
		connect.addBooking(3,1, "4/05/2017", "10:00", "10:40", 2,"active");
		connect.addBooking(3,2, "6/05/2017", "11:00", "11:59", 3,"active");
		


		
		//connect.cancelBooking(2);
    }

}
