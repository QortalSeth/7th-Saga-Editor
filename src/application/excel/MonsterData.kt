package application.excel

import application.ROM
import application.controllers.MonsterEditor
import application.models.lists.*

class MonsterData(fileName: String) : Excel(fileName)
{
	override fun makeChangeList()
	{
		val sheet = workbook.createSheet("Monster Data")
		val sheetData = SheetData()
		//val monsters = Monsters(Lists.monsters, false)
		var monsters = Monsters(Lists.monsters, ROM.settings.showEmptyValues, ROM.settings.showMonsterDuplicates)

		if (MonsterEditor.isChronologicalOrderSelected) monsters.chronologicalIndexSort()

		sheetData.addRow(RowData(boldStyle, mutableListOf("Stats", "", "Resists", "", "Spells", "", "Drops")))
		sheetData.addRow(RowData(boldStyle, mutableListOf("")))

		val spells = Spells(Lists.spells, true)
		val dropTables = DropTables(Lists.dropTables)
		val abstractItems = AbstractItems(Lists.abstractItems, true)

		for (i in 0..monsters.models.size - 1)
		{
			val m = monsters.models[i]
			val Dm = monsters.getDModel(m)

			val spellNames = spells.getNames(m.spells)
			val DspellNames = spells.getDNames(Dm.spells)

			val spellChances = m.spellChanceStrings
			val DspellChances = Dm.spellChanceStrings

			val itemDropNames = abstractItems.getNames(dropTables.models[m.itemDropSet])
			val DitemDropNames = abstractItems.getDNames(dropTables.dModels[Dm.itemDropSet])

			var index = 0
			sheetData.addRow(RowData(boldStyle, mutableListOf(m.name)))

			sheetData.addRow(RowData(defaultStyle, mutableListOf("HP", CompareValues(Dm.hp, m.hp), "Boss Bit", CompareValues(Integer.toHexString(Dm.runFlag), Integer.toHexString(m.runFlag)), CompareValues(DspellNames[index], spellNames[index]), CompareValues(DspellChances[index], spellChances[index]), CompareValues(Pget(DitemDropNames, index), Pget(itemDropNames, index++)))))
			sheetData.addRow(RowData(defaultStyle, mutableListOf("MP", CompareValues(Dm.mp, m.mp), "Lightning", CompareValues(Dm.laserRes, m.laserRes), CompareValues(DspellNames[index], spellNames[index]), CompareValues(DspellChances[index], spellChances[index]), CompareValues(Pget(DitemDropNames, index), Pget(itemDropNames, index++)))))
			sheetData.addRow(RowData(defaultStyle, mutableListOf("Power", CompareValues(Dm.power, m.power), "Unknown", CompareValues(Dm.unknownRes1, m.unknownRes1), CompareValues(DspellNames[index], spellNames[index]), CompareValues(DspellChances[index], spellChances[index]), CompareValues(Pget(DitemDropNames, index), Pget(itemDropNames, index++)))))
			sheetData.addRow(RowData(defaultStyle, mutableListOf("Guard", CompareValues(Dm.guard, m.guard), "Unknown2", CompareValues(Dm.unknownRes2, m.unknownRes2), CompareValues(DspellNames[index], spellNames[index]), CompareValues(DspellChances[index], spellChances[index]), CompareValues(Pget(DitemDropNames, index), Pget(itemDropNames, index++)))))
			sheetData.addRow(RowData(defaultStyle, mutableListOf("Magic", CompareValues(Dm.magic, m.magic), "Fire", CompareValues(Dm.fireRes, m.fireRes), CompareValues(DspellNames[index], spellNames[index]), CompareValues(DspellChances[index], spellChances[index]), CompareValues(Pget(DitemDropNames, index), Pget(itemDropNames, index++)))))
			sheetData.addRow(RowData(defaultStyle, mutableListOf("Speed", CompareValues(Dm.speed, m.speed), "Ice", CompareValues(Dm.iceRes, m.iceRes), CompareValues(DspellNames[index], spellNames[index]), CompareValues(DspellChances[index], spellChances[index]), CompareValues(Pget(DitemDropNames, index), Pget(itemDropNames, index++)))))

			sheetData.addRow(RowData(defaultStyle, mutableListOf("Gold", CompareValues(Dm.gold, m.gold), "Vacuum", CompareValues(Dm.vacuumRes, m.vacuumRes), CompareValues(DspellNames[index], spellNames[index]), CompareValues(DspellChances[index], spellChances[index]), CompareValues(Pget(DitemDropNames, index), Pget(itemDropNames, index++)))))
			sheetData.addRow(RowData(defaultStyle, mutableListOf("Exp", CompareValues(Dm.experience, m.experience), "Debuff", CompareValues(Dm.debuffRes, m.debuffRes), CompareValues(DspellNames[index], spellNames[index]), CompareValues(DspellChances[index], spellChances[index]), CompareValues(Pget(DitemDropNames, index), Pget(itemDropNames, index++)))))
			sheetData.addRow(RowData(defaultStyle, mutableListOf("")))
			sheetData.addRow(RowData(defaultStyle, mutableListOf("")))
		}
		sheet.createFreezePane(0,2,0,2)
		writeDataToSheet(sheet, sheetData)
	}
}


