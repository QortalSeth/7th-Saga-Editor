package application.models.lists

import application.ROM
import application.Settings
import application.models.Armor
import java.io.Serializable

class Armors : Equipment<Armor>, Serializable
{
	constructor() : super()
	constructor(addFrom: Armors, equipCode: Int) : super(addFrom, equipCode)
	constructor(gameIndexes: List<Int>) : super(gameIndexes)
	constructor(ModelsList: ModelsList<Armor>) : super(ModelsList)
	constructor(addFrom: Armors) : super(addFrom)
	constructor(addFrom: Armors, addEmptyModel: Boolean) : super(addFrom)
	{
		if (addEmptyModel)
			this.addEmptyModel(models)
	}

	override fun initialize(models: ModelsList<Armor>)
	{
		for (i in 0..armorListSize - 1)
		{
			val a = Armor(i)
			a.getValuesFromROM()
			models.add(a)
		}
	}

	override fun saveModels() = models.forEach { it.writeValuesToROM() }

	fun addUsefulBodyArmors(armors: Armors, equipCode: Int)
	{
		armors.models
				.filter { (it.isBodyArmor && equipCode and (it.equipCode or 0x80) > 0) && (it.name.isNotEmpty() || ROM.settings.showEmptyValues)  }
				.forEach { models.add(it) }
		dModels.addAll(armors.dModels)
	}

	fun addUsefulAccessories(armors: Armors, equipCode: Int)
	{
		armors.models
				.filter { (it.isAccessory && equipCode and (it.equipCode or 0x80) > 0) && (it.name.isNotEmpty() || ROM.settings.showEmptyValues)  }
				.forEach { models.add(it) }
		dModels.addAll(armors.dModels)
	}

	override fun addEmptyModel(models: MutableList<Armor>)
	{
		val emptyArmor = Armor(0)
		emptyArmor.gameIndex = 0
		emptyArmor.itemCode = 0
		emptyArmor.name = ""
		emptyArmor.equipCode = 0
		models.add(0, emptyArmor)
	}

	companion object
	{
		private val armorListSize = 105
	}
}
