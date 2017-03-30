package staticClasses;

public class TextReader
{

    public static String readText(int pointer, byte[] bytes)
    {
	pointer &= 0x0FFFFF;

	StringBuilder text = new StringBuilder();

	boolean readNextByte = true;
	while (readNextByte)
	{
	    int charValue = UByte.u1Byte(bytes[pointer]);

	    switch (charValue)
	    {
		case 0:
		    text.append('0');
		    break;
		case 1:
		    text.append('1');
		    break;
		case 2:
		    text.append('2');
		    break;
		case 3:
		    text.append('3');
		    break;
		case 4:
		    text.append('4');
		    break;
		case 5:
		    text.append('5');
		    break;
		case 6:
		    text.append('6');
		    break;
		case 7:
		    text.append('7');
		    break;
		case 8:
		    text.append('8');
		    break;
		case 9:
		    text.append('9');
		    break;
		case 0x0D:
		    text.append(' ');
		    break;
		case 0x20:
		    text.append('A');
		    break;
		case 0x21:
		    text.append('B');
		    break;
		case 0x22:
		    text.append('C');
		    break;
		case 0x23:
		    text.append('D');
		    break;
		case 0x24:
		    text.append('E');
		    break;
		case 0x25:
		    text.append('F');
		    break;
		case 0x26:
		    text.append('G');
		    break;
		case 0x27:
		    text.append('H');
		    break;
		case 0x28:
		    text.append('I');
		    break;
		case 0x29:
		    text.append('J');
		    break;
		case 0x2A:
		    text.append('K');
		    break;
		case 0x2B:
		    text.append('L');
		    break;
		case 0x2C:
		    text.append('M');
		    break;
		case 0x2D:
		    text.append('N');
		    break;
		case 0x2E:
		    text.append('O');
		    break;
		case 0x2F:
		    text.append('P');
		    break;
		case 0x30:
		    text.append('Q');
		    break;
		case 0x31:
		    text.append('R');
		    break;
		case 0x32:
		    text.append('S');
		    break;
		case 0x33:
		    text.append('T');
		    break;
		case 0x34:
		    text.append('U');
		    break;
		case 0x35:
		    text.append('V');
		    break;
		case 0x36:
		    text.append('W');
		    break;
		case 0x37:
		    text.append('X');
		    break;
		case 0x38:
		    text.append('Y');
		    break;
		case 0x39:
		    text.append('Z');
		    break;
		case 0x3A:
		    text.append('a');
		    break;
		case 0x3B:
		    text.append('b');
		    break;
		case 0x3C:
		    text.append('c');
		    break;
		case 0x3D:
		    text.append('d');
		    break;
		case 0x3E:
		    text.append('e');
		    break;
		case 0x3F:
		    text.append('f');
		    break;
		case 0x40:
		    text.append('g');
		    break;
		case 0x41:
		    text.append('h');
		    break;
		case 0x42:
		    text.append('i');
		    break;
		case 0x43:
		    text.append('j');
		    break;
		case 0x44:
		    text.append('k');
		    break;
		case 0x45:
		    text.append('l');
		    break;
		case 0x46:
		    text.append('m');
		    break;
		case 0x47:
		    text.append('n');
		    break;
		case 0x48:
		    text.append('o');
		    break;
		case 0x49:
		    text.append('p');
		    break;
		case 0x4A:
		    text.append('q');
		    break;
		case 0x4B:
		    text.append('r');
		    break;
		case 0x4C:
		    text.append('s');
		    break;
		case 0x4D:
		    text.append('t');
		    break;
		case 0x4E:
		    text.append('u');
		    break;
		case 0x4F:
		    text.append('v');
		    break;
		case 0x50:
		    text.append('w');
		    break;
		case 0x51:
		    text.append('x');
		    break;
		case 0x52:
		    text.append('y');
		    break;
		case 0x53:
		    text.append('z');
		    break;
		case 0x56:
		    text.append('?');
		    break;
		case 0x57:
		    text.append('1');
		    break;
		case 0x58:
		    text.append('2');
		    break;
		case 0x59:
		    text.append('3');
		    break;
		case 0x5A:
		    text.append(':');
		    break;
		case 0x5B:
		    text.append(';');
		    break;
		case 0x66:
		    text.append("'");
		    break;
		case 0x67:
		    text.append('"');
		    break;
		case 0x68:
		    text.append('-');
		    break;
		case 0x69:
		    text.append(',');
		    break;
		case 0x6A:
		    text.append('.');
		    break;
		case 0x6B:
		    text.append("HT");
		    break;
		case 0x6C:
		    text.append("SB");
		    break;
		case 0x6D:
		    text.append("CR");
		    break;
		case 0x6E:
		    text.append("MK");
		    break;
		case 0x6F:
		    text.append("HA");
		    break;
		case 0x70:
		    text.append("AX");
		    break;
		case 0x71:
		    text.append("SW");
		    break;
		case 0x72:
		    text.append("KN");
		    break;
		case 0x73:
		    text.append("ST");
		    break;
		case 0x74:
		    text.append("AR");
		    break;
		case 0x75:
		    text.append("SH");
		    break;
		case 0x76:
		    text.append("CK");
		    break;
		case 0x7A:
		    text.append("AM");
		    break;
		case 0x7D:
		    text.append("RD");
		    break;
		case 0x7E:
		    text.append("ML");
		    break;
		case 0x7F:
		    text.append("RB");
		    break;
		// case 0x82: text.append("bold"); break;
		case 0x85:
		    text.append('!');
		    break;
		case 0xC9:
		    text.append("  ");
		    break;
		case 0xCA:
		    text.append("er");
		    break;
		case 0xCB:
		    text.append("ar");
		    break;
		case 0xCC:
		    text.append("an");
		    break;
		case 0xCD:
		    text.append("be");
		    break;
		case 0xCE:
		    text.append("re");
		    break;
		case 0xCF:
		    text.append("de");
		    break;
		case 0xD0:
		    text.append("me");
		    break;
		case 0xD1:
		    text.append("is");
		    break;
		case 0xD2:
		    text.append("if ");
		    break;
		case 0xD3:
		    text.append("ll");
		    break;
		case 0xD4:
		    text.append("se");
		    break;
		case 0xD5:
		    text.append("es");
		    break;
		case 0xD6:
		    text.append("n't");
		    break;
		case 0xD7:
		    text.append("you ");
		    break;
		case 0xD8:
		    text.append("the");
		    break;
		case 0xD9:
		    text.append("The ");
		    break;
		case 0xDA:
		    text.append("it ");
		    break;
		case 0xDB:
		    text.append("It ");
		    break;
		case 0xDC:
		    text.append("to ");
		    break;
		case 0xDD:
		    text.append("We ");
		    break;
		case 0xDE:
		    text.append("we");
		    break;
		case 0xDF:
		    text.append("ty");
		    break;
		case 0xE0:
		    text.append("I'm ");
		    break;
		case 0xE1:
		    text.append("have");
		    break;
		case 0xE2:
		    text.append("ble");
		    break;
		case 0xE3:
		    text.append("do");
		    break;
		case 0xE4:
		    text.append("my");
		    break;
		case 0xE5:
		    text.append("oo");
		    break;
		case 0xE6:
		    text.append("st");
		    break;
		case 0xE7:
		    text.append("ed");
		    break;
		case 0xE8:
		    text.append("on");
		    break;
		case 0xE9:
		    text.append("fa");
		    break;
		case 0xEA:
		    text.append("y ");
		    break;
		case 0xEB:
		    text.append("d ");
		    break;
		case 0xEC:
		    text.append("n ");
		    break;
		case 0xED:
		    text.append("wh");
		    break;
		case 0xEE:
		    text.append("in");
		    break;
		// case 0xEF: text.append(" bold"); break;
		case 0xF7:
		    readNextByte = false;
		    break;
		case 0xF9:
		    text.append("\n");
		    break;
		// case 0xFA: text.append("\n\n"); break;
		case 0xFA:
		    text.append("\n");
		    break;
	    }
	    pointer++;

	    if (text.length() > 9000) readNextByte = false;
	}
	if (text.length() > 0)
	{
	    return text.toString();
	}
	else if (text.length() > 100)
	{
	    return "too long";
	}
	else
	    return "";
    }

    public static String pointerToText(int pointer)
    {
	StringBuilder sb = new StringBuilder();
	String lastByte = Integer.toHexString(pointer >> 16).toUpperCase();
	String midByte = Integer.toHexString(((pointer >> 8) & 0x00FF)).toUpperCase();
	String firstByte = Integer.toHexString(pointer & 0xFF).toUpperCase();

	appendByteToStringBuilder(sb, firstByte);
	appendByteToStringBuilder(sb, midByte);
	appendByteToStringBuilder(sb, lastByte);
	//
	// if(firstByte.length()<2)
	// {sb.append("0");}
	// sb.append(firstByte+" ");
	//
	// if(midByte.length()<2)
	// {sb.append("0");}
	// sb.append(midByte+" ");
	//
	// if(lastByte.length()<2)
	// {sb.append("0");}
	// sb.append(lastByte);

	return sb.toString();
    }

    public static void appendByteToStringBuilder(StringBuilder sb, String stringByte)
    {
	if (stringByte.length() < 2)
	{
	    sb.append("0");
	}
	sb.append(stringByte + " ");
    }

}
