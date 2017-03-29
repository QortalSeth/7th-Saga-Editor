package excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import models.Experience;

public class ExpData
{

    public static void makeChangeList()
    {
	try (XSSFWorkbook workbook = new XSSFWorkbook(); OutputStream fileOut = Excel.createOutputStream("Experience.xlsx"))
	{

	    XSSFSheet sheet = workbook.createSheet("Experience Data");

	    XSSFCellStyle defaultStyle = Excel.setDefaultStyle(workbook);
	    XSSFCellStyle boldStyle = Excel.setBoldStyle(workbook);

	    List<RowData> rowData = new ArrayList<RowData>();

	    List<Integer> exp = Experience.getMarginalExpTable();
	    List<Integer> Dexp = Experience.getDefaultMarginalExpTable();
	    rowData.add(new RowData(defaultStyle, new String[]
	    {
		    "Level", "Exp to Next Level"
	    }));

	    for (int i = 0; i < exp.size(); i++)
	    {
		rowData.add(new RowData(defaultStyle, new String[]
		{
			Integer.toString(i + 1), Excel.CompareValues(Dexp.get(i), exp.get(i))
		}));

	    }
	    Excel.writeDataToSheet(sheet, rowData);
	    workbook.write(fileOut);
	}

	catch (IOException e)
	{
	    e.printStackTrace();
	}
    }

}
