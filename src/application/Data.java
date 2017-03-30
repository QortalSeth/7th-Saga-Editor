package application;
import application.controllers.MainMenu;
import application.models.AbstractItem;
import application.models.Experience;
import application.models.lists.*;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Data implements Serializable
{

    private int				            DgoldPerLevel;
    private AbstractItems<AbstractItem>	abstractItems;
    private Apprentices		         	apprentices;
    private Armors			            armors;
    private Characters		         	characters;
    private DropTables		         	dropTables;
    private Items			            items;
    private Monsters		         	monsters;
    private Shops		            	shops;
    private Spells			            spells;
    private Weapons			            weapons;
    private List<Integer>	         	marginalExperience;
    private List<Integer>	         	totalExperience;

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
	    File defaultData = new File(Main.class.getResource("models/defaultModels.data").toURI());
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
	    InputStream inputFile = new BufferedInputStream(Main.class.getResourceAsStream("models/defaultModels.data"));
	   // File defaultData = new File(Main.class.getResource("models/defaultModels.data").toURI());
	   // System.out.println("File Path from disk: " + defaultData.getAbsolutePath());
	    try (ObjectInputStream input = new ObjectInputStream(inputFile))
	    {
		// deserialize the List
		Data d = (Data) input.readObject();

		// make new application.models list
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

		// fill application.models with new data
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
	catch (ClassNotFoundException | IOException e)
	{
	    e.printStackTrace();
	}
    }
}
