package application.models

import application.ROM
import application.staticClasses.TextReader
import java.io.Serializable

class Weapon : Equipment, Serializable
{
	constructor(index: Int) : super(index)
	{
		itemCode = index + 0x64
	}

	constructor(w: Weapon) : super(w)


	fun getValuesFromROM()
	{
		ROM.offset = baseOffset + bytesPerWeapon * gameIndex

		power = ROM.nextShort
		cost = ROM.nextShort
		equipCode = ROM.nextByte
		namePointer = ROM.nextTriple
		name = TextReader.readText(namePointer)
		discount = ROM.nextShort
	}

	fun writeValuesToROM()
	{
		ROM.offset = baseOffset + bytesPerWeapon * gameIndex

		ROM.nextShort = power
		ROM.nextShort = cost
		ROM.nextByte = equipCode
		ROM.nextTriple = namePointer
		ROM.nextShort = discount
	}

	companion object
	{
		private val baseOffset = 0x639D
		private val bytesPerWeapon = 10
	}

}
