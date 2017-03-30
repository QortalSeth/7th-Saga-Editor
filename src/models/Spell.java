package models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import controllers.MainMenu;
import staticClasses.TextReader;
import staticClasses.UByte;

public class Spell extends Model implements Serializable
{
    private int			power;
    private int			target;
    private int			cost;
    private int			domain;
    private int			element;
    private int			unknown1;
    private int			unknown2;

    private static final int	baseOffset    = 0x7018;
    private static final int	bytesPerSpell = 12;

    private static List<String>	elements      = Arrays.asList(new String[]
						{
							"None", "Lightning", "Unknown", "Unknown", "Fire", "Ice", "Vacuum", "Debuff"
						});

    public Spell(int index)
    {
	super(index);
    }

    public void getValuesFromROM()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerSpell * gameIndex + header;

	power = UByte.u2Byte(bytes[offset], bytes[offset + 1]);
	target = UByte.u1Byte(bytes[offset + 2]);
	cost = UByte.u2Byte(bytes[offset + 3], bytes[offset + 4]);
	domain = UByte.u1Byte(bytes[offset + 5]);
	element = UByte.u1Byte(bytes[offset + 6]);
	unknown1 = UByte.u1Byte(bytes[offset + 7]);
	unknown2 = UByte.u1Byte(bytes[offset + 8]);
	namePointer = UByte.u3Byte(bytes[offset + 9], bytes[offset + 10], bytes[offset + 11]);
	name = TextReader.readText(namePointer, bytes);
    }

    public void writeValuesToROM()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerSpell * gameIndex + header;

	bytes[offset] = (byte) power;
	bytes[offset + 1] = UByte.intToByte2(power);
	bytes[offset + 2] = (byte) target;
	bytes[offset + 3] = (byte) cost;
	bytes[offset + 4] = UByte.intToByte2(cost);
	bytes[offset + 5] = (byte) domain;
	bytes[offset + 6] = (byte) element;
	bytes[offset + 7] = (byte) unknown1;
	bytes[offset + 8] = (byte) unknown2;
    }

    public int getElementPriority()
    {
	int priority = 10;
	switch (element) {
	    case 0:
		priority = 5;
		break;
	    case 1:
		priority = 2;
		break;
	    case 2:
		priority = 2;
		break;
	    case 3:
		priority = 2;
		break;
	    case 4:
		priority = 0;
		break;
	    case 5:
		priority = 1;
		break;
	    case 6:
		priority = 3;
		break;
	    case 7:
		priority = 4;
		break;
	    default:
		System.out.println("Error, priority is: " + priority + " element is: " + element);
	}
	return priority;
    }

    // private static final Comparator<Spell> spellTypeComparator = new Comparator<Spell>()
    // {
    // @Override
    // public int compare(Spell s1, Spell s2)
    // {
    // return s1.getTypePriority() - s2.getTypePriority();
    // }
    // };

    public int getPower()
    {
	return power;
    }

    public void setPower(int power)
    {
	this.power = power;
    }

    public int getTarget()
    {
	return target;
    }

    public void setTarget(int target)
    {
	this.target = target;
    }

    public int getCost()
    {
	return cost;
    }

    public void setCost(int cost)
    {
	this.cost = cost;
    }

    public int getDomain()
    {
	return domain;
    }

    public void setDomain(int domain)
    {
	this.domain = domain;
    }

    public int getElement()
    {
	return element;
    }

    public void setElement(int element)
    {
	this.element = element;
    }

    public int getUnknown1()
    {
	return unknown1;
    }

    public void setUnknown1(int unknown1)
    {
	this.unknown1 = unknown1;
    }

    public int getUnknown2()
    {
	return unknown2;
    }

    public void setUnknown2(int unknown2)
    {
	this.unknown2 = unknown2;
    }

    public static List<String> getElements()
    {
	return elements;
    }

    public Spell(Spell s, int listIndex)
    {
	this.power = s.power;
	this.target = s.target;
	this.cost = s.cost;
	this.domain = s.domain;
	this.element = s.element;
	this.unknown1 = s.unknown1;
	this.unknown2 = s.unknown2;
	this.namePointer = s.namePointer;
	this.name = s.name;
	this.gameIndex = s.gameIndex;
    }

}
