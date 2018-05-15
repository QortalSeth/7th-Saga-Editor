package application.models.lists

import application.ROM
import application.Settings
import application.models.Monster
import java.io.Serializable

class Monsters : Models<Monster>, Serializable
{

	constructor() : super()
	constructor(addFrom: Monsters, keepEmptyModel: Boolean) : super(addFrom, keepEmptyModel)
	constructor(addFrom: Monsters, showEmptyValues: Boolean, showDuplicates: Boolean){addUsefulMonsters(addFrom, showEmptyValues, showDuplicates)}

	override fun initialize(models: ModelsList<Monster>)
	{
		for (i in 0..monsterListSize - 1)
		{
			val monster = Monster(i)
			monster.getValuesFromROM()
			models.add(monster)
		}
	}

	fun addUsefulMonsters(addFrom: Monsters, showEmptyValues: Boolean, showDuplicates: Boolean)
	{
		addFrom.models
				.filter {it.name.trim().isNotEmpty() || showEmptyValues}
				.forEach {

					if(showDuplicates == false)
					{
						if (models.contains(it))
						// if monster is duplicate of existing monster,
						// add it to that monsters list of aliases
						{
							val monsterInList = models[models.indexOf(it)]
							monsterInList.aliases.add(it)
						}
						else models.add(it)
					}
					else
					{models.add(it)}

		}
		dModels.addAll(addFrom.dModels)
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
