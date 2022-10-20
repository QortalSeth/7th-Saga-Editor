package application

import application.controllers.MainMenu
import javafx.application.Application

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class MyApplication : Application()
{
	override fun start(primaryStage: Stage)
	{
		try
		{
			MainMenu.primaryStage = primaryStage
			val fxmlLoader = FXMLLoader(this.javaClass.getResource("controllers/fxmls/MainMenu.fxml"))
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

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch()
			// Error: Error: JavaFX runtime components are missing, and are required to run this application
		}
	}

	}

// JavaFX dir: /lib/jvm/javafx-sdk-19


/*fun main(args: Array<String>)
{
	Application.launch(MyApplication::class.java, *args)
}*/
/*
WARNING: Unsupported JavaFX configuration: classes were loaded from 'unnamed module @2c0070e0'
Graphics Device initialization failed for :  es2, sw
Error initializing QuantumRenderer: no suitable pipeline found
java.lang.RuntimeException: java.lang.RuntimeException: Error initializing QuantumRenderer: no suitable pipeline found
*/

