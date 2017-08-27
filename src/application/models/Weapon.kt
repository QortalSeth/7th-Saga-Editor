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
		if (ROM.updateBelaineSwordTexts)
		{
			updateBelaineSwordTexts()
		}
	}

	fun updateBelaineSwordTexts()
	{
		when (itemCode)
		{
			0x77 -> updateTidal()
			0x78 -> updateZnte()
			0x79 -> updateMura()
			else -> return
		}
	}

	fun updateBelaineSword(costOffset: Int, textOffset: Int)
	{
		ROM.setShort(costOffset, cost)
		val text = getCostText()
		ROM.offset = textOffset
		text.forEach { ROM.nextByte = it }
	}

	fun updateMura()
	{
		updateBelaineSword(0xC338, 0x66756)
	}

	fun updateTidal()
	{
		updateBelaineSword(0xC28A, 0x6659C)
	}

	fun updateZnte()
	{
		updateBelaineSword(0xC2E1, 0x66671)
	}

	companion object
	{
		private val baseOffset = 0x639D
		private val bytesPerWeapon = 10
	}

}
