package application.controllers

import application.ControllerInitilizer
import application.models.Armor
import application.models.Item
import application.models.Weapon
import application.models.lists.Armors
import application.models.lists.Items
import application.models.lists.Lists
import application.models.lists.Weapons
import application.staticClasses.Listeners
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyEvent
import java.lang.Integer.parseInt
import java.lang.Integer.toString

class EquipmentEditor : ControllerInitilizer
{
	private var previouslySelectedItem: Item? = null
	private var previouslySelectedWeapon: Weapon? = null
	private var previouslySelectedArmor: Armor? = null
	private var previouslySelectedTab: Tab? = null
	private var previouslySelectedArmorComboBox: ComboBox<Armor>? = null

	private var items = Items(Lists.items, false)
	private var weapons = Weapons(Lists.weapons, 0xFF)
	private var armors = Armors(Lists.armors, 0xFF)
	private var accessories = Armors(Lists.armors, 0xFF)
	lateinit var users: MutableList<CheckBox>
	lateinit var Dusers: MutableList<CheckBox>
	@FXML lateinit var tabPane: TabPane


	@FXML fun initialize()
	{
		if (startArmorCombobox == 0) previouslySelectedArmorComboBox = armorsC
		if (startArmorCombobox == 1) previouslySelectedArmorComboBox = accessoriesC
		users = mutableListOf(kamilCH, olvanCH, esunaCH, wilmeCH, luxCH, valsuCH, lejesCH)
		Dusers = mutableListOf(DKamilCH, DOlvanCH, DEsunaCH, DWilmeCH, DLuxCH, DValsuCH, DLejesCH)


		addChangeListenerToTextFields()
		initializeComboboxes()
		setSelectedWeapon()
		setSelectedArmor()
		setSelectedItem()

		var i = itemUp.graphic as ImageView
		i.image = Image(javaClass.getResourceAsStream("images/Triangle.png"))
		i = itemDown.graphic as ImageView
		i.image = Image(javaClass.getResourceAsStream("images/Triangle.png"))

		i = weaponUp.graphic as ImageView
		i.image = Image(javaClass.getResourceAsStream("images/Triangle.png"))
		i = weaponDown.graphic as ImageView
		i.image = Image(javaClass.getResourceAsStream("images/Triangle.png"))

		i = armorUp.graphic as ImageView
		i.image = Image(javaClass.getResourceAsStream("images/Triangle.png"))
		i = armorDown.graphic as ImageView
		i.image = Image(javaClass.getResourceAsStream("images/Triangle.png"))

		i = accessoryUp.graphic as ImageView
		i.image = Image(javaClass.getResourceAsStream("images/Triangle.png"))
		i = accessoryDown.graphic as ImageView
		i.image = Image(javaClass.getResourceAsStream("images/Triangle.png"))

		if (previouslySelectedTab == null) previouslySelectedTab = itemTab

		tabPane.selectionModel.select(startTab)
	}


	private fun initializeComboboxes()
	{

		itemsC.items.addAll(items.models)
		itemsC.selectionModel.select(startItem)

		targetC.items = FXCollections.observableArrayList("Single Enemy", "All Enemies", "Ally", "Map")
		usesC.items = FXCollections.observableArrayList("Reusable", "Destroy on Use")
		sellRatioC.items = FXCollections.observableArrayList<String>("50%", "100%")

		val equipSortValues = FXCollections.observableArrayList<String>("All", "Kamil", "Olvan", "Esuna", "Wilme", "Lux", "Valsu", "Lejes")
		weaponSortC.items = equipSortValues
		armorSortC.items = equipSortValues

		weaponSortC.selectionModel.select(startWeaponSort)
		armorSortC.selectionModel.select(startArmorSort)

		sortWeapons()
		sortArmors()
		weaponsC.items = weapons.models
		armorsC.items = armors.models
		accessoriesC.items = accessories.models

		weaponsC.selectionModel.select(startWeapon)
		accessoriesC.selectionModel.select(startAccessory)
		armorsC.selectionModel.select(startArmor)

		val c = ChangeListener<Boolean> { observable: ObservableValue<*>, oldValue: Any, newValue: Any ->
			val a = observable as ReadOnlyBooleanProperty
			val comboBox = a.bean as ComboBox<Armor>
			previouslySelectedArmorComboBox = comboBox
			setSelectedArmor()
		}
		armorsC.focusedProperty().addListener(c)
		accessoriesC.focusedProperty().addListener(c)
	}


	@FXML fun setSelectedItem()
	{
		val selectedTab = tabPane.selectionModel.selectedItem
		if (selectedTab !== itemTab) return

		saveItems()
		val item = itemsC.selectionModel.selectedItem
		// System.out.println("Selected item is: "+item.getName());
		val Ditem = items.getDModel(item)

		previouslySelectedItem = item

		var target = item.target
		if (target == 4)
		{
			target--
		}
		targetC.selectionModel.select(target)
		usesC.selectionModel.select(item.uses)
		itemCostT.text = toString(item.cost)
		sellRatioC.selectionModel.select(item.sellRatio)
		selectUsers(item.users, users)

		var Dtarget = Ditem.target
		if (Dtarget == 4) Dtarget--
		DtargetT.text = targetC.items[Dtarget]
		DusesT.text = usesC.items[Ditem.uses]
		DitemCostT.text = toString(Ditem.cost)
		DsellRatioT.text = sellRatioC.items[Ditem.sellRatio]
		selectUsers(Ditem.users, Dusers)

	}

	private fun saveItems()
	{
		if (previouslySelectedTab !== itemTab) return
		val item = previouslySelectedItem ?: return

		// System.out.println("Saving Item: "+item.getName());
		var target = targetC.selectionModel.selectedIndex
		if (target == 3) target++

		item.target = target
		item.uses = usesC.selectionModel.selectedIndex
		item.cost = parseInt(itemCostT.text)
		item.sellRatio = sellRatioC.selectionModel.selectedIndex
		item.users = getUsers(users)
	}

	@FXML fun setSelectedWeapon()
	{
		val selectedTab = tabPane.selectionModel.selectedItem
		if (selectedTab != weaponTab) return

		saveWeapons()
		val weapon = weaponsC.selectionModel.selectedItem ?: return
		val Dweapon = weapons.getDModel(weapon)

		// System.out.println("Selected weapon is: "+weapon.getName());
		previouslySelectedWeapon = weapon
		weaponPowerT.text = toString(weapon.power)
		weaponCostT.text = toString(weapon.cost)
		weaponDiscountT.text = toString(weapon.discount)
		selectUsers(weapon.equipCode, users)

		DweaponPowerT.text = toString(Dweapon.power)
		DweaponCostT.text = toString(Dweapon.cost)
		DweaponDiscountT.text = toString(Dweapon.discount)

		selectUsers(Dweapon.equipCode, Dusers)
	}

	private fun saveWeapons()
	{
		if (previouslySelectedTab !== weaponTab) return

		val w = previouslySelectedWeapon ?: return
		// System.out.println("Saving Weapon: "+w.getName());
		w.power = parseInt(weaponPowerT.text)
		w.cost = parseInt(weaponCostT.text)
		w.equipCode = getUsers(users)
		w.discount = parseInt(weaponDiscountT.text)

	}

	@FXML fun sortWeapons()
	{
		val characterEquipCode: Int
		saveData()

		if (weaponSortC.selectionModel.selectedIndex == 0) // if character index ==ALL
			characterEquipCode = 0xFF
		else characterEquipCode = Math.pow(2.0, weaponSortC.selectionModel.selectedIndex - 1.0).toInt()

		weapons.clear()
		weapons.addUsefulModels(Lists.weapons, characterEquipCode)
		weapons.sortByAscendingPower()
		weaponsC.selectionModel.select(0)
	}

	@FXML fun setSelectedArmor()
	{
		val selectedTab = tabPane.selectionModel.selectedItem
		if (selectedTab !== armorTab) return
		val source = previouslySelectedArmorComboBox
		saveArmors()
		val armor = source!!.selectionModel.selectedItem

		// System.out.println("Selected armor is: " + armor.getName());
		highlightSelectedArmorComboBox()

		if (armor == null) return
		val Darmor = armors.getDModel(armor)
		// System.out.println(armor.toString());

		previouslySelectedArmor = armor
		armorPowerT.text = toString(armor.power)
		armorCostT.text = toString(armor.cost)
		laserResT.text = toString(armor.laserRes)
		unknown1ResT.text = toString(armor.unknownRes1)
		unknown2ResT.text = toString(armor.unknownRes2)
		fireResT.text = toString(armor.fireRes)
		iceResT.text = toString(armor.iceRes)
		vacuumResT.text = toString(armor.vacuumRes)
		debuffResT.text = toString(armor.debuffRes)
		armorDiscountT.text = toString(armor.discount)

		DarmorPowerT.text = toString(Darmor.power)
		DarmorCostT.text = toString(Darmor.cost)
		DlaserResT.text = toString(Darmor.laserRes)
		Dunknown1ResT.text = toString(Darmor.unknownRes1)
		Dunknown2ResT.text = toString(Darmor.unknownRes2)
		DfireResT.text = toString(Darmor.fireRes)
		DiceResT.text = toString(Darmor.iceRes)
		DvacuumResT.text = toString(Darmor.vacuumRes)
		DdebuffResT.text = toString(Darmor.debuffRes)
		DarmorDiscountT.text = toString(Darmor.discount)

		selectUsers(armor.equipCode, users)
		selectUsers(Darmor.equipCode, Dusers)
	}

	private fun saveArmors()
	{
		if (previouslySelectedTab !== armorTab) return
		val a = previouslySelectedArmor ?: return
		println("Saving Armor: " + a.name)
		a.power = parseInt(armorPowerT.text)
		a.cost = parseInt(armorCostT.text)
		a.laserRes = parseInt(laserResT.text)
		a.unknownRes1 = parseInt(unknown1ResT.text)
		a.unknownRes2 = parseInt(unknown2ResT.text)
		a.fireRes = parseInt(fireResT.text)
		a.iceRes = parseInt(iceResT.text)
		a.vacuumRes = parseInt(vacuumResT.text)
		a.debuffRes = parseInt(debuffResT.text)
		a.discount = parseInt(armorDiscountT.text)
		a.equipCode = getUsers(users)
	}

	@FXML fun sortArmors()
	{
		saveData()
		val characterEquipCode: Int
		if (armorSortC.selectionModel.selectedIndex == 0)// if character index ==ALL
			characterEquipCode = 0xFF
		else characterEquipCode = Math.pow(2.0, armorSortC.selectionModel.selectedIndex - 1.0).toInt()

		accessories.clear()
		accessories.addUsefulAccessories(Lists.armors, characterEquipCode)
		accessories.sortByAscendingPower()
		accessoriesC.selectionModel.select(0)

		armors.clear()
		armors.addUsefulBodyArmors(Lists.armors, characterEquipCode)
		armors.sortByAscendingPower()
		armorsC.selectionModel.select(0)
	}

	private fun highlightSelectedArmorComboBox()
	{
		val source = previouslySelectedArmorComboBox
		if (source == armorsC)
		{
			armorsC.style = "-fx-border-color:-fx-focus-color;"
			accessoriesC.setStyle("-fx-border-color: transparent;")
		}
		else if (source === accessoriesC)
		{
			accessoriesC.style = "-fx-border-color:-fx-focus-color;"
			armorsC.setStyle("-fx-border-color: transparent;")
		}
		else println("source is NULL")
	}

	@FXML fun saveTab()
	{

		val selectedTab = tabPane.selectionModel.selectedItem
		when
		{
			previouslySelectedTab == null       -> return
			previouslySelectedTab === itemTab   -> saveItems()
			previouslySelectedTab === weaponTab -> saveWeapons()
			previouslySelectedTab === armorTab  -> saveArmors()
		}

		// System.out.println("previouslySelectedTab is: "
		// +previouslySelectedTab.getText());
		// System.out.println("selectedTab is: " +selectedTab.getText());

		// System.out.println("previouslySelectedTab is: "
		// +previouslySelectedTab.getText());
		// System.out.println("selectedTab is: " +selectedTab.getText());

		if (selectedTab === itemTab) setSelectedItem()
		if (selectedTab === weaponTab) setSelectedWeapon()
		if (selectedTab === armorTab) setSelectedArmor()

		previouslySelectedTab = selectedTab
	}


	private fun selectUsers(users: Int, characters: MutableList<CheckBox>)
	{
		println("users to select: " + users)
		characters.forEach { it.isSelected = false }

		if (users and 0x01 > 0) characters[0].isSelected = true
		if (users and 0x02 > 0) characters[1].isSelected = true
		if (users and 0x04 > 0) characters[2].isSelected = true
		if (users and 0x08 > 0) characters[3].isSelected = true
		if (users and 0x10 > 0) characters[4].isSelected = true
		if (users and 0x20 > 0) characters[5].isSelected = true
		if (users and 0x40 > 0) characters[6].isSelected = true
	}

	private fun getUsers(characters: MutableList<CheckBox>): Int
	{
		var users = 0

		if (characters[0].isSelected) users = users or 0x01
		if (characters[1].isSelected) users = users or 0x02
		if (characters[2].isSelected) users = users or 0x04
		if (characters[3].isSelected) users = users or 0x08
		if (characters[4].isSelected) users = users or 0x10
		if (characters[5].isSelected) users = users or 0x20
		if (characters[6].isSelected) users = users or 0x40

		return users
	}

	@FXML fun removeNonNumbers(event: KeyEvent) = Listeners.removeNonNumbers(event, null)
	@FXML fun incrementWeapon() = weaponsC.selectionModel.selectNext()
	@FXML fun decrementWeapon() = weaponsC.selectionModel.selectPrevious()
	@FXML fun incrementItem() = itemsC.selectionModel.selectNext()
	@FXML fun decrementItem() = itemsC.selectionModel.selectPrevious()

	@FXML fun incrementArmor()
	{
		previouslySelectedArmorComboBox = armorsC
		armorsC.selectionModel.selectNext()
		armorsC.requestFocus()
	}

	@FXML fun decrementArmor()
	{
		previouslySelectedArmorComboBox = armorsC
		armorsC.selectionModel.selectPrevious()
		setSelectedArmor()
		armorsC.requestFocus()
	}

	@FXML fun incrementAccessory()
	{
		previouslySelectedArmorComboBox = accessoriesC
		accessoriesC.selectionModel.selectNext()
		accessoriesC.requestFocus()
	}

	@FXML fun decrementAccessory()
	{
		previouslySelectedArmorComboBox = accessoriesC
		accessoriesC.selectionModel.selectPrevious()
		accessoriesC.requestFocus()
	}

	override fun saveData() = saveTab()

	private fun addChangeListenerToTextFields()
	{
		itemCostT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535))
		weaponPowerT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535))
		weaponCostT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535))
		weaponDiscountT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535))
		armorPowerT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535))
		armorCostT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535))
		laserResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		unknown1ResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		unknown2ResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		fireResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		iceResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		vacuumResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		debuffResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		armorDiscountT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535))
	}

	@FXML lateinit var olvanCH: CheckBox
	@FXML lateinit var wilmeCH: CheckBox
	@FXML lateinit var laserResT: TextField
	@FXML lateinit var armorCostT: TextField
	@FXML lateinit var itemCostT: TextField
	@FXML lateinit var weaponCostT: TextField
	@FXML lateinit var usesC: ComboBox<String>
	@FXML lateinit var debuffResT: TextField
	@FXML lateinit var sellRatioC: ComboBox<String>
	@FXML lateinit var fireResT: TextField
	@FXML lateinit var iceResT: TextField
	@FXML lateinit var lejesCH: CheckBox
	@FXML lateinit var luxCH: CheckBox
	@FXML lateinit var weaponPowerT: TextField
	@FXML lateinit var DweaponPowerT: TextField
	@FXML lateinit var DweaponCostT: TextField
	@FXML lateinit var DweaponDiscountT: TextField
	@FXML lateinit var armorDiscountT: TextField
	@FXML lateinit var valsuCH: CheckBox
	@FXML lateinit var weaponsC: ComboBox<Weapon>
	@FXML lateinit var weaponSortC: ComboBox<String>
	@FXML lateinit var armorSortC: ComboBox<String>
	@FXML lateinit var esunaCH: CheckBox
	@FXML lateinit var DEsunaCH: CheckBox
	@FXML lateinit var DKamilCH: CheckBox
	@FXML lateinit var DOlvanCH: CheckBox
	@FXML lateinit var DWilmeCH: CheckBox
	@FXML lateinit var DLuxCH: CheckBox
	@FXML lateinit var DValsuCH: CheckBox
	@FXML lateinit var DLejesCH: CheckBox
	@FXML lateinit var vacuumResT: TextField
	@FXML lateinit var weaponDiscountT: TextField
	@FXML lateinit var kamilCH: CheckBox
	@FXML lateinit var armorPowerT: TextField
	@FXML lateinit var DarmorPowerT: TextField
	@FXML lateinit var DarmorCostT: TextField
	@FXML lateinit var DlaserResT: TextField
	@FXML lateinit var Dunknown1ResT: TextField
	@FXML lateinit var Dunknown2ResT: TextField
	@FXML lateinit var DfireResT: TextField
	@FXML lateinit var DiceResT: TextField
	@FXML lateinit var DdebuffResT: TextField
	@FXML lateinit var DvacuumResT: TextField
	@FXML lateinit var DarmorDiscountT: TextField
	@FXML lateinit var unknown2ResT: TextField
	@FXML lateinit var targetC: ComboBox<String>
	@FXML lateinit var DtargetT: TextField
	@FXML lateinit var DusesT: TextField
	@FXML lateinit var DitemCostT: TextField
	@FXML lateinit var DsellRatioT: TextField
	@FXML lateinit var armorsC: ComboBox<Armor>
	@FXML lateinit var accessoriesC: ComboBox<Armor>
	@FXML lateinit var itemsC: ComboBox<Item>
	@FXML lateinit var unknown1ResT: TextField
	@FXML lateinit var itemTab: Tab
	@FXML lateinit var weaponTab: Tab
	@FXML lateinit var armorTab: Tab
	@FXML lateinit var weaponUp: Button
	@FXML lateinit var weaponDown: Button
	@FXML lateinit var armorUp: Button
	@FXML lateinit var armorDown: Button
	@FXML lateinit var itemUp: Button
	@FXML lateinit var itemDown: Button
	@FXML lateinit var accessoryUp: Button
	@FXML lateinit var accessoryDown: Button

	override fun saveState()
	{
		startItem = itemsC.selectionModel.selectedIndex
		startWeapon = weaponsC.selectionModel.selectedIndex
		startArmor = armorsC.selectionModel.selectedIndex
		startAccessory = accessoriesC.selectionModel.selectedIndex
		startWeaponSort = weaponSortC.selectionModel.selectedIndex
		startArmorSort = armorSortC.selectionModel.selectedIndex
		if (previouslySelectedTab == weaponTab) startTab = 1
		else if (previouslySelectedTab == armorTab) startTab = 2
		else startTab = 0

		if (previouslySelectedArmorComboBox == armorsC) startArmorCombobox = 0
		else startArmorCombobox = 1

	}

	companion object
	{
		private var startItem: Int = 0
		private var startWeapon: Int = 0
		private var startArmor: Int = 0
		private var startAccessory: Int = 0
		private var startWeaponSort: Int = 0
		private var startArmorSort: Int = 0
		private var startTab: Int = 0
		private var startArmorCombobox: Int = 0
	}
}
