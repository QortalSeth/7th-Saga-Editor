package application

import application.models.lists.Lists
import java.io.*
import java.net.URISyntaxException

class Data : Serializable
{

	private val DgoldPerLevel = Lists.apprentices.goldPerLevel
	private val apprentices = ArrayList(Lists.apprentices.dModels)
	private val abstractItems = ArrayList(Lists.abstractItems.dModels)
	private val armors = ArrayList(Lists.armors.dModels)
	private val characters = ArrayList(Lists.characters.dModels)
	private val dropTables = ArrayList(Lists.dropTables.dModels)
	private val items = ArrayList(Lists.items.dModels)
	private val monsters = ArrayList(Lists.monsters.dModels)
	private val shops = ArrayList(Lists.shops.dModels)
	private val spells = ArrayList(Lists.spells.dModels)
	private val weapons = ArrayList(Lists.weapons.dModels)

	private val experience = Lists.experience
	private val defaultMarginalExperience = experience.marginalExpTable
	private val defaultTotalExperience = experience.totalExpTable


	fun serializeDefaultDataToDisk()
	{
		try
		{
			val defaultData = File(javaClass.getResource("models/defaultModels.data").toURI())
			println("file path to disk: " + defaultData.absolutePath)
			val file = FileOutputStream(defaultData)
			val buffer = BufferedOutputStream(file)
			val output = ObjectOutputStream(buffer)
			output.writeObject(this)
			output.close()
			file.close()
		}
		catch (e: IOException)
		{
			e.printStackTrace()
		}
		catch (e: URISyntaxException)
		{
			e.printStackTrace()
		}
	}

	fun serializeDefaultDataFromDisk()
	{
		// fill application.models with new data
		Lists.clear()
		Lists.apprentices.dgoldPerLevel = DgoldPerLevel

		Lists.abstractItems.dModels.addAll(abstractItems)
		Lists.apprentices.dModels.addAll(apprentices)
		Lists.armors.dModels.addAll(armors)
		Lists.characters.dModels.addAll(characters)
		Lists.dropTables.dModels.addAll(dropTables)
		Lists.items.dModels.addAll(items)
		Lists.monsters.dModels.addAll(monsters)
		Lists.shops.dModels.addAll(shops)
		Lists.spells.dModels.addAll(spells)
		Lists.weapons.dModels.addAll(weapons)
		Lists.experience.defaultMarginalExpTable.addAll(defaultMarginalExperience)
		Lists.experience.defaultTotalExpTable.addAll(defaultTotalExperience)
	}
}