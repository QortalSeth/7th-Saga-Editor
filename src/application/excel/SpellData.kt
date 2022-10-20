package application.excel

import application.enums.SpellType
import application.models.Spell
import application.models.lists.Lists
import application.models.lists.Spells

class SpellData(fileName: String) : Excel(fileName)
{
    override fun makeChangeList() {
        val sheet = workbook.createSheet("Spell Data")
        val sheetData = SheetData()
        val spells = Spells(Lists.spells, false)
        spells.chronologicalIndexSort()

        sheetData.addRow(RowData(boldStyle, mutableListOf(
            "Name", "Targeting", "Power", "Cost",
            "Element", "Domain", "Unknown 1", "Unknown 2")))

        sheetData.addRow(RowData(defaultStyle, mutableListOf("")))

        var lineBreak = SpellType.AttackOne
        spells.models.forEach {
            val Dspell = spells.getDModel(it)

            if( lineBreak != it.spellType)
            {
                sheetData.addRow(RowData(defaultStyle, mutableListOf("")))
                lineBreak = it.spellType

            }
            sheetData.addRow(RowData(defaultStyle, mutableListOf(
            CompareValues(Dspell.name, it.name),
            CompareValues(Spell.targetingText[Dspell.target]!!, Spell.targetingText[it.target]!!),
            CompareValues(Dspell.power, it.power),
            CompareValues(Dspell.cost, it.cost),
            CompareValues(Spell.elementText[Dspell.element], Spell.elementText[it.element]),
            CompareValues(Spell.domainText[Dspell.domain], Spell.domainText[it.domain]),
            CompareValues(Dspell.unknown1, it.unknown1),
            CompareValues(Dspell.unknown2, it.unknown2)
)))

        }
        sheet.createFreezePane(0,2,0,2)
        sheetData.setColumnStyle(boldStyle, 0)
        writeDataToSheet(sheet, sheetData)
    }
}
