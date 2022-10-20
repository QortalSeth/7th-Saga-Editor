package application.excel

import application.models.lists.Lists

class ExpData(fileName: String) : Excel(fileName)
{
	override fun makeChangeList()
	{
		val sheet = workbook.createSheet("Experience Data")
		val sheetData = SheetData()
		val exp = Lists.experience
		//	val exp = Experience.getMarginalExpTable()
		//	val Dexp = Experience.defaultMarginalExpTable
		sheetData.addRow(RowData(boldStyle, mutableListOf("Level", "Exp to Next Level")))
		sheetData.addRow(RowData(defaultStyle, mutableListOf("")))
		exp.marginalExpTable.indices.forEach { sheetData.addRow(RowData(defaultStyle, mutableListOf(Integer.toString(it + 1), CompareValues(exp.defaultMarginalExpTable[it], exp.marginalExpTable[it])))) }

		sheet.createFreezePane(0,2,0,2)
		sheetData.setColumnStyle(boldStyle, 0)
		writeDataToSheet(sheet, sheetData)
	}
}
