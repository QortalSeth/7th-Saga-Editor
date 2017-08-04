package application.models.lists

import application.models.Spell
import java.io.Serializable
import java.util.*

class Spells : Models<Spell>, Serializable
{

	constructor() : super()
	constructor(addFrom: Spells, keepEmptyModel: Boolean) : super(addFrom, keepEmptyModel)

	override fun initialize(models: ModelsList<Spell>)
	{
		for (i in 0..spellListSize - 1)
		{
			val s = Spell(i)
			s.getValuesFromROM()
			models.add(s)
		}
	}

	override fun saveModels() = models.forEach { it.writeValuesToROM() }

	fun sortSpellsByType() = sortList(Spells.spellTypeComparator)

	companion object
	{
		private val spellListSize = 61

		val spellTypeComparator: Comparator<Spell> = Comparator { s1, s2 ->
			var difference = s1.elementPriority - s2.elementPriority
			if (difference != 0) difference
			else
			{
				difference = s1.target - s2.target
				if (difference != 0) difference
				else s1.cost - s2.cost
			}
		}
	}
}
