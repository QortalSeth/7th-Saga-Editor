package application.enums

import application.models.Equipment

enum class EquipmentType
{

	SWORD, AXE, SABER, KNIFE, ROD,
	STAFF, HAND, MAIL, ARMOR, ROBE,
	CLOAK, SHIELD, CROWN, AMULET, MASK,
	HELMET, MISC, EMPTY;

	companion object
	{
		fun getType(type: Equipment): EquipmentType
		{
			if (type.name.isEmpty())
			{
				return EMPTY
			}
			when (type.name.substring(0, 2))
			{
			//Weapons
				"SW" -> return SWORD
				"AX" -> return AXE
				"SB" -> return SABER
				"KN" -> return KNIFE
				"RD" -> return ROD
				"ST" -> return STAFF
				"HA" -> return HAND

			//Armors
				"ML" -> return MAIL
				"AR" -> return ARMOR
				"CK" -> return CLOAK
				"RB" -> return ROBE

			//Accessories
				"SH" -> return SHIELD
				"CR" -> return CROWN
				"AM" -> return AMULET
				"MK" -> return MASK
				"HT" -> return HELMET
				else -> return MISC
			}
		}

		fun compareTo(e1: Equipment, e2: Equipment): Int
		{
			return getType(e1).compareTo(getType(e2))
		}
	}
}
