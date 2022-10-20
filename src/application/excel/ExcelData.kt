package application.excel

import org.apache.poi.xssf.usermodel.XSSFCellStyle

class SheetData()
{
    var rows: MutableList<RowData> = mutableListOf()



    fun addRow(rowData: RowData)
    {
    rows.add(rowData)
    }

    fun setColumnStyle(style: XSSFCellStyle, column: Int)
    {
        rows.forEach{
            try {
                it.cells[column].style = style
            }
            catch(e: IndexOutOfBoundsException)
            {
                println("""Column: ${column.toString()} empty""")
            }
        }
    }
}


class RowData(defaultStyle: XSSFCellStyle, text: MutableList<String>){
    var cells: MutableList<CellData> = mutableListOf()

    init{
        text.forEach {
            cells.add(CellData(defaultStyle, it))
        }
    }


}

class CellData(var style: XSSFCellStyle, var text: String){}



