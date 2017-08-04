package application.models.lists

import application.models.DropTable
import java.io.Serializable

class DropTables : Models<DropTable>, Serializable
{

	constructor() : super()
	constructor(addFrom: DropTables) : super(addFrom)

	override fun initialize(models: ModelsList<DropTable>)
	{
		for (i in 0..dropTableSize - 1)
		{
			val d = DropTable(i)
			d.getValuesFromROM()
			models.add(d)
		}
	}

	override fun saveModels()
	{
		models.forEach { it.writeValuesToROM() }
	}

	companion object
	{
		private val dropTableSize = 16
	}
}
