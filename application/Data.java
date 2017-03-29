package application;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import controllers.MainMenu;
import models.AbstractItem;
import models.Experience;
import models.lists.AbstractItems;
import models.lists.Apprentices;
import models.lists.Armors;
import models.lists.Characters;
import models.lists.DropTables;
import models.lists.Items;
import models.lists.Lists;
import models.lists.ModelsList;
import models.lists.Monsters;
import models.lists.Shops;
import models.lists.Spells;
import models.lists.Weapons;

public class Data implements Serializable
{

    private int				DgoldPerLevel;
    private AbstractItems<AbstractItem>	abstractItems;
    private Apprentices			apprentices;
    private Armors			armors;
    private Characters			characters;
    private DropTables			dropTables;
    private Items			items;
    private Monsters			monsters;
    private Shops			shops;
    private Spells			spells;
    private Weapons			weapons;
    private List<Integer>		marginalExperience;
    private List<Integer>		totalExperience;

    public Data()
    {
	DgoldPerLevel = Lists.getApprentices().getGoldPerLevel();
	apprentices = Lists.getApprentices();
	abstractItems = Lists.getAbstractItems();
	armors = Lists.getArmors();
	characters = Lists.getCharacters();
	dropTables = Lists.getDropTables();
	items = Lists.getItems();
	monsters = Lists.getMonsters();
	shops = Lists.getShops();
	spells = Lists.getSpells();
	weapons = Lists.getWeapons();
	Experience.getValuesFromROM();
	marginalExperience = Experience.getMarginalExpTable();
	totalExperience = Experience.getTotalExpTable();
    }

    public static void serializeDefaultDataToDisk()
    {
	Data d = new Data();

	try
	{
	    File defaultData = new File(MainMenu.class.getResource("images/defaultModels.data").toURI());
	    System.out.println("file path to disk: " + defaultData.getAbsolutePath());
	    OutputStream file = new FileOutputStream(defaultData);
	    OutputStream buffer = new BufferedOutputStream(file);
	    ObjectOutput output = new ObjectOutputStream(buffer);
	    output.writeObject(d);
	    output.close();
	    file.close();

	}
	catch (IOException | URISyntaxException e)
	{
	    e.printStackTrace();
	}
    }

    public static void serializeDefaultDataFromDisk()
    {

	try
	{
	    // use buffering
	    InputStream inputFile = new BufferedInputStream(MainMenu.class.getResourceAsStream("images/defaultModels.data"));
	    File defaultData = new File(MainMenu.class.getResource("images/defaultModels.data").toURI());
	    System.out.println("File Path from disk: " + defaultData.getAbsolutePath());
	    try (ObjectInputStream input = new ObjectInputStream(inputFile);)
	    {
		// deserialize the List
		Data d = (Data) input.readObject();

		// make new models list
		d.abstractItems.setModels(new ModelsList<>());
		d.apprentices.setModels(new ModelsList<>());
		d.armors.setModels(new ModelsList<>());
		d.characters.setModels(new ModelsList<>());
		d.dropTables.setModels(new ModelsList<>());
		d.items.setModels(new ModelsList<>());
		d.monsters.setModels(new ModelsList<>());
		d.shops.setModels(new ModelsList<>());
		d.spells.setModels(new ModelsList<>());
		d.weapons.setModels(new ModelsList<>());
		d.armors.addEmptyModel(d.armors.getDModels());
		d.weapons.addEmptyModel(d.weapons.getDModels());

		// fill models with new data
		Lists.setAbstractItems(d.abstractItems);
		d.apprentices.setDgoldPerLevel(d.DgoldPerLevel);
		Lists.setApprentices(d.apprentices);
		Lists.setArmors(d.armors);
		Lists.setCharacters(d.characters);
		Lists.setDropTables(d.dropTables);
		Lists.setItems(d.items);
		Lists.setMonsters(d.monsters);
		Lists.setShops(d.shops);
		Lists.setSpells(d.spells);
		Lists.setWeapons(d.weapons);
		Experience.setDefaultMarginalExpTable(d.marginalExperience);
		Experience.setDefaultTotalExpTable(d.totalExperience);
		Experience.setMarginalExpTable(new ArrayList<Integer>());
		Experience.setTotalExpTable(new ArrayList<Integer>());
		Experience.getValuesFromROM();
	    }
	}
	catch (ClassNotFoundException | IOException | URISyntaxException e)
	{
	    e.printStackTrace();
	}
    }
};
