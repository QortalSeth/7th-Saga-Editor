package application.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import application.ROM;
import application.controllers.MainMenu;
import application.models.lists.Characters;

public class Apprentice extends Model implements Serializable
{
    private List<EquipmentAtLevel>  equipmentAtLevels          = new ArrayList<EquipmentAtLevel>();
    private int[][]		            dialogues;
    private static List<Apprentice> models;
    private static List<Apprentice> Dmodels;
    private static final int	    apprenticeListSize         = 7;
    private int			            equipCode;
    private int[]		            dialoguePointers;
    private static final int	    baseOffset		           = 0x8740;
    private static final int	    bytesPerApprentice         = 60;
    private static final int	    baseEquipmentOffset	       = 0x88E4;
    private static final int	    bytesPerEquipmentList      = 44;
    private static final int	    equipmentRowsPerApprentice = 10;
    private static final int	    numOfDialogues	       = 6;

    public Apprentice(int gameIndex)
    {
	super(gameIndex);
	name = Characters.getCharacterNames()[gameIndex];
	equipCode = Characters.getEquipCodes()[gameIndex];
    }

    public void getValuesFromROM()
    {
	dialoguePointers = new int[numOfDialogues];
	ROM.setOffset(baseOffset + bytesPerApprentice * gameIndex);
	for(int i=0; i<numOfDialogues;i++)
	{
		dialoguePointers[i] = ROM.getNextTriple();
	}
	name = Characters.getCharacterNames()[gameIndex];

	dialogues = new int[apprenticeListSize][numOfDialogues];

	for (int i = 0; i < apprenticeListSize; i++)
	{
	    for (int k = 0; k < numOfDialogues; k++)
	    {
		dialogues[i][k] = ROM.getNextByte();
	    }
	}

	ROM.setOffset(baseEquipmentOffset + bytesPerEquipmentList * gameIndex);
	for (int i = 0; i < equipmentRowsPerApprentice; i++)
	{
	    equipmentAtLevels.add(new EquipmentAtLevel(ROM.getNextByte(), new int[]
	    {
				ROM.getNextByte(), ROM.getNextByte(), ROM.getNextByte()
	    }));
	}
    }

    public void writeValuesToROM()
    {
	ROM.setOffset(baseOffset + 18 + bytesPerApprentice * gameIndex);

	for (int i = 0; i < apprenticeListSize; i++)
	{
	    for (int k = 0; k < numOfDialogues; k++)
	    {
		ROM.setNextByte(dialogues[i][k]);
	    }
	}

	ROM.setOffset(baseEquipmentOffset + bytesPerEquipmentList * gameIndex);

	for (int i = 0; i < equipmentRowsPerApprentice; i++)
	{
	    for (EquipmentAtLevel entry : equipmentAtLevels)
	    {
		ROM.setNextByte(entry.getLevel());
		ROM.setNextByte(entry.getEquipment()[0]);
		ROM.setNextByte(entry.getEquipment()[1]);
		ROM.setNextByte(entry.getEquipment()[2]);
	    }
	}
    }

    public List<EquipmentAtLevel> getEquipmentAtLevels()
    {
	return equipmentAtLevels;
    }

    public void setEquipmentAtLevels(List<EquipmentAtLevel> equipmentAtLevels)
    {
	this.equipmentAtLevels = equipmentAtLevels;
    }

    public int[][] getDialogues()
    {
	return dialogues;
    }

    public static List<Apprentice> getModels()
    {
	return models;
    }

    public static List<Apprentice> getDModels()
    {
	return Dmodels;
    }

    public static void setDModels(List<Apprentice> defaultApprentices)
    {
	Apprentice.Dmodels = defaultApprentices;
    }

    public int getEquipCode()
    {
	return equipCode;
    }

    public int[] getDialoguePointers()
    {
	return dialoguePointers;
    }

}
