package application.models.lists

import application.models.AbstractItem
import application.models.Experience
import java.io.Serializable

object Lists : Serializable
{

	var abstractItems = AbstractItems<AbstractItem>()
	var apprentices = Apprentices()
	var armors = Armors()
	var characters = Characters()
	var dropTables = DropTables()
	var items = Items()
	var monsters = Monsters()
	var shops = Shops()
	var spells = Spells()
	var weapons = Weapons()
	var experience = Experience()


	fun initializeModels()
	{
		apprentices.initializeModels()
		armors.initializeModels()
		characters.initializeModels()
		dropTables.initializeModels()
		items.initializeModels()
		monsters.initializeModels()
		shops.initializeModels()
		spells.initializeModels()
		weapons.initializeModels()
		abstractItems.initializeModels()
		experience.getValuesFromROM()
	}

	fun initializeDModels()
	{
		apprentices.initializeDModels()
		armors.initializeDModels()
		characters.initializeDModels()
		dropTables.initializeDModels()
		items.initializeDModels()
		monsters.initializeDModels()
		shops.initializeDModels()
		spells.initializeDModels()
		weapons.initializeDModels()
		abstractItems.initializeDModels()
		experience.getValuesFromROM()
	}

	fun saveModels()
	{
		apprentices.saveModels()
		items.saveModels()
		armors.saveModels()
		characters.saveModels()
		dropTables.saveModels()
		shops.saveModels()
		spells.saveModels()
		monsters.saveModels()
		weapons.saveModels()
		experience.saveModels()
		println("bytes[] saved")
	}

	fun clear()
	{
		apprentices.clear()
		items.clear()
		armors.clear()
		characters.clear()
		dropTables.clear()
		shops.clear()
		spells.clear()
		monsters.clear()
		weapons.clear()
		experience.clear()
	}
}
