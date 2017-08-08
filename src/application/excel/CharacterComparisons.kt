package application.excel

import application.models.lists.Characters
import application.models.lists.Lists
import application.models.lists.Spells

class CharacterComparisons(fileName: String) : Excel(fileName)
{
	override fun makeChangeList()
	{
					val sheet = workbook.createSheet("Spell Comparisons")
					val rowData = mutableListOf<RowData>()
					val characterNames = Characters.characterNames
					characterNames.add(0, "")

					val characters = Characters(Lists.characters)
					characters.chronologicalIndexSort()
					rowData.add(RowData(boldStyle, characterNames)) // creates row made of character names

					val spells = Spells()
					spells.addUsefulModels(Lists.spells, false)
					spells.chronologicalIndexSort()

					// this loop creates a row for each spell
					spells.models.forEach {
						if (!characters.charactersCanlearnSpell(it.gameIndex)) return@forEach
						val data = mutableListOf<String>()
						data.add(it.name)

						// this loop fills the level a character learns the spell for each character
						(0..Characters.characterListSize - 1).forEach { i ->
							val character = characters.models[i]
							val Dcharacter = characters.dModels[i]
							data.add(CompareValues(Dcharacter.getSpellLevel(it.gameIndex), character.getSpellLevel(it.gameIndex)))
						}
						rowData.add(RowData(defaultStyle, data))
					}
					writeDataToSheet(sheet, rowData)
	}
}
