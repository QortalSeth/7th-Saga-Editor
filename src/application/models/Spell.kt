package application.models

import application.ROM
import application.staticClasses.TextReader
import java.io.Serializable

class Spell : Model, Serializable
{
	constructor(index: Int) : super(index)

	var power: Int = 0
	var target: Int = 0
	var cost: Int = 0
	var domain: Int = 0
	var element: Int = 0
	var unknown1: Int = 0
	var unknown2: Int = 0

	fun getValuesFromROM()
	{
		ROM.offset = baseOffset + bytesPerSpell * gameIndex

		power = ROM.nextShort
		target = ROM.nextByte
		cost = ROM.nextShort
		domain = ROM.nextByte
		element = ROM.nextByte
		unknown1 = ROM.nextByte
		unknown2 = ROM.nextByte
		namePointer = ROM.nextTriple
		name = TextReader.readText(namePointer)
	}

	fun writeValuesToROM()
	{
		ROM.offset = baseOffset + bytesPerSpell * gameIndex

		ROM.nextShort = power
		ROM.nextByte = target
		ROM.nextShort = cost
		ROM.nextByte = domain
		ROM.nextByte = element
		ROM.nextByte = unknown1
		ROM.nextByte = unknown2
		ROM.nextTriple = namePointer
	}

	val elementPriority: Int
		get()
		{
			var priority = 10
			when (element)
			{
				0    -> priority = 5
				1    -> priority = 2
				2    -> priority = 2
				3    -> priority = 2
				4    -> priority = 0
				5    -> priority = 1
				6    -> priority = 3
				7    -> priority = 4
				else -> println("Error, priority is: $priority element is: $element")
			}
			return priority
		}

	constructor(s: Spell) : super(s)
	{
		power = s.power
		target = s.target
		cost = s.cost
		domain = s.domain
		element = s.element
		unknown1 = s.unknown1
		unknown2 = s.unknown2
	}

	companion object
	{
		private val baseOffset = 0x7018
		private val bytesPerSpell = 12
		val elements = arrayOf("None", "Lightning", "Unknown", "Unknown", "Fire", "Ice", "Vacuum", "Debuff")
	}

}
