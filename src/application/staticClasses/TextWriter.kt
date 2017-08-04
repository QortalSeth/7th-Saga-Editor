package application.staticClasses

import java.util.*

object TextWriter
{

	@JvmStatic fun TextToBytes(text: String): ArrayList<Int>
	{
		val textBytes = ArrayList<Int>()


		//for((index, x) in text.withIndex())
		var index: Int = 0
		while (index < text.length)
		{
			var value = 0

			value = getByte4(text, index)
			if (value != 0xFF)
			{
				textBytes.add(value); index += 4; continue
			}

			value = getByte3(text, index)
			if (value != 0xFF)
			{
				textBytes.add(value); index += 3; continue
			}

			value = getByte2(text, index)
			if (value != 0xFF)
			{
				textBytes.add(value); index += 2; continue
			}

			value = getByte1(text, index)
			if (value != 0xFF)
			{
				textBytes.add(value); index++; continue
			}
			index++
		}
		return textBytes
	}

	fun getSubString(text: String, index: Int, length: Int): String
	{
		if (index + length > text.length) return ""
		else
		{
			println("text: $text start: $index end: ${index + length}")
			return text.substring(index, index + length)
		}
	}

	@JvmStatic fun getByte4(text: String, index: Int): Int
	{

		val subString = getSubString(text, index, 4)
		if (subString.isEmpty()) return 0xFF
		when (subString)
		{
			"you " -> return 0xD7
			"The " -> return 0xD9
			"I'm " -> return 0xE0
			"have" -> return 0xE1
			"/END" -> return 0xF7
		//"" -> return 0x
		}
		return 0xFF

	}

	@JvmStatic fun getByte3(text: String, index: Int): Int
	{
		val subString = getSubString(text, index, 3)
		if (subString.isEmpty()) return 0xFF
		when (subString)
		{
			"/HT" -> return 0x6B
			"/SB" -> return 0x6C
			"/CR" -> return 0x6D
			"/MK" -> return 0x6E
			"/HA" -> return 0x6F
			"/AX" -> return 0x70
			"/SW" -> return 0x71
			"/KN" -> return 0x72
			"/ST" -> return 0x73
			"/AR" -> return 0x74
			"/SH" -> return 0x75
			"/CK" -> return 0x76
			"/AM" -> return 0x7A
			"/RD" -> return 0x7D
			"/ML" -> return 0x7E
			"/RB" -> return 0x7F

			"if " -> return 0xD2
			"n't" -> return 0xD6
			"the" -> return 0xD8
			"it " -> return 0xDA
			"It " -> return 0xDB
			"to " -> return 0xDC
			"We " -> return 0xDD
			"ble" -> return 0xE2
			" /B" -> return 0xEF
			"/PD" -> return 0xFA
		}
		return 0xFF
	}

	@JvmStatic fun getByte2(text: String, index: Int): Int
	{
		val subString = getSubString(text, index, 2)
		if (subString.isEmpty()) return 0xFF
		when (subString)
		{
			"/1" -> return 0x57
			"/2" -> return 0x58
			"/3" -> return 0x59
			"/B" -> return 0x82
			"/G" -> return 0x8C
			"  " -> return 0xC9
			"er" -> return 0xCA
			"ar" -> return 0xCB
			"an" -> return 0xCC
			"be" -> return 0xCD
			"re" -> return 0xCE
			"de" -> return 0xCF
			"me" -> return 0xD0
			"is" -> return 0xD1
			"ll" -> return 0xD3
			"se" -> return 0xD4
			"es" -> return 0xD5
			"we" -> return 0xDE
			"ty" -> return 0xDF
			"do" -> return 0xE3
			"my" -> return 0xE4
			"oo" -> return 0xE5
			"st" -> return 0xE6
			"ed" -> return 0xE7
			"on" -> return 0xE8
			"fa" -> return 0xE9
			"y " -> return 0xEA
			"d " -> return 0xEB
			"n " -> return 0xEC
			"wh" -> return 0xED
			"in" -> return 0xEE
		}
		return 0xFF
	}

	@JvmStatic fun getByte1(text: String, index: Int): Int
	{
		when (text[index])
		{
			'0'  -> return 0x00
			'1'  -> return 0x01
			'2'  -> return 0x02
			'3'  -> return 0x03
			'4'  -> return 0x04
			'5'  -> return 0x05
			'6'  -> return 0x06
			'7'  -> return 0x07
			'8'  -> return 0x08
			'9'  -> return 0x09
			' '  -> return 0x0D
			'A'  -> return 0x20
			'B'  -> return 0x21
			'C'  -> return 0x22
			'D'  -> return 0x23
			'E'  -> return 0x24
			'F'  -> return 0x25
			'G'  -> return 0x26
			'H'  -> return 0x27
			'I'  -> return 0x28
			'J'  -> return 0x29
			'K'  -> return 0x2A
			'L'  -> return 0x2B
			'M'  -> return 0x2C
			'N'  -> return 0x2D
			'O'  -> return 0x2E
			'P'  -> return 0x2F
			'Q'  -> return 0x30
			'R'  -> return 0x31
			'S'  -> return 0x32
			'T'  -> return 0x33
			'U'  -> return 0x34
			'V'  -> return 0x35
			'W'  -> return 0x36
			'X'  -> return 0x37
			'Y'  -> return 0x38
			'Z'  -> return 0x39

			'a'  -> return 0x3A
			'b'  -> return 0x3B
			'c'  -> return 0x3C
			'd'  -> return 0x3D
			'e'  -> return 0x3E
			'f'  -> return 0x3F
			'g'  -> return 0x40
			'h'  -> return 0x41
			'i'  -> return 0x42
			'j'  -> return 0x43
			'k'  -> return 0x44
			'l'  -> return 0x45
			'm'  -> return 0x46
			'n'  -> return 0x47
			'o'  -> return 0x48
			'p'  -> return 0x49
			'q'  -> return 0x4A
			'r'  -> return 0x4B
			's'  -> return 0x4C
			't'  -> return 0x4D
			'u'  -> return 0x4E
			'v'  -> return 0x4F
			'w'  -> return 0x50
			'x'  -> return 0x51
			'y'  -> return 0x52
			'z'  -> return 0x53
			'?'  -> return 0x56
			':'  -> return 0x5A
			';'  -> return 0x5B
			'\'' -> return 0x66
			'\"' -> return 0x67
			'-'  -> return 0x68
			','  -> return 0x69
			'.'  -> return 0x6A
			'!'  -> return 0x85
			'\n' -> return 0xF9
		}
		return 0xFF
	}
}
