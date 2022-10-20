package application.excel

import application.models.lists.Characters
import application.models.lists.Lists
import org.apache.poi.xssf.usermodel.XSSFSheet

class CharacterData(fileName: String) : Excel(fileName)
{
	override fun makeChangeList()
	{
		val characters = Characters()
		characters.addUsefulModels(Lists.characters, false)
		characters.chronologicalIndexSort()
		fillStatsSheet(characters)
		fillSpellsSheet(characters)

	}
	private fun fillStatsSheet(characters: Characters) {
		val statsSheet = workbook.createSheet("Stats")
		val sheetData = SheetData()
		sheetData.addRow(RowData(bigBoldStyle, mutableListOf("", "HP", "MP", "Power", "Guard", "Magic", "Speed")))
		sheetData.addRow(RowData(bigStyle, mutableListOf("Starting Stats")))
		characters.models.forEachIndexed { index, c ->
			val Dc = characters.dModels[index]
			sheetData.addRow(RowData(defaultStyle, mutableListOf(
				c.name,
				CompareValues(Dc.hpStart,c.hpStart),
				CompareValues(Dc.mpStart,c.mpStart),
				CompareValues(Dc.powerStart,c.powerStart),
				CompareValues(Dc.guardStart,c.guardStart),
				CompareValues(Dc.magicStart, c.magicStart),
				CompareValues(Dc.speedStart, c.speedStart)
			)))
		}
		sheetData.addRow(RowData(defaultStyle, mutableListOf("")))
		sheetData.addRow(RowData(bigBoldStyle, mutableListOf("Growth Rates")))
		characters.models.forEachIndexed { index, c ->
			val Dc = characters.dModels[index]
			sheetData.addRow(RowData(defaultStyle, mutableListOf(
				c.name,
				CompareValues(Dc.hpGrowth,c.hpGrowth),
				CompareValues(Dc.mpGrowth,c.mpGrowth),
				CompareValues(Dc.powerGrowth,c.powerGrowth),
				CompareValues(Dc.guardGrowth,c.guardGrowth),
				CompareValues(Dc.magicGrowth, c.magicGrowth),
				CompareValues(Dc.speedGrowth, c.speedGrowth)
			)))
		}
		statsSheet.createFreezePane(0,1,0,1)
		sheetData.setColumnStyle(bigBoldStyle,0)
		writeDataToSheet(statsSheet, sheetData)
	}


	private fun fillSpellsSheet(characters: Characters) {
		val spellsSheet = workbook.createSheet("Spells")
		val sheetData = SheetData()
		val header = mutableListOf<String>()
		header.add("#")
		characters.models.forEach{

			header.add(it.name)
			header.add("Level")
			header.add(" ")
		}

		sheetData.addRow(RowData(boldStyle, header))
		val spells = Lists.spells
		for(i in 0 until 16){
			val row = mutableListOf<String>()
			row.add((i+1).toString())

			characters.models.forEachIndexed{ index, c ->
				val Dc = characters.dModels[index]


				row.add(CompareValues(spells.getDName(Dc.spells[i]), spells.getName(c.spells[i])))
				row.add(CompareValues(Dc.spellLevels[i], c.spellLevels[i]))
				row.add("")
			}
			sheetData.addRow(RowData(defaultStyle, row))
		}
		spellsSheet.createFreezePane(1,1,1,1)
		sheetData.setColumnStyle(bigBoldStyle,0)
		writeDataToSheet(spellsSheet, sheetData)
	}
	/*	for (i in 0..Characters.characterListSize - 1)
		{

			val c = characters.models[i]
			val Dc = characters.dModels[i]

			sheetData.addRow(RowData(boldStyle, mutableListOf(c.name)))

			sheetData.addRow(RowData(defaultStyle, mutableListOf("Initial", CompareValues(Dc.hpStart, c.hpStart), CompareValues(Dc.mpStart, c.mpStart), CompareValues(Dc.powerStart, c.powerStart), CompareValues(Dc.guardStart, c.guardStart), CompareValues(Dc.magicStart, c.magicStart), CompareValues(Dc.speedStart, c.speedStart))))
			sheetData.addRow(RowData(defaultStyle, mutableListOf("Level Up", CompareValues(Dc.hpGrowth, c.hpGrowth), CompareValues(Dc.mpGrowth, c.mpGrowth), CompareValues(Dc.powerGrowth, c.powerGrowth), CompareValues(Dc.guardGrowth, c.guardGrowth), CompareValues(Dc.magicGrowth, c.magicGrowth), CompareValues(Dc.speedGrowth, c.speedGrowth))))
			sheetData.addRow(RowData(defaultStyle, mutableListOf("")))

			val spells = mutableListOf<String>()
			val levels = mutableListOf<String>()

			val spells2 = mutableListOf<String>()
			val levels2 = mutableListOf<String>()

			spells.add("Spells")
			levels.add("Levels")

			spells2.add("Spells")
			levels2.add("Levels")

			val spellsList = Lists.spells

			val characterSpells = spellsList.getNames(c.spells)
			val DCharacterSpells = spellsList.getDNames(Dc.spells)

			characterSpells.indices.forEach { characterSpells[it] = modifyCase(characterSpells[it]) }
			DCharacterSpells.indices.forEach { DCharacterSpells[it] = modifyCase(DCharacterSpells[it]) }

			characterSpells.indices.forEach { k ->
				if (k < DCharacterSpells.size)
				{
					spells.add(CompareValues(DCharacterSpells[k], characterSpells[k]))
					levels.add(CompareValues(Dc.spellLevels[k], c.spellLevels[k]))
				}
				else
				{
					spells.add(CompareValues("", characterSpells[k]))
					levels.add(CompareValues("", Integer.toString(c.spellLevels[k])))
				}
			}

			while (spells.size > 9)
			{
				spells2.add(spells.removeAt(9))
			}
			while (levels.size > 9)
			{
				levels2.add(levels.removeAt(9))
			}

			sheetData.addRow(RowData(defaultStyle, spells))
			sheetData.addRow(RowData(defaultStyle, levels))

			if (spells2.size > 1)
			{
				sheetData.addRow(RowData(defaultStyle, mutableListOf("")))
				sheetData.addRow(RowData(defaultStyle, spells2))
				sheetData.addRow(RowData(defaultStyle, levels2))
			}

			sheetData.addRow(RowData(defaultStyle, mutableListOf("")))
		}
		writeDataToSheet(statsSheet, sheetData)*/

}