package application.models.lists

import application.models.*
import application.models.Equipment
import java.io.Serializable
import java.util.*

open class AbstractItems<T : AbstractItem> : Models<T>, Serializable
{
	constructor() : super()
	constructor(addFrom: Models<T>, keepEmptyModel: Boolean) : super(addFrom, keepEmptyModel)

	constructor(gameIndexes: MutableList<Int>) : super()
	{
		this.addModels<AbstractItems<T>>(gameIndexes)
	}

	constructor(ModelsList: ModelsList<T>) : super(ModelsList)

	override fun initialize(models: ModelsList<T>)
	{
		addUsefulModels(Lists.items as AbstractItems<T>, true)
		addUsefulModels(Lists.weapons as AbstractItems<T>, false)
		addUsefulModels(Lists.armors as AbstractItems<T>, false)
	}

	override fun getIndex(iCode: Int): Int
	{
		models.withIndex().forEach { (index, i) ->
			if (i.itemCode == iCode)
			{
				return index
			}
		}
		println("iCode $iCode not found")
		return 0
	}

	override fun getDIndex(iCode: Int): Int
	{
		dModels.withIndex().forEach { (index, i) ->
			if (i.itemCode == iCode)
			{
				return index
			}
		}
		println("iCode $iCode not found")
		return 0
	}

	override fun getName(iCode: Int): String
	{
		models.filter { it.itemCode == iCode }.forEach { return it.toString() }
		return "Item not found"
	}

	override fun getDName(iCode: Int): String
	{
		dModels.filter { it.itemCode == iCode }.forEach { return it.toString() }
		return "Item not found"
	}

	fun getNames(d: DropTable): MutableList<String>
	{
		val names = mutableListOf<String>()
		d.drops.forEach { names.add(this.getName(it)) }
		return names
	}

	fun getDNames(d: DropTable): MutableList<String>
	{
		val names = mutableListOf<String>()
		d.drops.forEach { names.add(this.getDName(it)) }
		return names
	}

	fun <P : AbstractItems<T>> addModels(itemCodes: MutableList<Int>)
	{
		for (i in itemCodes)
		{
			models.add(Lists.abstractItems.getItem(i) as T)
		}
		println("Item code $itemCodes returns NULL")
	}

	fun getDItem(iCode: Int): T
	{
		dModels.filter { it.itemCode == iCode }.forEach { return it }
		println("Item code $iCode returns NULL")
		return dModels.first()
	}

	fun getItem(iCode: Int): T
	{
		models.filter { it.itemCode == iCode }.forEach { return it }
		println("Item code $iCode returns NULL")
		return models.first()
	}

	override fun toIntegers(): MutableList<Int>
	{
		val indexes = mutableListOf<Int>()
		models.forEach { m -> indexes.add(m.itemCode) }
		return indexes
	}

	override fun toDIntegers(): MutableList<Int>
	{
		val indexes = mutableListOf<Int>()
		dModels.forEach { m -> indexes.add(m.itemCode) }
		return indexes
	}

	override fun getDModel(model: T): T
	{
		dModels.filter { model.itemCode == it.itemCode }.forEach { return it }
		println("Model " + model.name + " has no default!")
		return dModels.first()
	}

	fun sortByDescendingCost() = sortList(descendingCostComparator as Comparator<T>)


	fun sortByAscendingCost() = sortList(ascendingCostComparator as Comparator<T>)

	companion object
	{
		fun sortByItemcode(list: MutableList<Int>) = list.sortWith(itemcodeComparator)

		fun getLocations(shops: MutableList<Shop>, characters: MutableList<Character>, itemCode: Int): String
		{
			val locations = mutableListOf<String>()

			val startCharacters = StringBuilder()

			characters
					// add initial weapons
					.filter { it.weaponStart == itemCode || it.armorStart == itemCode || it.accessoryStart == itemCode }.forEach { startCharacters.append(it.equipName) }

			if (startCharacters.isNotEmpty())
			{
				locations.add("Initial (" + startCharacters.toString() + ")")
			}

			shops
					// add weapons from shops
					.filter { it.weaponCodes.contains(itemCode) || it.armorCodes.contains(itemCode) }.forEach {
				if (it.name.length < 6)
				{
					locations.add(it.name)
				}
				else
				{
					locations.add(it.name.substring(0, 3))
				}
			}

			val monsterDrops = mutableListOf<String>()
			Lists.dropTables.models // add weapons from monster drops
					.forEach { d ->
						d.drops.filter { itemCode == it }.forEach {
							Lists.monsters.models.filter { it.itemDropSet == d.gameIndex && it.name !in monsterDrops }.forEach { monsterDrops.add(it.name) }
						}
					}
			var sb = StringBuilder()

			if (monsterDrops.isNotEmpty())
			{
				sb.append("Dropped by: ")
				for (s in monsterDrops)
				{
					sb.append(s + ", ")
				}
				sb.deleteCharAt(sb.length - 2)
				locations.add(sb.toString().trim { it <= ' ' })
				sb = StringBuilder()
			}

			locations.forEach { s ->
				sb.append(s)
				sb.append(" ")
			}
			return sb.toString().trim { it <= ' ' }
		}

		private val AbstractItemComparator = Comparator<AbstractItem> { a1, a2 ->
			if (a2.itemCode == 0) 1
			else a1.itemCode - a2.itemCode
		}

		private val itemcodeComparator = Comparator<Int> { i1, i2 ->
			if (i1 == 0) 1
			else i1 - i2
		}

		val descendingCostComparator: Comparator<out Equipment> = Comparator { e1, e2 ->
			if (e1.cost == 0) return@Comparator 1
			e2.cost - e1.cost
		}

		val ascendingCostComparator: Comparator<out application.models.Equipment> = Comparator { e1, e2 ->
			if (e1.cost == 0) return@Comparator 1
			e1.cost - e2.cost
		}
	}
}
