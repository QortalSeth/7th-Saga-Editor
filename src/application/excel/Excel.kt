package application.excel

import application.ROM
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Integer.toString

open class Excel(var fileName: String)
{
	val workbook = XSSFWorkbook()
	val defaultStyle = workbook.createCellStyle()
	val boldStyle = workbook.createCellStyle()

	init
	{
		setDefaultStyle()
		setBoldStyle()
		saveData()
	}

	fun setDefaultStyle()
	{
		val defaultFont = workbook.createFont()
		defaultFont.fontHeightInPoints = 10.toShort()
		defaultFont.fontName = "Times New Roman"
		defaultFont.bold = false
		defaultStyle.setFont(defaultFont)
	}

	fun setBoldStyle()
	{
		val boldFont = workbook.createFont()
		boldFont.fontHeightInPoints = 12.toShort()
		boldFont.fontName = "Times New Roman"
		boldFont.bold = true
		boldStyle.setFont(boldFont)
	}

	fun CompareValues(a: Int, b: Int): String
	{
		if (a == b && a >= 0) return toString(a)

		val aString: String
		val bString: String

		if (a < 0) aString = ""
		else aString = toString(a)
		if (b < 0) bString = ""
		else bString = toString(b)

		if (a < 0 && b < 0) return ""

		return aString + "=>" + bString
	}

	fun CompareValues(a: String, b: String): String
	{
		if (a == b) return a
		else return a + "=>" + b
	}

	fun writeDataToSheet(sheet: XSSFSheet, rowData: List<RowData>)
	{
		rowData.indices.forEach { rowNum ->
			val data = rowData[rowNum].text
			val style = rowData[rowNum].style
			val row = sheet.createRow(rowNum)

			data.indices.forEach { cellNum ->
				val cell = row.createCell(cellNum)
				cell.row.height = 0.toShort()
				cell.cellStyle = style
				cell.setCellValue(data[cellNum])
			}
		}

		val columnsInSheet = calculateNumberOfColumnsInSheet(sheet)
		// System.out.println("number of columns is:
		// "+Integer.toString(columnsInSheet));
		for (i in 0..columnsInSheet - 1) sheet.autoSizeColumn(i)
	}

	fun calculateNumberOfColumnsInSheet(sheet: XSSFSheet): Int
	{
		var numberOfColumns = 0
		(0..sheet.lastRowNum - 1).forEach {
			val columnsInRow = sheet.getRow(it).lastCellNum.toInt()
			if (columnsInRow > numberOfColumns) numberOfColumns = columnsInRow
		}
		return numberOfColumns
	}

	fun modifyCase(s: String): String
	{
		if (s.isEmpty()) return ""
		val sb = StringBuilder(s.toLowerCase().trim { it <= ' ' })
		sb.setCharAt(0, Character.toUpperCase(s[0]))

		(0..sb.length - 1).forEach { if (Character.isWhitespace(sb[it])) sb.setCharAt(it + 1, Character.toUpperCase(sb[it + 1])) }

		return sb.toString()
	}

	fun Pget(t: List<String>, i: Int): String
	{
		if (i < t.size) return t[i]
		else return ""
	}

	open fun saveData()
	{
		makeChangeList()
		try
		{
			var file = ROM.changeListsDirectory
			println("Changelist Directory: " + file)
			file.mkdirs()

			file = File(file.path + "/" + fileName)
			println("Changelist File Location: " + file.absolutePath)
			file.createNewFile()
			BufferedOutputStream(FileOutputStream(file)).use {
				workbook.write(it)
			}
		}
		catch (e: IOException)
		{
			// TODO Auto-generated catch block
			e.printStackTrace()
		}
	}

	open fun makeChangeList()
	{
	}

}
