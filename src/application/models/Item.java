package application.models;

import java.io.Serializable;

import application.controllers.MainMenu;
import application.staticClasses.TextReader;
import application.staticClasses.UByte;

public class Item extends AbstractItem implements Serializable
{
    private int		     target;
    private int		     uses;
    private int		     users;
    private int		     sellRatio;

    private static final int baseOffset	  = 0x6C94;
    private static final int bytesPerItem = 9;

    public Item(int index)
    {
	super(index);
	itemCode = index;
	setChronologicalIndex();
    }

    public void getValuesFromROM()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerItem * gameIndex + header;

	target = UByte.u1Byte(bytes[offset]);
	uses = UByte.u1Byte(bytes[offset + 1]);
	cost = UByte.u2Byte(bytes[offset + 2], bytes[offset + 3]);
	users = UByte.u1Byte(bytes[offset + 4]);
	sellRatio = UByte.u1Byte(bytes[offset + 5]);
	namePointer = UByte.u3Byte(bytes[offset + 6], bytes[offset + 7], bytes[offset + 8]);
	name = TextReader.readText(namePointer, bytes);
    }

    public void writeValuesToROM()
    {
	int header = MainMenu.getHeader();
	byte[] bytes = MainMenu.getBytes();
	int offset = baseOffset + bytesPerItem * gameIndex + header;

	bytes[offset] = (byte) target;
	bytes[offset + 1] = (byte) uses;
	bytes[offset + 2] = (byte) cost;
	bytes[offset + 3] = UByte.intToByte2(cost);
	bytes[offset + 4] = (byte) users;
	bytes[offset + 5] = (byte) sellRatio;
    }

    private void setChronologicalIndex()
    {
	switch (itemCode) {
	    case 0:
		chronologicalIndex = 0;
		break;
	    case 0x0B:
		chronologicalIndex = 1;
		break;
	    case 0x0C:
		chronologicalIndex = 2;
		break;
	    case 0x0D:
		chronologicalIndex = 3;
		break;
	    case 0x15:
		chronologicalIndex = 4;
		break;
	    case 0x11:
		chronologicalIndex = 5;
		break;
	    case 0x12:
		chronologicalIndex = 6;
		break;
	    case 0x13:
		chronologicalIndex = 7;
		break;
	    case 0x14:
		chronologicalIndex = 8;
		break;
	    case 0x01:
		chronologicalIndex = 9;
		break;
	    case 0x02:
		chronologicalIndex = 10;
		break;
	    case 0x38:
		chronologicalIndex = 11;
		break;
	    case 0x29:
		chronologicalIndex = 12;
		break;
	    case 0x35:
		chronologicalIndex = 13;
		break;
	    case 0x30:
		chronologicalIndex = 14;
		break;
	    case 0x32:
		chronologicalIndex = 15;
		break;
	    case 0x34:
		chronologicalIndex = 16;
		break;
	    case 0x2D:
		chronologicalIndex = 17;
		break;
	    case 0x2E:
		chronologicalIndex = 18;
		break;
	    case 0x39:
		chronologicalIndex = 19;
		break;
	    case 0x3A:
		chronologicalIndex = 20;
		break;
	    case 0x43:
		chronologicalIndex = 21;
		break;
	    default:
		chronologicalIndex = itemCode + 30;
	}
    }
    // healing
    // buff
    // attack
    // etc

    // 00=NOTHING => 255
    // 01=EXIGATE => 8
    // 02=WINBALL => 9
    // 03=MAP
    // 04=WATR RN
    // 05=WIND RN
    // 06=STAR RN
    // 07=MOON RN
    // 08=LGHT RN
    // 09=SKY RN
    // 0A=WZRD RN
    // 0B=POTN 1 => 0
    // 0C=POTN 2 => 1
    // 0D=POTN 3 => 2
    // 11=M. HERB 1 => 4
    // 12=M. HERB 2 => 5
    // 13=ANTID => 6
    // 14=M. WATER => 7
    // 15=RECVRY => 3
    // 16=V. SEED => 150
    // 17=M. SEED => 151
    // 18=P. SEED => 152
    // 19=PR. SEED => 153
    // 1A=I. SEED => 154
    // 1B=A. SEED => 155
    // 29=B. POWER => 11
    // 2D=MSQUITO => 16
    // 2E=M SIPHN => 17
    // 30=B. FIRE => 13
    // 32=B. ICE => 14
    // 34=B. FOSSL => 15
    // 35=B. AGLTY => 12
    // 38=B. PRTCT => 10
    // 39=S DSTRY => 18
    // 3A=VACUUM => 19
    // 43=MIRROR => 20
    // 44=HARP => 21
    // 47=OPAL
    // 48=PEARL
    // 49=TOPAZ
    // 4A=RUBY
    // 4B=SAPHR
    // 4C=EMRLD
    // 4D=DMND
    // 51=WHSTLE
    // 52=MN LIGHT
    // 53=KEY BRI
    // 54=RMTCNTL
    // 55=JL KEY
    // 56=LETTER
    // 5A=KY ERTH
    // 5B=CRY PCE
    // 5C=STAR
    // 5D=WATR RN
    // 5E=WIND RN
    // 5F=STAR RN
    // 60=MOON RN
    // 61=LGHT RN
    // 62=SKY RN
    // 63=WZRD RN

    public int getTarget()
    {
	return target;
    }

    public void setTarget(int target)
    {
	this.target = target;
    }

    public int getUses()
    {
	return uses;
    }

    public void setUses(int uses)
    {
	this.uses = uses;
    }

    public int getUsers()
    {
	return users;
    }

    public void setUsers(int users)
    {
	this.users = users;
    }

    public int getSellRatio()
    {
	return sellRatio;
    }

    public void setSellRatio(int sellRatio)
    {
	this.sellRatio = sellRatio;
    }

    public Item(Item i, int listIndex)
    {
	super(i, listIndex);
	target = i.target;
	uses = i.uses;
	users = i.users;
	sellRatio = i.sellRatio;
    }

}
