package application.excel

import application.models.lists.Lists

class ExpData(fileName: String) : Excel(fileName)
{
	override fun makeChangeList()
	{
		val sheet = workbook.createSheet("Experience Data")
		val rowData = mutableListOf<RowData>()
		val exp = Lists.experience
		//	val exp = Experience.getMarginalExpTable()
		//	val Dexp = Experience.defaultMarginalExpTable
		rowData.add(RowData(defaultStyle, mutableListOf("Level", "Exp to Next Level")))
		exp.marginalExpTable.indices.forEach { rowData.add(RowData(defaultStyle, mutableListOf(Integer.toString(it + 1), CompareValues(exp.defaultMarginalExpTable[it], exp.marginalExpTable[it])))) }
		writeDataToSheet(sheet, rowData)
	}
}
