package application.enums

import application.models.Shop

enum class ShopType
{
	LEMELE, RABLESK, BONRO, ZELLIS,
	PELL, PATROF, BONE,
	DOWAINE, BELAINE, TELAINE, PADAL, PANG,
	PANDAM, BRUSH, TIFFANA, BILTHEM, POLASU,
	VALENCA, BUGASK, GUANTA,
	PHARANO, PASANDA, LIGENA, MELENAM2, PALSU, AIRSHIP,

	MELENAM, EYGUS, GUNTZ, LUZE, BARAN, GORFUN, GUANAS, GUANAS2, BIJENIA,
	BULANDO, LUZE2, EMPTY;

	companion object
	{
		fun getChronOrder(shop: Shop): ShopType
				= when (shop.gameIndex)
		{
			0    -> LEMELE
			0x01 -> RABLESK
			0x02 -> BONRO
			0x03 -> ZELLIS
			0x04 -> MELENAM
			0x05 -> EYGUS
			0x06 -> PELL
			0x07 -> GUNTZ
			0x08 -> PATROF
			0x09 -> BONE
			0x0A -> DOWAINE
			0x0B -> BELAINE
			0x0C -> TELAINE
			0x0D -> LUZE
			0x0E -> PANG
			0x0F -> PADAL
			0x10 -> BARAN
			0x11 -> POLASU
			0x12 -> TIFFANA
			0x13 -> BILTHEM
			0x14 -> PANDAM
			0x15 -> BRUSH
			0x16 -> VALENCA
			0x17 -> BUGASK
			0x18 -> EMPTY
			0x19 -> GUANTA
			0x1A -> GORFUN
			0x1B -> PHARANO
			0x1C -> PASANDA
			0x1D -> LIGENA
			0x1E -> GUANAS
			0x1F -> PALSU
			0x20 -> MELENAM2
			0x21 -> AIRSHIP
			0x22 -> GUANAS
			0x23 -> BIJENIA
			0x24 -> BULANDO
			0x25 -> LUZE2
			0x26 -> EMPTY
			0x27 -> EMPTY
			else -> EMPTY
		}
	}
}