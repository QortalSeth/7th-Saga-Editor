package application.excel

import application.models.lists.Characters
import application.models.lists.Lists
import application.models.lists.Spells
import org.apache.poi.xssf.usermodel.XSSFSheet




class CharacterComparisons(fileName: String) : Excel(fileName)
{
	override fun makeChangeList()
	{
					val sheet = workbook.createSheet("Spell Comparisons")
					val sheetData = SheetData()
					var characterNames = mutableListOf<String>()
					characterNames.add("")
					characterNames.addAll(Characters.characterNamesByClassOrder)

					val characters = Characters(Lists.characters)
					characters.chronologicalIndexSort()
					sheetData.addRow(RowData(boldStyle, characterNames)) // creates row made of character names
					sheetData.addRow(RowData(defaultStyle, mutableListOf("")))

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
						sheetData.addRow(RowData(defaultStyle, data))
					}
					sheet.createFreezePane(1,1,1,1)

					sheetData.setColumnStyle(boldStyle, 0)
					writeDataToSheet(sheet, sheetData)
					(sheet as XSSFSheet).columnHelper.setColDefaultStyle(1, boldStyle)
	}
}
