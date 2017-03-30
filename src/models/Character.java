package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import controllers.MainMenu;
import models.lists.Characters;
import staticClasses.UByte;

public class Character extends Model implements Serializable
{
    private int		     hpStart;
    private int		     mpStart;
    private int		     powerStart;
    private int		     guardStart;
    private int		     magicStart;
    private int		     speedStart;

    private int		     hpGrowth;
    private int		     mpGrowth;
    private int		     powerGrowth;
    private int		     guardGrowth;
    private int		     magicGrowth;
    private int		     speedGrowth;

    private int		     weaponStart;
    private int		     armorStart;
    private int		     accessoryStart;
    private int		     experienceStart;

    private List<Integer>    spells;
    private List<Integer>    spellLevels;

    private int		     equipCode;

    private static final int baseOffset	       = 0x623F;
    private static final int bytesPerCharacter = 18;

    private static final int baseSpellOffset   = 0x62BD;
    private static final int bytesPerSpellList = 32;

    public Character(String name, int gameIndex)
    {
	super(gameIndex);
	this.name = name;
	this.equipCode = Characters.getEquipCodes()[gameIndex];
    }

    public void getValuesFromROM()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerCharacter * gameIndex + header;

	hpStart = UByte.u2Byte(bytes[offset], bytes[offset + 1]);
	mpStart = UByte.u2Byte(bytes[offset + 2], bytes[offset + 3]);
	powerStart = UByte.u1Byte(bytes[offset + 4]);
	guardStart = UByte.u1Byte(bytes[offset + 5]);
	magicStart = UByte.u1Byte(bytes[offset + 6]);
	speedStart = UByte.u1Byte(bytes[offset + 7]);

	hpGrowth = UByte.u1Byte(bytes[offset + 8]);
	mpGrowth = UByte.u1Byte(bytes[offset + 9]);
	powerGrowth = UByte.u1Byte(bytes[offset + 10]);
	guardGrowth = UByte.u1Byte(bytes[offset + 11]);
	magicGrowth = UByte.u1Byte(bytes[offset + 12]);
	speedGrowth = UByte.u1Byte(bytes[offset + 13]);

	weaponStart = UByte.u1Byte(bytes[offset + 14]);
	armorStart = UByte.u1Byte(bytes[offset + 15]);
	accessoryStart = UByte.u1Byte(bytes[offset + 16]);
	experienceStart = UByte.u1Byte(bytes[offset + 17]);

	int spellListOffset = baseSpellOffset + bytesPerSpellList * gameIndex + header;

	spells = new ArrayList<Integer>();
	spellLevels = new ArrayList<Integer>();

	for (int i = 0; i < 16; i++)
	{
	    spells.add(UByte.u1Byte(bytes[spellListOffset]));
	    spellListOffset++;
	}

	for (int i = 0; i < 16; i++)
	{
	    spellLevels.add(UByte.u1Byte(bytes[spellListOffset]));
	    spellListOffset++;
	}
    }

    public void writeValuesToROM()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerCharacter * gameIndex + header;

	bytes[offset] = (byte) hpStart;
	bytes[offset + 1] = UByte.intToByte2(hpStart);

	bytes[offset + 2] = (byte) mpStart;
	bytes[offset + 3] = UByte.intToByte2(mpStart);

	bytes[offset + 4] = (byte) powerStart;
	bytes[offset + 5] = (byte) guardStart;
	bytes[offset + 6] = (byte) magicStart;
	bytes[offset + 7] = (byte) speedStart;

	bytes[offset + 8] = (byte) hpGrowth;
	bytes[offset + 9] = (byte) mpGrowth;
	bytes[offset + 10] = (byte) powerGrowth;
	bytes[offset + 11] = (byte) guardGrowth;
	bytes[offset + 12] = (byte) magicGrowth;
	bytes[offset + 13] = (byte) speedGrowth;
	bytes[offset + 14] = (byte) weaponStart;
	bytes[offset + 15] = (byte) armorStart;
	bytes[offset + 16] = (byte) accessoryStart;
	bytes[offset + 17] = (byte) experienceStart;

	int spellListOffset = baseSpellOffset + bytesPerSpellList * gameIndex + header;

	for (int i : spells)
	{
	    bytes[spellListOffset] = (byte) i;
	    spellListOffset++;
	}

	for (int i : spellLevels)
	{
	    bytes[spellListOffset] = (byte) i;
	    spellListOffset++;
	}
    }

    public String getEquipName()
    {
	if (this.getName().equals("Lux"))
	{
	    return "X";
	}
	else
	{
	    return name.substring(0, 1);
	}
    }

    public boolean canLearnSpell(Integer spellID)
    {
	if (spells.contains(spellID))
	{
	    return true;
	}
	else
	{
	    return false;
	}
    }

    public int getSpellLevel(int spellID)
    {
	int index = spells.indexOf(spellID);
	if (index < 0)
	{
	    return -1;
	}
	else
	    return spellLevels.get(index);
    }

    public int getHpStart()
    {
	return hpStart;
    }

    public void setHpStart(int hpStart)
    {
	this.hpStart = hpStart;
    }

    public int getMpStart()
    {
	return mpStart;
    }

    public void setMpStart(int mpStart)
    {
	this.mpStart = mpStart;
    }

    public int getPowerStart()
    {
	return powerStart;
    }

    public void setPowerStart(int powerStart)
    {
	this.powerStart = powerStart;
    }

    public int getGuardStart()
    {
	return guardStart;
    }

    public void setGuardStart(int guardStart)
    {
	this.guardStart = guardStart;
    }

    public int getMagicStart()
    {
	return magicStart;
    }

    public void setMagicStart(int magicStart)
    {
	this.magicStart = magicStart;
    }

    public int getSpeedStart()
    {
	return speedStart;
    }

    public void setSpeedStart(int speedStart)
    {
	this.speedStart = speedStart;
    }

    public int getHpGrowth()
    {
	return hpGrowth;
    }

    public void setHpGrowth(int hpGrowth)
    {
	this.hpGrowth = hpGrowth;
    }

    public int getMpGrowth()
    {
	return mpGrowth;
    }

    public void setMpGrowth(int mpGrowth)
    {
	this.mpGrowth = mpGrowth;
    }

    public int getPowerGrowth()
    {
	return powerGrowth;
    }

    public void setPowerGrowth(int powerGrowth)
    {
	this.powerGrowth = powerGrowth;
    }

    public int getGuardGrowth()
    {
	return guardGrowth;
    }

    public void setGuardGrowth(int guardGrowth)
    {
	this.guardGrowth = guardGrowth;
    }

    public int getMagicGrowth()
    {
	return magicGrowth;
    }

    public void setMagicGrowth(int magicGrowth)
    {
	this.magicGrowth = magicGrowth;
    }

    public int getSpeedGrowth()
    {
	return speedGrowth;
    }

    public void setSpeedGrowth(int speedGrowth)
    {
	this.speedGrowth = speedGrowth;
    }

    public int getWeaponStart()
    {
	return weaponStart;
    }

    public void setWeaponStart(int weaponStart)
    {
	this.weaponStart = weaponStart;
    }

    public int getArmorStart()
    {
	return armorStart;
    }

    public void setArmorStart(int armorStart)
    {
	this.armorStart = armorStart;
    }

    public int getAccessoryStart()
    {
	return accessoryStart;
    }

    public void setAccessoryStart(int accessoryStart)
    {
	this.accessoryStart = accessoryStart;
    }

    public int getExpStart()
    {
	return experienceStart;
    }

    public void setExpStart(int experienceStart)
    {
	this.experienceStart = experienceStart;
    }

    public List<Integer> getSpells()
    {
	return spells;
    }

    public void setSpells(List<Integer> spells)
    {
	this.spells = spells;
    }

    public List<Integer> getSpellLevels()
    {
	return spellLevels;
    }

    public void setSpellLevels(List<Integer> spellLevels)
    {
	this.spellLevels = spellLevels;
    }

    public static int getBaseoffset()
    {
	return baseOffset;
    }

    public static int getBytespercharacter()
    {
	return bytesPerCharacter;
    }

    public int getEquipCode()
    {
	return equipCode;
    }
}
