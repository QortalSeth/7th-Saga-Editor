package application.models

import application.ROM
import application.staticClasses.TextReader
import java.io.Serializable
import java.util.*

class Armor : Equipment, Serializable
{
	var laserRes: Int = 0
	var unknownRes1: Int = 0
	var unknownRes2: Int = 0
	var fireRes: Int = 0
	var iceRes: Int = 0
	var vacuumRes: Int = 0
	var debuffRes: Int = 0

	constructor(index: Int) : super(index)
	{
		itemCode = index + 0x97
	}


	fun getValuesFromROM()
	{

		ROM.offset = baseOffset + bytesPerArmor * gameIndex
		offset = ROM.offset
		power = ROM.nextShort
		cost = ROM.nextShort
		equipCode = ROM.nextByte
		laserRes = ROM.nextByte
		unknownRes1 = ROM.nextByte
		unknownRes2 = ROM.nextByte
		fireRes = ROM.nextByte
		iceRes = ROM.nextByte
		vacuumRes = ROM.nextByte
		debuffRes = ROM.nextByte
		namePointer = ROM.nextTriple
		name = TextReader.readText(namePointer)
		discount = ROM.nextShort

		if (power == 0 && laserRes == 0 && fireRes == 0 && iceRes == 0 && cost == 0) equipCode = 0
	}

	fun writeValuesToROM()
	{
		ROM.offset = baseOffset + bytesPerArmor * gameIndex
		ROM.nextShort = power
		ROM.nextShort = cost
		ROM.nextByte = equipCode
		ROM.nextByte = laserRes
		ROM.nextByte = unknownRes1
		ROM.nextByte = unknownRes2
		ROM.nextByte = fireRes
		ROM.nextByte = iceRes
		ROM.nextByte = vacuumRes
		ROM.nextByte = debuffRes
		ROM.nextTriple = namePointer
		ROM.nextShort = discount
	}

	val isBodyArmor: Boolean
		get() = this.itemCode < 0xB5

	val isAccessory: Boolean
		get() = this.itemCode >= 0xB5

	val isCoat: Boolean
		get() = isBodyArmor && equipCode == 0x10

	constructor(a: Armor) : super(a)
	{
		laserRes = a.laserRes
		unknownRes1 = a.unknownRes1
		unknownRes2 = a.unknownRes2
		fireRes = a.fireRes
		iceRes = a.iceRes
		vacuumRes = a.vacuumRes
		debuffRes = a.debuffRes
	}

	companion object
	{

		private val baseOffset = 0x659B
		private val bytesPerArmor = 17

		val descendingTypeComparator: Comparator<Armor> = Comparator { e1, e2 ->
			when
			{
				e2.gameIndex == 0                -> -1
				e1.gameIndex == 0                -> 1
				e2.isAccessory && e1.isBodyArmor -> -1
				e1.isAccessory && e2.isBodyArmor -> 1
				else                             -> e2.power - e1.power
			}
		}

		val ascendingTypeComparator: Comparator<Armor> = Comparator { e1, e2 ->
			when
			{
				e2.gameIndex == 0                -> -1
				e1.gameIndex == 0                -> 1
				e2.isAccessory && e1.isBodyArmor -> -1
				e1.isAccessory && e2.isBodyArmor -> 1
				else                             -> e1.power - e2.power
			}
		}
	}
}
