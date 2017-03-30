package models;

import java.io.Serializable;

import controllers.MainMenu;
import staticClasses.TextReader;
import staticClasses.UByte;

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
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerWeapon * gameIndex + header;

	power = UByte.u2Byte(bytes[offset], bytes[offset + 1]);
	cost = UByte.u2Byte(bytes[offset + 2], bytes[offset + 3]);
	equipCode = UByte.u1Byte(bytes[offset + 4]);
	namePointer = UByte.u3Byte(bytes[offset + 5], bytes[offset + 6], bytes[offset + 7]);
	name = TextReader.readText(namePointer, bytes);
	discount = UByte.u2Byte(bytes[offset + 8], bytes[offset + 9]);
    }

    public void writeValuesToROM()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerWeapon * gameIndex + header;

	bytes[offset] = (byte) power;
	bytes[offset + 1] = UByte.intToByte2(power);

	bytes[offset + 2] = (byte) cost;
	bytes[offset + 3] = UByte.intToByte2(cost);

	bytes[offset + 4] = (byte) equipCode;

	bytes[offset + 8] = (byte) discount;
	bytes[offset + 9] = UByte.intToByte2(discount);
    }

    public Weapon(Weapon w, int listIndex)
    {
	super(w, listIndex);
    }

}
