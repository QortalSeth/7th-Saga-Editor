package application.staticClasses

import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.value.ChangeListener
import javafx.scene.control.TextField
import javafx.scene.input.KeyEvent
import java.lang.Integer.toString
import java.math.BigInteger


object Listeners
{

	fun removeNonNumbers(event: KeyEvent, exceptions: List<Char>?)
	{
		val keyTyped = event.character[0]
		var exception = false
		exceptions?.forEach {
			if (keyTyped == it)
			{
				exception = true
				return@forEach
			}
		}
		if (!Character.isDigit(keyTyped) && !exception) event.consume()
	}

	fun removeNonHexNumbers(event: KeyEvent, exceptions: List<Char>?)
	{
		val keyTyped = event.character[0].toUpperCase()
		var exception = false
		exceptions?.forEach {
			if (keyTyped == it)
			{
				exception = true
				return@forEach
			}
		}

		if (!Character.isDigit(keyTyped) && keyTyped !in 'A'..'F' && !exception) event.consume()
	}

	fun textFieldFocusListener(maxValue: Int): ChangeListener<Boolean>
	{
		val threeDigitListener = ChangeListener<Boolean> { observable, oldValue, newValue ->
			if (newValue.not())
			{
				val a = observable as ReadOnlyBooleanProperty
				val tf = a.bean as TextField

				if (tf.text.isEmpty())
				{
					tf.text = "0"
					return@ChangeListener
				}

				val bigValue = BigInteger(tf.text)
				var value = bigValue.min(BigInteger.valueOf(maxValue.toLong())).intValueExact()

				if (value < 0) value = 0
				if (value > maxValue) value = maxValue
				tf.text = toString(value)
			}
		}
		return threeDigitListener
	}
}
