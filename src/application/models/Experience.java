package application.models;

import java.io.Serializable;
import java.util.List;

import application.ROM;
import application.controllers.MainMenu;

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


	for (int i = 0; i < expListSize; i++)
	{
		ROM.setOffset(baseOffset + bytesPerExp * i);
	    totalExpTable.add(ROM.getNextTriple());
	}
	marginalExpTable.add(totalExpTable.get(0));
	for (int i = 1; i < totalExpTable.size(); i++)
	{
	    marginalExpTable.add(totalExpTable.get(i) - totalExpTable.get(i - 1));
	}
    }

    public static void saveModels()
    {

	int sum = 0;
	for (int i = 0; i < expListSize; i++)
	{
	    ROM.setOffset(baseOffset + bytesPerExp * i);
	    int value = marginalExpTable.get(i);
	    sum += value;
	    ROM.setNextTriple(sum);
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
