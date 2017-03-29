package controllers;

import java.util.ArrayList;
import java.util.List;

import application.ControllerInitilizer;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import models.AbstractItem;
import models.DropTable;
import models.Monster;
import models.Spell;
import models.lists.DropTables;
import models.lists.Lists;
import models.lists.Monsters;
import models.lists.Spells;
import staticClasses.Listeners;

public class MonsterEditor implements ControllerInitilizer
{
    private List<ComboBox<Spell>>	 spellsC	     = new ArrayList<>();
    private List<TextField>		 spellChanceT	     = new ArrayList<>();
    private List<TextField>		 DspellsT	     = new ArrayList<>();
    private List<TextField>		 DspellChanceT	     = new ArrayList<>();
    private List<ComboBox<AbstractItem>> itemsC		     = new ArrayList<>();
    private List<TextField>		 DitemsT	     = new ArrayList<>();
    private Monsters			 monsters;
    private DropTables			 dropTables;
    private Monster			 previouslySelectedMonster;
    private DropTable			 previouslySelectedDropTable;
    private Spells			 spells;

    private static int			 startMonster;
    private static int			 startDropTable;
    private static boolean		 gameIndexIsSelected = false;

    @FXML
    void initialize()
    {

	monsters = new Monsters(Lists.getMonsters(), false);
	monstersC.setItems(monsters.getModels());

	dropTables = new DropTables(Lists.getDropTables());
	itemDropListsC.setItems(dropTables.getModels());

	spells = new Spells();
	spells.addUsefulModels(Lists.getSpells(), true);

	if (gameIndexIsSelected == true)
	{
	    gameOrderR.setSelected(true);
	}
	else
	{
	    ChronOrderR.setSelected(true);
	    chronologicalSort();
	}

	this.assembleLists();

	monstersC.getSelectionModel().select(startMonster);
	itemDropListsC.getSelectionModel().select(startDropTable);
	this.setSelectedDropTable();
	this.setSelectedMonster();

	ImageView i = (ImageView) monsterUp.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));
	i = (ImageView) monsterDown.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));

	i = (ImageView) dropUp.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));
	i = (ImageView) dropDown.getGraphic();
	i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));
    }

    @FXML
    void setSelectedMonster()
    {
	this.saveData();
	Monster monster = monstersC.getSelectionModel().getSelectedItem();
	if (monster == null) { return; }
	Monster Dmonster = monsters.getDModel(monster);
	// System.out.println("DMonster is: " + Dmonster.getName() + " gameIndex
	// is: " + Dmonster.getGameIndex());

	previouslySelectedMonster = monster;
	unknown1T.setText(Integer.toString(monster.getUnknown1()));
	hpT.setText(Integer.toString(monster.getHp()));
	mpT.setText(Integer.toString(monster.getMp()));
	powerT.setText(Integer.toString(monster.getPower()));
	guardT.setText(Integer.toString(monster.getGuard()));

	magicT.setText(Integer.toString(monster.getMagic()));
	speedT.setText(Integer.toString(monster.getSpeed()));
	laserResT.setText(Integer.toString(monster.getLaserRes()));
	unknown1ResT.setText(Integer.toString(monster.getUnknownRes1()));
	unknown2ResT.setText(Integer.toString(monster.getUnknownRes2()));
	fireResT.setText(Integer.toString(monster.getFireRes()));
	iceResT.setText(Integer.toString(monster.getIceRes()));
	vacuumResT.setText(Integer.toString(monster.getVacuumRes()));
	debuffResT.setText(Integer.toString(monster.getDebuffRes()));
	goldT.setText(Integer.toString(monster.getGold()));
	expT.setText(Integer.toString(monster.getExperience()));
	itemDropT.setText(Integer.toString(monster.getItemDropSet()));
	defeatedFlagT.setText(Integer.toString(monster.getUnknown2()));
	runFlagT.setText(Integer.toString(monster.getRunFlag()));

	defaultUnknown1T.setText(Integer.toString(Dmonster.getUnknown1()));
	defaultHpT.setText(Integer.toString(Dmonster.getHp()));
	defaultMpT.setText(Integer.toString(Dmonster.getMp()));
	defaultPowerT.setText(Integer.toString(Dmonster.getPower()));
	defaultGuardT.setText(Integer.toString(Dmonster.getGuard()));
	defaultMagicT.setText(Integer.toString(Dmonster.getMagic()));
	defaultSpeedT.setText(Integer.toString(Dmonster.getSpeed()));
	defaultLaserResT.setText(Integer.toString(Dmonster.getLaserRes()));
	defaultUnknown1ResT.setText(Integer.toString(Dmonster.getUnknownRes1()));
	defaultUnknown2ResT.setText(Integer.toString(Dmonster.getUnknownRes2()));
	defaultFireResT.setText(Integer.toString(Dmonster.getFireRes()));
	defaultIceResT.setText(Integer.toString(Dmonster.getIceRes()));
	defaultVacuumResT.setText(Integer.toString(Dmonster.getVacuumRes()));
	defaultDebuffResT.setText(Integer.toString(Dmonster.getDebuffRes()));
	defaultGoldT.setText(Integer.toString(Dmonster.getGold()));
	defaultExpT.setText(Integer.toString(Dmonster.getExperience()));
	defaultItemDropT.setText(Integer.toString(Dmonster.getItemDropSet()));
	defaultDefeatedFlagT.setText(Integer.toString(Dmonster.getUnknown2()));
	defaultRunFlagT.setText(Integer.toString(Dmonster.getRunFlag()));

	for (int i = 0; i < spellsC.size(); i++)
	{
	    spellsC.get(i).getSelectionModel().select(spells.getIndex(monster.getSpells().get(i)));
	    DspellsT.get(i).setText(spells.getDName(Dmonster.getSpells().get(i)));
	}

	for (int i = 0; i < spellChanceT.size(); i++)
	{
	    spellChanceT.get(i).setText(Integer.toString(monster.getSpellChance().get(i)));
	    DspellChanceT.get(i).setText((Integer.toString(Dmonster.getSpellChance().get(i))));
	}
    }

    @FXML
    void monsterDecrement()
    {
	monstersC.getSelectionModel().selectPrevious();
    }

    @FXML
    void monsterIncrement()
    {
	monstersC.getSelectionModel().selectNext();
    }

    @FXML
    void dropListDecrement()
    {
	itemDropListsC.getSelectionModel().selectPrevious();
    }

    @FXML
    void dropListIncrement()
    {
	itemDropListsC.getSelectionModel().selectNext();
    }

    @FXML
    void removeNonNumbers(KeyEvent event)
    {
	Listeners.removeNonNumbers(event, null);
    }

    @FXML
    void gameIndexSort()
    {
	int selectedIndex = monstersC.getSelectionModel().getSelectedIndex();
	monsters.gameIndexSort();
	monstersC.getSelectionModel().select(selectedIndex);
	chronOrder = false;
    }

    @FXML
    void chronologicalSort()
    {
	int selectedIndex = monstersC.getSelectionModel().getSelectedIndex();
	monsters.chronologicalIndexSort();
	monstersC.getSelectionModel().select(selectedIndex);
	chronOrder = true;
    }

    @Override
    public void saveData()
    {
	Monster m = previouslySelectedMonster;
	saveItemDropData();
	if (m == null) { return; }

	saveMonster(m);

	for (Monster alias : m.getAliases()) // save aliases
	{
	    saveMonster(alias);
	}
    }

    private void saveMonster(Monster m)
    {
	m.setUnknown1(Integer.parseInt(unknown1T.getText()));
	m.setHp(Integer.parseInt(hpT.getText()));
	m.setMp(Integer.parseInt(mpT.getText()));
	m.setPower(Integer.parseInt(powerT.getText()));
	m.setGuard(Integer.parseInt(guardT.getText()));
	m.setMagic(Integer.parseInt(magicT.getText()));
	m.setSpeed(Integer.parseInt(speedT.getText()));
	m.setLaserRes(Integer.parseInt(laserResT.getText()));
	m.setUnknownRes1(Integer.parseInt(unknown1ResT.getText()));
	m.setUnknownRes2(Integer.parseInt(unknown2ResT.getText()));
	m.setFireRes(Integer.parseInt(fireResT.getText()));
	m.setIceRes(Integer.parseInt(iceResT.getText()));
	m.setVacuumRes(Integer.parseInt(vacuumResT.getText()));
	m.setDebuffRes(Integer.parseInt(debuffResT.getText()));
	m.setGold(Integer.parseInt(goldT.getText()));
	expT.setText(Integer.toString(m.getExperience()));
	m.setItemDropSet(Integer.parseInt(itemDropT.getText()));
	m.setUnknown2(Integer.parseInt(defeatedFlagT.getText()));
	m.setRunFlag(Integer.parseInt(runFlagT.getText()));

	for (int i = 0; i < spellsC.size(); i++)
	{
	    m.getSpells().set(i, spellsC.get(i).getSelectionModel().getSelectedItem().getGameIndex());
	}

	for (int i = 0; i < spellChanceT.size(); i++)
	{
	    m.getSpellChance().set(i, Integer.parseInt(spellChanceT.get(i).getText()));
	}
    }

    @FXML
    void setSelectedDropTable()
    {
	saveItemDropData();
	DropTable dropTable = itemDropListsC.getSelectionModel().getSelectedItem();
	DropTable DdropTable = dropTables.getDModel(dropTable);

	previouslySelectedDropTable = dropTable;

	int index = 0;
	for (int itemCode : dropTable.getDrops())
	{
	    // System.out.println("selected itemcode is: "+itemCode);
	    itemsC.get(index).getSelectionModel().select(Lists.getAbstractItems().getIndex(itemCode));
	    index++;
	}

	index = 0;
	for (TextField t : DitemsT)
	{
	    t.setText(Lists.getAbstractItems().getDName(DdropTable.getDrops()[index]));
	    index++;
	}

    }

    public void saveItemDropData()
    {
	DropTable d = previouslySelectedDropTable;
	if (d == null) { return; }

	int index = 0;
	for (int i = 0; i < d.getDrops().length; i++)
	{
	    d.getDrops()[i] = itemsC.get(index).getSelectionModel().getSelectedItem().getItemCode();
	    index++;
	}

    }

    private void assembleLists()
    {
	spellsC.add(spell1);
	spellsC.add(spell2);
	spellsC.add(spell3);
	spellsC.add(spell4);
	spellsC.add(spell5);
	spellsC.add(spell6);
	spellsC.add(spell7);
	spellsC.add(spell8);

	DspellsT.add(defaultSpell1);
	DspellsT.add(defaultSpell2);
	DspellsT.add(defaultSpell3);
	DspellsT.add(defaultSpell4);
	DspellsT.add(defaultSpell5);
	DspellsT.add(defaultSpell6);
	DspellsT.add(defaultSpell7);
	DspellsT.add(defaultSpell8);

	spellChanceT.add(spellChance1);
	spellChanceT.add(spellChance2);
	spellChanceT.add(spellChance3);
	spellChanceT.add(spellChance4);
	spellChanceT.add(spellChance5);
	spellChanceT.add(spellChance6);
	spellChanceT.add(spellChance7);
	spellChanceT.add(spellChance8);

	DspellChanceT.add(defaultSpellChance1);
	DspellChanceT.add(defaultSpellChance2);
	DspellChanceT.add(defaultSpellChance3);
	DspellChanceT.add(defaultSpellChance4);
	DspellChanceT.add(defaultSpellChance5);
	DspellChanceT.add(defaultSpellChance6);
	DspellChanceT.add(defaultSpellChance7);
	DspellChanceT.add(defaultSpellChance8);

	itemsC.add(item1C);
	itemsC.add(item2C);
	itemsC.add(item3C);
	itemsC.add(item4C);
	itemsC.add(item5C);
	itemsC.add(item6C);
	itemsC.add(item7C);
	itemsC.add(item8C);
	itemsC.add(item9C);
	itemsC.add(item10C);
	itemsC.add(item11C);
	itemsC.add(item12C);
	itemsC.add(item13C);
	itemsC.add(item14C);
	itemsC.add(item15C);
	itemsC.add(item16C);

	DitemsT.add(defaultItem1T);
	DitemsT.add(defaultItem2T);
	DitemsT.add(defaultItem3T);
	DitemsT.add(defaultItem4T);
	DitemsT.add(defaultItem5T);
	DitemsT.add(defaultItem6T);
	DitemsT.add(defaultItem7T);
	DitemsT.add(defaultItem8T);
	DitemsT.add(defaultItem9T);
	DitemsT.add(defaultItem10T);
	DitemsT.add(defaultItem11T);
	DitemsT.add(defaultItem12T);
	DitemsT.add(defaultItem13T);
	DitemsT.add(defaultItem14T);
	DitemsT.add(defaultItem15T);
	DitemsT.add(defaultItem16T);

	for (ComboBox<Spell> s : spellsC)
	{
	    s.setItems(spells.getModels());
	}

	for (ComboBox<AbstractItem> i : itemsC)
	{
	    i.setItems(Lists.getAbstractItems().getModels());
	}

	unknown1T.focusedProperty().addListener(Listeners.textFieldFocusListener(255));
	hpT.focusedProperty().addListener(Listeners.textFieldFocusListener(9999));
	mpT.focusedProperty().addListener(Listeners.textFieldFocusListener(9999));
	powerT.focusedProperty().addListener(Listeners.textFieldFocusListener(999));
	guardT.focusedProperty().addListener(Listeners.textFieldFocusListener(9999));
	magicT.focusedProperty().addListener(Listeners.textFieldFocusListener(999));
	speedT.focusedProperty().addListener(Listeners.textFieldFocusListener(999));
	laserResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	unknown1ResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	unknown2ResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	fireResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	iceResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	vacuumResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	debuffResT.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	goldT.focusedProperty().addListener(new ChangeListener<Boolean>()
	{

	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable,
		    // limit gold given from battle to prevent exp overflow
		    Boolean oldValue, Boolean newValue)
	    {

		if (!newValue)
		{
		    ReadOnlyBooleanProperty a = (ReadOnlyBooleanProperty) observable;
		    TextField tf = (TextField) a.getBean();

		    if (tf.getText().isEmpty())
		    {
			tf.setText("0");
			return;
		    }

		    int value = Integer.parseInt(tf.getText());

		    if (value < 0)
		    {
			value = 0;
		    }

		    int maxValue = 29000;
		    if (value > maxValue)
		    {
			value = maxValue;
		    }

		    tf.setText(Integer.toString(value));
		    expT.setText(Integer.toString(Monster.goldToExp(value)));
		}

	    }
	});
	itemDropT.focusedProperty().addListener(Listeners.textFieldFocusListener(20));
	defeatedFlagT.focusedProperty().addListener(Listeners.textFieldFocusListener(255));

	for (TextField t : spellChanceT)
	{
	    t.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
	}

    }

    public static boolean isChronologicalOrderSelected()
    {
	return chronOrder;
    }

    private static boolean	   chronOrder = true;
    @FXML
    private TextField		   defaultItem10T;
    @FXML
    private TextField		   defaultItem11T;
    @FXML
    private TextField		   defaultItem12T;
    @FXML
    private TextField		   defaultItem13T;
    @FXML
    private TextField		   defaultItem14T;
    @FXML
    private TextField		   defaultItem15T;
    @FXML
    private TextField		   defaultItem16T;
    @FXML
    private TextField		   defaultItem1T;
    @FXML
    private TextField		   defaultItem2T;
    @FXML
    private TextField		   defaultItem3T;
    @FXML
    private TextField		   defaultItem4T;
    @FXML
    private TextField		   defaultItem5T;
    @FXML
    private TextField		   defaultItem6T;
    @FXML
    private TextField		   defaultItem7T;
    @FXML
    private TextField		   defaultItem8T;
    @FXML
    private TextField		   defaultItem9T;
    @FXML
    private TextField		   defaultItemDropT;
    @FXML
    private TextField		   defaultMagicT;
    @FXML
    private TextField		   defaultMpT;
    @FXML
    private TextField		   defaultPowerT;
    @FXML
    private TextField		   defaultRunFlagT;
    @FXML
    private TextField		   defaultSpeedT;
    @FXML
    private TextField		   defaultSpell1;
    @FXML
    private TextField		   defaultSpell2;
    @FXML
    private TextField		   defaultSpell3;
    @FXML
    private TextField		   defaultSpell4;
    @FXML
    private TextField		   defaultSpell5;
    @FXML
    private TextField		   defaultSpell6;
    @FXML
    private TextField		   defaultSpell7;
    @FXML
    private TextField		   defaultSpell8;
    @FXML
    private TextField		   defaultSpellChance1;
    @FXML
    private TextField		   defaultSpellChance2;
    @FXML
    private TextField		   defaultSpellChance3;
    @FXML
    private TextField		   defaultSpellChance4;
    @FXML
    private TextField		   defaultSpellChance5;
    @FXML
    private TextField		   defaultSpellChance6;
    @FXML
    private TextField		   defaultSpellChance7;
    @FXML
    private TextField		   defaultSpellChance8;
    @FXML
    private ComboBox<AbstractItem> item10C;
    @FXML
    private ComboBox<AbstractItem> item11C;
    @FXML
    private ComboBox<AbstractItem> item12C;
    @FXML
    private ComboBox<AbstractItem> item13C;
    @FXML
    private ComboBox<AbstractItem> item14C;
    @FXML
    private ComboBox<AbstractItem> item15C;
    @FXML
    private ComboBox<AbstractItem> item16C;
    @FXML
    private ComboBox<AbstractItem> item1C;
    @FXML
    private ComboBox<AbstractItem> item2C;
    @FXML
    private ComboBox<AbstractItem> item3C;
    @FXML
    private ComboBox<AbstractItem> item4C;
    @FXML
    private ComboBox<AbstractItem> item5C;
    @FXML
    private ComboBox<AbstractItem> item6C;
    @FXML
    private ComboBox<AbstractItem> item7C;
    @FXML
    private ComboBox<AbstractItem> item8C;
    @FXML
    private ComboBox<AbstractItem> item9C;
    @FXML
    private ComboBox<Spell>	   spell1;
    @FXML
    private ComboBox<Spell>	   spell2;
    @FXML
    private ComboBox<Spell>	   spell3;
    @FXML
    private ComboBox<Spell>	   spell4;
    @FXML
    private ComboBox<Spell>	   spell5;
    @FXML
    private ComboBox<Spell>	   spell6;
    @FXML
    private ComboBox<Spell>	   spell7;
    @FXML
    private ComboBox<Spell>	   spell8;
    @FXML
    private TextField		   spellChance1;
    @FXML
    private TextField		   spellChance2;
    @FXML
    private TextField		   spellChance3;
    @FXML
    private TextField		   spellChance4;
    @FXML
    private TextField		   spellChance5;
    @FXML
    private TextField		   spellChance6;
    @FXML
    private TextField		   spellChance7;
    @FXML
    private TextField		   spellChance8;

    @FXML
    private Button		   monsterDown;
    @FXML
    private Button		   monsterUp;
    @FXML
    private Button		   dropUp;
    @FXML
    private Button		   dropDown;
    @FXML
    private RadioButton		   ChronOrderR;
    @FXML
    private TextField		   defaultExpT;
    @FXML
    private TextField		   defaultGoldT;
    @FXML
    private TextField		   defaultGuardT;
    @FXML
    private TextField		   defaultHpT;
    @FXML
    private TextField		   defaultUnknown1T;
    @FXML
    private TextField		   defaultDefeatedFlagT;
    @FXML
    private TextField		   expT;
    @FXML
    private RadioButton		   gameOrderR;
    @FXML
    private TextField		   goldT;
    @FXML
    private TextField		   guardT;
    @FXML
    private TextField		   hpT;
    @FXML
    private ComboBox<DropTable>	   itemDropListsC;
    @FXML
    private ComboBox<Monster>	   monstersC;
    @FXML
    private TextField		   itemDropT;
    @FXML
    private TextField		   magicT;
    @FXML
    private TextField		   mpT;
    @FXML
    private TextField		   powerT;
    @FXML
    private TextField		   runFlagT;
    @FXML
    private ToggleGroup		   sortMonsters;
    @FXML
    private TextField		   speedT;
    @FXML
    private TextField		   unknown1T;
    @FXML
    private TextField		   defeatedFlagT;
    @FXML
    private TextField		   laserResT;
    @FXML
    private TextField		   unknown1ResT;
    @FXML
    private TextField		   unknown2ResT;
    @FXML
    private TextField		   fireResT;
    @FXML
    private TextField		   iceResT;
    @FXML
    private TextField		   vacuumResT;
    @FXML
    private TextField		   debuffResT;
    @FXML
    private TextField		   defaultLaserResT;
    @FXML
    private TextField		   defaultUnknown1ResT;
    @FXML
    private TextField		   defaultUnknown2ResT;
    @FXML
    private TextField		   defaultFireResT;
    @FXML
    private TextField		   defaultIceResT;
    @FXML
    private TextField		   defaultVacuumResT;
    @FXML
    private TextField		   defaultDebuffResT;

    @Override
    public void saveState()
    {
	startMonster = monstersC.getSelectionModel().getSelectedIndex();
	startDropTable = itemDropListsC.getSelectionModel().getSelectedIndex();
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