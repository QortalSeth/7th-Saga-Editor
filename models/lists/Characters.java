package models.lists;

import java.io.Serializable;
import java.util.List;

import models.Character;

public class Characters extends Models<models.Character> implements Serializable
{

    public static final int characterListSize	 = 7;

    private static String[] characterNames	 =
    {
	    "Kamil", "Olvan", "Esuna", "Wilme", "Lux", "Valsu", "Lejes"
    };
    private static int[]    equipCodes		 =
    {
	    0b00000001, 0b00000010, 0b00000100, 0b00001000, 0b00010000, 0b00100000, 0b01000000
    };

    private static int[]    chronologicalIndexes =
    {
	    3, 2, 6, 0, 1, 5, 4
    };

    public Characters()
    {
	super();
    }

    public Characters(Characters addFrom)
    {
	super(addFrom);
    }

    @Override
    public void initializeModel(List<models.Character> models)
    {
	for (int i = 0; i < characterListSize; i++)
	{
	    Character c = new Character(Characters.characterNames[i], i);
	    c.getValuesFromROM();
	    c.setChronologicalIndex(chronologicalIndexes[i]);
	    models.add(c);
	}
    }

    @Override
    public void saveModels()
    {
	for (models.Character c : models)
	    c.writeValuesToROM();
    }

    // public static List<Character> getListByClassOrder(List<Character> charactersToSort)
    // {
    // List<Character> characters = new ArrayList<Character>();
    // characters.add(charactersToSort.get(3));
    // characters.add(charactersToSort.get(4));
    // characters.add(charactersToSort.get(1));
    // characters.add(charactersToSort.get(0));
    // characters.add(charactersToSort.get(6));
    // characters.add(charactersToSort.get(5));
    // characters.add(charactersToSort.get(2));
    // return characters;
    // }

    // public void sortByClassOrder()
    // {
    //
    //
    // kamil olvan esuna wilme lux valsu lejes
    // wilme lux olvan kamil lejes valsu esuna
    // ObservableList<Character> characters = FXCollections.observableArrayList();
    // characters.add(models.get(3));
    // characters.add(models.get(4));
    // characters.add(models.get(1));
    // characters.add(models.get(0));
    // characters.add(models.get(6));
    // characters.add(models.get(5));
    // characters.add(models.get(2));
    //
    // ObservableList<Character> Dcharacters = FXCollections.observableArrayList();
    // Dcharacters.add(models.get(3));
    // Dcharacters.add(models.get(4));
    // Dcharacters.add(models.get(1));
    // Dcharacters.add(models.get(0));
    // Dcharacters.add(models.get(6));
    // Dcharacters.add(models.get(5));
    // Dcharacters.add(models.get(2));
    //
    // models = characters;
    // Dmodels = Dcharacters;
    // }

    public boolean charactersCanlearnSpell(int spellID)
    {
	for (Character c : models)
	{
	    if (c.canLearnSpell(spellID)) { return true; }
	}
	return false;
    }

    public static String[] getCharacterNames()
    {
	return characterNames;
    }

    public static int[] getEquipCodes()
    {
	return equipCodes;
    }

}
