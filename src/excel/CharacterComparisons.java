package excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import models.Spell;
import models.lists.Characters;
import models.lists.Lists;
import models.lists.Spells;

public class CharacterComparisons
{
    public static void makeChangeList()
    {
	try (XSSFWorkbook workbook = new XSSFWorkbook(); OutputStream fileOut = Excel.createOutputStream("Character Comparisons.xlsx"))
	{

	    XSSFSheet sheet = workbook.createSheet("Spell Comparisons");

	    XSSFCellStyle defaultStyle = Excel.setDefaultStyle(workbook);
	    XSSFCellStyle boldStyle = Excel.setBoldStyle(workbook);

	    List<RowData> rowData = new ArrayList<RowData>();

	    String[] characterNames = new String[8];
	    characterNames[0] = "";

	    Characters characters = new Characters(Lists.getCharacters());
	    characters.chronologicalIndexSort();

	    for (int i = 0; i < Characters.characterListSize; i++)
	    {
		characterNames[i + 1] = characters.getModels().get(i).getName();
	    }
	    rowData.add(new RowData(boldStyle, characterNames)); // creates row made of character names

	    Spells spells = new Spells();
	    spells.addUsefulModels(Lists.getSpells(), false);
	    spells.sortSpellsByType();

	    for (int i = 0; i < spells.getModels().size(); i++) // this loop creates a row for each spell
	    {

		Spell spell = spells.getModels().get(i);

		if (!characters.charactersCanlearnSpell(spell.getGameIndex()))
		{
		    continue;
		}

		List<String> data = new ArrayList<String>();

		data.add(spells.getModels().get(i).getName());

		for (int k = 0; k < Characters.characterListSize; k++) // this loop fills the level a character learns the spell for each character
		{
		    models.Character character = characters.getModels().get(k);
		    models.Character Dcharacter = characters.getDModels().get(k);
		    data.add(Excel.CompareValues(Dcharacter.getSpellLevel(spell.getGameIndex()), character.getSpellLevel(spell.getGameIndex())));
		}

		String[] array = new String[data.size()];
		data.toArray(array);
		rowData.add(new RowData(defaultStyle, array));
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
