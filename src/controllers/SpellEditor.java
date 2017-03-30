package controllers;

import application.ControllerInitilizer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.Spell;
import models.lists.Lists;
import models.lists.Spells;
import staticClasses.Listeners;

public class SpellEditor implements ControllerInitilizer
{
    private Spell      previouslySelectedSpell;
    private static int startSpell;
    private Spells     spells;

    @FXML
    void setSelectedSpell()
    {
	this.saveData();
	Spell spell = spellsC.getSelectionModel().getSelectedItem();
	Spell defaultSpell = spells.getDModel(spell);
	previouslySelectedSpell = spell;
	powerT.setText(Integer.toString(spell.getPower()));

	int defaultTargeting = defaultSpell.getTarget();
	if (defaultTargeting == 4)
	{
	    defaultTargeting = 3;
	}
	int targeting = spell.getTarget();
	if (targeting == 4)
	{
	    targeting = 3;
	}
	targetingC.getSelectionModel().select(targeting);
	costT.setText(Integer.toString(spell.getCost()));
	domainC.getSelectionModel().select(spell.getDomain());
	elementC.getSelectionModel().select(spell.getElement());
	unknown1T.setText(Integer.toString(spell.getUnknown1()));
	unknown2T.setText(Integer.toString(spell.getUnknown2()));

	defaultPowerT.setText(Integer.toString(defaultSpell.getPower()));
	defaultTargetingT.setText(targetingC.getItems().get(defaultTargeting));
	defaultCostT.setText(Integer.toString(defaultSpell.getCost()));
	defaultDomainT.setText(domainC.getItems().get(defaultSpell.getDomain()));
	defaultElementT.setText(elementC.getItems().get(defaultSpell.getElement()));
	defaultUnknown1T.setText(Integer.toString(defaultSpell.getUnknown1()));
	defaultUnknown2T.setText(Integer.toString(defaultSpell.getUnknown2()));
    }

    @FXML
    void spellDecrement(MouseEvent event)
    {
	spellsC.getSelectionModel().selectPrevious();
    }

    @FXML
    void spellIncrement(MouseEvent event)
    {
	spellsC.getSelectionModel().selectNext();
    }

    @FXML
    void removeNonNumbers(KeyEvent event)
    {
	Listeners.removeNonNumbers(event, null);
    }

    @Override
    public void saveData()
    {
	Spell s = previouslySelectedSpell;
	if (s == null) { return; }
	s.setPower(Integer.parseInt(powerT.getText()));

	int targeting = targetingC.getSelectionModel().getSelectedIndex();
	if (targeting == 3)
	{
	    targeting++;
	}
	s.setTarget(targeting);

	s.setCost(Integer.parseInt(costT.getText()));
	s.setDomain(domainC.getSelectionModel().getSelectedIndex());
	s.setElement(elementC.getSelectionModel().getSelectedIndex());
	s.setUnknown1(Integer.parseInt(unknown1T.getText()));
	s.setUnknown2(Integer.parseInt(unknown2T.getText()));

    }

    @FXML
    void initialize()
    {
	spells = new Spells();
	spells.addUsefulModels(Lists.getSpells(), false);
	spellsC.setItems(spells.getModels());
	this.addChangeListenerToTextFields();
	this.initializeComboboxes();
	spellsC.getSelectionModel().select(startSpell);
	this.setSelectedSpell();

	ImageView i = (ImageView) spellUp.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));
	i = (ImageView) spellDown.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));
    }

    private void addChangeListenerToTextFields()
    {
	powerT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535));
	costT.focusedProperty().addListener(Listeners.textFieldFocusListener(65535));
	unknown1T.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
	unknown2T.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
    }

    private void initializeComboboxes()
    {
	targetingC.getItems().add("Single Enemy");
	targetingC.getItems().add("All Enemies");
	targetingC.getItems().add("Ally");
	targetingC.getItems().add("Map");

	domainC.getItems().add("All");
	domainC.getItems().add("Battle");
	domainC.getItems().add("Map");

	elementC.getItems().addAll(Spell.getElements());
    }

    @FXML
    private ComboBox<Spell>  spellsC;
    @FXML
    private TextField	     powerT;
    @FXML
    private ComboBox<String> targetingC;
    @FXML
    private TextField	     costT;
    @FXML
    private ComboBox<String> domainC;
    @FXML
    private ComboBox<String> elementC;
    @FXML
    private TextField	     unknown1T;

    @FXML
    private TextField	     unknown2T;

    @FXML
    private TextField	     defaultPowerT;
    @FXML
    private TextField	     defaultTargetingT;
    @FXML
    private TextField	     defaultCostT;
    @FXML
    private TextField	     defaultDomainT;
    @FXML
    private TextField	     defaultElementT;
    @FXML
    private TextField	     defaultUnknown1T;
    @FXML
    private TextField	     defaultUnknown2T;
    @FXML
    private Button	     spellUp;
    @FXML
    private Button	     spellDown;

    @Override
    public void saveState()
    {
	startSpell = spellsC.getSelectionModel().getSelectedIndex();
    }
}
