package application.models.lists

import application.ROM
import application.models.Model
import java.io.Serializable
import java.util.*

abstract class Models<T : Model> : Serializable
{
	var models: ModelsList<T>
	var dModels: ModelsList<T>

	constructor()
	{
		models = ModelsList<T>()
		dModels = ModelsList<T>()
	}

	constructor(ModelsList: ModelsList<T>)
	{
		models = ModelsList<T>()
		dModels = ModelsList()
		dModels.addUsefulModels(ModelsList, false)
	}

	constructor(addFrom: Models<T>, keepEmptyModel: Boolean)
	{
		models = ModelsList<T>()
		dModels = ModelsList<T>()
		this.addUsefulModels(addFrom, keepEmptyModel)
	}

	constructor(addFrom: Models<T>)
	{
		models = ModelsList<T>()
		dModels = ModelsList<T>()
		this.addUsefulModels(addFrom, false)
	}

	fun initializeDModels() = initialize(dModels)
	fun initializeModels() = initialize(models)

	open protected fun initialize(models: ModelsList<T>) = Unit
	open fun saveModels() = Unit

	fun clear()
	{
		models.clear()
		dModels.clear()
	}

	fun clearModels() = models.clear()

	fun clearDModels() = dModels.clear()

	fun add(model: T)
	{
		models.add(model)
		dModels.add(this.getDModel(model))
	}

	fun sortList(c: Comparator<T>)
	{
		models.sortWith(c)
		//dModels.sortWith(c)
	}

	fun gameIndexSort() = sortList(Comparator<T> { m1, m2 -> m1.gameIndex - m2.gameIndex })
	fun chronologicalIndexSort() = sortList(Comparator<T> { m1, m2 -> m1.chronologicalIndex - m2.chronologicalIndex })

	fun <R : T> addUsefulModels(addFrom: Models<R>, keepEmptyModel: Boolean)
	{
		addFrom.models.filter { model -> ROM.showEmptyValues || !model.name.trim { it <= ' ' }.isEmpty() || keepEmptyModel && model.gameIndex == 0 }.forEach { models.add(it) }
		dModels.addAll(addFrom.dModels)
	}

	open fun getDModel(model: T): T
	{
		dModels.filter { model.gameIndex == it.gameIndex }.forEach { return it }
		println("Model " + model.name + " has no default!")
		TODO()
		return dModels.first()
	}

	fun getDModel(gameIndex: Int): T
	{
		dModels.forEach { if (it.gameIndex == gameIndex) return it }
		TODO()
		return dModels.first()
	}

	fun getModel(gameIndex: Int): T
	{
		models.forEach { if (it.gameIndex == gameIndex) return it }
		TODO()
		return models.first()
	}

	open fun getIndex(index: Int): Int
	{
		models.withIndex().forEach { (i, model) ->
			if (model.gameIndex == index) return i
		}
		return -1
	}

	open fun getDIndex(index: Int): Int
	{
		dModels.withIndex().forEach { (i, model) ->
			if (model.gameIndex == index) return i
		}
		return -1
	}

	open fun getName(gameIndex: Int): String
	{
		models.filter { it.gameIndex == gameIndex }.forEach { return it.toString() }
		return "Model not found"
	}

	open fun getDName(gameIndex: Int): String
	{
		dModels.filter { it.gameIndex == gameIndex }.forEach { return it.toString() }
		return "DModel not found"
	}

	fun getNames(gameIndexes: MutableList<Int>): MutableList<String>
	{
		val names = ArrayList<String>()
		gameIndexes.forEach { names.add(getName(it)) }
		return names
	}

	fun getDNames(gameIndexes: MutableList<Int>): MutableList<String>
	{
		val names = ArrayList<String>()
		gameIndexes.forEach { names.add(getDName(it)) }
		return names
	}

	open fun toIntegers(): MutableList<Int>
	{
		val indexes = ArrayList<Int>()
		models.forEach { indexes.add(it.gameIndex) }
		return indexes
	}

	open fun toDIntegers(): MutableList<Int>
	{
		val indexes = ArrayList<Int>()
		dModels.forEach { indexes.add(it.gameIndex) }
		return indexes
	}
}
