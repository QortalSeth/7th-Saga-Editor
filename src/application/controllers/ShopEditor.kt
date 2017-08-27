package application.controllers

import application.ControllerInitilizer
import application.models.Armor
import application.models.Item
import application.models.Shop
import application.models.Weapon
import application.models.lists.*
import application.staticClasses.Listeners
import javafx.beans.property.ObjectProperty
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyEvent
import java.lang.Integer.parseInt
import java.lang.Integer.toString

class ShopEditor : ControllerInitilizer
{
	private var shops = Shops(Lists.shops)
	private var items = Items(Lists.items, true)
	private var weapons = Weapons(Lists.weapons, true)
	private var armors = Armors(Lists.armors, true)

	internal var previouslySelectedShop: Shop? = null

	lateinit var itemsC: MutableList<ComboBox<Item>>
	lateinit var weaponsC: MutableList<ComboBox<Weapon>>
	lateinit var armorsC: MutableList<ComboBox<Armor>>
	lateinit var defaultItemsT: MutableList<TextField>
	lateinit var DweaponsT: MutableList<TextField>
	lateinit var defaultArmorsT: MutableList<TextField>
	lateinit var weaponCostsT: MutableList<TextField>
	lateinit var DweaponCostsT: MutableList<TextField>
	lateinit var armorCostsT: MutableList<TextField>
	lateinit var DarmorCostsT: MutableList<TextField>


	@FXML fun initialize() // this runs when the shop editor is opened
	{
		addChangeListenerToTextFields()
		itemsC = mutableListOf(item1C, item2C, item3C, item4C, item5C, item6C, item7C, item8C, item9C)
		weaponsC = mutableListOf(weapon1C, weapon2C, weapon3C, weapon4C, weapon5C)
		armorsC = mutableListOf(armor1C, armor2C, armor3C, armor4C, armor5C, armor6C, armor7C, armor8C)
		defaultItemsT = mutableListOf(defaultItem1T, defaultItem2T, defaultItem3T, defaultItem4T, defaultItem5T, defaultItem6T, defaultItem7T, defaultItem8T, defaultItem9T)
		DweaponsT = mutableListOf(defaultWeapon1T, defaultWeapon2T, defaultWeapon3T, defaultWeapon4T, defaultWeapon5T)
		defaultArmorsT = mutableListOf(defaultArmor1T, defaultArmor2T, defaultArmor3T, defaultArmor4T, defaultArmor5T, defaultArmor6T, defaultArmor7T, defaultArmor8T)
		weaponCostsT = mutableListOf(weaponPower1, weaponPower2, weaponPower3, weaponPower4, weaponPower5)
		DweaponCostsT = mutableListOf(DweaponPower1, DweaponPower2, DweaponPower3, DweaponPower4, DweaponPower5)
		armorCostsT = mutableListOf(armorPower1, armorPower2, armorPower3, armorPower4, armorPower5, armorPower6, armorPower7, armorPower8)
		DarmorCostsT = mutableListOf(DarmorPower1, DarmorPower2, DarmorPower3, DarmorPower4, DarmorPower5, DarmorPower6, DarmorPower7, DarmorPower8)

		weaponsC.forEach {
			it.valueProperty().addListener { observable, oldValue, newValue ->
				val property = observable as ObjectProperty<Weapon>
				val comboBox = property.bean as ComboBox<Weapon>
				weaponCostsT[weaponsC.indexOf(comboBox)].text = newValue.cost.toString()
			}
		}
		armorsC.forEach {
			it.valueProperty().addListener { observable, oldValue, newValue ->
				val property = observable as ObjectProperty<Armor>
				val comboBox = property.bean as ComboBox<Armor>
				armorCostsT[armorsC.indexOf(comboBox)].text = newValue.cost.toString()
			}
		}


		if (gameIndexIsSelected) gameOrderR.isSelected = true
		else
		{
			ChronOrderR.isSelected = true
			chronologicalSort()
		}
		shopsC.items = shops.models
		armors.addEmptyModel(armors.dModels)
		shopsC.selectionModel.select(startShop)

		var i = shopUp.graphic as ImageView
		i.image = Image(this.javaClass.getResourceAsStream("images/Triangle.png"))
		i = shopDown.graphic as ImageView
		i.image = Image(this.javaClass.getResourceAsStream("images/Triangle.png"))

		assembleLists()
		setSelectedShop()
	}

	@FXML fun setSelectedShop() // fill values with data from selected shop
	{
		saveData()
		updateController()
	}

	private fun updateController()
	{
		var shop = shopsC.selectionModel.selectedItem
		if (shop == null) shop = previouslySelectedShop
		previouslySelectedShop = shop

		val Dshop = shops.getDModel(shop!!.gameIndex)
		innCostT.text = toString(shop.innCost)
		defaultInnCostT.text = toString(Dshop.innCost)

		shop.itemCodes.withIndex()
				// fill item shop values
				.forEach { (index, itemCode) ->
					val listIndex = items.getIndex(itemCode)
					itemsC[index].selectionModel.select(listIndex)

					defaultItemsT[index].text = items.getDName(Dshop.itemCodes[index])
				}


		shop.weaponCodes.withIndex()
				// fill weapon shop values
				.forEach { (index, weaponCode) ->

					val listIndex = weapons.getIndex(weaponCode)
					weaponsC[index].selectionModel.select(listIndex)

					val weapon = weapons.models[listIndex]
					weaponCostsT[index].text = toString(weapon.cost)

					val dWeaponIndex = Dshop.weaponCodes[index]
					val Dweapon = weapons.getDItem(dWeaponIndex)
					DweaponCostsT[index].text = toString(Dweapon.cost)
					DweaponsT[index].text = Dweapon.toString()
				}
		println("armor codes: ${shop.armorCodes}")
		shop.armorCodes.withIndex()
				// fill armor shop values
				.forEach { (index, armorCode) ->
					val listIndex = armors.getIndex(armorCode)
					armorsC[index].selectionModel.select(listIndex)

					val armor = armors.models[listIndex]
					armorCostsT[index].text = toString(armor.cost)

					val DarmorIndex = Dshop.armorCodes[index]
					val Darmor = armors.getDItem(DarmorIndex)
					DarmorCostsT[index].text = toString(Darmor.cost)
					defaultArmorsT[index].text = Darmor.toString()
				}
	}

	@FXML fun gameIndexSort() // sorts the comboBox of shops by the order of the shop table
	{
		val selectedIndex = shopsC.selectionModel.selectedIndex
		shops.gameIndexSort()
		shopsC.selectionModel.select(selectedIndex)
	}

	@FXML fun chronologicalSort() // sorts the comboBox of shops by the order you visit them in the game
	{
		val selectedIndex = shopsC.selectionModel.selectedIndex
		shops.chronologicalIndexSort()
		shopsC.selectionModel.select(selectedIndex)
	}

	@FXML fun removeNonNumbers(event: KeyEvent) = Listeners.removeNonNumbers(event, null)
	private fun addChangeListenerToTextFields() = innCostT.focusedProperty().addListener(Listeners.textFieldFocusListener(999))

	override fun saveData() // takes data from textfields and comboboxes and stores it in current shop
	{
		val shop = previouslySelectedShop ?: return

		val itemCodes = mutableListOf<Int>()
		val weaponCodes = mutableListOf<Int>()
		val armorCodes = mutableListOf<Int>()

		// for each combobox get its item code and add to list
		itemsC.forEach { itemCodes.add(it.selectionModel!!.selectedItem!!.gameIndex) }
		weaponsC.forEach { weaponCodes.add(it.selectionModel!!.selectedItem!!.itemCode) }
		armorsC.forEach { armorCodes.add(it.selectionModel!!.selectedItem!!.itemCode) }

		shop.itemCodes = itemCodes
		shop.armorCodes = armorCodes
		shop.weaponCodes = weaponCodes
		shop.innCost = parseInt(innCostT.text)
	}

	@FXML fun sortItemShop()
	{
		saveData()
		val s = shopsC.selectionModel.selectedItem
		Items.sortItemCodesChronologically(s.itemCodes)
		updateController()
	}

	@FXML fun sortAllItemShops()
	{
		saveData()
		shopsC.items.forEach { Items.sortItemCodesChronologically(it.itemCodes) }
		updateController()
	}

	@FXML fun sortWeaponsAscending()
	{
		saveData()
		val s = shopsC.selectionModel.selectedItem
		s.sortWeaponsAscending()
		updateController()
	}

	@FXML fun sortAllWeaponsAscending()
	{
		saveData()
		shopsC.items.forEach { it.sortWeaponsAscending() }
		updateController()
	}

	@FXML fun sortWeaponsDescending()
	{
		saveData()
		val s = shopsC.selectionModel.selectedItem
		s.sortWeaponsDescending()
		updateController()
	}

	@FXML fun sortAllWeaponsDescending()
	{
		saveData()
		shopsC.items.forEach { it.sortWeaponsDescending() }
		updateController()
	}

	@FXML fun sortArmorsAscending()
	{
		saveData()
		val s = shopsC.selectionModel.selectedItem
		s.sortArmorsAscending()
		updateController()
	}

	@FXML fun sortAllArmorsAscending()
	{
		saveData()
		shopsC.items.forEach { it.sortArmorsAscending() }
		updateController()
	}

	@FXML fun sortArmorsDescending()
	{
		saveData()
		val s = shopsC.selectionModel.selectedItem
		s.sortArmorsDescending()
		updateController()
	}

	@FXML fun sortAllArmorsDescending()
	{
		saveData()
		shopsC.items.forEach { it.sortArmorsDescending() }
		updateController()
	}

	private fun assembleLists()
	{
		items.chronologicalIndexSort()
		weapons.sortByAscendingType()
		armors.sortByAscendingType()

		itemsC.forEach { it.items = items.models }
		weaponsC.forEach { it.items = weapons.models }
		armorsC.forEach { it.items = armors.models }
	}

	@FXML fun characterDecrement() = shopsC.selectionModel.selectPrevious()
	@FXML fun characterIncrement() = shopsC.selectionModel.selectNext()

	@FXML lateinit var item7C: ComboBox<Item>
	@FXML lateinit var defaultWeapon3T: TextField
	@FXML lateinit var weapon2C: ComboBox<Weapon>
	@FXML lateinit var armor7C: ComboBox<Armor>
	@FXML lateinit var armor3C: ComboBox<Armor>
	@FXML lateinit var item2C: ComboBox<Item>
	@FXML lateinit var item6C: ComboBox<Item>
	@FXML lateinit var defaultWeapon2T: TextField
	@FXML lateinit var armor2C: ComboBox<Armor>
	@FXML lateinit var weapon1C: ComboBox<Weapon>
	@FXML lateinit var weapon5C: ComboBox<Weapon>
	@FXML lateinit var armor6C: ComboBox<Armor>
	@FXML lateinit var defaultItem3T: TextField
	@FXML lateinit var defaultItem2T: TextField
	@FXML lateinit var item1C: ComboBox<Item>
	@FXML lateinit var defaultItem1T: TextField
	@FXML lateinit var item5C: ComboBox<Item>
	@FXML lateinit var defaultWeapon1T: TextField
	@FXML lateinit var defaultArmor3T: TextField
	@FXML lateinit var defaultArmor2T: TextField
	@FXML lateinit var item9C: ComboBox<Item>
	@FXML lateinit var defaultWeapon5T: TextField
	@FXML lateinit var defaultArmor5T: TextField
	@FXML lateinit var defaultItem9T: TextField
	@FXML lateinit var defaultArmor4T: TextField
	@FXML lateinit var defaultItem8T: TextField
	@FXML lateinit var defaultItem7T: TextField
	@FXML lateinit var defaultItem6T: TextField
	@FXML lateinit var defaultArmor1T: TextField
	@FXML lateinit var defaultItem5T: TextField
	@FXML lateinit var defaultItem4T: TextField
	@FXML lateinit var armor1C: ComboBox<Armor>
	@FXML lateinit var defaultArmor7T: TextField
	@FXML lateinit var defaultArmor6T: TextField
	@FXML lateinit var weapon4C: ComboBox<Weapon>
	@FXML lateinit var defaultArmor8T: TextField
	@FXML lateinit var armor5C: ComboBox<Armor>
	@FXML lateinit var item4C: ComboBox<Item>
	@FXML lateinit var item8C: ComboBox<Item>
	@FXML lateinit var defaultWeapon4T: TextField
	@FXML lateinit var defaultInnCostT: TextField
	@FXML lateinit var weapon3C: ComboBox<Weapon>
	@FXML lateinit var ChronOrderR: RadioButton
	@FXML lateinit var armor8C: ComboBox<Armor>
	@FXML lateinit var armor4C: ComboBox<Armor>
	@FXML lateinit var item3C: ComboBox<Item>
	@FXML lateinit var weaponPower1: TextField
	@FXML lateinit var weaponPower2: TextField
	@FXML lateinit var weaponPower3: TextField
	@FXML lateinit var weaponPower4: TextField
	@FXML lateinit var weaponPower5: TextField
	@FXML lateinit var DweaponPower1: TextField
	@FXML lateinit var DweaponPower2: TextField
	@FXML lateinit var DweaponPower3: TextField
	@FXML lateinit var DweaponPower4: TextField
	@FXML lateinit var DweaponPower5: TextField
	@FXML lateinit var armorPower1: TextField
	@FXML lateinit var armorPower2: TextField
	@FXML lateinit var armorPower3: TextField
	@FXML lateinit var armorPower4: TextField
	@FXML lateinit var armorPower5: TextField
	@FXML lateinit var armorPower6: TextField
	@FXML lateinit var armorPower7: TextField
	@FXML lateinit var armorPower8: TextField
	@FXML lateinit var DarmorPower1: TextField
	@FXML lateinit var DarmorPower2: TextField
	@FXML lateinit var DarmorPower3: TextField
	@FXML lateinit var DarmorPower4: TextField
	@FXML lateinit var DarmorPower5: TextField
	@FXML lateinit var DarmorPower6: TextField
	@FXML lateinit var DarmorPower7: TextField
	@FXML lateinit var DarmorPower8: TextField
	@FXML lateinit var shopUp: Button
	@FXML lateinit var shopDown: Button
	@FXML lateinit var shopsC: ComboBox<Shop>
	@FXML lateinit var gameOrderR: RadioButton
	@FXML lateinit var sortShops: ToggleGroup
	@FXML lateinit var innCostT: TextField


	override fun saveState()
	{
		startShop = shopsC.selectionModel.selectedIndex
		gameIndexIsSelected = gameOrderR.isSelected
	}

	companion object
	{
		private var startShop: Int = 0
		private var gameIndexIsSelected = false
	}
}
