package application.models;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import application.controllers.MainMenu;
import application.staticClasses.TextReader;
import application.staticClasses.UByte;

public class Armor extends Equipment implements Serializable
{
    private int		       laserRes;
    private int		       unknownRes1;
    private int		       unknownRes2;
    private int		       fireRes;
    private int		       iceRes;
    private int		       vacuumRes;
    private int		       debuffRes;

    private static final int   baseOffset    = 0x659B;
    private static final int   bytesPerArmor = 17;

    private static List<Armor> models;
    private static List<Armor> Dmodels;

    public Armor(int index)
    {
	super(index);
	itemCode = index + 0x97;
    }

    public void getValuesFromROM()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerArmor * gameIndex + header;

	power = UByte.u2Byte(bytes[offset], bytes[offset + 1]);
	cost = UByte.u2Byte(bytes[offset + 2], bytes[offset + 3]);
	equipCode = UByte.u1Byte(bytes[offset + 4]);
	laserRes = UByte.u1Byte(bytes[offset + 5]);
	unknownRes1 = UByte.u1Byte(bytes[offset + 6]);
	unknownRes2 = UByte.u1Byte(bytes[offset + 7]);
	fireRes = UByte.u1Byte(bytes[offset + 8]);
	iceRes = UByte.u1Byte(bytes[offset + 9]);
	vacuumRes = UByte.u1Byte(bytes[offset + 10]);
	debuffRes = UByte.u1Byte(bytes[offset + 11]);
	namePointer = UByte.u3Byte(bytes[offset + 12], bytes[offset + 13], bytes[offset + 14]);
	name = TextReader.readText(namePointer, bytes);
	discount = UByte.u2Byte(bytes[offset + 15], bytes[offset + 16]);

	if (power == 0 && laserRes == 0 && fireRes == 0 && iceRes == 0 && cost == 0)
	{
	    equipCode = 0;
	}
    }

    public void writeValuesToROM()
    {
	byte[] bytes = MainMenu.getBytes();
	int header = MainMenu.getHeader();
	int offset = baseOffset + bytesPerArmor * gameIndex + header;

	bytes[offset] = (byte) power;
	bytes[offset + 1] = UByte.intToByte2(power);

	bytes[offset + 2] = (byte) cost;
	bytes[offset + 3] = UByte.intToByte2(cost);

	bytes[offset + 4] = (byte) equipCode;
	bytes[offset + 5] = (byte) laserRes;
	bytes[offset + 6] = (byte) unknownRes1;
	bytes[offset + 7] = (byte) unknownRes2;
	bytes[offset + 8] = (byte) fireRes;
	bytes[offset + 9] = (byte) iceRes;
	bytes[offset + 10] = (byte) vacuumRes;
	bytes[offset + 11] = (byte) debuffRes;
	bytes[offset + 15] = (byte) discount;
	bytes[offset + 16] = UByte.intToByte2(discount);
    }

    public boolean isBodyArmor()
    {
	return this.getItemCode() < 0xB5;
    }

    public boolean isAccessory()
    {
	return this.getItemCode() >= 0xB5;
    }

    public boolean isCoat()
    {
	return isBodyArmor() && equipCode == 0x10;
    }

    public int getLaserRes()
    {
	return laserRes;
    }

    public int getUnknownRes1()
    {
	return unknownRes1;
    }

    public int getUnknownRes2()
    {
	return unknownRes2;
    }

    public int getFireRes()
    {
	return fireRes;
    }

    public int getIceRes()
    {
	return iceRes;
    }

    public int getVacuumRes()
    {
	return vacuumRes;
    }

    public int getDebuffRes()
    {
	return debuffRes;
    }

    public static List<Armor> getModels()
    {
	return models;
    }

    public void setLaserRes(int laserRes)
    {
	this.laserRes = laserRes;
    }

    public void setUnknownRes1(int unknownRes1)
    {
	this.unknownRes1 = unknownRes1;
    }

    public void setUnknownRes2(int unknownRes2)
    {
	this.unknownRes2 = unknownRes2;
    }

    public void setFireRes(int fireRes)
    {
	this.fireRes = fireRes;
    }

    public void setIceRes(int iceRes)
    {
	this.iceRes = iceRes;
    }

    public void setVacuumRes(int vacuumRes)
    {
	this.vacuumRes = vacuumRes;
    }

    public void setDebuffRes(int debuffRes)
    {
	this.debuffRes = debuffRes;
    }

    public static List<Armor> getDModels()
    {
	return Dmodels;
    }

    public static void setDModels(List<Armor> defaultArmors)
    {
	Armor.Dmodels = defaultArmors;
    }

    public Armor(Armor a, int listIndex)
    {
	super(a, listIndex);
	this.laserRes = a.laserRes;
	this.unknownRes1 = a.unknownRes1;
	this.unknownRes2 = a.unknownRes2;
	this.fireRes = a.fireRes;
	this.iceRes = a.iceRes;
	this.vacuumRes = a.vacuumRes;
	this.debuffRes = a.debuffRes;
    }

    public static final Comparator<Armor> descendingTypeComparator = new Comparator<Armor>()
							       {
								   @Override
								   public int compare(Armor e1, Armor e2)
								   {
								       if (e2.getGameIndex() == 0)
								       {
									   return -1;
								       }
								       else if (e1.getGameIndex() == 0)
								       {
									   return 1;
								       }
								       else if (e2.isAccessory() && e1.isBodyArmor())
								       {
									   return -1;
								       }
								       else if (e1.isAccessory() && e2.isBodyArmor())
								       {
									   return 1;
								       }
								       else
									   return e2.getPower() - e1.getPower();
								   }
							       };

    public static final Comparator<Armor> ascendingTypeComparator  = new Comparator<Armor>()
							       {
								   @Override
								   public int compare(Armor e1, Armor e2)
								   {
								       if (e2.getGameIndex() == 0)
								       {
									   return -1;
								       }
								       else if (e1.getGameIndex() == 0)
								       {
									   return 1;
								       }
								       else if (e2.isAccessory() && e1.isBodyArmor())
								       {
									   return -1;
								       }
								       else if (e1.isAccessory() && e2.isBodyArmor())
								       {
									   return 1;
								       }
								       else
									   return e1.getPower() - e2.getPower();
								   }
							       };
}
