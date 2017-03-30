package application.excel;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import application.controllers.MainMenu;

public class Excel
{

    public static XSSFCellStyle setDefaultStyle(XSSFWorkbook workbook)
    {
	XSSFFont defaultFont = workbook.createFont();
	XSSFCellStyle defaultStyle = workbook.createCellStyle();
	defaultFont.setFontHeightInPoints((short) 10);
	defaultFont.setFontName("Times New Roman");
	defaultFont.setBold(false);
	defaultStyle.setFont(defaultFont);

	return defaultStyle;
    }

    public static XSSFCellStyle setBoldStyle(XSSFWorkbook workbook)
    {
	XSSFCellStyle boldStyle = workbook.createCellStyle();
	XSSFFont boldFont = workbook.createFont();
	boldFont.setFontHeightInPoints((short) 12);
	boldFont.setFontName("Times New Roman");
	boldFont.setBold(true);
	boldFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	boldStyle.setFont(boldFont);

	return boldStyle;
    }

    public static OutputStream createOutputStream(String fileName)
    {

	try
	{
	    File file = new File(MainMenu.getProgramDirectory() + "/ChangeLists");
	    if (!file.exists())
	    {
		file.mkdir();
	    }
	    file = new File(MainMenu.getProgramDirectory() + "/ChangeLists/" + fileName);
	    System.out.println("File is: " + file.getAbsolutePath());
	    file.createNewFile();

	    return new BufferedOutputStream(new FileOutputStream(file));
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return null;
	}
    }

    public static String CompareValues(int a, int b)
    {
	if (a == b && a >= 0) return Integer.toString(a);

	String aString;
	String bString;

	if (a < 0)
	    aString = "";
	else
	    aString = Integer.toString(a);
	if (b < 0)
	    bString = "";
	else
	    bString = Integer.toString(b);

	if (a < 0 && b < 0) return "";

	return aString + "=>" + bString;
    }

    public static String CompareValues(String a, String b)
    {
	if (a.equals(b))
	    return a;
	else
	    return a + "=>" + b;
    }

    public static void writeDataToSheet(XSSFSheet sheet, List<RowData> rowData)
    {
	for (int rowNum = 0; rowNum < rowData.size(); rowNum++)
	{
	    String[] data = rowData.get(rowNum).getText();
	    XSSFCellStyle style = rowData.get(rowNum).getStyle();
	    XSSFRow row = sheet.createRow(rowNum);

	    for (int cellNum = 0; cellNum < data.length; cellNum++)
	    {
		XSSFCell cell = row.createCell(cellNum);
		cell.getRow().setHeight((short) 0);
		cell.setCellStyle(style);
		cell.setCellValue(data[cellNum]);
	    }
	}
	int columnsInSheet = calculateNumberOfColumnsInSheet(sheet);
	// System.out.println("number of columns is:
	// "+Integer.toString(columnsInSheet));
	for (int i = 0; i < columnsInSheet; i++)
	{
	    sheet.autoSizeColumn(i);
	}
    }

    public static int calculateNumberOfColumnsInSheet(XSSFSheet sheet)
    {
	int numberOfColumns = 0;
	for (int i = 0; i < sheet.getLastRowNum(); i++)
	{
	    int columnsInRow = sheet.getRow(i).getLastCellNum();
	    if (columnsInRow > numberOfColumns)
	    {
		numberOfColumns = columnsInRow;
	    }
	}

	return numberOfColumns;
    }

    public static String modifyCase(String s)
    {
	if (s.isEmpty()) { return ""; }
	StringBuilder sb = new StringBuilder(s.toLowerCase().trim());
	sb.setCharAt(0, Character.toUpperCase(s.charAt(0)));

	for (int i = 0; i < sb.length(); i++)
	{
	    if (Character.isWhitespace(sb.charAt(i)))
	    {
		sb.setCharAt(i + 1, Character.toUpperCase(sb.charAt(i + 1)));
	    }
	}

	return sb.toString();
    }

    public static String Pget(List<String> t, int i)
    {
	if (i < t.size())
	{
	    return t.get(i);
	}
	else
	{
	    return "";
	}
    }

    public static void makeChangeLists()
    {
	CharacterData.makeChangeList();
	EquipmentData.makeChangeList();
	CharacterComparisons.makeChangeList();
	ExpData.makeChangeList();
	MonsterData.makeChangeList();
	// ApprenticeData.makeChangeList();
	// DropTableData.makeChangeList();

	// ItemData.makeChangeList();

	// ShopData.makeChangeList();
	// SpellData.makeChangeList();

    }

}
