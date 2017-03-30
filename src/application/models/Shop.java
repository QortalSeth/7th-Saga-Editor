package application.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import application.controllers.MainMenu;
import application.models.lists.Armors;
import application.models.lists.Lists;
import application.models.lists.Weapons;
import application.staticClasses.TextReader;
import application.staticClasses.UByte;

public class Shop extends Model implements Serializable
{

    List<Integer>	     weaponCodes  = new ArrayList<Integer>();
    List<Integer>	     armorCodes	  = new ArrayList<Integer>();
    List<Integer>	     itemCodes	  = new ArrayList<Integer>();
    private int		     innCost;
    private static final int baseOffset	  = 0x8308;
    private static final int bytesPerShop = 27;

    public Shop(int index)
    {
	super(index);
	this.setChronologicalIndex();
    }

    public void getValuesFromROM()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerShop * gameIndex + header;

	for (int i = 0; i < 5; i++)
	{
	    weaponCodes.add(UByte.u1Byte(bytes[offset]));
	    offset++;
	}

	for (int i = 0; i < 8; i++)
	{
	    armorCodes.add(UByte.u1Byte(bytes[offset]));
	    offset++;
	}

	for (int i = 0; i < 9; i++)
	{
	    itemCodes.add(UByte.u1Byte(bytes[offset]));
	    offset++;
	}

	innCost = UByte.u2Byte(bytes[offset], bytes[offset + 1]);
	namePointer = UByte.u3Byte(bytes[offset + 2], bytes[offset + 3], bytes[offset + 4]);
	name = TextReader.readText(namePointer, bytes);
    }

    public void writeValuesToROM()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerShop * gameIndex + header;

	for (int i = 0; i < 5; i++)
	{
	    bytes[offset] = (byte) weaponCodes.get(i).intValue();
	    offset++;
	}

	for (int i = 0; i < 8; i++)
	{
	    bytes[offset] = (byte) armorCodes.get(i).intValue();
	    offset++;
	}

	for (int i = 0; i < 9; i++)
	{
	    bytes[offset] = (byte) itemCodes.get(i).intValue();
	    offset++;
	}

	bytes[offset] = (byte) innCost;
	bytes[offset + 1] = UByte.intToByte2(innCost);
    }

    private void setChronologicalIndex()
    {
	switch (gameIndex) {
	    case 0:
		chronologicalIndex = 0;
		break;
	    case 1:
		chronologicalIndex = 1;
		break;
	    case 2:
		chronologicalIndex = 2;
		break;
	    case 3:
		chronologicalIndex = 3;
		break;
	    case 4:
		chronologicalIndex = 4;
		break;
	    case 5:
		chronologicalIndex = 5;
		break;
	    case 6:
		chronologicalIndex = 6;
		break;
	    case 7:
		chronologicalIndex = 7;
		break;
	    case 8:
		chronologicalIndex = 8;
		break;
	    case 9:
		chronologicalIndex = 9;
		break;
	    case 10:
		chronologicalIndex = 10;
		break;
	    case 11:
		chronologicalIndex = 11;
		break;
	    case 12:
		chronologicalIndex = 12;
		break;
	    case 13:
		chronologicalIndex = 13;
		break;
	    case 14:
		chronologicalIndex = 15;
		break;
	    case 15:
		chronologicalIndex = 14;
		break;
	    case 16:
		chronologicalIndex = 19;
		break;
	    case 17:
		chronologicalIndex = 20;
		break;
	    case 18:
		chronologicalIndex = 18;
		break;
	    case 19:
		chronologicalIndex = 21;
		break;
	    case 20:
		chronologicalIndex = 16;
		break;
	    case 21:
		chronologicalIndex = 17;
		break;
	    case 22:
		chronologicalIndex = 22;
		break;
	    case 23:
		chronologicalIndex = 23;
		break;
	    // case 24: chronologicalIndex=; break;
	    case 25:
		chronologicalIndex = 24;
		break;
	    case 26:
		chronologicalIndex = 25;
		break;
	    case 27:
		chronologicalIndex = 26;
		break;
	    case 28:
		chronologicalIndex = 27;
		break;
	    case 29:
		chronologicalIndex = 28;
		break;
	    case 30:
		chronologicalIndex = 32;
		break;
	    case 31:
		chronologicalIndex = 30;
		break;
	    case 32:
		chronologicalIndex = 29;
		break;
	    case 33:
		chronologicalIndex = 31;
		break;
	    case 34:
		chronologicalIndex = 33;
		break;
	    case 35:
		chronologicalIndex = 34;
		break;
	    case 36:
		chronologicalIndex = 35;
		break;
	    case 37:
		chronologicalIndex = 36;
		break;
	    // case 38: chronologicalIndex=; break;
	    // case 39: chronologicalIndex=; break;
	    default:
		chronologicalIndex = 90;
		break;
	}
    }
    /*
     * Town GameIndex ChronologicalIndex 
     * Lemele 0 0 
     * Rablesk 1 1 
     * Bonro 2 2 
     * Zellis 3 3 
     * Melenam 4 4 
     * Eygus 5 5 
     * Pell 6 6 
     * Guntz 7 7 
     * Patrof 8 8 
     * Bone 9 9
     * Dowaine 10 10 
     * Belaine 11 11 
     * Telaine 12 12 
     * Luze 13 13 
     * Padal 15 14 
     * Pang 14 15 
     * Pandam 20 16 
     * Brush 21 17 
     * Tiffana 18 18 
     * Baran 16 19 
     * Polasu 17 20
     * Bilthem 19 21 
     * Valenca 22 22 
     * Bugask 23 23 
     * Guanta 25 24 
     * Gorfun 26 25 
     * Pharano 27 26 
     * Pasanda 28 27 
     * Ligena 29 28 
     * Melenam 32 29 
     * Palsu 31 30 
     * AIRSHIP 33 31 
     * Guanas 30 32 
     * Guanas 34 33 
     * Bijenia 35 34 
     * Bulando 36 35 
     * Luze 37 36
     */

    public void sortWeaponsAscending()
    {
	Weapons weapons = new Weapons(weaponCodes);
	weapons.sortByAscendingType();
	weaponCodes = weapons.toIntegers();
    }

    public void sortWeaponsDescending()
    {
	Weapons weapons = new Weapons(weaponCodes);
	weapons.sortByDescendingType();
	weaponCodes = weapons.toIntegers();
    }

    public void sortArmorsAscending()
    {
	Armors armors = new Armors(armorCodes);
	armors.sortByAscendingType();
	armorCodes = armors.toIntegers();
    }

    public void sortArmorsDescending()
    {
	Armors armors = new Armors(armorCodes);
	armors.sortByDescendingType();
	armorCodes = armors.toIntegers();
    }

    public void printList(List<Integer> list)
    {
	for (Integer i : list)
	{
	    System.out.println("Item Code is: " + Integer.toHexString(i) + " Name is: " + Lists.getAbstractItems().getName(i));
	}
	System.out.println();
    }

    public List<Integer> getWeaponCodes()
    {
	return weaponCodes;
    }

    public List<Integer> getArmorCodes()
    {
	return armorCodes;
    }

    public List<Integer> getItemCodes()
    {
	return itemCodes;
    }

    public int getInnCost()
    {
	return innCost;
    }

    public void setInnCost(int innCost)
    {
	this.innCost = innCost;
    }

    public void setWeaponCodes(List<Integer> weaponCodes)
    {
	this.weaponCodes = weaponCodes;
    }

    public void setArmorCodes(List<Integer> armorCodes)
    {
	this.armorCodes = armorCodes;
    }

    public void setItemCodes(List<Integer> itemCodes)
    {
	this.itemCodes = itemCodes;
    }

    public static List<Shop> copyShops(List<Shop> shops)
    {
	List<Shop> copy = new ArrayList<Shop>();
	for (Shop s : shops)
	{
	    copy.add(new Shop(s));
	}
	return copy;
    }

    public Shop(Shop s)
    {
	super(s);
	weaponCodes.addAll(s.getWeaponCodes());
	armorCodes.addAll(s.getArmorCodes());
	itemCodes.addAll(s.getItemCodes());
	innCost = s.getInnCost();
    }
}
