package application.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import application.ROM;
import application.controllers.MainMenu;
import application.models.lists.Characters;

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

		ROM.setOffset(baseOffset + bytesPerCharacter * gameIndex);

	hpStart = ROM.getNextShort();
	mpStart = ROM.getNextShort();
	powerStart = ROM.getNextByte();
	guardStart = ROM.getNextByte();
	magicStart = ROM.getNextByte();
	speedStart = ROM.getNextByte();

	hpGrowth = ROM.getNextByte();
	mpGrowth = ROM.getNextByte();
	powerGrowth = ROM.getNextByte();
	guardGrowth = ROM.getNextByte();
	magicGrowth = ROM.getNextByte();
	speedGrowth = ROM.getNextByte();

	weaponStart = ROM.getNextByte();
	armorStart = ROM.getNextByte();
	accessoryStart = ROM.getNextByte();
	experienceStart = ROM.getNextByte();

	ROM.setOffset(baseSpellOffset + bytesPerSpellList * gameIndex);

	spells = new ArrayList<Integer>();
	spellLevels = new ArrayList<Integer>();

	for (int i = 0; i < 16; i++)
	{
	    spells.add(ROM.getNextByte());
	}

	for (int i = 0; i < 16; i++)
	{
	    spellLevels.add(ROM.getNextByte());
	}
    }

    public void writeValuesToROM()
    {

	ROM.setOffset(baseOffset + bytesPerCharacter * gameIndex);
	ROM.setNextShort(hpStart);
	ROM.setNextShort(mpStart);
	ROM.setNextByte(powerStart);
		ROM.setNextByte(guardStart);
		ROM.setNextByte(magicStart);
		ROM.setNextByte(speedStart);
		ROM.setNextByte(hpGrowth);
		ROM.setNextByte(mpGrowth);
		ROM.setNextByte(powerGrowth);
		ROM.setNextByte(guardGrowth);
		ROM.setNextByte(magicGrowth);
		ROM.setNextByte(speedGrowth);
		ROM.setNextByte(weaponStart);
		ROM.setNextByte(armorStart);
		ROM.setNextByte(accessoryStart);
		ROM.setNextByte(experienceStart);


	ROM.setOffset(baseSpellOffset + bytesPerSpellList * gameIndex);

	for (int i : spells)
	{
		ROM.setNextByte(i);
	}

	for (int i : spellLevels)
	{
		ROM.setNextByte(i);
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
		return spells.contains(spellID);
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
