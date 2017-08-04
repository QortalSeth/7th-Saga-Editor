package application.models.lists

import application.models.Shop
import java.io.Serializable

class Shops : Models<Shop>, Serializable
{

	constructor() : super()
	constructor(addFrom: Shops) : super(addFrom)

	override fun initialize(models: ModelsList<Shop>)
	{
		for (i in 0..shopListSize - 1)
		{
			val s = Shop(i)
			s.getValuesFromROM()
			models.add(s)
		}
	}

	override fun saveModels() = models.forEach { it.writeValuesToROM() }

	companion object
	{
		private val shopListSize = 40
	}

}
