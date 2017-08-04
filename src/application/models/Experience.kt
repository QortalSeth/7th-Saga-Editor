package application.models

import application.ROM
import java.io.Serializable

class Experience() : Serializable
{
	val baseOffset = 0x8CC8
	val bytesPerExp = 3
	val expListSize = 79
	var totalExpTable = mutableListOf<Int>()
	var marginalExpTable = mutableListOf<Int>()
	var defaultTotalExpTable = mutableListOf<Int>()
	var defaultMarginalExpTable = mutableListOf<Int>()

	constructor(e: Experience) : this()
	{
		totalExpTable.addAll(e.totalExpTable)
		marginalExpTable.addAll(e.marginalExpTable)
		defaultTotalExpTable.addAll(e.defaultTotalExpTable)
		defaultMarginalExpTable.addAll(e.defaultMarginalExpTable)

	}

	fun getValuesFromROM()
	{
		//gets total exp Table
		for (i in 0..expListSize - 1)
		{
			ROM.offset = baseOffset + bytesPerExp * i
			totalExpTable.add(ROM.nextTriple)
		}

		marginalExpTable.add(totalExpTable[0])
		for (i in 1..totalExpTable.size - 1)
		{
			marginalExpTable.add(totalExpTable[i] - totalExpTable[i - 1])
		}
	}

	fun saveModels()
	{
		var sum = 0
		for (i in 0..expListSize - 1)
		{
			ROM.offset = baseOffset + bytesPerExp * i
			val value = marginalExpTable[i]
			sum += value
			ROM.nextTriple = sum
		}
	}

	fun clear()
	{
		totalExpTable.clear()
		marginalExpTable.clear()
		defaultTotalExpTable.clear()
		defaultMarginalExpTable.clear()
	}
}
