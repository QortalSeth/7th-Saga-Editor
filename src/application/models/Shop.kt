package application.models

import application.ROM
import application.enums.ShopType
import application.models.lists.Armors
import application.models.lists.Lists
import application.models.lists.Weapons
import application.staticClasses.TextReader
import java.io.Serializable
import java.util.*

class Shop : Model, Serializable
{

	internal var weaponCodes: MutableList<Int> = ArrayList()
	internal var armorCodes: MutableList<Int> = ArrayList()
	internal var itemCodes: MutableList<Int> = ArrayList()
	var innCost: Int = 0

	constructor(index: Int) : super(index)
	{
		chronologicalIndex = ShopType.getChronOrder(this).ordinal
	}

	fun getValuesFromROM()
	{
		ROM.offset = baseOffset + bytesPerShop * gameIndex
		offset = ROM.offset

		for (i in 0..4) weaponCodes.add(ROM.nextByte)
		for (i in 0..7) armorCodes.add(ROM.nextByte)
		for (i in 0..8) itemCodes.add(ROM.nextByte)
		innCost = ROM.nextShort
		namePointer = ROM.nextTriple
		name = TextReader.readText(namePointer)
	}

	fun writeValuesToROM()
	{
		ROM.offset = baseOffset + bytesPerShop * gameIndex
		for (i in 0..4) ROM.nextByte = weaponCodes[i]
		for (i in 0..7) ROM.nextByte = armorCodes[i]
		for (i in 0..8) ROM.nextByte = itemCodes[i]
		ROM.nextShort = innCost
		ROM.nextTriple = namePointer
	}

	fun sortWeaponsAscending()
	{
		val weapons = Weapons(weaponCodes)
		//weapons.sortByAscendingCost()
		weapons.sortByAscendingType()
		weaponCodes = weapons.toIntegers()
	}

	fun sortWeaponsDescending()
	{
		val weapons = Weapons(weaponCodes)
		//weapons.sortByDescendingCost()
		weapons.sortByDescendingType()
		weaponCodes = weapons.toIntegers()
	}

	fun sortArmorsAscending()
	{
		val armors = Armors(armorCodes)
		//armors.sortByAscendingCost()
		armors.sortByAscendingType()
		armorCodes = armors.toIntegers()
	}

	fun sortArmorsDescending()
	{
		val armors = Armors(armorCodes)
		//armors.sortByDescendingCost()
		armors.sortByDescendingType()
		armorCodes = armors.toIntegers()
	}

	fun printList(list: List<Int>)
	{
		list.forEach { println("Item Code is: " + Integer.toHexString(it) + " Name is: " + Lists.abstractItems.getName(it)) }
		println()
	}

	constructor(s: Shop) : super(s)
	{
		weaponCodes.addAll(s.weaponCodes)
		armorCodes.addAll(s.armorCodes)
		itemCodes.addAll(s.itemCodes)
		innCost = s.innCost
	}

	companion object
	{
		private val baseOffset = 0x8308
		private val bytesPerShop = 27

		fun copyShops(shops: List<Shop>): List<Shop>
		{
			val copy = ArrayList<Shop>()
			shops.forEach { copy.add(Shop(it)) }
			return copy
		}
	}
}
