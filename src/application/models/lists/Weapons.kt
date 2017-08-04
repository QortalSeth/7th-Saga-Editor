package application.models.lists

import application.models.Weapon
import java.io.Serializable

class Weapons : Equipment<Weapon>, Serializable
{

	constructor() : super()
	constructor(addFrom: Weapons, equipCode: Int) : super(addFrom, equipCode)
	constructor(addFrom: Weapons) : super(addFrom)
	constructor(gameIndexes: List<Int>) : super(gameIndexes)
	constructor(DmodelsList: ModelsList<Weapon>) : super(DmodelsList)

	constructor(addFrom: Weapons, addEmptyModel: Boolean) : super(addFrom)
	{
		if (addEmptyModel) this.addEmptyModel(models)
	}


	override fun initialize(models: ModelsList<Weapon>)
	{
		for (i in 0..weaponListSize - 1)
		{
			val w = Weapon(i)
			w.getValuesFromROM()
			models.add(w)
		}
	}

	override fun saveModels() = models.forEach { it.writeValuesToROM() }

	override fun addEmptyModel(models: MutableList<Weapon>)
	{
		val emptyWeapon = Weapon(0)
		emptyWeapon.gameIndex = 0
		emptyWeapon.itemCode = 0
		emptyWeapon.name = ""
		emptyWeapon.power = 0
		emptyWeapon.equipCode = 255
		models.add(0, emptyWeapon)
	}

	companion object
	{
		private val weaponListSize = 51
	}
}
