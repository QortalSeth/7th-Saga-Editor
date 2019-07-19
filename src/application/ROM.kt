package application

import application.excel.*
import application.models.lists.Lists
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.io.BufferedInputStream
import java.io.File
import java.io.IOException
import java.io.ObjectInputStream
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import kotlinx.coroutines.experimental.runBlocking

/**
 * Created by Seth on 4/3/2017.
 */
object ROM
{
	var bytes: ByteArray = byteArrayOf(1)
	private var header: Int = 0


	var programDirectory = File(".")
	private var rom = File(".")
	var romDirectory = File(".")
	var magicAndSpeedPatch = false


	var changeListsDirectory = File(".")
	var offset: Int = 0
	var settings = Settings()

	fun openDefaultROM()
	{
		try
		{
			rom = File(romDirectory.toString() + "/7th Saga Unedited.smc")
			bytes = Files.readAllBytes(rom.toPath())
			header = 0
			Lists.initializeDModels()
			val d = Data()
			d.serializeDefaultDataToDisk()
		}
		catch (e: Exception)
		{
			println("Open default ROM failed")
			e.printStackTrace()
		}
	}



	fun openROM(file: File)
	{
		rom = file
		changeListsDirectory = File(romDirectory.toString() + "/ChangeLists/" + removeFileExtension(rom))
		println(rom.canonicalPath)
		try
		{
			bytes = Files.readAllBytes(rom.toPath())

			val headerTest = ROM.bytes.size % 1024
			when (headerTest)
			{
				0    -> ROM.header = 0
				512  -> ROM.header = 512
				else ->
				{
					ROM.header = 0
					println("improperly configured ROM")
				}
			}

			// assemble Data object

			println("file path from disk: " + File(javaClass.getResource("models/defaultModels.data").toExternalForm()))
			val inputFile = BufferedInputStream(this.javaClass.getResourceAsStream("models/defaultModels.data"))
			// File defaultData = new File(Main.class.getResource("models/defaultModels.data").toURI());
			// System.out.println("File Path from disk: " + defaultData.getAbsolutePath());
			ObjectInputStream(inputFile).use {
				// deserialize the List
				val d = it.readObject() as Data
				d.serializeDefaultDataFromDisk()
			}

			Lists.initializeModels()
			if (getByte(0x7FFF0) and 1 > 0 && getByte(0x7FFF0) != 0xFF)
			{
				magicAndSpeedPatch = true
			}
		}
		catch(e: IOException)
		{
			e.printStackTrace()
		}
	}

	fun saveROM()
	{
		Lists.saveModels()
		println("Before save: " + Integer.toHexString(ROM.getTriple(0x6532)).toUpperCase())
		if(ROM.settings.createExcelFilesOnSave){saveToExcelFiles()}

		Files.write(rom.toPath(), ROM.bytes, StandardOpenOption.WRITE)
		println("Data saved to Disk" + "\n")
	}

	fun saveToExcelFiles()
	{
	launch()
	}

	fun standard()
	{
		val startTime = System.currentTimeMillis()

		CharacterComparisons("Character Comparisons.xlsx")
		CharacterData("Character.xlsx")
		EquipmentData("Equipment.xlsx")
		ExpData("Experience.xlsx")
		MonsterData("Monster.xlsx")
		val endTime = System.currentTimeMillis()
		val timeElapsed = (endTime - startTime)
		println("Excel Export Time is: ${timeElapsed.toDouble()/1000} seconds")
	}

	fun launch() = runBlocking {

		val startTime = System.currentTimeMillis()

		val job1 = launch{CharacterComparisons("Character Comparisons.xlsx")}
		val job2 = 	launch{	CharacterData("Character.xlsx")}
		val job3 = 	launch{EquipmentData("Equipment.xlsx")}
		val job4 = 	launch{ExpData("Experience.xlsx")}
		val job5 = 	launch{MonsterData("Monster.xlsx")}


		job1.join()
		job2.join()
		job3.join()
		job4.join()
		job5.join()
		val endTime = System.currentTimeMillis()
		val timeElapsed = (endTime - startTime)
		println("Excel Export Time is: ${timeElapsed.toDouble()/1000} seconds")
	}

	fun acyncExcel()
	{
		val startTime = System.currentTimeMillis()

		val cc = async{CharacterComparisons("Character Comparisons.xlsx")}
		val ch = async{CharacterData("Character.xlsx")}
		val eq = async{EquipmentData("Equipment.xlsx")}
		val ex = async{ExpData("Experience.xlsx")}
		val mo = async{MonsterData("Monster.xlsx")}

		runBlocking {
			cc.await()
			ch.await()
			eq.await()
			ex.await()
			mo.await()
			val endTime = System.currentTimeMillis()
			val timeElapsed = (endTime - startTime)
			println("Excel Export Time is: ${timeElapsed.toDouble()/1000} seconds")
		}
	}





	fun setProgramDirectory()
	{
		try
		{
			//programDirectory =new File(System.getProperty("java.class.path")).getParentFile();
			programDirectory = File(".")
			programDirectory = programDirectory
		}
		catch (e: Exception)
		{
			programDirectory = File(".")
		}

	}

	private fun removeFileExtension(file: File): String
	{
		var fileName = file.name
		val pos = fileName.lastIndexOf(".")
		if (pos > 0) fileName = fileName.substring(0, pos)
		return fileName
	}

	fun getByte(offset: Int): Int
	{
		val unsignedByte: Int = bytes[offset + header].toInt() and 0xFF
		return unsignedByte
	}

	// this returned value is little endian
	fun getShort(offset: Int): Int
	{
		val unsignedByte1 = getByte(offset)
		var unsignedByte2 = getByte(offset + 1)

		unsignedByte2 = unsignedByte2 shl 8
		return unsignedByte1 or unsignedByte2

	}

	// this returned value is little endian
	fun getTriple(offset: Int): Int
	{
		val shortValue = getShort(offset)
		var finalByte = getByte(offset + 2)
		finalByte = finalByte shl 16
		return finalByte or shortValue
	}

	fun setByte(offset: Int, value: Int)
	{
		bytes[offset + header] = value.toByte()
	}

	fun setShort(offset: Int, value: Int)
	{
		var value = value
		setByte(offset, value and 0xFF)
		value = value shr 8
		setByte(offset + 1, value and 0xFF)
	}

	fun setTriple(offset: Int, value: Int)
	{
		var value = value
		setShort(offset, value)
		value = value shr 16
		setByte(offset + 2, value and 0xFF)
	}

	var nextByte: Int
		get() = getByte(offset++)
		set(value)
		{
			setByte(offset, value)
			offset++
		}
	var nextShort: Int
		get()
		{
			val value = getShort(offset)
			offset += 2
			return value
		}
		set(value)
		{
			setShort(offset, value)
			offset += 2
		}
	var nextTriple: Int
		get()
		{
			val value = getTriple(offset)
			offset += 3
			return value
		}
		set(value)
		{
			setTriple(offset, value)
			offset += 3
		}
}
