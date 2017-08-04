package application.models.lists

import application.ROM
import application.models.Apprentice
import java.io.Serializable

class Apprentices : Models<Apprentice>(), Serializable
{

	private val apprenticeListSize = 7

	var goldPerLevel: Int = 0
	var dgoldPerLevel: Int = 0

	override fun initialize(models: ModelsList<Apprentice>)
	{
		(0..apprenticeListSize - 1).forEach { i ->
			val apprentice = Apprentice(i)
			apprentice.getValuesFromROM()
			models.add(apprentice)
		}
		goldPerLevel = ROM.getByte(0x280C2)
	}

	override fun saveModels()
	{
		for (a in models) a.writeValuesToROM()
		ROM.setByte(0x280C2, goldPerLevel)
	}

	companion object
	{
		val dialogueTypes = arrayOf("Offer to Join", "Beg to Join", "Make no Offer", "Ask to get Stronger", "Offer to Fight", "Ask to go Away")
	}

}
