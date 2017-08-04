package application.staticClasses

import application.ROM

object TextReader
{

	fun readText(pointer: Int): String
	{
		var pointer = pointer
		pointer = pointer and 0x0FFFFF

		val text = StringBuilder()

		var readNextByte = true
		while (readNextByte)
		{
			val charValue = ROM.getByte(pointer)

			when (charValue)
			{
				0    -> text.append('0')
				1    -> text.append('1')
				2    -> text.append('2')
				3    -> text.append('3')
				4    -> text.append('4')
				5    -> text.append('5')
				6    -> text.append('6')
				7    -> text.append('7')
				8    -> text.append('8')
				9    -> text.append('9')
				0x0D -> text.append(' ')
				0x20 -> text.append('A')
				0x21 -> text.append('B')
				0x22 -> text.append('C')
				0x23 -> text.append('D')
				0x24 -> text.append('E')
				0x25 -> text.append('F')
				0x26 -> text.append('G')
				0x27 -> text.append('H')
				0x28 -> text.append('I')
				0x29 -> text.append('J')
				0x2A -> text.append('K')
				0x2B -> text.append('L')
				0x2C -> text.append('M')
				0x2D -> text.append('N')
				0x2E -> text.append('O')
				0x2F -> text.append('P')
				0x30 -> text.append('Q')
				0x31 -> text.append('R')
				0x32 -> text.append('S')
				0x33 -> text.append('T')
				0x34 -> text.append('U')
				0x35 -> text.append('V')
				0x36 -> text.append('W')
				0x37 -> text.append('X')
				0x38 -> text.append('Y')
				0x39 -> text.append('Z')
				0x3A -> text.append('a')
				0x3B -> text.append('b')
				0x3C -> text.append('c')
				0x3D -> text.append('d')
				0x3E -> text.append('e')
				0x3F -> text.append('f')
				0x40 -> text.append('g')
				0x41 -> text.append('h')
				0x42 -> text.append('i')
				0x43 -> text.append('j')
				0x44 -> text.append('k')
				0x45 -> text.append('l')
				0x46 -> text.append('m')
				0x47 -> text.append('n')
				0x48 -> text.append('o')
				0x49 -> text.append('p')
				0x4A -> text.append('q')
				0x4B -> text.append('r')
				0x4C -> text.append('s')
				0x4D -> text.append('t')
				0x4E -> text.append('u')
				0x4F -> text.append('v')
				0x50 -> text.append('w')
				0x51 -> text.append('x')
				0x52 -> text.append('y')
				0x53 -> text.append('z')
				0x56 -> text.append('?')
				0x57 -> text.append('1')
				0x58 -> text.append('2')
				0x59 -> text.append('3')
				0x5A -> text.append(':')
				0x5B -> text.append(';')
				0x66 -> text.append("'")
				0x67 -> text.append('"')
				0x68 -> text.append('-')
				0x69 -> text.append(',')
				0x6A -> text.append('.')
				0x6B -> text.append("HT")
				0x6C -> text.append("SB")
				0x6D -> text.append("CR")
				0x6E -> text.append("MK")
				0x6F -> text.append("HA")
				0x70 -> text.append("AX")
				0x71 -> text.append("SW")
				0x72 -> text.append("KN")
				0x73 -> text.append("ST")
				0x74 -> text.append("AR")
				0x75 -> text.append("SH")
				0x76 -> text.append("CK")
				0x7A -> text.append("AM")
				0x7D -> text.append("RD")
				0x7E -> text.append("ML")
				0x7F -> text.append("RB")
			// case 0x82: text.append("bold"); break;
				0x85 -> text.append('!')
				0xC9 -> text.append("  ")
				0xCA -> text.append("er")
				0xCB -> text.append("ar")
				0xCC -> text.append("an")
				0xCD -> text.append("be")
				0xCE -> text.append("re")
				0xCF -> text.append("de")
				0xD0 -> text.append("me")
				0xD1 -> text.append("is")
				0xD2 -> text.append("if ")
				0xD3 -> text.append("ll")
				0xD4 -> text.append("se")
				0xD5 -> text.append("es")
				0xD6 -> text.append("n't")
				0xD7 -> text.append("you ")
				0xD8 -> text.append("the")
				0xD9 -> text.append("The ")
				0xDA -> text.append("it ")
				0xDB -> text.append("It ")
				0xDC -> text.append("to ")
				0xDD -> text.append("We ")
				0xDE -> text.append("we")
				0xDF -> text.append("ty")
				0xE0 -> text.append("I'm ")
				0xE1 -> text.append("have")
				0xE2 -> text.append("ble")
				0xE3 -> text.append("do")
				0xE4 -> text.append("my")
				0xE5 -> text.append("oo")
				0xE6 -> text.append("st")
				0xE7 -> text.append("ed")
				0xE8 -> text.append("on")
				0xE9 -> text.append("fa")
				0xEA -> text.append("y ")
				0xEB -> text.append("d ")
				0xEC -> text.append("n ")
				0xED -> text.append("wh")
				0xEE -> text.append("in")
			// case 0xEF: text.append(" bold"); break;
				0xF7 -> readNextByte = false
				0xF9 -> text.append("\n")
			// case 0xFA: text.append("\n\n"); break;
				0xFA -> text.append("\n")
			}
			pointer++

			if (text.length > 9000) readNextByte = false
		}
		if (text.isNotEmpty()) return text.toString()
		else if (text.length > 1000) return "too long"
		else return ""
	}

	fun pointerToText(pointer: Int): String
	{
		val sb = StringBuilder()
		val lastByte = Integer.toHexString(pointer shr 16).toUpperCase()
		val midByte = Integer.toHexString(pointer shr 8 and 0x00FF).toUpperCase()
		val firstByte = Integer.toHexString(pointer and 0xFF).toUpperCase()

		appendByteToStringBuilder(sb, firstByte)
		appendByteToStringBuilder(sb, midByte)
		appendByteToStringBuilder(sb, lastByte)
		return sb.toString()
	}

	fun appendByteToStringBuilder(sb: StringBuilder, stringByte: String)
	{
		if (stringByte.length < 2) sb.append("0")
		sb.append(stringByte + " ")
	}
}
