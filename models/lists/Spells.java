package models.lists;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import models.Spell;

public class Spells extends Models<Spell> implements Serializable
{
    private static final int spellListSize = 61;

    public Spells()
    {
	super();
    }

    public Spells(Spells addFrom, boolean keepEmptyModel)
    {
	super(addFrom, keepEmptyModel);
    }

    @Override
    public void initializeModel(List<Spell> models)
    {
	for (int i = 0; i < spellListSize; i++)
	{
	    Spell s = new Spell(i);
	    s.getValuesFromROM();
	    models.add(s);
	}
    }

    @Override
    public void saveModels()
    {
	for (Spell s : models)
	    s.writeValuesToROM();
    }

    public void sortSpellsByType()
    {
	sortList(Spells.spellTypeComparator);
    }

    public static final Comparator<Spell> spellTypeComparator = new Comparator<Spell>()
    {
	@Override
	public int compare(Spell s1, Spell s2)
	{
	    int difference = s1.getElementPriority() - s2.getElementPriority();
	    if (difference != 0)
		return difference;
	    else
	    {
		difference = s1.getTarget() - s2.getTarget();
		if (difference != 0)
		    return difference;

		else
		{
		    return s1.getCost() - s2.getCost();
		}
	    }
	}
    };

    // public static List<String> spellCodesToNames(Spells spells, List<Integer> spellCodes)
    // {
    // List<String> spellNames = new ArrayList<String>();
    // for (Integer spellIndex : spellCodes)
    // {
    // if (spellIndex > 0)
    // {
    // spellNames.add(Excel.modifyCase(spells.getName(spellIndex)));
    // }
    // else
    // spellNames.add("");
    // }
    // return spellNames;
    //
    // }
}
