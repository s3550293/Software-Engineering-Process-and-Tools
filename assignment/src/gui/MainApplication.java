package gui;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import program.Main;

public class MainApplication extends Application {

	static final Logger log = Logger.getLogger(Main.class);
    public MainApplication(){}
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainLayout.fxml"));
        primaryStage.setTitle("Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("images/ic_collections_bookmark_black_48dp_2x.png"));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(650);
        primaryStage.setMaxWidth(1000);
        primaryStage.setMaxHeight(850);
        primaryStage.show();

    }
}
