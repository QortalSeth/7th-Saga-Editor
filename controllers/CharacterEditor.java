package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.ControllerInitilizer;
import expr.Expr;
import expr.Parser;
import expr.SyntaxException;
import expr.Variable;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.Armor;
import models.Experience;
import models.Spell;
import models.Weapon;
import models.lists.Armors;
import models.lists.Characters;
import models.lists.Lists;
import models.lists.Spells;
import models.lists.Weapons;
import staticClasses.Listeners;

public class CharacterEditor implements ControllerInitilizer
{
    private models.Character	  previouslySelectedCharacter;
    private List<Integer>	  previewExp;

    private List<ComboBox<Spell>> spellsC	= new ArrayList<ComboBox<Spell>>();
    private List<TextField>	  spellLevelsT	= new ArrayList<TextField>();
    private List<TextField>	  DspellsT	= new ArrayList<TextField>();
    private List<TextField>	  DspellLevelsT	= new ArrayList<TextField>();

    private Weapons		  weapons;
    private Armors		  armors;
    private Armors		  accessories;
    private Spells		  spells;
    private Characters		  characters;

    private static int		  startCharacter;

    @FXML
    void initialize()
    {

	characters = new Characters(Lists.getCharacters());
	characters.chronologicalIndexSort();
	charactersC.setItems(characters.getModels());
	charactersC.getSelectionModel().select(startCharacter);

	weapons = new Weapons();
	armors = new Armors();
	accessories = new Armors();
	spells = new Spells(Lists.getSpells(), true);

	ImageView i = (ImageView) charUp.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));
	i = (ImageView) charDown.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));

	this.createStatsLists();
	this.addChangeListenerToTextFields();
	this.initalizeExpTable();
	this.setSelectedCharacter();
    }

    private void createStatsLists()
    {
	spellsC.add(spell1);
	spellsC.add(spell2);
	spellsC.add(spell3);
	spellsC.add(spell4);
	spellsC.add(spell5);
	spellsC.add(spell6);
	spellsC.add(spell7);
	spellsC.add(spell8);
	spellsC.add(spell9);
	spellsC.add(spell10);
	spellsC.add(spell11);
	spellsC.add(spell12);
	spellsC.add(spell13);
	spellsC.add(spell14);
	spellsC.add(spell15);
	spellsC.add(spell16);

	spellLevelsT.add(spellLevel1);
	spellLevelsT.add(spellLevel2);
	spellLevelsT.add(spellLevel3);
	spellLevelsT.add(spellLevel4);
	spellLevelsT.add(spellLevel5);
	spellLevelsT.add(spellLevel6);
	spellLevelsT.add(spellLevel7);
	spellLevelsT.add(spellLevel8);
	spellLevelsT.add(spellLevel9);
	spellLevelsT.add(spellLevel10);
	spellLevelsT.add(spellLevel11);
	spellLevelsT.add(spellLevel12);
	spellLevelsT.add(spellLevel13);
	spellLevelsT.add(spellLevel14);
	spellLevelsT.add(spellLevel15);
	spellLevelsT.add(spellLevel16);

	DspellsT.add(defaultSpell1);
	DspellsT.add(defaultSpell2);
	DspellsT.add(defaultSpell3);
	DspellsT.add(defaultSpell4);
	DspellsT.add(defaultSpell5);
	DspellsT.add(defaultSpell6);
	DspellsT.add(defaultSpell7);
	DspellsT.add(defaultSpell8);
	DspellsT.add(defaultSpell9);
	DspellsT.add(defaultSpell10);
	DspellsT.add(defaultSpell11);
	DspellsT.add(defaultSpell12);
	DspellsT.add(defaultSpell13);
	DspellsT.add(defaultSpell14);
	DspellsT.add(defaultSpell15);
	DspellsT.add(defaultSpell16);

	DspellLevelsT.add(defaultSpellLevel1);
	DspellLevelsT.add(defaultSpellLevel2);
	DspellLevelsT.add(defaultSpellLevel3);
	DspellLevelsT.add(defaultSpellLevel4);
	DspellLevelsT.add(defaultSpellLevel5);
	DspellLevelsT.add(defaultSpellLevel6);
	DspellLevelsT.add(defaultSpellLevel7);
	DspellLevelsT.add(defaultSpellLevel8);
	DspellLevelsT.add(defaultSpellLevel9);
	DspellLevelsT.add(defaultSpellLevel10);
	DspellLevelsT.add(defaultSpellLevel11);
	DspellLevelsT.add(defaultSpellLevel12);
	DspellLevelsT.add(defaultSpellLevel13);
	DspellLevelsT.add(defaultSpellLevel14);
	DspellLevelsT.add(defaultSpellLevel15);
	DspellLevelsT.add(defaultSpellLevel16);

	weaponStart.setItems(weapons.getModels());
	armorStart.setItems(armors.getModels());
	accessoryStart.setItems(accessories.getModels());

	for (ComboBox<Spell> spell : spellsC)
	{
	    spell.setItems(spells.getModels());
	}
    }

    @FXML
    void setSelectedCharacter() // when character is selected, fills all data
				// fields with that character's data
    {
	saveData();
	models.Character character = charactersC.getSelectionModel().getSelectedItem();
	models.Character Dcharacter = characters.getDModel(character);
	previouslySelectedCharacter = character;
	image.setImage(new Image(this.getClass().getResourceAsStream("images/" + character.getName() + ".png")));

	weapons.clear();
	armors.clear();
	accessories.clear();
	weapons.addUsefulModels(Lists.getWeapons(), character.getEquipCode());
	armors.addUsefulBodyArmors(Lists.getArmors(), character.getEquipCode());
	accessories.addUsefulAccessories(Lists.getArmors(), character.getEquipCode());

	weapons.sortByAscendingPower();
	armors.sortByAscendingPower();
	accessories.sortByAscendingPower();

	hpStart.setText(Integer.toString(character.getHpStart()));
	mpStart.setText(Integer.toString(character.getMpStart()));
	powerStart.setText(Integer.toString(character.getPowerStart()));
	guardStart.setText(Integer.toString(character.getGuardStart()));
	magicStart.setText(Integer.toString(character.getMagicStart()));
	speedStart.setText(Integer.toString(character.getSpeedStart()));

	defaultHpStart.setText(Integer.toString(Dcharacter.getHpStart()));
	defaultMpStart.setText(Integer.toString(Dcharacter.getMpStart()));
	defaultPowerStart.setText(Integer.toString(Dcharacter.getPowerStart()));
	defaultGuardStart.setText(Integer.toString(Dcharacter.getGuardStart()));
	defaultMagicStart.setText(Integer.toString(Dcharacter.getMagicStart()));
	defaultSpeedStart.setText(Integer.toString(Dcharacter.getSpeedStart()));

	weaponStart.getSelectionModel().select(weapons.getIndex(character.getWeaponStart()));
	armorStart.getSelectionModel().select(armors.getIndex(character.getWeaponStart()));
	accessoryStart.getSelectionModel().select(accessories.getIndex(character.getWeaponStart()));

	defaultWeaponStart.setText(weapons.getDName(Dcharacter.getWeaponStart()));
	defaultArmorStart.setText(armors.getDName(Dcharacter.getArmorStart()));
	defaultAccessoryStart.setText(accessories.getDName(Dcharacter.getAccessoryStart()));

	expStart.setText(Integer.toString(character.getExpStart()));
	defaultExpStart.setText(Integer.toString(Dcharacter.getExpStart()));

	hpGrowth.setText(Integer.toString(character.getHpGrowth()));
	mpGrowth.setText(Integer.toString(character.getMpGrowth()));
	powerGrowth.setText(Integer.toString(character.getPowerGrowth()));
	guardGrowth.setText(Integer.toString(character.getGuardGrowth()));
	magicGrowth.setText(Integer.toString(character.getMagicGrowth()));
	speedGrowth.setText(Integer.toString(character.getSpeedGrowth()));

	defaultHpGrowth.setText(Integer.toString(Dcharacter.getHpGrowth()));
	defaultMpGrowth.setText(Integer.toString(Dcharacter.getMpGrowth()));
	defaultPowerGrowth.setText(Integer.toString(Dcharacter.getPowerGrowth()));
	defaultGuardGrowth.setText(Integer.toString(Dcharacter.getGuardGrowth()));
	defaultMagicGrowth.setText(Integer.toString(Dcharacter.getMagicGrowth()));
	defaultSpeedGrowth.setText(Integer.toString(Dcharacter.getSpeedGrowth()));

	for (int i = 0; i < spellsC.size(); i++)
	{
	    spellsC.get(i).getSelectionModel().select(spells.getIndex(character.getSpells().get(i)));
	    DspellsT.get(i).setText(spells.getDName(Dcharacter.getSpells().get(i)));
	}

	for (int i = 0; i < spellLevelsT.size(); i++)
	{
	    spellLevelsT.get(i).setText(Integer.toString(character.getSpellLevels().get(i)));
	    DspellLevelsT.get(i).setText((Integer.toString(Dcharacter.getSpellLevels().get(i))));
	}
    }

    @Override
    public void saveData()
    {
	models.Character c = previouslySelectedCharacter;
	if (c == null) { return; }

	c.setHpStart(Integer.parseInt(hpStart.getText()));
	c.setMpStart(Integer.parseInt(mpStart.getText()));
	c.setPowerStart(Integer.parseInt(powerStart.getText()));
	c.setGuardStart(Integer.parseInt(guardStart.getText()));
	c.setMagicStart(Integer.parseInt(magicStart.getText()));
	c.setSpeedStart(Integer.parseInt(speedStart.getText()));

	c.setWeaponStart(weaponStart.getSelectionModel().getSelectedItem().getItemCode());
	c.setArmorStart(armorStart.getSelectionModel().getSelectedItem().getItemCode());
	c.setAccessoryStart(accessoryStart.getSelectionModel().getSelectedItem().getItemCode());

	c.setHpGrowth(Integer.parseInt(hpGrowth.getText()));
	c.setMpGrowth(Integer.parseInt(mpGrowth.getText()));
	c.setPowerGrowth(Integer.parseInt(powerGrowth.getText()));
	c.setGuardGrowth(Integer.parseInt(guardGrowth.getText()));
	c.setMagicGrowth(Integer.parseInt(magicGrowth.getText()));
	c.setSpeedGrowth(Integer.parseInt(speedGrowth.getText()));
	
	sortSpellsByLevel(null);

	for (int i = 0; i < spellsC.size(); i++)
	{
	    c.getSpells().set(i, spellsC.get(i).getSelectionModel().getSelectedItem().getGameIndex());
	}

	for (int i = 0; i < spellLevelsT.size(); i++)
	{
	    c.getSpellLevels().set(i, Integer.parseInt(spellLevelsT.get(i).getText()));
	}

    }

    @FXML
    void characterDecrement(MouseEvent event)
    {
	charactersC.getSelectionModel().selectPrevious();
    }

    @FXML
    void characterIncrement(MouseEvent event)
    {
	charactersC.getSelectionModel().selectNext();
    }

    @FXML
    void removeNonNumbers(KeyEvent event)
    {
	Listeners.removeNonNumbers(event, null);
    }

    private void addChangeListenerToTextFields()
    {
	hpStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
	mpStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
	powerStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
	guardStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
	magicStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
	speedStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
	expStart.focusedProperty().addListener(Listeners.textFieldFocusListener(255));

	hpGrowth.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
	mpGrowth.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
	powerGrowth.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
	guardGrowth.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
	magicGrowth.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
	speedGrowth.focusedProperty().addListener(Listeners.textFieldFocusListener(255));

	for (TextField tf : spellLevelsT)
	{
	    tf.focusedProperty().addListener(Listeners.textFieldFocusListener(99));
	}
    }

    private void initalizeExpTable()
    {
	expChart.getData().clear();
	XYChart.Series<Integer, Integer> defaultExp = new XYChart.Series<Integer, Integer>();
	defaultExp.setName("Default Exp");

	XYChart.Series<Integer, Integer> currentExp = new XYChart.Series<Integer, Integer>();
	currentExp.setName("Current Exp");

	int i = 2;
	for (Integer expToNextLevel : Experience.getDefaultMarginalExpTable())
	{
	    defaultExp.getData().add(new XYChart.Data<Integer, Integer>(i, expToNextLevel));
	    i++;
	}

	i = 2;
	for (Integer expToNextLevel : Experience.getMarginalExpTable())
	{
	    currentExp.getData().add(new XYChart.Data<Integer, Integer>(i, expToNextLevel));
	    i++;
	}

	XYChart.Series<Integer, Integer> previewExp = new XYChart.Series<Integer, Integer>();
	previewExp.setName("Preview Exp");

	expChart.getData().add(defaultExp);
	expChart.getData().add(currentExp);
	expChart.getData().add(previewExp);
    }

    @FXML
    void restoreDefaultExpTable(MouseEvent event)
    {
	Experience.getMarginalExpTable().clear();
	Experience.getMarginalExpTable().addAll(Experience.getDefaultMarginalExpTable());
	this.initalizeExpTable();
	applyB.setDisable(true);
    }

    @FXML
    void previewChanges(MouseEvent event)
    {
	Variable x = Variable.make("x");
	Parser parser = new Parser();
	parser.allow(x);
	Expr expr;
	try
	{
	    expr = parser.parseString(expression.getText());
	}
	catch (SyntaxException e)
	{
	    JOptionPane.showMessageDialog(null, e.explain(), "Error", JOptionPane.ERROR_MESSAGE);
	    return;
	}

	previewExp = new ArrayList<Integer>();
	XYChart.Series<Integer, Integer> previewExpS = new XYChart.Series<Integer, Integer>();
	previewExpS.setName("Preview Exp");

	// XYChart.Series<Integer, Integer> currentExpS =
	// expChart.getData().get(1);
	for (int i = 0; i < Experience.getDefaultMarginalExpTable().size(); i++)
	{
	    x.setValue(i + 1);
	    previewExp.add((int) expr.value());
	    previewExpS.getData().add(new XYChart.Data<Integer, Integer>(i + 2, previewExp.get(i)));
	}

	expChart.getData().remove(2);
	expChart.getData().add(previewExpS);
	applyB.setDisable(false);
    }

    @FXML
    void applyChanges(MouseEvent event)
    {
	for (Integer i : previewExp)
	{
	    if (i <= 0)
	    {
		JOptionPane.showMessageDialog(null, "Exp to next level can't be less than 1", "Error", JOptionPane.ERROR_MESSAGE);
		return;
	    }
	}

	Experience.getMarginalExpTable().clear();
	Experience.getMarginalExpTable().addAll(previewExp);
	this.initalizeExpTable();
	applyB.setDisable(true);
    }
    
    @FXML
    void sortSpellsByLevel(MouseEvent event)
    {
    	class SpellData
    	{
    		int spellIndex;
    		int spellLevel;
    		public SpellData(int index, int level){spellIndex=index; spellLevel=level;}
    	}
    	List<SpellData> data = new ArrayList<>();
    	
    	for(int i=0; i<16; i++)
    	{
    	data.add(new SpellData(spellsC.get(i).getSelectionModel().getSelectedIndex(), 
    	      Integer.parseInt(spellLevelsT.get(i).getText())));
    	}
    	data.sort(new Comparator<SpellData>(){

			@Override
			public int compare(SpellData s1, SpellData s2) {
				int level1 = s1.spellLevel;
				int level2 = s2.spellLevel;
				
				if(level1==0)
				{level1=99;}
				
				if(level2==0)
				{level2=99;}
				
				int difference = level1-level2;
				
				if(difference==0)
				{difference =s1.spellIndex-s2.spellIndex;}
				
				return difference;
			}});
    	for(int i=0; i<16; i++)
    	{
    		spellsC.get(i).getSelectionModel().select(data.get(i).spellIndex);
    		spellLevelsT.get(i).setText(Integer.toString(data.get(i).spellLevel));
    	}
    }

    @FXML
    private TextField			defaultHpStart;
    @FXML
    private TextField			defaultMpStart;
    @FXML
    private TextField			defaultPowerStart;
    @FXML
    private TextField			defaultGuardStart;
    @FXML
    private TextField			defaultMagicStart;
    @FXML
    private TextField			defaultSpeedStart;
    @FXML
    private TextField			defaultWeaponStart;
    @FXML
    private TextField			defaultArmorStart;
    @FXML
    private TextField			defaultAccessoryStart;
    @FXML
    private TextField			defaultHpGrowth;
    @FXML
    private TextField			defaultMpGrowth;
    @FXML
    private TextField			defaultPowerGrowth;
    @FXML
    private TextField			defaultGuardGrowth;
    @FXML
    private TextField			defaultMagicGrowth;
    @FXML
    private TextField			defaultSpeedGrowth;

    @FXML
    private TextField			defaultSpell1;
    @FXML
    private TextField			defaultSpell2;
    @FXML
    private TextField			defaultSpell3;
    @FXML
    private TextField			defaultSpell4;
    @FXML
    private TextField			defaultSpell5;
    @FXML
    private TextField			defaultSpell6;
    @FXML
    private TextField			defaultSpell7;
    @FXML
    private TextField			defaultSpell8;
    @FXML
    private TextField			defaultSpell9;
    @FXML
    private TextField			defaultSpell10;
    @FXML
    private TextField			defaultSpell11;
    @FXML
    private TextField			defaultSpell12;
    @FXML
    private TextField			defaultSpell13;
    @FXML
    private TextField			defaultSpell14;
    @FXML
    private TextField			defaultSpell15;
    @FXML
    private TextField			defaultSpell16;

    @FXML
    private TextField			defaultSpellLevel1;
    @FXML
    private TextField			defaultSpellLevel2;
    @FXML
    private TextField			defaultSpellLevel3;
    @FXML
    private TextField			defaultSpellLevel4;
    @FXML
    private TextField			defaultSpellLevel5;
    @FXML
    private TextField			defaultSpellLevel6;
    @FXML
    private TextField			defaultSpellLevel7;
    @FXML
    private TextField			defaultSpellLevel8;
    @FXML
    private TextField			defaultSpellLevel9;
    @FXML
    private TextField			defaultSpellLevel10;
    @FXML
    private TextField			defaultSpellLevel11;
    @FXML
    private TextField			defaultSpellLevel12;
    @FXML
    private TextField			defaultSpellLevel13;
    @FXML
    private TextField			defaultSpellLevel14;
    @FXML
    private TextField			defaultSpellLevel15;
    @FXML
    private TextField			defaultSpellLevel16;

    @FXML
    private ComboBox<Spell>		spell7;
    @FXML
    private ComboBox<Spell>		spell6;
    @FXML
    private ComboBox<Spell>		spell5;
    @FXML
    private ComboBox<Spell>		spell4;
    @FXML
    private ComboBox<Spell>		spell3;
    @FXML
    private ComboBox<Spell>		spell2;
    @FXML
    private ComboBox<Spell>		spell1;
    @FXML
    private ComboBox<Spell>		spell11;
    @FXML
    private ComboBox<Spell>		spell12;
    @FXML
    private ComboBox<Spell>		spell9;
    @FXML
    private ComboBox<Spell>		spell14;
    @FXML
    private ComboBox<Spell>		spell8;
    @FXML
    private ComboBox<Spell>		spell13;
    @FXML
    private ComboBox<Spell>		spell10;
    @FXML
    private ComboBox<Spell>		spell15;
    @FXML
    private ComboBox<Spell>		spell16;

    @FXML
    private TextField			spellLevel1;
    @FXML
    private TextField			spellLevel2;
    @FXML
    private TextField			spellLevel3;
    @FXML
    private TextField			spellLevel8;
    @FXML
    private TextField			spellLevel11;
    @FXML
    private TextField			spellLevel9;
    @FXML
    private TextField			spellLevel12;
    @FXML
    private TextField			spellLevel13;
    @FXML
    private TextField			spellLevel14;
    @FXML
    private TextField			spellLevel4;
    @FXML
    private TextField			spellLevel5;
    @FXML
    private TextField			spellLevel6;
    @FXML
    private TextField			spellLevel7;
    @FXML
    private TextField			spellLevel10;
    @FXML
    private TextField			spellLevel15;
    @FXML
    private TextField			spellLevel16;

    @FXML
    private ResourceBundle		resources;
    @FXML
    private URL				location;
    @FXML
    private ComboBox<models.Character>	charactersC;
    @FXML
    private Button			charUp;
    @FXML
    private Button			charDown;
    @FXML
    private TextField			hpStart;
    @FXML
    private TextField			mpStart;
    @FXML
    private TextField			powerStart;
    @FXML
    private TextField			guardStart;
    @FXML
    private TextField			magicStart;
    @FXML
    private TextField			speedStart;
    @FXML
    private TextField			hpGrowth;
    @FXML
    private TextField			mpGrowth;
    @FXML
    private TextField			powerGrowth;
    @FXML
    private TextField			guardGrowth;
    @FXML
    private TextField			magicGrowth;
    @FXML
    private TextField			speedGrowth;
    @FXML
    private ComboBox<Weapon>		weaponStart;
    @FXML
    private ComboBox<Armor>		armorStart;
    @FXML
    private ComboBox<Armor>		accessoryStart;
    @FXML
    private TextField			expStart;
    @FXML
    private TextField			defaultExpStart;
    @FXML
    private ImageView			image;
    @FXML
    private AnchorPane			frame;
    @FXML
    private LineChart<Integer, Integer>	expChart;
    @FXML
    private TextField			expression;
    @FXML
    private Button			defaultB;
    @FXML
    private Button			previewB;
    @FXML
    private Button			applyB;

    @Override
    public void saveState()
    {
	startCharacter = charactersC.getSelectionModel().getSelectedIndex();
    }
}
