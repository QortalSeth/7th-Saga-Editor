package application.controllers

import application.ControllerInitilizer
import application.ROM
import application.models.AbstractItem
import application.models.DropTable
import application.models.Monster
import application.models.Spell
import application.models.lists.DropTables
import application.models.lists.Lists
import application.models.lists.Monsters
import application.models.lists.Spells
import application.staticClasses.Listeners
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.value.ChangeListener
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyEvent
import java.lang.Integer.parseInt
import java.lang.Integer.toString

class MonsterEditor : ControllerInitilizer
{
	private var monsters = Monsters(Lists.monsters, false)
	private var dropTables = DropTables(Lists.dropTables)
	private var previouslySelectedMonster: Monster? = null
	private var previouslySelectedDropTable: DropTable? = null

	private var spells = Spells()
	private lateinit var spellsC: MutableList<ComboBox<Spell>>
	private lateinit var spellChanceT: MutableList<TextField>
	private lateinit var DspellsT: MutableList<TextField>
	private lateinit var DspellChanceT: MutableList<TextField>
	private lateinit var itemsC: MutableList<ComboBox<AbstractItem>>
	private lateinit var DitemsT: MutableList<TextField>

	@FXML fun initialize()
	{
		monstersC.items = monsters.models
		itemDropListsC.items = dropTables.models
		spells.addUsefulModels(Lists.spells, true)

		if (gameIndexIsSelected) gameOrderR.isSelected = true
		else
		{
			ChronOrderR.isSelected = true
			chronologicalSort()
		}

		assembleLists()

		monstersC.selectionModel.select(startMonster)
		itemDropListsC.selectionModel.select(startDropTable)
		setSelectedDropTable()
		setSelectedMonster()

		var i = monsterUp.graphic as ImageView
		i.image = Image(javaClass.getResourceAsStream("images/Triangle.png"))
		i = monsterDown.graphic as ImageView
		i.image = Image(javaClass.getResourceAsStream("images/Triangle.png"))

		i = dropUp.graphic as ImageView
		i.image = Image(javaClass.getResourceAsStream("images/Triangle.png"))
		i = dropDown.graphic as ImageView
		i.image = Image(javaClass.getResourceAsStream("images/Triangle.png"))
	}

	@FXML fun setSelectedMonster()
	{
		saveData()
		val monster = monstersC.selectionModel.selectedItem ?: return
		val Dmonster = monsters.getDModel(monster)
		// System.out.println("DMonster is: " + Dmonster.getName() + " gameIndex
		// is: " + Dmonster.getGameIndex());

		previouslySelectedMonster = monster
		unknown1T.text = toString(monster.unknown1)
		hpT.text = toString(monster.hp)
		mpT.text = toString(monster.mp)
		powerT.text = toString(monster.power)
		guardT.text = toString(monster.guard)

		magicT.text = toString(monster.magic)
		speedT.text = toString(monster.speed)
		laserResT.text = toString(monster.laserRes)
		unknown1ResT.text = toString(monster.unknownRes1)
		unknown2ResT.text = toString(monster.unknownRes2)
		fireResT.text = toString(monster.fireRes)
		iceResT.text = toString(monster.iceRes)
		vacuumResT.text = toString(monster.vacuumRes)
		debuffResT.text = toString(monster.debuffRes)
		goldT.text = toString(monster.gold)
		expT.text = toString(monster.experience)
		itemDropT.text = toString(monster.itemDropSet)
		defeatedFlagT.text = toString(monster.unknown2)
		runFlagT.text = toString(monster.runFlag)

		defaultUnknown1T.text = toString(Dmonster.unknown1)
		defaultHpT.text = toString(Dmonster.hp)
		defaultMpT.text = toString(Dmonster.mp)
		defaultPowerT.text = toString(Dmonster.power)
		defaultGuardT.text = toString(Dmonster.guard)
		defaultMagicT.text = toString(Dmonster.magic)
		defaultSpeedT.text = toString(Dmonster.speed)
		defaultLaserResT.text = toString(Dmonster.laserRes)
		defaultUnknown1ResT.text = toString(Dmonster.unknownRes1)
		defaultUnknown2ResT.text = toString(Dmonster.unknownRes2)
		defaultFireResT.text = toString(Dmonster.fireRes)
		defaultIceResT.text = toString(Dmonster.iceRes)
		defaultVacuumResT.text = toString(Dmonster.vacuumRes)
		defaultDebuffResT.text = toString(Dmonster.debuffRes)
		defaultGoldT.text = toString(Dmonster.gold)
		defaultExpT.text = toString(Dmonster.experience)
		defaultItemDropT.text = toString(Dmonster.itemDropSet)
		defaultDefeatedFlagT.text = toString(Dmonster.unknown2)
		defaultRunFlagT.text = toString(Dmonster.runFlag)

		spellsC.indices.forEach {
			spellsC[it].selectionModel?.select(spells.getIndex(monster.spells[it]))
			DspellsT[it].text = spells.getDName(Dmonster.spells[it])
		}

		spellChanceT.indices.forEach {
			spellChanceT[it].text = toString(monster.spellChance[it])
			DspellChanceT[it].text = toString(Dmonster.spellChance[it])
		}
	}

	@FXML fun monsterDecrement() = monstersC.selectionModel.selectPrevious()
	@FXML fun monsterIncrement() = monstersC.selectionModel.selectNext()
	@FXML fun dropListDecrement() = itemDropListsC.selectionModel.selectPrevious()
	@FXML fun dropListIncrement() = itemDropListsC.selectionModel.selectNext()
	@FXML fun removeNonNumbers(event: KeyEvent) = Listeners.removeNonNumbers(event, null)

	@FXML fun gameIndexSort()
	{
		val selectedIndex = monstersC.selectionModel.selectedIndex
		monsters.gameIndexSort()
		monstersC.selectionModel.select(selectedIndex)
		isChronologicalOrderSelected = false
	}

	@FXML fun chronologicalSort()
	{
		val selectedIndex = monstersC.selectionModel.selectedIndex
		monsters.chronologicalIndexSort()
		monstersC.selectionModel.select(selectedIndex)
		isChronologicalOrderSelected = true
	}

	override fun saveData()
	{
		val m = previouslySelectedMonster
		saveItemDropData()
		if (m == null) return

		saveMonster(m)
		m.aliases.forEach { saveMonster(it) }
	}

	private fun saveMonster(m: Monster)
	{
		m.unknown1 = parseInt(unknown1T.text)
		m.hp = parseInt(hpT.text)
		m.mp = parseInt(mpT.text)
		m.power = parseInt(powerT.text)
		m.guard = parseInt(guardT.text)
		m.magic = parseInt(magicT.text)
		m.speed = parseInt(speedT.text)
		m.laserRes = parseInt(laserResT.text)
		m.unknownRes1 = parseInt(unknown1ResT.text)
		m.unknownRes2 = parseInt(unknown2ResT.text)
		m.fireRes = parseInt(fireResT.text)
		m.iceRes = parseInt(iceResT.text)
		m.vacuumRes = parseInt(vacuumResT.text)
		m.debuffRes = parseInt(debuffResT.text)
		m.gold = parseInt(goldT.text)
		expT.text = toString(m.experience)
		m.itemDropSet = parseInt(itemDropT.text)
		m.unknown2 = parseInt(defeatedFlagT.text)
		m.runFlag = parseInt(runFlagT.text)

		spellsC.indices.forEach { m.spells[it] = spellsC[it].selectionModel!!.selectedItem!!.gameIndex }
		spellChanceT.indices.forEach { m.spellChance[it] = parseInt(spellChanceT[it].text) }
	}

	@FXML fun setSelectedDropTable()
	{
		saveItemDropData()
		val dropTable = itemDropListsC.selectionModel.selectedItem
		val DdropTable = dropTables.getDModel(dropTable)

		previouslySelectedDropTable = dropTable

		dropTable.drops.withIndex().forEach { (index, itemCode) ->
			// System.out.println("selected itemcode is: "+itemCode);
			itemsC[index].selectionModel?.select(Lists.abstractItems.getIndex(itemCode))
		}

		DitemsT.withIndex().forEach { (index, t) -> t.text = Lists.abstractItems.getDName(DdropTable.drops[index]) }
	}

	fun saveItemDropData()
	{
		val d = previouslySelectedDropTable ?: return
		d.drops.withIndex().forEach { (index, i) ->
			//			print("index is: $index  i is: $i  ")
			//			println("d.drops[i]: ${d.drops[index]}  selected item: ${itemsC[index].selectionModel.selectedItem.itemCode} ")
			d.drops[index] = itemsC[index].selectionModel.selectedItem.itemCode
		}
	}


	private fun assembleLists()
	{
		spellsC = mutableListOf(spell1, spell2, spell3, spell4, spell5, spell6, spell7, spell8)
		spellChanceT = mutableListOf(spellChance1, spellChance2, spellChance3, spellChance4, spellChance5, spellChance6, spellChance7, spellChance8)
		DspellsT = mutableListOf(defaultSpell1, defaultSpell2, defaultSpell3, defaultSpell4, defaultSpell5, defaultSpell6, defaultSpell7, defaultSpell8)
		DspellChanceT = mutableListOf(defaultSpellChance1, defaultSpellChance2, defaultSpellChance3, defaultSpellChance4, defaultSpellChance5, defaultSpellChance6, defaultSpellChance7, defaultSpellChance8)
		itemsC = mutableListOf(item1C, item2C, item3C, item4C, item5C, item6C, item7C, item8C, item9C, item10C, item11C, item12C, item13C, item14C, item15C, item16C)
		DitemsT = mutableListOf(defaultItem1T, defaultItem2T, defaultItem3T, defaultItem4T, defaultItem5T, defaultItem6T, defaultItem7T, defaultItem8T, defaultItem9T, defaultItem10T, defaultItem11T, defaultItem12T, defaultItem13T, defaultItem14T, defaultItem15T, defaultItem16T)

		spellsC.forEach { it.items = spells.models }
		itemsC.forEach { it.items = Lists.abstractItems.models }

		unknown1T.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		hpT.focusedProperty().addListener(Listeners.textFieldFocusListener(9999))
		mpT.focusedProperty().addListener(Listeners.textFieldFocusListener(9999))
		powerT.focusedProperty().addListener(Listeners.textFieldFocusListener(999))
		guardT.focusedProperty().addListener(Listeners.textFieldFocusListener(9999))

		if (ROM.magicAndSpeedPatch)
		{
			magicT.focusedProperty().addListener(Listeners.textFieldFocusListener(999))
			speedT.focusedProperty().addListener(Listeners.textFieldFocusListener(999))
		}
		else
		{
			magicT.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
			speedT.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		}
		laserResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		unknown1ResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		unknown2ResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		fireResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		iceResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		vacuumResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		debuffResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100))
		goldT.focusedProperty().addListener(// limit gold given from battle to prevent exp overflow
				ChangeListener<Boolean> { observable, oldValue, newValue ->
					if (!newValue)
					{
						val a = observable as ReadOnlyBooleanProperty
						val tf = a.bean as TextField

						if (tf.text.isEmpty())
						{
							tf.text = "0"
							return@ChangeListener
						}

						var value = parseInt(tf.text)

						if (value < 0) value = 0
						value = maxOf(value, 0)

						val maxValue = 29000
						value = minOf(value, maxValue)

						tf.text = toString(value)
						expT.text = toString(Monster.goldToExp(value))
					}
				})
		itemDropT.focusedProperty().addListener(Listeners.textFieldFocusListener(20))
		defeatedFlagT.focusedProperty().addListener(Listeners.textFieldFocusListener(255))

		spellChanceT.forEach { it.focusedProperty()?.addListener(Listeners.textFieldFocusListener(100)) }

	}

	@FXML lateinit var defaultItem10T: TextField
	@FXML lateinit var defaultItem11T: TextField
	@FXML lateinit var defaultItem12T: TextField
	@FXML lateinit var defaultItem13T: TextField
	@FXML lateinit var defaultItem14T: TextField
	@FXML lateinit var defaultItem15T: TextField
	@FXML lateinit var defaultItem16T: TextField
	@FXML lateinit var defaultItem1T: TextField
	@FXML lateinit var defaultItem2T: TextField
	@FXML lateinit var defaultItem3T: TextField
	@FXML lateinit var defaultItem4T: TextField
	@FXML lateinit var defaultItem5T: TextField
	@FXML lateinit var defaultItem6T: TextField
	@FXML lateinit var defaultItem7T: TextField
	@FXML lateinit var defaultItem8T: TextField
	@FXML lateinit var defaultItem9T: TextField
	@FXML lateinit var defaultItemDropT: TextField
	@FXML lateinit var defaultMagicT: TextField
	@FXML lateinit var defaultMpT: TextField
	@FXML lateinit var defaultPowerT: TextField
	@FXML lateinit var defaultRunFlagT: TextField
	@FXML lateinit var defaultSpeedT: TextField
	@FXML lateinit var defaultSpell1: TextField
	@FXML lateinit var defaultSpell2: TextField
	@FXML lateinit var defaultSpell3: TextField
	@FXML lateinit var defaultSpell4: TextField
	@FXML lateinit var defaultSpell5: TextField
	@FXML lateinit var defaultSpell6: TextField
	@FXML lateinit var defaultSpell7: TextField
	@FXML lateinit var defaultSpell8: TextField
	@FXML lateinit var defaultSpellChance1: TextField
	@FXML lateinit var defaultSpellChance2: TextField
	@FXML lateinit var defaultSpellChance3: TextField
	@FXML lateinit var defaultSpellChance4: TextField
	@FXML lateinit var defaultSpellChance5: TextField
	@FXML lateinit var defaultSpellChance6: TextField
	@FXML lateinit var defaultSpellChance7: TextField
	@FXML lateinit var defaultSpellChance8: TextField
	@FXML lateinit var item10C: ComboBox<AbstractItem>
	@FXML lateinit var item11C: ComboBox<AbstractItem>
	@FXML lateinit var item12C: ComboBox<AbstractItem>
	@FXML lateinit var item13C: ComboBox<AbstractItem>
	@FXML lateinit var item14C: ComboBox<AbstractItem>
	@FXML lateinit var item15C: ComboBox<AbstractItem>
	@FXML lateinit var item16C: ComboBox<AbstractItem>
	@FXML lateinit var item1C: ComboBox<AbstractItem>
	@FXML lateinit var item2C: ComboBox<AbstractItem>
	@FXML lateinit var item3C: ComboBox<AbstractItem>
	@FXML lateinit var item4C: ComboBox<AbstractItem>
	@FXML lateinit var item5C: ComboBox<AbstractItem>
	@FXML lateinit var item6C: ComboBox<AbstractItem>
	@FXML lateinit var item7C: ComboBox<AbstractItem>
	@FXML lateinit var item8C: ComboBox<AbstractItem>
	@FXML lateinit var item9C: ComboBox<AbstractItem>
	@FXML lateinit var spell1: ComboBox<Spell>
	@FXML lateinit var spell2: ComboBox<Spell>
	@FXML lateinit var spell3: ComboBox<Spell>
	@FXML lateinit var spell4: ComboBox<Spell>
	@FXML lateinit var spell5: ComboBox<Spell>
	@FXML lateinit var spell6: ComboBox<Spell>
	@FXML lateinit var spell7: ComboBox<Spell>
	@FXML lateinit var spell8: ComboBox<Spell>
	@FXML lateinit var spellChance1: TextField
	@FXML lateinit var spellChance2: TextField
	@FXML lateinit var spellChance3: TextField
	@FXML lateinit var spellChance4: TextField
	@FXML lateinit var spellChance5: TextField
	@FXML lateinit var spellChance6: TextField
	@FXML lateinit var spellChance7: TextField
	@FXML lateinit var spellChance8: TextField

	@FXML lateinit var monsterDown: Button
	@FXML lateinit var monsterUp: Button
	@FXML lateinit var dropUp: Button
	@FXML lateinit var dropDown: Button
	@FXML lateinit var ChronOrderR: RadioButton
	@FXML lateinit var defaultExpT: TextField
	@FXML lateinit var defaultGoldT: TextField
	@FXML lateinit var defaultGuardT: TextField
	@FXML lateinit var defaultHpT: TextField
	@FXML lateinit var defaultUnknown1T: TextField
	@FXML lateinit var defaultDefeatedFlagT: TextField
	@FXML lateinit var expT: TextField
	@FXML lateinit var gameOrderR: RadioButton
	@FXML lateinit var goldT: TextField
	@FXML lateinit var guardT: TextField
	@FXML lateinit var hpT: TextField
	@FXML lateinit var itemDropListsC: ComboBox<DropTable>
	@FXML lateinit var monstersC: ComboBox<Monster>
	@FXML lateinit var itemDropT: TextField
	@FXML lateinit var magicT: TextField
	@FXML lateinit var mpT: TextField
	@FXML lateinit var powerT: TextField
	@FXML lateinit var runFlagT: TextField
	@FXML lateinit var sortMonsters: ToggleGroup
	@FXML lateinit var speedT: TextField
	@FXML lateinit var unknown1T: TextField
	@FXML lateinit var defeatedFlagT: TextField
	@FXML lateinit var laserResT: TextField
	@FXML lateinit var unknown1ResT: TextField
	@FXML lateinit var unknown2ResT: TextField
	@FXML lateinit var fireResT: TextField
	@FXML lateinit var iceResT: TextField
	@FXML lateinit var vacuumResT: TextField
	@FXML lateinit var debuffResT: TextField
	@FXML lateinit var defaultLaserResT: TextField
	@FXML lateinit var defaultUnknown1ResT: TextField
	@FXML lateinit var defaultUnknown2ResT: TextField
	@FXML lateinit var defaultFireResT: TextField
	@FXML lateinit var defaultIceResT: TextField
	@FXML lateinit var defaultVacuumResT: TextField
	@FXML lateinit var defaultDebuffResT: TextField


	override fun saveState()
	{
		startMonster = monstersC.selectionModel.selectedIndex
		startDropTable = itemDropListsC.selectionModel.selectedIndex
		gameIndexIsSelected = gameOrderR.isSelected
	}

	companion object
	{

		private var startMonster: Int = 0
		private var startDropTable: Int = 0
		private var gameIndexIsSelected = false

		var isChronologicalOrderSelected = true
			private set
	}

}