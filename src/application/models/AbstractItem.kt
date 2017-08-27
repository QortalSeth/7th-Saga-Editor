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

	fun getCostText(): MutableList<Int>
	{
		var list = mutableListOf<Int>()
		var temp = cost
		while (temp > 0)
		{
			list.add(temp % 10)
			temp /= 10
		}
		list.reverse()

		return list
	}

}