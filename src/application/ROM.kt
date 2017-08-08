package application

import application.excel.*
import application.models.lists.Lists
import java.io.BufferedInputStream
import java.io.File
import java.io.IOException
import java.io.ObjectInputStream
import java.nio.file.Files
import java.nio.file.StandardOpenOption

/**
 * Created by Seth on 4/3/2017.
 */
object ROM
{
	var bytes: ByteArray = byteArrayOf(1)
	private var header: Int = 0
	@JvmStatic var showEmptyValues = false
	var showMonsterDuplicates = false
	var programDirectory = File(".")

	private var rom = File(".")
	var romDirectory = File(".")
	var magicAndSpeedPatch = false


	var changeListsDirectory = File(".")
	var offset: Int = 0


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
		}
	}

	private fun removeFileExtension(file: File): String
	{
		var fileName = file.name
		val pos = fileName.lastIndexOf(".")
		if (pos > 0) fileName = fileName.substring(0, pos)
		return fileName
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
		saveToExcelFiles()
		Files.write(rom.toPath(), ROM.bytes, StandardOpenOption.WRITE)
		println("Data saved to Disk" + "\n")
	}

	fun saveToExcelFiles()
	{
		CharacterComparisons("Character Comparisons.xlsx")
		CharacterData("Character.xlsx")
		EquipmentData("Equipment.xlsx")
		ExpData("Experience.xlsx")
		MonsterData("Monster.xlsx")

		// ApprenticeData.makeChangeList();
		// DropTableData.makeChangeList();
		// ItemData.makeChangeList();
		// ShopData.makeChangeList();
		// SpellData.makeChangeList();
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
