package application.excel;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public class RowData 
{
	private XSSFCellStyle style;
	private String[] text;

	public RowData(XSSFCellStyle style, String[] text) 
	{
		super();
		this.style = style;
		this.text = text;
	}

	public XSSFCellStyle getStyle() {return style;}
	public String[] getText() {return text;}
}
