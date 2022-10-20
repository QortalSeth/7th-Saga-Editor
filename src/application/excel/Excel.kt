package application.excel

import application.ROM
import org.apache.poi.xssf.usermodel.XSSFCellStyle
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
	val defaultStyle: XSSFCellStyle = workbook.createCellStyle()
	val bigStyle: XSSFCellStyle = workbook.createCellStyle()
	val boldStyle: XSSFCellStyle = workbook.createCellStyle()
	val bigBoldStyle: XSSFCellStyle = workbook.createCellStyle()


	init
	{
		setDefaultStyle()
		setBigStyle()
		setBoldStyle()
		setBigBoldStyle()
		saveData()
	}

	private fun setDefaultStyle()
	{
		val defaultFont = workbook.createFont()
		defaultFont.fontHeightInPoints = 14.toShort()
		defaultFont.fontName = "Times New Roman"
		defaultFont.bold = false
		defaultStyle.setFont(defaultFont)
	}

	fun setBigStyle()
	{
		val boldFont = workbook.createFont()
		boldFont.fontHeightInPoints = 16.toShort()
		boldFont.fontName = "Times New Roman"
		boldFont.bold = false
		bigStyle.setFont(boldFont)
	}

	fun setBoldStyle()
	{
		val boldFont = workbook.createFont()
		boldFont.fontHeightInPoints = 14.toShort()
		boldFont.fontName = "Times New Roman"
		boldFont.bold = true
		boldStyle.setFont(boldFont)
	}
	fun setBigBoldStyle()
	{
		val boldFont = workbook.createFont()
		boldFont.fontHeightInPoints = 16.toShort()
		boldFont.fontName = "Times New Roman"
		boldFont.bold = true
		bigBoldStyle.setFont(boldFont)
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

	fun writeDataToSheet(sheet: XSSFSheet, sheetData: SheetData)
	{
		sheetData.rows.forEachIndexed {rowIndex, rowData ->
			val row = sheet.createRow(rowIndex)

			rowData.cells.forEachIndexed{ cellIndex, cellData ->
				val cell = row.createCell(cellIndex)
				cell.cellStyle = cellData.style
				cell.setCellValue(cellData.text)
			}
		}

		val columnsInSheet = calculateNumberOfColumnsInSheet(sheet)
		// System.out.println("number of columns is:
		// "+Integer.toString(columnsInSheet));
		for (i in 0 until columnsInSheet) sheet.autoSizeColumn(i)
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

	fun saveData()
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
