package application.models.lists

import application.ROM
import application.models.Model
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import java.io.Serializable

open class ModelsList<T : Model>() : SimpleListProperty<T>(FXCollections.observableArrayList<T>()), Serializable
{
	constructor(models: ModelsList<T>) : this()
	{
		addUsefulModels(models, true)
	}

	fun addUsefulModels(models: ModelsList<T>, keepEmptyModel: Boolean)
	{
		models.asSequence().filter { model -> ROM.showEmptyValues == true || !model.name.trim { it <= ' ' }.isEmpty() || keepEmptyModel == true && model.gameIndex == 0 }.forEach { add(it) }
	}
}