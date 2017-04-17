package application.models;

import java.io.Serializable;

import application.ROM;
import application.controllers.MainMenu;
import application.staticClasses.TextReader;

public class Weapon extends Equipment implements Serializable
{
    public Weapon(int index)
    {
	super(index);
	itemCode = index + 0x64;
    }

    private static int baseOffset     = 0x639D;
    private static int bytesPerWeapon = 10;

    public void getValuesFromROM()
    {
		ROM.setOffset(baseOffset + bytesPerWeapon * gameIndex);

	power = ROM.getNextShort();
	cost = ROM.getNextShort();
	equipCode = ROM.getNextByte();
	namePointer = ROM.getNextTriple();
	name = TextReader.readText(namePointer);
	discount = ROM.getNextShort();
    }

    public void writeValuesToROM()
    {
	ROM.setOffset(baseOffset + bytesPerWeapon * gameIndex);

	ROM.setNextShort(power);
		ROM.setNextShort(cost);
		ROM.setNextByte(equipCode);
		ROM.setNextTriple(namePointer);
		ROM.setNextShort(discount);
    }

    public Weapon(Weapon w, int listIndex)
    {
	super(w, listIndex);
    }

}
