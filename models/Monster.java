package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import controllers.MainMenu;
import staticClasses.TextReader;
import staticClasses.UByte;

public class Monster extends Model implements Serializable
{
    private int			unknown1;
    private int			hp;
    private int			mp;
    private int			power;
    private int			guard;
    private int			magic;
    private int			speed;
    private List<Integer>	spells		= new ArrayList<Integer>();
    private List<Integer>	spellChance	= new ArrayList<Integer>();
    private int			laserRes;
    private int			unknownRes1;
    private int			unknownRes2;
    private int			fireRes;
    private int			iceRes;
    private int			vacuumRes;
    private int			debuffRes;
    private int			gold;
    private int			itemDropSet;
    private int			unknown2;
    private int			runFlag;
    private static final double	goldToExpRatio	= 2.203120312031203;

    private static final int	baseOffset	= 0x72F4;
    private static final int	bytesPerMonster	= 42;

    private List<Monster>	aliases;

    public Monster(int index)
    {
	super(index);
	this.setChronologicalIndex();
	aliases = new ArrayList<Monster>();
    }

    public void getValuesFromROM()
    {
	byte[] bytes = MainMenu.getBytes();
	int header = MainMenu.getHeader();
	int offset = baseOffset + bytesPerMonster * gameIndex + header;

	unknown1 = UByte.u1Byte(bytes[offset]);
	hp = UByte.u2Byte(bytes[offset + 1], bytes[offset + 2]);
	mp = UByte.u2Byte(bytes[offset + 3], bytes[offset + 4]);
	power = UByte.u2Byte(bytes[offset + 5], bytes[offset + 6]);
	guard = UByte.u2Byte(bytes[offset + 7], bytes[offset + 8]);
	magic = UByte.u1Byte(bytes[offset + 9]);
	speed = UByte.u1Byte(bytes[offset + 10]);

	offset += 11;
	for (int i = 0; i < 8; i++)
	{
	    spells.add(UByte.u1Byte(bytes[offset]));
	    offset++;
	}

	for (int i = 0; i < 8; i++)
	{
	    spellChance.add(UByte.u1Byte(bytes[offset]));
	    offset++;
	}

	laserRes = UByte.u1Byte(bytes[offset]);
	unknownRes1 = UByte.u1Byte(bytes[offset + 1]);
	unknownRes2 = UByte.u1Byte(bytes[offset + 2]);
	fireRes = UByte.u1Byte(bytes[offset + 3]);
	iceRes = UByte.u1Byte(bytes[offset + 4]);
	vacuumRes = UByte.u1Byte(bytes[offset + 5]);
	debuffRes = UByte.u1Byte(bytes[offset + 6]);

	gold = UByte.u2Byte(bytes[offset + 7], bytes[offset + 8]);
	itemDropSet = UByte.u1Byte(bytes[offset + 9]);
	unknown2 = UByte.u1Byte(bytes[offset + 10]);
	runFlag = UByte.u1Byte(bytes[offset + 11]);
	namePointer = UByte.u3Byte(bytes[offset + 12], bytes[offset + 13], bytes[offset + 14]);
	name = TextReader.readText(namePointer, bytes);

	int magicMod = 0;
	int speedMod = 0;

	int mod = power >> 8;
	power &= 0x03FF;

	if ((mod & 0x04) > 0)
	{
	    magicMod += 256;
	}
	if ((mod & 0x08) > 0)
	{
	    magicMod += 512;
	}
	if ((mod & 0x10) > 0)
	{
	    speedMod += 256;
	}
	if ((mod & 0x20) > 0)
	{
	    speedMod += 512;
	}

	magic += magicMod;
	speed += speedMod;
    }

    public void writeValuesToROM()
    {
	byte[] bytes = MainMenu.getBytes();
	int header = MainMenu.getHeader();
	int offset = baseOffset + bytesPerMonster * gameIndex + header;

	bytes[offset] = (byte) unknown1;
	bytes[offset + 1] = (byte) hp;
	bytes[offset + 2] = UByte.intToByte2(hp);
	bytes[offset + 3] = (byte) mp;
	bytes[offset + 4] = UByte.intToByte2(mp);
	bytes[offset + 5] = (byte) power;
	int magicValue = magic;
	int speedValue = speed;
	int powerValue = power;

	int mod = 0;
	if (magicValue >= 512)
	{
	    magicValue -= 512;
	    mod += 0x0800;
	}

	if (magicValue >= 256)
	{
	    magicValue -= 256;
	    mod += 0x0400;
	}

	if (speedValue >= 512)
	{
	    speedValue -= 512;
	    mod += 0x2000;
	}

	if (speedValue >= 256)
	{
	    speedValue -= 256;
	    mod += 0x1000;
	}

	powerValue |= mod;

	bytes[offset + 6] = UByte.intToByte2(powerValue);
	bytes[offset + 7] = (byte) guard;
	bytes[offset + 8] = UByte.intToByte2(guard);
	bytes[offset + 9] = (byte) magicValue;
	bytes[offset + 10] = (byte) speedValue;

	offset += 11;
	for (int i = 0; i < 8; i++)
	{
	    bytes[offset] = (byte) spells.get(i).intValue();
	    offset++;
	}

	for (int i = 0; i < 8; i++)
	{
	    bytes[offset] = (byte) spellChance.get(i).intValue();
	    offset++;
	}

	bytes[offset] = (byte) laserRes;
	bytes[offset + 1] = (byte) unknownRes1;
	bytes[offset + 2] = (byte) unknownRes2;
	bytes[offset + 3] = (byte) fireRes;
	bytes[offset + 4] = (byte) iceRes;
	bytes[offset + 5] = (byte) vacuumRes;
	bytes[offset + 6] = (byte) debuffRes;
	bytes[offset + 7] = (byte) gold;
	bytes[offset + 8] = UByte.intToByte2(gold);
	bytes[offset + 9] = (byte) itemDropSet;
	bytes[offset + 10] = (byte) unknown2;
	bytes[offset + 11] = (byte) runFlag;
    }

    private void setChronologicalIndex()
    {
	switch (gameIndex) {
	    case 0x00:
		chronologicalIndex = 0;
		break;
	    case 0x02:
		chronologicalIndex = 3;
		break;
	    case 0x03:
		chronologicalIndex = 13;
		break;
	    case 0x04:
		chronologicalIndex = 19;
		break;
	    case 0x05:
		chronologicalIndex = 18;
		break;
	    case 0x06:
		chronologicalIndex = 30;
		break;
	    case 0x07:
		chronologicalIndex = 21;
		break;
	    case 0x08:
		chronologicalIndex = 7;
		break;
	    case 0x09:
		chronologicalIndex = 23;
		break;
	    case 0x0A:
		chronologicalIndex = 12;
		break;
	    case 0x0B:
		chronologicalIndex = 99;
		break;
	    case 0x0C:
		chronologicalIndex = 99;
		break;
	    case 0x0D:
		chronologicalIndex = 17;
		break;
	    case 0x0E:
		chronologicalIndex = 40;
		break;
	    case 0x0F:
		chronologicalIndex = 55;
		break;
	    case 0x10:
		chronologicalIndex = 99;
		break;
	    case 0x11:
		chronologicalIndex = 10;
		break;
	    case 0x12:
		chronologicalIndex = 99;
		break;
	    case 0x13:
		chronologicalIndex = 99;
		break;
	    case 0x14:
		chronologicalIndex = 99;
		break;
	    case 0x15:
		chronologicalIndex = 16;
		break;
	    case 0x16:
		chronologicalIndex = 49;
		break;
	    case 0x17:
		chronologicalIndex = 52;
		break;
	    case 0x18:
		chronologicalIndex = 59;
		break;
	    case 0x19:
		chronologicalIndex = 32;
		break;
	    case 0x1A:
		chronologicalIndex = 9;
		break;
	    case 0x1B:
		chronologicalIndex = 26;
		break;
	    case 0x1C:
		chronologicalIndex = 33;
		break;
	    case 0x1D:
		chronologicalIndex = 34;
		break;
	    case 0x1E:
		chronologicalIndex = 8;
		break;
	    case 0x1F:
		chronologicalIndex = 14;
		break;
	    case 0x20:
		chronologicalIndex = 42;
		break;
	    case 0x21:
		chronologicalIndex = 6;
		break;
	    case 0x22:
		chronologicalIndex = 36;
		break;
	    case 0x23:
		chronologicalIndex = 37;
		break;
	    case 0x24:
		chronologicalIndex = 01;
		break;
	    case 0x26:
		chronologicalIndex = 48;
		break;
	    case 0x27:
		chronologicalIndex = 56;
		break;
	    case 0x28:
		chronologicalIndex = 67;
		break;
	    case 0x29:
		chronologicalIndex = 2;
		break;
	    case 0x2A:
		chronologicalIndex = 27;
		break;
	    case 0x2B:
		chronologicalIndex = 99;
		break;
	    case 0x2C:
		chronologicalIndex = 15;
		break;
	    case 0x2D:
		chronologicalIndex = 20;
		break;
	    case 0x2E:
		chronologicalIndex = 31;
		break;
	    case 0x2F:
		chronologicalIndex = 4;
		break;
	    case 0x30:
		chronologicalIndex = 5;
		break;
	    case 0x31:
		chronologicalIndex = 38;
		break;
	    case 0x32:
		chronologicalIndex = 29;
		break;
	    case 0x33:
		chronologicalIndex = 36;
		break;
	    case 0x34:
		chronologicalIndex = 48;
		break;
	    case 0x35:
		chronologicalIndex = 67;
		break;
	    case 0x36:
		chronologicalIndex = 67;
		break;
	    case 0x37:
		chronologicalIndex = 67;
		break;
	    case 0x3D:
		chronologicalIndex = 25;
		break;
	    case 0x3E:
		chronologicalIndex = 64;
		break;
	    case 0x3F:
		chronologicalIndex = 99;
		break;
	    case 0x40:
		chronologicalIndex = 11;
		break;
	    case 0x41:
		chronologicalIndex = 24;
		break;
	    case 0x42:
		chronologicalIndex = 61;
		break;
	    case 0x43:
		chronologicalIndex = 22;
		break;
	    case 0x44:
		chronologicalIndex = 35;
		break;
	    case 0x45:
		chronologicalIndex = 99;
		break;
	    case 0x46:
		chronologicalIndex = 53;
		break;
	    case 0x47:
		chronologicalIndex = 57;
		break;
	    case 0x48:
		chronologicalIndex = 28;
		break;
	    case 0x49:
		chronologicalIndex = 41;
		break;
	    case 0x4A:
		chronologicalIndex = 62;
		break;
	    case 0x4C:
		chronologicalIndex = 43;
		break;
	    case 0x4D:
		chronologicalIndex = 60;
		break;
	    case 0x4E:
		chronologicalIndex = 44;
		break;
	    case 0x4F:
		chronologicalIndex = 65;
		break;
	    case 0x50:
		chronologicalIndex = 39;
		break;
	    case 0x51:
		chronologicalIndex = 47;
		break;
	    case 0x52:
		chronologicalIndex = 58;
		break;
	    case 0x53:
		chronologicalIndex = 66;
		break;
	    case 0x54:
		chronologicalIndex = 51;
		break;
	    case 0x55:
		chronologicalIndex = 46;
		break;
	    case 0x56:
		chronologicalIndex = 50;
		break;
	    case 0x57:
		chronologicalIndex = 54;
		break;
	    case 0x58:
		chronologicalIndex = 45;
		break;
	    case 0x59:
		chronologicalIndex = 63;
		break;
	    case 0x5B:
		chronologicalIndex = 77;
		break;
	    case 0x5C:
		chronologicalIndex = 78;
		break;
	    case 0x5D:
		chronologicalIndex = 79;
		break;
	    case 0x5E:
		chronologicalIndex = 80;
		break;
	    case 0x5F:
		chronologicalIndex = 81;
		break;
	    case 0x60:
		chronologicalIndex = 82;
		break;
	    case 0x61:
		chronologicalIndex = 83;
		break;
	}
    }

    @Override
    public boolean equals(Object o)
    {
	if (o == null) { return false; }
	Monster m = (Monster) o;
	if (m.getName().trim().isEmpty() || name.trim().isEmpty()) { return false; }
	return name.equals(m.getName());
    }

    public List<String> getSpellChanceStrings()
    {
	List<String> spellChances = new ArrayList<String>();
	for (Integer i : spellChance)
	{
	    if (i > 0)
	    {
		spellChances.add(Integer.toString(i));
	    }
	    else
	    {
		spellChances.add("");
	    }
	}
	return spellChances;
    }

    public int getUnknown1()
    {
	return unknown1;
    }

    public void setUnknown1(int unknown1)
    {
	this.unknown1 = unknown1;
    }

    public int getHp()
    {
	return hp;
    }

    public void setHp(int hp)
    {
	this.hp = hp;
    }

    public int getMp()
    {
	return mp;
    }

    public void setMp(int mp)
    {
	this.mp = mp;
    }

    public int getPower()
    {
	return power;
    }

    public void setPower(int power)
    {
	this.power = power;
    }

    public int getGuard()
    {
	return guard;
    }

    public void setGuard(int guard)
    {
	this.guard = guard;
    }

    public int getMagic()
    {
	return magic;
    }

    public void setMagic(int magic)
    {
	this.magic = magic;
    }

    public int getSpeed()
    {
	return speed;
    }

    public void setSpeed(int speed)
    {
	this.speed = speed;
    }

    public int getLaserRes()
    {
	return laserRes;
    }

    public void setLaserRes(int laserRes)
    {
	this.laserRes = laserRes;
    }

    public int getUnknownRes1()
    {
	return unknownRes1;
    }

    public void setUnknownRes1(int unknownRes1)
    {
	this.unknownRes1 = unknownRes1;
    }

    public int getUnknownRes2()
    {
	return unknownRes2;
    }

    public void setUnknownRes2(int unknownRes2)
    {
	this.unknownRes2 = unknownRes2;
    }

    public int getFireRes()
    {
	return fireRes;
    }

    public void setFireRes(int fireRes)
    {
	this.fireRes = fireRes;
    }

    public int getIceRes()
    {
	return iceRes;
    }

    public void setIceRes(int iceRes)
    {
	this.iceRes = iceRes;
    }

    public int getVacuumRes()
    {
	return vacuumRes;
    }

    public void setVacuumRes(int vacuumRes)
    {
	this.vacuumRes = vacuumRes;
    }

    public int getDebuffRes()
    {
	return debuffRes;
    }

    public void setDebuffRes(int debuffRes)
    {
	this.debuffRes = debuffRes;
    }

    public int getGold()
    {
	return gold;
    }

    public void setGold(int gold)
    {
	this.gold = gold;
    }

    public int getItemDropSet()
    {
	return itemDropSet;
    }

    public void setItemDropSet(int itemDropSet)
    {
	this.itemDropSet = itemDropSet;
    }

    public int getUnknown2()
    {
	return unknown2;
    }

    public void setUnknown2(int unknown2)
    {
	this.unknown2 = unknown2;
    }

    public int getRunFlag()
    {
	return runFlag;
    }

    public void setRunFlag(int runFlag)
    {
	this.runFlag = runFlag;
    }

    public List<Integer> getSpells()
    {
	return spells;
    }

    public List<Integer> getSpellChance()
    {
	return spellChance;
    }

    public int getExperience()
    {
	return (int) (gold * goldToExpRatio);
    }

    public List<Monster> getAliases()
    {
	return aliases;
    }

    public static int goldToExp(int value)
    {
	return (int) (value * goldToExpRatio);
    }

    public Monster(Monster m, int listIndex)
    {
	this.unknown1 = m.unknown1;
	this.hp = m.hp;
	this.mp = m.mp;
	this.power = m.power;
	this.guard = m.guard;
	this.magic = m.magic;
	this.speed = m.speed;

	for (Integer i : m.getSpells())
	{
	    spells.add(i);
	}

	for (Integer i : m.getSpellChance())
	{
	    spellChance.add(i);
	}

	this.laserRes = m.laserRes;
	this.unknownRes1 = m.unknownRes1;
	this.unknownRes2 = m.unknownRes2;
	this.fireRes = m.fireRes;
	this.iceRes = m.iceRes;
	this.vacuumRes = m.vacuumRes;
	this.debuffRes = m.debuffRes;
	this.gold = m.gold;
	this.itemDropSet = m.itemDropSet;
	this.unknown2 = m.unknown2;
	this.runFlag = m.runFlag;
	this.namePointer = m.namePointer;
	this.name = m.name;
	this.gameIndex = m.gameIndex;
	this.chronologicalIndex = m.chronologicalIndex;

	int index = 0;
	for (Monster monster : m.aliases)
	{
	    aliases.add(new Monster(monster, index));
	    index++;
	}
    }

}
