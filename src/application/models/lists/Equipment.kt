package application.models.lists

import application.ROM
import application.Settings
import application.enums.EquipmentType
import java.io.Serializable
import java.util.*

open class Equipment<T : application.models.Equipment> : AbstractItems<T>, Serializable
{
	constructor() : super()
	constructor(ModelsList: ModelsList<T>) : super(ModelsList)

	constructor(addFrom: Equipment<T>, equipCode: Int) : super()
	{
		addUsefulModels(addFrom, equipCode)
	}

	constructor(addFrom: Equipment<T>) : super()
	{
		addUsefulModels(addFrom, 0xFF)
	}

	constructor(itemCodes: List<Int>)
	{
		this.addEquipment(itemCodes)
	}

	open fun addEmptyModel(models: MutableList<T>)
	{
		val emptyEquipment = application.models.Equipment(0)
		emptyEquipment.gameIndex = 0
		emptyEquipment.itemCode = 0
		emptyEquipment.name = ""
		emptyEquipment.power = 0
		emptyEquipment.equipCode = 255
		models.add(0, emptyEquipment as T)
	}

	fun addUsefulModels(addFrom: Equipment<T>, equipCode: Int)
	{
		addFrom.models
				.filter { equipCode and (it.equipCode or 0x80) > 0 && (ROM.settings.showEmptyValues || it.name.isNotEmpty()) }
				.forEach { models.add(it) }

		dModels.addAll(addFrom.dModels)
	}

	fun sortByAscendingPower() = sortList(ascendingComparator as Comparator<T>)
	fun sortByDescendingPower() = sortList(descendingComparator as Comparator<T>)
	fun sortByAscendingType() = sortList(ascendingTypeComparator as Comparator<T>)
	fun sortByDescendingType() = sortList(descendingTypeComparator as Comparator<T>)


	fun addEquipment(itemCodes: List<Int>)
	{
		val equipment = Equipment<T>()
		equipment.addUsefulModels(Lists.weapons as Equipment<T>, 0xFF)
		equipment.addUsefulModels(Lists.armors as Equipment<T>, 0xFF)
		equipment.addEmptyModel(equipment.models)

		itemCodes.forEach { models.add(equipment.getItem(it)) }
		println("Item code: $itemCodes")
	}

	companion object
	{

		val ascendingComparator: Comparator<out application.models.Equipment> = Comparator { e1, e2 ->
			e1.power - e2.power
		}
		val descendingComparator: Comparator<out application.models.Equipment> = Comparator { e1, e2 ->
			e2.power - e1.power
		}
		val ascendingTypeComparator: Comparator<out application.models.Equipment> = Comparator { e1, e2 ->
			var difference = EquipmentType.compareTo(e1, e2)
			if (difference == 0)
			{
				difference = e1.power - e2.power
				if (difference == 0)
					difference = e1.cost - e2.cost
			}
			difference
		}
		val descendingTypeComparator: Comparator<out application.models.Equipment> = Comparator { e1, e2 ->
			var difference = EquipmentType.compareTo(e1, e2)
			if (difference == 0)
			{
				difference = e2.power - e1.power

				if (difference == 0)
					difference = e2.cost - e1.cost
			}
			difference
		}
	}

}
