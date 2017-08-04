package application.controllers

import application.ControllerInitilizer
import application.ROM
import application.staticClasses.TextWriter
import javafx.fxml.FXML
import javafx.scene.control.TextArea
import javafx.scene.input.MouseEvent
import java.util.*

class TextEditor : ControllerInitilizer
{
	@FXML lateinit var text2BytesT: TextArea
	@FXML lateinit var hexValuesT: TextArea
	@FXML lateinit var locationsT: TextArea
	@FXML lateinit var pointersT: TextArea


	@FXML fun generateResults(event: MouseEvent)
	{

		val bytes = bytes
		val locations = getLocations(bytes)
		getPointers(locations)
	}

	private fun bytesToString(bytes: List<Int>): String
	{
		val s = StringBuilder()
		var index = 1
		for (b in bytes)
		{
			if (b < 0x10) s.append("0")
			s.append(Integer.toHexString(b).toUpperCase() + " ")
			if (index % 10 == 0) s.append('\n')
			index++
		}
		return s.toString()
	}

	private fun bytesToStringX(bytes: List<Int>): String
	{
		val s = StringBuilder()
		var index = 1
		for (b in bytes)
		{
			if (b < 0x10) s.append("0x0" + Integer.toHexString(b).toUpperCase() + " ")
			else s.append("0x" + Integer.toHexString(b).toUpperCase() + " ")
			if (index % 5 == 0) s.append('\n')
			index++
		}
		return s.toString()
	}

	private val bytes: List<Int>
		get()
		{
			println("Text is: " + text2BytesT.text)
			val bytes = TextWriter.TextToBytes(text2BytesT.text)
			hexValuesT.text = bytesToString(bytes)
			return bytes
		}


	private fun getLocations(bytes: List<Int>): List<Int>
	{
		val results = ArrayList<Int>()

		for (pointer in 0..ROM.bytes.size - 1)
		{
			var match = true
			var p = pointer
			for (b in bytes)
			{
				if (b !== ROM.getByte(p))
				{
					match = false
					break
				}
				else p++
			}
			if (match) results.add(pointer)

		}

		locationsT.text = bytesToStringX(results)
		return results
	}

	private fun getPointers(locations: List<Int>)
	{
		val offsets = ArrayList<Int>()

		for (i in 0..ROM.bytes.size - 5)
		{
			for (x in locations)
			{
				val value = x or 0xC00000

				val romPointer = ROM.getTriple(i)
				if (value == romPointer)
				{
					offsets.add(i)
				}
			}

		}

		pointersT.text = bytesToStringX(offsets)

	}

	@FXML fun initialize()
	{
	}

	override fun saveData()
	{

	}

	override fun saveState()
	{

	}
}
