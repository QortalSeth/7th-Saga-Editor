package models.lists;

import java.io.Serializable;
import java.util.List;

import models.DropTable;

public class DropTables extends Models<DropTable> implements Serializable
{
    private final static int dropTableSize = 16;

    public DropTables()
    {
	super();
    }

    public DropTables(DropTables addFrom)
    {
	super(addFrom);
    }

    @Override
    public void initializeModel(List<DropTable> models)
    {

	for (int i = 0; i < dropTableSize; i++)
	{
	    DropTable d = new DropTable(i);
	    d.getValuesFromROM();
	    models.add(d);
	}

	// for(int i=0; i<16; i++)
	// {
	// System.out.println("drop table "+i);
	// for(int k : dropTables.get(i).drops)
	// {System.out.print(k+" ");}
	// System.out.println("\n");
	// }
    }

    @Override
    public void saveModels()
    {
	for (DropTable d : models)
	    d.writeValuesToROM();
    }

}
