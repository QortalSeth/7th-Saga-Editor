package application.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import application.controllers.MainMenu;
import application.models.lists.AbstractItems;
import application.models.lists.Lists;
import application.staticClasses.UByte;

public class DropTable extends Model implements Serializable
{
    int[]		     drops;
    private final static int baseOffset	       = 0x8A18;
    private final static int bytesPerDropTable = 16;

    public DropTable(int i)
    {
	super(i);
	drops = new int[16];
	name = Integer.toString(gameIndex);

    }

    public void getValuesFromROM()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerDropTable * gameIndex + header;
	for (int i = 0; i < bytesPerDropTable; i++)
	{
	    drops[i] = UByte.u1Byte(bytes[offset + i]);
	}
    }

    public void writeValuesToROM()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerDropTable * gameIndex + header;
	// System.out.println("Offset is: "+Integer.toHexString(offset)+" index is "+gameIndex);

	for (int i = 0; i < bytesPerDropTable; i++)
	{
	    // System.out.println("write Drop "+i+" new value is: "+drops[i] + " Old value is: "+bytes[offset+i]);
	    bytes[offset + i] = (byte) drops[i];
	}
	// System.out.println("\n");
    }

    public List<String> getItemNames(AbstractItems<AbstractItem> items)
    {
	List<String> names = new ArrayList<String>();
	List<Integer> counts = new ArrayList<Integer>();

	for (Integer i : drops)
	{
	    if (i == 0)
	    {
		continue;
	    }
	    String itemName = items.getName(i);
	    if (names.contains(itemName))
	    {
		int index = names.indexOf(itemName);
		counts.set(index, counts.get(index) + 1);
	    }
	    else
	    {
		names.add(itemName);
		counts.add(1);
	    }
	}

	List<String> results = new ArrayList<String>();
	for (int k = 0; k < names.size(); k++)
	{
	    results.add(names.get(k) + " " + counts.get(k) + "/16");
	}
	return results;
    }

    public static void printTables()
    {
	int header = MainMenu.getHeader();
	for (DropTable d : Lists.getDropTables().getModels())
	{
	    int offset = baseOffset + bytesPerDropTable * d.gameIndex + header;
	    System.out.println("Offset is: " + Integer.toHexString(offset) + " index is " + d.gameIndex);
	    for (int i = 0; i < bytesPerDropTable; i++)
	    {
		System.out.println("Drop " + i + " is: " + d.drops[i] + " " + Lists.getAbstractItems().getName(d.drops[i]));
	    }
	}
	System.out.println("\n");
    }

    public int[] getDrops()
    {
	return drops;
    }

    public void setDrops(int[] drops)
    {
	this.drops = drops;
    }
}