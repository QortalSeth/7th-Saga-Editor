package application.enums

import application.models.Item

enum class ItemType
{
	EMPTY,
	POTN_1, POTN_2, POTN_3, RECVRY, M_HERB_1, M_HERB_2, ANTID, M_WATER,
	EXIGATE, WINBALL, B_PRTCT, B_POWER, B_AGLTY,
	B_FIRE, B_ICE, B_FOSSL, MSQUITO, M_SIPHN, S_DSTRY, VACUUM, MIRROR, HARP,


	MAP, WATR_RN, WIND_RN, STAR_RN, MOON_RN, LGHT_RN, SKY_RN, WZRD_RN,
	V_SEED, M_SEED, P_SEED, PR_SEED, I_SEED, A_SEED, OPAL, PEARL,
	TOPAZ, RUBY, SAPHR, EMRLD, DMND, WHSTLE, MN_LIGHT, KEY_BRI,
	RMTCNTL, JL_KEY, LETTER, KY_ERTH, CRY_PCE, STAR, WATR_RN2, WIND_RN2,
	STAR_RN2, MOON_RN2, LGHT_RN2, SKY_RN2, WZRD_RN2, MISC;

	companion object
	{
		fun getChronOrder(item: Item): ItemType
				= when (item.itemCode)
		{
			0    -> EMPTY
			0x01 -> EXIGATE
			0x02 -> WINBALL
			0x0B -> POTN_1
			0x0C -> POTN_2
			0x0D -> POTN_3
			0x11 -> M_HERB_1
			0x12 -> M_HERB_2
			0x13 -> ANTID
			0x14 -> M_WATER
			0x15 -> RECVRY
			0x29 -> B_POWER
			0x2D -> MSQUITO
			0x2E -> M_SIPHN
			0x30 -> B_FIRE
			0x32 -> B_ICE
			0x34 -> B_FOSSL
			0x35 -> B_AGLTY
			0x38 -> B_PRTCT
			0x39 -> S_DSTRY
			0x3A -> VACUUM
			0x43 -> MIRROR
			0x44 -> HARP
			else -> MISC
		}


	}

}