package application.models

import application.ROM
import application.models.lists.Characters
import java.io.Serializable
import java.util.*

class Character(nameP: String, gameIndex: Int) : Model(gameIndex), Serializable
{
	var hpStart: Int = 0
	var mpStart: Int = 0
	var powerStart: Int = 0
	var guardStart: Int = 0
	var magicStart: Int = 0
	var speedStart: Int = 0

	var hpGrowth: Int = 0
	var mpGrowth: Int = 0
	var powerGrowth: Int = 0
	var guardGrowth: Int = 0
	var magicGrowth: Int = 0
	var speedGrowth: Int = 0

	var weaponStart: Int = 0
	var armorStart: Int = 0
	var accessoryStart: Int = 0
	var expStart: Int = 0

	var spells = mutableListOf<Int>()
	var spellLevels = mutableListOf<Int>()

	val equipCode: Int

	init
	{
		name = nameP
		equipCode = Characters.equipCodes[gameIndex]
	}

	fun getValuesFromROM()
	{

		ROM.offset = baseoffset + bytespercharacter * gameIndex
		offset = ROM.offset

		hpStart = ROM.nextShort
		mpStart = ROM.nextShort
		powerStart = ROM.nextByte
		guardStart = ROM.nextByte
		magicStart = ROM.nextByte
		speedStart = ROM.nextByte

		hpGrowth = ROM.nextByte
		mpGrowth = ROM.nextByte
		powerGrowth = ROM.nextByte
		guardGrowth = ROM.nextByte
		magicGrowth = ROM.nextByte
		speedGrowth = ROM.nextByte

		weaponStart = ROM.nextByte
		armorStart = ROM.nextByte
		accessoryStart = ROM.nextByte
		expStart = ROM.nextByte

		ROM.offset = baseSpellOffset + bytesPerSpellList * gameIndex

		spells = ArrayList<Int>()
		spellLevels = ArrayList<Int>()

		for (i in 0..15)
		{
			spells.add(ROM.nextByte)
		}

		for (i in 0..15)
		{
			spellLevels.add(ROM.nextByte)
		}
	}

	fun writeValuesToROM()
	{

		ROM.offset = baseoffset + bytespercharacter * gameIndex
		ROM.nextShort = hpStart
		ROM.nextShort = mpStart
		ROM.nextByte = powerStart
		ROM.nextByte = guardStart
		ROM.nextByte = magicStart
		ROM.nextByte = speedStart
		ROM.nextByte = hpGrowth
		ROM.nextByte = mpGrowth
		ROM.nextByte = powerGrowth
		ROM.nextByte = guardGrowth
		ROM.nextByte = magicGrowth
		ROM.nextByte = speedGrowth
		ROM.nextByte = weaponStart
		ROM.nextByte = armorStart
		ROM.nextByte = accessoryStart
		ROM.nextByte = expStart


		ROM.offset = baseSpellOffset + bytesPerSpellList * gameIndex

		for (i in spells)
		{
			ROM.nextByte = i
		}

		for (i in spellLevels)
		{
			ROM.nextByte = i
		}
	}

	val equipName: String
		get()
		{
			if (this.name == "Lux") return "X"
			else return name.substring(0, 1)
		}

	fun canLearnSpell(spellID: Int?): Boolean = spells.contains(spellID)

	fun getSpellLevel(spellID: Int): Int
	{
		val index = spells.indexOf(spellID)
		if (index < 0) return -1
		else return spellLevels[index]
	}


	companion object
	{
		val baseoffset = 0x623F
		val bytespercharacter = 18
		private val baseSpellOffset = 0x62BD
		private val bytesPerSpellList = 32
	}
}
