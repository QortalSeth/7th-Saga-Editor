package excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import models.Character;
import models.lists.Characters;
import models.lists.Lists;
import models.lists.Spells;

public class CharacterData
{
    public static void makeChangeList()
    {
	try (XSSFWorkbook workbook = new XSSFWorkbook(); OutputStream fileOut = Excel.createOutputStream("Character.xlsx"))
	{

	    XSSFSheet sheet = workbook.createSheet("Character Data");

	    XSSFCellStyle defaultStyle = Excel.setDefaultStyle(workbook);
	    XSSFCellStyle boldStyle = Excel.setBoldStyle(workbook);

	    List<RowData> rowData = new ArrayList<RowData>();

	    Characters characters = new Characters();
	    characters.addUsefulModels(Lists.getCharacters(), false);

	    for (int i = 0; i < Characters.characterListSize; i++)
	    {

		Character c = characters.getModels().get(i);
		Character Dc = characters.getDModels().get(i);

		rowData.add(new RowData(boldStyle, new String[]
		{
			c.getName()
		}));

		rowData.add(new RowData(defaultStyle, new String[]
		{
			"Stats", "HP", "MP", "Power", "Guard", "Magic", "Speed"
		}));
		rowData.add(new RowData(defaultStyle, new String[]
		{
			"Initial", Excel.CompareValues(Dc.getHpStart(), c.getHpStart()), Excel.CompareValues(Dc.getMpStart(), c.getMpStart()),
			Excel.CompareValues(Dc.getPowerStart(), c.getPowerStart()), Excel.CompareValues(Dc.getGuardStart(), c.getGuardStart()),
			Excel.CompareValues(Dc.getMagicStart(), c.getMagicStart()), Excel.CompareValues(Dc.getSpeedStart(), c.getSpeedStart())
		}));

		rowData.add(new RowData(defaultStyle, new String[]
		{
			"Level Up", Excel.CompareValues(Dc.getHpGrowth(), c.getHpGrowth()), Excel.CompareValues(Dc.getMpGrowth(), c.getMpGrowth()),
			Excel.CompareValues(Dc.getPowerGrowth(), c.getPowerGrowth()), Excel.CompareValues(Dc.getGuardGrowth(), c.getGuardGrowth()),
			Excel.CompareValues(Dc.getMagicGrowth(), c.getMagicGrowth()), Excel.CompareValues(Dc.getSpeedGrowth(), c.getSpeedGrowth()),
		}));

		rowData.add(new RowData(defaultStyle, new String[]
		{
			""
		}));

		List<String> spells = new ArrayList<String>();
		List<String> levels = new ArrayList<String>();

		List<String> spells2 = new ArrayList<String>();
		List<String> levels2 = new ArrayList<String>();

		spells.add("Spells");
		levels.add("Levels");

		spells2.add("Spells");
		levels2.add("Levels");

		Spells spellsList = Lists.getSpells();

		List<String> characterSpells = spellsList.getNames(c.getSpells());
		List<String> DCharacterSpells = spellsList.getDNames(Dc.getSpells());

		for (int j = 0; j < characterSpells.size(); j++)
		{
		    characterSpells.set(j, Excel.modifyCase(characterSpells.get(j)));
		}

		for (int j = 0; j < DCharacterSpells.size(); j++)
		{
		    DCharacterSpells.set(j, Excel.modifyCase(DCharacterSpells.get(j)));
		}

		for (int k = 0; k < characterSpells.size(); k++)
		{
		    if (k < DCharacterSpells.size())
		    {
			spells.add(Excel.CompareValues(DCharacterSpells.get(k), characterSpells.get(k)));
			levels.add(Excel.CompareValues(Dc.getSpellLevels().get(k), c.getSpellLevels().get(k)));
		    }
		    else
		    {
			spells.add(Excel.CompareValues("", characterSpells.get(k)));
			levels.add(Excel.CompareValues("", Integer.toString(c.getSpellLevels().get(k))));
		    }
		}

		while (spells.size() > 9)
		{
		    spells2.add(spells.remove(9));
		}
		while (levels.size() > 9)
		{
		    levels2.add(levels.remove(9));
		}

		rowData.add(new RowData(defaultStyle, spells.toArray(new String[spells.size()])));
		rowData.add(new RowData(defaultStyle, levels.toArray(new String[levels.size()])));

		if (spells2.size() > 1)
		{
		    rowData.add(new RowData(defaultStyle, new String[]
		    {
			    ""
		    }));
		    rowData.add(new RowData(defaultStyle, spells2.toArray(new String[spells2.size()])));
		    rowData.add(new RowData(defaultStyle, levels2.toArray(new String[levels2.size()])));

		}

		rowData.add(new RowData(defaultStyle, new String[]
		{
			""
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
