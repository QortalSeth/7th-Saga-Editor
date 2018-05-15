package application.models

import application.ROM
import application.enums.MonsterType
import application.staticClasses.TextReader
import java.io.Serializable
import java.util.*

class Monster : Model, Serializable
{
	var unknown1: Int = 0
	var hp: Int = 0
	var mp: Int = 0
	var power: Int = 0
	var guard: Int = 0
	var magic: Int = 0
	var speed: Int = 0
	val spells = mutableListOf<Int>()
	val spellChance = mutableListOf<Int>()
	var laserRes: Int = 0
	var unknownRes1: Int = 0
	var unknownRes2: Int = 0
	var fireRes: Int = 0
	var iceRes: Int = 0
	var vacuumRes: Int = 0
	var debuffRes: Int = 0
	var gold: Int = 0
	var itemDropSet: Int = 0
	var unknown2: Int = 0
	var runFlag: Int = 0

	var aliases = mutableListOf<Monster>()

	constructor(index: Int) : super(index)
	{
		chronologicalIndex = MonsterType.getChronOrder(this).ordinal
	}

	fun getValuesFromROM()
	{
		ROM.offset = baseOffset + bytesPerMonster * gameIndex

		unknown1 = ROM.nextByte
		hp = ROM.nextShort
		mp = ROM.nextShort
		power = ROM.nextShort
		guard = ROM.nextShort
		magic = ROM.nextByte
		speed = ROM.nextByte

		for (i in 0..7) spells.add(ROM.nextByte)

		for (i in 0..7) spellChance.add(ROM.nextByte)

		laserRes = ROM.nextByte
		unknownRes1 = ROM.nextByte
		unknownRes2 = ROM.nextByte
		fireRes = ROM.nextByte
		iceRes = ROM.nextByte
		vacuumRes = ROM.nextByte
		debuffRes = ROM.nextByte

		gold = ROM.nextShort
		itemDropSet = ROM.nextByte
		unknown2 = ROM.nextByte
		runFlag = ROM.nextByte
		namePointer = ROM.nextTriple
		name = TextReader.readText(namePointer)


		var magicMod = 0
		var speedMod = 0

		val mod = power shr 8
		power = power and 0x03FF

		if (mod and 0x04 > 0) magicMod += 256
		if (mod and 0x08 > 0) magicMod += 512
		if (mod and 0x10 > 0) speedMod += 256
		if (mod and 0x20 > 0) speedMod += 512
		magic += magicMod
		speed += speedMod
	}

	fun writeValuesToROM()
	{
		ROM.offset = baseOffset + bytesPerMonster * gameIndex
		ROM.nextByte = unknown1
		ROM.nextShort = hp
		ROM.nextShort = mp

		var magicValue = magic
		var speedValue = speed
		var powerValue = power

		var mod = 0
		if (magicValue >= 512)
		{
			magicValue -= 512
			mod += 0x0800
		}

		if (magicValue >= 256)
		{
			magicValue -= 256
			mod += 0x0400
		}

		if (speedValue >= 512)
		{
			speedValue -= 512
			mod += 0x2000
		}

		if (speedValue >= 256)
		{
			speedValue -= 256
			mod += 0x1000
		}

		powerValue = powerValue or mod
		ROM.nextShort = powerValue
		ROM.nextShort = guard
		ROM.nextByte = magicValue
		ROM.nextByte = speedValue

		(0..7).forEach { ROM.nextByte = spells[it] }
		(0..7).forEach { ROM.nextByte = spellChance[it] }

		ROM.nextByte = laserRes
		ROM.nextByte = unknownRes1
		ROM.nextByte = unknownRes2
		ROM.nextByte = fireRes
		ROM.nextByte = iceRes
		ROM.nextByte = vacuumRes
		ROM.nextByte = debuffRes
		ROM.nextShort = gold
		ROM.nextByte = itemDropSet
		ROM.nextByte = unknown2
		ROM.nextByte = runFlag
		ROM.nextTriple = namePointer
	}

	override fun equals(o: Any?): Boolean
	{
		if (o == null) return false
		val m = o as Monster?
//		if(name.trim().isEmpty() || m!!.name.trim().isEmpty()) return false  this line causes a bug where empty values cannot be selected
		return name == m!!.name
	}

	val spellChanceStrings: List<String>
		get()
		{
			val spellChances = ArrayList<String>()
			spellChance.forEach {
				if (it > 0) spellChances.add(Integer.toString(it))
				else spellChances.add("")
			}
			return spellChances
		}

	val experience: Int
		get() = (gold * goldToExpRatio).toInt()


	constructor(m: Monster) : super(m)
	{
		this.unknown1 = m.unknown1
		this.hp = m.hp
		this.mp = m.mp
		this.power = m.power
		this.guard = m.guard
		this.magic = m.magic
		this.speed = m.speed

		m.spells.forEach { spells.add(it) }
		m.spellChance.forEach { spellChance.add(it) }

		this.laserRes = m.laserRes
		this.unknownRes1 = m.unknownRes1
		this.unknownRes2 = m.unknownRes2
		this.fireRes = m.fireRes
		this.iceRes = m.iceRes
		this.vacuumRes = m.vacuumRes
		this.debuffRes = m.debuffRes
		this.gold = m.gold
		this.itemDropSet = m.itemDropSet
		this.unknown2 = m.unknown2
		this.runFlag = m.runFlag
		this.namePointer = m.namePointer
		this.name = m.name
		this.gameIndex = m.gameIndex
		this.chronologicalIndex = m.chronologicalIndex

		m.aliases.forEach { aliases.add(Monster(it)) }
	}

	companion object
	{
		private val goldToExpRatio = 2.203120312031203
		private val baseOffset = 0x72F4
		private val bytesPerMonster = 42
		fun goldToExp(value: Int): Int = (value * goldToExpRatio).toInt()
	}

}
