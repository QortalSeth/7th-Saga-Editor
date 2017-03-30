package application.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import application.controllers.MonsterEditor;
import application.models.AbstractItem;
import application.models.Monster;
import application.models.lists.AbstractItems;
import application.models.lists.DropTables;
import application.models.lists.Lists;
import application.models.lists.Monsters;
import application.models.lists.Spells;

public class MonsterData
{
    public static void makeChangeList()
    {
	try (XSSFWorkbook workbook = new XSSFWorkbook(); OutputStream fileOut = Excel.createOutputStream("Monster.xlsx"))
	{
	    XSSFSheet sheet = workbook.createSheet("Monster Data");
	    XSSFCellStyle defaultStyle = Excel.setDefaultStyle(workbook);
	    XSSFCellStyle boldStyle = Excel.setBoldStyle(workbook);

	    List<RowData> rowData = new ArrayList<RowData>();

	    Monsters monsters = new Monsters(Lists.getMonsters(), false);

	    if (MonsterEditor.isChronologicalOrderSelected())
	    {
		monsters.chronologicalIndexSort();
	    }

	    rowData.add(new RowData(boldStyle, new String[]
	    {
		    "Stats", "", "Resists", "", "Spells", "", "Drops"
	    }));
	    rowData.add(new RowData(boldStyle, new String[]
	    {
		    ""
	    }));

	    Spells spells = new Spells(Lists.getSpells(), true);
	    DropTables dropTables = new DropTables(Lists.getDropTables());
	    AbstractItems<AbstractItem> abstractItems = new AbstractItems<>(Lists.getAbstractItems(), true);

	    for (int i = 0; i < monsters.getModels().size(); i++)
	    {
		Monster m = monsters.getModels().get(i);
		Monster Dm = monsters.getDModel(m);

		List<String> spellNames = spells.getNames(m.getSpells());
		List<String> DspellNames = spells.getDNames(Dm.getSpells());

		List<String> spellChances = m.getSpellChanceStrings();
		List<String> DspellChances = Dm.getSpellChanceStrings();

		List<String> itemDropNames = abstractItems.getNames(dropTables.getModels().get(m.getItemDropSet()));
		List<String> DitemDropNames = abstractItems.getDNames(dropTables.getDModels().get(Dm.getItemDropSet()));

		int index = 0;
		rowData.add(new RowData(defaultStyle, new String[]
		{
			m.getName()
		}));

		rowData.add(new RowData(defaultStyle, new String[]
		{
			"HP", Excel.CompareValues(Dm.getHp(), m.getHp()), "Boss Bit", Excel.CompareValues(Dm.getRunFlag(), m.getRunFlag()),
			Excel.CompareValues(DspellNames.get(index), spellNames.get(index)), Excel.CompareValues(DspellChances.get(index), spellChances.get(index)),
			Excel.CompareValues(Excel.Pget(DitemDropNames, index), Excel.Pget(itemDropNames, index++))
		}));
		rowData.add(new RowData(defaultStyle, new String[]
		{
			"MP", Excel.CompareValues(Dm.getMp(), m.getMp()), "Lightning", Excel.CompareValues(Dm.getLaserRes(), m.getLaserRes()),
			Excel.CompareValues(DspellNames.get(index), spellNames.get(index)), Excel.CompareValues(DspellChances.get(index), spellChances.get(index)),
			Excel.CompareValues(Excel.Pget(DitemDropNames, index), Excel.Pget(itemDropNames, index++))
		}));
		rowData.add(new RowData(defaultStyle, new String[]
		{
			"Power", Excel.CompareValues(Dm.getPower(), m.getPower()), "Unknown", Excel.CompareValues(Dm.getUnknownRes1(), m.getUnknownRes1()),
			Excel.CompareValues(DspellNames.get(index), spellNames.get(index)), Excel.CompareValues(DspellChances.get(index), spellChances.get(index)),
			Excel.CompareValues(Excel.Pget(DitemDropNames, index), Excel.Pget(itemDropNames, index++))
		}));
		rowData.add(new RowData(defaultStyle, new String[]
		{
			"Guard", Excel.CompareValues(Dm.getGuard(), m.getGuard()), "Unknown2", Excel.CompareValues(Dm.getUnknownRes2(), m.getUnknownRes2()),
			Excel.CompareValues(DspellNames.get(index), spellNames.get(index)), Excel.CompareValues(DspellChances.get(index), spellChances.get(index)),
			Excel.CompareValues(Excel.Pget(DitemDropNames, index), Excel.Pget(itemDropNames, index++))
		}));
		rowData.add(new RowData(defaultStyle, new String[]
		{
			"Magic", Excel.CompareValues(Dm.getMagic(), m.getMagic()), "Fire", Excel.CompareValues(Dm.getFireRes(), m.getFireRes()),
			Excel.CompareValues(DspellNames.get(index), spellNames.get(index)), Excel.CompareValues(DspellChances.get(index), spellChances.get(index)),
			Excel.CompareValues(Excel.Pget(DitemDropNames, index), Excel.Pget(itemDropNames, index++))
		}));
		rowData.add(new RowData(defaultStyle, new String[]
		{
			"Speed", Excel.CompareValues(Dm.getSpeed(), m.getSpeed()), "Ice", Excel.CompareValues(Dm.getIceRes(), m.getIceRes()),
			Excel.CompareValues(DspellNames.get(index), spellNames.get(index)), Excel.CompareValues(DspellChances.get(index), spellChances.get(index)),
			Excel.CompareValues(Excel.Pget(DitemDropNames, index), Excel.Pget(itemDropNames, index++))
		}));

		rowData.add(new RowData(defaultStyle, new String[]
		{
			"Gold", Excel.CompareValues(Dm.getGold(), m.getGold()), "Vacuum", Excel.CompareValues(Dm.getVacuumRes(), m.getVacuumRes()),
			Excel.CompareValues(DspellNames.get(index), spellNames.get(index)), Excel.CompareValues(DspellChances.get(index), spellChances.get(index)),
			Excel.CompareValues(Excel.Pget(DitemDropNames, index), Excel.Pget(itemDropNames, index++))
		}));
		rowData.add(new RowData(defaultStyle, new String[]
		{
			"Exp", Excel.CompareValues(Dm.getExperience(), m.getExperience()), "Debuff", Excel.CompareValues(Dm.getDebuffRes(), m.getDebuffRes()),
			Excel.CompareValues(DspellNames.get(index), spellNames.get(index)), Excel.CompareValues(DspellChances.get(index), spellChances.get(index)),
			Excel.CompareValues(Excel.Pget(DitemDropNames, index), Excel.Pget(itemDropNames, index++))
		}));
		rowData.add(new RowData(defaultStyle, new String[]
		{
			""
		}));
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
