package application.controllers

import application.ControllerInitilizer
import application.models.Spell
import application.models.lists.Lists
import application.models.lists.Spells
import application.staticClasses.Listeners
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyEvent

class SpellEditor : ControllerInitilizer
{
	private var previouslySelectedSpell: Spell? = null
	private var spells = Spells(Lists.spells, false)

	@FXML fun setSelectedSpell()
	{
		this.saveData()
		val spell = spellsC.selectionModel.selectedItem
		val defaultSpell = spells.getDModel(spell)
		previouslySelectedSpell = spell
		powerT.text = Integer.toString(spell.power)

		var defaultTargeting = defaultSpell.target
		if (defaultTargeting == 4) defaultTargeting = 3

		var targeting = spell.target
		if (targeting == 4) targeting = 3
		targetingC.selectionModel.select(targeting)
		costT.text = Integer.toString(spell.cost)
		domainC.selectionModel.select(spell.domain)
		elementC.selectionModel.select(spell.element)
		unknown1T.text = Integer.toString(spell.unknown1)
		unknown2T.text = Integer.toString(spell.unknown2)

		defaultPowerT.text = Integer.toString(defaultSpell.power)
		defaultTargetingT.text = targetingC.items[defaultTargeting]
		defaultCostT.text = Integer.toString(defaultSpell.cost)
		defaultDomainT.text = domainC.items[defaultSpell.domain]
		defaultElementT.text = elementC.items[defaultSpell.element]
		defaultUnknown1T.text = Integer.toString(defaultSpell.unknown1)
		defaultUnknown2T.text = Integer.toString(defaultSpell.unknown2)
	}

	@FXML fun spellDecrement() = spellsC.selectionModel.selectPrevious()

	@FXML fun spellIncrement() = spellsC.selectionModel.selectNext()

	@FXML fun removeNonNumbers(event: KeyEvent) = Listeners.removeNonNumbers(event, null)

	override fun saveData()
	{
		val s = previouslySelectedSpell ?: return
		s.power = Integer.parseInt(powerT.text)

		var targeting = targetingC.selectionModel.selectedIndex
		if (targeting == 3)
		{
			targeting++
		}
		s.target = targeting

		s.cost = Integer.parseInt(costT.text)
		s.domain = domainC.selectionModel.selectedIndex
		s.element = elementC.selectionModel.selectedIndex
		s.unknown1 = Integer.parseInt(unknown1T.text)
		s.unknown2 = Integer.parseInt(unknown2T.text)

	}

	@FXML fun initialize()
	{
		spells.chronologicalIndexSort()
		spellsC.items = spells.models
		addChangeListenerToTextFields()
		initializeComboboxes()
		spellsC.selectionModel.select(startSpell)
		this.setSelectedSpell()

		var i = spellUp.graphic as ImageView
		i.image = Image(this.javaClass.getResourceAsStream("images/Triangle.png"))
		i = spellDown.graphic as ImageView
		i.image = Image(this.javaClass.getResourceAsStream("images/Triangle.png"))
	}

	private fun addChangeListenerToTextFields()
	{
		powerT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535))
		costT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535))
		unknown1T.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
		unknown2T.focusedProperty().addListener(Listeners.textFieldFocusListener(255))
	}

	private fun initializeComboboxes()
	{
		targetingC.items = FXCollections.observableArrayList("Single Enemy", "All Enemies", "Ally", "Map")
		domainC.items = FXCollections.observableArrayList("All", "Battle", "Map")
		elementC.items.addAll(Spell.elementText)
	}

	@FXML lateinit var spellsC: ComboBox<Spell>
	@FXML lateinit var powerT: TextField
	@FXML lateinit var targetingC: ComboBox<String>
	@FXML lateinit var costT: TextField
	@FXML lateinit var domainC: ComboBox<String>
	@FXML lateinit var elementC: ComboBox<String>
	@FXML lateinit var unknown1T: TextField
	@FXML lateinit var unknown2T: TextField
	@FXML lateinit var defaultPowerT: TextField
	@FXML lateinit var defaultTargetingT: TextField
	@FXML lateinit var defaultCostT: TextField
	@FXML lateinit var defaultDomainT: TextField
	@FXML lateinit var defaultElementT: TextField
	@FXML lateinit var defaultUnknown1T: TextField
	@FXML lateinit var defaultUnknown2T: TextField
	@FXML lateinit var spellUp: Button
	@FXML lateinit var spellDown: Button

	override fun saveState()
	{
		startSpell = spellsC.selectionModel.selectedIndex
	}

	companion object
	{
		private var startSpell: Int = 0
	}
}
