package models.lists;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import controllers.MainMenu;
import enums.EquipmentType;

public class Equipment<T extends models.Equipment> extends AbstractItems<T> implements Serializable
{
	public Equipment()
	{
		super();
	}

	public Equipment(Equipment<T> addFrom, int equipCode)
	{
		super();
		addUsefulModels(addFrom, equipCode);
	}

	public Equipment(Equipment<T> addFrom)
	{
		super();
		addUsefulModels(addFrom, 0xFF);
	}

	public Equipment(List<Integer> itemCodes)
	{
		this.addEquipment(itemCodes);
	}

	public Equipment(DModelsList<T> DmodelsList)
	{
		super(DmodelsList);
	}

	public void addEmptyModel(List<T> models)
	{
		models.Equipment emptyEquipment = new models.Equipment(0);
		emptyEquipment.setGameIndex(0);
		emptyEquipment.setItemCode(0);
		emptyEquipment.setName("");
		emptyEquipment.setPower(0);
		emptyEquipment.setEquipCode(255);
		models.add(0, (T) emptyEquipment);
	}

	public void addUsefulModels(Equipment<T> addFrom, int equipCode)
	{
		for (models.Equipment equipment : addFrom.getModels())
		{
			if ((equipCode & equipment.getEquipCode()) > 0)
			{
				if (MainMenu.getShowEmptyValues() == true || !equipment.getName().isEmpty())
				{
					models.add((T) equipment);
				}
			}
		}

		Dmodels.addAll(addFrom.getDModels());
	}

	public void sortByAscendingPower()
	{
		sortList((Comparator<T>) Equipment.ascendingComparator);
	}

	public void sortByDescendingPower()
	{
		sortList((Comparator<T>) descendingComparator);
	}

	public void sortByAscendingType()
	{
		sortList((Comparator<T>) ascendingTypeComparator);
	}

	public void sortByDescendingType()
	{
		sortList((Comparator<T>) descendingTypeComparator);
	}

//	public static int getTypePrecedence(String equipmentName)
//	{
//		if (equipmentName.isEmpty()) { return 9999; }
//		String type = equipmentName.substring(0, 2);
//		switch(type)
//		{
//		//Weapons
//		case "SW":  return 1;
//		case "AX":  return 2;
//		case "KN":  return 3;
//		case "SB":  return 4;
//		case "RD":  return 4;
//		case "ST":  return 5;
//		case "HA":  return 6;
//
//		//Armors
//		case "ML":  return 7;
//		case "AR":  return 8;
//		case "CK":  return 9;
//		case "RB":  return 10;
//
//		//Accessories
//		case "SH":  return 11;
//		case "CR":  return 12;
//		case "AM":  return 13;
//		case "MK":  return 14;
//		case "HT":  return 15;
//
//		default : return 20;
//		}
//
//
//	}

	public <P extends Equipment<T>> P addEquipment(List<Integer> itemCodes)
	{

		Equipment<T> equipment = new Equipment<>();
		equipment.addUsefulModels((Equipment<T>) Lists.getWeapons(), 0xFF);
		equipment.addUsefulModels((Equipment<T>) Lists.getArmors(), 0xFF);
		equipment.addEmptyModel(equipment.getModels());

		for (Integer i : itemCodes)
		{
			models.add(equipment.getItem(i));
		}
		System.out.println("Item code " + itemCodes);
		return null;
	}

	public static final Comparator<? extends models.Equipment> ascendingComparator	= new Comparator<models.Equipment>()
	{
		@Override
		public int compare(models.Equipment e1, models.Equipment e2)
		{
			// if (e1.getItemCode() == 0) { return 1; }
			return e1.getPower() - e2.getPower();
		}
	};
	public static final Comparator<? extends models.Equipment> descendingComparator	= new Comparator<models.Equipment>()
	{
		@Override
		public int compare(models.Equipment e1, models.Equipment e2)
		{
			// if (e2.getItemCode() == 0) { return 1; }
			return e2.getPower() - e1.getPower();
		}
	};
	public static final Comparator<? extends models.Equipment> ascendingTypeComparator	= new Comparator<models.Equipment>()
	{
		@Override
		public int compare(models.Equipment e1, models.Equipment e2)
		{
			// if (e1.getItemCode() == 0) { return 1; }
			//int difference = getTypePrecedence(e1.getName()) - getTypePrecedence(e2.getName());
			int difference = EquipmentType.compareTo(e1, e2);
			if (difference == 0)
			{
				difference = e1.getPower() - e2.getPower();
				if(difference==0)
				{
					difference = e1.getCost()-e2.getCost();
				}
			}

			return difference;
		}
	};
	public static final Comparator<? extends models.Equipment> descendingTypeComparator	= new Comparator<models.Equipment>()
	{
		@Override
		public int compare(models.Equipment e1, models.Equipment e2)
		{
			// if (e2.getItemCode() == 0) { return 1; }
			//int difference = getTypePrecedence(e1.getName()) - getTypePrecedence(e2.getName());
			int difference = EquipmentType.compareTo(e1, e2);
			if (difference == 0)
			{
				difference = e2.getPower() - e1.getPower();

				if(difference==0)
				{
					difference = e2.getCost()-e1.getCost();
				}
			}

			return difference;
		}
	};

}
