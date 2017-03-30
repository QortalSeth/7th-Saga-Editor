package controllers;

import java.util.ArrayList;
import java.util.List;

import application.ControllerInitilizer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.Armor;
import models.Item;
import models.Model;
import models.Shop;
import models.Weapon;
import models.lists.Armors;
import models.lists.Items;
import models.lists.Lists;
import models.lists.Shops;
import models.lists.Weapons;
import staticClasses.Listeners;

public class ShopEditor extends Model implements ControllerInitilizer
{
    private List<ComboBox<Item>>   itemsC	       = new ArrayList<ComboBox<Item>>();
    private List<ComboBox<Weapon>> weaponsC	       = new ArrayList<ComboBox<Weapon>>();
    private List<ComboBox<Armor>>  armorsC	       = new ArrayList<ComboBox<Armor>>();
    private List<TextField>	   defaultItemsT       = new ArrayList<TextField>();
    private List<TextField>	   defaultWeaponsT     = new ArrayList<TextField>();
    private List<TextField>	   defaultArmorsT      = new ArrayList<TextField>();

    private List<TextField>	   weaponAttacksT      = new ArrayList<TextField>();
    private List<TextField>	   DweaponAttacksT     = new ArrayList<TextField>();
    private List<TextField>	   armorDefensesT      = new ArrayList<TextField>();
    private List<TextField>	   DarmorDefensesT     = new ArrayList<TextField>();

    private Shops		   shops;
    private Items		   items;
    private Weapons		   weapons;
    private Armors		   armors;
    private static int		   startShop;
    private static boolean	   gameIndexIsSelected = false;

    Shop			   previouslySelectedShop;

    @FXML
    private ComboBox<Shop>	   shopsC;
    @FXML
    private RadioButton		   gameOrderR;
    @FXML
    private ToggleGroup		   sortShops;
    @FXML
    private TextField		   innCostT;

    @FXML
    void initialize() // this runs when the shop editor is opened
    {
	this.addChangeListenerToTextFields();

	shops = new Shops(Lists.getShops());
	items = new Items(Lists.getItems(), true);
	weapons = new Weapons(Lists.getWeapons(), true);
	armors = new Armors(Lists.getArmors(), true);

	if (gameIndexIsSelected == true)
	{
	    gameOrderR.setSelected(true);
	}
	else
	{
	    ChronOrderR.setSelected(true);
	    chronologicalSort();
	}
	shopsC.setItems(shops.getModels());
	shopsC.getSelectionModel().select(startShop);

	ImageView i = (ImageView) shopUp.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));
	i = (ImageView) shopDown.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));

	this.assembleLists();
	this.setSelectedShop();
    }

    @FXML
    void setSelectedShop() // fill values with data from selected shop
    {
	saveData();
	updateController();
    }

    public void updateController()
    {
	Shop shop = shopsC.getSelectionModel().getSelectedItem();
	if (shop == null)
	{
	    shop = previouslySelectedShop;
	}
	previouslySelectedShop = shop;

	Shop defaultShop = shops.getDModel(shop);
	innCostT.setText(Integer.toString(shop.getInnCost()));
	defaultInnCostT.setText(Integer.toString(defaultShop.getInnCost()));

	int index = 0;
	for (Integer itemCode : shop.getItemCodes()) // fill item shop values
	{
	    int listIndex = items.getIndex(itemCode);
	    itemsC.get(index).getSelectionModel().select(listIndex);

	    defaultItemsT.get(index).setText(items.getDName(defaultShop.getItemCodes().get(index)));
	    index++;
	}

	index = 0;
	for (Integer weaponCode : shop.getWeaponCodes()) // fill weapon shop values

	{
	    // System.out.println("weapon code is: " + Integer.toHexString(weaponCode));
	    int listIndex = weapons.getIndex(weaponCode);
	    // System.out.println("ListIndex is: " + listIndex);
	    weaponsC.get(index).getSelectionModel().select(listIndex);

	    Weapon weapon = weapons.getModels().get(listIndex);
	    weaponAttacksT.get(index).setText(Integer.toString(weapon.getPower()));

	    Weapon Dweapon = weapons.getDModel(weapon);
	    DweaponAttacksT.get(index).setText(Integer.toString(Dweapon.getPower()));
	    defaultWeaponsT.get(index).setText(Dweapon.toString());
	    index++;
	}

	index = 0;
	for (Integer armorCode : shop.getArmorCodes()) // fill armor shop values
	{
	    int listIndex = armors.getIndex(armorCode);
	    armorsC.get(index).getSelectionModel().select(listIndex);

	    Armor armor = armorsC.get(index).getSelectionModel().getSelectedItem();
	    armorDefensesT.get(index).setText(Integer.toString(armor.getPower()));

	    Armor Darmor = armors.getDModel(armor);
	    DarmorDefensesT.get(index).setText(Integer.toString(Darmor.getPower()));
	    defaultArmorsT.get(index).setText(Darmor.toString());
	    index++;
	}
    }

    @FXML
    void gameIndexSort() // sorts the comboBox of shops by the order of the
			 // games shop table
    {
	int selectedIndex = shopsC.getSelectionModel().getSelectedIndex();
	shops.chronologicalIndexSort();
	shopsC.getSelectionModel().select(selectedIndex);
    }

    @FXML
    void chronologicalSort()
    // sorts the comboBox of shops by the order you visit them in the game
    {
	int selectedIndex = shopsC.getSelectionModel().getSelectedIndex();
	shops.gameIndexSort();
	shopsC.getSelectionModel().select(selectedIndex);
    }

    @FXML
    void removeNonNumbers(KeyEvent event)
    {
	Listeners.removeNonNumbers(event, null);
    }

    private void addChangeListenerToTextFields()
    {
	innCostT.focusedProperty().addListener(Listeners.textFieldFocusListener(999));
    }

    @Override
    public void saveData() // takes data from textfields and comboboxes and
			   // stores it in current shop
    {
	Shop shop = previouslySelectedShop;
	if (shop == null) { return; }

	List<Integer> itemCodes = new ArrayList<Integer>();
	List<Integer> weaponCodes = new ArrayList<Integer>();
	List<Integer> armorCodes = new ArrayList<Integer>();

	for (ComboBox<Item> i : itemsC) // for each combobox get its item code
					// and add to list
	{
	    itemCodes.add(i.getSelectionModel().getSelectedItem().getGameIndex());
	}

	for (ComboBox<Weapon> w : weaponsC) // for each combobox get its item
					    // code and add to list
	{
	    weaponCodes.add(w.getSelectionModel().getSelectedItem().getItemCode());
	}

	for (ComboBox<Armor> a : armorsC) // for each combobox get its item code
					  // and add to list
	{
	    armorCodes.add(a.getSelectionModel().getSelectedItem().getItemCode());
	}

	shop.setItemCodes(itemCodes);
	shop.setArmorCodes(armorCodes);
	shop.setWeaponCodes(weaponCodes);
	shop.setInnCost(Integer.parseInt(innCostT.getText()));
    }

    @FXML
    void sortItemShop()
    {
	saveData();
	Shop s = shopsC.getSelectionModel().getSelectedItem();
	Items.sortItemCodesChronologically(s.getItemCodes());
	updateController();
    }

    @FXML
    void sortAllItemShops()
    {
	saveData();
	for (Shop s : shopsC.getItems())
	{
	    Items.sortItemCodesChronologically(s.getItemCodes());
	}
	updateController();
    }

    @FXML
    void sortWeaponsAscending()
    {
	saveData();
	Shop s = shopsC.getSelectionModel().getSelectedItem();
	s.sortWeaponsAscending();
	updateController();
    }

    @FXML
    void sortAllWeaponsAscending()
    {
	saveData();
	for (Shop s : shopsC.getItems())
	{
	    s.sortWeaponsAscending();
	}
	updateController();
    }

    @FXML
    void sortWeaponsDescending()
    {
	saveData();
	Shop s = shopsC.getSelectionModel().getSelectedItem();
	s.sortWeaponsDescending();
	updateController();
    }

    @FXML
    void sortAllWeaponsDescending()
    {
	saveData();
	for (Shop s : shopsC.getItems())
	{
	    s.sortWeaponsDescending();
	}
	updateController();
    }

    @FXML
    void sortArmorsAscending()
    {
	saveData();
	Shop s = shopsC.getSelectionModel().getSelectedItem();
	s.sortArmorsAscending();
	updateController();
    }

    @FXML
    void sortAllArmorsAscending()
    {
	saveData();
	for (Shop s : shopsC.getItems())
	{
	    s.sortArmorsAscending();
	}
	updateController();
    }

    @FXML
    void sortArmorsDescending()
    {
	saveData();
	Shop s = shopsC.getSelectionModel().getSelectedItem();
	s.sortArmorsDescending();
	updateController();
    }

    @FXML
    void sortAllArmorsDescending()
    {
	saveData();
	for (Shop s : shopsC.getItems())
	{
	    s.sortArmorsDescending();
	}
	updateController();
    }

    private void assembleLists()
    {
	itemsC.add(item1C);
	itemsC.add(item2C);
	itemsC.add(item3C);
	itemsC.add(item4C);
	itemsC.add(item5C);
	itemsC.add(item6C);
	itemsC.add(item7C);
	itemsC.add(item8C);
	itemsC.add(item9C);

	weaponsC.add(weapon1C);
	weaponsC.add(weapon2C);
	weaponsC.add(weapon3C);
	weaponsC.add(weapon4C);
	weaponsC.add(weapon5C);

	weaponAttacksT.add(weaponPower1);
	weaponAttacksT.add(weaponPower2);
	weaponAttacksT.add(weaponPower3);
	weaponAttacksT.add(weaponPower4);
	weaponAttacksT.add(weaponPower5);

	DweaponAttacksT.add(DweaponPower1);
	DweaponAttacksT.add(DweaponPower2);
	DweaponAttacksT.add(DweaponPower3);
	DweaponAttacksT.add(DweaponPower4);
	DweaponAttacksT.add(DweaponPower5);

	armorDefensesT.add(armorPower1);
	armorDefensesT.add(armorPower2);
	armorDefensesT.add(armorPower3);
	armorDefensesT.add(armorPower4);
	armorDefensesT.add(armorPower5);
	armorDefensesT.add(armorPower6);
	armorDefensesT.add(armorPower7);
	armorDefensesT.add(armorPower8);

	DarmorDefensesT.add(DarmorPower1);
	DarmorDefensesT.add(DarmorPower2);
	DarmorDefensesT.add(DarmorPower3);
	DarmorDefensesT.add(DarmorPower4);
	DarmorDefensesT.add(DarmorPower5);
	DarmorDefensesT.add(DarmorPower6);
	DarmorDefensesT.add(DarmorPower7);
	DarmorDefensesT.add(DarmorPower8);

	armorsC.add(armor1C);
	armorsC.add(armor2C);
	armorsC.add(armor3C);
	armorsC.add(armor4C);
	armorsC.add(armor5C);
	armorsC.add(armor6C);
	armorsC.add(armor7C);
	armorsC.add(armor8C);

	defaultItemsT.add(defaultItem1T);
	defaultItemsT.add(defaultItem2T);
	defaultItemsT.add(defaultItem3T);
	defaultItemsT.add(defaultItem4T);
	defaultItemsT.add(defaultItem5T);
	defaultItemsT.add(defaultItem6T);
	defaultItemsT.add(defaultItem7T);
	defaultItemsT.add(defaultItem8T);
	defaultItemsT.add(defaultItem9T);

	defaultWeaponsT.add(defaultWeapon1T);
	defaultWeaponsT.add(defaultWeapon2T);
	defaultWeaponsT.add(defaultWeapon3T);
	defaultWeaponsT.add(defaultWeapon4T);
	defaultWeaponsT.add(defaultWeapon5T);

	defaultArmorsT.add(defaultArmor1T);
	defaultArmorsT.add(defaultArmor2T);
	defaultArmorsT.add(defaultArmor3T);
	defaultArmorsT.add(defaultArmor4T);
	defaultArmorsT.add(defaultArmor5T);
	defaultArmorsT.add(defaultArmor6T);
	defaultArmorsT.add(defaultArmor7T);
	defaultArmorsT.add(defaultArmor8T);

	items.chronologicalIndexSort();
	weapons.sortByAscendingType();
	armors.sortByAscendingType();

	for (ComboBox<Item> i : itemsC)
	    i.setItems(items.getModels());

	for (ComboBox<Weapon> w : weaponsC)
	    w.setItems(weapons.getModels());

	for (ComboBox<Armor> a : armorsC)
	    a.setItems(armors.getModels());
    }

    @FXML
    void characterDecrement(MouseEvent event)
    {
	shopsC.getSelectionModel().selectPrevious();
    }

    @FXML
    void characterIncrement(MouseEvent event)
    {
	shopsC.getSelectionModel().selectNext();
    }

    @FXML
    private ComboBox<Item>   item7C;
    @FXML
    private TextField	     defaultWeapon3T;
    @FXML
    private ComboBox<Weapon> weapon2C;
    @FXML
    private ComboBox<Armor>  armor7C;
    @FXML
    private ComboBox<Armor>  armor3C;
    @FXML
    private ComboBox<Item>   item2C;
    @FXML
    private ComboBox<Item>   item6C;
    @FXML
    private TextField	     defaultWeapon2T;
    @FXML
    private ComboBox<Armor>  armor2C;
    @FXML
    private ComboBox<Weapon> weapon1C;
    @FXML
    private ComboBox<Weapon> weapon5C;
    @FXML
    private ComboBox<Armor>  armor6C;
    @FXML
    private TextField	     defaultItem3T;
    @FXML
    private TextField	     defaultItem2T;
    @FXML
    private ComboBox<Item>   item1C;
    @FXML
    private TextField	     defaultItem1T;
    @FXML
    private ComboBox<Item>   item5C;
    @FXML
    private TextField	     defaultWeapon1T;

    @FXML
    private TextField	     defaultArmor3T;
    @FXML
    private TextField	     defaultArmor2T;
    @FXML
    private ComboBox<Item>   item9C;
    @FXML
    private TextField	     defaultWeapon5T;
    @FXML
    private TextField	     defaultArmor5T;
    @FXML
    private TextField	     defaultItem9T;
    @FXML
    private TextField	     defaultArmor4T;
    @FXML
    private TextField	     defaultItem8T;
    @FXML
    private TextField	     defaultItem7T;
    @FXML
    private TextField	     defaultItem6T;
    @FXML
    private TextField	     defaultArmor1T;
    @FXML
    private TextField	     defaultItem5T;
    @FXML
    private TextField	     defaultItem4T;
    @FXML
    private ComboBox<Armor>  armor1C;
    @FXML
    private TextField	     defaultArmor7T;
    @FXML
    private TextField	     defaultArmor6T;
    @FXML
    private ComboBox<Weapon> weapon4C;
    @FXML
    private TextField	     defaultArmor8T;
    @FXML
    private ComboBox<Armor>  armor5C;
    @FXML
    private ComboBox<Item>   item4C;
    @FXML
    private ComboBox<Item>   item8C;
    @FXML
    private TextField	     defaultWeapon4T;
    @FXML
    private TextField	     defaultInnCostT;
    @FXML
    private ComboBox<Weapon> weapon3C;
    @FXML
    private RadioButton	     ChronOrderR;
    @FXML
    private ComboBox<Armor>  armor8C;
    @FXML
    private ComboBox<Armor>  armor4C;
    @FXML
    private ComboBox<Item>   item3C;
    @FXML
    private TextField	     weaponPower1;
    @FXML
    private TextField	     weaponPower2;
    @FXML
    private TextField	     weaponPower3;
    @FXML
    private TextField	     weaponPower4;
    @FXML
    private TextField	     weaponPower5;
    @FXML
    private TextField	     DweaponPower1;
    @FXML
    private TextField	     DweaponPower2;
    @FXML
    private TextField	     DweaponPower3;
    @FXML
    private TextField	     DweaponPower4;
    @FXML
    private TextField	     DweaponPower5;
    @FXML
    private TextField	     armorPower1;
    @FXML
    private TextField	     armorPower2;
    @FXML
    private TextField	     armorPower3;
    @FXML
    private TextField	     armorPower4;
    @FXML
    private TextField	     armorPower5;
    @FXML
    private TextField	     armorPower6;
    @FXML
    private TextField	     armorPower7;
    @FXML
    private TextField	     armorPower8;
    @FXML
    private TextField	     DarmorPower1;
    @FXML
    private TextField	     DarmorPower2;
    @FXML
    private TextField	     DarmorPower3;
    @FXML
    private TextField	     DarmorPower4;
    @FXML
    private TextField	     DarmorPower5;
    @FXML
    private TextField	     DarmorPower6;
    @FXML
    private TextField	     DarmorPower7;
    @FXML
    private TextField	     DarmorPower8;
    @FXML
    private Button	     shopUp;
    @FXML
    private Button	     shopDown;

    @Override
    public void saveState()
    {
	startShop = shopsC.getSelectionModel().getSelectedIndex();
	if (gameOrderR.isSelected())
	{
	    gameIndexIsSelected = true;
	}
	else
	{
	    gameIndexIsSelected = false;
	}
    }
}
