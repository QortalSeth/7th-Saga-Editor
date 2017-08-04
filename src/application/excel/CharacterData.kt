package application.excel

import application.models.lists.Characters
import application.models.lists.Lists

class CharacterData(fileName: String) : Excel(fileName)
{
	override fun makeChangeList()
	{
		val sheet = workbook.createSheet("Character Data")
		val rowData = mutableListOf<RowData>()

		val characters = Characters()
		characters.addUsefulModels(Lists.characters, false)

		for (i in 0..Characters.characterListSize - 1)
		{

			val c = characters.models[i]
			val Dc = characters.dModels[i]

			rowData.add(RowData(boldStyle, mutableListOf(c.name)))
			rowData.add(RowData(defaultStyle, mutableListOf("Stats", "HP", "MP", "Power", "Guard", "Magic", "Speed")))
			rowData.add(RowData(defaultStyle, mutableListOf("Initial", CompareValues(Dc.hpStart, c.hpStart), CompareValues(Dc.mpStart, c.mpStart), CompareValues(Dc.powerStart, c.powerStart), CompareValues(Dc.guardStart, c.guardStart), CompareValues(Dc.magicStart, c.magicStart), CompareValues(Dc.speedStart, c.speedStart))))
			rowData.add(RowData(defaultStyle, mutableListOf("Level Up", CompareValues(Dc.hpGrowth, c.hpGrowth), CompareValues(Dc.mpGrowth, c.mpGrowth), CompareValues(Dc.powerGrowth, c.powerGrowth), CompareValues(Dc.guardGrowth, c.guardGrowth), CompareValues(Dc.magicGrowth, c.magicGrowth), CompareValues(Dc.speedGrowth, c.speedGrowth))))
			rowData.add(RowData(defaultStyle, mutableListOf("")))

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

			rowData.add(RowData(defaultStyle, spells))
			rowData.add(RowData(defaultStyle, levels))

			if (spells2.size > 1)
			{
				rowData.add(RowData(defaultStyle, mutableListOf("")))
				rowData.add(RowData(defaultStyle, spells2))
				rowData.add(RowData(defaultStyle, levels2))
			}

			rowData.add(RowData(defaultStyle, mutableListOf("")))
		}
		writeDataToSheet(sheet, rowData)
	}
}