package application.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;

import application.ControllerInitilizer;
import application.Data;
import application.ROM;
import application.excel.Excel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import application.models.lists.Lists;
import application.models.lists.states.DataState;

public class MainMenu
{

	private Window	       mainWindow;
	private static Stage   primaryStage;
    boolean		           debug;

    @FXML
    void initialize()
    {
	this.disableButtons(true);
	debug = false;
	initializeImages();
	ROM.setProgramDirectory();
    }

    private void disableButtons(boolean disable)
    {
	save.setDisable(disable);
	character.setDisable(disable);
	apprentice.setDisable(disable);
	spell.setDisable(disable);
	equipment.setDisable(disable);
	shop.setDisable(disable);
	monster.setDisable(disable);
    }

    private void initializeImages()
    {

	open.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/Open Icon2.png"))));
	save.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/save icon.png"))));
	character.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/Lemele.png"))));
	apprentice.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/Lejes.png"))));
	spell.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/Laser 3.png"))));
	equipment.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/Weapon & Armor Shop Characters 2.png"))));
	shop.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/Item Shop Character.png"))));
	ImageView i = (ImageView) monster.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Brain.png")));
	sceneryImage.setImage(new Image(this.getClass().getResourceAsStream("images/Scenery Icon.png")));
	sceneryImage.fitWidthProperty().bind(primaryStage.widthProperty());
	sceneryImage.fitHeightProperty().bind(primaryStage.heightProperty());
	primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("images/Lux.png")));
    }

    @FXML
    void getFileToOpen(MouseEvent event) throws IOException
    {
	FileChooser fc = new FileChooser();
	fc.setTitle("Choose file to open");
	fc.getExtensionFilters().add(new ExtensionFilter("SNES Files", "*.smc"));
	fc.setInitialDirectory(ROM.getProgramDirectory());

	File rom = fc.showOpenDialog(null);

	if (rom != null)
	{
	    this.disableButtons(false);

	    ROM.setROMDirectory(rom.getParentFile());
		ROM.openDefaultROM();
		ROM.openROM(rom);
		System.out.println("ROM directory is: "+ROM.getROMDirectory());
	}
    }



    @FXML
    void writeDataToDisk()
    {
	    ROM.saveROM();
    }

    @FXML
    void characterEditor()
    {
	this.openDialog("fxmls/CharacterEditor.fxml", "Character Editor", true);
    }

    @FXML
    void apprenticeEditor()
    {
	this.openDialog("fxmls/ApprenticeEditor.fxml", "Apprentice Editor", false);
    }

    @FXML
    void spellEditor()
    {
	this.openDialog("fxmls/SpellEditor.fxml", "Spell Editor", false);
    }

    @FXML
    void equipmentEditor()
    {
	this.openDialog("fxmls/EquipmentEditor.fxml", "Equipment Editor", false);
    }

    @FXML
    void shopEditor()
    {
	this.openDialog("fxmls/ShopEditor.fxml", "Shop Editor", false);
    }

    @FXML
    void monsterEditor()
    {
	this.openDialog("fxmls/MonsterEditor.fxml", "Monster Editor", false);
    }

    public void openDialog(String filename, String title, boolean resizable)
    {
	try
	{
	    // Load the fxml file and create a new stage for the popup dialog.
	    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(filename));

	    Parent root = fxmlLoader.load();
	    final ControllerInitilizer controller = fxmlLoader.getController();

	    // Create the dialog Stage.
	    Stage editorStage = new Stage();
	    editorStage.initOwner(mainWindow);
	    editorStage.setTitle(title);
	    // editorStage.initStyle(StageStyle.);

	    editorStage.initModality(Modality.WINDOW_MODAL);
	    editorStage.setResizable(true);
	    Scene scene = new Scene(root);
	    editorStage.setScene(scene);

	    editorStage.setOnCloseRequest(new EventHandler<WindowEvent>()
	    {
		@Override
		public void handle(WindowEvent arg0)
		{
		    controller.saveData();
		    controller.saveState();

		}
	    });
	    editorStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
	    {

		@Override
		public void handle(KeyEvent event)
		{
		    final KeyCodeCombination kb = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
		    if (kb.match(event))
		    {
			System.out.println("data saved from main menu");
			controller.saveData();
			writeDataToDisk();
		    }
		}

	    });

	    // Show the dialog and wait until the user closes it
	    double height = primaryStage.getHeight();
	    double width = primaryStage.getWidth();
	    primaryStage.hide();
	    editorStage.showAndWait();
	    editorStage = null;
	    primaryStage.setHeight(height);
	    primaryStage.setWidth(width);
	    primaryStage.show();

	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
    }



    public void setMainWindow(Window mainWindow)
    {
	this.mainWindow = mainWindow;
    }


    public static Stage getPrimaryStage()
    {
	return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage)
    {
	MainMenu.primaryStage = primaryStage;
    }

    @FXML
    private ResourceBundle resources;
    @FXML
    private ImageView	   sceneryImage;
    @FXML
    private URL		   location;
    @FXML
    private Button	   spell;
    @FXML
    private Button	   character;
    @FXML
    private Button	   apprentice;
    @FXML
    private Button	   shop;
    @FXML
    private Button	   save;
    @FXML
    private Button	   equipment;
    @FXML
    private AnchorPane	   mainMenuPane;
    @FXML
    private Button	   open;
    @FXML
    private Button	   monster;
}
