package models.lists;

import java.io.Serializable;
import java.util.List;

import models.Shop;

public class Shops extends Models<Shop> implements Serializable
{

    private static final int shopListSize = 40;

    public Shops()
    {
	super();
    }

    public Shops(Shops addFrom)
    {
	super(addFrom);
    }

    @Override
    public void initializeModel(List<Shop> models)
    {
	for (int i = 0; i < shopListSize; i++)
	{
	    Shop s = new Shop(i);
	    s.getValuesFromROM();
	    models.add(s);
	}
    }

    @Override
    public void saveModels()
    {
	for (Shop s : models)
	    s.writeValuesToROM();
    }

}
