package application.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import application.ROM;
import application.controllers.MainMenu;
import application.staticClasses.TextReader;

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

    private static List<String>	elements      = Arrays.asList("None", "Lightning", "Unknown", "Unknown", "Fire", "Ice", "Vacuum", "Debuff");

    public Spell(int index)
    {
	super(index);
    }

    public void getValuesFromROM()
    {
		ROM.setOffset(baseOffset + bytesPerSpell * gameIndex);

	power = ROM.getNextShort();
	target = ROM.getNextByte();
	cost = ROM.getNextShort();
	domain = ROM.getNextByte();
	element = ROM.getNextByte();
	unknown1 = ROM.getNextByte();
	unknown2 = ROM.getNextByte();
	namePointer = ROM.getNextTriple();
	name = TextReader.readText(namePointer);
    }

    public void writeValuesToROM()
    {
	ROM.setOffset(baseOffset + bytesPerSpell * gameIndex);

	ROM.setNextShort(power);
	ROM.setNextByte(target);
		ROM.setNextShort(cost);
		ROM.setNextByte(domain);
		ROM.setNextByte(element);
		ROM.setNextByte(unknown1);
		ROM.setNextByte(unknown2);
		ROM.setNextTriple(namePointer);
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
