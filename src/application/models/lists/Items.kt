package application.models.lists

import application.models.Item
import java.io.Serializable
import java.util.*

class Items : AbstractItems<Item>, Serializable
{

	constructor() : super()
	constructor(addFrom: Items, keepEmptyModel: Boolean) : super(addFrom, keepEmptyModel)

	override fun initialize(models: ModelsList<Item>)
	{
		for (i in 0..itemListSize - 1)
		{
			val item = Item(i)
			item.getValuesFromROM()
			models.add(item)
		}
	}

	override fun saveModels() = models.forEach { i -> i.writeValuesToROM() }

	fun itemCodesToItems(itemCodes: List<Int>): Items
	{
		val items = Items()

		itemCodes.forEach { Lists.items.getItem(it) }
		return items
	}

	fun getItemCodes(list: Items): MutableList<Int>
	{
		val itemCodes = mutableListOf<Int>()
		models.forEach { itemCodes.add(it.itemCode) }
		return itemCodes
	}

	fun getDItemCodes(list: Items): MutableList<Int>
	{
		val itemCodes = mutableListOf<Int>()
		dModels.forEach { itemCodes.add(it.itemCode) }
		return itemCodes
	}

	companion object
	{
		private val itemListSize = 100

		fun sortItemCodesChronologically(itemCodes: MutableList<Int>)
		{
			val items = Items()
			items.addModels<AbstractItems<Item>>(itemCodes)
			items.chronologicalIndexSort()
			itemCodes.indices.forEach { itemCodes[it] = items.models[it].itemCode }
			moveZerosToEndOfList(itemCodes)
		}

		private fun moveZerosToEndOfList(itemCodes: MutableList<Int>)
		{
			val newItemCodes = ArrayList<Int>()
			var zerosNum = 0
			itemCodes.indices.forEach {
				if (itemCodes[it] != 0) newItemCodes.add(itemCodes[it])
				else zerosNum++
			}

			for (i in 0..zerosNum - 1) newItemCodes.add(0) // addRow zeros back into list
			for (i in itemCodes.indices) itemCodes[i] = newItemCodes[i]
		}
	}
}
