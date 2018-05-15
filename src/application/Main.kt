package application

import application.controllers.MainMenu
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class Main : Application()
{
	override fun start(primaryStage: Stage)
	{
		try
		{
			MainMenu.primaryStage = primaryStage
			val fxmlLoader = FXMLLoader(MainMenu.javaClass.getResource("fxmls/MainMenu.fxml"))
			val root = fxmlLoader.load<Parent>()
			primaryStage.title = "7th Saga Editor"
			primaryStage.scene = Scene(root)
			primaryStage.isResizable = false
			val controller = fxmlLoader.getController<Any>() as MainMenu
			controller.setMainWindow(primaryStage.scene.window)
			primaryStage.show()
		}
		catch (e: Exception)
		{
			e.printStackTrace()
		}

	}

	companion object
	{
		@JvmStatic fun main(args: Array<String>)
		{
			//Application.launch(Main.javaClass.getResource)
			Application.launch(Main::class.java, *args)
		}


	}
}