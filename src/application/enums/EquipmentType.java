package application.enums;

import application.models.Equipment;

public enum EquipmentType {

	SWORD, AXE, SABER,KNIFE, ROD, STAFF, HAND, 
	MAIL, ARMOR,  ROBE, CLOAK, 
	SHIELD, CROWN, AMULET, MASK, HELMET, MISC, EMPTY;


	public static EquipmentType getType(Equipment type)
	{
		if(type.getName().isEmpty()){return EMPTY;}
		switch(type.getName().substring(0, 2))
		{
		//Weapons
		case "SW":  return SWORD;
		case "AX":  return AXE;
		case "SB":  return SABER;
		case "KN":  return KNIFE;
		case "RD":  return ROD;
		case "ST":  return STAFF;
		case "HA":  return HAND;

		//Armors
		case "ML":  return MAIL;
		case "AR":  return ARMOR;
		case "CK":  return CLOAK;
		case "RB":  return ROBE;

		//Accessories
		case "SH":  return SHIELD;
		case "CR":  return CROWN;
		case "AM":  return AMULET;
		case "MK":  return MASK;
		case "HT":  return HELMET;

		default : return MISC;
		}
	}

	public static int compareTo(Equipment e1, Equipment e2)
	{
		return getType(e1).compareTo(getType(e2));
	}

}
