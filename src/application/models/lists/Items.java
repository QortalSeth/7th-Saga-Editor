package application.models.lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import application.models.Item;

public class Items extends AbstractItems<Item> implements Serializable
{
    private static final int itemListSize = 100;

    public Items()
    {
	super();
    }

    public Items(Items addFrom, boolean keepEmptyModel)
    {
	super(addFrom, keepEmptyModel);
    }

    @Override
    public void initializeModel(List<Item> models)
    {
	for (int i = 0; i < itemListSize; i++)
	{
	    Item item = new Item(i);
	    item.getValuesFromROM();
	    models.add(item);
	}
    }

    @Override
    public void saveModels()
    {
	for (Item i : models)
	    i.writeValuesToROM();
    }

    public static void sortItemCodesChronologically(List<Integer> itemCodes)
    {
	Items items = new Items();
	items.addModels(itemCodes);
	items.chronologicalIndexSort();
	for (int i = 0; i < itemCodes.size(); i++)
	{
	    itemCodes.set(i, items.getModels().get(i).getItemCode());
	}
	moveZerosToEndOfList(itemCodes);
    }

    private static void moveZerosToEndOfList(List<Integer> itemCodes)
    {
	List<Integer> newItemCodes = new ArrayList<Integer>();
	int zerosNum = 0;
	for (int i = 0; i < itemCodes.size(); i++)
	{
	    if (itemCodes.get(i) != 0)
	    {
		newItemCodes.add(itemCodes.get(i));
	    }
	    else
	    {
		zerosNum++;
	    }
	}

	for (int i = 0; i < zerosNum; i++)
	{
	    newItemCodes.add(0);
	} // add zeros back into list

	for (int i = 0; i < itemCodes.size(); i++)
	{
	    itemCodes.set(i, newItemCodes.get(i));
	}
    }

    public Items itemCodesToItems(List<Integer> list)
    {
	Items items = new Items();

	for (Integer itemCode : list)
	{
	    Lists.getItems().getItem(itemCode);
	}
	return items;
    }

    public List<Integer> getItemCodes(Items list)
    {
	List<Integer> itemCodes = new ArrayList<Integer>();

	for (Item item : models)
	{
	    itemCodes.add(item.getItemCode());
	}

	return itemCodes;
    }

    public List<Integer> getDItemCodes(Items list)
    {
	List<Integer> itemCodes = new ArrayList<Integer>();

	for (Item item : Dmodels)
	{
	    itemCodes.add(item.getItemCode());
	}

	return itemCodes;
    }

}
