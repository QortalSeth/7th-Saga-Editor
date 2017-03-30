package controllers;

import application.ControllerInitilizer;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import models.Armor;
import models.Item;
import models.Weapon;
import models.lists.Armors;
import models.lists.Items;
import models.lists.Lists;
import models.lists.Weapons;
import staticClasses.Listeners;

public class EquipmentEditor implements ControllerInitilizer
{
    private Item	    previouslySelectedItem;
    private Weapon	    previouslySelectedWeapon;
    private Armor	    previouslySelectedArmor;
    private Items	    items;
    private Weapons	    weapons;
    private Armors	    armors;
    private Armors	    accessories;

    private static int	    startItem;
    private static int	    startWeapon;
    private static int	    startArmor;
    private static int	    startAccessory;
    private static int	    startWeaponSort;
    private static int	    startArmorSort;
    private Tab		    previouslySelectedTab;
    private ComboBox<Armor> previouslySelectedArmorComboBox;
    private static int	    startTab;
    private static int	    startArmorCombobox;

    @FXML
    private TabPane	    tabPane;

    CheckBox[]		    users  = new CheckBox[7];
    CheckBox[]		    Dusers = new CheckBox[7];

    @FXML
    void setSelectedItem()
    {
	Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
	if (selectedTab != itemTab) { return; }

	this.saveItems();
	Item item = itemsC.getSelectionModel().getSelectedItem();
	// System.out.println("Selected item is: "+item.getName());
	Item Ditem = items.getDModel(item);

	previouslySelectedItem = item;

	int target = item.getTarget();
	if (target == 4)
	{
	    target--;
	}
	targetC.getSelectionModel().select(target);
	usesC.getSelectionModel().select(item.getUses());
	itemCostT.setText(Integer.toString((item.getCost())));
	sellRatioC.getSelectionModel().select(item.getSellRatio());
	this.selectUsers(item.getUsers(), users);

	int Dtarget = Ditem.getTarget();
	if (Dtarget == 4)
	{
	    Dtarget--;
	}
	DtargetT.setText(targetC.getItems().get(Dtarget));
	DusesT.setText(usesC.getItems().get(Ditem.getUses()));
	DitemCostT.setText(Integer.toString(Ditem.getCost()));
	DsellRatioT.setText(sellRatioC.getItems().get(Ditem.getSellRatio()));
	this.selectUsers(Ditem.getUsers(), Dusers);

    }

    private void saveItems()
    {
	if (previouslySelectedTab != itemTab) { return; }
	Item item = previouslySelectedItem;
	if (item == null) { return; }

	// System.out.println("Saving Item: "+item.getName());
	int target = targetC.getSelectionModel().getSelectedIndex();
	if (target == 3)
	{
	    target++;
	}

	item.setTarget(target);
	item.setUses(usesC.getSelectionModel().getSelectedIndex());
	item.setCost(Integer.parseInt(itemCostT.getText()));
	item.setSellRatio(sellRatioC.getSelectionModel().getSelectedIndex());
	item.setUsers(this.getUsers(users));
    }

    @FXML
    void setSelectedWeapon()
    {
	Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
	if (selectedTab != weaponTab) { return; }

	this.saveWeapons();
	Weapon weapon = weaponsC.getSelectionModel().getSelectedItem();
	if (weapon == null) { return; }
	Weapon Dweapon = weapons.getDModel(weapon);

	// System.out.println("Selected weapon is: "+weapon.getName());
	previouslySelectedWeapon = weapon;
	weaponPowerT.setText(Integer.toString(weapon.getPower()));
	weaponCostT.setText(Integer.toString(weapon.getCost()));
	weaponDiscountT.setText(Integer.toString(weapon.getDiscount()));
	this.selectUsers(weapon.getEquipCode(), users);

	DweaponPowerT.setText(Integer.toString(Dweapon.getPower()));
	DweaponCostT.setText(Integer.toString(Dweapon.getCost()));
	DweaponDiscountT.setText(Integer.toString(Dweapon.getDiscount()));

	this.selectUsers(Dweapon.getEquipCode(), Dusers);
    }

    private void saveWeapons()
    {
	if (previouslySelectedTab != weaponTab) { return; }

	Weapon w = previouslySelectedWeapon;
	if (w == null) { return; }
	// System.out.println("Saving Weapon: "+w.getName());
	w.setPower(Integer.parseInt(weaponPowerT.getText()));
	w.setCost(Integer.parseInt(weaponCostT.getText()));
	w.setEquipCode(this.getUsers(users));
	w.setUnknown(Integer.parseInt(weaponDiscountT.getText()));

    }

    @FXML
    void sortWeapons()
    {
	int characterEquipCode;
	this.saveData();

	if (weaponSortC.getSelectionModel().getSelectedIndex() == 0) // if
								     // character
								     // index =
								     // ALL
	{
	    characterEquipCode = 0xFF;
	}
	else
	{
	    characterEquipCode = (int) Math.pow(2.0, weaponSortC.getSelectionModel().getSelectedIndex() - 1.0);
	}

	weapons.clear();
	weapons.addUsefulModels(Lists.getWeapons(), characterEquipCode);
	weapons.sortByAscendingPower();
	weaponsC.getSelectionModel().select(0);
    }

    @FXML
    void setSelectedArmor()
    {
	Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
	if (selectedTab != armorTab) { return; }
	ComboBox<Armor> source = previouslySelectedArmorComboBox;
	this.saveArmors();
	Armor armor = source.getSelectionModel().getSelectedItem();

	// System.out.println("Selected armor is: " + armor.getName());
	highlightSelectedArmorComboBox();

	if (armor == null) { return; }
	Armor Darmor = armors.getDModel(armor);
	// System.out.println(armor.toString());

	previouslySelectedArmor = armor;
	armorPowerT.setText(Integer.toString(armor.getPower()));
	armorCostT.setText(Integer.toString(armor.getCost()));
	laserResT.setText(Integer.toString(armor.getLaserRes()));
	unknown1ResT.setText(Integer.toString(armor.getUnknownRes1()));
	unknown2ResT.setText(Integer.toString(armor.getUnknownRes2()));
	fireResT.setText(Integer.toString(armor.getFireRes()));
	iceResT.setText(Integer.toString(armor.getIceRes()));
	vacuumResT.setText(Integer.toString(armor.getVacuumRes()));
	debuffResT.setText(Integer.toString(armor.getDebuffRes()));
	armorDiscountT.setText(Integer.toString(armor.getDiscount()));

	DarmorPowerT.setText(Integer.toString(Darmor.getPower()));
	DarmorCostT.setText(Integer.toString(Darmor.getCost()));
	DlaserResT.setText(Integer.toString(Darmor.getLaserRes()));
	Dunknown1ResT.setText(Integer.toString(Darmor.getUnknownRes1()));
	Dunknown2ResT.setText(Integer.toString(Darmor.getUnknownRes2()));
	DfireResT.setText(Integer.toString(Darmor.getFireRes()));
	DiceResT.setText(Integer.toString(Darmor.getIceRes()));
	DvacuumResT.setText(Integer.toString(Darmor.getVacuumRes()));
	DdebuffResT.setText(Integer.toString(Darmor.getDebuffRes()));
	DarmorDiscountT.setText(Integer.toString(Darmor.getDiscount()));

	this.selectUsers(armor.getEquipCode(), users);
	this.selectUsers(Darmor.getEquipCode(), Dusers);
    }

    private void saveArmors()
    {
	if (previouslySelectedTab != armorTab) { return; }
	Armor a = previouslySelectedArmor;
	if (a == null) { return; }
	System.out.println("Saving Armor: " + a.getName());
	a.setPower(Integer.parseInt(armorPowerT.getText()));
	a.setCost((Integer.parseInt(armorCostT.getText())));
	a.setLaserRes((Integer.parseInt(laserResT.getText())));
	a.setUnknownRes1((Integer.parseInt(unknown1ResT.getText())));
	a.setUnknownRes2((Integer.parseInt(unknown2ResT.getText())));
	a.setFireRes((Integer.parseInt(fireResT.getText())));
	a.setIceRes((Integer.parseInt(iceResT.getText())));
	a.setVacuumRes((Integer.parseInt(vacuumResT.getText())));
	a.setDebuffRes((Integer.parseInt(debuffResT.getText())));
	a.setUnknown((Integer.parseInt(armorDiscountT.getText())));
	a.setEquipCode(this.getUsers(users));
    }

    @FXML
    void sortArmors()
    {
	this.saveData();

	int characterEquipCode;
	if (armorSortC.getSelectionModel().getSelectedIndex() == 0) // if
								    // character
								    // index =
								    // ALL
	{
	    characterEquipCode = 0xFF;
	}
	else
	{
	    characterEquipCode = (int) Math.pow(2.0, armorSortC.getSelectionModel().getSelectedIndex() - 1.0);
	}

	accessories.clear();
	accessories.addUsefulAccessories(Lists.getArmors(), characterEquipCode);
	accessories.sortByAscendingPower();
	accessoriesC.getSelectionModel().select(0);

	armors.clear();
	armors.addUsefulBodyArmors(Lists.getArmors(), characterEquipCode);
	armors.sortByAscendingPower();
	armorsC.getSelectionModel().select(0);
    }

    private void highlightSelectedArmorComboBox()
    {
	ComboBox<Armor> source = previouslySelectedArmorComboBox;
	if (source == armorsC)
	{
	    armorsC.setStyle("-fx-border-color:-fx-focus-color;");
	    accessoriesC.setStyle("-fx-border-color: transparent;");
	}
	else if (source == accessoriesC)
	{
	    accessoriesC.setStyle("-fx-border-color:-fx-focus-color;");
	    armorsC.setStyle("-fx-border-color: transparent;");
	}
	else
	{
	    System.out.println("source is NULL");
	    ;
	}
    }

    @FXML
    void saveTab()
    {

	Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
	if (previouslySelectedTab == null)
	{
	    return;
	}
	else if (previouslySelectedTab == itemTab)
	{
	    this.saveItems();
	}
	else if (previouslySelectedTab == weaponTab)
	{
	    this.saveWeapons();
	}
	else if (previouslySelectedTab == armorTab)
	{
	    this.saveArmors();
	}

	// System.out.println("previouslySelectedTab is: "
	// +previouslySelectedTab.getText());
	// System.out.println("selectedTab is: " +selectedTab.getText());

	if (selectedTab == itemTab)
	{
	    this.setSelectedItem();
	}

	if (selectedTab == weaponTab)
	{
	    this.setSelectedWeapon();
	}

	if (selectedTab == armorTab)
	{
	    this.setSelectedArmor();
	}

	previouslySelectedTab = selectedTab;
    }

    @FXML
    void initialize()
    {
	if (startArmorCombobox == 0)
	{
	    previouslySelectedArmorComboBox = armorsC;
	}
	if (startArmorCombobox == 1)
	{
	    previouslySelectedArmorComboBox = accessoriesC;
	}

	this.addChangeListenerToTextFields();
	this.initializeComboboxes();
	this.setSelectedWeapon();
	this.setSelectedArmor();
	this.setSelectedItem();

	ImageView i = (ImageView) itemUp.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));
	i = (ImageView) itemDown.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));

	i = (ImageView) weaponUp.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));
	i = (ImageView) weaponDown.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));

	i = (ImageView) armorUp.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));
	i = (ImageView) armorDown.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));

	i = (ImageView) accessoryUp.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));
	i = (ImageView) accessoryDown.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));

	if (previouslySelectedTab == null)
	{
	    previouslySelectedTab = itemTab;
	}

	tabPane.getSelectionModel().select(startTab);
    }

    private void initializeComboboxes()
    {
	items = new Items(Lists.getItems(), false);
	itemsC.setItems(items.getModels());
	itemsC.getSelectionModel().select(startItem);

	targetC.getItems().add("Single Enemy");
	targetC.getItems().add("All Enemies");
	targetC.getItems().add("Ally");
	targetC.getItems().add("Map");

	usesC.getItems().add("Reusable");
	usesC.getItems().add("Destroy on Use");

	sellRatioC.getItems().add("50%");
	sellRatioC.getItems().add("100%");

	ObservableList<String> equipSortValues = FXCollections.observableArrayList();
	equipSortValues.add("All");
	equipSortValues.add("Kamil");
	equipSortValues.add("Olvan");
	equipSortValues.add("Esuna");
	equipSortValues.add("Wilme");
	equipSortValues.add("Lux");
	equipSortValues.add("Valsu");
	equipSortValues.add("Lejes");
	weaponSortC.setItems(equipSortValues);
	armorSortC.setItems(equipSortValues);

	weaponSortC.getSelectionModel().select(startWeaponSort);
	armorSortC.getSelectionModel().select(startArmorSort);

	users[0] = kamilCH;
	users[1] = olvanCH;
	users[2] = esunaCH;
	users[3] = wilmeCH;
	users[4] = luxCH;
	users[5] = valsuCH;
	users[6] = lejesCH;

	Dusers[0] = DKamilCH;
	Dusers[1] = DOlvanCH;
	Dusers[2] = DEsunaCH;
	Dusers[3] = DWilmeCH;
	Dusers[4] = DLuxCH;
	Dusers[5] = DValsuCH;
	Dusers[6] = DLejesCH;

	weapons = new Weapons(Lists.getWeapons(), 0xFF);
	armors = new Armors(Lists.getArmors(), 0xFF);
	accessories = new Armors(Lists.getArmors(), 0xFF);

	sortWeapons();
	sortArmors();
	weaponsC.setItems(weapons.getModels());
	armorsC.setItems(armors.getModels());
	accessoriesC.setItems(accessories.getModels());

	weaponsC.getSelectionModel().select(startWeapon);
	accessoriesC.getSelectionModel().select(startAccessory);
	armorsC.getSelectionModel().select(startArmor);

	ChangeListener c = new ChangeListener()
	{

	    @Override
	    public void changed(ObservableValue observable, Object oldValue, Object newValue)
	    {

		ReadOnlyBooleanProperty a = (ReadOnlyBooleanProperty) observable;
		ComboBox<Armor> comboBox = (ComboBox<Armor>) a.getBean();
		previouslySelectedArmorComboBox = comboBox;
		setSelectedArmor();
	    }
	};
	armorsC.focusedProperty().addListener(c);
	accessoriesC.focusedProperty().addListener(c);
    }

    private void selectUsers(int users, CheckBox[] characters)
    {
	System.out.println("users to select: " + users);
	for (CheckBox c : characters)
	{
	    c.setSelected(false);
	}

	if ((users & 0x01) > 0) characters[0].setSelected(true);
	if ((users & 0x02) > 0) characters[1].setSelected(true);
	if ((users & 0x04) > 0) characters[2].setSelected(true);
	if ((users & 0x08) > 0) characters[3].setSelected(true);
	if ((users & 0x10) > 0) characters[4].setSelected(true);
	if ((users & 0x20) > 0) characters[5].setSelected(true);
	if ((users & 0x40) > 0) characters[6].setSelected(true);
    }

    private int getUsers(CheckBox[] characters)
    {
	int users = 0;

	if (characters[0].isSelected()) users |= 0x01;
	if (characters[1].isSelected()) users |= 0x02;
	if (characters[2].isSelected()) users |= 0x04;
	if (characters[3].isSelected()) users |= 0x08;
	if (characters[4].isSelected()) users |= 0x10;
	if (characters[5].isSelected()) users |= 0x20;
	if (characters[6].isSelected()) users |= 0x40;

	return users;
    }

    @FXML
    void removeNonNumbers(KeyEvent event)
    {
	Listeners.removeNonNumbers(event, null);
    }

    @FXML
    void incrementWeapon()
    {
	weaponsC.getSelectionModel().selectNext();
    }

    @FXML
    void decrementWeapon()
    {
	weaponsC.getSelectionModel().selectPrevious();
    }

    @FXML
    void incrementItem()
    {
	itemsC.getSelectionModel().selectNext();
    }

    @FXML
    void decrementItem()
    {
	itemsC.getSelectionModel().selectPrevious();
    }

    @FXML
    void incrementArmor()
    {
	previouslySelectedArmorComboBox = armorsC;
	armorsC.getSelectionModel().selectNext();
	armorsC.requestFocus();
    }

    @FXML
    void decrementArmor()
    {
	previouslySelectedArmorComboBox = armorsC;
	armorsC.getSelectionModel().selectPrevious();
	setSelectedArmor();
	armorsC.requestFocus();
    }

    @FXML
    void incrementAccessory()
    {
	previouslySelectedArmorComboBox = accessoriesC;
	accessoriesC.getSelectionModel().selectNext();
	accessoriesC.requestFocus();
    }

    @FXML
    void decrementAccessory()
    {
	previouslySelectedArmorComboBox = accessoriesC;
	accessoriesC.getSelectionModel().selectPrevious();
	accessoriesC.requestFocus();
    }

    @Override
    public void saveData()
    {
	this.saveTab();
    }

    private void addChangeListenerToTextFields()
    {
	itemCostT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535));
	weaponPowerT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535));
	weaponCostT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535));
	weaponDiscountT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535));
	armorPowerT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535));
	armorCostT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535));
	laserResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	unknown1ResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	unknown2ResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	fireResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	iceResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	vacuumResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	debuffResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	armorDiscountT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535));
    }

    @FXML
    private CheckBox	     olvanCH;

    @FXML
    private CheckBox	     wilmeCH;

    @FXML
    private TextField	     laserResT;

    @FXML
    private TextField	     armorCostT;

    @FXML
    private TextField	     itemCostT;

    @FXML
    private TextField	     weaponCostT;

    @FXML
    private ComboBox<String> usesC;

    @FXML
    private TextField	     debuffResT;

    @FXML
    private ComboBox<String> sellRatioC;

    @FXML
    private TextField	     fireResT;

    @FXML
    private TextField	     iceResT;

    @FXML
    private CheckBox	     lejesCH;

    @FXML
    private CheckBox	     luxCH;

    @FXML
    private TextField	     weaponPowerT;
    @FXML
    private TextField	     DweaponPowerT;
    @FXML
    private TextField	     DweaponCostT;
    @FXML
    private TextField	     DweaponDiscountT;
    @FXML
    private TextField	     armorDiscountT;

    @FXML
    private CheckBox	     valsuCH;

    @FXML
    private ComboBox<Weapon> weaponsC;

    @FXML
    private ComboBox<String> weaponSortC;

    @FXML
    private ComboBox<String> armorSortC;

    @FXML
    private CheckBox	     esunaCH;
    @FXML
    private CheckBox	     DEsunaCH;
    @FXML
    private CheckBox	     DKamilCH;
    @FXML
    private CheckBox	     DOlvanCH;
    @FXML
    private CheckBox	     DWilmeCH;
    @FXML
    private CheckBox	     DLuxCH;
    @FXML
    private CheckBox	     DValsuCH;
    @FXML
    private CheckBox	     DLejesCH;
    @FXML
    private TextField	     vacuumResT;

    @FXML
    private TextField	     weaponDiscountT;

    @FXML
    private CheckBox	     kamilCH;

    @FXML
    private TextField	     armorPowerT;
    @FXML
    private TextField	     DarmorPowerT;
    @FXML
    private TextField	     DarmorCostT;
    @FXML
    private TextField	     DlaserResT;
    @FXML
    private TextField	     Dunknown1ResT;
    @FXML
    private TextField	     Dunknown2ResT;
    @FXML
    private TextField	     DfireResT;
    @FXML
    private TextField	     DiceResT;
    @FXML
    private TextField	     DdebuffResT;
    @FXML
    private TextField	     DvacuumResT;
    @FXML
    private TextField	     DarmorDiscountT;

    @FXML
    private TextField	     unknown2ResT;

    @FXML
    private ComboBox<String> targetC;
    @FXML
    private TextField	     DtargetT;
    @FXML
    private TextField	     DusesT;
    @FXML
    private TextField	     DitemCostT;
    @FXML
    private TextField	     DsellRatioT;

    @FXML
    private ComboBox<Armor>  armorsC;
    @FXML
    private ComboBox<Armor>  accessoriesC;

    @FXML
    private ComboBox<Item>   itemsC;

    @FXML
    private TextField	     unknown1ResT;

    @FXML
    private Tab		     itemTab;

    @FXML
    private Tab		     weaponTab;

    @FXML
    private Tab		     armorTab;
    @FXML
    private Button	     weaponUp;
    @FXML
    private Button	     weaponDown;
    @FXML
    private Button	     armorUp;
    @FXML
    private Button	     armorDown;
    @FXML
    private Button	     itemUp;
    @FXML
    private Button	     itemDown;
    @FXML
    private Button	     accessoryUp;
    @FXML
    private Button	     accessoryDown;

    @Override
    public void saveState()
    {
	startItem = itemsC.getSelectionModel().getSelectedIndex();
	startWeapon = weaponsC.getSelectionModel().getSelectedIndex();
	startArmor = armorsC.getSelectionModel().getSelectedIndex();
	startAccessory = accessoriesC.getSelectionModel().getSelectedIndex();
	startWeaponSort = weaponSortC.getSelectionModel().getSelectedIndex();
	startArmorSort = armorSortC.getSelectionModel().getSelectedIndex();
	if (previouslySelectedTab == weaponTab)
	    startTab = 1;
	else if (previouslySelectedTab == armorTab)
	    startTab = 2;
	else
	{
	    startTab = 0;
	}

	if (previouslySelectedArmorComboBox == armorsC)
	{
	    startArmorCombobox = 0;
	}
	else
	{
	    startArmorCombobox = 1;
	}

    }

}
