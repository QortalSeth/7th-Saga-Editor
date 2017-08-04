package application.models

import application.ROM
import application.models.lists.AbstractItems
import application.models.lists.Lists
import java.io.Serializable
import java.util.*

class DropTable(i: Int) : Model(i), Serializable
{
	var drops = IntArray(16)

	init
	{
		name = gameIndex.toString()
	}

	fun getValuesFromROM()
	{
		ROM.offset = baseOffset + bytesPerDropTable * gameIndex
		for (i in 0..bytesPerDropTable - 1)
		{
			drops[i] = ROM.nextByte
		}
	}

	fun writeValuesToROM()
	{
		ROM.offset = baseOffset + bytesPerDropTable * gameIndex
		// System.out.println("Offset is: "+Integer.toHexString(offset)+" index is "+gameIndex);

		for (i in 0..bytesPerDropTable - 1)
		{
			// System.out.println("write Drop "+i+" new value is: "+drops[i] + " Old value is: "+bytes[offset+i]);
			ROM.nextByte = drops[i]
		}
		// System.out.println("\n");
	}

	fun getItemNames(items: AbstractItems<AbstractItem>): List<String>
	{
		val names = ArrayList<String>()
		val counts = ArrayList<Int>()

		drops.forEach {
			if (it == 0) return@forEach
			val itemName = items.getName(it)
			if (names.contains(itemName))
			{
				val index = names.indexOf(itemName)
				counts[index] = counts[index] + 1
			}
			else
			{
				names.add(itemName)
				counts.add(1)
			}
		}

		val results = ArrayList<String>()
		names.indices.forEach { results.add(names[it] + " " + counts[it] + "/16") }
		return results
	}

	companion object
	{
		private val baseOffset = 0x8A18
		private val bytesPerDropTable = 16

		fun printTables()
		{

			Lists.dropTables.models.forEach {
				val offset = baseOffset + bytesPerDropTable * it.gameIndex
				ROM.offset = offset
				println("Offset is: " + Integer.toHexString(offset) + " index is " + it.gameIndex)
				for (i in 0..bytesPerDropTable - 1)
				{
					println("Drop " + i + " is: " + it.drops[i] + " " + Lists.abstractItems.getName(it.drops[i]))
				}
			}
			println("\n")
		}
	}
}