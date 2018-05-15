package application.controllers

import application.ControllerInitilizer
import application.ROM
import application.Settings
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.input.KeyEvent
import javafx.stage.*
import javafx.stage.FileChooser.ExtensionFilter
import java.io.IOException

class MainMenu
{

	private lateinit var mainWindow: Window
	internal var debug: Boolean = false

	@FXML fun initialize()
	{
		this.disableButtons(true)
		initializeImages()
		ROM.setProgramDirectory()
		ROM.settings.readSettings()
	}

	private fun disableButtons(disable: Boolean)
	{
		save.isDisable = disable
		character.isDisable = disable
		apprentice.isDisable = disable
		spell.isDisable = disable
		equipment.isDisable = disable
		shop.isDisable = disable
		monster.isDisable = disable
		text.isDisable = disable
	}

	private fun initializeImages()
	{

		open.graphic = ImageView(Image(this.javaClass.getResourceAsStream("images/Open_Icon2.png")))
		save.graphic = ImageView(Image(this.javaClass.getResourceAsStream("images/save_icon.png")))
		character.graphic = ImageView(Image(this.javaClass.getResourceAsStream("images/Lemele.png")))
		apprentice.graphic = ImageView(Image(this.javaClass.getResourceAsStream("images/Lejes.png")))
		spell.graphic = ImageView(Image(this.javaClass.getResourceAsStream("images/Laser_3.png")))
		equipment.graphic = ImageView(Image(this.javaClass.getResourceAsStream("images/Weapon_and_Armor_Shop_Characters_2.png")))
		shop.graphic = ImageView(Image(this.javaClass.getResourceAsStream("images/Item_Shop_Character.png")))
		val i = monster.graphic as ImageView
		i.image = Image(this.javaClass.getResourceAsStream("images/Brain.png"))
		sceneryImage.image = Image(this.javaClass.getResourceAsStream("images/Scenery_Icon.png"))
		sceneryImage.fitWidthProperty().bind(primaryStage!!.widthProperty())
		sceneryImage.fitHeightProperty().bind(primaryStage!!.heightProperty())
		primaryStage!!.icons.add(Image(this.javaClass.getResourceAsStream("images/Lux.png")))
	}

	@FXML @Throws(IOException::class) fun getFileToOpen()
	{
		val fc = FileChooser()
		fc.title = "Choose file to open"
		fc.extensionFilters.add(ExtensionFilter("SNES Files", "*.smc"))
		fc.initialDirectory = ROM.programDirectory

		val rom = fc.showOpenDialog(null)

		if (rom != null)
		{
			disableButtons(false)

			ROM.romDirectory = rom.parentFile
			if(debug)
			{ROM.openDefaultROM()}
			ROM.openROM(rom)
			System.out.println("ROM directory is: ${ROM.romDirectory.canonicalPath}")
		}
	}


	@FXML fun writeDataToDisk() = ROM.saveROM()
	@FXML fun characterEditor() = openDialog("fxmls/CharacterEditor.fxml", "Character Editor", true)
	@FXML fun apprenticeEditor() = openDialog("fxmls/ApprenticeEditor.fxml", "Apprentice Editor", false)
	@FXML fun spellEditor() = openDialog("fxmls/SpellEditor.fxml", "Spell Editor", false)
	@FXML fun equipmentEditor() = openDialog("fxmls/EquipmentEditor.fxml", "Equipment Editor", false)
	@FXML fun shopEditor() = openDialog("fxmls/ShopEditor.fxml", "Shop Editor", false)
	@FXML fun monsterEditor() = openDialog("fxmls/MonsterEditor.fxml", "Monster Editor", false)
	@FXML fun textEditor() = openDialog("fxmls/TextEditor.fxml", "Text Editor", false)
	@FXML fun settingsEditor() = openDialog("fxmls/Settings.fxml", "Settings Editor", false)

	fun openDialog(filename: String, title: String, resizable: Boolean)
	{
		try
		{
			// Load the fxml file and create a new stage for the popup dialog.
			val fxmlLoader = FXMLLoader(this.javaClass.getResource(filename))

			val root = fxmlLoader.load<Parent>()
			val controller = fxmlLoader.getController<ControllerInitilizer>()

			// Create the dialog Stage.
			val editorStage: Stage? = Stage()
			editorStage!!.initOwner(mainWindow)
			editorStage.title = title
			// editorStage.initStyle(StageStyle.);

			editorStage.initModality(Modality.WINDOW_MODAL)
			editorStage.isResizable = true
			val scene = Scene(root)
			editorStage.scene = scene

			editorStage.onCloseRequest = EventHandler<WindowEvent> {
				controller.saveData()
				controller.saveState()
			}
			editorStage.addEventHandler(KeyEvent.KEY_PRESSED) { event ->
				val kb = KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)
				if (kb.match(event))
				{
					println("data saved from main menu")
					controller.saveData()
					writeDataToDisk()
				}
			}

			// Show the dialog and wait until the user closes it
			val height = primaryStage!!.height
			val width = primaryStage!!.width
			primaryStage!!.hide()
			editorStage.showAndWait()
			primaryStage!!.height = height
			primaryStage!!.width = width
			primaryStage!!.show()

		}
		catch (e: IOException)
		{
			e.printStackTrace()
		}

	}

	fun setMainWindow(mainWindow: Window)
	{
		this.mainWindow = mainWindow
	}

	@FXML lateinit var sceneryImage: ImageView
	@FXML lateinit var spell: Button
	@FXML lateinit var character: Button
	@FXML lateinit var apprentice: Button
	@FXML lateinit var shop: Button
	@FXML lateinit var save: Button
	@FXML lateinit var equipment: Button
	@FXML lateinit var open: Button
	@FXML lateinit var monster: Button
	@FXML lateinit var text: Button
	@FXML lateinit var settings: Button

	companion object
	{
		var primaryStage: Stage? = null
	}
}