package application.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import application.enums.EquipmentType;
import application.models.Armor;
import application.models.Weapon;
import application.models.lists.AbstractItems;
import application.models.lists.Armors;
import application.models.lists.Lists;
import application.models.lists.Shops;
import application.models.lists.Weapons;
import application.staticClasses.TextReader;

public class EquipmentData
{
    public static void makeChangeList()
    {

	try (XSSFWorkbook workbook = new XSSFWorkbook(); OutputStream fileOut = Excel.createOutputStream("Equipment.xlsx"))
	{

	    XSSFSheet weaponsSheet = workbook.createSheet("Weapon Data");
	    XSSFSheet armorSheet = workbook.createSheet("Armor Data");

	    XSSFCellStyle defaultStyle = Excel.setDefaultStyle(workbook);
	    XSSFCellStyle boldStyle = Excel.setBoldStyle(workbook);

	    List<RowData> rowData = fillWeaponSheet(defaultStyle, boldStyle);
	    Excel.writeDataToSheet(weaponsSheet, rowData);
	    rowData = fillArmorSheet(defaultStyle, boldStyle);
	    Excel.writeDataToSheet(armorSheet, rowData);

	    workbook.write(fileOut);
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}

    }

    private static List<RowData> fillWeaponSheet(XSSFCellStyle defaultStyle, XSSFCellStyle boldStyle)
    {

	Weapons weapons = new Weapons(Lists.getWeapons());

	List<RowData> rowData = new ArrayList<RowData>();

	Weapons axes = new Weapons(Lists.getWeapons().getDModels());
	;
	Weapons knightSwords = new Weapons(weapons.getDModels());
	Weapons swords = new Weapons(weapons.getDModels());
	Weapons knives = new Weapons(weapons.getDModels());
	Weapons rods = new Weapons(weapons.getDModels());
	Weapons fists = new Weapons(weapons.getDModels());
	Weapons misc = new Weapons(weapons.getDModels());

	for (Weapon w : weapons.getModels())
	{
	    switch (EquipmentType.getType(w)) {
		case SWORD:
		    if (w.getEquipCode() == 01)
		    {
			knightSwords.getModels().add(w);
		    }
		    else if ((w.getEquipCode() & 0x08) > 0) // if only Wilme can equip it
		    {
			misc.getModels().add(w);
		    }
		    else
			swords.getModels().add(w);
		    break;
		case AXE:
			axes.getModels().add(w);
		    break;

		case SABER:
		    rods.getModels().add(w);
		    break;
		case ROD:
		    rods.getModels().add(w);
		    break;
		case KNIFE:
		    knives.getModels().add(w);
		    break;
		case HAND:
		    fists.getModels().add(w);
		    break;
		default:
		    misc.getModels().add(w);
		    break;
	    }
	}

	rowData.add(new RowData(defaultStyle, new String[]
	{
		"Name", "Power", "Cost", "Discount", "Equip", "Locations", "Default Locations", "Name Pointer"
	}));
	rowData.add(new RowData(defaultStyle, new String[]
	{
		""
	}));

	Shops shops = new Shops();
	shops.addUsefulModels(Lists.getShops(), false);
	shops.chronologicalIndexSort();

	rowData.add(new RowData(boldStyle, new String[]
	{
		"Knight Swords"
	}));
	addWeaponType(rowData, knightSwords, shops, defaultStyle);

	rowData.add(new RowData(boldStyle, new String[]
	{
		"Swords"
	}));
	addWeaponType(rowData, swords, shops, defaultStyle);

	rowData.add(new RowData(boldStyle, new String[]
	{
		"Axes"
	}));
	addWeaponType(rowData, axes, shops, defaultStyle);

	rowData.add(new RowData(boldStyle, new String[]
	{
		"Knives"
	}));
	addWeaponType(rowData, knives, shops, defaultStyle);
	rowData.add(new RowData(boldStyle, new String[]
	{
		"Rods"
	}));
	addWeaponType(rowData, rods, shops, defaultStyle);
	rowData.add(new RowData(boldStyle, new String[]
	{
		"Fists"
	}));
	addWeaponType(rowData, fists, shops, defaultStyle);
	rowData.add(new RowData(boldStyle, new String[]
	{
		"Misc."
	}));
	addWeaponType(rowData, misc, shops, defaultStyle);
	return rowData;
    }

    private static void addWeaponType(List<RowData> rowData, Weapons weapons, Shops shops, XSSFCellStyle defaultStyle)
    {
	weapons.sortList((Comparator<Weapon>) application.models.lists.Equipment.ascendingComparator);

	for (Weapon w : weapons.getModels())
	{
	    Weapon Dw = weapons.getDModel(w);

	    String namePointer = Excel.CompareValues(TextReader.pointerToText(Dw.getNamePointer()), TextReader.pointerToText(w.getNamePointer()));

	    rowData.add(new RowData(defaultStyle, new String[]
	    {
		    Excel.CompareValues(Dw.getName(), w.getName()), Excel.CompareValues(Dw.getPower(), w.getPower()), Excel.CompareValues(Dw.getCost(), w.getCost()),
		    Excel.CompareValues(Dw.getDiscount(), w.getDiscount()), Excel.CompareValues(getEquipNames(Dw.getEquipCode()), getEquipNames(w.getEquipCode())),
		    AbstractItems.getLocations(shops.getModels(), Lists.getCharacters().getModels(), w.getItemCode()),
		    AbstractItems.getLocations(shops.getDModels(), Lists.getCharacters().getDModels(), Dw.getItemCode()), namePointer
	    }));
	}
	rowData.add(new RowData(defaultStyle, new String[]
	{
		""
	}));
    }

    private static List<RowData> fillArmorSheet(XSSFCellStyle defaultStyle, XSSFCellStyle boldStyle)
    {
	List<RowData> rowData = new ArrayList<RowData>();
	Armors armors = new Armors(Lists.getArmors());
	Armors bodyArmors = new Armors(armors.getDModels());
	Armors robes = new Armors(armors.getDModels());
	Armors coats = new Armors(armors.getDModels());
	Armors miscArmors = new Armors(armors.getDModels());
	Armors shields = new Armors(armors.getDModels());
	Armors accessories = new Armors(armors.getDModels());

	for (Armor a : armors.getModels())
	{
	    switch (EquipmentType.getType(a)) {
		case MAIL:
		    bodyArmors.getModels().add(a);
		    break;
		case ARMOR:
		    bodyArmors.getModels().add(a);
		    break;
		case CLOAK:
		    if (a.getEquipCode() == 8)
		    {
			miscArmors.getModels().add(a);
		    }
		    else
		    {
			robes.getModels().add(a);
		    }
		    break;
		case ROBE:
		    robes.getModels().add(a);
		    break;
		case SHIELD:
		    shields.getModels().add(a);
		    break;
		default:
		    if (a.isCoat())
		    {
			coats.getModels().add(a);
			break;
		    }
		    else if (a.isBodyArmor())
		    {
			miscArmors.getModels().add(a);
			break;
		    }
		    else
		    {
			accessories.getModels().add(a);
			break;
		    }
	    }
	}

	rowData.add(new RowData(defaultStyle, new String[]
	{
		"Name", "Defense", "Cost", "Discount", "Equip", "Lit", "???", "???", "Fire", "Ice", "Vac", "Deb", "Locations", "Default Locations", "Misc."
	}));
	rowData.add(new RowData(defaultStyle, new String[]
	{
		""
	}));

	Shops shops = new Shops(Lists.getShops());
	shops.chronologicalIndexSort();

	rowData.add(new RowData(boldStyle, new String[]
	{
		"Armors"
	}));
	addArmorType(rowData, bodyArmors, shops, defaultStyle);
	rowData.add(new RowData(boldStyle, new String[]
	{
		"Robes"
	}));
	addArmorType(rowData, robes, shops, defaultStyle);
	rowData.add(new RowData(boldStyle, new String[]
	{
		"Coats"
	}));
	addArmorType(rowData, coats, shops, defaultStyle);
	rowData.add(new RowData(boldStyle, new String[]
	{
		"Misc. Armors"
	}));
	addArmorType(rowData, miscArmors, shops, defaultStyle);
	rowData.add(new RowData(boldStyle, new String[]
	{
		"Shields"
	}));
	addArmorType(rowData, shields, shops, defaultStyle);
	rowData.add(new RowData(boldStyle, new String[]
	{
		"Accessories"
	}));
	addArmorType(rowData, accessories, shops, defaultStyle);
	return rowData;
    }

    private static void addArmorType(List<RowData> rowData, Armors armors, Shops shops, XSSFCellStyle defaultStyle)
    {
	armors.sortList((Comparator<Armor>) application.models.lists.Equipment.ascendingComparator);
	for (Armor a : armors.getModels())
	{
	    Armor Da = armors.getDModel(a);

	    String miscData = "";

	    if (a.getNamePointer() != Da.getNamePointer())
	    {
		miscData = "Name Pointer Changed to " + TextReader.pointerToText(a.getNamePointer());
	    }

	    rowData.add(new RowData(defaultStyle, new String[]
	    {
		    Excel.CompareValues(Da.getName(), a.getName()), Excel.CompareValues(Da.getPower(), a.getPower()), Excel.CompareValues(Da.getCost(), a.getCost()),
		    Excel.CompareValues(Da.getDiscount(), a.getDiscount()), Excel.CompareValues(getEquipNames(Da.getEquipCode()), getEquipNames(a.getEquipCode())),
		    Excel.CompareValues(Da.getLaserRes(), a.getLaserRes()), Excel.CompareValues(Da.getUnknownRes1(), a.getUnknownRes1()), Excel.CompareValues(Da.getUnknownRes2(), a.getUnknownRes2()),
		    Excel.CompareValues(Da.getFireRes(), a.getFireRes()), Excel.CompareValues(Da.getIceRes(), a.getIceRes()), Excel.CompareValues(Da.getVacuumRes(), a.getVacuumRes()),
		    Excel.CompareValues(Da.getDebuffRes(), a.getDebuffRes()), AbstractItems.getLocations(shops.getModels(), Lists.getCharacters().getModels(), a.getItemCode()),
		    AbstractItems.getLocations(shops.getDModels(), Lists.getCharacters().getDModels(), Da.getItemCode()), miscData
	    }));
	}
	rowData.add(new RowData(defaultStyle, new String[]
	{
		""
	}));
    }

    public static String getEquipNames(int equipCode)
    {
	StringBuilder sb = new StringBuilder();

	if ((equipCode & 0x01) > 0)
	{
	    sb.append("K");
	}
	if ((equipCode & 0x02) > 0)
	{
	    sb.append("O");
	}
	if ((equipCode & 0x04) > 0)
	{
	    sb.append("E");
	}
	if ((equipCode & 0x08) > 0)
	{
	    sb.append("W");
	}
	if ((equipCode & 0x10) > 0)
	{
	    sb.append("X");
	}
	if ((equipCode & 0x20) > 0)
	{
	    sb.append("V");
	}
	if ((equipCode & 0x40) > 0)
	{
	    sb.append("L");
	}
	return sb.toString();
    }
}
