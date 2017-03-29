package models;

import java.io.Serializable;
import java.util.List;

import controllers.MainMenu;
import staticClasses.UByte;

public class Experience implements Serializable
{

    private static final int	 baseOffset  = 0x8CC8;
    private static final int	 bytesPerExp = 3;
    private static final int	 expListSize = 79;
    private static List<Integer> totalExpTable;
    private static List<Integer> marginalExpTable;
    private static List<Integer> defaultTotalExpTable;
    private static List<Integer> defaultMarginalExpTable;

    public static void getValuesFromROM()
    {

	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	for (int i = 0; i < expListSize; i++)
	{
	    int offset = baseOffset + bytesPerExp * i + header;
	    int value = UByte.u3Byte(bytes[offset], bytes[offset + 1], bytes[offset + 2]);
	    totalExpTable.add(value);
	}
	marginalExpTable.add(totalExpTable.get(0));
	for (int i = 1; i < totalExpTable.size(); i++)
	{
	    marginalExpTable.add(totalExpTable.get(i) - totalExpTable.get(i - 1));
	}
    }

    public static void saveModels()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int sum = 0;
	for (int i = 0; i < expListSize; i++)
	{
	    int offset = baseOffset + bytesPerExp * i + header;
	    int value = marginalExpTable.get(i);
	    sum += value;
	    bytes[offset] = (byte) sum;
	    bytes[offset + 1] = UByte.intToByte2(sum);
	    bytes[offset + 2] = UByte.intToByte3(sum);
	}
    }

    public static List<Integer> getTotalExpTable()
    {
	return totalExpTable;
    }

    public static List<Integer> getMarginalExpTable()
    {
	return marginalExpTable;
    }

    public static List<Integer> getDefaultTotalExpTable()
    {
	return defaultTotalExpTable;
    }

    public static void setDefaultTotalExpTable(List<Integer> defaultTotalExpTable)
    {
	Experience.defaultTotalExpTable = defaultTotalExpTable;
    }

    public static List<Integer> getDefaultMarginalExpTable()
    {
	return defaultMarginalExpTable;
    }

    public static void setDefaultMarginalExpTable(List<Integer> defaultMarginalExpTable)
    {
	Experience.defaultMarginalExpTable = defaultMarginalExpTable;
    }

    public static void setTotalExpTable(List<Integer> totalExpTable)
    {
	Experience.totalExpTable = totalExpTable;
    }

    public static void setMarginalExpTable(List<Integer> marginalExpTable)
    {
	Experience.marginalExpTable = marginalExpTable;
    }

}
