package models.lists;

import java.io.Serializable;
import java.util.ArrayList;

import models.AbstractItem;
import models.Experience;
import models.lists.states.DefaultDataState;
import models.lists.states.IModelState;

public class Lists implements Serializable
{
    private static AbstractItems<AbstractItem> abstractItems;
    private static Monsters		       monsters;
    private static Apprentices		       apprentices;
    private static Items		       items;
    private static Armors		       armors;
    private static Characters		       characters;
    private static DropTables		       dropTables;
    private static Shops		       shops;
    private static Spells		       spells;
    private static Weapons		       weapons;
    private static IModelState		       state;

    public static void CreateLists()
    {
	abstractItems = new AbstractItems<>();
	apprentices = new Apprentices();
	armors = new Armors();
	characters = new Characters();
	dropTables = new DropTables();
	items = new Items();
	monsters = new Monsters();
	shops = new Shops();
	spells = new Spells();
	weapons = new Weapons();
	state = new DefaultDataState();

	Experience.setTotalExpTable(new ArrayList<Integer>());
	Experience.setMarginalExpTable(new ArrayList<Integer>());
	Experience.setDefaultTotalExpTable(new ArrayList<Integer>());
	Experience.setDefaultMarginalExpTable(new ArrayList<Integer>());

	// Experience.totalExpTable = new ArrayList<Integer>();
	// Experience.marginalExpTable = new ArrayList<Integer>();
	// Experience.defaultTotalExpTable = new ArrayList<Integer>();
	// Experience.defaultMarginalExpTable = new ArrayList<Integer>();

    }

    public static void InitializeLists()
    {

	state.Initialize(apprentices);
	state.Initialize(armors);
	state.Initialize(characters);
	state.Initialize(dropTables);
	state.Initialize(items);
	state.Initialize(monsters);
	state.Initialize(shops);
	state.Initialize(spells);
	state.Initialize(weapons);
	state.Initialize(abstractItems);

    }

    public static void saveModels()
    {
	apprentices.saveModels();
	items.saveModels();
	armors.saveModels();
	characters.saveModels();
	dropTables.saveModels();
	shops.saveModels();
	spells.saveModels();
	monsters.saveModels();
	weapons.saveModels();
	Experience.saveModels();
	System.out.println("bytes[] saved");
    }

    public static Monsters getMonsters()
    {
	return monsters;
    }

    public static IModelState getState()
    {
	return state;
    }

    public static void setState(IModelState state)
    {
	Lists.state = state;
    }

    public static void setMonsters(Monsters monsters)
    {
	Lists.monsters = monsters;
    }

    public static AbstractItems<AbstractItem> getAbstractItems()
    {
	return abstractItems;
    }

    public static void setAbstractItems(AbstractItems<AbstractItem> abstractItems)
    {
	Lists.abstractItems = abstractItems;
    }

    public static Apprentices getApprentices()
    {
	return apprentices;
    }

    public static void setApprentices(Apprentices apprentices)
    {
	Lists.apprentices = apprentices;
    }

    public static Items getItems()
    {
	return items;
    }

    public static void setItems(Items items)
    {
	Lists.items = items;
    }

    public static Armors getArmors()
    {
	return armors;
    }

    public static void setArmors(Armors armors)
    {
	Lists.armors = armors;
    }

    public static Characters getCharacters()
    {
	return characters;
    }

    public static void setCharacters(Characters characters)
    {
	Lists.characters = characters;
    }

    public static DropTables getDropTables()
    {
	return dropTables;
    }

    public static void setDropTables(DropTables dropTables)
    {
	Lists.dropTables = dropTables;
    }

    public static Shops getShops()
    {
	return shops;
    }

    public static void setShops(Shops shops)
    {
	Lists.shops = shops;
    }

    public static Spells getSpells()
    {
	return spells;
    }

    public static void setSpells(Spells spells)
    {
	Lists.spells = spells;
    }

    public static Weapons getWeapons()
    {
	return weapons;
    }

    public static void setWeapons(Weapons weapons)
    {
	Lists.weapons = weapons;
    }

}
