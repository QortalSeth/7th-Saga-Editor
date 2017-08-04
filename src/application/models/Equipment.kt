package application.models

import java.io.Serializable

open class Equipment : AbstractItem, Serializable
{
	var power: Int = 0
	var equipCode: Int = 0
	var discount: Int = 0

	constructor(index: Int) : super(index)
	constructor(e: Equipment) : super(e)
	{
		power = e.power
		cost = e.cost
		equipCode = e.equipCode
		discount = e.discount
	}
}
