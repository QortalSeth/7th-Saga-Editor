package application.models.lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import application.models.AbstractItem;
import application.models.Character;
import application.models.DropTable;
import application.models.Monster;
import application.models.Shop;

public class AbstractItems<T extends AbstractItem> extends Models<T> implements Serializable
{
    public AbstractItems()
    {
	super();
    }

    public AbstractItems(Models<T> addFrom, boolean keepEmptyModel)
    {
	super(addFrom, keepEmptyModel);
    }

    public AbstractItems(Models<T> addFrom)
    {
	super(addFrom, false);
    }

    public AbstractItems(List<Integer> gameIndexes)
    {
	super();
	this.addModels(gameIndexes);
    }

    public AbstractItems(DModelsList<T> DmodelsList)
    {
	super(DmodelsList);
    }

    @Override
    public void initializeModel(List<T> models)
    {
	addUsefulModels((AbstractItems<T>) Lists.getItems(), true);
	addUsefulModels((AbstractItems<T>) Lists.getWeapons(), false);
	addUsefulModels((AbstractItems<T>) Lists.getArmors(), false);
	//
	// int listIndex = 1;
	// for (Item i : Item.getModels())
	// {
	// if (!i.getName().trim().isEmpty())
	// {
	// application.models.add((T) new Item(i, listIndex++));
	// }
	// }
	//
	// for (Weapon w : Weapon.getModels())
	// {
	// if (!w.getName().trim().isEmpty() && w.getEquipCode() > 0)
	// {
	// application.models.add((T) new Weapon(w, listIndex++));
	// }
	// }
	//
	// for (Armor a : Armor.getModels())
	// {
	// if (!a.getName().trim().isEmpty() && a.getEquipCode() > 0)
	// {
	// application.models.add((T) new Armor(a, listIndex++));
	// }
	// }
    }

    @Override
    public int getIndex(int iCode)
    {
	// System.out.println("Item code is: " + Integer.toHexString(iCode));
	int index = 0;
	for (AbstractItem i : models)
	{
	    if (i.getItemCode() == iCode) { return index; }
	    index++;
	}
	System.out.println("iCode " + iCode + " not found");
	return 0;
    }

    @Override
    public int getDIndex(int iCode)
    {
	// System.out.println("Item code is: " + Integer.toHexString(iCode));
	int index = 0;
	for (AbstractItem i : Dmodels)
	{
	    if (i.getItemCode() == iCode) { return index; }
	    index++;
	}
	System.out.println("iCode " + iCode + " not found");
	return 0;

    }

    // public static int itemCodeToIndex(List<? extends AbstractItem> items, int iCode)
    // {
    // // System.out.println("Item code is: " + Integer.toHexString(iCode));
    // int index = 0;
    // for (AbstractItem i : items)
    // {
    // if (i.getItemCode() == iCode) { return index; }
    // index++;
    // }
    // System.out.println("iCode " + iCode + " not found");
    // return 0;
    // }

    public static void sortByItemcode(List<Integer> list)
    {
	list.sort(itemcodeComparator);
    }

    // public static String itemCodeToName(List<? extends AbstractItem> items, int iCode)
    // {
    //
    // for (AbstractItem i : items)
    // {
    // if (i.getItemCode() == iCode) { return i.toString(); }
    // }
    // return "Item not found";
    // }

    @Override
    public String getName(int iCode)
    {
	for (AbstractItem i : models)
	{
	    if (i.getItemCode() == iCode) { return i.toString(); }
	}
	return "Item not found";
    }

    @Override
    public String getDName(int iCode)
    {
	for (AbstractItem i : Dmodels)
	{
	    if (i.getItemCode() == iCode) { return i.toString(); }
	}
	return "Item not found";
    }

    public List<String> getNames(DropTable d)
    {
	List<String> names = new ArrayList<String>();

	for (Integer drop : d.getDrops())
	{
	    names.add(this.getName(drop));
	}

	return names;
    }

    public List<String> getDNames(DropTable d)
    {
	List<String> names = new ArrayList<String>();

	for (Integer drop : d.getDrops())
	{
	    names.add(this.getDName(drop));
	}
	return names;
    }
    // public static <T extends AbstractItem> T itemCodeToItem(List<T> items, int iCode)
    // {
    //
    // for (T i : items)
    // {
    // if (i.getItemCode() == iCode) { return i; }
    // }
    // System.out.println("Item code " + iCode + " returns NULL");
    // return null;
    // }

    public <P extends AbstractItems<T>> P addModels(List<Integer> itemCodes)
    {

	for (Integer i : itemCodes)
	{
	    models.add((T) Lists.getAbstractItems().getItem(i));
	}
	System.out.println("Item code " + itemCodes + " returns NULL");
	return null;
    }

    public T getDItem(int iCode)
    {
	for (T i : Dmodels)
	{
	    if (i.getItemCode() == iCode) { return i; }
	}
	System.out.println("Item code " + iCode + " returns NULL");
	return null;
    }

    public T getItem(int iCode)
    {
	for (T i : models)
	{
	    if (i.getItemCode() == iCode) { return i; }
	}
	System.out.println("Item code " + iCode + " returns NULL");
	return null;
    }

    public static String getLocations(List<Shop> shops, List<Character> characters, int itemCode)
    {
	List<String> locations = new ArrayList<String>();

	StringBuilder startCharacters = new StringBuilder();

	for (Character c : characters) // add initial weapons
	{
	    if (c.getWeaponStart() == itemCode || c.getArmorStart() == itemCode || c.getAccessoryStart() == itemCode)
	    {
		startCharacters.append(c.getEquipName());
	    }
	}

	if (startCharacters.length() > 0)
	{
	    locations.add("Initial (" + startCharacters.toString() + ")");
	}

	for (Shop s : shops) // add weapons from shops
	{
	    if (s.getWeaponCodes().contains(itemCode) || s.getArmorCodes().contains(itemCode))
	    {
		if (s.getName().length() < 6)
		{
		    locations.add(s.getName());
		}
		else
		{
		    locations.add(s.getName().substring(0, 3));
		}
	    }
	}

	List<String> monsterDrops = new ArrayList<String>();
	for (DropTable d : Lists.getDropTables().getModels()) // add weapons from monster
	// drops
	{
	    for (Integer i : d.getDrops())
	    {
		if (itemCode == i)
		{
		    for (Monster m : Lists.getMonsters().getModels())
		    {
			if (m.getItemDropSet() == d.getGameIndex() && !monsterDrops.contains(m.getName()))
			{
			    monsterDrops.add(m.getName());
			}

		    }
		}
	    }
	}
	StringBuilder sb = new StringBuilder();

	if (!monsterDrops.isEmpty())
	{
	    sb.append("Dropped by: ");
	    for (String s : monsterDrops)
	    {
		sb.append(s + ", ");
	    }
	    sb.deleteCharAt(sb.length() - 2);
	    locations.add(sb.toString().trim());
	    sb = new StringBuilder();
	}

	for (String s : locations)
	{
	    sb.append(s);
	    sb.append(" ");
	}
	return sb.toString().trim();
    }

    @Override
    public List<Integer> toIntegers()
    {
	List<Integer> indexes = new ArrayList<Integer>();

	for (AbstractItem m : models)
	{
	    indexes.add(m.getItemCode());
	}
	return indexes;
    }

    @Override
    public List<Integer> toDIntegers()
    {
	List<Integer> indexes = new ArrayList<Integer>();

	for (AbstractItem m : Dmodels)
	{
	    indexes.add(m.getItemCode());
	}
	return indexes;
    }

    @Override
    public T getDModel(T model)
    {
	for (T m : Dmodels)
	{
	    if (model.getItemCode() == m.getItemCode()) { return m; }
	}
	System.out.println("Model " + model.getName() + " has no default!");
	return null;
    }

    public void sortByDescendingCost()
    {
	sortList((Comparator<T>) descendingCostComparator);
    }

    public void sortByAscendingCost()
    {
	sortList((Comparator<T>) ascendingCostComparator);
    }

    private static final Comparator<AbstractItem>	       AbstractItemComparator	= new Comparator<AbstractItem>()
											{

											    @Override
											    public int compare(AbstractItem a1, AbstractItem a2)
											    {
												if (a2.getItemCode() == 0)
												{
												    return 1;
												}
												else
												{
												    return a1.getItemCode() - a2.getItemCode();
												}
											    }
											};

    private static final Comparator<Integer>		       itemcodeComparator	= new Comparator<Integer>()
											{
											    @Override
											    public int compare(Integer i1, Integer i2)
											    {
												if (i1 == 0)
												{
												    return 1;
												}
												else
												{
												    return i1 - i2;
												}
											    }
											};

    public static final Comparator<? extends application.models.Equipment> descendingCostComparator	= new Comparator<application.models.Equipment>()
											{
											    @Override
											    public int compare(application.models.Equipment e1, application.models.Equipment e2)
											    {
												if (e1.getCost() == 0) { return 1; }
												return e2.getCost() - e1.getCost();
											    }
											};

    public static final Comparator<? extends application.models.Equipment> ascendingCostComparator	= new Comparator<application.models.Equipment>()
											{
											    @Override
											    public int compare(application.models.Equipment e1, application.models.Equipment e2)
											    {
												if (e1.getCost() == 0) { return 1; }
												return e1.getCost() - e2.getCost();
											    }
											};
}
