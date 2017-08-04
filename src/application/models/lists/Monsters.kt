package application.models.lists

import application.ROM
import application.models.Monster
import java.io.Serializable

class Monsters : Models<Monster>, Serializable
{

	constructor() : super()
	constructor(addFrom: Monsters, keepEmptyModel: Boolean) : super(addFrom, keepEmptyModel)

	override fun initialize(models: ModelsList<Monster>)
	{
		for (i in 0..monsterListSize - 1)
		{
			val monster = Monster(i)
			monster.getValuesFromROM()

			if (ROM.showMonsterDuplicates == false)
			{
				if (models.contains(monster))
				// if monster is duplicate of existing monster,
				// add it to that monsters list of aliases
				{
					val monsterInList = models[models.indexOf(monster)]
					monsterInList.aliases.add(monster)
				}
				else models.add(monster)
			}
			else models.add(monster)
		}
	}

	override fun saveModels()
	{
		models.forEach {
			it.writeValuesToROM()
			it.aliases.forEach { it.writeValuesToROM() }
		}
	}

	companion object
	{
		private val monsterListSize = 98
	}

}
