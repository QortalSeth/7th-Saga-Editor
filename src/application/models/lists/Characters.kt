package application.models.lists

import application.models.Character
import java.io.Serializable

class Characters : Models<application.models.Character>, Serializable
{

	constructor() : super()
	constructor(addFrom: Characters) : super(addFrom)

	override fun initialize(models: ModelsList<application.models.Character>)
	{
		for (i in 0..characterListSize - 1)
		{
			val c = Character(Characters.characterNamesByGameIndex[i], i)
			c.getValuesFromROM()
			c.chronologicalIndex = chronologicalIndexes[i]
			models.add(c)
		}
    }

	override fun saveModels()
	{
		for (c in models) c.writeValuesToROM()
    }

	fun charactersCanlearnSpell(spellID: Int): Boolean
	{
		models.forEach({
			               if (it.canLearnSpell(spellID)) return true
		               })
		return false
    }

	companion object
	{

		val characterListSize = 7

		val characterNamesByGameIndex = mutableListOf("Kamil", "Olvan", "Esuna", "Wilme", "Lux", "Valsu", "Lejes")
		val characterNamesByClassOrder = mutableListOf("Wilme","Lux","Olvan","Kamil","Lejes","Valsu","Esuna")
		val equipCodes = intArrayOf(1, 2, 4, 8, 16, 32, 64)

		private val chronologicalIndexes = intArrayOf(3, 2, 6, 0, 1, 5, 4)
    }
}
