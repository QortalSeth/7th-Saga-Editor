package application.controllers

import application.ControllerInitilizer
import application.models.Armor
import application.models.Experience
import application.models.Spell
import application.models.Weapon
import application.models.lists.*
import application.staticClasses.Listeners
import expr.Expr
import expr.Parser
import expr.SyntaxException
import expr.Variable
import javafx.fxml.FXML
import javafx.scene.chart.LineChart
import javafx.scene.chart.XYChart
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyEvent
import java.lang.Integer.parseInt
import java.lang.Integer.toString
import java.util.*
import javax.swing.JOptionPane

class CharacterEditor : ControllerInitilizer
{
	private var previouslySelectedCharacter: application.models.Character? = null
	private var previewExp = mutableListOf<Int>()
	private lateinit var spellsC: MutableList<ComboBox<Spell>>
	private lateinit var spellLevelsT: MutableList<TextField>
	private lateinit var DspellsT: MutableList<TextField>
	private lateinit var DspellLevelsT: MutableList<TextField>

	private var weapons = Weapons(Lists.weapons)
	private var armors = Armors(Lists.armors)
	private var accessories = Armors(Lists.armors)

	private var spells = Spells(Lists.spells, true)
	private var characters = Characters(Lists.characters)
	private var experience = Experience(Lists.experience)

	@FXML fun initialize()
	{
		characters.chronologicalIndexSort()
		charactersC.items.addAll(characters.models)
		charactersC.selectionModel.select(startCharacter)

		var i = charUp.graphic as ImageView
		i.image = Image(this.javaClass.getResourceAsStream("images/Triangle.png"))
		i = charDown.graphic as ImageView
		i.image = Image(this.javaClass.getResourceAsStream("images/Triangle.png"))

		createStatsLists()
		addChangeListenerToTextFields()
		initalizeExpTable()
		setSelectedCharacter()
	}

	private fun createStatsLists()
	{
		spellsC = mutableListOf(spell1, spell2, spell3, spell4, spell5, spell6, spell7, spell8, spell9, spell10, spell11, spell12, spell13, spell14, spell15, spell16)
		spellLevelsT = mutableListOf(spellLevel1, spellLevel2, spellLevel3, spellLevel4, spellLevel5, spellLevel6, spellLevel7, spellLevel8, spellLevel9, spellLevel10, spellLevel11, spellLevel12, spellLevel13, spellLevel14, spellLevel15, spellLevel16)
		DspellsT = mutableListOf(defaultSpell1, defaultSpell2, defaultSpell3, defaultSpell4, defaultSpell5, defaultSpell6, defaultSpell7, defaultSpell8, defaultSpell9, defaultSpell10, defaultSpell11, defaultSpell12, defaultSpell13, defaultSpell14, defaultSpell15, defaultSpell16)
		DspellLevelsT = mutableListOf(defaultSpellLevel1, defaultSpellLevel2, defaultSpellLevel3, defaultSpellLevel4, defaultSpellLevel5, defaultSpellLevel6, defaultSpellLevel7, defaultSpellLevel8, defaultSpellLevel9, defaultSpellLevel10, defaultSpellLevel11, defaultSpellLevel12, defaultSpellLevel13, defaultSpellLevel14, defaultSpellLevel15, defaultSpellLevel16)
		weaponStart.items = weapons.models
		armorStart.items = armors.models
		accessoryStart.items = accessories.models

		spellsC.forEach { it.items = spells.models }
	}

	@FXML fun setSelectedCharacter() // when character is selected, fills all data
			// fields with that character's data
	{
		saveData()
		val character = charactersC.selectionModel.selectedItem
		val Dcharacter = characters.getDModel(character)
		previouslySelectedCharacter = character
		image.image = Image(this.javaClass.getResourceAsStream("images/" + character.name + ".png"))

		weapons.clear()
		armors.clear()
		accessories.clear()
		weapons.addUsefulModels(Lists.weapons, character.equipCode)
		armors.addUsefulBodyArmors(Lists.armors, character.equipCode)
		accessories.addUsefulAccessories(Lists.armors, character.equipCode)

		weapons.sortByAscendingPower()
		armors.sortByAscendingPower()
		accessories.sortByAscendingPower()

		hpStart.text = toString(character.hpStart)
		mpStart.text = toString(character.mpStart)
		powerStart.text = toString(character.powerStart)
		guardStart.text = toString(character.guardStart)
		magicStart.text = toString(character.magicStart)
		speedStart.text = toString(character.speedStart)

		defaultHpStart.text = toString(Dcharacter.hpStart)
		defaultMpStart.text = toString(Dcharacter.mpStart)
		defaultPowerStart.text = toString(Dcharacter.powerStart)
		defaultGuardStart.text = toString(Dcharacter.guardStart)
		defaultMagicStart.text = toString(Dcharacter.magicStart)
		defaultSpeedStart.text = toString(Dcharacter.speedStart)

		weaponStart.selectionModel.select(weapons.getIndex(character.weaponStart))
		armorStart.selectionModel.select(armors.getIndex(character.armorStart))
		accessoryStart.selectionModel.select(accessories.getIndex(character.accessoryStart))

		defaultWeaponStart.text = weapons.getDName(Dcharacter.weaponStart)
		defaultArmorStart.text = armors.getDName(Dcharacter.armorStart)
		defaultAccessoryStart.text = accessories.getDName(Dcharacter.accessoryStart)

		expStart.text = toString(character.expStart)
		defaultExpStart.text = toString(Dcharacter.expStart)

		hpGrowth.text = toString(character.hpGrowth)
		mpGrowth.text = toString(character.mpGrowth)
		powerGrowth.text = toString(character.powerGrowth)
		guardGrowth.text = toString(character.guardGrowth)
		magicGrowth.text = toString(character.magicGrowth)
		speedGrowth.text = toString(character.speedGrowth)

		defaultHpGrowth.text = toString(Dcharacter.hpGrowth)
		defaultMpGrowth.text = toString(Dcharacter.mpGrowth)
		defaultPowerGrowth.text = toString(Dcharacter.powerGrowth)
		defaultGuardGrowth.text = toString(Dcharacter.guardGrowth)
		defaultMagicGrowth.text = toString(Dcharacter.magicGrowth)
		defaultSpeedGrowth.text = toString(Dcharacter.speedGrowth)

		spellsC.indices.forEach {
			spellsC[it].selectionModel?.select(spells.getIndex(character!!.spells[it]))
			DspellsT[it].text = spells.getDName(Dcharacter.spells[it])
		}

		spellLevelsT.indices.forEach {
			spellLevelsT[it].text = toString(character.spellLevels[it])
			DspellLevelsT[it].text = toString(Dcharacter.spellLevels[it])
		}
	}

	override fun saveData()
	{
		val c = previouslySelectedCharacter ?: return

		c.hpStart = parseInt(hpStart.text)
		c.mpStart = parseInt(mpStart.text)
		c.powerStart = parseInt(powerStart.text)
		c.guardStart = parseInt(guardStart.text)
		c.magicStart = parseInt(magicStart.text)
		c.speedStart = parseInt(speedStart.text)

		c.weaponStart = weaponStart.selectionModel.selectedItem.itemCode
		c.armorStart = armorStart.selectionModel.selectedItem.itemCode
		c.accessoryStart = accessoryStart.selectionModel.selectedItem.itemCode
		c.expStart = expStart.text.toInt()
		c.hpGrowth = parseInt(hpGrowth.text)
		c.mpGrowth = parseInt(mpGrowth.text)
		c.powerGrowth = parseInt(powerGrowth.text)
		c.guardGrowth = parseInt(guardGrowth.text)
		c.magicGrowth = parseInt(magicGrowth.text)
		c.speedGrowth = parseInt(speedGrowth.text)

		sortSpellsByLevel()

		spellsC.indices.forEach { c.spells[it] = spellsC[it].selectionModel?.selectedItem?.gameIndex!! }
		spellLevelsT.indices.forEach { c.spellLevels[it] = parseInt(spellLevelsT[it].text) }
	}

	@FXML fun characterDecrement() = charactersC.selectionModel.selectPrevious()
	@FXML fun characterIncrement() = charactersC.selectionModel.selectNext()
	@FXML fun removeNonNumbers(event: KeyEvent) = Listeners.removeNonNumbers(event, null)

	private fun addChangeListenerToTextFields()
	{
		hpStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		mpStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		powerStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		guardStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		magicStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		speedStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		expStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255))

		hpGrowth.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		mpGrowth.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		powerGrowth.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		guardGrowth.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		magicGrowth.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		speedGrowth.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		spellLevelsT.forEach { it.focusedProperty()?.addListener(Listeners.textFieldFocusListener(99)) }
	}

	private fun initalizeExpTable()
	{
		expChart.data.clear()
		val defaultExp = XYChart.Series<Int, Int>()
		defaultExp.name = "Default Exp"

		val currentExp = XYChart.Series<Int, Int>()
		currentExp.name = "Current Exp"

		var i = 2
		experience.defaultMarginalExpTable.forEach {
			defaultExp.data.add(XYChart.Data(i, it))
			i++
		}

		i = 2
		experience.marginalExpTable.forEach {
			currentExp.data.add(XYChart.Data(i, it))
			i++
		}

		val previewExp = XYChart.Series<Int, Int>()
		previewExp.name = "Preview Exp"

		expChart.data.add(defaultExp)
		expChart.data.add(currentExp)
		expChart.data.add(previewExp)
	}

	@FXML fun restoreDefaultExpTable()
	{
		experience.marginalExpTable.clear()
		experience.marginalExpTable.addAll(experience.defaultMarginalExpTable)
		initalizeExpTable()
		applyB.isDisable = true
	}

	@FXML fun previewChanges()
	{
		val x = Variable.make("x")
		val parser = Parser()
		parser.allow(x)
		val expr: Expr
		try
		{
			var text = StringBuilder(expression.text)
			var append = 0
			expression.text.withIndex().forEach { (index, value) ->
				if (value == 'x')
				{
					text.insert(index + append, '*')
					append++
				}
			}
			expr = parser.parseString(text.toString())
		}
		catch (e: SyntaxException)
		{
			JOptionPane.showMessageDialog(null, e.explain(), "Error", JOptionPane.ERROR_MESSAGE)
			return
		}

		previewExp = mutableListOf<Int>()
		val previewExpS = XYChart.Series<Int, Int>()
		previewExpS.name = "Preview Exp"

		// XYChart.Series<Integer, Integer> currentExpS =
		// expChart.getData().get(1);
		experience.defaultMarginalExpTable.indices.forEach {
			x.setValue((it + 1).toDouble())
			previewExp.add(expr.value().toInt())
			previewExpS.data.add(XYChart.Data(it + 2, previewExp[it]))
		}

		expChart.data.removeAt(2)
		expChart.data.add(previewExpS)
		applyB.isDisable = false
	}

	@FXML fun applyChanges()
	{
		previewExp.forEach {
			if (it <= 0)
			{
				JOptionPane.showMessageDialog(null, "Exp to next level can't be less than 1", "Error", JOptionPane.ERROR_MESSAGE)
				return
			}
		}

		experience.marginalExpTable.clear()
		experience.marginalExpTable.addAll(previewExp)
		this.initalizeExpTable()
		applyB.isDisable = true
	}

	@FXML fun sortSpellsByLevel()
	{
		class SpellData(var spellIndex: Int, var spellLevel: Int)

		val data = mutableListOf<SpellData>()

		(0..15).forEach { data.add(SpellData(spellsC[it].selectionModel?.selectedIndex!!, parseInt(spellLevelsT[it].text))) }
		data.sortWith(Comparator<SpellData> { s1, s2 ->
			var level1 = s1.spellLevel
			var level2 = s2.spellLevel

			if (level1 == 0) level1 = 99

			if (level2 == 0) level2 = 99

			var difference = level1 - level2

			if (difference == 0) difference = s1.spellIndex - s2.spellIndex

			difference
		})
		(0..15).forEach {
			spellsC[it].selectionModel?.select(data[it].spellIndex)
			spellLevelsT[it].text = toString(data[it].spellLevel)
		}
	}

	@FXML lateinit private var defaultHpStart: TextField
	@FXML lateinit var defaultMpStart: TextField
	@FXML lateinit var defaultPowerStart: TextField
	@FXML lateinit var defaultGuardStart: TextField
	@FXML lateinit var defaultMagicStart: TextField
	@FXML lateinit var defaultSpeedStart: TextField
	@FXML lateinit var defaultWeaponStart: TextField
	@FXML lateinit var defaultArmorStart: TextField
	@FXML lateinit var defaultAccessoryStart: TextField
	@FXML lateinit var defaultHpGrowth: TextField
	@FXML lateinit var defaultMpGrowth: TextField
	@FXML lateinit var defaultPowerGrowth: TextField
	@FXML lateinit var defaultGuardGrowth: TextField
	@FXML lateinit var defaultMagicGrowth: TextField
	@FXML lateinit var defaultSpeedGrowth: TextField

	@FXML lateinit var defaultSpell1: TextField
	@FXML lateinit var defaultSpell2: TextField
	@FXML lateinit var defaultSpell3: TextField
	@FXML lateinit var defaultSpell4: TextField
	@FXML lateinit var defaultSpell5: TextField
	@FXML lateinit var defaultSpell6: TextField
	@FXML lateinit var defaultSpell7: TextField
	@FXML lateinit var defaultSpell8: TextField
	@FXML lateinit var defaultSpell9: TextField
	@FXML lateinit var defaultSpell10: TextField
	@FXML lateinit var defaultSpell11: TextField
	@FXML lateinit var defaultSpell12: TextField
	@FXML lateinit var defaultSpell13: TextField
	@FXML lateinit var defaultSpell14: TextField
	@FXML lateinit var defaultSpell15: TextField
	@FXML lateinit var defaultSpell16: TextField

	@FXML lateinit var defaultSpellLevel1: TextField
	@FXML lateinit var defaultSpellLevel2: TextField
	@FXML lateinit var defaultSpellLevel3: TextField
	@FXML lateinit var defaultSpellLevel4: TextField
	@FXML lateinit var defaultSpellLevel5: TextField
	@FXML lateinit var defaultSpellLevel6: TextField
	@FXML lateinit var defaultSpellLevel7: TextField
	@FXML lateinit var defaultSpellLevel8: TextField
	@FXML lateinit var defaultSpellLevel9: TextField
	@FXML lateinit var defaultSpellLevel10: TextField
	@FXML lateinit var defaultSpellLevel11: TextField
	@FXML lateinit var defaultSpellLevel12: TextField
	@FXML lateinit var defaultSpellLevel13: TextField
	@FXML lateinit var defaultSpellLevel14: TextField
	@FXML lateinit var defaultSpellLevel15: TextField
	@FXML lateinit var defaultSpellLevel16: TextField

	@FXML lateinit var spell7: ComboBox<Spell>
	@FXML lateinit var spell6: ComboBox<Spell>
	@FXML lateinit var spell5: ComboBox<Spell>
	@FXML lateinit var spell4: ComboBox<Spell>
	@FXML lateinit var spell3: ComboBox<Spell>
	@FXML lateinit var spell2: ComboBox<Spell>
	@FXML lateinit var spell1: ComboBox<Spell>
	@FXML lateinit var spell11: ComboBox<Spell>
	@FXML lateinit var spell12: ComboBox<Spell>
	@FXML lateinit var spell9: ComboBox<Spell>
	@FXML lateinit var spell14: ComboBox<Spell>
	@FXML lateinit var spell8: ComboBox<Spell>
	@FXML lateinit var spell13: ComboBox<Spell>
	@FXML lateinit var spell10: ComboBox<Spell>
	@FXML lateinit var spell15: ComboBox<Spell>
	@FXML lateinit var spell16: ComboBox<Spell>

	@FXML lateinit var spellLevel1: TextField
	@FXML lateinit var spellLevel2: TextField
	@FXML lateinit var spellLevel3: TextField
	@FXML lateinit var spellLevel8: TextField
	@FXML lateinit var spellLevel11: TextField
	@FXML lateinit var spellLevel9: TextField
	@FXML lateinit var spellLevel12: TextField
	@FXML lateinit var spellLevel13: TextField
	@FXML lateinit var spellLevel14: TextField
	@FXML lateinit var spellLevel4: TextField
	@FXML lateinit var spellLevel5: TextField
	@FXML lateinit var spellLevel6: TextField
	@FXML lateinit var spellLevel7: TextField
	@FXML lateinit var spellLevel10: TextField
	@FXML lateinit var spellLevel15: TextField
	@FXML lateinit var spellLevel16: TextField
	@FXML lateinit var charactersC: ComboBox<application.models.Character>
	@FXML lateinit var charUp: Button
	@FXML lateinit var charDown: Button
	@FXML lateinit var hpStart: TextField
	@FXML lateinit var mpStart: TextField
	@FXML lateinit var powerStart: TextField
	@FXML lateinit var guardStart: TextField
	@FXML lateinit var magicStart: TextField
	@FXML lateinit var speedStart: TextField
	@FXML lateinit var hpGrowth: TextField
	@FXML lateinit var mpGrowth: TextField
	@FXML lateinit var powerGrowth: TextField
	@FXML lateinit var guardGrowth: TextField
	@FXML lateinit var magicGrowth: TextField
	@FXML lateinit var speedGrowth: TextField
	@FXML lateinit var weaponStart: ComboBox<Weapon>
	@FXML lateinit var armorStart: ComboBox<Armor>
	@FXML lateinit var accessoryStart: ComboBox<Armor>
	@FXML lateinit var expStart: TextField
	@FXML lateinit var defaultExpStart: TextField
	@FXML lateinit var image: ImageView
	@FXML lateinit var expChart: LineChart<Int, Int>
	@FXML lateinit var expression: TextField
	@FXML lateinit var applyB: Button

	override fun saveState()
	{
		startCharacter = charactersC.selectionModel.selectedIndex
	}

	companion object
	{

		private var startCharacter: Int = 0
	}
}
