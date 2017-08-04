package application.excel

import org.apache.poi.xssf.usermodel.XSSFCellStyle

class RowData(val style: XSSFCellStyle, val text: MutableList<String>)
