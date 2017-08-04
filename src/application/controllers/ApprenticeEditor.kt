package application.controllers

import application.ControllerInitilizer
import application.models.Apprentice
import application.models.Armor
import application.models.EquipmentAtLevel
import application.models.Weapon
import application.models.lists.Armors
import application.models.lists.Lists
import application.models.lists.Weapons
import application.staticClasses.Listeners
import application.staticClasses.TextReader
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyEvent
import java.lang.Integer.*

class ApprenticeEditor : ControllerInitilizer
{
	private lateinit var weaponsC: MutableList<ComboBox<Weapon>>
	private lateinit var armorsC: MutableList<ComboBox<Armor>>
	private lateinit var accessoriesC: MutableList<ComboBox<Armor>>
	private var weapons = Weapons(Lists.weapons)
	private var armors = Armors(Lists.armors)
	private var accessories = Armors(Lists.armors)
	private var previouslySelectedApprentice: Apprentice? = null
	private var apprentices = Lists.apprentices


	lateinit var levelTs: Array<TextField>
	lateinit var DlevelTs: Array<TextField>
	lateinit var dialogueTs: Array<Array<TextField>>
	lateinit var DDialogueTs: Array<Array<TextField>>
	lateinit var DweaponsT: Array<TextField>
	lateinit var DarmorsT: Array<TextField>
	lateinit var DaccessoriesT: Array<TextField>

	@FXML fun initialize()
	{
		apprenticesC.items.addAll(apprentices.models)
		DgoldPerLevelT.text = toString(apprentices.dgoldPerLevel)
		goldPerLevelT.text = toString(apprentices.goldPerLevel)

		levelTs = arrayOf(level1T, level2T, level3T, level4T, level5T, level6T, level7T, level8T, level9T, level10T)
		DlevelTs = arrayOf(Dlevel1, Dlevel2, Dlevel3, Dlevel4, Dlevel5, Dlevel6, Dlevel7, Dlevel8, Dlevel9, Dlevel10)
		dialogueTs = arrayOf(arrayOf(a2kOJ, a2kBJ, a2kNO, a2kAS, a2kOF, a2kAA), arrayOf(a2oOJ, a2oBJ, a2oNO, a2oAS, a2oOF, a2oAA), arrayOf(a2eOJ, a2eBJ, a2eNO, a2eAS, a2eOF, a2eAA), arrayOf(a2wOJ, a2wBJ, a2wNO, a2wAS, a2wOF, a2wAA), arrayOf(a2xOJ, a2xBJ, a2xNO, a2xAS, a2xOF, a2xAA), arrayOf(a2vOJ, a2vBJ, a2vNO, a2vAS, a2vOF, a2vAA), arrayOf(a2lOJ, a2lBJ, a2lNO, a2lAS, a2lOF, a2lAA))
		DDialogueTs = arrayOf(arrayOf(Da2kOJ, Da2kBJ, Da2kNO, Da2kAS, Da2kOF, Da2kAA), arrayOf(Da2oOJ, Da2oBJ, Da2oNO, Da2oAS, Da2oOF, Da2oAA), arrayOf(Da2eOJ, Da2eBJ, Da2eNO, Da2eAS, Da2eOF, Da2eAA), arrayOf(Da2wOJ, Da2wBJ, Da2wNO, Da2wAS, Da2wOF, Da2wAA), arrayOf(Da2xOJ, Da2xBJ, Da2xNO, Da2xAS, Da2xOF, Da2xAA), arrayOf(Da2vOJ, Da2vBJ, Da2vNO, Da2vAS, Da2vOF, Da2vAA), arrayOf(Da2lOJ, Da2lBJ, Da2lNO, Da2lAS, Da2lOF, Da2lAA))
		DweaponsT = arrayOf(Dweapon1, Dweapon2, Dweapon3, Dweapon4, Dweapon5, Dweapon6, Dweapon7, Dweapon8, Dweapon9, Dweapon10)
		DarmorsT = arrayOf(Darmor1, Darmor2, Darmor3, Darmor4, Darmor5, Darmor6, Darmor7, Darmor8, Darmor9, Darmor10)
		DaccessoriesT = arrayOf(Daccessory1, Daccessory2, Daccessory3, Daccessory4, Daccessory5, Daccessory6, Daccessory7, Daccessory8, Daccessory9, Daccessory10)

		var i = apprenticeUp.graphic as ImageView
		i.image = Image(this.javaClass.getResourceAsStream("images/Triangle.png"))
		i = apprenticeDown.graphic as ImageView
		i.image = Image(this.javaClass.getResourceAsStream("images/Triangle.png"))

		goldPerLevelT.focusedProperty().addListener(Listeners.textFieldFocusListener(999))
		dialogueTs.forEach {
			it.forEach { t ->
				t.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
				t.focusedProperty().addListener { observable: ObservableValue<*>, oldValue: Any, newValue: Any ->
					val a = observable as ReadOnlyBooleanProperty
					val t = a.bean as TextField
					val apprentice = apprenticesC.selectionModel.selectedItem
					dialogueTs.indices.forEach { i ->
						dialogueTs[i].indices.forEach { k ->
							if (dialogueTs[i][k] === t)
							{
								dialogueDisplay.text = TextReader.readText(apprentice.dialoguePointers[k])
								selectedTextFieldIndex = k
							}
						}
					}
				}
			}
		}

		weaponsC = mutableListOf(weapon1, weapon2, weapon3, weapon4, weapon5, weapon6, weapon7, weapon8, weapon9, weapon10)
		armorsC = mutableListOf(armor1, armor2, armor3, armor4, armor5, armor6, armor7, armor8, armor9, armor10)
		accessoriesC = mutableListOf(accessory1, accessory2, accessory3, accessory4, accessory5, accessory6, accessory7, accessory8, accessory9, accessory10)

		weaponsC.forEach { it.items = weapons.models }
		armorsC.forEach { it.items = armors.models }
		accessoriesC.forEach { it.items = accessories.models }

		apprenticesC.selectionModel.select(startApprentice)
		this.setSelectedApprentice()
	}

	@FXML fun setSelectedApprentice()
	{
		val apprentice = apprenticesC.selectionModel.selectedItem
		val Dapprentice = apprentices.getDModel(apprentice)
		this.saveData()
		previouslySelectedApprentice = apprentice

		dialogueTs.indices.forEach { i -> (0..dialogueTs[i].size - 1).forEach { k -> dialogueTs[i][k].text = toString(apprentice.dialogues[i][k]) } }
		DDialogueTs.indices.forEach { i -> (0..DDialogueTs[i].size - 1).forEach { k -> DDialogueTs[i][k].text = toString(Dapprentice.dialogues[i][k]) } }

		// reset data structures to have equipment that can only be equipped by
		// currently selected apprentice
		weapons.clear()
		armors.clear()
		accessories.clear()
		weapons.addUsefulModels(Lists.weapons, apprentice.equipCode)
		armors.addUsefulBodyArmors(Lists.armors, apprentice.equipCode)
		accessories.addUsefulAccessories(Lists.armors, apprentice.equipCode)

		weapons.sortByAscendingPower()
		armors.sortByAscendingPower()
		accessories.sortByAscendingPower()


		apprentice.equipmentAtLevels.withIndex().forEach { (index, entry) ->
			levelTs[index].text = toString(entry.level)
			weaponsC[index].selectionModel.select(weapons.getIndex(entry.equipment[0]))
			armorsC[index].selectionModel.select(armors.getIndex(entry.equipment[1]))
			accessoriesC[index].selectionModel.select(accessories.getIndex(entry.equipment[2]))
		}

		Dapprentice.equipmentAtLevels.withIndex().forEach { (index, entry) ->
			DlevelTs[index].text = toString(entry.level)
			DweaponsT[index].text = weapons.getDName(entry.equipment[0])
			DarmorsT[index].text = armors.getDName(entry.equipment[1])
			DaccessoriesT[index].text = accessories.getDName(entry.equipment[2])
		}
		if (selectedTextFieldIndex < MAX_VALUE) dialogueDisplay.text = TextReader.readText(apprentice.dialoguePointers[selectedTextFieldIndex])
	}

	@FXML fun characterIncrement() = apprenticesC.selectionModel.selectNext()
	@FXML fun characterDecrement() = apprenticesC.selectionModel.selectPrevious()
	@FXML fun removeNonNumbers(event: KeyEvent) = Listeners.removeNonNumbers(event, null)

	override fun saveData()
	{
		val apprentice = previouslySelectedApprentice ?: return

		dialogueTs.indices.forEach { i ->
			(0..dialogueTs[i].size - 1).forEach { k -> apprentice.dialogues[i][k] = parseInt(dialogueTs[i][k].text) }
		}
		val equipmentAtLevel = mutableListOf<EquipmentAtLevel>()
		(0..9).forEach { i -> equipmentAtLevel.add(EquipmentAtLevel(parseInt(levelTs[i].text), intArrayOf(weaponsC[i].selectionModel.selectedItem.itemCode, armorsC[i].selectionModel.selectedItem.itemCode, accessoriesC[i].selectionModel.selectedItem.itemCode))) }
		apprentice.equipmentAtLevels.clear()
		apprentice.equipmentAtLevels = equipmentAtLevel
		apprentices.goldPerLevel = parseInt(goldPerLevelT.text)
	}

	@FXML lateinit var a2eOJ: TextField
	@FXML lateinit var Da2vOF: TextField
	@FXML lateinit var a2wBJ: TextField
	@FXML lateinit var Da2oAA: TextField
	@FXML lateinit var a2eOF: TextField
	@FXML lateinit var Da2vOJ: TextField
	@FXML lateinit var a2oBJ: TextField
	@FXML lateinit var level4T: TextField
	@FXML lateinit var a2kBJ: TextField
	@FXML lateinit var Da2kAA: TextField
	@FXML lateinit var Da2xBJ: TextField
	@FXML lateinit var Da2vNO: TextField
	@FXML lateinit var a2kAS: TextField
	@FXML lateinit var Da2lBJ: TextField
	@FXML lateinit var armor7: ComboBox<Armor>
	@FXML lateinit var armor6: ComboBox<Armor>
	@FXML lateinit var a2wAA: TextField
	@FXML lateinit var armor5: ComboBox<Armor>
	@FXML lateinit var armor4: ComboBox<Armor>
	@FXML lateinit var armor3: ComboBox<Armor>
	@FXML lateinit var armor2: ComboBox<Armor>
	@FXML lateinit var armor8: ComboBox<Armor>
	@FXML lateinit var armor9: ComboBox<Armor>
	@FXML lateinit var armor10: ComboBox<Armor>
	@FXML lateinit var Da2xAS: TextField
	@FXML lateinit var level3T: TextField
	@FXML lateinit var level8T: TextField
	@FXML lateinit var level9T: TextField
	@FXML lateinit var level10T: TextField
	@FXML lateinit var armor1: ComboBox<Armor>
	@FXML lateinit var a2vOJ: TextField
	@FXML lateinit var Da2lAS: TextField
	@FXML lateinit var a2wAS: TextField
	@FXML lateinit var a2eNO: TextField
	@FXML lateinit var Da2xAA: TextField
	@FXML lateinit var a2oAS: TextField
	@FXML lateinit var a2xBJ: TextField
	@FXML lateinit var Da2wOF: TextField
	@FXML lateinit var Da2wOJ: TextField
	@FXML lateinit var Da2kOJ: TextField
	@FXML lateinit var Da2oOF: TextField
	@FXML lateinit var a2vNO: TextField
	@FXML lateinit var level6T: TextField
	@FXML lateinit var a2lBJ: TextField
	@FXML lateinit var Da2oOJ: TextField
	@FXML lateinit var Da2lAA: TextField
	@FXML lateinit var a2oAA: TextField
	@FXML lateinit var a2vOF: TextField
	@FXML lateinit var a2kAA: TextField
	@FXML lateinit var Da2kOF: TextField
	@FXML lateinit var Da2wNO: TextField
	@FXML lateinit var Da2eBJ: TextField
	@FXML lateinit var a2lAS: TextField
	@FXML lateinit var Da2oNO: TextField
	@FXML lateinit var a2xAA: TextField
	@FXML lateinit var level5T: TextField
	@FXML lateinit var a2wOJ: TextField
	@FXML lateinit var Da2kNO: TextField
	@FXML lateinit var weapon6: ComboBox<Weapon>
	@FXML lateinit var weapon7: ComboBox<Weapon>
	@FXML lateinit var weapon4: ComboBox<Weapon>
	@FXML lateinit var weapon5: ComboBox<Weapon>
	@FXML lateinit var a2xAS: TextField
	@FXML lateinit var Da2eAS: TextField
	@FXML lateinit var weapon2: ComboBox<Weapon>
	@FXML lateinit var weapon3: ComboBox<Weapon>
	@FXML lateinit var weapon1: ComboBox<Weapon>
	@FXML lateinit var weapon8: ComboBox<Weapon>
	@FXML lateinit var weapon9: ComboBox<Weapon>
	@FXML lateinit var weapon10: ComboBox<Weapon>
	@FXML lateinit var Da2xOF: TextField
	@FXML lateinit var Da2xOJ: TextField
	@FXML lateinit var Da2eAA: TextField
	@FXML lateinit var a2wNO: TextField
	@FXML lateinit var Da2lOJ: TextField
	@FXML lateinit var Da2vBJ: TextField
	@FXML lateinit var a2wOF: TextField
	@FXML lateinit var a2lAA: TextField
	@FXML lateinit var a2oOJ: TextField
	@FXML lateinit var Da2lOF: TextField
	@FXML lateinit var accessory4: ComboBox<Armor>
	@FXML lateinit var accessory5: ComboBox<Armor>
	@FXML lateinit var a2kOJ: TextField
	@FXML lateinit var a2oOF: TextField
	@FXML lateinit var accessory2: ComboBox<Armor>
	@FXML lateinit var accessory3: ComboBox<Armor>
	@FXML lateinit var Da2xNO: TextField
	@FXML lateinit var a2kOF: TextField
	@FXML lateinit var accessory6: ComboBox<Armor>
	@FXML lateinit var accessory7: ComboBox<Armor>
	@FXML lateinit var accessory8: ComboBox<Armor>
	@FXML lateinit var accessory9: ComboBox<Armor>
	@FXML lateinit var accessory10: ComboBox<Armor>
	@FXML lateinit var Da2lNO: TextField
	@FXML lateinit var accessory1: ComboBox<Armor>
	@FXML lateinit var a2eAS: TextField
	@FXML lateinit var level7T: TextField
	@FXML lateinit var Da2vAS: TextField
	@FXML lateinit var a2xOJ: TextField
	@FXML lateinit var a2oNO: TextField
	@FXML lateinit var a2eBJ: TextField
	@FXML lateinit var a2kNO: TextField
	@FXML lateinit var Da2vAA: TextField
	@FXML lateinit var a2vBJ: TextField
	@FXML lateinit var apprenticesC: ComboBox<Apprentice>
	@FXML lateinit var a2xNO: TextField
	@FXML lateinit var level2T: TextField
	@FXML lateinit var a2xOF: TextField
	@FXML lateinit var Da2wBJ: TextField
	@FXML lateinit var Da2eOJ: TextField
	@FXML lateinit var a2lOJ: TextField
	@FXML lateinit var Da2oBJ: TextField
	@FXML lateinit var a2eAA: TextField
	@FXML lateinit var a2lOF: TextField
	@FXML lateinit var Da2eOF: TextField
	@FXML lateinit var Da2kBJ: TextField
	@FXML lateinit var Da2eNO: TextField
	@FXML lateinit var a2vAA: TextField
	@FXML lateinit var Da2wAS: TextField
	@FXML lateinit var level1T: TextField
	@FXML lateinit var Da2kAS: TextField
	@FXML lateinit var a2lNO: TextField
	@FXML lateinit var Da2oAS: TextField
	@FXML lateinit var a2vAS: TextField
	@FXML lateinit var Da2wAA: TextField
	@FXML lateinit var dialogueDisplay: TextArea
	@FXML lateinit var Dlevel1: TextField
	@FXML lateinit var Dlevel2: TextField
	@FXML lateinit var Dlevel3: TextField
	@FXML lateinit var Dlevel4: TextField
	@FXML lateinit var Dlevel5: TextField
	@FXML lateinit var Dlevel6: TextField
	@FXML lateinit var Dlevel7: TextField
	@FXML lateinit var Dlevel8: TextField
	@FXML lateinit var Dlevel9: TextField
	@FXML lateinit var Dlevel10: TextField
	@FXML lateinit var Dweapon1: TextField
	@FXML lateinit var Dweapon2: TextField
	@FXML lateinit var Dweapon3: TextField
	@FXML lateinit var Dweapon4: TextField
	@FXML lateinit var Dweapon5: TextField
	@FXML lateinit var Dweapon6: TextField
	@FXML lateinit var Dweapon7: TextField
	@FXML lateinit var Dweapon8: TextField
	@FXML lateinit var Dweapon9: TextField
	@FXML lateinit var Dweapon10: TextField
	@FXML lateinit var Darmor1: TextField
	@FXML lateinit var Darmor2: TextField
	@FXML lateinit var Darmor3: TextField
	@FXML lateinit var Darmor4: TextField
	@FXML lateinit var Darmor5: TextField
	@FXML lateinit var Darmor6: TextField
	@FXML lateinit var Darmor7: TextField
	@FXML lateinit var Darmor8: TextField
	@FXML lateinit var Darmor9: TextField
	@FXML lateinit var Darmor10: TextField
	@FXML lateinit var Daccessory1: TextField
	@FXML lateinit var Daccessory2: TextField
	@FXML lateinit var Daccessory3: TextField
	@FXML lateinit var Daccessory4: TextField
	@FXML lateinit var Daccessory5: TextField
	@FXML lateinit var Daccessory6: TextField
	@FXML lateinit var Daccessory7: TextField
	@FXML lateinit var Daccessory8: TextField
	@FXML lateinit var Daccessory9: TextField
	@FXML lateinit var Daccessory10: TextField
	@FXML lateinit var goldPerLevelT: TextField
	@FXML lateinit var DgoldPerLevelT: TextField
	@FXML lateinit var apprenticeUp: Button
	@FXML lateinit var apprenticeDown: Button


	override fun saveState()
	{
		startApprentice = apprenticesC.selectionModel.selectedIndex
	}

	companion object
	{
		private var startApprentice: Int = 0
		private var selectedTextFieldIndex = MAX_VALUE
	}
}
