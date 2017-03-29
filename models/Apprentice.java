package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import controllers.MainMenu;
import models.lists.Characters;
import staticClasses.UByte;

public class Apprentice extends Model implements Serializable
{
    private List<EquipmentAtLevel>  equipmentAtLevels	       = new ArrayList<EquipmentAtLevel>();
    private int[][]		    dialogues;
    private static List<Apprentice> models;
    private static List<Apprentice> Dmodels;

    private static final int	    apprenticeListSize	       = 7;

    private int			    equipCode;

    private int[]		    dialoguePointers;

    private static final int	    baseOffset		       = 0x8740;
    private static final int	    bytesPerApprentice	       = 60;

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
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerApprentice * gameIndex + header;
	dialoguePointers = new int[numOfDialogues];
	dialoguePointers[0] = UByte.u3Byte(bytes[offset], bytes[offset + 1], bytes[offset + 2]);
	dialoguePointers[1] = UByte.u3Byte(bytes[offset + 3], bytes[offset + 4], bytes[offset + 5]);
	dialoguePointers[2] = UByte.u3Byte(bytes[offset + 6], bytes[offset + 7], bytes[offset + 8]);
	dialoguePointers[3] = UByte.u3Byte(bytes[offset + 9], bytes[offset + 10], bytes[offset + 11]);
	dialoguePointers[4] = UByte.u3Byte(bytes[offset + 12], bytes[offset + 13], bytes[offset + 14]);
	dialoguePointers[5] = UByte.u3Byte(bytes[offset + 15], bytes[offset + 16], bytes[offset + 17]);
	name = Characters.getCharacterNames()[gameIndex];

	dialogues = new int[apprenticeListSize][numOfDialogues];

	offset += 18;
	for (int i = 0; i < apprenticeListSize; i++)
	{
	    for (int k = 0; k < numOfDialogues; k++)
	    {
		dialogues[i][k] = UByte.u1Byte(bytes[offset]);
		offset++;
	    }
	}

	offset = baseEquipmentOffset + bytesPerEquipmentList * gameIndex + header;
	for (int i = 0; i < equipmentRowsPerApprentice; i++)
	{
	    // equipment.put(UByte.u1Byte(bytes[offset]),new
	    // int[]{UByte.u1Byte(bytes[offset+1]),UByte.u1Byte(bytes[offset+2]),UByte.u1Byte(bytes[offset+3])});
	    equipmentAtLevels.add(new EquipmentAtLevel(UByte.u1Byte(bytes[offset]), new int[]
	    {
		    UByte.u1Byte(bytes[offset + 1]), UByte.u1Byte(bytes[offset + 2]), UByte.u1Byte(bytes[offset + 3])
	    }));
	    offset += 4;
	}
    }

    public void writeValuesToROM()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerApprentice * gameIndex + header;
	offset += 18;

	for (int i = 0; i < apprenticeListSize; i++)
	{
	    for (int k = 0; k < numOfDialogues; k++)
	    {
		bytes[offset] = (byte) dialogues[i][k];
		offset++;
	    }
	}

	offset = baseEquipmentOffset + bytesPerEquipmentList * gameIndex + header;

	for (int i = 0; i < equipmentRowsPerApprentice; i++)
	{
	    for (EquipmentAtLevel entry : equipmentAtLevels)
	    {
		bytes[offset] = (byte) entry.getLevel();
		bytes[offset + 1] = (byte) entry.getEquipment()[0];
		bytes[offset + 2] = (byte) entry.getEquipment()[1];
		bytes[offset + 3] = (byte) entry.getEquipment()[2];
		offset += 4;
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
