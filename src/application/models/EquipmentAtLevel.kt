package application.models

import java.io.Serializable

class EquipmentAtLevel(val level: Int, val equipment: IntArray) : Comparable<EquipmentAtLevel>, Serializable
{
	override fun compareTo(e: EquipmentAtLevel): Int = this.level - e.level
}
