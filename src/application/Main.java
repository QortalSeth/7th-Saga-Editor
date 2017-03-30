package application;

import application.controllers.MainMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
	try
	{
	    MainMenu.setPrimaryStage(primaryStage);
	    FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("fxmls/MainMenu.fxml"));
	    Parent root = fxmlLoader.load();
	    primaryStage.setTitle("7th Saga Editor");
	    primaryStage.setScene(new Scene(root));
	    primaryStage.setResizable(false);
	    MainMenu controller = (MainMenu) fxmlLoader.getController();
	    controller.setMainWindow(primaryStage.getScene().getWindow());
	    primaryStage.show();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }

    public static void main(String[] args)
    {
	launch(args);
    }
}
