package application

import java.io.*
import java.net.URISyntaxException

class Settings : Serializable{

   var showEmptyValues = false
   var showMonsterDuplicates = false
   var createExcelFilesOnSave = true
   var belaineSwordsMatchCosts = true




    constructor()
    constructor(belaineSwordsMatchCosts: Boolean, createExcelFilesOnSave: Boolean, showEmptyValues: Boolean, showMonsterDuplicates: Boolean) {
        this.belaineSwordsMatchCosts = belaineSwordsMatchCosts
        this.createExcelFilesOnSave = createExcelFilesOnSave
        this.showEmptyValues = showEmptyValues
        this.showMonsterDuplicates = showMonsterDuplicates
    }


    fun readSettings() {
        try {
            ObjectInputStream(BufferedInputStream(FileInputStream(File(ROM.programDirectory.absolutePath + "Settings.data")))).use{

                val s = it.readObject() as Settings
                showEmptyValues = s.showEmptyValues
                showMonsterDuplicates = s.showMonsterDuplicates
                createExcelFilesOnSave = s.createExcelFilesOnSave
                belaineSwordsMatchCosts = s.belaineSwordsMatchCosts

            }
            printSettings()
        }
        catch(e:Exception){println("No Settings Found")}
    }

    fun writeSettings()
    {
        try
        {
            ObjectOutputStream(BufferedOutputStream(FileOutputStream(File(ROM.programDirectory.absolutePath + "Settings.data")))).use{it.writeObject(this)}
            printSettings()
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            println("Write Settings Failed")
        }
    }



    fun printSettings()
    {
        println("Settings Location: ${ROM.programDirectory.absolutePath}")
        println("Show Empty Values: $showEmptyValues")
        println("Show Monster Duplicates: $showMonsterDuplicates")
        println("Create Excel Files On Save: $createExcelFilesOnSave")
        println("Belaine Swords Match Costs: $belaineSwordsMatchCosts")
    }
}