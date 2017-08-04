package application.models

import application.ROM
import application.enums.ItemType
import application.staticClasses.TextReader
import java.io.Serializable

class Item : AbstractItem, Serializable
{

	var target: Int = 0
	var uses: Int = 0
	var users: Int = 0
	var sellRatio: Int = 0

	constructor(index: Int) : super(index)
	{
		itemCode = index
		setChronologicalIndex()
	}

	constructor(i: Item) : super(i)
	{
		target = i.target
		uses = i.uses
		users = i.users
		sellRatio = i.sellRatio
	}

	fun getValuesFromROM()
	{
		ROM.offset = baseOffset + bytesPerItem * gameIndex

		target = ROM.nextByte
		uses = ROM.nextByte
		cost = ROM.nextShort
		users = ROM.nextByte
		sellRatio = ROM.nextByte
		namePointer = ROM.nextTriple
		name = TextReader.readText(namePointer)
	}

	fun writeValuesToROM()
	{
		ROM.offset = baseOffset + bytesPerItem * gameIndex

		ROM.nextByte = target
		ROM.nextByte = uses
		ROM.nextShort = cost
		ROM.nextByte = users
		ROM.nextByte = sellRatio
		ROM.nextTriple = namePointer
	}

	private fun setChronologicalIndex()
	{
		var index = ItemType.getChronOrder(this).ordinal
		if (index == ItemType.MISC.ordinal) index = gameIndex + 50
		chronologicalIndex = index
	}


	companion object
	{
		private val baseOffset = 0x6C94
		private val bytesPerItem = 9
	}

}
