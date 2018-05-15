package application.controllers

import application.ControllerInitilizer
import application.ROM
import application.staticClasses.Listeners
import application.staticClasses.TextReader
import application.staticClasses.TextWriter
import javafx.fxml.FXML
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import java.util.*

class TextEditor : ControllerInitilizer
{
	@FXML lateinit var pointerToReadT: TextField
	@FXML lateinit var text2BytesT: TextArea
	@FXML lateinit var hexValuesT: TextArea
	@FXML lateinit var locationsT: TextArea
	@FXML lateinit var pointersT: TextArea
	@FXML lateinit var textWriteT: TextArea
	@FXML lateinit var textLocationT: TextField
	@FXML lateinit var textPointerLocationT: TextField
	@FXML lateinit var textPointerValueT: TextField
	@FXML lateinit var textMoverOldLocationT: TextField
	@FXML lateinit var textMoverNewLocationT: TextField


	@FXML fun readText()
	{
		text2BytesT.text = TextReader.readText(pointerToReadT.text.toInt(16))
	}

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

	@FXML fun writeText()
	{
		val bytes = TextWriter.TextToBytes(textWriteT.text)
		bytes.appendEndCharacter()

		if (textLocationT.text.isEmpty()) return

		val location = textLocationT.text.toInt(16)

		ROM.offset = location
		bytes.forEach { ROM.nextByte = it }//// write text to location
	}

	@FXML fun writePointer()
	{
		val pointerLocation = textPointerLocationT.text
		val pointerValue = textPointerValueT.text
		if (pointerLocation.isNotEmpty() && pointerValue.isNotEmpty()) // overwrite pointer
			ROM.setTriple(pointerLocation.toInt(16), pointerValue.toInt(16))
	}

	// get text into list
	// replace original text with 0xFF
	// place text in new location
	@FXML fun moveText()
	{
		val oldText = textMoverOldLocationT.text
		val newText = textMoverNewLocationT.text
		if (oldText.isNotEmpty() && newText.isNotEmpty()) // overwrite pointer
		{
			var oldPointer = oldText.toInt(16)
			var newPointer = newText.toInt(16)
			var text = mutableListOf<Int>()
			var pointer = oldPointer
			ROM.offset = oldPointer

			while(pointer< ROM.bytes.size)
			{
			var byte = ROM.getByte(pointer)
				text.add(byte)
				ROM.setByte(pointer, 0xFF)
				pointer++
			if(byte == 0xF7)
				break
			}

			ROM.offset=newPointer
			text.forEach{ROM.nextByte = it}
		}
	}

	@FXML fun removeNonHexNumbers(event: KeyEvent) = Listeners.removeNonHexNumbers(event, null)

	@FXML fun initialize()
	{
	}

	override fun saveData()
	{

	}

	override fun saveState()
	{
		textToReadS = pointerToReadT.text
		text2BytesS = text2BytesT.text
		hexValuesS = hexValuesT.text
		locationsS = locationsT.text
		pointersS = pointersT.text
		textWriteS = textWriteT.text
		textLocationS = textLocationT.text
		textPointerLocationS = textPointerLocationT.text
		textPointerValueS = textPointerValueT.text
	}

	private fun MutableList<Int>.appendEndCharacter()
	{
		this.add(0xF7)
	}

	companion object
	{
		private var textToReadS: String = ""
		private var text2BytesS: String = ""
		private var hexValuesS: String = ""
		private var locationsS: String = ""
		private var pointersS: String = ""
		private var textWriteS: String = ""
		private var textLocationS: String = ""
		private var textPointerLocationS: String = ""
		private var textPointerValueS: String = ""
	}
}


