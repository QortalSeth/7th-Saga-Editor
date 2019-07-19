package application.models

import application.ROM
import application.models.lists.Characters
import java.io.Serializable
import java.util.*

class Apprentice(gameIndex: Int) : Model(gameIndex), Serializable
{
	var equipmentAtLevels: MutableList<EquipmentAtLevel> = ArrayList()
	var dialogues = Array(apprenticeListSize) { IntArray(numOfDialogues) }
	val equipCode = Characters.equipCodes[gameIndex]
	var dialoguePointers = IntArray(numOfDialogues)

	init
	{
		name = Characters.characterNamesByGameIndex[gameIndex]
	}

	fun getValuesFromROM()
	{

		ROM.offset = baseOffset + bytesPerApprentice * gameIndex
		offset = ROM.offset
		for (i in 0..numOfDialogues - 1)
		{
			dialoguePointers[i] = ROM.nextTriple
		}
		name = Characters.characterNamesByGameIndex[gameIndex]



		for (i in 0..apprenticeListSize - 1)
		{
			for (k in 0..numOfDialogues - 1)
			{
				dialogues[i][k] = ROM.nextByte
			}
		}

		ROM.offset = baseEquipmentOffset + bytesPerEquipmentList * gameIndex
		for (i in 0..equipmentRowsPerApprentice - 1)
		{
			equipmentAtLevels.add(EquipmentAtLevel(ROM.nextByte, intArrayOf(ROM.nextByte, ROM.nextByte, ROM.nextByte)))
		}
	}

	fun writeValuesToROM()
	{
		ROM.offset = baseOffset + 18 + bytesPerApprentice * gameIndex

		for (i in 0..apprenticeListSize - 1)
		{
			for (k in 0..numOfDialogues - 1)
			{
				ROM.nextByte = dialogues[i][k]
			}
		}

		ROM.offset = baseEquipmentOffset + bytesPerEquipmentList * gameIndex

		for (i in 0..equipmentRowsPerApprentice - 1)
		{
			for (entry in equipmentAtLevels)
			{
				ROM.nextByte = entry.level
				ROM.nextByte = entry.equipment[0]
				ROM.nextByte = entry.equipment[1]
				ROM.nextByte = entry.equipment[2]
			}
		}
	}


	companion object
	{
		private val apprenticeListSize = 7
		private val baseOffset = 0x8740
		private val bytesPerApprentice = 60
		private val baseEquipmentOffset = 0x88E4
		private val bytesPerEquipmentList = 44
		private val equipmentRowsPerApprentice = 10
		private val numOfDialogues = 6
	}

}
