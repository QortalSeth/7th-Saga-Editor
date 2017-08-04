package application.models

import java.io.Serializable

open class AbstractItem : Model, Serializable
{

	var itemCode: Int = 0
	var cost: Int = 0


	constructor(index: Int) : super(index)
	constructor(i: AbstractItem) : super(i)
	{
		this.itemCode = i.itemCode
		this.cost = i.cost
	}

}