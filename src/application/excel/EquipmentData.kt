package application.excel

import application.enums.EquipmentType
import application.models.Armor
import application.models.Weapon
import application.models.lists.*
import application.staticClasses.TextReader
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import java.util.*

class EquipmentData(fileName: String) : Excel(fileName)
{
	override fun makeChangeList()
	{
		val weaponsSheet = workbook.createSheet("Weapon Data")
		val armorSheet = workbook.createSheet("Armor Data")

		weaponsSheet.createFreezePane(2,2,2,2)
		armorSheet.createFreezePane(2,2,2,2)
		writeDataToSheet(weaponsSheet, fillWeaponSheet())
		writeDataToSheet(armorSheet, fillArmorSheet())
	}


	private fun fillWeaponSheet(): SheetData
	{
		val weapons = Weapons(Lists.weapons)
		val axes = Weapons(weapons.dModels)
		val knightSwords = Weapons(weapons.dModels)
		val swords = Weapons(weapons.dModels)
		val knives = Weapons(weapons.dModels)
		val rods = Weapons(weapons.dModels)
		val fists = Weapons(weapons.dModels)
		val misc = Weapons(weapons.dModels)

		val sheetData = SheetData()
		weapons.models.forEach {
			when (EquipmentType.getType(it))
			{
				EquipmentType.SWORD                                         -> if (it.equipCode == 1) knightSwords.models.add(it)
				else if (it.equipCode and 0x08 > 0) misc.models.add(it) // if only Wilme can equip it
				else swords.models.add(it)

				EquipmentType.AXE                                           -> axes.models.add(it)
				EquipmentType.STAFF, EquipmentType.ROD, EquipmentType.SABER -> rods.models.add(it)
				EquipmentType.KNIFE                                         -> knives.models.add(it)
				EquipmentType.HAND                                          -> fists.models.add(it)
				else                                                        -> misc.models.add(it)
			}
		}

		sheetData.addRow(RowData(boldStyle, mutableListOf("Name", "Power", "Cost", "Discount", "Equip", "Locations", "Default Locations", "Name Pointer", "ROM Location")))
		sheetData.addRow(RowData(defaultStyle, mutableListOf("")))

		val shops = Shops()
		shops.addUsefulModels(Lists.shops, false)
		shops.chronologicalIndexSort()

		sheetData.addRow(RowData(boldStyle, mutableListOf("Knight Swords")))
		addWeaponType(sheetData, knightSwords, shops, defaultStyle)

		sheetData.addRow(RowData(boldStyle, mutableListOf("Axes")))
		addWeaponType(sheetData, axes, shops, defaultStyle)

		sheetData.addRow(RowData(boldStyle, mutableListOf("Swords")))
		addWeaponType(sheetData, swords, shops, defaultStyle)
		
		sheetData.addRow(RowData(boldStyle, mutableListOf("Knives")))
		addWeaponType(sheetData, knives, shops, defaultStyle)

		sheetData.addRow(RowData(boldStyle, mutableListOf("Rods")))
		addWeaponType(sheetData, rods, shops, defaultStyle)

		sheetData.addRow(RowData(boldStyle, mutableListOf("Fists")))
		addWeaponType(sheetData, fists, shops, defaultStyle)

		sheetData.addRow(RowData(boldStyle, mutableListOf("Misc.")))
		addWeaponType(sheetData, misc, shops, defaultStyle)
		return sheetData
	}

	private fun addWeaponType(rowData: SheetData, weapons: Weapons, shops: Shops, defaultStyle: XSSFCellStyle)
	{
		weapons.sortList(application.models.lists.Equipment.ascendingComparator as Comparator<Weapon>)

		weapons.models.forEach {
			val Dw = weapons.getDModel(it)
			val namePointer = CompareValues(TextReader.pointerToText(Dw.namePointer), TextReader.pointerToText(it.namePointer))
			rowData.addRow(RowData(defaultStyle,
				mutableListOf(CompareValues(Dw.name, it.name),
					CompareValues(Dw.power, it.power),
					CompareValues(Dw.cost, it.cost),
					CompareValues(Dw.discount, it.discount),
					CompareValues(getEquipNames(Dw.equipCode),
						getEquipNames(it.equipCode)),
					AbstractItems.getLocations(shops.models,
						Lists.characters.models, it.itemCode),
					AbstractItems.getLocations(shops.dModels,
						Lists.characters.dModels, Dw.itemCode),
					namePointer,
					"0x${Integer.toHexString(it.offset).toUpperCase()}")))
		}
		rowData.addRow(RowData(defaultStyle, mutableListOf("")))
	}

	private fun fillArmorSheet(): SheetData
	{
		val sheetData = SheetData()
		val armors = Armors(Lists.armors)
		armors.models.removeIf{it.equipCode==0}


		val bodyArmors = Armors(armors.dModels)
		val robes = Armors(armors.dModels)
		val coats = Armors(armors.dModels)
		val miscArmors = Armors(armors.dModels)
		val shields = Armors(armors.dModels)
		val accessories = Armors(armors.dModels)

		armors.models.forEach {
			when (EquipmentType.getType(it))
			{
				EquipmentType.MAIL   -> bodyArmors.models.add(it)
				EquipmentType.ARMOR  -> bodyArmors.models.add(it)
				EquipmentType.CLOAK  -> if (it.equipCode == 8) miscArmors.models.add(it)
				else robes.models.add(it)
				EquipmentType.ROBE   -> robes.models.add(it)
				EquipmentType.SHIELD -> shields.models.add(it)
				else                 -> if (it.isCoat) //Default
					coats.models.add(it)
				else if (it.isBodyArmor) miscArmors.models.add(it)
				else accessories.models.add(it)
			}
		}

		sheetData.addRow(RowData(boldStyle, mutableListOf("Name", "Defense", "Cost", "Discount", "Equip", "Lit", "???", "???", "Fire", "Ice", "Vac", "Deb", "Locations", "Default Locations", "Name Pointer", "ROM Location")))
		sheetData.addRow(RowData(defaultStyle, mutableListOf("")))

		val shops = Shops(Lists.shops)
		shops.chronologicalIndexSort()

		sheetData.addRow(RowData(boldStyle, mutableListOf("Armors")))
		addArmorType(sheetData, bodyArmors, shops, defaultStyle)
		sheetData.addRow(RowData(boldStyle, mutableListOf("Robes")))
		addArmorType(sheetData, robes, shops, defaultStyle)
		sheetData.addRow(RowData(boldStyle, mutableListOf("Coats")))
		addArmorType(sheetData, coats, shops, defaultStyle)
		sheetData.addRow(RowData(boldStyle, mutableListOf("Misc. Armors")))
		addArmorType(sheetData, miscArmors, shops, defaultStyle)
		sheetData.addRow(RowData(boldStyle, mutableListOf("Shields")))
		addArmorType(sheetData, shields, shops, defaultStyle)
		sheetData.addRow(RowData(boldStyle, mutableListOf("Accessories")))
		addArmorType(sheetData, accessories, shops, defaultStyle)
		return sheetData
	}

	private fun addArmorType(rowData: SheetData, armors: Armors, shops: Shops, defaultStyle: XSSFCellStyle)
	{
		armors.sortList(application.models.lists.Equipment.ascendingComparator as Comparator<Armor>)
		armors.models.forEach {
			val Da = armors.getDModel(it)



			val namePointer = CompareValues(TextReader.pointerToText(Da.namePointer), TextReader.pointerToText(it.namePointer))
			rowData.addRow(RowData(defaultStyle, mutableListOf(CompareValues(Da.name, it.name), CompareValues(Da.power, it.power), CompareValues(Da.cost, it.cost), CompareValues(Da.discount, it.discount), CompareValues(getEquipNames(Da.equipCode), getEquipNames(it.equipCode)), CompareValues(Da.laserRes, it.laserRes), CompareValues(Da.unknownRes1, it.unknownRes1), CompareValues(Da.unknownRes2, it.unknownRes2), CompareValues(Da.fireRes, it.fireRes), CompareValues(Da.iceRes, it.iceRes), CompareValues(Da.vacuumRes, it.vacuumRes), CompareValues(Da.debuffRes, it.debuffRes), AbstractItems.getLocations(shops.models, Lists.characters.models, it.itemCode), AbstractItems.getLocations(shops.dModels, Lists.characters.dModels, Da.itemCode), namePointer, "0x${Integer.toHexString(it.offset).toUpperCase()}")))
		}
		rowData.addRow(RowData(defaultStyle, mutableListOf("")))
	}

	fun getEquipNames(equipCode: Int): String
	{
		val sb = StringBuilder()
		if (equipCode and 0x01 > 0) sb.append("K")
		if (equipCode and 0x02 > 0) sb.append("O")
		if (equipCode and 0x04 > 0) sb.append("E")
		if (equipCode and 0x08 > 0) sb.append("W")
		if (equipCode and 0x10 > 0) sb.append("X")
		if (equipCode and 0x20 > 0) sb.append("V")
		if (equipCode and 0x40 > 0) sb.append("L")
		return sb.toString()
	}
}
