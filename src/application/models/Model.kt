package application.models

import java.io.Serializable

abstract class Model(index: Int) : Serializable
{
	var name: String = ""
	var gameIndex = index
	var namePointer: Int = 0
	var chronologicalIndex: Int = 0
	var listIndex: Int = 0

	constructor(m: Model) : this(m.gameIndex)
	{
		name = m.name
		namePointer = m.namePointer
		chronologicalIndex = m.chronologicalIndex
		listIndex = m.listIndex
	}

	override fun toString(): String
	{
		if (name.trim().isEmpty()) return "(empty)"
		return name
	}

}
